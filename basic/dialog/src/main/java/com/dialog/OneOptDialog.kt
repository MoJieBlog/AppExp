package com.dialog

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import com.dialog.base.BaseDialog
import com.dialog.base.IOneOptDialog
import com.dialog.base.ITitle
import kotlinx.android.synthetic.main.dialog_one_opt.*

class OneOptDialog : BaseDialog, IOneOptDialog<OneOptDialog>, ITitle<OneOptDialog> {

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)


    init {
        setGravity(Gravity.CENTER)
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_one_opt, null, false)
        setContentView(inflate)
    }

    override fun setMessageText(text: String?): OneOptDialog {
        messageTv.text = text
        return this
    }

    override fun setMessageText(textRes: Int): OneOptDialog {
        messageTv.text = context.resources.getString(textRes)
        return this
    }

    override fun setMessageTextSize(size: Float): OneOptDialog {
        messageTv.textSize = size
        return this
    }

    override fun setMessageTextColor(color: Int): OneOptDialog {
        messageTv.setTextColor(color)
        return this
    }

    override fun setMessageTextColorRes(color: Int): OneOptDialog {
        messageTv.setTextColor(context.resources.getColor(color))
        return this
    }

    override fun setMessageTextTypeFace(typeFace: Typeface?): OneOptDialog {
        messageTv.typeface = typeFace
        return this
    }

    override fun setMessageTextBold(bold: Boolean): OneOptDialog {
        messageTv.paint.isFakeBoldText = bold
        return this
    }

    override fun setOptText(text: String?): OneOptDialog {
        optTv.text = text
        return this
    }

    override fun setOptText(textRes: Int): OneOptDialog {
        optTv.text = context.resources.getString(textRes)
        return this
    }

    override fun setOptTextSize(size: Float): OneOptDialog {
        optTv.textSize = size
        return this
    }

    override fun setOptTextColor(color: Int): OneOptDialog {
        optTv.setTextColor(color)
        return this
    }

    override fun setOptTextColorRes(color: Int): OneOptDialog {
        optTv.setTextColor(context.resources.getColor(color))
        return this
    }

    override fun setOptTextTypeFace(typeFace: Typeface?): OneOptDialog {
        optTv.typeface = typeFace
        return this
    }

    override fun setOptTextBold(bold: Boolean): OneOptDialog {
        optTv.paint.isFakeBoldText = bold
        return this
    }

    override fun setTitleVisible(visible: Int): OneOptDialog {
        titleTv.visibility = visible
        return this
    }

    override fun setTitleText(text: String?): OneOptDialog {
        titleTv.text = text
        return this
    }

    override fun setTitleText(text: Int): OneOptDialog {
        titleTv.text = context.resources.getString(text)
        return this
    }

    override fun setTitleColor(color: Int): OneOptDialog {
        titleTv.setTextColor(color)
        return this
    }

    override fun setTitleColorRes(color: Int): OneOptDialog {
        titleTv.setTextColor(context.resources.getColor(color))
        return this
    }

    override fun setTitleSize(size: Float): OneOptDialog {
        titleTv.textSize = size
        return this
    }

    override fun setTitleBold(bold: Boolean): OneOptDialog {
        titleTv.paint.isFakeBoldText = bold
        return this
    }
}