package com.lzp.appexp.car;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.base.compat.BaseActivity;
import com.lzp.appexp.Constants;
import com.lzp.appexp.R;
import com.lzp.appexp.car.adapter.CarAdapter;
import com.lzp.appexp.car.view.HomeBottomSheetView;
import com.utils.SizeUtils;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-28
 */
public class CarActivityNew extends BaseActivity {

    private RecyclerView rcv;

    private HomeBottomSheetView rootView;
    private LinearLayout llTop;
    private ImageView iv;
    private Button btn;

    private boolean open = false;

    private int topViewHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_new);
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
    }

    @Override
    public void setListener() {
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CarAdapter garageAdapter = new CarAdapter(2);
                rcv.setAdapter(garageAdapter);
            }
        });

        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarActivityNew.this, GarageActivityNew.class);
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
        btn = findViewById(R.id.btn);
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
