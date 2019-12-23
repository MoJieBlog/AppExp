package com.lzp.appexp;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.base.compat.ActionBarView;
import com.base.compat.ActionBarView.ActionBarClickAdapter;
import com.base.compat.StatusBarView;
import com.base.compat.ToastBaseActivity;
import com.dialog.TwoOptMsgDialog;
import com.dialog.TwoOptMsgDialog.OnOptClickListener;
import com.lzp.appexp.adapter.TestAdapter;
import com.utils.permission.PermissionConstant;
import com.utils.permission.PermissionUtils;
import com.view.loadmore.LoadMoreRecyclerView;
import com.view.loadmore.LoadMoreRecyclerView.OnLoadMoreListener;
import com.view.refresh.SwipeRefreshLayout;
import com.view.refresh.SwipeRefreshLayout.OnRefreshListener;

public class MainActivity extends ToastBaseActivity {

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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.getPermission(this, PermissionConstant.EXTERNAL_STORAGE_GROUP);
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
                TwoOptMsgDialog twoOptMsgDialog = new TwoOptMsgDialog(MainActivity.this);
                twoOptMsgDialog.setLeftText("this is title");
                twoOptMsgDialog.setMsgText("this is message.");
                twoOptMsgDialog.setLeftText("cancel");
                twoOptMsgDialog.setRightText("ok");
                twoOptMsgDialog.setOptClickListener(new OnOptClickListener() {
                    @Override
                    public void leftOptClick(View v) {
                        twoOptMsgDialog.dismiss();
                    }

                    @Override
                    public void rightOptClick(View v) {
                        showToast("ok");
                        twoOptMsgDialog.dismiss();
                    }
                });

                twoOptMsgDialog.show();

            }

            @Override
            public void onClickSubTitle(View v) {
                super.onClickSubTitle(v);


            }

            @Override
            public void onClickLeftText(View v) {

            }

            @Override
            public void onClickLeftImg(View v) {
                super.onClickLeftImg(v);
            }

            @Override
            public void onClickRightText(View v) {
                super.onClickRightText(v);
                //((MessageCardView)findViewById(R.id.messageCardView)).show();
               /* Intent intent = new Intent(MainActivity.this, HorizontalScrollableLinearLayoutActivity.class);
                startActivity(intent);*/
            }

            @Override
            public void onClickRightImg(View v) {
                super.onClickRightImg(v);
            }
        });


        refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setCanRefresh(true);

        // rcv.setLayoutManager(new GridLayoutManager(this,2));
        //rcv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rcv.setLayoutManager(new LinearLayoutManager(this));

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
            public void onLoadMore() {
                freshType = 1;
                getData();
            }
        });

    }

    private long preTime = 0;

    @Override
    public void onBackPressed() {
        if (preTime == 0) {
            preTime = System.currentTimeMillis();
            showToast("再按一次退出");
            return;
        }
        if (System.currentTimeMillis() - preTime > 1000) {
            preTime = System.currentTimeMillis();
            showToast("再按一次退出");
        } else {
            super.onBackPressed();
        }
    }

    private int freshType = 0;

    public void getData() {
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
        }, 3000);
    }
}
