package com.lzp.customview.damp;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * @describe 弹性ViewPager
 * @author: lixiaopeng
 * @Date: 2019-12-16
 */
public class DampViewPager  extends ViewPager {
    private static final String TAG = "DampViewPager";

    private float downX = 0;
    private float downY = 0;
    private float damp = 0.3f;
    private int viewOffset = 0;
    ValueAnimator animator;

    private int originalX;

    public DampViewPager(@NonNull Context context) {
        this(context, null);
    }

    public DampViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(OVER_SCROLL_NEVER);
        post(new Runnable() {
            @Override
            public void run() {
                originalX = getLeft();
            }
        });
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int action = e.getAction();
        if (animator != null && animator.isRunning()) {
            animator.cancel();
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
                downY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float nowX = e.getX();
                float dx = nowX - downX;
                downX = nowX;
                if (dx > 0 && getCurrentItem() == 0) {//在顶部
                    offsetLeftAndRight((int) (dx * damp));
                } else {
                    if (getAdapter() != null) {
                        if (dx < 0 && getCurrentItem() == getAdapter().getCount() - 1) {//在末尾
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


    private void doDamp() {
        Log.e(TAG, "doDamp: ");
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            return;
        }
        viewOffset = getLeft();

        final int viewOffset_ = viewOffset;
        animator = ValueAnimator.ofInt(viewOffset_, originalX);
        animator.setDuration(400);
        animator.setInterpolator(new DecelerateInterpolator(2));
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                int dv = animatedValue - viewOffset;
                viewOffset = animatedValue;
                offsetLeftAndRight(dv);
            }
        });
        animator.start();
    }
}
