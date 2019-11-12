package com.lzp.appexp.tabmanager;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

import com.utils.SizeUtils;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-11-07
 */
public class TabItemDecorator extends ItemDecoration {

    private Context context;
    private int dp60;

    public TabItemDecorator(Context context) {
        this.context = context;
        dp60 = SizeUtils.dip2px(context,60);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.top = dp60;
    }
}
