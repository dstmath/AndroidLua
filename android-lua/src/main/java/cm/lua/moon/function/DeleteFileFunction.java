package cm.lua.moon.function;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;

import java.io.File;
import java.io.IOException;

public class DeleteFileFunction extends JavaFunction {
    public DeleteFileFunction(LuaState luaState) {
        super(luaState);
    }

    @Override
    public int execute() throws LuaException {
        Object toJavaObject = this.L.toJavaObject(2);
        File file = toJavaObject instanceof String ? new File((String) toJavaObject) : toJavaObject instanceof File ? (File) toJavaObject : null;
        try {
            Runtime.getRuntime().exec("chmod 777 " + file.getAbsolutePath());
            file.delete();
            return 0;
        } catch (IOException e) {
            throw new LuaException("request permission error");
        }
    }
}
