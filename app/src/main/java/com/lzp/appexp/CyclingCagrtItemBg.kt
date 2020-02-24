package com.lzp.appexp

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020-02-19
 */
class CyclingCagrtItemBg: View {

    private var viewHeight = 0
    private var viewWidth = 0

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG or Paint.DITHER_FLAG)

    private val rectf = RectF()

    private val rids = FloatArray(8)

    private var radius = 20f

    private val path = Path()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        //硬件加速一定要关了
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 10f
        paint.maskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.OUTER)
        paint.color = Color.RED
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if(w!=0&&h!=0){
            viewHeight = h
            viewWidth = w
            rectf.set(50f,50f,viewWidth.toFloat()-50f,viewHeight.toFloat())

            radius = viewWidth/2f
            rids[0] = radius
            rids[1] = radius
            rids[2] = radius
            rids[3] = radius
            rids[4] = 0f
            rids[5] = 0f
            rids[6] = 0f
            rids[7] = 0f

            path.addRoundRect(rectf, rids, Path.Direction.CW)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawPath(path,paint)
    }
}