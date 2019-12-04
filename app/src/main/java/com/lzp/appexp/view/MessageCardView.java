package com.lzp.appexp.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzp.appexp.R;
import com.utils.SizeUtils;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-11-13
 */
public class MessageCardView extends FrameLayout implements OnClickListener {

    private static final String TAG = "MessageCardView";
    //裁剪的path
    private Path mPath;
    //画出来的path
    private Path mDrawPath;
    private Paint mPaint;
    private RectF mRectF;
    private float mRadius = 0;

    private int viewWidth = 0;
    private int viewHeight = 0;

    private int currentStartX = 0;
    private int currentEndX = 0;
    private int currentStartY = 0;
    private int currentEndY = 0;

    private AnimatorSet showAnimatorSet;
    private AnimatorSet dismissAnimatorSet;
    private ValueAnimator showAnimator;
    private ValueAnimator dismissAnimator;

    private ValueAnimator showColorAnimator;
    private ValueAnimator dismissColrAnimator;

    private ValueAnimator showShakeAnimator;
    private ValueAnimator dismissShakeAnimator;

    private boolean isAnimating = false;

    private int dp1;

    //高/宽
    private float whRate = 0;
    /****view****/
    private ImageView ivBell;
    private TextView tvMessageContent;
    private TextView tvMessageDate;
    private TextView tvHind;

    public MessageCardView(@NonNull Context context) {
        this(context, null);
    }

    public MessageCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.msg_card_view, this);

        setBackgroundResource(R.drawable.rect_gradient_ffa240_ff8c49_r4);
        dp1 = SizeUtils.dip2px(context, 1);

        mPath = new Path();
        mDrawPath = new Path();
        mRectF = new RectF();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(SizeUtils.dip2px(context, 2));
        mPaint.setStyle(Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#d70000"));

        ivBell = findViewById(R.id.ivBell);
        tvMessageContent = findViewById(R.id.tvMessageContent);
        tvMessageDate = findViewById(R.id.tvMessageDate);
        tvHind = findViewById(R.id.tvHind);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        setOnClickListener(this);
        tvHind.setOnClickListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setOnClickListener(null);
        if (showAnimatorSet != null && showAnimatorSet.isStarted()) {
            showAnimatorSet.cancel();
        }
        if (dismissAnimatorSet != null && dismissAnimatorSet.isStarted()) {
            dismissAnimatorSet.cancel();
        }
        tvHind.setOnClickListener(null);
    }


    private Path genPath() {
        mPath.reset();
        // 计算要显示的矩形区域
        mRectF.set(currentStartX, currentStartY, currentEndX, currentEndY);
        mPath.addRoundRect(mRectF, mRadius, mRadius, Path.Direction.CW);
        return mPath;
    }

    /**
     * 显示
     */
    public void show() {
        currentStartX = 0;
        currentStartY = 0;
        currentEndX = viewWidth;
        currentEndY = viewHeight;
        if (getVisibility() != View.VISIBLE) {
            setVisibility(VISIBLE);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (viewWidth == 0) {
            viewWidth = w;
            viewHeight = h;
            whRate = (float) viewHeight / viewWidth * 1f;
        }
    }

    /**
     * 显示并伴随动画
     */
    public void showWithAnimation() {
        if (getVisibility() != View.VISIBLE) {
            setVisibility(VISIBLE);
        }
        if (isAnimating) {
            return;
        }
        if (showAnimatorSet == null) {
            showAnimatorSet = new AnimatorSet();
        }

        //缩放动画
        if (showAnimator == null) {
            showAnimator = ValueAnimator.ofInt(0, 500);
            showAnimator.setDuration(500);
            showAnimator.setInterpolator(new LinearInterpolator());
            showAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    isAnimating = false;
                    super.onAnimationEnd(animation);
                }

                @Override
                public void onAnimationStart(Animator animation) {
                    isAnimating = true;
                    super.onAnimationStart(animation);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    isAnimating = false;
                    super.onAnimationCancel(animation);
                }
            });

            showAnimator.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedFraction = animation.getAnimatedFraction();
                    int startR = dp1 * 16;
                    // 从宽度8到viewWidth的过程

                    //要增加的宽度
                    int dx = (int) ((viewWidth - startR) * animatedFraction);
                    //如果增加的宽度+原始宽度小于viewHeight
                    if (dx + startR <= viewHeight) {
                        currentStartX = viewWidth - (dx + startR);
                        currentEndX = viewWidth;
                        currentStartY = 0;
                        currentEndY = dx + startR;

                        mRadius = (dx + startR) / 2;
                    } else {
                        currentStartX = viewWidth - (dx + startR);
                        currentEndX = viewWidth;
                        currentEndY = viewHeight;
                        //mRadius到4dp
                        if (mRadius > dp1 * 4) {
                            mRadius = viewHeight / 2 - viewHeight / 2 * (animatedFraction - whRate) / (1 - whRate);
                        }
                    }

                    postInvalidate();
                }
            });
        }

        //铃铛颜色渐变动画
        if (showColorAnimator == null) {
            if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                showColorAnimator = ValueAnimator.ofArgb(0xff7c828c, 0xffffffff);
                showColorAnimator.setInterpolator(new LinearInterpolator());
                showColorAnimator.setDuration(200);
                showColorAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        Drawable drawable = ivBell.getDrawable();
                        if (drawable != null) {
                            drawable.setColorFilter(animatedValue, Mode.SRC_IN);
                        }
                    }
                });
            }
        }

        //抖动动画
        if (showShakeAnimator == null) {
            showShakeAnimator = ValueAnimator.ofInt(0, 32, -26, 14, 0);
            showShakeAnimator.setInterpolator(new LinearInterpolator());
            showShakeAnimator.setDuration(200);
            showShakeAnimator.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int animatedValue = (int) animation.getAnimatedValue();
                    ivBell.setRotation(animatedValue);
                }
            });
        }
        showAnimatorSet.play(showShakeAnimator).after(200);
        showAnimatorSet.play(showColorAnimator).after(200);
        showAnimatorSet.play(showAnimator);
        showAnimatorSet.start();
    }

    public void hide() {
        isAnimating = false;
        if (showAnimatorSet != null && showAnimatorSet.isStarted()) {
            showAnimatorSet.cancel();
        }
        if (dismissAnimatorSet != null && dismissAnimatorSet.isStarted()) {
            dismissAnimatorSet.cancel();
        }
        if (getVisibility() == View.VISIBLE) {
            setVisibility(INVISIBLE);

        }
    }

    public boolean isShowing() {
        return getVisibility() == VISIBLE;
    }

    /**
     * 隐藏
     */
    public void dismiss(boolean withAnim) {
        if (!withAnim) {
            hide();
            return;
        }

        if (isAnimating) {
            return;
        }
        if (dismissAnimatorSet == null) {
            dismissAnimatorSet = new AnimatorSet();
        }
        if (dismissAnimator == null) {
            dismissAnimator = ValueAnimator.ofInt(0, 500);
            dismissAnimator.setDuration(500);
            dismissAnimator.setInterpolator(new LinearInterpolator());
            dismissAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    isAnimating = false;
                    if (getVisibility() == View.VISIBLE) {
                        setVisibility(INVISIBLE);
                    }
                }

                @Override
                public void onAnimationStart(Animator animation, boolean isReverse) {
                    isAnimating = true;
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    isAnimating = false;
                    if (getVisibility() == View.VISIBLE) {
                        setVisibility(INVISIBLE);
                    }
                    super.onAnimationCancel(animation);
                }
            });

            dismissAnimator.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float animatedFraction = animation.getAnimatedFraction();
                    int endR = dp1 * 16;
                    // 从宽度8到viewWidth的过程

                    //要减少的宽度
                    int dx = (int) ((viewWidth - endR) * animatedFraction);
                    //减少后的宽度小于viewHeight
                    if (viewWidth - dx < viewHeight) {
                        if (viewHeight - (viewWidth - dx) > endR) {//已经缩小到圆的大小，直接消失
                            currentStartX = dx;
                            currentEndX = viewWidth;
                            currentStartY = 0;
                            currentEndY = viewWidth - currentStartX;

                            mRadius = (viewWidth - dx) / 2;
                        }
                    } else {
                        currentStartX = dx;
                        currentEndX = viewWidth;
                        //4dp到viewHeight/2
                        if (mRadius < viewHeight / 2) {
                            mRadius = viewHeight / 2 * animatedFraction / whRate;
                        }
                        Log.e(TAG, "onAnimationUpdate: " + (viewHeight / 2) + "*" + animatedFraction + "/" + whRate + "=" + mRadius);
                    }

                    postInvalidate();
                }
            });
        }

        if (dismissColrAnimator == null) {
            if (VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
                dismissColrAnimator = ValueAnimator.ofArgb(0xffffffff, 0xff7c828c);
                dismissColrAnimator.setInterpolator(new LinearInterpolator());
                dismissColrAnimator.setDuration(200);
                dismissColrAnimator.addUpdateListener(new AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        Drawable drawable = ivBell.getDrawable();
                        if (drawable != null) {
                            drawable.setColorFilter(animatedValue, Mode.SRC_IN);
                        }
                    }
                });
            }
        }

        //抖动动画
        if (dismissShakeAnimator == null) {
            dismissShakeAnimator = ValueAnimator.ofInt(0, 32, -26, 14, 0);
            dismissShakeAnimator.setInterpolator(new LinearInterpolator());
            dismissShakeAnimator.setDuration(200);
            dismissShakeAnimator.addUpdateListener(new AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int animatedValue = (int) animation.getAnimatedValue();
                    ivBell.setRotation(animatedValue);
                }
            });
        }
        dismissAnimatorSet.play(dismissShakeAnimator).after(200);
        dismissAnimatorSet.play(dismissColrAnimator).after(200);
        dismissAnimatorSet.play(dismissAnimator);
        dismissAnimatorSet.start();
    }

    /******************以下为系统方法，无须关注**********************/

    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {
        canvas.clipPath(genPath());
        super.draw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.clipPath(genPath());
        super.dispatchDraw(canvas);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvHind) {
            dismiss(true);
        }
    }

}
