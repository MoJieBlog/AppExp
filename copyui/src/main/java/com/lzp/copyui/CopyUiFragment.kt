package com.lzp.copyui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.compat.BaseFragment

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/3/24
 */
class CopyUiFragment :BaseFragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.copyui_fragment,null)
    }
}