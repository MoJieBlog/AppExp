package com.lzp.appexp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imageloader.ImageLoader;
import com.imageloader.interfaces.IMGLoadListener;
import com.lzp.appexp.car.CarActivity;
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
    protected int mGetItemCount() {
        return size;
    }

    @Override
    protected ViewHolder mOnCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.test_item, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    protected void mOnBindViewHolder(ViewHolder viewHolder, int position, List p) {
        MyViewHolder holder = (MyViewHolder) viewHolder;

        if (position==0){
            holder.tv.setText("水平滚动的LinerLayout");
        }else if(position==1){
            holder.tv.setText("K线图");
        }else if(position==2){
            holder.tv.setText("转场动画");
        }else if(position==3){
            holder.tv.setText("banner");
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


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView testIv;
        TextView tv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
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
                        Intent intent = new Intent(context,KLineActivity.class);
                        context.startActivity(intent);
                    }else if(adapterPosition==3){
                        Intent intent = new Intent(context,BannerLayoutActivity.class);
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
