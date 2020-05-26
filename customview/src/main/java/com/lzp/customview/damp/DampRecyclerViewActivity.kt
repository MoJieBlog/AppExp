package com.lzp.customview.damp

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
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
        actionBar.title = "弹性recyclerView"
        recyclerView.setNeedStartDamp(true)
        recyclerView.setNeedEndDamp(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CommonRecyclerViewTestAdapter(20)
    }
}