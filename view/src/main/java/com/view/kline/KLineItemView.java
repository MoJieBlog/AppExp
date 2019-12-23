package com.view.kline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.utils.PhoneUtils;
import com.utils.SizeUtils;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-08
 */
public class KLineItemView extends View {

    private static final String TAG = "KLineItemView";
    //KLin线的
    private Paint paint;
    private Path path;

    private Point prePoint;
    private Point currentPoint;

    private int itemHeight;
    private int itemWidth;

    private float lineHeight;


    public KLineItemView(Context context) {
        this(context, null);
    }

    public KLineItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        lineHeight = SizeUtils.dip2px(getContext(), 100);
        path = new Path();
        initPaint();

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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((PhoneUtils.getWinWide(getContext()) - SizeUtils.dip2px(getContext(), 52f)) / 60,
                MeasureSpec.getSize(heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (h != 0) {
            itemHeight = h;
            itemWidth = w;
        }
    }

    private void computePath() {
        if (currentPoint == null) return;
        if (prePoint != null) {
            path.moveTo(0, computeY(prePoint.getValue()));
            path.cubicTo(itemWidth / 2, computeY(prePoint.getValue()), itemWidth / 2, computeY(currentPoint.getValue()), itemWidth, computeY(currentPoint.getValue()));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }

    private float computeY(float value) {
        return lineHeight - lineHeight * value / 4;
    }

    public void refreshData(Point prePoint, Point currentPoint) {
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
}