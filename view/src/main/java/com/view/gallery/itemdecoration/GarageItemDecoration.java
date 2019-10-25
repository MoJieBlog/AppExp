package com.view.gallery.itemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
public class GarageItemDecoration  extends RecyclerView.ItemDecoration {

    private static final String TAG = "GarageItemDecoration";
    private int pagerOffset;
    private int diverWidth;
    public GarageItemDecoration(Context context) {
        pagerOffset = (PhoneUtils.getWinWide(context)- SizeUtils.dip2px(context,200))/2;
        diverWidth = SizeUtils.dip2px(context,5);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
        super.getItemOffsets(outRect, view, parent, state);

        LayoutManager layoutManager = parent.getLayoutManager();
        int childCount = parent.getAdapter().getItemCount();

        int position = layoutManager.getPosition(view);
        Log.e(TAG, "getItemOffsets: "+position+"  "+childCount);
        if (position==0){
            outRect.left = pagerOffset;
            outRect.right =diverWidth;
        }else if(position==childCount-1){
            outRect.right =pagerOffset;
            outRect.left = diverWidth;
        }else{
            outRect.left = diverWidth;
            outRect.right =diverWidth;
        }
    }

    public int getDiverWidth(){
        return diverWidth;
    }
    /**
     * 设置选中的pager距离屏幕左右两侧的距离
     * @param pagerOffset
     */
    public void setPagerOffset(int pagerOffset) {
        this.pagerOffset = pagerOffset;
    }

    /**
     * 设置
     * @param diverWidth
     */
    public void setDiverWidth(int diverWidth){
        this.diverWidth = diverWidth;
    }
}
