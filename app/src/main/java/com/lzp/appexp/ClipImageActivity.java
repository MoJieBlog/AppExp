package com.lzp.appexp;

import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.base.BaseActivity;
import com.lzp.customview.ClipAbleImageView;


/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-12-16
 */
public class ClipImageActivity extends BaseActivity implements OnClickListener {

    private ClipAbleImageView clipImageView;
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
                Path path = new Path();
                path.addCircle(clipImageView.getWidth()/2,clipImageView.getHeight()/2,Math.min(clipImageView.getWidth()/2,clipImageView.getHeight()/2), Path.Direction.CCW);
                clipImageView.clipPath(path);
                break;
            case R.id.btnNotClip:
                clipImageView.setNeedClip(false);
                break;
        }
    }
}
