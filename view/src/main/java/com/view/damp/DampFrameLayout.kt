package com.view.damp

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.customview.widget.ViewDragHelper

/**
 * @describe:拖拽控件
 * @Author: lixiaopeng
 * @Date: 2020-01-10
 */
class DampFrameLayout : FrameLayout {

    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1
        const val ALL = 2
        const val NON = 3
    }

    private val helper = ViewDragHelper.create(this, CallBack())

    private var dampView: View? = null

    private var damp = 0.3f

    private var dampType = VERTICAL

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        ev?.apply {
            return helper.shouldInterceptTouchEvent(ev)
        }
        return super.onInterceptTouchEvent(ev)
    }

    fun setDampView(view: View?) {
        dampView = view
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.apply {
            helper.processTouchEvent(event)
            return true
        }

        return super.onTouchEvent(event)
    }

    override fun computeScroll() {
        if (helper.continueSettling(true)) {
            invalidate()
        }
    }

    inner class CallBack : ViewDragHelper.Callback() {

        private var originalLeft = -1
        private var originalTop = -1

        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            if (dampView != null) {//如果指定了View,只有指定的View可以回弹
                return child == dampView
            }
            return true//没有指定，每个View都可以弹
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            super.onViewCaptured(capturedChild, activePointerId)
            originalLeft = capturedChild.left
            originalTop = capturedChild.top
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
            helper.settleCapturedViewAt(originalLeft, originalTop)
            invalidate()
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return if (dampType == HORIZONTAL || dampType == ALL) {
                child.left + (dx * damp).toInt()
            }else{
                child.left
            }
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return if (dampType== VERTICAL||dampType==ALL){
                child.top + (dy * damp).toInt()
            }else{
                child.top
            }
        }
    }
}


