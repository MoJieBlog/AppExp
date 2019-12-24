package com.lzp.appexp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.imageloader.ImageLoader;
import com.imageloader.interfaces.IMGLoadListener;
import com.lzp.appexp.BannerLayoutActivity;
import com.lzp.appexp.ClipImageActivity;
import com.lzp.appexp.Constants;
import com.lzp.appexp.HorizontalScrollableLinearLayoutActivity;
import com.lzp.appexp.KLineActivity;
import com.lzp.appexp.MessageActivity;
import com.lzp.appexp.R;
import com.lzp.appexp.tabmanager.TabManagerActivity;
import com.lzp.appexp.transition.CarActivity;
import com.lzp.appexp.viewpager.ViewPagerNestActivity;
import com.view.loadmore.LoadMoreAdapter;

import java.util.List;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-07-15
 */
public class TestAdapter extends LoadMoreAdapter {

    final String url = "https://www.baidu.com/img/bd_logo1.png";

    private int size = 15;


    public void loadMore() {
        size += 10;
        notifyDataSetChanged();
    }

    public void refresh() {
        size = 15;
        notifyDataSetChanged();
    }

    public TestAdapter(Context context) {
        super(context);
    }

    @Override
    public int mGetItemCount() {
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        if (position<size){
            return position;
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder mOnCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.test_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        myViewHolder.tv.setText(String.valueOf(viewType));



        return new MyViewHolder(inflate);
    }

    @Override
    public void mOnBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, List p) {
        MyViewHolder holder = (MyViewHolder) viewHolder;

        if (position==0){
            holder.tv.setText("水平滚动的LinerLayout");
        }else if(position==1){
            holder.tv.setText("K线图");
        }else if(position==2){
            holder.tv.setText("转场动画");
        }else if(position==3){
            holder.tv.setText("banner");
        } else if(position==4){
            holder.tv.setText("拖拽排序");
        }else if(position==5){
            holder.tv.setText("消息卡片");
        }else if(position==6){
            holder.tv.setText("裁切图片");
        }else if(position==7){
            holder.tv.setText("viewpager 嵌套");
        }

        else{
            holder.tv.setText(String.valueOf(position));
        }

        if (position==0){

        }
        ImageLoader.get(context)
                .display(url)
                // .size(300,300)
                .needMemory(true)
                .placeHolder(R.mipmap.ic_launcher_round)
                .errHolder(R.mipmap.ic_launcher)
                .listener(new IMGLoadListener<Drawable>() {
                    @Override
                    public void success(Drawable drawable) {

                    }

                    @Override
                    public void fail(Exception e) {

                    }
                })
                .into(holder.testIv);
    }


   static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView testIv;
        TextView tv;

        Context context;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            testIv = itemView.findViewById(R.id.testIv);
            tv = itemView.findViewById(R.id.tv);


            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition==0){
                        Intent intent = new Intent(context, HorizontalScrollableLinearLayoutActivity.class);
                        context.startActivity(intent);
                    }else if(adapterPosition==1){
                        Intent intent = new Intent(context, KLineActivity.class);
                        context.startActivity(intent);
                    }else if(adapterPosition==3){
                        Intent intent = new Intent(context, BannerLayoutActivity.class);
                        context.startActivity(intent);
                    }
                    else if(adapterPosition==4){
                        Intent intent = new Intent(context, TabManagerActivity.class);
                        context.startActivity(intent);
                    }
                    else if(adapterPosition==5){
                        Intent intent = new Intent(context, MessageActivity.class);
                        context.startActivity(intent);
                    }
                    else if(adapterPosition==6){
                        Intent intent = new Intent(context, ClipImageActivity.class);
                        context.startActivity(intent);
                    }
                    else if(adapterPosition==7){
                        Intent intent = new Intent(context, ViewPagerNestActivity.class);
                        context.startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(context, CarActivity.class);
                        ActivityOptionsCompat options = ActivityOptionsCompat
                                .makeSceneTransitionAnimation((Activity) context,
                                        v, Constants.TRANSITION_HOME);
                        context.startActivity(intent, options.toBundle());
                    }
                }
            });

        }
    }
}
