package com.utils;

import android.content.Context;

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

    public static int dip2px(Context context, int dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static float dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
    }

    /**
     * px转dp
     *
     * @param pxValue
     * @return
     */
    public static float px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return pxValue / scale + 0.5f;
    }

    /**
     * 将px转换为sp
     */
    public static float px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return pxValue / fontScale + 0.5f;
    }


    /**
     * 将sp转换为px
     */
    public static float sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale + 0.5f;
    }
}
