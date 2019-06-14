package com.view.refresh;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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
        createViewPlaceHolder();
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

    public abstract int getViewWidth();

    public abstract int getViewHeight();

    // 加载更多完成并显示对应文案
    public void onLoadMoreText(String text) {
    }

    public boolean isLoadMoreFailure(){
        return false;
    }

    private void createViewPlaceHolder() {
        View viewPlaceHolder = new View(mContext);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(viewPlaceHolder, lp);
    }
}
