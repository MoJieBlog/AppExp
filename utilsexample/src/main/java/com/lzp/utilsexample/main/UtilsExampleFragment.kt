package com.lzp.utilsexample.main

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.base.BaseFragment
import com.dialog.MultipleDialog
import com.lzp.utilsexample.R
import kotlinx.android.synthetic.main.utils_example_fragment.*

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/3/24
 */
class UtilsExampleFragment : BaseFragment(), View.OnClickListener {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.utils_example_fragment, null)
    }

    override fun setListener() {
        super.setListener()
        dialogTv.setOnClickListener(this)
    }

    override fun clearListener() {
        super.clearListener()
        dialogTv.setOnClickListener(null)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.dialogTv -> {
                showMultipleDialog()
            }
        }
    }

    private fun showMultipleDialog() {
        if (isAdded){
            val multipleDialog = object : MultipleDialog(this@UtilsExampleFragment.context!!) {
                override fun bindView(position: Int, view: TextView) {
                    view.text = position.toString()
                }

                override fun itemSize(): Int {
                    return 15
                }
            }
            multipleDialog.setOptTextColor(0xffff0000.toInt())
            multipleDialog.show()
        }
    }
}