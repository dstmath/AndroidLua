package cm.lua.moon.function;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;

public class EvalFunction extends JavaFunction {
    public Object result;

    public EvalFunction(LuaState luaState) {
        super(luaState);
    }

    @Override
    public int execute() throws LuaException {
        for (int i = 2; i <= this.L.getTop(); i++) {
            String typeName = this.L.typeName(this.L.type(i));
            if (typeName.equals("userdata")) {
                Object toJavaObject = this.L.toJavaObject(i);
                if (toJavaObject != null) {
                    this.result = toJavaObject;
                }
            } else if (typeName.equals("number")) {
                this.result = Double.valueOf(this.L.toNumber(i));
            } else if (typeName.equals("boolean")) {
                this.result = Boolean.valueOf(this.L.toBoolean(i));
            } else {
                this.result = this.L.toString(i);
            }
            if (this.result == null) {
                this.result = typeName;
            }
        }
        return 0;
    }
}
