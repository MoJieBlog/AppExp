package com.lzp.appexp.transition;

import android.graphics.Color;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.base.compat.view.ActionBarView;
import com.base.compat.view.ActionBarView.ActionBarClickAdapter;
import com.base.compat.BaseActivity;
import com.base.compat.view.StatusBarView;
import com.lzp.appexp.R;
import com.lzp.appexp.transition.adapter.GarageAdapter;
import com.lzp.appexp.transition.transition.GarageEnterTransition;
import com.lzp.appexp.transition.transition.GarageReturnTransition;
import com.lzp.appexp.transition.transition.OtherTransition;
import com.lzp.appexp.transition.transition.PositionTransition;
import com.view.gallery.GalleryRecyclerView;
import com.view.gallery.GalleryRecyclerView.MONScrollListener;
import com.view.gallery.IndicatorView;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-25
 */
public class GarageActivityNew extends BaseActivity {

    //为了看清楚动画过程设置为1500毫秒，经过试验发现300毫秒体验最好
    private int playTime = 300;
    private ImageView car;
    private GalleryRecyclerView rcv;
    ActionBarView actionBar;
    StatusBarView statusBarView;
    private LinearLayout rootView;
    private FrameLayout carView;
    private IndicatorView indicatorView;

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
        indicatorView = findViewById(R.id.indicatorView);
        rootView = findViewById(R.id.rootView);
        carView = findViewById(R.id.carView);
    }

    @Override
    public void initView() {

        statusBarView.setBackgroundColor(getResources().getColor(R.color.color_status_bar));
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

        adapter = new GarageAdapter(rcv.getItemWidth());

        selectedPosition = 0;
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

        indicatorView.attachRecyclerView(rcv);
        indicatorView.setItemCount(adapter.getItemCount());
        indicatorView.setPageOffset(rcv.getOffset());
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

            OtherTransition otherTransition = new OtherTransition(this, rootView, indicatorView, carView, actionBar,statusBarView);
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
