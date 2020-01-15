package com.lzp.appexp;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.base.compat.ToastBaseActivity;
import com.base.compat.view.ActionBarView;
import com.base.compat.view.ActionBarView.ActionBarClickAdapter;
import com.base.compat.view.StatusBarView;
import com.dialog.OneOptDialog;
import com.dialog.TwoOptMsgDialog;
import com.dialog.TwoOptMsgDialog.OnOptClickListener;
import com.lzp.appexp.adapter.TestAdapter;
import com.utils.permission.PermissionConstant;
import com.utils.permission.PermissionUtils;
import com.view.loadmore.LoadMoreRecyclerView;
import com.view.refresh.SwipeRefreshLayout;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionUtils.getPermission(this, PermissionConstant.EXTERNAL_STORAGE_GROUP);
        }
        setContentView(R.layout.activity_main);
    }

    @Override
    public void findView() {
        rcv = findViewById(R.id.rcv);
        actionBar = findViewById(R.id.actionBar);
        statusBarView = findViewById(R.id.statusBarView);
        //statusBarView.setBgColorRes(R.color.color_status_bar);
        actionBar.setActionBarBgRes(R.color.color_actionbar_bg);
        refreshLayout = findViewById(R.id.refresh);
    }

    @Override
    public void initView() {
        initActionBar();
        refreshLayout.setCanRefresh(true);
        rcv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TestAdapter();
        rcv.setAdapter(mAdapter);
    }


    @Override
    public void setListener() {
        refreshLayout.setOnRefreshListener(() -> {
            Log.d("SwipeRefreshLayout", "onRefresh: "+System.currentTimeMillis()/1000);
            freshType = 0;
            getData();
        });
        rcv.setOnLoadmoreListener(() -> {
            freshType = 1;
            getData();
        });
    }

    @Override
    public void clearListener() {
        refreshLayout.setOnRefreshListener(null);
        rcv.setOnLoadmoreListener(null);
    }

    private void initActionBar() {
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
            public void onClickRightText(View v) {
                super.onClickRightText(v);
                new OneOptDialog(MainActivity.this)
                        .setTitleText("标题")
                        .setMessageText("测试文案")
                        .setOptText("确定")
                        .show();
            }
        });
    }

    private long preTime = 0;

    @Override
    public void onBackPressed() {
        if (preTime == 0) {
            preTime = System.currentTimeMillis()-1001;
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
                            rcv.setLoadMoreStatus(LoadMoreRecyclerView.LM_LOAD_COMPLETE,"已加载全部");
                        } else {
                            rcv.setLoadMoreStatus(LoadMoreRecyclerView.LM_AUTO_LOAD);
                        }
                    }
                });
            }
        }, 1000);
    }
}
