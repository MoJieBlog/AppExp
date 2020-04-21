package com.lzp.utilsexample.dialog

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.base.BaseActivity
import com.dialog.MultipleDialog
import com.dialog.OneOptDialog
import com.dialog.TwoOptMsgDialog
import com.lzp.utilsexample.R
import kotlinx.android.synthetic.main.dialog_demo_activity.*

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/4/21
 */
class DialogDemoActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_demo_activity)
    }

    override fun setListener() {
        super.setListener()
        oneOptDialog.setOnClickListener(this)
        twoOptDialog.setOnClickListener(this)
        multipleOptDialog.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.multipleOptDialog -> {
                showMultipleDialog()
            }

            R.id.oneOptDialog -> {
                val oneOptDialog = OneOptDialog(this)
                oneOptDialog.setTitleText("一个选项")
                        .setMessageText("描述信息")
                        .setOptText("确定")
                        .setOnOptClickListener(View.OnClickListener { oneOptDialog.dismiss() })
                        .show()
            }

            R.id.twoOptDialog -> {
                val twoOptMsgDialog = TwoOptMsgDialog(this)
                twoOptMsgDialog.setTitleText("两个选项")
                twoOptMsgDialog.setMessageText("两个选项的msg")
                twoOptMsgDialog.setLeftText("确定")
                twoOptMsgDialog.setRightText("取消")
                twoOptMsgDialog.setOptClickListener(object :TwoOptMsgDialog.OnOptClickListener{
                    override fun leftOptClick(v: View?) {
                        twoOptMsgDialog.dismiss()
                    }

                    override fun rightOptClick(v: View?) {
                        twoOptMsgDialog.dismiss()
                    }
                })
                twoOptMsgDialog.show()
            }
        }
    }

    private fun showMultipleDialog() {
        val multipleDialog = object : MultipleDialog(this@DialogDemoActivity) {
            override fun bindView(position: Int, viewHolder: Companion.ViewHolder) {
                viewHolder.textView.text = position.toString()
            }

            override fun itemSize(): Int {
                return 15
            }
        }
        multipleDialog
                .setOptTextColor(0xffff0000.toInt())
                .setConfirmClickListener(object : MultipleDialog.OnConfirmListener {
                    override fun onConfirmClick(v: View, position: Int) {
                        Toast.makeText(this@DialogDemoActivity, "$position", Toast.LENGTH_SHORT).show()
                    }
                }).setSelectedPosition(2)
        multipleDialog.show()
    }
}