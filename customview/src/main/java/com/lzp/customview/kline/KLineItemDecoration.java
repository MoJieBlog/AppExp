package com.lzp.customview.kline;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lzp.customview.R;
import com.utils.DateTimeUtils;
import com.utils.SizeUtils;
import java.util.ArrayList;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-08
 */
public class KLineItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "KLineItemDecoration";
    private TextPaint textPaint;
    private Paint linePaint;
    private int textHeight;//显示底部文字的高度
    private ArrayList<Point> points = new ArrayList<>();

    private float lineHeight;

    private int textWidth;

    private int endImgSize;
    private Bitmap endImg;

    public KLineItemDecoration(Context context) {
        textPaint = new TextPaint();
        textPaint.setTextSize(45);
        textPaint.setColor(Color.WHITE);
        textHeight = SizeUtils.dip2px(context, 24);
        linePaint = new Paint();
        linePaint.setColor(0xff95a4b3);
        linePaint.setStyle(Style.FILL_AND_STROKE);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(1);

        lineHeight = SizeUtils.dip2px(context, 100);
        endImg = BitmapFactory.decodeResource(context.getResources(), R.mipmap.kline_end);
        endImgSize = endImg.getWidth();
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = textHeight;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (points.size()>0&&layoutManager.getPosition(view)==points.size()-1){
            Log.e(TAG, "getItemOffsets: ");
            outRect.right = endImgSize/2;
        }else{
            outRect.right = 0;
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (i != 0) {
                View child = parent.getChildAt(i);
                RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
                if (layoutManager == null) {
                    throw new IllegalArgumentException("layoutManager must not be null.");
                }

                int position = layoutManager.getPosition(child);
                Point point = points.get(position);
                if (point.isDisconnect()) {
                    //画分割线
                    c.drawLine(child.getRight() - 1, child.getTop(), child.getRight() + 1, child.getBottom(), linePaint);
                }

                //画日期
                if (point.isDisconnect()) {
                    String title = DateTimeUtils.timeLong2timeStr(String.valueOf(point.getDate()), "HH:mm:ss");
                    Rect rect = new Rect();
                    textPaint.getTextBounds(title, 0, title.length(), rect);
                    textWidth = rect.right - rect.left;
                    if (point.isShowDate()) {
                        c.drawText(title, ((child.getLeft()+child.getRight()-(rect.right-rect.left)))/2, child.getBottom() + textHeight - (rect.height() / 2), textPaint);
                    }
                }

                if (position==points.size()-1){
                    Log.e(TAG, "onDrawOver: ");
                    c.drawBitmap(endImg,child.getRight()-endImgSize/2,computeY(point.getValue())-endImgSize/2,linePaint);
                }

            }
        }
    }

    private float computeY(float value) {
        return lineHeight - lineHeight * value / 4;
    }

    public void refreshData(ArrayList<Point> points) {
        this.points.clear();
        this.points.addAll(points);
    }

    public void addPoint(Point point) {
        this.points.add(point);
    }
}
