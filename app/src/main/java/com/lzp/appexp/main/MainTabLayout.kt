package com.lzp.appexp.main

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.lzp.appexp.R
import kotlinx.android.synthetic.main.activity_main_new.view.*

/**
 * @describe: mainActivity 底部tab
 * @Author: lixiaopeng
 * @Date: 2020/3/13
 */
class MainTabLayout : LinearLayout, View.OnClickListener {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        orientation = HORIZONTAL
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        tab1.setOnClickListener(this)
        tab2.setOnClickListener(this)
        tab3.setOnClickListener(this)
        tab4.setOnClickListener(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        tab1.setOnClickListener(null)
        tab2.setOnClickListener(null)
        tab3.setOnClickListener(null)
        tab4.setOnClickListener(null)
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tab1->{
                tab1.isSelected = true
                tab2.isSelected = false
                tab3.isSelected = false
                tab4.isSelected = false
            }
            R.id.tab2->{
                tab1.isSelected = false
                tab2.isSelected = false
                tab3.isSelected = true
                tab4.isSelected = false
            }
            R.id.tab3->{
                tab1.isSelected = false
                tab2.isSelected = false
                tab3.isSelected = true
                tab4.isSelected = false
            }
            R.id.tab4->{
                tab1.isSelected = false
                tab2.isSelected = false
                tab3.isSelected = false
                tab4.isSelected = true
            }
        }
    }

    interface OnTabChange {
        /**
         * @param originalTab 原来的tab位置
         * @param currentTab 新的tab位置
         */
        fun onTabChange(originalTab: Int, currentTab: Int)
    }
}