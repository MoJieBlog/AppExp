package com.view.indicator.ext

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.view.indicator.IIndicatorView
import android.graphics.RectF
import com.utils.SizeUtils


/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020-01-06
 */
class LineIndicatorView : View,IIndicatorView{

    private val lineColor = Color.RED
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    private var rectF: RectF = RectF()

    private var startX: Float = 0.toFloat()
    private var endX: Float = 0.toFloat()


    private var viewWidth = SizeUtils.dip2px(context,33)
    private var indicatorHeight = SizeUtils.dip2px(context,3)
    private var radius = indicatorHeight/2f

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        paint.style = Paint.Style.FILL
        endX = startX + viewWidth
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(viewWidth,MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(indicatorHeight,MeasureSpec.EXACTLY))
    }
    override fun onPageScrolled(startX: Float, endX: Float, positionOffset: Float) {
        this.startX = startX
        this.endX = endX
        rectF.set(startX, (measuredHeight -indicatorHeight).toFloat(), endX, measuredHeight.toFloat())
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRoundRect(rectF, radius, radius, paint)
    }


}