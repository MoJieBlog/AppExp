package com.lzp.copyui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.BaseFragment
import com.lzp.copyui.R
import com.lzp.copyui.message.MessageActivity
import com.lzp.copyui.tabmanager.TabManagerActivity
import com.lzp.copyui.transition.CarActivity
import kotlinx.android.synthetic.main.copyui_fragment.*

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/3/24
 */
class CopyUiFragment : BaseFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.copyui_fragment, null)
    }

    override fun setListener() {
        super.setListener()
        switchMsgOpenTv.setOnClickListener(this)
        transitionTv.setOnClickListener(this)
        tabManagerTv.setOnClickListener(this)
    }

    override fun clearListener() {
        super.clearListener()
        switchMsgOpenTv.setOnClickListener(null)
        transitionTv.setOnClickListener(null)
        tabManagerTv.setOnClickListener(null)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.switchMsgOpenTv -> {
                val intent = Intent(activity, MessageActivity::class.java)
                startActivity(intent)
            }
            R.id.transitionTv -> {
                val intent = Intent(activity, CarActivity::class.java)
                startActivity(intent)
            }
            R.id.tabManagerTv -> {
                val intent = Intent(activity, TabManagerActivity::class.java)
                startActivity(intent)
            }
        }
    }
}