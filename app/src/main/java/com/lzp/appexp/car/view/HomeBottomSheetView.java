package com.lzp.appexp.car.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-28
 */
public class HomeBottomSheetView extends FrameLayout implements NestedScrollingParent {

    private View topView;
    private View scrollView;
    private int topViewHeight;

    public HomeBottomSheetView(Context context) {
        super(context);
    }

    public HomeBottomSheetView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed,l,t,r,b);
        int childCount = getChildCount();
        if (childCount != 2) {
            throw new IllegalArgumentException("child count must be 2.");
        }
        topView = getChildAt(0);
        scrollView = getChildAt(1);
        topViewHeight = topView.getBottom();
        if (!ViewCompat.isNestedScrollingEnabled(scrollView)) {
            throw new IllegalArgumentException("child must be scrollable.");
        }
        ViewCompat.offsetTopAndBottom(scrollView, topView.getBottom());
    }


    @Override
    public int getNestedScrollAxes() {
        return super.getNestedScrollAxes();
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        //只观察竖直方向
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        super.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return super.onNestedFling(target, velocityX, velocityY, consumed);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        if (target==scrollView){
            int currentTop = target.getTop();
            int newTop = currentTop - dy;
            /*if (dy > 0) {
                if (newTop < this.getExpandedOffset()) {
                    consumed[1] = currentTop - this.getExpandedOffset();
                    ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                    this.setStateInternal(STATE_EXPANDED);
                } else {
                    consumed[1] = dy;
                    ViewCompat.offsetTopAndBottom(child, -dy);
                    this.setStateInternal(STATE_DRAGGING);
                }
            } else if (dy < 0 && !target.canScrollVertically(-1)) {
                if (newTop > this.topViewHeight && !this.hideable) {
                    consumed[1] = currentTop - this.topViewHeight;
                    ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                    this.setStateInternal(STATE_COLLAPSED);
                } else {
                    consumed[1] = dy;
                    ViewCompat.offsetTopAndBottom(child, -dy);
                    this.setStateInternal(1);
                }
            }

            this.dispatchOnSlide(child.getTop());
            this.lastNestedScrollDy = dy;
            this.nestedScrolled = true;*/
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public void onStopNestedScroll(View child) {
        super.onStopNestedScroll(child);
    }
}
