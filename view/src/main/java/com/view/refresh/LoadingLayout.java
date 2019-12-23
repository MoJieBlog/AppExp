package com.view.refresh;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-14
 */
public abstract class LoadingLayout  extends FrameLayout implements ILoadingLayout {

    protected Context mContext;
    protected Resources mResources;

    public LoadingLayout(@NonNull Context context) {
        this(context, null);
    }

    public LoadingLayout(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mResources = context.getResources();
        init();
    }

    public LoadingLayout(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public LoadingLayout(@NonNull Context context, AttributeSet attrs, int defStyleAttr,
                         int defStyleRes) {
        this(context, attrs);
    }

    public abstract void init();

}
