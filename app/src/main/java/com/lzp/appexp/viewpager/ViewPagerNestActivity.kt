package com.lzp.appexp.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.base.BaseActivity
import com.lzp.appexp.R
import com.utils.SizeUtils
import com.view.indicator.ext.LineIndicatorView
import com.view.indicator.ext.TextTabView
import kotlinx.android.synthetic.main.activity_viewpager_nested.*

class ViewPagerNestActivity : BaseActivity() {

    private val adapter: ParentAdapter = ParentAdapter(supportFragmentManager)
    private val fragmentOne: ViewPagerFragmentOne = ViewPagerFragmentOne()
    private val fragmentTwo: ViewPagerFragmentOne = ViewPagerFragmentOne()
    private val fragmentThree: ViewPagerFragmentOne = ViewPagerFragmentOne()
   /* private val fragmentTwo: ViewPagerFragmentTwo = ViewPagerFragmentTwo()
    private val fragmentThree: ViewPagerFragmentOne = ViewPagerFragmentOne()*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager_nested)
    }

    override fun initView() {
        super.initView()
        viewpager.adapter = adapter
        indicatorView.attachViewPager(viewpager)
        indicatorView.setLeftMargin(SizeUtils.dip2px(this,10))
        indicatorView.setRightMargin(SizeUtils.dip2px(this,10))
        indicatorView.createTab(0,TextTabView(this).apply { text = "000000000" })
        indicatorView.createTab(1,TextTabView(this).apply { text = "000000001" })
        indicatorView.createTab(2,TextTabView(this).apply { text = "000000002" })
        indicatorView.setIndicatorView(LineIndicatorView(this))
    }


    inner class ParentAdapter : FragmentPagerAdapter {
        constructor(fm: FragmentManager) : super(fm)
        override fun getCount(): Int {
            return 3
        }

        override fun getItem(position: Int): Fragment {
            return if (position==0){
                fragmentOne
            }else if (position==2){
                fragmentThree
            }else {
                fragmentTwo
            }
        }
    }

}