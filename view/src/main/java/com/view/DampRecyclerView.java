package com.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;

/**
 * @describe 弹性RecyclerView 目前只支持LinearLayoutManager,GridLayoutManager,如果想支持瀑布流，需要重写getLastPosition和getFirstItem
 * @author: lixiaopeng
 * @Date: 2019-11-26
 */
public class DampRecyclerView extends RecyclerView {
    public DampRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public DampRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    private float downX = 0;
    private float damp = 0.5f;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            downX = e.getX();
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = e.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float nowX = e.getX();
                float dx = nowX - downX;
                downX = nowX;

                if (dx > 0 && getFirstItem() == 0) {//在顶部
                    offsetLeftAndRight((int) (dx * damp));
                } else {
                    if (getAdapter() != null) {
                        if (getLastPosition() == getAdapter().getItemCount() - 1) {//在末尾
                            offsetLeftAndRight((int) (dx * damp));
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:

                doDamp();
                break;
        }
        return super.onTouchEvent(e);
    }

    private int getLastPosition() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager != null && layoutManager instanceof LinearLayoutManager) {
            int lastCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
            return lastCompletelyVisibleItemPosition;
        }
        return 0;
    }

    private int getFirstItem() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager != null && layoutManager instanceof LinearLayoutManager) {
            int firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
            return firstVisibleItemPosition;
        }
        return 0;
    }

    private int viewOffset = 0;

    private void doDamp() {
        viewOffset = getLeft();
        ValueAnimator animator = ValueAnimator.ofInt(viewOffset, 0);
        animator.setDuration(400);
        animator.setInterpolator(new DecelerateInterpolator(2));
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                int dx = animatedValue - viewOffset;
                viewOffset = animatedValue;
                offsetLeftAndRight(dx);
            }
        });
        animator.start();
    }
}
