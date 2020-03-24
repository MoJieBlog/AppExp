package com.lzp.appexp.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.base.compat.Constants

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/3/13
 */
class MainActivityNew : BaseMainActivity() {

    companion object {
        const val INDEX = "index"

        fun startMainActivity(context: Context, index: Int = 0) {
            val intent = Intent(context, MainActivityNew::class.java)
            if (context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            val bundle = Bundle()
            bundle.putInt(INDEX, index)
            intent.putExtra(Constants.EXTRA_BUNDLE, bundle)
            context.startActivity(intent)
        }
    }

    private var index = 0

    override fun readArgument(bundle: Bundle) {
        super.readArgument(bundle)
        index = bundle.getInt(INDEX, index)
    }

    override fun writeArgument(bundle: Bundle) {
        super.writeArgument(bundle)
        bundle.putInt(INDEX, index)
    }

    override fun initView() {
        super.initView()
        setSelectedIndex(index)
        onTabChange(-1,index)
    }
}