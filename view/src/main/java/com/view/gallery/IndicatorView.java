package com.view.gallery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.utils.SizeUtils;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-11-26
 */
public class IndicatorView extends View {

    private static final String TAG = "IndicatorView";
    private Context context;

    private int indicatorHeight;
    private int indicatorWidth;
    /****选中*****/
    private Paint selectedPaint;
    private int seletedWidth;//选中的宽度
    private int selectedColor = 0xffe60012;
    /****未选中*****/
    private Paint unSelectedPaint;
    private int unselectedWidth;//未选中的宽度
    private int unselectedColor = 0x80ffffff;
    //indicator间隔
    private int indicatorGap;
    //其他参数
    private boolean isSelected = false;
    private int selectedPosition = 0;

    private int itemCount = 0;
    private float progress = 0;

    private int pagerOffset = 0;

    private RectF rectF;
    private int totleWidth;
    private float startX;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        rectF = new RectF();
        initIndicator();
    }

    private void initIndicator() {
        indicatorHeight = SizeUtils.dip2px(context, 5);
        indicatorWidth = SizeUtils.dip2px(context, 15);
        seletedWidth = SizeUtils.dip2px(context, 15);
        unselectedWidth = SizeUtils.dip2px(context, 5);
        indicatorGap = SizeUtils.dip2px(context, 0);
        initPaint();
    }

    private void initPaint() {
        selectedPaint = new Paint();
        selectedPaint.setColor(selectedColor);
        selectedPaint.setAntiAlias(true);
        selectedPaint.setStyle(Style.FILL);

        unSelectedPaint = new Paint();
        unSelectedPaint.setColor(unselectedColor);
        unSelectedPaint.setAntiAlias(true);
        unSelectedPaint.setStyle(Style.FILL);
    }

    public void setPageOffset(int pagerOffset) {
        this.pagerOffset = pagerOffset;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
        totleWidth = itemCount * indicatorWidth + indicatorGap * itemCount - 1;
    }

    public void attachRecyclerView(RecyclerView recyclerView) {

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            int firstCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
            if (selectedPosition != firstCompletelyVisibleItemPosition && firstCompletelyVisibleItemPosition != -1) {
                selectedPosition = firstCompletelyVisibleItemPosition;
            }
        } else {
            return;
        }
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
                if (selectedPosition != firstCompletelyVisibleItemPosition && firstCompletelyVisibleItemPosition != -1) {
                    selectedPosition = firstCompletelyVisibleItemPosition;
                }

                View visibleChild = layoutManager.findViewByPosition(selectedPosition);
                if (visibleChild == null) {
                    return;
                }
                int left = visibleChild.getLeft();
                int width = visibleChild.getWidth();
                progress = (1f * left - 1f * pagerOffset) * -1f / (float) width;
                if (progress > 1) {
                    progress = 1;
                }

                if (progress < -1) {
                    progress = -1;
                }

                if (scrollListener != null) {
                    scrollListener.onScroll(selectedPosition, progress);
                }
                postInvalidate();
            }
        });


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        startX = (getMeasuredWidth() - totleWidth) / 2f;
        if (itemCount <= 1) {
            return;
        }
        for (int i = 0; i < itemCount; i++) {
            isSelected = (selectedPosition == i);

            int indicatorTop = 2;
            int indicatorBottom = indicatorTop + indicatorHeight;
            if (isSelected) {
                float start = startX + i * indicatorWidth + (indicatorWidth - Math.max(unselectedWidth, seletedWidth * (1 - Math.abs(progress)))) / 2 + i * indicatorGap;
                rectF.set(start, indicatorTop, start + Math.max(unselectedWidth, seletedWidth * (1 - Math.abs(progress))), indicatorBottom);
            } else if (progress < 0 && i == selectedPosition - 1) {//向左
                float start = startX + i * indicatorWidth + (indicatorWidth - Math.max(unselectedWidth, seletedWidth * Math.abs(progress))) / 2 + i * indicatorGap;
                rectF.set(start, indicatorTop, start + Math.max(unselectedWidth, seletedWidth * Math.abs(progress)), indicatorBottom);
            } else if (progress > 0 && selectedPosition + 1 == i) {//向右
                float start = startX + i * indicatorWidth + (indicatorWidth - Math.max(unselectedWidth, seletedWidth * Math.abs(progress))) / 2 + i * indicatorGap;
                rectF.set(start, indicatorTop, start + Math.max(unselectedWidth, seletedWidth * Math.abs(progress)), indicatorBottom);
            } else {
                float start = startX + i * indicatorWidth + (indicatorWidth - unselectedWidth) / 2 + i * indicatorGap;
                rectF.set(start, indicatorTop, start + unselectedWidth, indicatorBottom);
            }
            canvas.drawRoundRect(rectF, indicatorHeight / 2, indicatorHeight / 2, isSelected ? selectedPaint : unSelectedPaint);
        }
    }


    public void setIndicatorColor(@ColorInt int selectedColor, @ColorInt int unselectedColor) {
        this.selectedColor = selectedColor;
        this.unselectedColor = unselectedColor;
        selectedPaint.setColor(selectedColor);
        unSelectedPaint.setColor(unselectedColor);
        invalidate();
    }

    public void setIndicatorColor(boolean dark) {
        if (dark) {
            setIndicatorColor(Color.parseColor("#ffffff"), Color.parseColor("#80ffffff"));
        } else {
            setIndicatorColor(Color.parseColor("#ff7c828c"), Color.parseColor("#807c828c"));
        }
    }

    private MOnScrollListener scrollListener;

    public void setMScrollListener(MOnScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public interface MOnScrollListener {
        void onScroll(int position, float progress);
    }
}
