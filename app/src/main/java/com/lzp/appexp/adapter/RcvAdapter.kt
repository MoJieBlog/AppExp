package com.lzp.appexp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lzp.appexp.R

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020-01-13
 */
class RcvAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private var size = 15

    constructor() : super()
    constructor(size: Int) : super() {
        this.size = size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.test_item, parent, false)
        return MyViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder){
            holder.testIv.setImageResource(R.mipmap.yinsuwan)
            holder.tv.text = position.toString()
        }
    }

    override fun getItemCount(): Int {
        return size
    }

    internal class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var testIv: ImageView = itemView.findViewById(R.id.testIv)
        var tv: TextView = itemView.findViewById(R.id.tv)
    }
}
