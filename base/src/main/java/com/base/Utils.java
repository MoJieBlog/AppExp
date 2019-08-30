package com.base;

import android.content.Context;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-12
 */
public class Utils {
    /**
     * dp转px
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context,float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
