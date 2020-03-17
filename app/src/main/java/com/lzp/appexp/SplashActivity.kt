package com.lzp.appexp

import android.content.Intent
import android.os.Bundle
import com.base.compat.BaseActivity
import com.lzp.appexp.main.MainActivityNew

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/3/13
 */
class SplashActivity :BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        MainActivityNew.startMainActivity(this)
    }
}