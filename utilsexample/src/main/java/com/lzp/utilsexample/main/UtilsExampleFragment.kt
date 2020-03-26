package com.lzp.utilsexample.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.BaseFragment
import com.lzp.utilsexample.R

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/3/24
 */
class UtilsExampleFragment : BaseFragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.utils_example_fragment,null)
    }
}