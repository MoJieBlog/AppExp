package com.lzp.appexp.transition;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.core.widget.NestedScrollView;

import com.base.BaseActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.lzp.appexp.Constants;
import com.lzp.appexp.R;
import com.lzp.appexp.transition.behavior.HomeBottomSheetBehavior;
import com.view.refresh.ext.moveopen.MoveOpenAndRefreshLayout;
import com.view.refresh.ext.moveopen.MoveOpenAndRefreshLayout.OpenRefreshListener;

public class CarActivity extends BaseActivity {
    private static final String TAG = "CarActivity";

    private MoveOpenAndRefreshLayout fresh;
    private NestedScrollView contentLayout;
    private HomeBottomSheetBehavior behavior;

    private CoordinatorLayout rootView;
    private LinearLayout llTop;
    private ImageView iv;
    private Button btn;

    private TextView content1;
    private TextView content2;
    private TextView content3;
    private TextView content4;
    private TextView content5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
    }

    @Override
    public void initView() {
        setTransition();
        isLong = false;
        content5.setVisibility(View.GONE);
        content4.setVisibility(View.GONE);
        content3.setVisibility(View.GONE);

        fresh.setCanRefresh(true);

        fresh.setOnRefreshListener(new OpenRefreshListener() {
            @Override
            public void openActivity() {
                Intent intent = new Intent(CarActivity.this, GarageActivityNew.class);
                transitionTo(intent);
            }

            @Override
            public void onRefresh() {
                fresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fresh.stopRefresh();
                    }
                },3000);
            }
        });
    }

    private boolean isLong = true;
    @Override
    public void setListener() {
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLong){
                    isLong = false;
                    content5.setVisibility(View.GONE);
                    content4.setVisibility(View.GONE);
                    content3.setVisibility(View.GONE);
                }else{
                    isLong = true;
                    content5.setVisibility(View.VISIBLE);
                    content4.setVisibility(View.VISIBLE);
                    content3.setVisibility(View.VISIBLE);
                }
            }
        });
        behavior = (HomeBottomSheetBehavior) HomeBottomSheetBehavior.from(fresh);

        rootView.post(new Runnable() {
            @Override
            public void run() {
                int measuredHeight = llTop.getMeasuredHeight();
                behavior.setTopViewHeight(measuredHeight,rootView);
            }
        });

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
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
                }
            }
        });

        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarActivity.this, GarageActivityNew.class);
                transitionTo(intent);
            }
        });
    }

    public void findView() {
        iv = findViewById(R.id.iv);
        contentLayout = findViewById(R.id.contentLayout);
        llTop = findViewById(R.id.llTop);
        rootView = findViewById(R.id.rootView);
        btn = findViewById(R.id.btn);
        content1 = findViewById(R.id.content1);
        content2 = findViewById(R.id.content2);
        content3 = findViewById(R.id.content3);
        content4 = findViewById(R.id.content4);
        content5 = findViewById(R.id.content5);
        fresh = findViewById(R.id.fresh);
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        supportFinishAfterTransition();
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
}
