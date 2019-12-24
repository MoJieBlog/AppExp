package com.lzp.appexp.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.base.compat.BaseActivity
import com.lzp.appexp.R
import kotlinx.android.synthetic.main.activity_viewpager_nested.*

class ViewPagerNestActivity : BaseActivity() {

    private val adapter: ParentAdapter = ParentAdapter(supportFragmentManager)
    private val fragmentOne: ViewPagerFragmentOne = ViewPagerFragmentOne()
    private val fragmentTwo: ViewPagerFragmentTwo = ViewPagerFragmentTwo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager_nested)
    }

    override fun initView() {
        super.initView()
        viewpager.adapter = adapter
    }


    inner class ParentAdapter : FragmentPagerAdapter {

        constructor(fm: FragmentManager?) : super(fm)

        override fun getCount(): Int {
            return 2
        }

        override fun getItem(position: Int): Fragment {
            return if (position==0){
                fragmentOne
            }else {
                fragmentTwo
            }
        }
    }

}