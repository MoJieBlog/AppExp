package com.view.shadow

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.widget.FrameLayout
import com.utils.SizeUtils


/**
 * @describe:注意View大小包括shadow的大小,注意加padding
 * @Author: lixiaopeng
 * @Date: 2020-01-10
 */
class ShadowFrameLayout : FrameLayout {

    //阴影相关
    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    private var shadowStartColor = Color.RED
    private var shadowEndColor = 0x00000000
    private var shadowLeftWidth = SizeUtils.dip2px(context, 12)
    private var shadowRightWidth = SizeUtils.dip2px(context, 15)
    private var shadowTopWidth = SizeUtils.dip2px(context, 12)
    private var shadowBottomWidth = SizeUtils.dip2px(context, 15)

    //四个边的矩形
    private val leftRect = Rect()
    private val topRect = Rect()
    private val rightRect = Rect()
    private val bottomRect = Rect()

    //四个角的矩形
    private val leftTopRect = RectF()
    private val rightTopRect = RectF()
    private val leftBottomRect = RectF()
    private val rightBottomRect = RectF()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        shadowPaint.style = Paint.Style.FILL
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        leftRect.set(0, shadowTopWidth, shadowLeftWidth, h - shadowBottomWidth)
        topRect.set(shadowLeftWidth, 0, w - shadowRightWidth, shadowTopWidth)
        rightRect.set(w - shadowRightWidth, shadowTopWidth, w, h - shadowBottomWidth)
        bottomRect.set(shadowLeftWidth, h - shadowBottomWidth, w - shadowRightWidth, h)

        leftTopRect.set(0f, 0f, 2*shadowRightWidth.toFloat(), 2*shadowTopWidth.toFloat())
        rightTopRect.set(w - 2*shadowRightWidth.toFloat(), 0f, w.toFloat(), 2*shadowTopWidth.toFloat())
        leftBottomRect.set(0f,h-2*shadowBottomWidth.toFloat(),2*shadowLeftWidth.toFloat(),h.toFloat())
        rightBottomRect.set(w-2*shadowRightWidth.toFloat(),h-2*shadowBottomWidth.toFloat(),w.toFloat(),h.toFloat())
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)

        //画左侧
         shadowPaint.shader = LinearGradient(shadowLeftWidth.toFloat(), 0f, 0f, 0f, shadowStartColor, shadowEndColor, Shader.TileMode.CLAMP)
         canvas?.drawRect(leftRect, shadowPaint)

         //画上侧
         shadowPaint.shader = LinearGradient(0f, shadowTopWidth.toFloat(), 0f, 0f, shadowStartColor, shadowEndColor, Shader.TileMode.CLAMP)
         canvas?.drawRect(topRect, shadowPaint)

         //画右侧
         shadowPaint.shader = LinearGradient(width-shadowRightWidth.toFloat(), 0f, width.toFloat(), 0f, shadowStartColor, shadowEndColor, Shader.TileMode.CLAMP)
         canvas?.drawRect(rightRect, shadowPaint)

         //画下侧
         shadowPaint.shader = LinearGradient(0f, height-shadowBottomWidth.toFloat(), 0f, height.toFloat(), shadowStartColor, shadowEndColor, Shader.TileMode.CLAMP)
         canvas?.drawRect(bottomRect, shadowPaint)

        //画四个角
        //左上角
        shadowPaint.shader = LinearGradient(shadowLeftWidth.toFloat(), shadowTopWidth.toFloat(), 0f, 0f, shadowStartColor, shadowEndColor, Shader.TileMode.CLAMP)
        canvas?.drawArc(leftTopRect, 180f, 90f, true, shadowPaint)
        //右上角
        shadowPaint.shader = LinearGradient(width-shadowRightWidth.toFloat(), shadowTopWidth.toFloat(), width.toFloat(), 0f, shadowStartColor, shadowEndColor, Shader.TileMode.CLAMP)
        canvas?.drawArc(rightTopRect, 270f, 90f, true, shadowPaint)
        //左下角
        shadowPaint.shader = LinearGradient(shadowLeftWidth.toFloat(), height-shadowBottomWidth.toFloat(), 0f, height.toFloat(), shadowStartColor, shadowEndColor, Shader.TileMode.CLAMP)
        canvas?.drawArc(leftBottomRect, 90f, 90f, true, shadowPaint)
        //右下角
        shadowPaint.shader = LinearGradient(width-shadowLeftWidth.toFloat(), height-shadowBottomWidth.toFloat(), width.toFloat(), height.toFloat(), shadowStartColor, shadowEndColor, Shader.TileMode.CLAMP)
        canvas?.drawArc(rightBottomRect, 0f, 90f, true, shadowPaint)
    }
}
