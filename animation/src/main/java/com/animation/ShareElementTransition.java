package com.animation;

import android.animation.Animator;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.ViewGroup;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-18
 */
@RequiresApi(api = VERSION_CODES.KITKAT)
public class ShareElementTransition extends Transition {

    @Override
    public void captureStartValues(TransitionValues transitionValues) {

    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {

    }

    @Override
    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {

        return super.createAnimator(sceneRoot, startValues, endValues);
    }
}
