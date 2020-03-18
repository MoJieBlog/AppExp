package com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-08-19
 */
public class DashBoardView extends View {

    private static final String TAG = "DashBoardView";

    private final float startDegree = 145f;//开始的角度
    private final float swipDegree = 250f;//扫过的角度
    private final int radios = 250;//半径

    private int viewWidth = 0;
    private int viewHeight = 0;
    /***外圈**/
    private final float outStrokeWidth = 15;
    private int outStartColor = 0x00ffffff;//外圈渐变开始色
    private int outEndColor = 0xffffffff;//外圈渐变结束色
    private Paint outPaint;

    RectF rect;

    public DashBoardView(Context context) {
        this(context, null);
    }

    public DashBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

        viewWidth = 2 * radios;
        Log.e(TAG, "init: "+Math.sqrt(3));
        float remianDegree = (360 - swipDegree) / 2;
        Log.e(TAG, "remianDegree: "+remianDegree);
        double v = Math.toRadians(remianDegree);
        Log.e(TAG, "v: "+v);
        viewHeight = radios + (int) (radios * (Math.cos(v)));

        outPaint = new Paint();
        outPaint.setDither(true);
        outPaint.setAntiAlias(true);
        outPaint.setStyle(Style.STROKE);
        outPaint.setStrokeWidth(outStrokeWidth);
        outPaint.setStrokeCap(Cap.ROUND);
        Shader shader = new LinearGradient(viewWidth/2, viewHeight, viewWidth/2, outStrokeWidth, outStartColor, outEndColor, TileMode.MIRROR);
        outPaint.setShader(shader);

        rect = new RectF(outStrokeWidth, outStrokeWidth, 2 * radios - outStrokeWidth, 2 * radios - outStrokeWidth);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(viewWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(viewHeight, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(rect, startDegree, swipDegree, false, outPaint);
    }
}
