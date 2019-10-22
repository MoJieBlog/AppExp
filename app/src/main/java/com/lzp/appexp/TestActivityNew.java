package com.lzp.appexp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.base.compat.BaseActivity;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-22
 */
public class TestActivityNew extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_new);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        supportFinishAfterTransition();
    }
}
