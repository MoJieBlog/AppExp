package com.lzp.customview.damp;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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
    //原始顶部和尾部偏移量
    private int originalStartOffset = 0;
    private int originalEndOffset = 0;


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
    public boolean onInterceptTouchEvent(MotionEvent e) {
        if (!needDamp()) {
            return super.onInterceptTouchEvent(e);
        }
        if (animator != null && animator.isRunning()) {
            return super.onInterceptTouchEvent(e);
        }
        if (!ensureLayoutManager()) {
            return super.onInterceptTouchEvent(e);
        }
        int action = e.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            downX = e.getX();
            downY = e.getY();
        }
        //计算子View原始偏移量
        originalStartOffset = getStartOffset();
        originalEndOffset = getEndOffset();
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (animator != null && animator.isRunning()) {
            return super.onTouchEvent(e);
        }
        if (layoutManager == null) {
            return super.onTouchEvent(e);
        }
        if (layoutManager.getItemCount() == 0) {
            return super.onTouchEvent(e);
        }
        int action = e.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "onTouchEvent: ACTION_DOWN" );
                downX = e.getX();
                downY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!needStartDamp && !needEndDamp) {
                    return super.onTouchEvent(e);
                }
                if (orientation == LinearLayoutManager.HORIZONTAL) {
                    float nowX = e.getX();
                    float dx = nowX - downX;
                    downX = nowX;
                    if (dx > 0 && !canScrollHorizontally(-1) && needStartDamp) {//在顶部
                        int dampX = (int) (dx * damp);
                        layoutManager.offsetChildrenHorizontal(dampX);
                        Log.d(TAG, "onTouchEvent: 在顶部" + getStartOffset());
                    } else {
                        if (dx < 0 && !canScrollHorizontally(1) && needEndDamp) {//在末尾
                            int dampX = (int) (dx * damp);
                            layoutManager.offsetChildrenHorizontal(dampX);
                            Log.d(TAG, "onTouchEvent: 在底部" + getEndOffset());
                        }
                    }
                } else {
                    float nowY = e.getY();
                    float dy = nowY - downY;
                    downY = nowY;
                    int dampY = (int) (dy * damp);
                    if (dy > 0 && !canScrollVertically(-1) && needStartDamp) {//在顶部
                        layoutManager.offsetChildrenVertical(dampY);
                        Log.d(TAG, "onTouchEvent: 在顶部");
                    } else if (dy < 0 && !canScrollVertically(1) && needEndDamp) {
                        layoutManager.offsetChildrenVertical(dampY);
                        Log.d(TAG, "onTouchEvent: 在底部");
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
        int toPosition = 0;
        if (orientation == RecyclerView.VERTICAL) {
            if (!canScrollVertically(-1)) {
                //在顶部|在左侧
                viewOffset = getStartOffset();
                toPosition = originalStartOffset;
                Log.d(TAG, "doDamp: start viewOffset = " + viewOffset + "  toPosition = " + toPosition);
            } else if (!canScrollVertically(1)) {
                //在底部|在右侧
                viewOffset = getEndOffset();
                toPosition = originalEndOffset;
                Log.d(TAG, "doDamp: end viewOffset = " + viewOffset + "  toPosition = " + toPosition);
            }
        } else {
            if (!canScrollHorizontally(-1)) {
                //在顶部|在左侧
                viewOffset = getStartOffset();
                toPosition = originalStartOffset;
                Log.d(TAG, "doDamp: start viewOffset = " + viewOffset + "  toPosition = " + toPosition);
            } else if (!canScrollHorizontally(1)) {
                //在底部|在右侧
                viewOffset = getEndOffset();
                toPosition = originalEndOffset;
                Log.d(TAG, "doDamp: end viewOffset = " + viewOffset + "  toPosition = " + toPosition);
            }
        }

        final int viewOffset_ = viewOffset;
        animator = ValueAnimator.ofInt(viewOffset_, toPosition);
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

    private int getEndOffset() {
        if (layoutManager.getItemCount() == 0) {
            return 0;
        }
        View childAt = layoutManager.getChildAt(getChildCount() - 1);
        if (childAt == null) {
            return 0;
        }
        if (orientation == RecyclerView.HORIZONTAL) {
            return childAt.getLeft();
        } else {
            return childAt.getTop();
        }
    }

    private int getStartOffset() {
        if (layoutManager.getItemCount() == 0) {
            return 0;
        }
        View childAt = layoutManager.getChildAt(0);
        if (childAt == null) {
            return 0;
        }
        if (orientation == RecyclerView.HORIZONTAL) {
            return childAt.getLeft();
        } else {
            return childAt.getTop();
        }
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