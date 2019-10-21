package com.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @describe 半圆 注意View的宽高会自动根据半径设置。手动设置宽高无效
 * @author: lixiaopeng
 * @Date: 2019-08-09
 */
public class HalfCircleView extends View {

    /**
     * 圆弧方向，凸面在哪个方向
     */
    public static final int TOP = 0;
    public static final int BOTTOM = 1;
    public static final int RIGHT = 2;
    public static final int LEFT = 3;

    /***半圆的填充色**/
    private int bgColor = 0xffffff00;

    /*****半圆的半径 默认****/
    private float radius = 20;

    /******圆弧方向，凸面在哪个方向******/
    private int direction = TOP;

    private Paint paint;

    private Path path = new Path();

    private float viewWidth;
    private float viewHeight;


    private float[] rids = new float[8];

    public HalfCircleView(Context context) {
        this(context, null);
    }

    public HalfCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HalfCircleView);
        radius = typedArray.getDimension(R.styleable.HalfCircleView_radius, radius);
        bgColor = typedArray.getColor(R.styleable.HalfCircleView_bgColor, bgColor);
        direction = typedArray.getInt(R.styleable.HalfCircleView_direction, direction);
        typedArray.recycle();

        initPaint();
        initPath();
    }

    private void initPath() {
        if (direction == TOP) {
            viewWidth = 2 * radius;
            viewHeight = radius;
            rids[0] = radius;
            rids[1] = radius;
            rids[2] = radius;
            rids[3] = radius;
            rids[4] = 0;
            rids[5] = 0;
            rids[6] = 0;
            rids[7] = 0;
        } else if (direction == BOTTOM) {
            viewWidth = 2 * radius;
            viewHeight = radius;
            rids[0] = 0;
            rids[1] = 0;
            rids[2] = 0;
            rids[3] = 0;
            rids[4] = radius;
            rids[5] = radius;
            rids[6] = radius;
            rids[7] = radius;
        } else if (direction == LEFT) {
            viewWidth = radius;
            viewHeight = 2 * radius;
            rids[0] = radius;
            rids[1] = radius;
            rids[2] = 0;
            rids[3] = 0;
            rids[4] = 0;
            rids[5] = 0;
            rids[6] = radius;
            rids[7] = radius;
        } else if (direction == RIGHT) {
            viewWidth = radius;
            viewHeight = 2 * radius;
            rids[0] = 0;
            rids[1] = 0;
            rids[2] = radius;
            rids[3] = radius;
            rids[4] = radius;
            rids[5] = radius;
            rids[6] = 0;
            rids[7] = 0;
        }
        path.addRoundRect(new RectF(0, 0, viewWidth, viewHeight), rids, Direction.CW);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) viewWidth, (int) viewHeight);
    }


    private void initPaint() {
        paint = new Paint();
        paint.setColor(bgColor);
        paint.setStyle(Style.FILL);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }

    /**
     * 设置填充色
     * @param bgColor
     */
    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        paint.setColor(bgColor);
        postInvalidate();
    }

    /**
     * 设置半径，宽高会根据半径改变
     * @param radius
     */
    public void setRadius(float radius) {
        this.radius = radius;
        initPath();
        postInvalidate();
    }

    /**
     * 设置方向
     *
     * @param direction {@link HalfCircleView#TOP}
     *                  {@link HalfCircleView#RIGHT}
     *                  {@link HalfCircleView#BOTTOM}
     *                  {@link HalfCircleView#LEFT}
     */
    public void setDirection(int direction) {
        this.direction = direction;
        initPath();
        postInvalidate();
    }
}
