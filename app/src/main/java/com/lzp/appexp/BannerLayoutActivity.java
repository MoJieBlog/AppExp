package com.lzp.appexp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.base.compat.BaseActivity;
import com.lzp.appexp.car.adapter.BannerAdapter;
import com.view.banner.LooperLayoutManager;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-11-04
 */
public class BannerLayoutActivity extends BaseActivity {

    private RecyclerView horizontalRcv;
    private RecyclerView verticalRcv;

    private LooperLayoutManager hBannerLayoutManager;

    private PagerSnapHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
    }

    @Override
    public void findView() {
        horizontalRcv = findViewById(R.id.horizontalRcv);
        verticalRcv = findViewById(R.id.verticalRcv);
    }

    @Override
    public void initView() {
        hBannerLayoutManager = new LooperLayoutManager();

        horizontalRcv.setLayoutManager(hBannerLayoutManager);
        helper = new PagerSnapHelper();
        helper.attachToRecyclerView(horizontalRcv);
        horizontalRcv.setAdapter(new BannerAdapter());


    }
}
