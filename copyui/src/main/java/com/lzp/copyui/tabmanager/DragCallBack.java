package com.lzp.copyui.tabmanager;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-12-02
 */
public interface DragCallBack {
    void onMove(int fromPosition, int toPosition);

    void onSelected(RecyclerView.ViewHolder viewHolder);

    void onEndMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder);
}
