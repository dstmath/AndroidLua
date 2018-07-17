package cm.lua.moon.function;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;

public class InstanceOfFunction extends JavaFunction {

    public InstanceOfFunction(LuaState luaState) {
        super(luaState);
    }

    public static void main(String[] strArr) {
        try {
            System.out.println(new RuntimeException("test").getClass().isAssignableFrom(Class.forName("java.lang.NullPointerException")));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int execute() throws LuaException {
        Object toJavaObject = this.L.toJavaObject(2);
        Object toJavaObject2 = this.L.toJavaObject(3);
        if (!(toJavaObject2 instanceof String)) {
            this.L.pushBoolean(false);
            return 1;
        } else if (toJavaObject == null) {
            this.L.pushBoolean(false);
            return 1;
        } else {
            try {
                this.L.pushBoolean(toJavaObject.getClass().isInstance(Class.forName((String) toJavaObject2)));
                return 1;
            } catch (ClassNotFoundException e) {
                throw new LuaException(((String) toJavaObject2) + "is not found");
            }
        }
    }
}
