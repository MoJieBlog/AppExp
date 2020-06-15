package com.lzp.copyui.gallery

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.lzp.copyui.gallery.itemdecoration.GalleryItemDecoration

/**
 * @describe:画廊效果
 * @Author: lixiaopeng
 * @Date: 2020/6/11
 */
class GalleryRecyclerViewNew : RecyclerView {

    private val galleryLayoutManager: LinearLayoutManager
    private val helper: LinearSnapHelper
    private val itemDecoration: GalleryItemDecoration

    //图片最小的缩放倍数
    private val minScale = 0.66f

    //偏移量，保证第一个和最后一个居中
    private var pagerOffset = 0
    private var itemWidth = 0

    private var selectedPos = 0

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {

        overScrollMode = View.OVER_SCROLL_NEVER
        setFadingEdgeLength(0)
        galleryLayoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        helper = LinearSnapHelper()
        itemDecoration = GalleryItemDecoration(context)

        addItemDecoration(itemDecoration)
        helper.attachToRecyclerView(this)
        layoutManager = galleryLayoutManager
        setListener()
    }

    private fun setListener() {
        addOnLayoutChangeListener(object : OnLayoutChangeListener {
            override fun onLayoutChange(v: View?, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int) {
                onScrollChanged(this@GalleryRecyclerViewNew)
            }
        })
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                listener?.let {
                    it.onScrollStateChanged(recyclerView, newState)
                    if (newState == SCROLL_STATE_IDLE) {
                        selectedPos = findSnapPosition()
                        it.onPageSelected(selectedPos)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                onScrollChanged(recyclerView)
            }
        })
    }

    private fun onScrollChanged(recyclerView: RecyclerView) {
        val childCount = childCount //注意这个childCount是View的个数，不是条目的个数
        for (i in 0 until childCount) {
            val childAt = getChildAt(i)
            val left = childAt.left
            /////////摘抄自网上
            var rate = 0f //是一个缩放比例
            if (left <= pagerOffset) { //如果view距离左边的宽度 小于等于 左侧剩余空间(offset) （意味着这个view开始往左边滑动了，并且有遮挡）
                rate = if (left + childAt.width >= pagerOffset) { //如果view距离左边的距离 小于等于滑进去的距离 （其实就是说滑动到一半的时候）
                    (pagerOffset - left) * 1f / childAt.width //（这个比例的计算结果一般都会大于1，这样一来，根据下面的 1- rate * 0.1 得出，这个比例最多不会到达1，也就是 1- 0.1， 也就是 0.9， 所以这个view的宽度最大不会小于他本身的90%）
                } else {
                    1f
                }
                childAt.scaleY = 1 - rate * (1 - minScale)
                childAt.scaleX = 1 - rate * (1 - minScale)
            } else {
                if (left <= recyclerView.width - pagerOffset) { //这个过程大概是指这个view 从最后侧刚刚出现的时候开始滑动过offset的距离
                    rate = (recyclerView.width - pagerOffset - left) * 1f / childAt.width
                }
                childAt.scaleY = minScale + rate * (1 - minScale)
                childAt.scaleX = minScale + rate * (1 - minScale)
            }
        }
    }

    fun setItemWidth(itemWidth: Int) {
        post {
            this.itemWidth = itemWidth
            this.pagerOffset = (width - itemWidth) / 2
            itemDecoration.setPagerOffset(pagerOffset)
            adapter?.notifyDataSetChanged()
        }
    }

    fun setCurrentItem(position: Int) {
        val currentItemPosition = findSnapPosition()
        smoothScrollBy((position - currentItemPosition) * (itemDecoration.diverWidth * 2 + itemWidth), 0)
    }

    private fun findSnapPosition(): Int {
        val snapView = helper.findSnapView(galleryLayoutManager)
        if (snapView!=null){
            return galleryLayoutManager.getPosition(snapView!!)
        }
        return 0
    }


    fun getSelectedItemPosition(): Int {
        return selectedPos
    }


    interface MONScrollListener {
        fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {}
        fun onPageSelected(position: Int) {}
    }

    private var listener: MONScrollListener? = null

    fun setMOnScrollListener(listener: MONScrollListener?) {
        this.listener = listener
    }

    fun getItemWidth(): Int {
        return itemWidth
    }

    fun getOffset(): Int {
        return pagerOffset
    }
}