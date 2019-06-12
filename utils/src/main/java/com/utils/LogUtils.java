package com.utils;

import android.util.Log;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-05-28
 */
public final class LogUtils {

    public static boolean enable = false;

    public static void init(boolean debug) {
        enable = debug;
    }

    public static void e(String tag, String msg) {
        if (enable){
            Log.e(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (enable){
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (enable){
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (enable){
            Log.w(tag, msg);
        }
    }
}
