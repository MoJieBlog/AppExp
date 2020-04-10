package com.lzp.customview.togglebtn

import android.os.Bundle
import com.base.BaseActivity
import com.lzp.customview.R
import kotlinx.android.synthetic.main.activity_toggle_button.*

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/3/27
 */
class ToggleButtonActivity:BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toggle_button)
    }

    override fun initView() {
        super.initView()

        slideView.setOnSlideFinishListener(object :SlideButtonView.OnSlideFinishListener{
            override fun onSlideFinish() {
                slideView.setState(SlideButtonView.STATE_ACTIVATING)
                slideView.postDelayed({
                    slideView.setState(SlideButtonView.STATE_ACTIVATE_SUCCESS)
                },1000)
                slideView.postDelayed({
                    slideView.setState(SlideButtonView.STATE_UN_ACTIVATE)
                },2000)
            }
        })
    }
}