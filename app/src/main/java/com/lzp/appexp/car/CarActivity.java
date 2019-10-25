package com.lzp.appexp.car;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.base.compat.BaseActivity;
import com.lzp.appexp.Constants;
import com.lzp.appexp.R;
import com.lzp.appexp.car.adapter.CarAdapter;
import com.lzp.appexp.car.behavior.HomeBottomSheetBehavior;
import com.lzp.appexp.car.behavior.HomeBottomSheetBehavior.BottomSheetCallback;
import com.utils.PhoneUtils;
import com.utils.SizeUtils;

public class CarActivity extends BaseActivity {
    private static final String TAG = "CarActivity";

    private RecyclerView rcv;
    private HomeBottomSheetBehavior behavior;

    private CoordinatorLayout rootView;
    private LinearLayout llTop;
    private ImageView iv;

    private boolean open = false;

    private int topViewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
    }

    @Override
    public void initView() {
        setTransition();
        topViewHeight = SizeUtils.dip2px(this, 350);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(linearLayoutManager);
        //rcv.addItemDecoration(new GarageItemDecoration(this));
        CarAdapter garageAdapter = new CarAdapter(12);
        rcv.setAdapter(garageAdapter);
        rcv.post(new Runnable() {
            @Override
            public void run() {
                int measuredHeight = rcv.getMeasuredHeight();
                int disHeight = PhoneUtils.getDisHeight(CarActivity.this) + PhoneUtils.getStatusBarHeight(CarActivity.this);
                if (topViewHeight + measuredHeight < disHeight) {
                    measuredHeight = disHeight - topViewHeight;
                }
                behavior.setFitToContentsOffset(disHeight - measuredHeight);
            }
        });
    }

    @Override
    public void setListener() {
        behavior = HomeBottomSheetBehavior.from(rcv);
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
                } else {//下半部分位移
                    if (Math.abs(rate) > 0.2f && !open) {
                        open = true;
                        //ViewPager
                        //Intent intent = new Intent(CarActivity.this, GarageActivity.class);
                        //使用RecyclerView
                        Intent intent = new Intent(CarActivity.this, GarageActivityNew.class);
                        transitionTo(intent);
                    }
                }
            }
        });

        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(CarActivity.this, GarageActivity.class);
                //使用RecyclerView
                Intent intent = new Intent(CarActivity.this, GarageActivityNew.class);
                transitionTo(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        if (open) {
            open = false;
        }
        super.onResume();
    }

    public void findView() {
        iv = findViewById(R.id.iv);
        rcv = findViewById(R.id.rcv);
        llTop = findViewById(R.id.llTop);
        rootView = findViewById(R.id.rootView);
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
