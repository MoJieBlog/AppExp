package com.view.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager


/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020-01-06
 */
class IndicatorLayout : FrameLayout, ViewPager.OnPageChangeListener {

    private val indicatorContainer = LinearLayout(context)
    private val tabContainer = LinearLayout(context)

    private var currentPosition: Int = 0

    private var indicatorView: IIndicatorView? = null

    private var leftMargin = 0
    private var rightMargin = 0

    private var viewPager: ViewPager? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        isHorizontalScrollBarEnabled = false
        createTabContainer()
        createIndicatorContainer()
    }

    private fun createTabContainer() {
        tabContainer.orientation = LinearLayout.HORIZONTAL
        tabContainer.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        addView(tabContainer)
    }

    private fun createIndicatorContainer() {
        indicatorContainer.orientation = LinearLayout.HORIZONTAL
        indicatorContainer.layoutParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        addView(indicatorContainer)
    }

    fun attachViewPager(viewPager: ViewPager) {
        if (viewPager.adapter == null) {
            throw IllegalArgumentException("viewpager adapter is null.")
            return
        }
        this.viewPager = viewPager
        viewPager.addOnPageChangeListener(this)
        currentPosition = viewPager.currentItem
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        //移动布局
        moveLayout(position, positionOffset)
        //移动indicator
        moveIndicator(position, positionOffset)
    }


    override fun onPageSelected(position: Int) {
        if (currentPosition == position) {
            return
        }
        val childCount = tabContainer.childCount
        val currentTab = tabContainer.getChildAt(currentPosition)
        if (childCount > position) {
            val childAt = tabContainer.getChildAt(position)
            if (childAt is ITabView && currentTab is ITabView) {
                (currentTab as ITabView).unselected()
                (childAt as ITabView).selected()
                this.currentPosition = position
            } else {
                throw RuntimeException("this tab is not a tabView.")
            }
        } else {
            throw RuntimeException("tabs count is less than pages.")
        }
    }

    private fun moveIndicator(position: Int, positionOffset: Float) {
        indicatorView?.apply {
            val positionView = tabContainer.getChildAt(position)
            val positionLeft = positionView.left
            val positionViewWidth = positionView.width
            val afterView = tabContainer.getChildAt(position + 1)
            var afterViewWith = 0
            if (afterView != null) {
                afterViewWith = afterView.width
            }
            val viewWidth = this.getIndicatorWidth()
            if (positionOffset <= 0.5) {
                val startX = (positionLeft + (positionViewWidth - viewWidth) / 2).toFloat()
                val endX = (startX
                        + viewWidth
                        + ((positionViewWidth + afterViewWith) / 2 + rightMargin + leftMargin) * positionOffset * 2)
                this.onPageScrolled(startX, endX, positionOffset)
            } else {
                val startX = (positionLeft
                        + (positionViewWidth - viewWidth) / 2
                        + (positionViewWidth + rightMargin + leftMargin) * (positionOffset - 0.5f) * 2)
                val endX = (positionLeft +
                        (positionViewWidth + viewWidth) / 2 +
                        ((positionViewWidth + afterViewWith) / 2 + rightMargin + leftMargin)).toFloat()
                this.onPageScrolled(startX, endX, positionOffset)
            }
        }
    }

    private fun moveLayout(position: Int, positionOffset: Float) {
        if (position < tabContainer.childCount) {
            val positionView = tabContainer.getChildAt(position)
            val afterView = tabContainer.getChildAt(position + 1)
            val positionViewRight = positionView.right
            val positionViewWidth = positionView.width

            val afterViewWidth = afterView?.width ?: 0
            val winWide = measuredWidth

            val offsetStart = positionViewRight - positionViewWidth / 2 - winWide / 2
            val scrollX = ((afterViewWidth / 2 + positionViewWidth / 2 + leftMargin + rightMargin) * positionOffset).toInt() + offsetStart
            scrollIndicator(scrollX)
        }
    }

    private fun scrollIndicator(scrollX: Int) {
        tabContainer.scrollTo(scrollX, 0)
        indicatorContainer.scrollTo(scrollX, 0)
    }

    fun createTab(pos: Int, tab: View) {
        if (tab is ITabView) {
            setTabLayoutParams(tab)
            tabContainer.addView(tab, pos)
            if (pos==currentPosition){
                tab.selected()
            }else{
                tab.unselected()
            }
            tab.setOnClickListener(OnClickListener {
                viewPager?.setCurrentItem(pos, true)
            })
        }
    }


    private fun setTabLayoutParams(tab: View) {
        val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.rightMargin = rightMargin
        layoutParams.leftMargin = leftMargin
        layoutParams.gravity = Gravity.CENTER
        tab.layoutParams = layoutParams
    }

    fun setIndicatorView(indicatorView: IIndicatorView) {
        if (indicatorView is View){
            this.indicatorView = indicatorView
            indicatorContainer.addView(indicatorView)
            val layoutParams = indicatorView.layoutParams as LinearLayout.LayoutParams
            layoutParams.gravity = Gravity.BOTTOM
        }

    }

    fun setIndicatorView(indicatorView: IIndicatorView, gravity: Int) {
        if (indicatorView is View){
            this.indicatorView = indicatorView
            indicatorContainer.addView(indicatorView)
            val layoutParams = indicatorView.layoutParams as LinearLayout.LayoutParams
            layoutParams.gravity = gravity
        }
    }

    fun setLeftMargin(lefeMargin:Int){
        this.leftMargin = leftMargin
    }

    fun setRightMargin(rightMargin:Int){
        this.rightMargin = rightMargin
    }
}
