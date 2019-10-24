package com.lzp.appexp.car.transition;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.lzp.appexp.R;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-23
 */
@RequiresApi(api = VERSION_CODES.KITKAT)
public class GarageReturnTransition extends Transition {

    @Override
    public void captureStartValues(TransitionValues transitionValues) {

        View view = transitionValues.view;
        if (view instanceof ImageView) {//指定初始时的动画状态
            ((ImageView) view).setImageResource(R.mipmap.ngt_15);
        }
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view instanceof ImageView) {//指定初始时的动画状态
            ((ImageView) view).setImageResource(R.mipmap.ngt_1);
        }
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        if (startValues != null) {
            View view = startValues.view;
            if (view != null && view instanceof ImageView) {

                ValueAnimator valueAnimator = ValueAnimator.ofInt(15, 0);
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        if (animatedValue == 1) {
                            ((ImageView) view).setImageResource(R.mipmap.ngt_1);
                        } else if (animatedValue == 2) {
                            ((ImageView) view).setImageResource(R.mipmap.ngt_2);
                        } else if (animatedValue == 3) {
                            ((ImageView) view).setImageResource(R.mipmap.ngt_3);
                        } else if (animatedValue == 4) {
                            ((ImageView) view).setImageResource(R.mipmap.ngt_4);
                        } else if (animatedValue == 5) {
                            ((ImageView) view).setImageResource(R.mipmap.ngt_5);
                        } else if (animatedValue == 6) {
                            ((ImageView) view).setImageResource(R.mipmap.ngt_6);
                        } else if (animatedValue == 7) {
                            ((ImageView) view).setImageResource(R.mipmap.ngt_7);
                        } else if (animatedValue == 8) {
                            ((ImageView) view).setImageResource(R.mipmap.ngt_8);
                        } else if (animatedValue == 9) {
                            ((ImageView) view).setImageResource(R.mipmap.ngt_9);
                        } else if (animatedValue == 10) {
                            ((ImageView) view).setImageResource(R.mipmap.ngt_10);
                        } else if (animatedValue == 11) {
                            ((ImageView) view).setImageResource(R.mipmap.ngt_11);
                        } else if (animatedValue == 12) {
                            ((ImageView) view).setImageResource(R.mipmap.ngt_12);
                        } else if (animatedValue == 13) {
                            ((ImageView) view).setImageResource(R.mipmap.ngt_13);
                        } else if (animatedValue == 14) {
                            ((ImageView) view).setImageResource(R.mipmap.ngt_14);
                        } else if (animatedValue == 15) {
                            ((ImageView) view).setImageResource(R.mipmap.ngt_15);
                        }
                    }
                });
                return valueAnimator;
            }
        }
        return super.createAnimator(sceneRoot, startValues, endValues);
    }
}
