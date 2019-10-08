package com.view.kline;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.State;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;

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

    private int textWidth;

    public KLineItemDecoration(Context context) {
        textPaint = new TextPaint();
        textPaint.setTextSize(45);
        textPaint.setColor(Color.WHITE);
        textHeight = SizeUtils.dip2px(context, 24f);
        linePaint = new Paint();
        linePaint.setColor(0xff95a4b3);
        linePaint.setStyle(Style.FILL_AND_STROKE);
        linePaint.setAntiAlias(true);
        linePaint.setStrokeWidth(2);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = textHeight;
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull State state) {
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
                        c.drawText(title, child.getLeft() + rect.left / 2, child.getBottom() + textHeight - (rect.height() / 2), textPaint);
                    }
                }
            }
        }
    }

    public void refreshData(ArrayList<Point> points) {
        this.points.clear();
        this.points.addAll(points);
    }

    public void addPoint(Point point) {
        this.points.add(point);
    }
}
