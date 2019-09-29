package com.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * 水平滚动的LinearLayout
 */
public class HorizontalLinearLayout extends LinearLayout {

    private static final String TAG = "HorizontalLinearLayout";

    private int lastX;
    private Scroller scroller;
    private VelocityTracker velocityTracker;
    private int lastInterceptX = 0;
    private int lastInterceptY = 0;

    //子元素的宽度
    private int childWidth;
    //fling时的阻尼系数
    private float damp = 0.5f;

    public HorizontalLinearLayout(Context context) {
        this(context, null);
    }

    public HorizontalLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
        scroller = new Scroller(getContext());
        velocityTracker = VelocityTracker.obtain();
    }

    /**
     * 主要处理手指按下时，停止滚动，以及竖直滚动大于横向滚动时，不滚动
     *
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercept = false;
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                int dx = x - lastInterceptX;
                int dy = y - lastInterceptY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    intercept = true;
                } else {
                    intercept = false;
                }
                break;

            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }

        lastX = x;
        lastInterceptX = x;
        lastInterceptY = y;
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        velocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.abortAnimation();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                int dx = x - lastX;
                if (getScrollX() - dx <= 0) {
                    dx = 0;
                }
                Log.e(TAG, "getScrollX:" + getScrollX());
                Log.e(TAG, "dx:" + dx);
                scrollBy(-dx, 0);
                break;
            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(1000);
                float xVelocity = velocityTracker.getXVelocity() * damp;
                smoothScrollTo((int) (getScrollX() - xVelocity), 0);
                velocityTracker.clear();
                break;
        }
        lastX = x;
        return true;
    }

    @Override
    public void scrollBy(int x, int y) {

        super.scrollBy(x, y);
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            int scrollX = scroller.getCurrX();
            if (scrollX < 0) {
                scrollX = 0;
            }
            scrollTo(scrollX, scroller.getCurrY());
            postInvalidate();
        }
        super.computeScroll();
    }

    public void smoothScrollTo(int destX, int destY) {
        scroller.startScroll(getScrollX(), getScrollY(), destX - getScrollX(), destY - getScrollY(), 1000);
        invalidate();
    }
}
