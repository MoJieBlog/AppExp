package com.view.cardView.cardViewNew

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.utils.SizeUtils

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020-01-08
 */
class CardViewNew:FrameLayout{

    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)
    private val radius = SizeUtils.dip2px(context,15f)
    private val shadowColor = Color.BLUE

    private var rectF = RectF()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    init {
        setLayerType(View.LAYER_TYPE_SOFTWARE,null)
        shadowPaint.style = Paint.Style.FILL
        shadowPaint.strokeWidth = 1f
        shadowPaint.color = shadowColor
        //shadowPaint.setShadowLayer(radius,0f,0f,shadowColor)
        shadowPaint.maskFilter = BlurMaskFilter(radius,BlurMaskFilter.Blur.NORMAL)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        rectF.set(0f+radius,0f+radius,w.toFloat()-radius,h.toFloat()-radius)
    }


    override fun dispatchDraw(canvas: Canvas?) {
        canvas?.apply {
            this.drawRoundRect(rectF,radius,radius,shadowPaint)
        }
        super.dispatchDraw(canvas)
    }
}
