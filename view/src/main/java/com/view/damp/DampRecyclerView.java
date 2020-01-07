package com.view.damp;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private float downY = 0;
    private float damp = 0.5f;
    private int orientation = RecyclerView.VERTICAL;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        RecyclerView.LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
        }
        if (action == MotionEvent.ACTION_DOWN) {
            downX = e.getX();
            downY = e.getY();
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
                if (orientation == LinearLayoutManager.HORIZONTAL) {
                    float nowX = e.getX();
                    float dx = nowX - downX;
                    downX = nowX;
                    if (dx > 0 && getFirstItem() == 0) {//在顶部
                        offsetLeftAndRight((int) (dx * damp));
                    } else {
                        if (getAdapter() != null) {
                            if (dx < 0 && getLastPosition() == getAdapter().getItemCount() - 1) {//在末尾
                                offsetLeftAndRight((int) (dx * damp));
                            }
                        }
                    }
                } else {
                    float nowY = e.getY();
                    float dy = nowY - downY;
                    downY = nowY;
                    if (dy > 0 && getFirstItem() == 0) {//在顶部
                        offsetTopAndBottom((int) (dy * damp));
                    } else {
                        if (getAdapter() != null) {
                            if (dy < 0 && getLastPosition() == getAdapter().getItemCount() - 1) {//在末尾
                                offsetTopAndBottom((int) (dy * damp));
                            }
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
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            viewOffset = getLeft();
        } else {
            viewOffset = getTop();
        }

        final int viewOffset_ = viewOffset;
        ValueAnimator animator = ValueAnimator.ofInt(viewOffset_, 0);
        animator.setDuration(400);
        animator.setInterpolator(new DecelerateInterpolator(2));
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                int dv = animatedValue - viewOffset;
                viewOffset = animatedValue;
                if (orientation == LinearLayoutManager.HORIZONTAL) {
                    offsetLeftAndRight(dv);
                } else {
                    offsetTopAndBottom(dv);
                }
            }
        });
        animator.start();
    }
}
