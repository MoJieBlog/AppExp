package com.lzp.appexp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.base.compat.BaseActivity;
import com.view.ClipRectImageView;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-12-16
 */
public class ClipImageActivity extends BaseActivity implements OnClickListener {

    private ClipRectImageView clipImageView;
    private Button btnClip;
    private Button btnNotClip;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_imageview);
    }

    @Override
    public void setListener() {
        btnClip.setOnClickListener(this);
        btnNotClip.setOnClickListener(this);
    }

    @Override
    public void findView() {
        clipImageView = findViewById(R.id.clipImageView);
        btnClip = findViewById(R.id.btnClip);
        btnNotClip = findViewById(R.id.btnNotClip);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnClip:
                clipImageView.setClipEndX(500);
                break;
            case R.id.btnNotClip:
                clipImageView.setNeedClip(false);
                break;
        }
    }
}
