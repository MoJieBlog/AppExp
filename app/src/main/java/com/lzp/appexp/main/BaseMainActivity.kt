package com.lzp.appexp.main

import android.annotation.SuppressLint
import android.os.Bundle
import com.base.compat.BaseActivity
import com.lzp.appexp.R
import kotlinx.android.synthetic.main.activity_main_base.*

/**
 * @describe: 实现底部tab切换fragment
 * @Author: lixiaopeng
 * @Date: 2020/3/13
 */
@SuppressLint("Registered")
open class BaseMainActivity :BaseActivity(), MainTabLayout.OnTabChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_base)
    }

    override fun initView() {
        super.initView()
    }
    fun setSelectedIndex(index:Int){
        tabLayout.setSelectedTab(index)
    }

    override fun setListener() {
        super.setListener()
        tabLayout.setOnTabChangedListener(this)
    }

    override fun clearListener() {
        super.clearListener()
        tabLayout.setOnTabChangedListener(null)
    }

    override fun onTabChange(preIndex: Int, currentIndex: Int) {

    }
}