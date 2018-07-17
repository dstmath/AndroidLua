package cm.lua.moon.function;

import org.keplerproject.luajava.JavaFunction;
import org.keplerproject.luajava.LuaException;
import org.keplerproject.luajava.LuaState;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import cm.lua.moon.Logw;

public class DumpFunction extends JavaFunction {

    public DumpFunction(LuaState luaState) {
        super(luaState);
    }

    @Override
    public int execute() throws LuaException {
        try {
            Object toJavaObject = this.L.toJavaObject(2);
            if (toJavaObject == null || !(toJavaObject instanceof String)) {
                return 0;
            }
            StringBuilder stringBuilder = new StringBuilder(64);
            Class cls = Class.forName((String) toJavaObject);
            stringBuilder.append("className::" + cls.getName());
            stringBuilder.append("\n");
            Constructor[] declaredConstructors = cls.getDeclaredConstructors();
            stringBuilder.append("constructors::\n");
            for (Constructor constructor : declaredConstructors) {
                stringBuilder.append(constructor.toString());
                stringBuilder.append("\n");
            }
            Method[] declaredMethods = cls.getDeclaredMethods();
            stringBuilder.append("methods::\n");
            for (Method method : declaredMethods) {
                stringBuilder.append(method.toString());
                stringBuilder.append("\n");
            }
            Field[] declaredFields = cls.getDeclaredFields();
            stringBuilder.append("fields::\n");
            for (Field field : declaredFields) {
                stringBuilder.append(field.toString());
                stringBuilder.append("\n");
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            String stringBuilder2 = stringBuilder.toString();
            Logw.trace("MOON", stringBuilder2);
            countDownLatch.await(1, TimeUnit.SECONDS);
            return 0;
        } catch (Throwable th) {
            th.printStackTrace();
            return 0;
        }
    }
}
