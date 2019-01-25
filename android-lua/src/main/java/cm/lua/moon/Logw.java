package cm.lua.moon;

import org.keplerproject.luajava.LuaJavaAPI;

import android.util.Log;

public class Logw {
    public static final String TAG = "MOON";

    public Logw() {
    }

    public static int d(String tag, String msg) {
        return (isLoggable(tag, Log.DEBUG) && LuaJavaAPI.isDebug) ? Log.d(tag, msg) : -1;
    }

    public static int d(String tag, String msg, Throwable tr) {
        return (isLoggable(tag, Log.DEBUG) && LuaJavaAPI.isDebug) ? Log.d(tag, msg, tr) : -1;
    }

    public static int e(String tag, String msg) {
        return (isLoggable(tag, Log.ERROR) && LuaJavaAPI.isDebug) ? Log.e(tag, msg) : -1;
    }

    public static int e(String tag, String msg, Throwable tr) {
        return (isLoggable(tag, Log.ERROR) && LuaJavaAPI.isDebug) ? Log.e(tag, msg, tr) : -1;
    }

    public static int i(String tag, String msg) {
        return (isLoggable(tag, Log.INFO) && LuaJavaAPI.isDebug) ? Log.i(tag, msg) : -1;
    }

    public static int i(String tag, String msg, Throwable tr) {
        return (isLoggable(tag, Log.INFO) && LuaJavaAPI.isDebug) ? Log.i(tag, msg, tr) : -1;
    }

    private static boolean isLoggable(String tag, int level) {
        return Log.isLoggable(tag, level);
    }

    public static void trace(String tag, String msg) {
        if (LuaJavaAPI.isDebug) {
            System.out.println(tag + "::" + msg);
        }
    }

    public static int v(String tag, String msg) {
        return (isLoggable(tag, Log.VERBOSE) && LuaJavaAPI.isDebug) ? Log.v(tag, msg) : -1;
    }

    public static int v(String tag, String msg, Throwable tr) {
        return (isLoggable(tag, Log.VERBOSE) && LuaJavaAPI.isDebug) ? Log.v(tag, msg, tr) : -1;
    }

    public static int w(String tag, String msg) {
        return (isLoggable(tag, Log.WARN) && LuaJavaAPI.isDebug) ? Log.w(tag, msg) : -1;
    }

    public static int w(String tag, String msg, Throwable tr) {
        return (isLoggable(tag, Log.WARN) && LuaJavaAPI.isDebug) ? Log.w(tag, msg, tr) : -1;
    }
}
