package com.lzp.appexp.car;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionSet;

import com.base.compat.BaseActivity;
import com.lzp.appexp.R;
import com.lzp.appexp.car.transition.GarageEnterTransition;
import com.lzp.appexp.car.transition.GarageReturnTransition;
import com.lzp.appexp.car.transition.PositionTransition;

/**
 * @describe 车库
 * @author: lixiaopeng
 * @Date: 2019-10-22
 */
public class GarageActivity extends BaseActivity {

    private static final String TAG = "GarageActivity";

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
    }

    @Override
    public void initView() {
    }

    /******************设置转场动画*******************/
    private void setTransition() {
        if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {

            Fade fade = new Fade();
            fade.setDuration(300);
            getWindow().setEnterTransition(fade);

            getWindow().setSharedElementEnterTransition(buildEnterTransition());
            getWindow().setSharedElementReturnTransition(buildReturnTransition());
        }
    }
    
    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    private TransitionSet buildEnterTransition() {
        TransitionSet transitionSet = new TransitionSet();

        Transition changePos = new PositionTransition();
        changePos.setDuration(300);
        changePos.addTarget(R.id.car);
        transitionSet.addTransition(changePos);

        GarageEnterTransition enterTransition = new GarageEnterTransition();
        enterTransition.addTarget(R.id.car);
        enterTransition.setDuration(300);
        transitionSet.addTransition(enterTransition);

        return transitionSet;
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    private TransitionSet buildReturnTransition() {
        TransitionSet transitionSet = new TransitionSet();

        Transition changePos = new PositionTransition();
        changePos.setDuration(300);
        changePos.addTarget(R.id.car);
        transitionSet.addTransition(changePos);

        GarageReturnTransition returnTransition = new GarageReturnTransition();
        returnTransition.addTarget(R.id.car);
        returnTransition.setDuration(300);
        transitionSet.addTransition(returnTransition);

        return transitionSet;
    }


}
