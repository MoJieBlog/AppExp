package com.lzp.appexp.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.base.BaseActivity
import com.lzp.appexp.R
import com.lzp.copyui.CopyUiFragment
import com.lzp.customview.main.CustomViewFragment
import com.lzp.systemopt.SystemOptFragment
import com.lzp.utilsexample.UtilsExampleFragment
import kotlinx.android.synthetic.main.activity_main_base.*

/**
 * @describe: 实现底部tab切换fragment
 * @Author: lixiaopeng
 * @Date: 2020/3/13
 */
@SuppressLint("Registered")
open class BaseMainActivity : BaseActivity(), MainTabLayout.OnTabChangedListener {

    private var customViewFragment: CustomViewFragment? = null
    private var copyUiFragment: CopyUiFragment? = null
    private var systemOptFragment: SystemOptFragment? = null
    private var utilsExampleFragment: UtilsExampleFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        createFragment(savedInstanceState)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_base)
    }

    private fun createFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            val fragments = supportFragmentManager.fragments
            fragments.forEach {
                when (it) {
                    is CustomViewFragment -> {
                        customViewFragment = it
                    }
                    is CopyUiFragment -> {
                        copyUiFragment = it
                    }
                    is SystemOptFragment -> {
                        systemOptFragment = it
                    }
                    is UtilsExampleFragment -> {
                        utilsExampleFragment = it
                    }
                }
            }
        }
        if (customViewFragment == null) {
            customViewFragment = CustomViewFragment()
        }
        if (copyUiFragment == null) {
            copyUiFragment = CopyUiFragment()
        }
        if (systemOptFragment == null) {
            systemOptFragment = SystemOptFragment()
        }
        if (utilsExampleFragment == null) {
            utilsExampleFragment = UtilsExampleFragment()
        }
    }


    override fun initView() {
        super.initView()
    }

    fun setSelectedIndex(index: Int) {
        tabLayout.setSelectedTab(index)
    }

    override fun setListener() {
        super.setListener()
        tabLayout.setOnTabChangedListener(this)
    }

    override fun clearListener() {
        super.clearListener()
        tabLayout.setOnTabChangedListener(null)
    }

    override fun onTabChange(preIndex: Int, currentIndex: Int) {
        val beginTransaction = supportFragmentManager.beginTransaction()
        val preFragment = getFragmentByIndex(preIndex)
        val nowFragment = getFragmentByIndex(currentIndex)
        if (preFragment != null && preFragment.isAdded) {
            beginTransaction.hide(preFragment)
        }
        nowFragment?.let {
            if (it.isAdded) {
                beginTransaction.show(nowFragment)
            } else {
                beginTransaction.add(R.id.fragmentContainer, nowFragment, nowFragment.javaClass.name)
            }
            beginTransaction.show(nowFragment).commit()
        }
    }

    private fun getFragmentByIndex(index: Int): Fragment? {
        return when (index) {
            0 -> {
                customViewFragment
            }
            1 -> {
                copyUiFragment
            }
            2 -> {
                systemOptFragment
            }
            3 -> {
                utilsExampleFragment
            }
            else -> {
                null
            }
        }
    }
}