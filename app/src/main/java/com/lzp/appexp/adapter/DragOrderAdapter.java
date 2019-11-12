package com.lzp.appexp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzp.appexp.R;
import com.view.recyclerview.DragOrderItemTouchHelperCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-11-05
 */
public class DragOrderAdapter extends RecyclerView.Adapter {

    private int size = 10;

    private ItemTouchHelper itemTouchHelper;

    private List<Integer> list = new ArrayList();

    public DragOrderAdapter() {
        setData();
    }

    public void setSize(int size) {
        this.size = size;
        setData();
    }

    public List<Integer> getList() {
        return list;
    }

    private void setData() {
        list.clear();
        for (int i = 0; i < size; i++) {
            list.add(i);
        }
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (itemTouchHelper!=null){
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_car, viewGroup, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        MyViewHolder holder = (MyViewHolder) viewHolder;
        holder.imageView.setVisibility(View.GONE);
        holder.tv.setText(String.valueOf(i));
        holder.itemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                size++;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return size;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.car);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
