package com.lzp.customview.damp.demo

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.BaseActivity
import com.lzp.customview.R
import com.lzp.customview.common.CommonRecyclerViewTestAdapter
import kotlinx.android.synthetic.main.damp_recycler_view_activity.*

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/5/26
 */
class DampRecyclerViewActivity :BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.damp_recycler_view_activity)
    }

    override fun initView() {
        super.initView()
        actionBarView.setTitleText("弹性recyclerView")
        recyclerView.setNeedStartDamp(true)
        recyclerView.setNeedEndDamp(true)
        recyclerView.addItemDecoration(ItemDecoration())
        recyclerView.layoutManager = LinearLayoutManager(this/*,RecyclerView.HORIZONTAL,false*/)
        recyclerView.adapter = CommonRecyclerViewTestAdapter(10)
    }

    private class ItemDecoration:RecyclerView.ItemDecoration{
        constructor() : super()

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.left = 10
            outRect.bottom = 30
            outRect.top = 80
            outRect.right = 100
        }
    }
}