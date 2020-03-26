package com.view.damp

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.NestedScrollingParent
import androidx.core.view.NestedScrollingParentHelper

/**
 * @describe: 嵌套回弹效果
 * @Author: lixiaopeng
 * @Date: 2020-01-10
 */
class NestedDampLayout : FrameLayout,
        NestedScrollingParent,
        NestedScrollingChild {

    private val TAG = "NestedDampLayout"

    companion object {
        //水平方向可以弹
        const val DAMP_HORIZONTAL = 1
        //竖直方向可以弹
        const val DAMP_VERTICAL = 2
    }

    //阻尼系数
    private var dampRate = 0.3f

    private val mNestedScrollingParentHelper = NestedScrollingParentHelper(this)
    private val mNestedScrollingChildHelper = NestedScrollingChildHelper(this)

    private var targetView: View? = null//被弹性的View

    private val mDecelerateInterpolator = DecelerateInterpolator(2f)
    private val resetHeaderAnimator: ValueAnimator? = null

    private val mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop

    private var dampType = DAMP_VERTICAL

    private var moveOffset = 0f
    private var downX = 0f
    private var downY = 0f

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        setWillNotDraw(false)
        isNestedScrollingEnabled = true
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        mNestedScrollingChildHelper.isNestedScrollingEnabled = enabled
    }

    /**
     * 需要回弹的View
     */
    fun setTargetView(targetView: View) {
        this.targetView = targetView
    }

    private fun ensureTarget() {
        require(!(targetView == null && childCount == 0)) { "targetView is null." }
        if (targetView == null) {
            targetView = getChildAt(0)
        }
    }


    override fun canScrollVertically(direction: Int): Boolean {
        ensureTarget()
        return targetView?.canScrollVertically(direction) ?: false
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        ensureTarget()
        return targetView?.canScrollHorizontally(direction) ?: false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (dampType == DAMP_VERTICAL) {//竖直方向
            if (canScrollHorizontally(-1) || canScrollVertically(1)) {
                return false
            }
        }

        if (dampType == DAMP_HORIZONTAL) {
            if (canScrollHorizontally(-1) || canScrollHorizontally(1)) {
                return false
            }
        }
        ev?.apply {
            when (this.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    if (dampType == DAMP_VERTICAL) {
                        moveOffset = translationY
                        downY = ev.y
                    } else {
                        moveOffset = translationX
                        downY = ev.x
                    }

                }

                MotionEvent.ACTION_MOVE -> {
                    if (dampType == DAMP_VERTICAL) {
                        val dy = this.y - downY
                        if (dy > mTouchSlop) {
                            return true
                        }
                    } else {
                        val dx = this.x - downX
                        if (dx > mTouchSlop) {
                            return true
                        }
                    }
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (dampType == DAMP_VERTICAL) {//竖直方向
            if (canScrollHorizontally(-1) || canScrollVertically(1)) {
                return false
            }
        }

        if (dampType == DAMP_HORIZONTAL) {
            if (canScrollHorizontally(-1) || canScrollHorizontally(1)) {
                return false
            }
        }

        event?.apply {
            when (this.actionMasked) {
                MotionEvent.ACTION_DOWN -> {

                }

                MotionEvent.ACTION_MOVE -> {
                    moveOffset = if (dampType == DAMP_VERTICAL) {
                        val dy = this.y - downY
                        downY = this.y
                        dy * dampRate
                    } else {
                        val dx = this.x - downX
                        downX = this.x
                        dx * dampRate
                    }

                    onTargetMove(moveOffset)
                }

                MotionEvent.ACTION_UP,
                MotionEvent.ACTION_CANCEL -> {
                    moveOffset = 0f
                   // onFinishMove()
                }
            }
        }

        return super.onTouchEvent(event)
    }

    private fun onTargetMove(moveOffset: Float):Boolean {
        targetView.apply {
            if (dampType == DAMP_VERTICAL){
                translationY = moveOffset
            }else{
                translationX = moveOffset
            }
        }

        return moveOffset >= 0
    }
}
