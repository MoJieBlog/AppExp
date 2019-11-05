package com.lzp.appexp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzp.appexp.R;
import com.view.recyclerview.DragOrderItemTouchHelper;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-11-05
 */
public class DragOrderAdapter extends RecyclerView.Adapter {

    private int size = 10;

    private ItemTouchHelper itemTouchHelper;

    public DragOrderAdapter() {
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        DragOrderItemTouchHelper dragOrderItemTouchHelper = new DragOrderItemTouchHelper(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        itemTouchHelper = new ItemTouchHelper(dragOrderItemTouchHelper);
        itemTouchHelper.attachToRecyclerView(recyclerView);
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
        holder.itemView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (itemTouchHelper != null) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            itemTouchHelper.startDrag(holder);
                            break;
                    }
                    return true;
                }
                return false;
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
