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
 * @describez 弹性recyclerView 暂时只支持系统的三个LayoutManager(LinearLayoutManager,GridLayoutManager,StaggeredGridLayoutManager)，不支持自定义LayoutManager
 * @author: lixiaopeng
 * @Date: 2019-11-26
 */
public class DampRecyclerView extends RecyclerView {
    private static final String TAG = "DampRecyclerView";

    private float downX = 0;
    private float downY = 0;
    private float damp = 0.3f;
    private int orientation = RecyclerView.VERTICAL;
    ValueAnimator animator;

    private boolean needStartDamp = true;//是否需要弹性阻尼运动
    private boolean needEndDamp = true;//是否需要弹性阻尼运动

    private int originalX = -1;
    private int originalY = -1;

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
        if (!needStartDamp && !needEndDamp) {
            return super.onInterceptTouchEvent(e);
        }
        int action = e.getAction();
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        if (!ensureLayoutManager()){
            return super.onInterceptTouchEvent(e);
        }
        if (action == MotionEvent.ACTION_DOWN) {
            downX = e.getX();
            downY = e.getY();
        }
        return super.onInterceptTouchEvent(e);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (!ensureLayoutManager()){
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
                if (!needStartDamp && !needEndDamp) {
                    return super.onTouchEvent(e);
                }
                if (orientation == LinearLayoutManager.HORIZONTAL) {
                    float nowX = e.getX();
                    float dx = nowX - downX;
                    downX = nowX;
                    if (dx > 0 && isStart() && needStartDamp) {//在顶部
                        int dampX = (int) (dx * damp);
                        viewOffset += dampX;
                        layoutManager.offsetChildrenHorizontal(dampX);
                        Log.d(TAG, "onTouchEvent: 在顶部"+viewOffset);
                    } else {
                        if (getAdapter() != null) {
                            if (dx < 0 && isEnd() && needEndDamp) {//在末尾
                                int dampX = (int) (dx * damp);
                                viewOffset += dampX;
                                layoutManager.offsetChildrenHorizontal(dampX);
                                Log.d(TAG, "onTouchEvent: 在底部"+viewOffset);
                            }
                        }
                    }
                } else {
                    float nowY = e.getY();
                    float dy = nowY - downY;
                    downY = nowY;
                    if (dy > 0 && isStart() && needStartDamp) {//在顶部
                        int dampY = (int) (dy * damp);
                        viewOffset += dampY;
                        layoutManager.offsetChildrenVertical(dampY);
                        Log.d(TAG, "onTouchEvent: 在顶部"+viewOffset);
                    } else {
                        if (getAdapter() != null) {
                            if ((dy < 0 && isEnd() && needEndDamp)) {//在末尾
                                int dampY = (int) (dy * damp);
                                viewOffset += dampY;
                                layoutManager.offsetChildrenVertical(dampY);
                                Log.d(TAG, "onTouchEvent: 在底部"+viewOffset);
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (needStartDamp || needEndDamp) {
                    doDamp();
                }
                break;
        }
        return super.onTouchEvent(e);
    }

    /**
     * 检查LayoutManager是否支持
     * @return
     */
    private boolean ensureLayoutManager(){
        layoutManager = getLayoutManager();
        if (layoutManager == null) {
            return false;
        }
        if (layoutManager.getItemCount() == 0) {
            return false;
        }
        //计算子View原始偏移量
        if ((originalX == -1 || originalY == -1)) {
            View childAt = layoutManager.getChildAt(0);
            if (childAt != null) {
                originalX = childAt.getLeft();
                originalY = childAt.getTop();
            }
        }
        if (layoutManager instanceof LinearLayoutManager) {
            //GridLayoutManager instanceof LinearLayoutManager
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
        }else if(layoutManager instanceof StaggeredGridLayoutManager){
            orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        }
        return true;
    }

    /**
     * 是否到底部/右侧
     *
     * @return
     */
    private boolean isEnd() {
        if (layoutManager != null && layoutManager.getChildCount() > 0) {
            View childAt = layoutManager.getChildAt(layoutManager.getChildCount() - 1);
            if (childAt == null) {
                return false;
            }
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                return childAt.getRight() <= getWidth();
            }

            if (orientation == LinearLayoutManager.VERTICAL) {
                return childAt.getBottom() <= getHeight();
            }
        }
        return false;
    }

    /**
     * 是否在顶部/左侧
     * @return
     */
    private boolean isStart() {
        if (layoutManager != null && layoutManager.getItemCount() > 0) {
            View childAt = layoutManager.getChildAt(0);
            if (childAt == null) {
                return false;
            }
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                return childAt.getLeft() >= 0;
            }

            if (orientation == LinearLayoutManager.VERTICAL) {
                return childAt.getTop() >= 0;
            }
        }
        return false;
    }


    private void doDamp() {
        Log.d(TAG, "doDamp: ");
        if (!ensureLayoutManager()) {
            return;
        }
        if (!needStartDamp && !needEndDamp) {
            return;
        }
        if (animator != null && animator.isRunning()) {
            animator.cancel();
            return;
        }
        int toPosition;
        if (orientation == LinearLayoutManager.HORIZONTAL) {
            toPosition = originalX;
        } else {
            toPosition = originalY;
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

    public void setNeedStartDamp(boolean needStartDamp) {
        this.needStartDamp = needStartDamp;
    }

    public void setNeedEndDamp(boolean needEndDamp) {
        this.needEndDamp = needEndDamp;
    }
}
