package com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-08-29
 */
public class LinearGradientTextView extends AppCompatTextView {

    private static final String TAG = "LinearGradientTextView";
    private Paint paint;

    private LinearGradient linearGradient;

    private float viewHeight = 0;
    private float viewWidth = 0;

    public LinearGradientTextView(Context context) {
        this(context, null);
    }

    public LinearGradientTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    private void init() {
        paint = getPaint();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    /**
     * 竖直渐变
     *
     * @param startColor
     * @param endColor
     * @param top2Bottom true
     */
    public void verticalGradient(final int startColor, final int endColor, final boolean top2Bottom) {
        verticalGradient(startColor, endColor, top2Bottom, TileMode.CLAMP);
    }

    /**
     * 竖直渐变
     *
     * @param startColor
     * @param endColor
     * @param top2Bottom true
     */
    public void verticalGradient(final int startColor, final int endColor, final boolean top2Bottom, final TileMode mode) {
        post(new Runnable() {
            @Override
            public void run() {
                if (top2Bottom) {
                    linearGradient = new LinearGradient(0, 0, 0, viewHeight, startColor, endColor, mode);
                } else {
                    linearGradient = new LinearGradient(viewHeight, 0, 0, 0, startColor, endColor, mode);
                }
                paint.setShader(linearGradient);
                postInvalidate();
            }
        });
    }


    /**
     * 水平渐变
     *
     * @param startColor
     * @param endColor
     * @param left2Right true:左到右渐变 false:右到左渐变
     */
    public void horizontalGradient(final int startColor, final int endColor, final boolean left2Right) {
        horizontalGradient(startColor, endColor, left2Right, TileMode.CLAMP);
    }

    /**
     * 水平渐变
     *
     * @param startColor
     * @param endColor
     * @param left2Right true:左到右渐变 false:右到左渐变
     */
    public void horizontalGradient(final int startColor, final int endColor, final boolean left2Right, final TileMode mode) {
        post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "horizontalGradient: " + getWidth());
                Log.e(TAG, "horizontalGradient: " + viewWidth);
                if (left2Right) {
                    linearGradient = new LinearGradient(0, 0, viewWidth, 0, startColor, endColor, mode);
                } else {
                    linearGradient = new LinearGradient(viewWidth, 0, 0, 0, startColor, endColor, mode);
                }
                paint.setShader(linearGradient);
                postInvalidate();
            }
        });
    }

    /**
     * 为了支持多方向的渐变，参数同{@link LinearGradient#LinearGradient(float, float, float, float, int, int, TileMode)}
     *
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param startColor
     * @param endColor
     */
    public void setGradientColor(int startX, int startY, int endX, int endY, int startColor, int endColor) {
        setGradientColor(startX, startY, endX, endY, startColor, endColor, TileMode.CLAMP);
    }

    /**
     * 为了支持多方向的渐变，参数同{@link LinearGradient#LinearGradient(float, float, float, float, int, int, TileMode)}
     *
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @param startColor
     * @param endColor
     */
    public void setGradientColor(int startX, int startY, int endX, int endY, int startColor, int endColor, TileMode mode) {
        linearGradient = new LinearGradient(startX, startY, endX, endY, startColor, endColor, mode);
        paint.setShader(linearGradient);
        postInvalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewHeight = h;
        viewWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG, "onDraw: " + viewWidth);
        super.onDraw(canvas);
    }
}
