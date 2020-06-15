package com.lzp.copyui.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lzp.copyui.R

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/6/11
 */
class GalleryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private var size = 5

    constructor(size: Int) : super() {
        this.size = size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GalleryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false))
    }

    override fun getItemCount(): Int {
        return size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    private class GalleryViewHolder : RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView)
    }
}