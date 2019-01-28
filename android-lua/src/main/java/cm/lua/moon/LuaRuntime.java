package cm.lua.moon;

import org.keplerproject.luajava.LuaJavaAPI;

import android.content.Context;
import android.text.TextUtils;

import java.lang.ref.WeakReference;

public class LuaRuntime {
    private static WeakReference<Context> luaContext;

    public LuaRuntime() {
    }

    public static void addLuaInterface(String str, LuaFunction luaFunction) {
        synchronized (LuaExecutor.functions) {
            if (TextUtils.isEmpty(str) || luaFunction == null) {
                return;
            }

            if (LuaExecutor.functions.containsKey(str)) {
                LuaExecutor.functions.remove(str);
            }
            LuaExecutor.functions.put(str, luaFunction);
            Logw.trace("LuaRuntime", "addLuaInterface name::" + str + ", function::" + luaFunction);
        }
    }

    public static void debug(boolean z) {
        LuaJavaAPI.isDebug = z;
    }

    /**
     * ui相关操作必须在主线程执行：toast显示及activity跳转
     * @param str
     * @return
     */
    public static LuaResult exec(String str) {
        return invoke(new LuaRequest.Builder().luaScript(str).build());
    }

    public static LuaResult exec(String str, Object... objArr) {
        return invoke(new LuaRequest.Builder().luaScript(str).args(objArr).build());
    }

    public static Context handleContext() {
        if (luaContext == null) {
            return LuaUtils.getApplicationContext();
        }
        Context context = luaContext.get();
        return context == null ? LuaUtils.getApplicationContext() : context;
    }

    private static LuaResult invoke(LuaRequest luaRequest) {
        Context handleContext = handleContext();
        if (handleContext != null) {
            return LuaExecutor.newInstance(handleContext).execute(luaRequest);
        }
        LuaResult luaResult = new LuaResult();
        luaResult.e = new IllegalStateException("Lua context is null");
        return luaResult;
    }

    public static void setupContext(Context context) {
        if (context == null) {
            return;
        }
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            context = applicationContext;
        }
        luaContext = new WeakReference(context);
        Logw.trace("LuaRuntime", "setupContext success");
    }
}
