package com.view.gallery.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.util.Log;
import android.view.View;

import com.utils.PhoneUtils;
import com.utils.SizeUtils;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-25
 */
public class GarageItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "GarageItemDecoration";

    private Context context;
    private int pagerOffset;
    private int diverWidth;

    //indicator相关
    private int indicatorTopMargin;
    private int indicatorHeight;
    private int indicatorWidth;
    /****选中*****/
    private Paint selectedPaint;
    private int seletedWidth;//选中的宽度
    private int selectedColor = 0xffe60012;
    /****未选中*****/
    private Paint unSelectedPaint;
    private int unselectedWidth;//未选中的宽度
    private int unselectedColor = 0xffe60012;
    //indicator间隔
    private int indicatorGap;
    //其他参数
    private boolean showIndicator = true;
    private boolean isSelected = false;
    private int selectedPosition = 0;

    public GarageItemDecoration(Context context) {
        this.context = context;
        pagerOffset = (PhoneUtils.getWinWide(context) - SizeUtils.dip2px(context, 200)) / 2;
        diverWidth = SizeUtils.dip2px(context, 5);

        //indicator相关
        if (showIndicator) {
            initIndicator();
        }
    }

    private void initIndicator() {
        indicatorHeight = SizeUtils.dip2px(context, 5);
        indicatorTopMargin = SizeUtils.dip2px(context, 10);
        indicatorWidth = SizeUtils.dip2px(context, 15);
        seletedWidth = SizeUtils.dip2px(context, 15);
        unselectedWidth = SizeUtils.dip2px(context, 5);
        indicatorGap = SizeUtils.dip2px(context, 5);
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

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
        super.getItemOffsets(outRect, view, parent, state);

        LayoutManager layoutManager = parent.getLayoutManager();
        int childCount = parent.getAdapter().getItemCount();

        int position = layoutManager.getPosition(view);
        if (position == 0) {
            outRect.left = pagerOffset;
            outRect.right = diverWidth;
        } else if (position == childCount - 1) {
            outRect.right = pagerOffset;
            outRect.left = diverWidth;
        } else {
            outRect.left = diverWidth;
            outRect.right = diverWidth;
        }
        if (showIndicator) {
            outRect.bottom = indicatorTopMargin + indicatorHeight;
        }else{
            outRect.bottom = 0;
        }
    }


    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull State state) {
        super.onDrawOver(c, parent, state);
        Adapter adapter = parent.getAdapter();
        if (!showIndicator) {
            return;
        }
        if (adapter == null) {
            return;
        }
        int itemCount = adapter.getItemCount();
        int totleWidth = itemCount * indicatorWidth;
        float startX = (parent.getWidth() - totleWidth) / 2f;

        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            int firstCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
            if (selectedPosition != firstCompletelyVisibleItemPosition && firstCompletelyVisibleItemPosition != -1) {
                selectedPosition = firstCompletelyVisibleItemPosition;
            }
        }

        View visibleChild = layoutManager.findViewByPosition(selectedPosition);

        int left = visibleChild.getLeft();
        int width = visibleChild.getWidth();
        float progress = (left - pagerOffset) * -1 / (float) width;
        Log.e(TAG, "onDrawOver: " + progress);

        //画indicator
        for (int i = 0; i < itemCount; i++) {
            isSelected = (selectedPosition == i);
            RectF rectF;
            if (isSelected) {
                float start = startX + i * indicatorWidth + (indicatorWidth - Math.max(unselectedWidth, seletedWidth * (1 - Math.abs(progress)))) / 2 + i * indicatorGap;
                rectF = new RectF(start, parent.getBottom() - indicatorHeight, start + Math.max(unselectedWidth, seletedWidth * (1 - Math.abs(progress))), parent.getBottom());
            } else if (progress < 0 && i == selectedPosition - 1) {//向左
                float start = startX + i * indicatorWidth + (indicatorWidth - Math.max(unselectedWidth, seletedWidth * Math.abs(progress))) / 2 + i * indicatorGap;
                rectF = new RectF(start, parent.getBottom() - indicatorHeight, start + Math.max(unselectedWidth, seletedWidth * Math.abs(progress)), parent.getBottom());
            } else if (progress > 0 && selectedPosition + 1 == i) {//向右
                float start = startX + i * indicatorWidth + (indicatorWidth - Math.max(unselectedWidth, seletedWidth * Math.abs(progress))) / 2 + i * indicatorGap;
                rectF = new RectF(start, parent.getBottom() - indicatorHeight, start + Math.max(unselectedWidth, seletedWidth * Math.abs(progress)), parent.getBottom());
            } else {
                float start = startX + i * indicatorWidth + (indicatorWidth - unselectedWidth) / 2 + i * indicatorGap;
                rectF = new RectF(start, parent.getBottom() - indicatorHeight, start + unselectedWidth, parent.getBottom());
            }
            c.drawRoundRect(rectF, indicatorHeight / 2, indicatorHeight / 2, isSelected ? selectedPaint : unSelectedPaint);
        }
    }


    public void setIndicatorVisible(boolean showIndicator) {
        if (!this.showIndicator && showIndicator) {
            initIndicator();
        }
        this.showIndicator = showIndicator;
    }

    /**
     * 获取分割线宽度
     *
     * @return
     */
    public int getDiverWidth() {
        return diverWidth;
    }

    /**
     * 设置选中的pager距离屏幕左右两侧的距离
     *
     * @param pagerOffset
     */
    public void setPagerOffset(int pagerOffset) {
        this.pagerOffset = pagerOffset;
    }

    /**
     * 设置pager之间的分割线宽度
     *
     * @param diverWidth
     */
    public void setDiverWidth(int diverWidth) {
        this.diverWidth = diverWidth;
    }
}
