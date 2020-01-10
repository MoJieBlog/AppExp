package com.view.indicator

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020-01-06
 */
interface IIndicatorView{
    fun getIndicatorWidth():Int

    fun getIndicatorHeight():Int

    fun onPageScrolled(startX:Float,endX:Float,positionOffset:Float)
}
