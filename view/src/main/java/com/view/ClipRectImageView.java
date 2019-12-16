package com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * @describe 裁切的矩形ImageView
 * @author: lixiaopeng
 * @Date: 2019-12-16
 */
public class ClipRectImageView extends AppCompatImageView {

    private Rect rect;
    private boolean needClip = false;

    public ClipRectImageView(Context context) {
        this(context, null);
    }

    public ClipRectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        rect = new Rect();
    }

    public void setNeedClip(boolean needClip) {
        this.needClip = needClip;
        invalidate();
    }

    public void setClipEndX(int endX){
        post(new Runnable() {
            @Override
            public void run() {
                needClip = true;
                rect.set(0, 0, endX, getMeasuredHeight());
                invalidate();
            }
        });
    }
    public void setClipRect(int left, int top, int right, int bottom) {
        needClip = true;
        rect.set(left, top, right, bottom);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (needClip){
            canvas.clipRect(rect);
        }
        super.onDraw(canvas);
    }
}
