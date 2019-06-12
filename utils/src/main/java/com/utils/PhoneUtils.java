package com.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.util.DisplayMetrics;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-05-29
 */
public final class PhoneUtils {

    /**
     * 获取屏幕宽，高度包括通知栏和虚拟按键
     * @return
     */
    public static int getWinWide(){
        DisplayMetrics dm =  Utils.getApp().getResources().getDisplayMetrics();
        int widthPixels = dm.widthPixels;
        int heightPixels = dm.heightPixels;

        return widthPixels;
    }


    /**
     * 获取屏幕高
     * @return
     */
    public static int getWinHeight(){
        DisplayMetrics dm =  Utils.getApp().getResources().getDisplayMetrics();
        int widthPixels = dm.widthPixels;
        int heightPixels = dm.heightPixels;
        return heightPixels;
    }

    /**
     * 获取可编辑区域的高度
     * @param activity
     * @return
     */
    public static int getDisHeight(Activity activity){
        /**
         * 获取状态栏高度——方法1
         * */
        int heightPixels = -1;
        //获取status_bar_height资源的ID
        Rect outRect1 = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        heightPixels = outRect1.height();
        return heightPixels;
    }

    public static int getStatusBarHeight(){
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = Utils.getApp().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = Utils.getApp().getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

}
