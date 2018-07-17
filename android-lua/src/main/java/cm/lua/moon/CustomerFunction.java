package cm.lua.moon;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;

import java.util.ArrayList;
import java.util.List;

public class CustomerFunction extends JavaFunction {
    private static List<String> protectedMethod = new ArrayList<>();
    private LuaFunction function;

    static {
        protectedMethod.add("eval");
        protectedMethod.add("print");
        protectedMethod.add("instanceof");
        protectedMethod.add("delFile");
        protectedMethod.add("uploadFile");
        protectedMethod.add("log");
        protectedMethod.add("context");
    }

    public CustomerFunction(LuaState luaState, LuaFunction luaFunction) {
        super(luaState);
        this.function = luaFunction;
    }

    private boolean isProtected(String str) {
        return protectedMethod.contains(str);
    }

    @Override
    public int execute() throws LuaException {
        int i = 0;
        int top = this.L.getTop();
        Object[] objArr = new Object[(top - 1)];
        for (int i2 = 2; i2 <= top; i2++) {
            String typeName = this.L.typeName(this.L.type(i2));
            if ("number".equals(typeName)) {
                objArr[i2 - 2] = Double.valueOf(this.L.toNumber(i2));
            } else if ("string".equals(typeName)) {
                objArr[i2 - 2] = this.L.toString(i2);
            } else if ("boolean".equals(typeName)) {
                objArr[i2 - 2] = Boolean.valueOf(this.L.toBoolean(i2));
            } else if ("userdata".equals(typeName)) {
                objArr[i2 - 2] = this.L.toJavaObject(i2);
            }
        }
        while (i < objArr.length) {
            Logw.d("MOON", i + "::o::" + objArr[i]);
            i++;
        }
        this.L.pushJavaObject(this.function.execute(objArr));
        return 1;
    }

    @Override
    public void register(String str) throws LuaException {
        if (!isProtected(str)) {
            super.register(str);
        }
    }
}
