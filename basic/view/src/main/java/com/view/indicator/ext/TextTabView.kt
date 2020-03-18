package com.view.indicator.ext

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import com.view.indicator.ITabView


/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020-01-06
 */
class TextTabView : TextView ,ITabView{


    /**
     * 文字颜色
     */
    private var textSelectedColor = -0x10000//0xffff0000
    private var textUnSelectedColor = -0x1000000//0xff000000

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun unselected() {
        setTextColor(textUnSelectedColor)
    }

    override fun selected() {
        setTextColor(textSelectedColor)
    }


    fun setTextSelectedColor(textSelectedColor: Int) {
        this.textSelectedColor = textSelectedColor
    }

    fun setTextUnSelectedColor(textUnSelectedColor: Int) {
        this.textUnSelectedColor = textUnSelectedColor
    }

}