package com.view.indicator.ext

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.view.indicator.IIndicatorView
import android.graphics.RectF
import android.util.Log
import com.utils.SizeUtils


/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020-01-06
 */
class LineIndicatorView : View,IIndicatorView{
    override fun getIndicatorWidth(): Int {
        return viewWidth
    }

    override fun getIndicatorHeight(): Int {
        return indicatorHeight
    }

    private val lineColor = Color.RED
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    private var rectF: RectF = RectF()

    private var startX: Float = 0.toFloat()
    private var endX: Float = 0.toFloat()


    private var viewWidth = SizeUtils.dip2px(context,20)
    private var indicatorHeight = SizeUtils.dip2px(context,3)
    private var radius = indicatorHeight/2f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        paint.style = Paint.Style.FILL
        endX = startX + viewWidth
        paint.color = lineColor
    }

    override fun onPageScrolled(startX: Float, endX: Float, positionOffset: Float) {
        Log.e("=====","$startX   $endX       $positionOffset")
        this.startX = startX
        this.endX = endX
        rectF.set(startX, 0f, endX, indicatorHeight.toFloat())
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRoundRect(rectF, radius, radius, paint)
    }


}