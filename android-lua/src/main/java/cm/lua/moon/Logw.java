package cm.lua.moon;

import org.keplerproject.luajava.LuaJavaAPI;

import android.util.Log;

public class Logw {
    public static final String TAG = "MOON";

    public Logw() {
    }

    public static int d(String str, String str2) {
        return (isLoggable(str, 3) && LuaJavaAPI.isDebug) ? Log.d(str, str2) : -1;
    }

    public static int d(String str, String str2, Throwable th) {
        return (isLoggable(str, 3) && LuaJavaAPI.isDebug) ? Log.d(str, str2, th) : -1;
    }

    public static int e(String str, String str2) {
        return (isLoggable(str, 6) && LuaJavaAPI.isDebug) ? Log.e(str, str2) : -1;
    }

    public static int e(String str, String str2, Throwable th) {
        return (isLoggable(str, 6) && LuaJavaAPI.isDebug) ? Log.e(str, str2, th) : -1;
    }

    public static int i(String str, String str2) {
        return (isLoggable(str, 4) && LuaJavaAPI.isDebug) ? Log.i(str, str2) : -1;
    }

    public static int i(String str, String str2, Throwable th) {
        return (isLoggable(str, 4) && LuaJavaAPI.isDebug) ? Log.i(str, str2, th) : -1;
    }

    private static boolean isLoggable(String str, int i) {
        return Log.isLoggable(str, i);
    }

    public static void trace(String str, String str2) {
        if (LuaJavaAPI.isDebug) {
            System.out.println(str + "::" + str2);
        }
    }

    public static int v(String str, String str2) {
        return (isLoggable(str, 2) && LuaJavaAPI.isDebug) ? Log.v(str, str2) : -1;
    }

    public static int v(String str, String str2, Throwable th) {
        return (isLoggable(str, 2) && LuaJavaAPI.isDebug) ? Log.v(str, str2, th) : -1;
    }

    public static int w(String str, String str2) {
        return (isLoggable(str, 5) && LuaJavaAPI.isDebug) ? Log.w(str, str2) : -1;
    }

    public static int w(String str, String str2, Throwable th) {
        return (isLoggable(str, 5) && LuaJavaAPI.isDebug) ? Log.w(str, str2, th) : -1;
    }
}
