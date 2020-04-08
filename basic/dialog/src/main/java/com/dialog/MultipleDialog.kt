package com.dialog

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dialog.base.BaseDialog
import com.dialog.base.IMultipleDialog
import com.dialog.base.ITitle
import com.utils.PhoneUtils
import kotlinx.android.synthetic.main.dialog_multiple.*

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/4/1
 */
abstract class MultipleDialog : BaseDialog, ITitle<MultipleDialog>, IMultipleDialog<MultipleDialog> {

    private var adapter: MultipleAdapter = MultipleAdapter()
    private var selectedPosition = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, themeResId: Int) : super(context, themeResId)

    init {
        setGravity(Gravity.BOTTOM)
        val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_multiple, null, false)
        val layoutParams = ViewGroup.LayoutParams(1080, PhoneUtils.getWinHeight(context) / 2)
        setContentView(inflate, layoutParams)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        optTv.setOnClickListener {
            onClickListener?.onConfirmClick(it, selectedPosition)
            dismiss()
        }

        closeIv.setOnClickListener {
            dismiss()
        }
    }

    fun setSelectedPosition(position: Int) {
        this.selectedPosition = position
        adapter.notifyDataSetChanged()
    }

    override fun setTitleVisible(visible: Int): MultipleDialog {
        return this
    }

    override fun setTitleText(text: String?): MultipleDialog {
        return this
    }

    override fun setTitleText(text: Int): MultipleDialog {
        return this
    }

    override fun setTitleColor(color: Int): MultipleDialog {
        return this
    }

    override fun setTitleColorRes(color: Int): MultipleDialog {
        return this
    }

    override fun setTitleSize(size: Float): MultipleDialog {
        return this
    }

    override fun setTitleBold(bold: Boolean): MultipleDialog {
        return this
    }

    override fun setOptTextSize(size: Float): MultipleDialog {
        adapter.setOptTextSize(size)
        return this
    }

    override fun setOptTextColor(color: Int): MultipleDialog {
        adapter.setOptTextColor(color)
        return this
    }

    override fun setOptTextColorRes(color: Int): MultipleDialog {
        adapter.setOptTextColor(color)
        return this
    }

    override fun setOptTextTypeFace(typeFace: Typeface?): MultipleDialog {
        adapter.setOptTextTypeFace(typeFace)
        return this
    }

    override fun setOptTextBold(bold: Boolean): MultipleDialog {
        adapter.setOptTextBold(bold)
        return this
    }

    abstract fun bindView(position: Int, view: TextView)
    abstract fun itemSize(): Int

    companion object {
        class ViewHolder : RecyclerView.ViewHolder {
            val textView: TextView
            val bottomLine: View
            val selectedIv: ImageView

            constructor(itemView: View) : super(itemView) {
                textView = itemView.findViewById(R.id.textView)
                bottomLine = itemView.findViewById(R.id.bottomLine)
                selectedIv = itemView.findViewById(R.id.selectedIv)
            }
        }
    }

    inner class MultipleAdapter : RecyclerView.Adapter<ViewHolder>(), IMultipleDialog<MultipleAdapter> {

        private var textSize = 17f
        private var textColor = 0xff333333.toInt()
        private var textTypeFace: Typeface? = null
        private var textBold = false

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_multiple_dialog, parent, false)
            val viewHolder = ViewHolder(itemView = itemView)
            viewHolder.itemView.setOnClickListener {
                selectedPosition = viewHolder.adapterPosition
                notifyDataSetChanged()
            }
            return viewHolder
        }

        override fun getItemCount(): Int {
            return itemSize()
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.apply {
                this.textSize = this@MultipleAdapter.textSize
                this.setTextColor(textColor)
                this.paint.isFakeBoldText = textBold
                if (textTypeFace != null) {
                    this.typeface = textTypeFace
                }
            }
            if (position == selectedPosition) {
                holder.selectedIv.visibility = View.VISIBLE
            } else {
                holder.selectedIv.visibility = View.GONE
            }

            if (position==itemCount-1){
                holder.bottomLine.visibility = View.INVISIBLE
            }else{
                holder.bottomLine.visibility = View.VISIBLE
            }

            bindView(position, holder.textView)
        }

        override fun setOptTextSize(size: Float): MultipleAdapter {
            this.textSize = size
            return this
        }

        override fun setOptTextColor(color: Int): MultipleAdapter {
            this.textColor = color
            return this
        }

        override fun setOptTextColorRes(color: Int): MultipleAdapter {
            this.textColor = context.resources.getColor(color)
            return this
        }

        override fun setOptTextTypeFace(typeFace: Typeface?): MultipleAdapter {
            this.textTypeFace = typeFace
            return this
        }

        override fun setOptTextBold(bold: Boolean): MultipleAdapter {
            this.textBold = bold
            return this
        }
    }

    private var onClickListener: OnConfirmListener? = null
    fun setConfirmClickListener(onClickListener: OnConfirmListener?): MultipleDialog {
        this.onClickListener = onClickListener
        return this
    }

    interface OnConfirmListener {
        fun onConfirmClick(v: View, position: Int)
    }
}