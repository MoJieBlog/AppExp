package com.lzp.appexp.car.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-25
 */
public class GalleryTransformer implements ViewPager.PageTransformer {

    private Context context;

    private static final float ALPHA = 1.0f;
    private static final float SCALE = 0.8f;

    public GalleryTransformer(Context context) {
        this.context = context;
    }

    @Override
    public void transformPage(View page, float position) {
        if (position < -1 || position > 1) {
            page.setScaleX(SCALE);
            page.setScaleY(SCALE);
            page.setAlpha(ALPHA);
        }else{
            float deltScroll = 1 - Math.abs(position);
            page.setAlpha(ALPHA+ALPHA*deltScroll);
            float scale=Math.max(SCALE,deltScroll);
            page.setScaleX(scale);
            page.setScaleY(scale);
        }

    }
}
