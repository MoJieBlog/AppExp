package com.lzp.appexp;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.v4.math.MathUtils;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;

import java.lang.ref.WeakReference;

/**
 * @describe 注意必须只有两个ChildView，否则崩溃
 * @author: lixiaopeng
 * @Date: 2019-10-21
 */
public class MyBehavior<V extends View> extends Behavior<V> {

    private static final String TAG = "MyBehavior";
    /*******拖拽******/
    public static final int STATE_DRAGGING = 1;
    /*******滚动到某个状态******/
    public static final int STATE_SETTLING = 2;
    /*******打开******/
    public static final int STATE_EXPANDED = 3;
    /*******关闭******/
    public static final int STATE_COLLAPSED = 4;


    private int state = STATE_EXPANDED;

    //允许执行fling的最大速度
    private float maximumVelocity;
    //最小滚动距离
    private int scaledTouchSlop;

    private int downY;

    private int activePointerId;

    WeakReference<V> scrollView;
    WeakReference<View> topView;
    private int topViewHeight;

    private MyBehavior.BottomSheetCallback bottomSheetCallback;
    private VelocityTracker velocityTracker;
    private ViewDragHelper viewDragHelper;
    private final Callback dragCallback;
    boolean touchingScrollingChild;
    private boolean ignoreEvents;
    private int lastNestedScrollDy;
    private boolean nestedScrolled;//是否嵌套滚动

    public MyBehavior() {
        this.dragCallback = new MyDragCallBack();
    }

    public MyBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.dragCallback = new MyDragCallBack();

        ViewConfiguration configuration = ViewConfiguration.get(context);
        scaledTouchSlop = configuration.getScaledTouchSlop();
        this.maximumVelocity = (float) configuration.getScaledMaximumFlingVelocity();
    }

    class MyDragCallBack extends Callback {
        MyDragCallBack() {
        }

        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            if (MyBehavior.this.state == STATE_DRAGGING) {
                return false;
            } else if (MyBehavior.this.touchingScrollingChild) {
                return false;
            }else {
                if (MyBehavior.this.state == STATE_EXPANDED && MyBehavior.this.activePointerId == pointerId) {
                    View scroll = scrollView.get();
                    if (scroll != null && scroll.canScrollVertically(-1)) {
                        return false;
                    }
                }

                return scrollView.get() != null && scrollView.get() == child;
            }
        }

        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            dispatchOnSlide(top);
        }

        public void onViewDragStateChanged(int state) {
            if (state==STATE_DRAGGING){
                setStateInternal(STATE_DRAGGING);
            }
        }

        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            int top;
            byte targetState;
            int currentTop = releasedChild.getTop();
            if (currentTop>topViewHeight*2/3){
                top = topViewHeight;
                targetState = STATE_EXPANDED;
            }else{
                top = 0;
                targetState = STATE_COLLAPSED;
            }

            if (MyBehavior.this.viewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top)) {
                MyBehavior.this.setStateInternal(targetState);
                ViewCompat.postOnAnimation(releasedChild, MyBehavior.this.new SettleRunnable(releasedChild, targetState));
            } else {
                MyBehavior.this.setStateInternal(targetState);
            }
        }

        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            Log.e(TAG, "clampViewPositionVertical: ");
            return MathUtils.clamp(top, 0, topViewHeight);
        }

        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return child.getLeft();
        }

        /**
         * 控制边界
         *
         * @param child
         * @return
         */
        public int getViewVerticalDragRange(@NonNull View child) {
            return state!=STATE_EXPANDED?topViewHeight:0;
        }
    }

    @Override
    public Parcelable onSaveInstanceState(CoordinatorLayout parent, V child) {
        return new MyBehavior.SavedState(super.onSaveInstanceState(parent, child), this.state);
    }

    @Override
    public void onRestoreInstanceState(CoordinatorLayout parent, V child, Parcelable state) {
        MyBehavior.SavedState ss = (MyBehavior.SavedState) state;
        super.onRestoreInstanceState(parent, child, ss.getSuperState());
        if (ss.state != 1 && ss.state != 2) {
            this.state = ss.state;
        } else {
            this.state = STATE_COLLAPSED;
        }

    }

    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        int childCount = parent.getChildCount();
        if (childCount != 2) {
            throw new IllegalArgumentException("child count must be 2.");
        }
        if (!ViewCompat.isNestedScrollingEnabled(child)) {
            throw new IllegalArgumentException("child must be scrollable.");
        }
        parent.onLayoutChild(child, layoutDirection);
        View topView = parent.getChildAt(0);
        topViewHeight = topView.getBottom();
        this.topView = new WeakReference(topView);
        this.scrollView = new WeakReference<>(child);
        ViewCompat.offsetTopAndBottom(child, topView.getBottom());

        if (this.viewDragHelper == null) {
            this.viewDragHelper = ViewDragHelper.create(parent, this.dragCallback);
        }

        return true;
    }


    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        if (!child.isShown()) {
            this.ignoreEvents = true;
            return false;
        } else {
            int action = event.getActionMasked();
            if (action == 0) {
                this.reset();
            }

            if (this.velocityTracker == null) {
                this.velocityTracker = VelocityTracker.obtain();
            }

            this.velocityTracker.addMovement(event);
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    int initialX = (int) event.getX();
                    this.downY = (int) event.getY();
                    View scroll = this.scrollView != null ? this.scrollView.get() : null;
                    //如果按的是可滚动区域
                    if (scroll != null && parent.isPointInChildBounds(scroll, initialX, this.downY)) {
                        this.activePointerId = event.getPointerId(event.getActionIndex());
                        this.touchingScrollingChild = true;
                    }
                    this.ignoreEvents = this.activePointerId == -1 && !parent.isPointInChildBounds(child, initialX, this.downY);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    this.touchingScrollingChild = false;
                    this.activePointerId = -1;
                    if (this.ignoreEvents) {
                        this.ignoreEvents = false;
                        return false;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
            }

            if (!this.ignoreEvents&&this.viewDragHelper != null && this.viewDragHelper.shouldInterceptTouchEvent(event)) {
                return true;
            } else {
                View scroll = this.scrollView != null ? this.scrollView.get() : null;
                return action == MotionEvent.ACTION_MOVE
                        && scroll != null
                        && this.state != STATE_DRAGGING
                        && !this.ignoreEvents
                        && !parent.isPointInChildBounds(scroll, (int) event.getX(), (int) event.getY())
                        && this.viewDragHelper != null
                        && Math.abs((float) this.downY - event.getY()) > (float) this.viewDragHelper.getTouchSlop();
            }
        }
    }

    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        if (!child.isShown()) {
            return false;
        } else {
            int action = event.getActionMasked();
            if (this.state == STATE_DRAGGING && action == MotionEvent.ACTION_DOWN) {
                return true;
            } else {
                if (this.viewDragHelper != null) {
                    this.viewDragHelper.processTouchEvent(event);
                }

                if (action == 0) {
                    this.reset();
                }

                if (this.velocityTracker == null) {
                    this.velocityTracker = VelocityTracker.obtain();
                }

                this.velocityTracker.addMovement(event);

                if (action == MotionEvent.ACTION_MOVE && !this.ignoreEvents && Math.abs((float)this.downY - event.getY()) > (float)this.viewDragHelper.getTouchSlop()) {
                    this.viewDragHelper.captureChildView(child, event.getPointerId(event.getActionIndex()));
                }
                return !this.ignoreEvents;
            }
        }
    }

    void setStateInternal(int state) {
        if (this.state != state) {
            this.state = state;
            View bottomSheet = (View) this.scrollView.get();
            if (bottomSheet != null && this.bottomSheetCallback != null) {
                this.bottomSheetCallback.onStateChanged(bottomSheet, state);
            }

        }
    }


    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        this.lastNestedScrollDy = 0;
        this.nestedScrolled = false;
        return (axes & 2) != 0;
    }

    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (type != 1) {
            View scrollingChild = this.scrollView.get();
            if (target == scrollingChild) {
                int currentTop = child.getTop();
                int newTop = currentTop - dy;
                Log.e(TAG, "newTop: " + newTop);
                if (dy > 0) {//向上滚动
                    if (newTop >= 0) {
                        if (newTop >= topViewHeight) {
                            consumed[1] = currentTop - topViewHeight;
                            ViewCompat.offsetTopAndBottom(child, -consumed[1]);
                            this.setStateInternal(STATE_EXPANDED);
                        } else {
                            consumed[1] = dy;
                            ViewCompat.offsetTopAndBottom(child, -dy);
                            this.setStateInternal(STATE_DRAGGING);
                        }
                    }

                } else if (dy < 0 && !target.canScrollVertically(-1)) {//向下
                    if (newTop <= this.topViewHeight) {
                        consumed[1] = dy;
                        ViewCompat.offsetTopAndBottom(child, -dy);
                        this.setStateInternal(1);
                    }
                    //TODO 超过原始高度，打开新页面
                    if (newTop > this.topViewHeight) {

                    }
                }

                this.dispatchOnSlide(child.getTop());
                this.lastNestedScrollDy = dy;
                this.nestedScrolled = true;
            }
        }
    }

    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int type) {
        if (child.getTop() == this.topViewHeight) {
            this.setStateInternal(STATE_EXPANDED);
        } else if (target == this.scrollView.get() && this.nestedScrolled) {
            int top;
            byte targetState;

            int viewTop = child.getTop();

            if (viewTop > topViewHeight *2/3) {
                top = topViewHeight;
                targetState = STATE_EXPANDED;
            } else {
                top = 0;
                targetState = STATE_COLLAPSED;
            }
            if (this.viewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
                this.setStateInternal(STATE_SETTLING);
                ViewCompat.postOnAnimation(child, new MyBehavior.SettleRunnable(child, targetState));
            } else {
                this.setStateInternal(targetState);
            }

            this.nestedScrolled = false;
        }
    }

    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, float velocityX, float velocityY) {
        return target == this.scrollView.get() && (this.state != STATE_EXPANDED || super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY));
    }

    public void setBottomSheetCallback(MyBehavior.BottomSheetCallback callback) {
        this.bottomSheetCallback = callback;
    }


    public final int getState() {
        return this.state;
    }

    private void reset() {
        if (this.velocityTracker != null) {
            this.velocityTracker.recycle();
            this.velocityTracker = null;
        }

    }


    private float getYVelocity() {
        if (this.velocityTracker == null) {
            return 0.0F;
        } else {
            this.velocityTracker.computeCurrentVelocity(1000, this.maximumVelocity);
            return this.velocityTracker.getYVelocity(this.activePointerId);
        }
    }

    void dispatchOnSlide(int top) {
        View bottomSheet = (View) this.scrollView.get();
        if (bottomSheet != null && this.bottomSheetCallback != null) {
            if (top > this.topViewHeight) {
                //this.bottomSheetCallback.onSlide(bottomSheet, (float) (this.topViewHeight - top) / (float) (this.parentHeight - this.collapsedOffset));
            } else {
                this.bottomSheetCallback.onSlide(bottomSheet, (float) (this.topViewHeight - top) / (float) (this.topViewHeight));
            }
        }
    }


    public static <V extends View> MyBehavior<V> from(V view) {
        LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        } else {
            Behavior behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
            if (!(behavior instanceof MyBehavior)) {
                throw new IllegalArgumentException("The view is not associated with HomeBottomSheetBehavior");
            } else {
                return (MyBehavior) behavior;
            }
        }
    }

    protected static class SavedState extends AbsSavedState {
        final int state;
        public static final Creator<MyBehavior.SavedState> CREATOR = new ClassLoaderCreator<MyBehavior.SavedState>() {
            public MyBehavior.SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new MyBehavior.SavedState(in, loader);
            }

            public MyBehavior.SavedState createFromParcel(Parcel in) {
                return new MyBehavior.SavedState(in, (ClassLoader) null);
            }

            public MyBehavior.SavedState[] newArray(int size) {
                return new MyBehavior.SavedState[size];
            }
        };

        public SavedState(Parcel source) {
            this(source, (ClassLoader) null);
        }

        public SavedState(Parcel source, ClassLoader loader) {
            super(source, loader);
            this.state = source.readInt();
        }

        public SavedState(Parcelable superState, int state) {
            super(superState);
            this.state = state;
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.state);
        }
    }

    private class SettleRunnable implements Runnable {
        private final View view;
        private final int targetState;

        SettleRunnable(View view, int targetState) {
            this.view = view;
            this.targetState = targetState;
        }

        public void run() {
            if (MyBehavior.this.viewDragHelper != null && MyBehavior.this.viewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.view, this);
            } else {
                MyBehavior.this.setStateInternal(this.targetState);
            }

        }
    }

    public abstract static class BottomSheetCallback {
        public BottomSheetCallback() {
        }

        public abstract void onStateChanged(@NonNull View var1, int var2);

        public abstract void onSlide(@NonNull View var1, float var2);
    }
}
