package com.lzp.appexp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.base.compat.ActionBarView;
import com.base.compat.ActionBarView.ActionBarClickAdapter;
import com.base.compat.StatusBarView;
import com.view.loadmore.LoadMoreRecyclerView;
import com.view.loadmore.LoadMoreRecyclerView.OnLoadMoreListener;
import com.view.refresh.SwipeRefreshLayout;
import com.view.refresh.SwipeRefreshLayout.OnRefreshListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    SwipeRefreshLayout refreshLayout;

    TestAdapter mAdapter;

    private LoadMoreRecyclerView rcv;

    ActionBarView actionBar;
    StatusBarView statusBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            //取消设置Window半透明的Flag
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //添加Flag把状态栏设为可绘制模式
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_main);


        rcv = findViewById(R.id.rcv);
        actionBar = findViewById(R.id.actionBar);
        statusBarView = findViewById(R.id.statusBarView);


        statusBarView.setBgColorRes(R.color.color_status_bar);
        actionBar.setActionBarBgRes(R.color.color_actionbar_bg);

        actionBar.setTitleVisible(View.VISIBLE);
        actionBar.setSubTitleVisible(View.VISIBLE);
        actionBar.setLeftImgVisible(View.VISIBLE);
        actionBar.setRightTextVisible(View.VISIBLE);
        actionBar.setTitleTextColor(Color.WHITE);
        actionBar.setTitleText("main title");
        actionBar.setSubTitleText("main subTitle");
        actionBar.setRightText("right text");


        actionBar.setActionBarClickListener(new ActionBarClickAdapter() {
            @Override
            public void onClickTitle(View v) {
                super.onClickTitle(v);
            }

            @Override
            public void onClickSubTitle(View v) {
                super.onClickSubTitle(v);
            }

            @Override
            public void onClickLeftText(View v) {
                super.onClickLeftText(v);
            }

            @Override
            public void onClickLeftImg(View v) {
                super.onClickLeftImg(v);
            }

            @Override
            public void onClickRightText(View v) {
                super.onClickRightText(v);
                Intent intent = new Intent(MainActivity.this,KLineActivityNew.class);
                startActivity(intent);
            }

            @Override
            public void onClickRightImg(View v) {
                super.onClickRightImg(v);
            }
        });


        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setCanRefresh(true);

        // rcv.setLayoutManager(new GridLayoutManager(this,2));
        rcv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        //rcv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TestAdapter(this);

        getData();

        rcv.setAdapter(mAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                freshType = 0;
                getData();
            }
        });
        rcv.setOnLoadmoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                freshType = 1;
                getData();
            }
        });

    }

    private int freshType = 0;

    private void getData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (freshType == 0) {
                            mAdapter.refresh();
                            refreshLayout.stopRefresh();
                        } else {
                            mAdapter.loadMore();
                            rcv.stopLoad();
                        }
                        if (mAdapter.mGetItemCount() > 30) {
                            rcv.setLoadMoreStatus(LoadMoreRecyclerView.LM_LOAD_COMPLETE);
                        } else {
                            rcv.setLoadMoreStatus(LoadMoreRecyclerView.LM_AUTO_LOAD);
                        }
                    }
                });
            }
        }, 1000);
    }
}
