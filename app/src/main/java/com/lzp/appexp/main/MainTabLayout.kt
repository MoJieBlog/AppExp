package com.lzp.appexp.main

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * @describe: mainActivity 底部tab
 * @Author: lixiaopeng
 * @Date: 2020/3/13
 */
class MainTabLayout : LinearLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        orientation = HORIZONTAL
    }


    interface OnTabChange {
        /**
         * @param originalTab 原来的tab位置
         * @param currentTab 新的tab位置
         */
        fun onTabChange(originalTab: Int, currentTab: Int)
    }
}