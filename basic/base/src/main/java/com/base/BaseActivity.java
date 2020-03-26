package com.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-09-25
 */
public class BaseActivity extends AppCompatActivity implements IBase {

    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            readArgument(savedInstanceState);
        } else if (getIntent().getBundleExtra(Constants.EXTRA_BUNDLE) != null) {
            readArgument(getIntent().getBundleExtra(Constants.EXTRA_BUNDLE));
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        findView();
        initView();
        setListener();
        getData();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        findView();
        initView();
        setListener();
        getData();
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        findView();
        initView();
        setListener();
        getData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        writeArgument(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearListener();
    }
}
