package com.lzp.appexp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import com.utils.permission.PermissionConstant;
import com.utils.permission.PermissionUtils;
import com.view.LinearGradientTextView;
import com.view.loadmore.LoadMoreRecyclerView;
import com.view.loadmore.LoadMoreRecyclerView.OnLoadMoreListener;
import com.view.refresh.SwipeRefreshLayout;
import com.view.refresh.SwipeRefreshLayout.OnRefreshListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    SwipeRefreshLayout refreshLayout;

    TestAdapter mAdapter;

    private LoadMoreRecyclerView rcv;

    TestView testView;

    LinearGradientTextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testView = findViewById(R.id.testView);

        tv = findViewById(R.id.tv);

        /*tv.post(new Runnable() {
            @Override
            public void run() {
                tv.setGradientColor(0,0,tv.getMeasuredWidth(),0,0xffffff00,0xff00ffff);
            }
        });*/
        tv.verticalGradient(0xffff00ff,0xffffffff,true);


        testView.setTest();

        PermissionUtils.getPermission(this, PermissionConstant.EXTERNAL_STORAGE_GROUP);
        rcv = findViewById(R.id.rcv);


        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setCanRefresh(true);

        rcv.setLayoutManager(new GridLayoutManager(this,2));
        mAdapter = new TestAdapter(this);

        rcv.setAdapter(mAdapter);

        rcv.setLoadMoreStatus(LoadMoreRecyclerView.LM_LOAD_SUCCESS);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshLayout.stopRefresh();
                                mAdapter.refresh();
                            }
                        });
                    }
                }, 2000);
            }
        });
        rcv.setOnLoadmoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rcv.stopLoadMore();
                                mAdapter.loadMore();
                            }
                        });
                    }
                }, 3000);
            }
        });

    }
}
