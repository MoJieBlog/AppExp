package com.lzp.systemopt.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.BaseFragment
import com.lzp.systemopt.R

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/3/24
 */
class SystemOptFragment: BaseFragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.systemopt_fragment,null)
    }
}