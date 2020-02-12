package com.view.togglebtn

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.utils.SizeUtils
import com.view.R

/**
 * @describe:五个节点的toggleButton
 * @Author: lixiaopeng
 * @Date: 2020-02-12
 */
class FiveNodesToggleButton : FrameLayout {

    private val dp1 = SizeUtils.dip2px(context, 1)

    private var viewWidth = 0
    private var viewHeight = 0

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    private var bgColor = Color.parseColor("#ff8e8e93")

    private val bgLineHeight = dp1 * 2

    //切换的图片
    private val toggleButtonIcon = ImageView(context)
    private val toggleButtonWidth = dp1 * 44

    private var nodesX = arrayOf(0f, 0f, 0f, 0f, 0f)
    private var dx = 0f

    private val dragHelper: ViewDragHelper = ViewDragHelper.create(this, DragHelperCallBack())

    private var toggleImageRes = R.mipmap.pic_knob
    //当前节点，注意从1开始
    private var currentNodePosition = 1

    constructor(context: Context?) : super(context){
        toggleButtonIcon.setImageResource(toggleImageRes)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val typedArray = context?.obtainStyledAttributes(attrs, R.styleable.FiveNodesToggleButton)
        toggleImageRes = typedArray?.getResourceId(R.styleable.FiveNodesToggleButton_toggle_img_res, R.mipmap.pic_knob)
                ?: R.mipmap.pic_knob
        toggleButtonIcon.setImageResource(toggleImageRes)
        typedArray?.recycle()
    }

    init {
        bgPaint.style = Paint.Style.FILL
        bgPaint.color = bgColor
        bgPaint.strokeWidth = bgLineHeight.toFloat()

        toggleButtonIcon.layoutParams = LayoutParams(toggleButtonWidth, ViewGroup.LayoutParams.MATCH_PARENT)
        toggleButtonIcon.scaleType = ImageView.ScaleType.CENTER_INSIDE
        addView(toggleButtonIcon)
    }


    inner class DragHelperCallBack : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child == toggleButtonIcon
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            var tempLeft = left
            if (left < 0) {
                tempLeft = 0
            } else if (left > width - toggleButtonWidth) {
                tempLeft = width - toggleButtonWidth
            }
            return tempLeft
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            val originalNode = currentNodePosition
            val centerX = toggleButtonIcon.left + toggleButtonIcon.width / 2
            if (centerX <= nodesX[0] + dx / 2f) {//第一个节点
                currentNodePosition = 1
                dragHelper.smoothSlideViewTo(toggleButtonIcon, nodesX[0].toInt() - toggleButtonIcon.width / 2, 0)
                ViewCompat.postInvalidateOnAnimation(this@FiveNodesToggleButton)
            } else if (centerX > nodesX[0] + dx / 2f && centerX <= nodesX[1] + dx / 2f) {
                currentNodePosition = 2
                dragHelper.smoothSlideViewTo(toggleButtonIcon, nodesX[1].toInt() - toggleButtonIcon.width / 2, 0)
                ViewCompat.postInvalidateOnAnimation(this@FiveNodesToggleButton)
            } else if (centerX > nodesX[1] + dx / 2f && centerX < nodesX[2] + dx / 2f) {
                currentNodePosition = 3
                dragHelper.smoothSlideViewTo(toggleButtonIcon, nodesX[2].toInt() - toggleButtonIcon.width / 2, 0)
                ViewCompat.postInvalidateOnAnimation(this@FiveNodesToggleButton)
            } else if (centerX > nodesX[2] + dx / 2f && centerX < nodesX[3] + dx / 2f) {
                currentNodePosition = 4
                dragHelper.smoothSlideViewTo(toggleButtonIcon, nodesX[3].toInt() - toggleButtonIcon.width / 2, 0)
                ViewCompat.postInvalidateOnAnimation(this@FiveNodesToggleButton)
            } else {
                currentNodePosition = 5
                dragHelper.smoothSlideViewTo(toggleButtonIcon, nodesX[4].toInt() - toggleButtonIcon.width / 2, 0)
                ViewCompat.postInvalidateOnAnimation(this@FiveNodesToggleButton)
            }

            if (originalNode != currentNodePosition) {
                nodesChangeListener?.let {
                    it.onNodesChange(currentNodePosition)
                }
            }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return if (ev != null) {
            dragHelper.shouldInterceptTouchEvent(ev)
        } else {
            super.onInterceptTouchEvent(ev)
        }
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        event?.let {
            dragHelper.processTouchEvent(event)
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun computeScroll() {
        //回弹时刷新界面
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w != 0 && h != 0) {
            viewWidth = w
            viewHeight = h
            dx = (viewWidth - toggleButtonWidth) / 4f
            nodesX[0] = toggleButtonWidth / 2f
            nodesX[1] = toggleButtonWidth / 2f + dx
            nodesX[2] = toggleButtonWidth / 2f + dx * 2
            nodesX[3] = toggleButtonWidth / 2f + dx * 3
            nodesX[4] = viewWidth - toggleButtonWidth / 2f
        }
    }

    override fun dispatchDraw(canvas: Canvas?) {
        //画背景点
        drawBg(canvas)
        super.dispatchDraw(canvas)

    }

    private fun drawBg(canvas: Canvas?) {
        if (canvas == null) {
            return
        }
        //划线
        bgPaint.style = Paint.Style.STROKE
        canvas.drawLine(toggleButtonWidth / 2f, height / 2f, viewWidth - toggleButtonWidth / 2f, height / 2f, bgPaint)

        bgPaint.style = Paint.Style.FILL

        //第一个圆直径7dp
        canvas.drawCircle(nodesX[0], height / 2f, dp1 * 3.5f, bgPaint)
        //第二个圆直径6dp
        canvas.drawCircle(nodesX[1], height / 2f, dp1 * 3f, bgPaint)
        //第三个圆直径7dp
        canvas.drawCircle(nodesX[2], height / 2f, dp1 * 3.5f, bgPaint)
        //第四个圆直径6dp
        canvas.drawCircle(nodesX[3], height / 2f, dp1 * 3f, bgPaint)
        //第五个圆直径7dp
        canvas.drawCircle(nodesX[4], height / 2f, dp1 * 3.5f, bgPaint)
    }

    fun setNodePosition(nodePosition: Int) {
        //目前只支持1-5
        if (nodePosition < 1 || nodePosition > 5) {
            return
        }
        this.currentNodePosition = nodePosition
        post {
            dragHelper.smoothSlideViewTo(toggleButtonIcon, nodesX[nodePosition - 1].toInt() - toggleButtonIcon.width / 2, 0)
            ViewCompat.postInvalidateOnAnimation(this@FiveNodesToggleButton)
        }
    }

    fun setToggleImageRes(imgRes: Int) {
        toggleButtonIcon.setImageResource(imgRes)
    }

    fun getCurrentNode(): Int {
        return currentNodePosition
    }

    private var nodesChangeListener: NodesChangeListener? = null
    fun setNodesChangeListener(nodesChangeListener: NodesChangeListener?) {
        this.nodesChangeListener = nodesChangeListener
    }

    interface NodesChangeListener {
        fun onNodesChange(currentNode: Int)
    }
}