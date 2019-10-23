package com.lzp.appexp;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.NestedScrollView;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.base.compat.BaseActivity;
import com.lzp.appexp.behavior.HomeBottomSheetBehavior;
import com.lzp.appexp.behavior.HomeBottomSheetBehavior.BottomSheetCallback;

public class CarActivity extends BaseActivity {
    private static final String TAG = "CarActivity";

    private NestedScrollView scrollView;
    private HomeBottomSheetBehavior behavior;

    private CoordinatorLayout rootView;
    private LinearLayout llTop;
    private ImageView iv;

    private boolean open = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        findView();

        behavior = HomeBottomSheetBehavior.from(scrollView);
        behavior.setBottomSheetCallback(new BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                Log.e(TAG, "onStateChanged: " + i);
            }

            @Override
            public void onSlide(@NonNull View view, float rate) {
                Log.e(TAG, "onSlide: " + rate);
                if (rate >= 0) {//上半部分位移
                    llTop.setAlpha(Math.max((1 - rate), 0.5f));
                    iv.setScaleX(Math.max((1 - rate), 0.5f));
                    iv.setScaleY(Math.max((1 - rate), 0.5f));
                }else{//下半部分位移
                    if (Math.abs(rate)>0.2f&&!open){
                        open = true;
                        Intent intent = new Intent(CarActivity.this, GarageActivity.class);
                        transitionTo(intent);
                    }
                }

            }
        });

        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarActivity.this, GarageActivity.class);
                transitionTo(intent);
            }
        });


        setTransition();

    }


    private void setTransition() {
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
            Fade fade = new Fade();
            fade.setDuration(300);
            getWindow().setEnterTransition(fade);
        }
    }

    void transitionTo(Intent i) {
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                new Pair<>(iv, Constants.TRANSITION_HOME));
        startActivity(i, transitionActivityOptions.toBundle());
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
        rootView = findViewById(R.id.rootView);
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        supportFinishAfterTransition();
    }
}
