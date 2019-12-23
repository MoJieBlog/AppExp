package com.lzp.appexp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

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
        hBannerLayoutManager = new BannerLayoutManager(this,horizontalRcv);
        horizontalRcv.setAdapter(new BannerAdapter());
        horizontalRcv.setLayoutManager(hBannerLayoutManager);
    }
}
