package com.view.banner;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-11-04
 */
public class BannerLayout extends RecyclerView {

    private BannerLayoutManager layoutManager;

    private int itemCount = 0;

    public BannerLayout(@NonNull Context context) {
        this(context,null);
    }

    public BannerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        layoutManager = new BannerLayoutManager(context,this, LinearLayoutManager.HORIZONTAL);
        layoutManager.setRealCount(itemCount);
        setLayoutManager(layoutManager);
    }

    public void setItemCount(int itemCount){
        this.itemCount = itemCount;
    }
}
