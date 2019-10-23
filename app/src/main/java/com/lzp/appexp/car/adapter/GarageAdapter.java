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
public class GarageAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_garage, viewGroup, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        MyViewHolder holeder = (MyViewHolder) viewHolder;
        if (i==0){
            holeder.car.setImageResource(R.mipmap.ngt_1);
        }else if(i==1){
            holeder.car.setImageResource(R.mipmap.ngt_2);
        }else if(i==2){
            holeder.car.setImageResource(R.mipmap.ngt_3);
        }else if(i==3){
            holeder.car.setImageResource(R.mipmap.ngt_4);
        }else if(i==4){
            holeder.car.setImageResource(R.mipmap.ngt_5);
        }else if(i==5){
            holeder.car.setImageResource(R.mipmap.ngt_6);
        }else if(i==6){
            holeder.car.setImageResource(R.mipmap.ngt_7);
        }else if(i==7){
            holeder.car.setImageResource(R.mipmap.ngt_8);
        }else if(i==8){
            holeder.car.setImageResource(R.mipmap.ngt_9);
        }else if(i==9){
            holeder.car.setImageResource(R.mipmap.ngt_10);
        }else if(i==10){
            holeder.car.setImageResource(R.mipmap.ngt_11);
        }else if(i==11){
            holeder.car.setImageResource(R.mipmap.ngt_12);
        }else if(i==12){
            holeder.car.setImageResource(R.mipmap.ngt_13);
        }else if(i==13){
            holeder.car.setImageResource(R.mipmap.ngt_14);
        }else if(i==14){
            holeder.car.setImageResource(R.mipmap.ngt_15);
        }
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView car;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            car = itemView.findViewById(R.id.car);
        }
    }
}
