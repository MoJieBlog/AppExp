package com.lzp.customview.togglebtn

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import com.lzp.customview.R
import com.lzp.customview.textview.LinearGradientTextView
import com.utils.SizeUtils

/**
 * @describe: 滑动开关
 * @Author: lixiaopeng
 * @Date: 2020/4/10
 */
class SlideButtonView : FrameLayout {

    private val TAG = "SlideButtonView"

    companion object{
        //未激活
        const val STATE_UN_ACTIVATE = 1
        //激活中
        const val STATE_ACTIVATING = 2
        //激活成功
        const val STATE_ACTIVATE_SUCCESS = 3
        //激活失败
        const val STATE_ACTIVATE_FAIL = 4
    }
    private val slideView = View(context)
    private val textView = LinearGradientTextView(context)
    private val dragHelper: ViewDragHelper = ViewDragHelper.create(this, DragHelperCallBack())

    private var state = STATE_UN_ACTIVATE

    /*****************以下为支持的属性*******************/
    /*********背景**********/
    private var enableBackgroundRes = R.drawable.rect_ff000000_r4
    private var unEnableBackgroundRes = R.drawable.rect_ffaaaaaa_r4
    /*********文字**********/
    //文字开始的颜色
    private var textStartColor = 0x62ffffff.toInt()
    //文字结束的颜色
    private var textEndColor = 0xffffffff.toInt()
    //unEnable时的文字颜色
    private var unEnableTextColor = 0xffffffff.toInt()
    //未激活时的文字文本
    private var textUnActivate = "向右滑动以激活"
    //激活中的文字文本
    private var textActivating = "正在激活..."
    //激活成功的文字文本
    private var textActivateSuccess = "激活成功"
    //激活失败的文字文本
    private var textActivateFail = "激活失败"

    //文本大小
    private var textSize = 17f
    /*********滑块**********/
    //滑块的背景,可以是shape和图片
    private var slideViewBgRes = R.drawable.rect_ffffffff_r4
    //滑块的宽度
    private var slideViewWidth = SizeUtils.dip2px(context, 50f)
    //滑块的高度
    private var slideViewHeight = SizeUtils.dip2px(context, 40f)
    //滑块的左边距
    private var slideViewLeftMargin = SizeUtils.dip2px(context, 2.5f)
    //滑块的右边距
    private var slideViewRightMargin = SizeUtils.dip2px(context, 2.5f)

    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        if (attrs != null) {
            val obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.SlideButtonView)
            enableBackgroundRes = obtainStyledAttributes.getResourceId(R.styleable.SlideButtonView_enable_background_res, enableBackgroundRes)
            textStartColor = obtainStyledAttributes.getColor(R.styleable.SlideButtonView_text_start_color, textStartColor)
            textEndColor = obtainStyledAttributes.getColor(R.styleable.SlideButtonView_text_end_color, textEndColor)
            unEnableTextColor = obtainStyledAttributes.getColor(R.styleable.SlideButtonView_un_enable_text_color, unEnableTextColor)
            textUnActivate = obtainStyledAttributes.getString(R.styleable.SlideButtonView_text_un_activate)
                    ?: textUnActivate
            textActivating = obtainStyledAttributes.getString(R.styleable.SlideButtonView_text_activating)
                    ?: textActivating
            textActivateSuccess = obtainStyledAttributes.getString(R.styleable.SlideButtonView_text_activate_success)
                    ?: textActivateSuccess
            textActivateFail = obtainStyledAttributes.getString(R.styleable.SlideButtonView_text_activate_fail)
                    ?: textActivateFail
            textSize = obtainStyledAttributes.getDimension(R.styleable.SlideButtonView_text_size, textSize)
            slideViewBgRes = obtainStyledAttributes.getResourceId(R.styleable.SlideButtonView_slide_view_bg, slideViewBgRes)
            slideViewWidth = obtainStyledAttributes.getDimension(R.styleable.SlideButtonView_slide_view_width, slideViewWidth)
            slideViewHeight = obtainStyledAttributes.getDimension(R.styleable.SlideButtonView_slide_view_height, slideViewHeight)
            slideViewLeftMargin = obtainStyledAttributes.getDimension(R.styleable.SlideButtonView_slide_view_left_margin, slideViewLeftMargin)
            slideViewRightMargin = obtainStyledAttributes.getDimension(R.styleable.SlideButtonView_slide_view_right_margin, slideViewRightMargin)
            obtainStyledAttributes.recycle()
        }
        initView()
    }

    private fun initView() {
        //初始化背景
        setBackgroundResource(enableBackgroundRes)

        //初始化文字
        textView.textSize = this.textSize
        val textLp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        textLp.gravity = Gravity.CENTER
        textView.layoutParams = textLp
        addView(textView)

        //初始化滑块
        val slideViewLp = LayoutParams(slideViewWidth.toInt(), slideViewHeight.toInt())
        slideViewLp.gravity = Gravity.CENTER_VERTICAL
        slideViewLp.leftMargin = slideViewLeftMargin.toInt()
        slideView.layoutParams = slideViewLp
        slideView.setBackgroundResource(slideViewBgRes)
        addView(slideView)

        setStateUI(state)
    }

    fun setState(state:Int){
        this.state = state
        setStateUI(state)
    }

    private fun setStateUI(state: Int) {
        when(state){
            STATE_UN_ACTIVATE->{
                reset()
                setBackgroundResource(enableBackgroundRes)
                slideView.visibility = View.VISIBLE
                textView.text = textUnActivate
                textView.horizontalGradient(textStartColor, textEndColor, true)
            }
            STATE_ACTIVATING->{
                setBackgroundResource(unEnableBackgroundRes)
                slideView.visibility = View.INVISIBLE
                textView.text = textActivating
                textView.horizontalGradient(unEnableTextColor, unEnableTextColor, true)
            }
            STATE_ACTIVATE_SUCCESS->{
                setBackgroundResource(unEnableBackgroundRes)
                slideView.visibility = View.INVISIBLE
                textView.text = textActivateSuccess
                textView.horizontalGradient(unEnableTextColor, unEnableTextColor, true)
            }
            STATE_ACTIVATE_FAIL->{
                setBackgroundResource(unEnableBackgroundRes)
                slideView.visibility = View.INVISIBLE
                textView.text = textActivateFail
                textView.horizontalGradient(unEnableTextColor, unEnableTextColor, true)
            }
        }
    }


    inner class DragHelperCallBack : ViewDragHelper.Callback() {
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            return child == slideView
        }

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            var tempLeft = left
            if (left < slideViewLeftMargin) {
                tempLeft = slideViewLeftMargin.toInt()
            } else if (left > width - slideViewRightMargin - slideViewWidth) {
                tempLeft = (width - slideViewRightMargin - slideViewWidth).toInt()
            }
            return tempLeft
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return (height/2-slideViewHeight/2).toInt()
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            val left = slideView.left
            if (left<width/2){
                dragHelper.smoothSlideViewTo(slideView, slideViewLeftMargin.toInt(), slideView.top)
                ViewCompat.postInvalidateOnAnimation(this@SlideButtonView)
            }else{
                dragHelper.smoothSlideViewTo(slideView, (width - slideViewRightMargin - slideViewWidth).toInt(), slideView.top)
                ViewCompat.postInvalidateOnAnimation(this@SlideButtonView)
                onSlideFinishListener?.onSlideFinish()
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

    fun reset() {
        post {
            dragHelper.smoothSlideViewTo(slideView, slideViewLeftMargin.toInt(), slideView.top)
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

    }

    /********************设置监听***********************/
    private var onSlideFinishListener:OnSlideFinishListener? = null
    fun setOnSlideFinishListener(onSlideFinishListener:OnSlideFinishListener){
        this.onSlideFinishListener = onSlideFinishListener
    }
    interface OnSlideFinishListener{
        fun onSlideFinish()
    }

}