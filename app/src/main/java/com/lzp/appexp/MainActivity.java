package com.lzp.appexp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.view.refresh.SwipeRefreshLayout;
import com.view.refresh.ext.NiuLoadingLayout;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    NiuLoadingLayout niuLoadingLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        niuLoadingLayout = findViewById(R.id.niu);


        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // niuLoadingLayout.start();
            }
        });

        final SwipeRefreshLayout refreshLayout = findViewById(R.id.refresh);

        refreshLayout.setCanRefresh(true);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.stopRefresh();
                    }
                },5000);
            }
        });
    }
}
