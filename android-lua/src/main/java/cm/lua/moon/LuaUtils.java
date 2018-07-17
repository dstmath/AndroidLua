package cm.lua.moon;

import android.app.Application;
import android.content.Context;

public class LuaUtils {

    public LuaUtils() {
    }

    public static Context getApplicationContext() {
        try {
            Application application = (Application) Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication", new Class[0]).invoke(null, new Object[0]);
            return application != null ? application.getApplicationContext() : null;
        } catch (Throwable th) {
            return null;
        }
    }

}
