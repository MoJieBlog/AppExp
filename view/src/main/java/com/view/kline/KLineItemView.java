package com.view.kline;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.utils.SizeUtils;
import com.view.R;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-08
 */
public class KLineItemView extends View {

    //KLin线的
    private Paint paint;
    private Path path;

    private Point prePoint;
    private Point currentPoint;

    private int itemHeight;
    private int itemWidth;

    private float lineHeight;

    private boolean isLast = false;//是否为最后一个点

    private int endImgSize;
    private Bitmap endbmp;

    public KLineItemView(Context context) {
        this(context, null);
    }

    public KLineItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        lineHeight = SizeUtils.dip2px(getContext(), 100);
        path = new Path();
        initPaint();
        endbmp = BitmapFactory.decodeResource(getResources(), R.mipmap.kline_end);
        endImgSize = endbmp.getWidth();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Style.STROKE);
        paint.setColor(0xff5ef9de);
        paint.setStrokeCap(Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != 0 && h != 0) {
            itemHeight = h;
            itemWidth = w;
        }
    }

    private void computePath() {
        if (currentPoint == null) return;
        if (prePoint == null) {
            prePoint = currentPoint;
        }

        path.moveTo(0, computeY(prePoint.getValue()));
        if (isLast){
            path.cubicTo(itemWidth / 2, computeY(prePoint.getValue()), itemWidth / 2, computeY(currentPoint.getValue()), itemWidth - endImgSize /2, computeY(currentPoint.getValue()));
        }else{
            path.cubicTo(itemWidth / 2, computeY(prePoint.getValue()), itemWidth / 2, computeY(currentPoint.getValue()), itemWidth, computeY(currentPoint.getValue()));
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
        if (isLast){
            canvas.drawBitmap(endbmp,itemWidth- endImgSize,computeY(currentPoint.getValue())- endImgSize/2,paint);
        }
    }

    private float computeY(float value) {
        return lineHeight - lineHeight * value / 4;
    }

    public void refreshData(Point prePoint,Point currentPoint){
        this.path.reset();
        this.prePoint = prePoint;
        this.currentPoint = currentPoint;
        post(new Runnable() {
            @Override
            public void run() {
                computePath();
                postInvalidate();
            }
        });
    }

    public void setIsLast(boolean isLast) {
        this.isLast = isLast;
        postInvalidate();
    }
}