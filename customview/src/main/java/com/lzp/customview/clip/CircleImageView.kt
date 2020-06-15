package com.lzp.customview.clip

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/6/15
 */
class CircleImageView :AppCompatImageView{

    private val path = Path()
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    override fun onDraw(canvas: Canvas?) {
        path.reset()
        path.addCircle(width/2f,height/2f, min(width/2f,width/2f),Path.Direction.CW)
        canvas?.clipPath(path)
        super.onDraw(canvas)
    }
}