package com.animation;

import android.animation.Animator;
import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;
import android.transition.TransitionValues;
import android.transition.Visibility;
import android.view.View;
import android.view.ViewGroup;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-18
 */
@RequiresApi(api = VERSION_CODES.KITKAT)
public class ActivityTransformAnimation extends Visibility {

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
    }

    @Override
    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        return super.onAppear(sceneRoot, view, startValues, endValues);
    }

    @Override
    public Animator onAppear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        return super.onAppear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        return super.onDisappear(sceneRoot, view, startValues, endValues);
    }

    @Override
    public Animator onDisappear(ViewGroup sceneRoot, TransitionValues startValues, int startVisibility, TransitionValues endValues, int endVisibility) {
        return super.onDisappear(sceneRoot, startValues, startVisibility, endValues, endVisibility);
    }
}
