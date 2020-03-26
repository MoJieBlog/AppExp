package com.base;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.base.BaseActivity;
import com.base.R;
import com.utils.PhoneUtils;
import com.utils.SizeUtils;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-12-09
 */
public class ToastBaseActivity extends BaseActivity {

    private View toastView;
    private TextView tvToast;
    private int toastViewHeight = 0;

    private ValueAnimator showAnimation;
    private ValueAnimator dismissAnimation;

    private boolean isShowToast = false;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        buildToastView(R.layout.toast_layout);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        buildToastView(R.layout.toast_layout);
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        super.setContentView(view, params);
        buildToastView(R.layout.toast_layout);
    }

    private void buildToastView(int layoutRes) {
        toastViewHeight = (int) (SizeUtils.dip2px(this, 45) + PhoneUtils.getStatusBarHeight(this));
        if (toastView == null) {
            ViewGroup viewById = findViewById(android.R.id.content);
            toastView = getLayoutInflater().inflate(layoutRes, null);
            tvToast = toastView.findViewById(R.id.tvToast);
            MarginLayoutParams lp = new MarginLayoutParams(LayoutParams.MATCH_PARENT, toastViewHeight);
            toastView.setLayoutParams(lp);
            viewById.addView(toastView);
        }

        toastView.setTranslationY(-toastViewHeight);
    }

    public void showToast(String msg) {
        if (tvToast!=null){
            tvToast.setText(msg);
        }
        isShowToast = true;
        if (showAnimation == null) {
            showAnimation = ValueAnimator.ofInt(-toastViewHeight, 0);
            showAnimation.setDuration(200);
            showAnimation.setInterpolator(new DecelerateInterpolator());
            showAnimation.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int animatedValue = (int) animation.getAnimatedValue();
                    if (toastView != null) {
                        toastView.setTranslationY(animatedValue);
                    }
                }
            });

            showAnimation.addListener(new AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (isShowToast){
                        toastView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                hindToast();
                            }
                        },1000);
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }



        showAnimation.start();
    }

    public void hindToast() {
        isShowToast = false;
        if (dismissAnimation == null) {
            dismissAnimation = ValueAnimator.ofInt(0, -toastViewHeight);
            dismissAnimation.setDuration(200);
            showAnimation.setInterpolator(new DecelerateInterpolator());
            dismissAnimation.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int animatedValue = (int) animation.getAnimatedValue();
                    if (toastView != null) {
                        toastView.setTranslationY(animatedValue);
                    }
                }
            });

            dismissAnimation.addListener(new AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        dismissAnimation.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (showAnimation != null && showAnimation.isRunning()) {
            showAnimation.cancel();
        }

        if (dismissAnimation != null && dismissAnimation.isRunning()) {
            dismissAnimation.cancel();
        }
    }
}
