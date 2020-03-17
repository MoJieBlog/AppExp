package com.lzp.appexp.main

import android.annotation.SuppressLint
import android.os.Bundle
import com.base.compat.BaseActivity
import com.lzp.appexp.R

/**
 * @describe: 实现底部tab切换fragment
 * @Author: lixiaopeng
 * @Date: 2020/3/13
 */
@SuppressLint("Registered")
open class BaseMainActivity :BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_new)
    }
}