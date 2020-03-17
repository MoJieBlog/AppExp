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
class MainActivityNew : BaseMainActivity(){

    companion object{
        const val INDEX = "index"

        fun startMainActivity(context: Context){
            startMainActivity(context,0)
        }

        fun startMainActivity(context: Context,index:Int){
            val intent = Intent(context, MainActivityNew::class.java)
            if (context !is Activity){
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            val bundle = Bundle()
            bundle.putInt(INDEX,index)
            intent.putExtra(Constants.EXTRA_BUNDLE,bundle)
            context.startActivity(intent)
        }

    }
}