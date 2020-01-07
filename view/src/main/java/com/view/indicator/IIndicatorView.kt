package com.view.indicator

import androidx.viewpager.widget.ViewPager

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020-01-06
 */
interface IIndicatorView{
    fun getIndicatorWidth():Int{
        return 0
    }

    fun onPageScrolled(startX:Float,endX:Float,positionOffset:Float)
}
