package com.base.compat.view;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.utils.PhoneUtils;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-08-30
 */
public class StatusBarView  extends View {

    private int statusBarHeight;

    public StatusBarView(@NonNull Context context) {
        this(context,null);
    }

    public StatusBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        statusBarHeight = PhoneUtils.getStatusBarHeight(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        if (mode==MeasureSpec.AT_MOST||mode==MeasureSpec.UNSPECIFIED){
            int height = 0;
            if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
                height = MeasureSpec.makeMeasureSpec(statusBarHeight, MeasureSpec.EXACTLY);
            }
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), height);
        }else{
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }
}
