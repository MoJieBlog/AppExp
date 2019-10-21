package com.lzp.appexp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.imageloader.ImageLoader;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = "TestActivity";

    private NestedScrollView scrollView;
    private HomeBottomSheetBehavior behavior;

    private LinearLayout llTop;
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findView();

        ImageLoader.get(this).display("https://www.baidu.com/img/bd_logo1.png").into(iv);


        behavior = HomeBottomSheetBehavior.from(scrollView);
       // behavior.setHideable(true);
       //  behavior.setSkipCollapsed(false);
        behavior.setBottomSheetCallback(new HomeBottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                Log.e(TAG, "onStateChanged: " + i);
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                Log.e(TAG, "onSlide: " + v);

                llTop.setAlpha(1-v);

                iv.setScaleX(Math.max((1-v),0.5f));
                iv.setScaleY(Math.max((1-v),0.5f));
            }
        });

    }

    private void findView() {
        iv = findViewById(R.id.iv);
        scrollView = findViewById(R.id.scrollView);
        llTop = findViewById(R.id.llTop);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        supportFinishAfterTransition();

    }
}
