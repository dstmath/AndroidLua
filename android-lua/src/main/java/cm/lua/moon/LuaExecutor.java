package cm.lua.moon;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;
import org.keplerproject.luajava.LuaStateFactory;

import android.content.Context;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import cm.lua.moon.function.ContextFunction;
import cm.lua.moon.function.DeleteFileFunction;
import cm.lua.moon.function.DumpFunction;
import cm.lua.moon.function.EvalFunction;
import cm.lua.moon.function.InstanceOfFunction;
import cm.lua.moon.function.LogFunction;

public class LuaExecutor {
    public static Map<String, LuaFunction<?, ?>> functions = new HashMap();
    private LuaState L;
    private ContextFunction contextFunction;
    private DeleteFileFunction deleteFile;
    private DumpFunction dumpFunction;
    private EvalFunction eval;
    private InstanceOfFunction instanceOf;
    private LogFunction logFunction;
    private final StringBuilder output;
//    private UploadFileFunction uploadFile;


    public LuaExecutor() {
        this.output = new StringBuilder();
    }

    private String errorReason(int i) {
        switch (i) {
            case 1:
                return "Yield error";
            case 2:
                return "\nRuntime error\n";
            case 3:
                return "Syntax error";
            case 4:
                return "Out of memory";
            default:
                return "Unknown error " + i;
        }
    }

    private void evalLua(String str) throws LuaException {
        this.L.setTop(0);
        int ok = this.L.LloadString(str);

        if (ok != 0) {
            throw new LuaException(errorReason(ok) + "javaStackTrace:\n" + this.L.toString(-1));
        }

        this.L.getGlobal("debug");
        this.L.getField(-1, "traceback");
        this.L.remove(-2);
        this.L.insert(-2);
        ok = this.L.pcall(0, 0, -2);
        if (ok != 0) {
            return;
        }
        String stringBuilder = this.output.toString();
        this.output.setLength(0);
        Logw.d("MOON", "result::" + stringBuilder);
    }

    private void init(Context context) {
        try {
            this.L = LuaStateFactory.newLuaState();
            this.L.openLibs();
            setupMethods(this.L, context);
            Logw.trace("LuaExecutor", "init success");
        } catch (Throwable th) {
            Logw.e(Logw.TAG, "init", th);
        }
    }

    public static LuaExecutor newInstance(Context context) {
        LuaExecutor luaExecutor = new LuaExecutor();
        luaExecutor.init(context);
        return luaExecutor;
    }

    private void setupMethods(LuaState luaState, final Context context) {
        try {
            JavaFunction print = new JavaFunction(luaState) {

                @Override
                public int execute() throws LuaException {
                    LuaExecutor.this.output.append("\n");
                    int i = 2;
                    while (i <= this.L.getTop()) {
                        String typeName = this.L.typeName(this.L.type(i));
                        String str = null;
                        if (typeName.equals("userdata")) {
                            Object toJavaObject = this.L.toJavaObject(i);
                            if (toJavaObject != null) {
                                str = toJavaObject.toString();
                            }
                        } else {
                            str = typeName.equals("number") ? String.valueOf(this.L.toNumber(i)) : typeName.equals("boolean") ? this.L.toBoolean(i) ? "true" : "false" : this.L.toString(i);
                        }
                        if (str == null) {
                            str = typeName;
                        }
                        LuaExecutor.this.output.append(str);
                        LuaExecutor.this.output.append("\t");
                        Logw.trace("MOON", "print::" + str);
                        i++;
                    }
                    LuaExecutor.this.output.append("\n");
                    return 0;
                }
            };
            print.register("print");

            this.eval = new EvalFunction(luaState);
            this.eval.register("eval");
            this.instanceOf = new InstanceOfFunction(luaState);
            this.instanceOf.register("instanceof");
            this.deleteFile = new DeleteFileFunction(luaState);
            this.deleteFile.register("delFile");
//            this.uploadFile = new UploadFileFunction(luaState);
//            this.uploadFile.register("uploadFile");
            this.logFunction = new LogFunction(luaState);
            this.logFunction.register("log");
            this.contextFunction = new ContextFunction(luaState);
            this.contextFunction.register("context");
            this.dumpFunction = new DumpFunction(luaState);
            this.dumpFunction.register("dump");
            synchronized (functions) {
                for (Entry entry : functions.entrySet()) {
                    new CustomerFunction(luaState, (LuaFunction) entry.getValue()).register((String) entry.getKey());
                }
            }
            JavaFunction assetLoader = new JavaFunction(luaState) {

                private byte[] readAll(InputStream inputStream) throws Exception {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(4096);
                    byte[] bArr = new byte[4096];
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (-1 == read) {
                            return byteArrayOutputStream.toByteArray();
                        }
                        byteArrayOutputStream.write(bArr, 0, read);
                    }
                }

                @Override
                public int execute() throws LuaException {
                    String luaState = this.L.toString(-1);
                    try {
                        this.L.LloadBuffer(readAll(context.getAssets().open(luaState + ".lua")), luaState);
                        return 1;
                    } catch (Exception e) {
                        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        e.printStackTrace(new PrintStream(byteArrayOutputStream));
                        this.L.pushString("Cannot load module " + luaState + ":\n" + byteArrayOutputStream.toString());
                        return 1;
                    }
                }
            };
            luaState.getGlobal("package");
            luaState.getField(-1, "loaders");
            int nLoaders = luaState.objLen(-1);

            luaState.pushJavaFunction(assetLoader);
            luaState.rawSetI(-2, nLoaders + 1);
            luaState.pop(1);

            luaState.getField(-1, "path");
            luaState.pushString(";" + (context.getFilesDir() + "/?.lua"));
            luaState.concat(2);
            luaState.setField(-2, "path");
            luaState.pop(1);
        } catch (Throwable th) {
            Logw.e(Logw.TAG, "setupMethods", th);
        }
    }

    public LuaResult execute(LuaRequest luaRequest) {
        int i = 0;
        LuaResult luaResult = new LuaResult();
        try {
            if (luaRequest.args == null) {
                luaResult.e = new IllegalArgumentException("lua args is null");
                return luaResult;
            } else if (TextUtils.isEmpty(luaRequest.luaScript)) {
                luaResult.e = new IllegalArgumentException("lua script is null");
                return luaResult;
            } else {
                Object[] objArr = luaRequest.args;
                int length = objArr.length;
                while (i < length) {
                    this.L.pushJavaObject(objArr[i]);
                    this.L.setGlobal("arg" + (i + 1));
                    i++;
                }
                Logw.trace("LuaExecutor", "execute push args success");
                evalLua(luaRequest.luaScript);
                Logw.trace("LuaExecutor", "execute eval lua script success");
                if (this.eval.result == null) {
                    return luaResult;
                }
                luaResult.result = this.eval.result;
                Logw.d("MOON", "eval result::" + this.eval.result);
                return luaResult;
            }
        } catch (Throwable e2) {
            luaResult.e = e2;
            Logw.e(Logw.TAG, "execute", e2);
            return luaResult;
        }
    }
}
