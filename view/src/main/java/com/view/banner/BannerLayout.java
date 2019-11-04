package com.view.banner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-11-04
 */
public class BannerLayout extends FrameLayout {
    public BannerLayout(@NonNull Context context) {
        this(context,null);
    }

    public BannerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}
