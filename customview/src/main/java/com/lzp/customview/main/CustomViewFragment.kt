package com.lzp.customview.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.BaseFragment
import com.lzp.customview.R
import com.lzp.customview.damp.demo.DampRecyclerViewActivity
import com.lzp.customview.textview.WordWrapTextViewActivity
import com.lzp.customview.togglebtn.demo.ToggleButtonActivity
import kotlinx.android.synthetic.main.customview_fragment.*

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/3/24
 */
class CustomViewFragment: BaseFragment(), View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.customview_fragment,null)
    }

    override fun setListener() {
        super.setListener()
        toggleBtn.setOnClickListener(this)
        dampRv.setOnClickListener(this)
        dampVp.setOnClickListener(this)
        indicator.setOnClickListener(this)
        dashBoard.setOnClickListener(this)
        halfCircle.setOnClickListener(this)
        linearGradientTv.setOnClickListener(this)
        roundRl.setOnClickListener(this)
        wordWrapTv.setOnClickListener(this)
        freshLayout.setCanRefresh(true)
        freshLayout.setOnRefreshListener {
            freshLayout.postDelayed({
                freshLayout.stopRefresh()
            },1000)
        }
    }

    override fun clearListener() {
        super.clearListener()
        toggleBtn.setOnClickListener(null)
        dampRv.setOnClickListener(null)
        dampVp.setOnClickListener(null)
        indicator.setOnClickListener(null)
        dashBoard.setOnClickListener(null)
        halfCircle.setOnClickListener(null)
        linearGradientTv.setOnClickListener(null)
        roundRl.setOnClickListener(null)
        wordWrapTv.setOnClickListener(null)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.toggleBtn->{
                val intent = Intent(activity, ToggleButtonActivity::class.java)
                startActivity(intent)
            }
            R.id.dampRv->{
                val intent = Intent(activity, DampRecyclerViewActivity::class.java)
                startActivity(intent)
            }
            R.id.dampVp->{}
            R.id.indicator->{}
            R.id.dashBoard->{}
            R.id.halfCircle->{}
            R.id.linearGradientTv->{}
            R.id.roundRl->{}
            R.id.wordWrapTv->{
                val intent = Intent(activity, WordWrapTextViewActivity::class.java)
                startActivity(intent)
            }
        }
    }

}