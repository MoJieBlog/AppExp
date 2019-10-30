package com.view.refresh.ext;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;

import com.view.refresh.LoadingLayout;
import com.view.refresh.SwipeRefreshLayout;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-14
 */
public class DefaultRefreshLayout extends LoadingLayout {

    private GoLoadingLayout loadingLayout;

    private int animViewWidth, animViewHeight;

    public DefaultRefreshLayout(@NonNull Context context) {
        this(context, null);
    }

    public DefaultRefreshLayout(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {
        loadingLayout = new GoLoadingLayout(getContext());
        loadingLayout.setVisibility(VISIBLE);
        animViewHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60,
                mResources.getDisplayMetrics());

        animViewWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100,
                mResources.getDisplayMetrics());
        LayoutParams lp = new LayoutParams(animViewWidth, animViewHeight);
        lp.gravity = Gravity.CENTER;

        addView(loadingLayout, lp);
    }

    @Override
    public void onMove(float moveOffset, boolean isRefreshing) {
        if (loadingLayout != null)
            loadingLayout.startAnimation((int) (moveOffset * 100 / animViewHeight));
    }

    @Override
    public void onRefreshing() {
        if (loadingLayout != null)
            loadingLayout.repeatAnimation();
    }

    @Override
    public void onRefreshFinish() {
        if (loadingLayout != null)
            loadingLayout.stopAnimation();
    }

    @Override
    public void setTargetViewHeight(int height) {

    }

    @Override
    public void setRefreshLayoutInstance(SwipeRefreshLayout refreshLayout) {

    }

    @Override
    public int getLoadingOffsetHeight() {
        return animViewHeight;
    }
}
