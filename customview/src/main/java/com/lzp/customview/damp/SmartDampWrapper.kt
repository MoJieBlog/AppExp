package com.lzp.customview.damp

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.utils.BuildConfig

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/6/10
 */
class SmartDampWrapper : FrameLayout {

    private val TAG = "SmartDampWrapper"

    //被包裹的控件
    private var targetView: View? = null
    private val dragHelper: ViewDragHelper = ViewDragHelper.create(this, DragHelperCallBack())

    private var orientation = LinearLayout.VERTICAL

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (childCount != 1) {
            if (BuildConfig.DEBUG) {
                throw IllegalArgumentException("child count must be one")
            } else {
                Log.e(TAG, "child count must be one")
            }
            return
        }
        targetView = getChildAt(0)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (ev != null) {
            Log.e(TAG, "onInterceptTouchEvent: "+ dragHelper.shouldInterceptTouchEvent(ev))
            dragHelper.shouldInterceptTouchEvent(ev)
        } else {
            super.onInterceptTouchEvent(ev)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (event != null) {
            dragHelper.processTouchEvent(event)
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    inner class DragHelperCallBack : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            Log.d(TAG, "tryCaptureView: "+(child == targetView))
            return child == targetView
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return if (orientation == LinearLayout.HORIZONTAL) {
                Log.e(TAG, "clampViewPositionHorizontal: "+left )
                left
            } else {
                super.clampViewPositionHorizontal(child, left, dx)
            }
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return if (orientation == LinearLayout.VERTICAL) {
                Log.d(TAG, "clampViewPositionVertical: "+top)
                top
            } else {
                super.clampViewPositionVertical(child, top, dy)
            }
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {

        }
    }

    override fun computeScroll() {
        //回弹时刷新界面
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }
}