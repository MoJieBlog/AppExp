package com.view.kline;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.view.R;

import java.util.ArrayList;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-08
 */
public class KLineAdapter extends RecyclerView.Adapter {

    private ArrayList<Point> points = new ArrayList<>();
    private int itemWidth = 30;

    public void refreshData(ArrayList<Point> points) {
        this.points.clear();
        this.points.addAll(points);
        notifyDataSetChanged();
    }

    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.kline_item_view, viewGroup, false);
        return new KLineViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        KLineViewHolder holder = (KLineViewHolder) viewHolder;
        if (i > 0) {
            holder.klineItem.refreshData(points.get(i - 1), points.get(i));
        } else {
            holder.klineItem.refreshData(null, points.get(0));
        }
    }

    @Override
    public int getItemCount() {
        return points.size();
    }


    class KLineViewHolder extends RecyclerView.ViewHolder {

        KLineItemView klineItem;

        public KLineViewHolder(@NonNull View itemView) {
            super(itemView);
            klineItem = itemView.findViewById(R.id.klineItem);
        }
    }
}
