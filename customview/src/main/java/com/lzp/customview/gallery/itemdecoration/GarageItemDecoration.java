package com.lzp.customview.gallery.itemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.utils.PhoneUtils;
import com.utils.SizeUtils;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-25
 */
public class GarageItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "GarageItemDecoration";

    private int pagerOffset;
    private int diverWidth;


    public GarageItemDecoration(Context context) {
        pagerOffset = (PhoneUtils.getWinWide(context) - SizeUtils.dip2px(context, 200)) / 2;
        diverWidth = SizeUtils.dip2px(context, 15);
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
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
        outRect.bottom = 0;
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
