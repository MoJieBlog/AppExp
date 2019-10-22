package com.lzp.appexp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.base.compat.BaseActivity;
import com.imageloader.ImageLoader;
import com.lzp.appexp.behavior.HomeBottomSheetBehavior;

public class TestActivity extends BaseActivity {
    private static final String TAG = "TestActivity";

    private NestedScrollView scrollView;
    private HomeBottomSheetBehavior behavior;

    private LinearLayout llTop;
    private ImageView iv;

    private boolean open = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        findView();

       // ImageLoader.get(this).display("https://www.baidu.com/img/bd_logo1.png").into(iv);



        behavior = HomeBottomSheetBehavior.from(scrollView);
        //behavior.setHideable(true);
        //  behavior.setSkipCollapsed(false);
        behavior.setBottomSheetCallback(new HomeBottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                Log.e(TAG, "onStateChanged: " + i);
            }

            @Override
            public void onSlide(@NonNull View view, float rate) {
                Log.e(TAG, "onSlide: " + rate);

                if (rate >= 0) {
                    llTop.setAlpha(Math.max((1 - rate), 0.5f));

                    iv.setScaleX(Math.max((1 - rate), 0.5f));
                    iv.setScaleY(Math.max((1 - rate), 0.5f));
                }else{
                    if (Math.abs(rate)>0.2f&&!open){
                        open = true;
                        Intent intent = new Intent(TestActivity.this, TestActivityNew.class);
                        ActivityOptionsCompat options = ActivityOptionsCompat
                                .makeSceneTransitionAnimation((Activity) TestActivity.this,
                                        iv, Constants.TRANSITION_HOME);
                        TestActivity.this.startActivity(intent, options.toBundle());
                    }
                }

            }
        });

    }

    @Override
    protected void onResume() {
        if (open){
            open = false;
        }
        super.onResume();
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
