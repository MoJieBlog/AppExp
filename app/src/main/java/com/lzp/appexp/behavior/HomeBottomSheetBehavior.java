package com.lzp.appexp.behavior;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.v4.math.MathUtils;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v4.widget.ViewDragHelper.Callback;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * copy from {@link BottomSheetBehavior}
 * <p>
 * 对系统的bottomSheetBehavior进行改造
 * 注意必须只有两个ChildView，否则崩溃,child(0)为顶部View,child(1)必须为实现Nest的控件
 *
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-21
 */
public class HomeBottomSheetBehavior<V extends View> extends Behavior<V> {
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_SETTLING = 2;
    public static final int STATE_EXPANDED = 3;
    public static final int STATE_COLLAPSED = 4;
    public static final int STATE_HIDDEN = 5;
    public static final int STATE_HALF_EXPANDED = 6;
    private boolean fitToContents = true;
    private float maximumVelocity;
    int fitToContentsOffset;
    int halfExpandedOffset;
    boolean hideable = true;
    int state = STATE_COLLAPSED;
    ViewDragHelper viewDragHelper;
    private boolean ignoreEvents;
    private int lastNestedScrollDy;
    private boolean nestedScrolled;
    int parentHeight;


    private HomeBottomSheetBehavior.BottomSheetCallback callback;
    private VelocityTracker velocityTracker;
    int activePointerId;
    private int initialY;
    boolean touchingScrollingChild;
    private Map<View, Integer> importantForAccessibilityMap;
    private final Callback dragCallback;


    private WeakReference<V> topView;
    private WeakReference<View> scrollView;
    private int topViewHeight;

    public HomeBottomSheetBehavior() {
        this.dragCallback = new NamelessClass_1();
    }

    public HomeBottomSheetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.dragCallback = new NamelessClass_1();
        ViewConfiguration configuration = ViewConfiguration.get(context);
        this.maximumVelocity = (float) configuration.getScaledMaximumFlingVelocity();
    }

    class NamelessClass_1 extends Callback {
        NamelessClass_1() {
        }

        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            if (HomeBottomSheetBehavior.this.state == STATE_DRAGGING) {
                return false;
            } else if (HomeBottomSheetBehavior.this.touchingScrollingChild) {
                return false;
            } else {
                if (HomeBottomSheetBehavior.this.state == STATE_EXPANDED && HomeBottomSheetBehavior.this.activePointerId == pointerId) {
                    View scroll = (View) HomeBottomSheetBehavior.this.scrollView.get();
                    if (scroll != null && scroll.canScrollVertically(-1)) {
                        return false;
                    }
                }

                return HomeBottomSheetBehavior.this.scrollView != null && HomeBottomSheetBehavior.this.scrollView.get() == child;
            }
        }

        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            HomeBottomSheetBehavior.this.dispatchOnSlide(top);
        }

        public void onViewDragStateChanged(int state) {
            if (state == STATE_DRAGGING) {
                HomeBottomSheetBehavior.this.setStateInternal(STATE_DRAGGING);
            }

        }

        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            int top;
            byte targetState;
            int currentTop;
            if (yvel < 0.0F) {
                if (HomeBottomSheetBehavior.this.fitToContents) {
                    top = HomeBottomSheetBehavior.this.fitToContentsOffset;
                    targetState = STATE_EXPANDED;
                } else {
                    currentTop = releasedChild.getTop();
                    if (currentTop > HomeBottomSheetBehavior.this.halfExpandedOffset) {
                        top = HomeBottomSheetBehavior.this.halfExpandedOffset;
                        targetState = STATE_HALF_EXPANDED;
                    } else {
                        top = 0;
                        targetState = STATE_EXPANDED;
                    }
                }
            } else if (releasedChild.getTop() <= HomeBottomSheetBehavior.this.topViewHeight && Math.abs(xvel) >= Math.abs(yvel)) {
                if (yvel != 0.0F && Math.abs(xvel) <= Math.abs(yvel)) {
                    top = HomeBottomSheetBehavior.this.topViewHeight;
                    targetState = STATE_COLLAPSED;
                } else {
                    currentTop = releasedChild.getTop();
                    if (HomeBottomSheetBehavior.this.fitToContents) {
                        if (Math.abs(currentTop - HomeBottomSheetBehavior.this.fitToContentsOffset) < Math.abs(currentTop - HomeBottomSheetBehavior.this.topViewHeight)) {
                            top = HomeBottomSheetBehavior.this.fitToContentsOffset;
                            targetState = STATE_EXPANDED;
                        } else {
                            top = HomeBottomSheetBehavior.this.topViewHeight;
                            targetState = STATE_COLLAPSED;
                        }
                    } else if (currentTop < HomeBottomSheetBehavior.this.halfExpandedOffset) {
                        if (currentTop < Math.abs(currentTop - HomeBottomSheetBehavior.this.topViewHeight)) {
                            top = 0;
                            targetState = STATE_EXPANDED;
                        } else {
                            top = HomeBottomSheetBehavior.this.halfExpandedOffset;
                            targetState = STATE_HALF_EXPANDED;
                        }
                    } else if (Math.abs(currentTop - HomeBottomSheetBehavior.this.halfExpandedOffset) < Math.abs(currentTop - HomeBottomSheetBehavior.this.topViewHeight)) {
                        top = HomeBottomSheetBehavior.this.halfExpandedOffset;
                        targetState = STATE_HALF_EXPANDED;
                    } else {
                        top = HomeBottomSheetBehavior.this.topViewHeight;
                        targetState = STATE_COLLAPSED;
                    }
                }
            } else {
                top = HomeBottomSheetBehavior.this.parentHeight;
                targetState = STATE_HIDDEN;
            }

            if (HomeBottomSheetBehavior.this.viewDragHelper.settleCapturedViewAt(releasedChild.getLeft(), top)) {
                HomeBottomSheetBehavior.this.setStateInternal(2);
                ViewCompat.postOnAnimation(releasedChild, HomeBottomSheetBehavior.this.new SettleRunnable(releasedChild, targetState));
            } else {
                HomeBottomSheetBehavior.this.setStateInternal(targetState);
            }

        }

        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            return MathUtils.clamp(top, HomeBottomSheetBehavior.this.getExpandedOffset(), HomeBottomSheetBehavior.this.hideable ? HomeBottomSheetBehavior.this.parentHeight : HomeBottomSheetBehavior.this.topViewHeight);
        }

        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return child.getLeft();
        }

        public int getViewVerticalDragRange(@NonNull View child) {
            return HomeBottomSheetBehavior.this.hideable ? HomeBottomSheetBehavior.this.parentHeight : HomeBottomSheetBehavior.this.topViewHeight;
        }
    }

    public Parcelable onSaveInstanceState(CoordinatorLayout parent, V child) {
        return new HomeBottomSheetBehavior.SavedState(super.onSaveInstanceState(parent, child), this.state);
    }

    public void onRestoreInstanceState(CoordinatorLayout parent, V child, Parcelable state) {
        HomeBottomSheetBehavior.SavedState ss = (HomeBottomSheetBehavior.SavedState) state;
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
        this.parentHeight = parent.getHeight();


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
                case 0:
                    int initialX = (int) event.getX();
                    this.initialY = (int) event.getY();
                    View scroll = this.scrollView != null ? (View) this.scrollView.get() : null;
                    if (scroll != null && parent.isPointInChildBounds(scroll, initialX, this.initialY)) {
                        this.activePointerId = event.getPointerId(event.getActionIndex());
                        this.touchingScrollingChild = true;
                    }

                    this.ignoreEvents = this.activePointerId == -1 && !parent.isPointInChildBounds(child, initialX, this.initialY);
                    break;
                case 1:
                case 3:
                    this.touchingScrollingChild = false;
                    this.activePointerId = -1;
                    if (this.ignoreEvents) {
                        this.ignoreEvents = false;
                        return false;
                    }
                case 2:
            }

            if (!this.ignoreEvents && this.viewDragHelper != null && this.viewDragHelper.shouldInterceptTouchEvent(event)) {
                return true;
            } else {
                View scroll = this.scrollView != null ? (View) this.scrollView.get() : null;
                return action == 2 && scroll != null && !this.ignoreEvents && this.state != STATE_DRAGGING && !parent.isPointInChildBounds(scroll, (int) event.getX(), (int) event.getY()) && this.viewDragHelper != null && Math.abs((float) this.initialY - event.getY()) > (float) this.viewDragHelper.getTouchSlop();
            }
        }
    }

    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent event) {
        if (!child.isShown()) {
            return false;
        } else {
            int action = event.getActionMasked();
            if (this.state == STATE_DRAGGING && action == 0) {
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
                if (action == 2 && !this.ignoreEvents && Math.abs((float) this.initialY - event.getY()) > (float) this.viewDragHelper.getTouchSlop()) {
                    this.viewDragHelper.captureChildView(child, event.getPointerId(event.getActionIndex()));
                }

                return !this.ignoreEvents;
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
            View scrollingChild = (View) this.scrollView.get();
            if (target == scrollingChild) {
                int currentTop = child.getTop();
                int newTop = currentTop - dy;
                if (dy > 0) {
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
                this.nestedScrolled = true;
            }
        }
    }

    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, int type) {
        if (child.getTop() == this.getExpandedOffset()) {
            this.setStateInternal(STATE_EXPANDED);
        } else if (target == this.scrollView.get() && this.nestedScrolled) {
            int top;
            byte targetState;
            if (this.lastNestedScrollDy > 0) {
                top = this.getExpandedOffset();
                targetState = STATE_EXPANDED;
            } else if (this.lastNestedScrollDy == 0) {
                int currentTop = child.getTop();
                if (this.fitToContents) {
                    if (Math.abs(currentTop - this.fitToContentsOffset) < Math.abs(currentTop - this.topViewHeight)) {
                        top = this.fitToContentsOffset;
                        targetState = STATE_EXPANDED;
                    } else {
                        top = this.topViewHeight;
                        targetState = STATE_COLLAPSED;
                    }
                } else if (currentTop < this.halfExpandedOffset) {
                    if (currentTop < Math.abs(currentTop - this.topViewHeight)) {
                        top = 0;
                        targetState = STATE_EXPANDED;
                    } else {
                        top = this.halfExpandedOffset;
                        targetState = STATE_HALF_EXPANDED;
                    }
                } else if (Math.abs(currentTop - this.halfExpandedOffset) < Math.abs(currentTop - this.topViewHeight)) {
                    top = this.halfExpandedOffset;
                    targetState = STATE_HALF_EXPANDED;
                } else {
                    top = this.topViewHeight;
                    targetState = STATE_COLLAPSED;
                }
            } else {
                top = this.topViewHeight;
                targetState = STATE_COLLAPSED;
            }

            if (this.viewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
                this.setStateInternal(STATE_SETTLING);
                ViewCompat.postOnAnimation(child, new HomeBottomSheetBehavior.SettleRunnable(child, targetState));
            } else {
                this.setStateInternal(targetState);
            }

            this.nestedScrolled = false;
        }
    }

    public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull V child, @NonNull View target, float velocityX, float velocityY) {
        return target == this.scrollView.get() && (this.state != STATE_EXPANDED || super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY));
    }

    public void setBottomSheetCallback(HomeBottomSheetBehavior.BottomSheetCallback callback) {
        this.callback = callback;
    }

    public final int getState() {
        return this.state;
    }

    void setStateInternal(int state) {
        if (this.state != state) {
            this.state = state;
            if (state != STATE_HALF_EXPANDED && state != STATE_EXPANDED) {
                if (state == STATE_HIDDEN || state == STATE_COLLAPSED) {
                    this.updateImportantForAccessibility(false);
                }
            } else {
                this.updateImportantForAccessibility(true);
            }

            View bottomSheet = (View) this.scrollView.get();
            if (bottomSheet != null && this.callback != null) {
                this.callback.onStateChanged(bottomSheet, state);
            }

        }
    }

    private void reset() {
        this.activePointerId = -1;
        if (this.velocityTracker != null) {
            this.velocityTracker.recycle();
            this.velocityTracker = null;
        }

    }

    public final void setState(final int state) {
        if (state != this.state) {
            if (this.scrollView == null) {
                if (state == STATE_COLLAPSED || state == STATE_EXPANDED || state == STATE_HALF_EXPANDED || this.hideable && state == STATE_HIDDEN) {
                    this.state = state;
                }

            } else {
                final View child = this.scrollView.get();
                if (child != null) {
                    ViewParent parent = child.getParent();
                    if (parent != null && parent.isLayoutRequested() && ViewCompat.isAttachedToWindow(child)) {
                        child.post(new Runnable() {
                            public void run() {
                                HomeBottomSheetBehavior.this.startSettlingAnimation(child, state);
                            }
                        });
                    } else {
                        this.startSettlingAnimation(child, state);
                    }

                }
            }
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

    private int getExpandedOffset() {
        return this.fitToContents ? this.fitToContentsOffset : 0;
    }

    void startSettlingAnimation(View child, int state) {
        int top;
        if (state == STATE_COLLAPSED) {
            top = this.topViewHeight;
        } else if (state == STATE_HALF_EXPANDED) {
            top = this.halfExpandedOffset;
            if (this.fitToContents && top <= this.fitToContentsOffset) {
                state = STATE_EXPANDED;
                top = this.fitToContentsOffset;
            }
        } else if (state == STATE_EXPANDED) {
            top = this.getExpandedOffset();
        } else {
            if (!this.hideable || state != STATE_HIDDEN) {
                throw new IllegalArgumentException("Illegal state argument: " + state);
            }

            top = this.parentHeight;
        }

        if (this.viewDragHelper.smoothSlideViewTo(child, child.getLeft(), top)) {
            this.setStateInternal(STATE_SETTLING);
            ViewCompat.postOnAnimation(child, new HomeBottomSheetBehavior.SettleRunnable(child, state));
        } else {
            this.setStateInternal(state);
        }

    }

    void dispatchOnSlide(int top) {
        View bottomSheet = (View) this.scrollView.get();
        if (bottomSheet != null && this.callback != null) {
            if (top > this.topViewHeight) {
                this.callback.onSlide(bottomSheet, (float) (this.topViewHeight - top) / (float) (this.parentHeight - this.topViewHeight));
            } else {
                this.callback.onSlide(bottomSheet, (float) (this.topViewHeight - top) / (float) (this.topViewHeight - this.getExpandedOffset()));
            }
        }
    }

    public static <V extends View> HomeBottomSheetBehavior<V> from(V view) {
        LayoutParams params = view.getLayoutParams();
        if (!(params instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("The view is not a child of CoordinatorLayout");
        } else {
            Behavior behavior = ((CoordinatorLayout.LayoutParams) params).getBehavior();
            if (!(behavior instanceof HomeBottomSheetBehavior)) {
                throw new IllegalArgumentException("The view is not associated with HomeBottomSheetBehavior");
            } else {
                return (HomeBottomSheetBehavior) behavior;
            }
        }
    }

    private void updateImportantForAccessibility(boolean expanded) {
        if (this.scrollView != null) {
            ViewParent viewParent = ((View) this.scrollView.get()).getParent();
            if (viewParent instanceof CoordinatorLayout) {
                CoordinatorLayout parent = (CoordinatorLayout) viewParent;
                int childCount = parent.getChildCount();
                if (VERSION.SDK_INT >= 16 && expanded) {
                    if (this.importantForAccessibilityMap != null) {
                        return;
                    }

                    this.importantForAccessibilityMap = new HashMap(childCount);
                }

                for (int i = 0; i < childCount; ++i) {
                    View child = parent.getChildAt(i);
                    if (child != this.scrollView.get()) {
                        if (!expanded) {
                            if (this.importantForAccessibilityMap != null && this.importantForAccessibilityMap.containsKey(child)) {
                                ViewCompat.setImportantForAccessibility(child, (Integer) this.importantForAccessibilityMap.get(child));
                            }
                        } else {
                            if (VERSION.SDK_INT >= 16) {
                                this.importantForAccessibilityMap.put(child, child.getImportantForAccessibility());
                            }

                            ViewCompat.setImportantForAccessibility(child, 4);
                        }
                    }
                }

                if (!expanded) {
                    this.importantForAccessibilityMap = null;
                }

            }
        }
    }

    protected static class SavedState extends AbsSavedState {
        final int state;
        public static final Creator<HomeBottomSheetBehavior.SavedState> CREATOR = new ClassLoaderCreator<HomeBottomSheetBehavior.SavedState>() {
            public HomeBottomSheetBehavior.SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new HomeBottomSheetBehavior.SavedState(in, loader);
            }

            public HomeBottomSheetBehavior.SavedState createFromParcel(Parcel in) {
                return new HomeBottomSheetBehavior.SavedState(in, (ClassLoader) null);
            }

            public HomeBottomSheetBehavior.SavedState[] newArray(int size) {
                return new HomeBottomSheetBehavior.SavedState[size];
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
            if (HomeBottomSheetBehavior.this.viewDragHelper != null && HomeBottomSheetBehavior.this.viewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.view, this);
            } else {
                HomeBottomSheetBehavior.this.setStateInternal(this.targetState);
            }
        }
    }

    public void setHideable(boolean hideable) {
        this.hideable = hideable;
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({Scope.LIBRARY_GROUP})
    public @interface State {
    }

    public interface BottomSheetCallback {

        default void onStateChanged(@NonNull View view, int state) {
        }

        default void onSlide(@NonNull View view, float rate) {
        }
    }
}
