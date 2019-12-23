package com.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @describe 可以裁切的ImageView
 * @author: lixiaopeng
 * @Date: 2019-12-16
 */
public class ClipAbleImageView extends AppCompatImageView {

    private boolean needClip = false;
    private Path path;

    public ClipAbleImageView(Context context) {
        this(context, null);
    }

    public ClipAbleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
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
                path.reset();
                path.moveTo(0,0);
                path.lineTo(endX,0);
                path.lineTo(endX,getMeasuredHeight());
                path.lineTo(0,getMeasuredHeight());
                path.close();
                invalidate();
            }
        });
    }

    public void setClipRect(int left, int top, int right, int bottom) {
        needClip = true;
        path.reset();
        path.moveTo(left,top);
        path.lineTo(left,bottom);
        path.lineTo(right,bottom);
        path.lineTo(right,top);
        path.close();
        invalidate();
    }

    public void clipPath(Path path){
        needClip = true;
        this.path = path;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (needClip){
            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }
}
