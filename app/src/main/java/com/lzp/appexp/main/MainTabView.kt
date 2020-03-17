package com.lzp.appexp.main

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.lzp.appexp.R
import com.utils.SizeUtils

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/3/13
 */
class MainTabView : LinearLayout {

    private var tabImgSize = SizeUtils.dip2px(context, 40f)
    private var tabText = ""

    private var selectedTextColor = 0xffffffff.toInt()
    private var selectedTextSize = 13f
    private var selectedImgRes = 0

    private var unselectedTextColor = 0xffffffff.toInt()
    private var unselectedTextSize = 13f
    private var unselectedImgRes = 0

    private val textView = TextView(context)
    private val imageView = ImageView(context)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        context?.apply {
            val ty = obtainStyledAttributes(attrs, R.styleable.MainTabView)
            tabImgSize = ty.getDimension(R.styleable.MainTabView_tab_img_size, tabImgSize)
            tabText = ty.getString(R.styleable.MainTabView_tab_text) ?: ""
            selectedTextColor = ty.getColor(R.styleable.MainTabView_selected_text_color, selectedTextColor)
            selectedTextSize = ty.getFloat(R.styleable.MainTabView_selected_text_size, selectedTextSize)
            selectedImgRes = ty.getResourceId(R.styleable.MainTabView_selected_img_res, selectedImgRes)
            unselectedTextColor = ty.getColor(R.styleable.MainTabView_un_selected_text_color, unselectedTextColor)
            unselectedTextSize = ty.getFloat(R.styleable.MainTabView_un_selected_text_size, unselectedTextSize)
            unselectedImgRes = ty.getResourceId(R.styleable.MainTabView_un_selected_img_res, unselectedImgRes)
            ty.recycle()
        }

        gravity = Gravity.CENTER
        orientation = VERTICAL
        //初始化文字
        textView.text = tabText
        val textLayoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textView.layoutParams = textLayoutParams
        //初始化图片
        val imgLayoutParams = LayoutParams(tabImgSize.toInt(), tabImgSize.toInt())
        imageView.layoutParams = imgLayoutParams
        //addView
        addView(imageView)
        addView(textView)
        //设置为未选中
        isSelected = false

    }

    override fun setSelected(selected: Boolean) {
        if (selected){
            textView.textSize = selectedTextSize
            textView.setTextColor(selectedTextColor)
            imageView.setImageResource(selectedImgRes)
        }else{
            textView.textSize = unselectedTextSize
            textView.setTextColor(unselectedTextColor)
            imageView.setImageResource(unselectedImgRes)
        }
    }
}