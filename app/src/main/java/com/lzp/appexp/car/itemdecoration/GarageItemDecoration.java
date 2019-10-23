package com.lzp.appexp.car.itemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
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
 * @Date: 2019-10-23
 */
public class GarageItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "GarageItemDecoration";
    private int gap;
    public GarageItemDecoration(Context context) {
        gap = (PhoneUtils.getWinWide(context)-SizeUtils.dip2px(context,200))/2;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
        super.getItemOffsets(outRect, view, parent, state);

        LayoutManager layoutManager = parent.getLayoutManager();
        int childCount = parent.getAdapter().getItemCount();

        int position = layoutManager.getPosition(view);
        Log.e(TAG, "getItemOffsets: "+position+"  "+childCount);
        if (position==0){
            outRect.left = gap;
            outRect.right =0;
        }else if(position==childCount-1){
            outRect.right =gap;
            outRect.left = 0;
        }else{
            outRect.left = 0;
            outRect.right =0;
        }
    }
}
