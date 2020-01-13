package com.lzp.appexp.damp

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.compat.BaseActivity
import com.lzp.appexp.R
import com.lzp.appexp.adapter.RcvAdapter
import kotlinx.android.synthetic.main.damp_framelayout_activity.*

/**
 * @describe:越界回弹效果,万物皆可弹效果
 * @Author: lixiaopeng
 * @Date: 2020-01-10
 */
class DampFrameLayoutActivity :BaseActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.damp_framelayout_activity)
    }

    override fun initView() {
        super.initView()
        rcv.layoutManager = LinearLayoutManager(this)
        rcv.adapter = RcvAdapter()
    }

    override fun setListener() {
        super.setListener()
    }

    override fun onClick(v: View?) {
    }
}