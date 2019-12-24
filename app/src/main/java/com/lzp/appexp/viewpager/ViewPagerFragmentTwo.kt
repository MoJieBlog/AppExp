package com.lzp.appexp.viewpager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.base.compat.BaseFragment
import com.lzp.appexp.R
import kotlinx.android.synthetic.main.fragment_viewpager_two.*

class ViewPagerFragmentTwo : BaseFragment() {

    private val adapter: ParentAdapter = ParentAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_viewpager_two, null)
    }

    override fun initView() {
        super.initView()
        vpFragment2.adapter = adapter
    }

    inner class ParentAdapter : PagerAdapter() {
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val inflate = LayoutInflater.from(container.context).inflate(R.layout.layoyt_vp, container,false)
            container.addView(inflate)
            return inflate
        }

        override fun getCount(): Int {
            return 3
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }
    }
}