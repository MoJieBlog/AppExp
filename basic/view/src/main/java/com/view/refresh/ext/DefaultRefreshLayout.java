package com.view.refresh.ext;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import androidx.annotation.NonNull;

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
        lp.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

        addView(loadingLayout, lp);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(animViewHeight, MeasureSpec.EXACTLY));
    }

    @Override
    public void onMove(float moveOffset, boolean isRefreshing) {
        if (loadingLayout != null) {
            float progress = moveOffset / animViewHeight;
            if (progress > 1) {
                progress = 1;
            }
            loadingLayout.startAnimation((int) (moveOffset * 100 / animViewHeight));

            Matrix matrix = loadingLayout.getMatrix();
            matrix.postScale(progress,progress,animViewWidth/2,animViewHeight/2);
        }
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
