package com.lzp.customview.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.BaseFragment
import com.lzp.customview.R
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
        fiveToggleBtn.setOnClickListener(this)
        dampRv.setOnClickListener(this)
        dampVp.setOnClickListener(this)
        indicator.setOnClickListener(this)
        dashBoard.setOnClickListener(this)
        halfCircle.setOnClickListener(this)
        linearGradientTv.setOnClickListener(this)
        roundRl.setOnClickListener(this)
    }

    override fun clearListener() {
        super.clearListener()
        fiveToggleBtn.setOnClickListener(null)
        dampRv.setOnClickListener(null)
        dampVp.setOnClickListener(null)
        indicator.setOnClickListener(null)
        dashBoard.setOnClickListener(null)
        halfCircle.setOnClickListener(null)
        linearGradientTv.setOnClickListener(null)
        roundRl.setOnClickListener(null)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.fiveToggleBtn->{

            }
            R.id.dampRv->{}
            R.id.dampVp->{}
            R.id.indicator->{}
            R.id.dashBoard->{}
            R.id.halfCircle->{}
            R.id.linearGradientTv->{}
            R.id.roundRl->{}
        }
    }

}