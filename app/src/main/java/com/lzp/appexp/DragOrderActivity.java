package com.lzp.appexp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.base.compat.BaseActivity;
import com.lzp.appexp.adapter.DragOrderAdapter;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-11-05
 */
public class DragOrderActivity extends BaseActivity {

    private RecyclerView dragRcv;
    private DragOrderAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drag_order);
    }

    @Override
    public void findView() {
        dragRcv = findViewById(R.id.dragRcv);
    }

    @Override
    public void initView() {

        dragRcv.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new DragOrderAdapter();
        dragRcv.setAdapter(adapter);

    }
}
