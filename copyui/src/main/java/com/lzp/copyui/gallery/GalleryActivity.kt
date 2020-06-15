package com.lzp.copyui.gallery

import android.os.Bundle
import com.base.BaseActivity
import com.lzp.copyui.R
import com.utils.SizeUtils
import kotlinx.android.synthetic.main.gallery_activity.*

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/6/11
 */
class GalleryActivity :BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_activity)
    }

    override fun initView() {
        super.initView()
        gallery.setItemWidth(SizeUtils.dip2px(this,80))
        gallery.adapter = GalleryAdapter(10)
    }
}