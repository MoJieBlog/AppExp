package com.view.refresh.ext.moveopen;

import android.content.Context;
import android.util.AttributeSet;

import com.utils.SizeUtils;
import com.view.refresh.LoadingLayout;
import com.view.refresh.SwipeRefreshLayout;
import com.view.refresh.ext.DefaultRefreshLayout;

/**
 * @describe 下拉 大于freshSize 刷新，大于OpenSize 打开新页面
 * @author: lixiaopeng
 * @Date: 2019-10-30
 */
public class MoveOpenAndRefreshLayout extends SwipeRefreshLayout {

    public final int OPEN_ACTIVITY_SIZE;
    public final int REFRESH_SIZE;

    public MoveOpenAndRefreshLayout(Context context) {
        this(context, null);
    }

    public MoveOpenAndRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        OPEN_ACTIVITY_SIZE = SizeUtils.dip2px(context, 150);
        REFRESH_SIZE = SizeUtils.dip2px(context, 60);
    }


    @Override
    protected LoadingLayout createLoadingLayout() {
        if (freshViewType==1){
            if (headLoadingLayout == null) {
                headLoadingLayout = new MoveOpenRefreshLayout(getContext());
                headLoadingLayout.setRefreshLayoutInstance(this);
            }
        }else{
            if (headLoadingLayout == null) {
                headLoadingLayout = new DefaultRefreshLayout(getContext());
                headLoadingLayout.setRefreshLayoutInstance(this);
            }
        }
        return headLoadingLayout;
    }

    @Override
    protected void finishMove() {
        if (-getScrollY() > OPEN_ACTIVITY_SIZE) {
            if (mRefreshListener != null && mRefreshListener instanceof OpenRefreshListener) {
                ((OpenRefreshListener) mRefreshListener).openActivity();
            }
        } else if (-getScrollY() >= REFRESH_SIZE
                && mRefreshListener != null
                && !isRefreshing) {
            isRefreshing = true;
            mRefreshListener.onRefresh();
            headLoadingLayout.onRefreshing();
        }

        //用户刷新中，又滑动打开新界面
        if(isRefreshing){
            headLoadingLayout.onRefreshing();
        }
        resetHeader();
    }

    public void setOnRefreshListener(OpenRefreshListener listener){
        this.mRefreshListener = listener;
    }

    public interface OpenRefreshListener extends OnRefreshListener {
        void openActivity();
    }
}
