package com.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-11-13
 */
public class RoundRelativeLayout extends RelativeLayout {

    private Path mPath;
    private Paint mPaint;
    private RectF mRectF;
    private float mRadius = 0;

    public RoundRelativeLayout(@NonNull Context context) {
        this(context, null);
    }

    public RoundRelativeLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context,attrs);

        mPath = new Path();
        mRectF = new RectF();

        //Android P之后对setXfermode进行了修改
        if (Build.VERSION.SDK_INT < 28) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        }

    }

    public void setRadius(float radius) {
        mRadius = radius;
        postInvalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != 0 && h != 0) {
            mRectF.set(0, 0, w, h);
        }
    }

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

    private Path genPath() {
        mPath.reset();
        mPath.addRoundRect(mRectF, mRadius, mRadius, Path.Direction.CW);
        return mPath;
    }

}
