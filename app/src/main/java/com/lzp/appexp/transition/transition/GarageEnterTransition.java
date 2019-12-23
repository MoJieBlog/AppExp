package com.lzp.appexp.transition.transition;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.os.Build.VERSION_CODES;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import com.lzp.appexp.R;

/**
 * @describe 车库进入的动画
 * @author: lixiaopeng
 * @Date: 2019-10-18
 */
@RequiresApi(api = VERSION_CODES.KITKAT)
public class GarageEnterTransition extends Transition {

    private static final String TAG = "GarageEnterTransition";

    /**
     * 收集动画开始的信息
     *
     * @param transitionValues
     */
    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        Log.e(TAG, "captureStartValues: ");
        View view = transitionValues.view;
        if (view instanceof ImageView) {//指定初始时的动画状态
            ((ImageView) view).setImageResource(R.mipmap.ngt_1);
        }
    }

    /**
     * 收集动画结束的信息
     *
     * @param transitionValues
     */
    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view instanceof ImageView) {//指定初始时的动画状态
            ((ImageView) view).setImageResource(R.mipmap.ngt_1);
        }
    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        Log.e(TAG, "createAnimator: ");
        if (endValues != null) {
            View view = endValues.view;
            if (view != null && view instanceof ImageView) {

                ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 15);
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
