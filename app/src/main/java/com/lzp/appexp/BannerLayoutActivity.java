package com.lzp.appexp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;

import com.base.compat.BaseActivity;
import com.lzp.appexp.adapter.BannerAdapter;
import com.view.banner.BannerLayoutManager;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-11-04
 */
public class BannerLayoutActivity extends BaseActivity {

    private RecyclerView horizontalRcv;

    private BannerLayoutManager hBannerLayoutManager;

    private PagerSnapHelper helper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
    }

    @Override
    public void findView() {
        horizontalRcv = findViewById(R.id.horizontalRcv);
    }

    @Override
    public void initView() {
        hBannerLayoutManager = new BannerLayoutManager(this,horizontalRcv,4);
        horizontalRcv.setAdapter(new BannerAdapter());
        horizontalRcv.setLayoutManager(hBannerLayoutManager);
    }
}
