package com.lzp.appexp.car;

import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.compat.ActionBarView;
import com.base.compat.ActionBarView.ActionBarClickAdapter;
import com.base.compat.BaseActivity;
import com.base.compat.StatusBarView;
import com.lzp.appexp.R;
import com.lzp.appexp.car.adapter.GarageAdapter;
import com.lzp.appexp.car.transition.GarageEnterTransition;
import com.lzp.appexp.car.transition.GarageReturnTransition;
import com.lzp.appexp.car.transition.OtherTransition;
import com.lzp.appexp.car.transition.PositionTransition;
import com.view.gallery.GalleryRecyclerView;
import com.view.gallery.GalleryRecyclerView.MONScrollListener;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-25
 */
public class GarageActivityNew extends BaseActivity {

    //为了看清楚动画过程设置为1500毫秒，经过试验发现300毫秒体验最好
    private int playTime = 1500;
    private ImageView car;
    private GalleryRecyclerView rcv;
    ActionBarView actionBar;
    StatusBarView statusBarView;
    private TextView tvConfirm;
    private LinearLayout rootView;
    private FrameLayout carView;

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
        tvConfirm = findViewById(R.id.tvConfirm);
        rootView = findViewById(R.id.rootView);
        carView = findViewById(R.id.carView);
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

        rcv.setMOnScrollListener(new MONScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (car.getVisibility() == View.VISIBLE) {
                    car.setVisibility(View.INVISIBLE);
                    adapter.showLastCar(selectedPosition, rcv);
                }
            }

            @Override
            public void onPageSelected(int position) {
                selectedPosition = position;
            }
        });
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

            OtherTransition otherTransition = new OtherTransition(this, rootView, tvConfirm, carView, actionBar,statusBarView);
            otherTransition.setDuration(playTime);
            getWindow().setEnterTransition(otherTransition);

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
