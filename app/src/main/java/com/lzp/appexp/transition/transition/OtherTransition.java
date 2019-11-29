package com.lzp.appexp.transition.transition;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-29
 */
@RequiresApi(api = VERSION_CODES.KITKAT)
public class OtherTransition extends Visibility {

    private final int confirmDis = 800;
    private  int actionBarDis = -300;
    private  int statusDis = -300;
    private final int carDis = 500;

    private Context context;
    private View rootView;//透明变化
    private View confirmBtn;//上移动画
    private View carView;//上移动画
    private View actionBarView;//下移动画
    private View statusBar;//下移动画

    private final String KEY_Y = "y";

    public OtherTransition(Context context, View rootView, View confirmBtn, View carView, View actionBarView,View statusBar) {
        this.rootView = rootView;
        this.confirmBtn = confirmBtn;
        this.carView = carView;
        this.statusBar = statusBar;
        this.actionBarView = actionBarView;
        this.context = context;

    }


    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);

    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        statusDis =  - statusBar.getBottom();
        actionBarDis =  - actionBarView.getBottom();
    }

    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        if (view != null) {
            ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    rootView.setAlpha(animatedValue);

                    confirmBtn.setTranslationY(confirmDis - animatedValue * confirmDis);
                    actionBarView.setTranslationY(actionBarDis - animatedValue * actionBarDis);
                    statusBar.setTranslationY(statusDis - animatedValue * statusDis);
                    carView.setTranslationY(carDis - animatedValue * carDis);
                }
            });
            return animator;
        }
        return super.onAppear(sceneRoot, view, startValues, endValues);
    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        if (view != null) {
            ValueAnimator animator = ValueAnimator.ofFloat(1, 0);
            animator.setInterpolator(new LinearInterpolator());
            animator.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedValue = (float) animation.getAnimatedValue();
                    rootView.setAlpha(animatedValue);

                    confirmBtn.setTranslationY(confirmDis - animatedValue * confirmDis);
                    statusBar.setTranslationY(statusDis - animatedValue * statusDis);
                    actionBarView.setTranslationY(actionBarDis - animatedValue * actionBarDis);
                    carView.setTranslationY(carDis - animatedValue * carDis);
                }
            });
            return animator;
        }
        return super.onDisappear(sceneRoot, view, startValues, endValues);
    }
}
