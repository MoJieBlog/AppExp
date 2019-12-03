package com.lzp.appexp.tabmanager;

import android.support.v7.widget.RecyclerView;

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
