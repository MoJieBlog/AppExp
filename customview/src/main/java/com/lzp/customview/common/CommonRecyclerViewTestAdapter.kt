package com.lzp.customview.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzp.customview.R

/**
 * @describe:测试通用的adapter
 * @Author: lixiaopeng
 * @Date: 2020/5/26
 */
class CommonRecyclerViewTestAdapter(private var size: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun refresh(size: Int) {
        this.size = size
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.common_item, parent, false))
    }

    override fun getItemCount(): Int {
        return size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is MyViewHolder) {
            return
        }
        holder.commonTv.text = position.toString()
    }

    private class MyViewHolder : RecyclerView.ViewHolder {
        val commonTv: TextView

        constructor(itemView: View) : super(itemView) {
            commonTv = itemView.findViewById(R.id.commonTv)
        }
    }
}