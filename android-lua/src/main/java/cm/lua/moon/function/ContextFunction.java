package cm.lua.moon.function;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;

import cm.lua.moon.LuaRuntime;

public class ContextFunction extends JavaFunction {

    public ContextFunction(LuaState luaState) {
        super(luaState);
    }

    @Override
    public int execute() throws LuaException {
        try {
            this.L.pushJavaObject(LuaRuntime.handleContext());
            return 1;
        } catch (Throwable th) {
            return 0;
        }
    }
}
