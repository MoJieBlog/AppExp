package com.lzp.appexp.car;

import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.base.compat.ActionBarView;
import com.base.compat.BaseActivity;
import com.base.compat.StatusBarView;
import com.lzp.appexp.R;
import com.lzp.appexp.car.adapter.GarageViewPagerAdapter;
import com.lzp.appexp.car.transition.GarageEnterTransition;
import com.lzp.appexp.car.transition.GarageReturnTransition;
import com.lzp.appexp.car.transition.PositionTransition;
import com.utils.SizeUtils;

/**
 * @describe 车库
 * @author: lixiaopeng
 * @Date: 2019-10-22
 */
public class GarageActivity extends BaseActivity {

    private static final String TAG = "GarageActivity";

    private int playTime = 1000;
    private ImageView car;
    private ViewPager viewPager;
    private FrameLayout viewPagerContainer;

    ActionBarView actionBar;
    StatusBarView statusBarView;

    private int selectedPosition = 0;
    private GarageViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (VERSION.SDK_INT == VERSION_CODES.O) {//8.0透明背景崩溃
            setTheme(R.style.appTheme);
        }
        setContentView(R.layout.activity_garage);
        setTransition();
    }


    @Override
    public void findView() {
        car = findViewById(R.id.car);
        viewPager = findViewById(R.id.viewPager);
        actionBar = findViewById(R.id.actionBar);
        statusBarView = findViewById(R.id.statusBarView);
        viewPagerContainer = findViewById(R.id.viewPagerContainer);
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

        viewPager.setPageMargin(SizeUtils.dip2px(this, 30));
        adapter = new GarageViewPagerAdapter(12, playTime + 200);
        viewPager.setAdapter(adapter);

        car.postDelayed(new Runnable() {
            @Override
            public void run() {
                int top = car.getTop();
                car.setVisibility(View.INVISIBLE);
            }
        }, playTime + 200);
    }

    @Override
    public void setListener() {
        //透传事件
        viewPagerContainer.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                viewPager.onTouchEvent(event);
                return true;
            }
        });


        viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                selectedPosition = i;
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    @Override
    public void clearListener() {
        viewPagerContainer.setOnTouchListener(null);
    }

    @Override
    public void onBackPressed() {
        car.setVisibility(View.VISIBLE);
        //隐藏掉item的图片
        adapter.onDestroy(selectedPosition);
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
