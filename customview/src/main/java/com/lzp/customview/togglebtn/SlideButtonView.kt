package com.lzp.customview.togglebtn

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.lzp.customview.R
import com.lzp.customview.textview.LinearGradientTextView
import com.utils.SizeUtils

/**
 * @describe: 滑动开关
 * @Author: lixiaopeng
 * @Date: 2020/4/10
 */
class SlideButtonView :FrameLayout{

    private val TAG = "SlideButtonView"

    private var slideView = View(context)
    private var textView = LinearGradientTextView(context)

    /*****************以下为支持的属性*******************/
    /*********背景**********/
    private var backgroundRes = R.drawable.rect_ff000000_r4
    /*********文字**********/
    //文字开始的颜色
    private var textStartColor = 0x62ffffff.toInt()
    //文字结束的颜色
    private var textEndColor = 0xffffffff.toInt()
    //文字文本
    private var textStr = "向右滑动以激活"
    //文本大小
    private var textSize = 17f
    /*********滑块**********/
    //滑块的背景,可以是shape和图片
    private var slideViewBgRes = R.drawable.rect_ffffffff_r4
    //滑块的宽度
    private var slideViewWidth = SizeUtils.dip2px(context,50f)
    //滑块的高度
    private var slideViewHeight = SizeUtils.dip2px(context,40f)
    //滑块的左边距
    private var slideViewLeftMargin = SizeUtils.dip2px(context,2.5f)
    //滑块的右边距
    private var slideViewRightMargin = SizeUtils.dip2px(context,2.5f)

    constructor(context: Context) : super(context,null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){

        initView()
    }

    private fun initView() {
        //初始化背景
        setBackgroundResource(backgroundRes)

        //初始化文字
        textView.text = textStr
        textView.textSize = this.textSize
        val textLp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        textLp.gravity = Gravity.CENTER
        textView.layoutParams = textLp
        textView.horizontalGradient(textStartColor,textEndColor,true)
        addView(textView)

        //初始化滑块
        val slideViewLp = LayoutParams(slideViewWidth.toInt(), slideViewHeight.toInt())
        slideViewLp.gravity = Gravity.CENTER_VERTICAL
        slideViewLp.leftMargin = slideViewLeftMargin.toInt()
        slideView.layoutParams = slideViewLp
        addView(slideView)
        slideView.setBackgroundResource(slideViewBgRes)
    }


}