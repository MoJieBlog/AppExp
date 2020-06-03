package com.lzp.customview.damp;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @describez 弹性recyclerView
 * @author: lixiaopeng
 * @Date: 2019-11-26
 */
public class DampRecyclerView extends RecyclerView {
    private static final String TAG = "DampRecyclerView";

    private float downX = 0;
    private float downY = 0;
    private float damp = .5f;
    private int orientation = RecyclerView.VERTICAL;
    ValueAnimator animator;

    private boolean needStartDamp = true;//是否需要弹性阻尼运动
    private boolean needEndDamp = true;//是否需要弹性阻尼运动

    private LayoutManager layoutManager;
    private int viewOffset = 0;

    public DampRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public DampRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (!needDamp()) {
            return super.onTouchEvent(e);
        }
        if (!ensureLayoutManager()) {
            Log.w(TAG,"layoutManager is null or item count is 0.");
            return super.onTouchEvent(e);
        }
        if (animator != null && animator.isRunning()) {
            return super.onTouchEvent(e);
        }
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = e.getX();
                downY = e.getY();
                viewOffset = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                if (orientation == LinearLayoutManager.HORIZONTAL) {
                    float nowX = e.getX();
                    float dx = nowX - downX;
                    downX = nowX;
                    if (dx > 0 && !canScrollHorizontally(-1) && needStartDamp) {//在顶部
                        int dampX = (int) (dx * damp);
                        viewOffset += dampX;
                        layoutManager.offsetChildrenHorizontal(dampX);
                        Log.d(TAG, "onTouchEvent: 在顶部" + viewOffset);
                    } else {
                        if (dx < 0 && !canScrollHorizontally(1) && needEndDamp) {//在末尾
                            int dampX = (int) (dx * damp);
                            viewOffset += dampX;
                            layoutManager.offsetChildrenHorizontal(dampX);
                            Log.d(TAG, "onTouchEvent: 在底部" + viewOffset);
                        }
                    }
                } else {
                    float nowY = e.getY();
                    float dy = nowY - downY;
                    downY = nowY;
                    if (dy > 0 && !canScrollVertically(-1) && needStartDamp) {//在顶部
                        int dampY = (int) (dy * damp);
                        viewOffset += dampY;
                        layoutManager.offsetChildrenVertical(dampY);
                        Log.d(TAG, "onTouchEvent: 在顶部" + viewOffset);
                    } else if (dy < 0 && !canScrollVertically(1) && needEndDamp) {
                        int dampY = (int) (dy * damp);
                        viewOffset += dampY;
                        layoutManager.offsetChildrenVertical(dampY);
                        Log.d(TAG, "onTouchEvent: 在底部" + viewOffset);
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (needDamp()) {
                    doDamp();
                }
                break;
        }
        return super.onTouchEvent(e);
    }

    private boolean needDamp() {
        if (orientation == RecyclerView.HORIZONTAL) {
            return (needStartDamp || needEndDamp) && (!canScrollHorizontally(1) || !canScrollHorizontally(-1));
        } else {
            return (needStartDamp || needEndDamp) && (!canScrollVertically(1) || !canScrollVertically(-1));
        }
    }

    /**
     * 检查LayoutManager是否支持
     *
     * @return
     */
    private boolean ensureLayoutManager() {
        layoutManager = getLayoutManager();
        if (layoutManager == null) {
            return false;
        }
        if (layoutManager.getItemCount() == 0) {
            return false;
        }
        if (layoutManager instanceof LinearLayoutManager) {
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        }
        return true;
    }

    private void doDamp() {
        if (animator != null && animator.isRunning()) {
            return;
        }
        final int viewOffset_ = viewOffset;
        animator = ValueAnimator.ofInt(viewOffset_, 0);
        animator.setDuration(200);
        animator.setInterpolator(new DecelerateInterpolator(2));
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatedValue = (int) animation.getAnimatedValue();
                int dv = animatedValue - viewOffset;
                viewOffset = animatedValue;
                if (orientation == LinearLayoutManager.HORIZONTAL) {
                    layoutManager.offsetChildrenHorizontal(dv);
                } else {
                    layoutManager.offsetChildrenVertical(dv);
                }
            }
        });
        animator.start();
    }

    public void setNeedStartDamp(boolean needStartDamp) {
        this.needStartDamp = needStartDamp;
    }

    public void setNeedEndDamp(boolean needEndDamp) {
        this.needEndDamp = needEndDamp;
    }

    public void setDamp(float damp) {
        this.damp = damp;
    }
}