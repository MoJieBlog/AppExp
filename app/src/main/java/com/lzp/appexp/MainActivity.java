package com.lzp.appexp;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Window;
import android.view.WindowManager;

import com.view.loadmore.LoadMoreRecyclerView;
import com.view.loadmore.LoadMoreRecyclerView.OnLoadMoreListener;
import com.view.refresh.SwipeRefreshLayout;
import com.view.refresh.SwipeRefreshLayout.OnRefreshListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    SwipeRefreshLayout refreshLayout;

    TestAdapter mAdapter;

    private LoadMoreRecyclerView rcv;



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


        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setCanRefresh(true);

       // rcv.setLayoutManager(new GridLayoutManager(this,2));
        rcv.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //rcv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TestAdapter(this);

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
            public void onLoadmore() {
                freshType = 1;
                getData();
            }
        });

    }

    private int freshType = 0;

    private void getData(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (freshType==0){
                            mAdapter.refresh();
                            refreshLayout.stopRefresh();
                        }else{
                            mAdapter.loadMore();
                            rcv.stopLoadMore();
                        }

                        if (mAdapter.mGetItemCount()>30){
                            rcv.setCanLoad(false);
                        }else{
                            rcv.setCanLoad(true);
                        }
                    }
                });
            }
        }, 1000);
    }
}
