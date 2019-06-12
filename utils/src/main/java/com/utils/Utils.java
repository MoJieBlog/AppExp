package com.utils;

import android.content.Context;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-05-22
 */
public final class Utils {

    private static Context context;
    private static boolean isDebug = false;

    /**
     * 权限请求时使用的请求码
     *
     * code when request permission
     */
    private static int CODE_PERMISSION_REQUEST = 1;


    public static void init(Context context, boolean isDebug) {
        Utils.context = context;
        Utils.isDebug = isDebug;
        LogUtils.init(isDebug);
    }

    public static Context getApp() {
        return context;
    }

    public static boolean isDebug() {
        return isDebug;
    }

    public static void setPermissionRequestCode(int code) {
        CODE_PERMISSION_REQUEST = code;
    }

    public static int getPermissionRequestCode(){
        return CODE_PERMISSION_REQUEST;
    }

}
