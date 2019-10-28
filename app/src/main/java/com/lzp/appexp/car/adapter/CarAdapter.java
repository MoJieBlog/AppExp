package com.lzp.appexp.car.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzp.appexp.R;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-23
 */
public class CarAdapter extends RecyclerView.Adapter {

    private int size = 0;

    public CarAdapter() {
        this.size = 15;
    }

    public CarAdapter(int size) {
        this.size = size;
    }

    public void setSize(int size){
        this.size = size;
        notifyDataSetChanged();
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
        if (i==0){
            holder.car.setImageResource(R.mipmap.ngt_1);
        }else if(i==1){
            holder.car.setImageResource(R.mipmap.ngt_2);
        }else if(i==2){
            holder.car.setImageResource(R.mipmap.ngt_3);
        }else if(i==3){
            holder.car.setImageResource(R.mipmap.ngt_4);
        }else if(i==4){
            holder.car.setImageResource(R.mipmap.ngt_5);
        }else if(i==5){
            holder.car.setImageResource(R.mipmap.ngt_6);
        }else if(i==6){
            holder.car.setImageResource(R.mipmap.ngt_7);
        }else if(i==7){
            holder.car.setImageResource(R.mipmap.ngt_8);
        }else if(i==8){
            holder.car.setImageResource(R.mipmap.ngt_9);
        }else if(i==9){
            holder.car.setImageResource(R.mipmap.ngt_10);
        }else if(i==10){
            holder.car.setImageResource(R.mipmap.ngt_11);
        }else if(i==11){
            holder.car.setImageResource(R.mipmap.ngt_12);
        }else if(i==12){
            holder.car.setImageResource(R.mipmap.ngt_13);
        }else if(i==13){
            holder.car.setImageResource(R.mipmap.ngt_14);
        }else if(i==14){
            holder.car.setImageResource(R.mipmap.ngt_15);
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView car;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            car = itemView.findViewById(R.id.car);
        }
    }
}
