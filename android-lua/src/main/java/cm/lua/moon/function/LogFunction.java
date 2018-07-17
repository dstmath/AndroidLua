package cm.lua.moon.function;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;

import android.util.Log;

import cm.lua.moon.Logw;

public class LogFunction extends JavaFunction {

    public LogFunction(LuaState luaState) {
        super(luaState);
    }

    @Override
    public int execute() throws LuaException {
        Object toJavaObject = this.L.toJavaObject(2);
        if (toJavaObject == null || !(toJavaObject instanceof String)) {
            return 0;
        }
        Log.e(Logw.TAG, (String) toJavaObject);
        return 0;
    }
}
