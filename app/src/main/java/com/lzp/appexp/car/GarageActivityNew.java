package com.lzp.appexp.car;

import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.base.compat.ActionBarView;
import com.base.compat.ActionBarView.ActionBarClickAdapter;
import com.base.compat.BaseActivity;
import com.base.compat.StatusBarView;
import com.lzp.appexp.R;
import com.lzp.appexp.car.adapter.GarageAdapter;
import com.lzp.appexp.car.transition.GarageEnterTransition;
import com.lzp.appexp.car.transition.GarageReturnTransition;
import com.lzp.appexp.car.transition.PositionTransition;
import com.view.gallery.GalleryRecyclerView;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-25
 */
public class GarageActivityNew extends BaseActivity {

    private int playTime = 300;
    private ImageView car;
    private GalleryRecyclerView rcv;
    private FrameLayout garageContainer;
    ActionBarView actionBar;
    StatusBarView statusBarView;

    private GarageAdapter adapter;
    private int selectedPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (VERSION.SDK_INT == VERSION_CODES.O) {//8.0透明背景崩溃
            setTheme(R.style.appTheme);
        }
        setContentView(R.layout.activity_garage_new);
        setTransition();
    }

    @Override
    public void findView() {
        car = findViewById(R.id.car);
        rcv = findViewById(R.id.rcv);
        actionBar = findViewById(R.id.actionBar);
        statusBarView = findViewById(R.id.statusBarView);
        garageContainer = findViewById(R.id.garageContainer);
    }

    @Override
    public void initView() {

        statusBarView.setBgColorRes(R.color.color_status_bar);
        actionBar.setActionBarBgRes(R.color.color_actionbar_bg);

        actionBar.setTitleVisible(View.VISIBLE);
        actionBar.setSubTitleVisible(View.VISIBLE);
        actionBar.setLeftImgVisible(View.VISIBLE);
        actionBar.setRightTextVisible(View.VISIBLE);
        actionBar.setTitleTextColor(Color.WHITE);
        actionBar.setTitleText("main title");
        actionBar.setSubTitleText("main subTitle");
        actionBar.setRightText("right text");

        actionBar.setActionBarClickListener(new ActionBarClickAdapter() {
            @Override
            public void onClickLeftImg(View v) {
                onBackPressed();
            }
        });

        adapter = new GarageAdapter();

        selectedPosition = 2;
        //设置选中，首次加载会隐藏掉图片，用于播放转场动画
        adapter.setSelectedPosition(selectedPosition);
        //设置当前选中
        rcv.setCurrentItem(selectedPosition);
        rcv.setAdapter(adapter);

        car.postDelayed(new Runnable() {
            @Override
            public void run() {
                car.setVisibility(View.INVISIBLE);
            }
        }, playTime + 150);
    }

    @Override
    public void onBackPressed() {
        adapter.onDestroy(selectedPosition, rcv);
        //隐藏掉item的图片
        car.setVisibility(View.VISIBLE);
        super.onBackPressed();
    }

    /******************设置转场动画*******************/
    private void setTransition() {
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {

            Fade fade = new Fade();
            fade.setDuration(playTime);
            getWindow().setEnterTransition(fade);

            getWindow().setSharedElementEnterTransition(buildEnterTransition());
            getWindow().setSharedElementReturnTransition(buildReturnTransition());
        }
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    private TransitionSet buildEnterTransition() {
        TransitionSet transitionSet = new TransitionSet();

        Transition changePos = new PositionTransition();
        changePos.setDuration(playTime);
        changePos.addTarget(R.id.car);
        transitionSet.addTransition(changePos);

        GarageEnterTransition enterTransition = new GarageEnterTransition();
        enterTransition.addTarget(R.id.car);
        enterTransition.setDuration(playTime);
        transitionSet.addTransition(enterTransition);

        return transitionSet;
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    private TransitionSet buildReturnTransition() {
        TransitionSet transitionSet = new TransitionSet();

        Transition changePos = new PositionTransition();
        changePos.setDuration(playTime);
        changePos.addTarget(R.id.car);
        transitionSet.addTransition(changePos);

        GarageReturnTransition returnTransition = new GarageReturnTransition();
        returnTransition.addTarget(R.id.car);
        returnTransition.setDuration(playTime);
        transitionSet.addTransition(returnTransition);

        return transitionSet;
    }

}
