package com.lzp.utilsexample.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.base.BaseFragment
import com.dialog.MultipleDialog
import com.lzp.utilsexample.R
import com.lzp.utilsexample.dialog.DialogDemoActivity
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
        var intent:Intent? = null
        when (v?.id) {
            R.id.dialogTv -> {
                intent = Intent(activity,DialogDemoActivity::class.java)
                startActivity(intent)
            }
        }
    }
}