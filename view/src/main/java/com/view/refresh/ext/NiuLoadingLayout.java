package com.view.refresh.ext;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.view.SizeUtils;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-14
 */
public class NiuLoadingLayout extends View {

    private TextPaint paint;
    private Context context;
    private int paintColor = Color.RED;

   // private String content = "\uE906";//公司字体要钱
    private String content = "GO";

    private Path textPath,//文字的path
            dstPath;//已画出部分的path

    //文本宽度
    private float mTextWidth;//文字的宽度
    private float mTextHeight;//文字的高度

    private PathMeasure pathMeasure;//path的测量类，用于操作path
    private float pathLength = 0;//整个文字path的长度

    private ValueAnimator animator;//动画，可以用其他方式代替，这里为了方便直接用动画，如果过需要跟其他UI联动可以替换

    float mCurrentLength = 0;//当前已画path的长度

    private boolean animEnd = false;

    public NiuLoadingLayout(Context context) {
        this(context, null);
    }

    public NiuLoadingLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        initPaint();
        initTextPath();
        initAnimator();
    }

    public void startAnimation(int pb){
        mCurrentLength = pathLength/100*pb;
        postInvalidate();
    }

    public void stopAnimation(){
        mCurrentLength = pathLength;
        if (animator.isRunning())animator.cancel();
        postInvalidate();
    }

    public void repeatAnimation(){
        if (animator!=null)animator.start();
    }
    private void initAnimator() {
        if (animator == null) {
            animator = ValueAnimator.ofFloat(0, pathLength);
        }
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                mCurrentLength = animatedValue;
                postInvalidate();
            }
        });

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (animListener!=null)animListener.animStart();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                animEnd = true;
                postInvalidate();
                if (animListener!=null)animListener.animFinish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    private void drawPath(Canvas canvas, float mCurrentLength) {
        canvas.save();//防止错位
        dstPath.reset();//重置已画的path
        //在中间绘制
        if (mCurrentLength>=pathLength) {
            canvas.translate(getWidth() / 2 - mTextWidth / 2, 0);
            canvas.translate(0, getHeight() / 2 - mTextHeight / 2);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawPath(textPath, paint);
        } else {
            //重置路径
            paint.setStyle(Paint.Style.STROKE);
            canvas.translate(getWidth() / 2 - mTextWidth / 2, 0);
            canvas.translate(0, getHeight() / 2 - mTextHeight / 2);
            pathMeasure.setPath(textPath, false);

            while (mCurrentLength > pathMeasure.getLength()) {//如果当前要画出的长度大于某一个轮廓的长度
                mCurrentLength = mCurrentLength - pathMeasure.getLength();//当前要画出的长度 = 当前要画出的长度 - 这个轮廓。
                pathMeasure.getSegment(0, pathMeasure.getLength(), dstPath, true);//将这个轮廓添加到 dstPath
                if (!pathMeasure.nextContour()) {//当没有下一个轮廓时跳出
                    break;
                }
            }
            //注意最后一个轮廓还没加进去
            pathMeasure.getSegment(0, mCurrentLength, dstPath, true);
            canvas.drawPath(dstPath, paint);
        }
        canvas.restore();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPath(canvas, mCurrentLength);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //处理包裹内容的情况
        int warpDefaultSize = SizeUtils.dip2px(context, 100);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            widthSize = heightSize = warpDefaultSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = warpDefaultSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = warpDefaultSize;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    private void initTextPath() {
        if (TextUtils.isEmpty(content)) {
            throw new RuntimeException("content can not be empty");
        }
        if (paint == null) {
            throw new RuntimeException("paint not init");
        }
        textPath = new Path();
        dstPath = new Path();
        pathMeasure = new PathMeasure();
        paint.getTextPath(content, 0, content.length(), 0, paint.getTextSize(), textPath);
        pathMeasure.setPath(textPath, false);
        pathLength = pathMeasure.getLength();
        //获取所有路径的总长度
        while (pathMeasure.nextContour()) {
            pathLength += pathMeasure.getLength();
        }
    }

    private void initPaint() {
        paint = new TextPaint();
        paint.setColor(paintColor);
        paint.setStrokeWidth(5);
        paint.setTextSize(150f);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);//设置画笔末端为圆形 BUTT：无线帽   ROUND：圆形线帽    SQUARE：方形线帽
        paint.setStrokeJoin(Paint.Join.ROUND);//设置画笔转弯处我圆形 MITER：锐角 ROUND：圆弧  BEVEL：斜线
        paint.setStyle(Paint.Style.STROKE);

        //paint.setTypeface(Typeface.createFromAsset(getContext().getAssets(),"niu.ttf"));

        mTextWidth = Layout.getDesiredWidth(content, paint);
        Paint.FontMetrics metrics = paint.getFontMetrics();
        mTextHeight = metrics.bottom - metrics.top;
    }

    private AnimListener animListener;

    public void setAnimListener(AnimListener animListener) {
        this.animListener = animListener;
    }

    public interface AnimListener{
        void animStart();
        void animFinish();
    }
}
