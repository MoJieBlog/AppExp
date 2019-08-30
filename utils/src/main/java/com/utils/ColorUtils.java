package com.utils;

import android.graphics.Color;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-08-30
 */
public class ColorUtils {
    /**
     * 判断一个颜色是否是浅色
     * @param color
     * @return
     */
    public static boolean isLightColor(int color){
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness<0.5;
    }
}
