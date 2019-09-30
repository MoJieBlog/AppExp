package com.lzp.appexp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.base.compat.BaseActivity;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-09-25
 */
public class KLineActivity extends BaseActivity {


    private RecyclerView rcv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_line);
        getData();
        initView();

    }

    private void initView() {
    }

    private void getData() {


    }
}
