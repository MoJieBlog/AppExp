package com.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @describe 弹性布局
 * @author: lixiaopeng
 * @Date: 2019-11-26
 */
public class DampLayout extends FrameLayout {

    public DampLayout(@NonNull Context context) {
        this(context, null);
    }

    public DampLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

   /* private View getScrollbleChild(){
        int childCount = getChildCount();
        if (childCount==0){
            return null;
        }
    }*/
}
