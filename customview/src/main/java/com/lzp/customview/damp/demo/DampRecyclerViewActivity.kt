package com.lzp.customview.damp.demo

import android.os.Bundle
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
        recyclerView.layoutManager = LinearLayoutManager(this/*,RecyclerView.HORIZONTAL,false*/)
        recyclerView.adapter = CommonRecyclerViewTestAdapter(10)
    }
}