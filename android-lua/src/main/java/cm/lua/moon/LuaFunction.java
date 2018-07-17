package cm.lua.moon;

public interface LuaFunction<Params, Result> {
    Result execute(Params... paramsArr);
}
