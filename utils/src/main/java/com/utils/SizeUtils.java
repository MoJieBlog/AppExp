package com.utils;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-05-29
 */
public final class SizeUtils {

    /**
     * dp转px
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param pxValue
     * @return
     */
    public static int px2dp(float pxValue) {
        float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
