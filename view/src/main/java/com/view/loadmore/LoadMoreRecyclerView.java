package com.view.loadmore;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 加载更多RecyclerView
 */
public class LoadMoreRecyclerView extends RecyclerView {

    private static final String TAG = "LoadMoreRecyclerView";
    public static final int LM_LOAD_NON = 0;
    /**
     * 加载成功
     */
    public static final int LM_LOAD_SUCCESS = 1;
    /**
     * 加载失败
     */
    public static final int LM_LOAD_FAILURE = 2;
    /**
     * 加载完成
     */
    public static final int LM_LOAD_COMPLETE = 3;
    /**
     * 加载中
     */
    public static final int LM_LOADING = 4;
    /**
     * 距离底部条目个数（触发预加载）
     */

    static final int PRE_LOAD_COUNT = 0;
    private int loadMoreStatus = LM_LOAD_NON;
    private OnLoadMoreListener onLoadmoreListener;
    // 是否预加载
    private boolean canLoad = true;

    /***是否想上滚动****/
    private boolean isSlidingUpward = false;

    public LoadMoreRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    LayoutManager layoutManager = getLayoutManager();
                    int itemCount = layoutManager.getItemCount();
                    if (layoutManager instanceof LinearLayoutManager){
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                        int lastItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                        if (lastItemPosition == (itemCount - 1-PRE_LOAD_COUNT) && isSlidingUpward) {
                            //加载更多
                            onLoading();
                        }
                    }else if(layoutManager instanceof GridLayoutManager){
                        GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
                        int lastItemPosition = gridLayoutManager.findLastCompletelyVisibleItemPosition();
                        if (lastItemPosition == (itemCount - 1-PRE_LOAD_COUNT) && isSlidingUpward) {
                            //加载更多
                            onLoading();
                        }
                    }else if(layoutManager instanceof StaggeredGridLayoutManager){
                        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                        staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions()
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                isSlidingUpward = dy > 0;
            }
        });

    }

    public void onLoading() {
        Log.e(TAG, "onLoading: 开始加载更多");
        if (canLoad
                && (getLoadMoreStatus() != LoadMoreRecyclerView.LM_LOAD_COMPLETE
                && getLoadMoreStatus() != LoadMoreRecyclerView.LM_LOADING)) {
            Log.e(TAG, "onLoading: loading");
            setLoadMoreStatus(LoadMoreRecyclerView.LM_LOADING);
            getAdapter().notifyDataSetChanged();
            if (getOnLoadmoreListener() != null) {
                onLoadmoreListener.onLoadmore();
            }
        }

    }

    public void stopLoadMore() {
        stopLoadMore(LM_LOAD_SUCCESS);
    }

    public void stopLoadMore(int loadmoreStatus) {
        setLoadMoreStatus(loadmoreStatus);
        if (getAdapter() != null) {
            getAdapter().notifyItemChanged(getAdapter().getItemCount() - 1);
        }
    }

    public void setLoadMoreStatus(int loadMoreStatus) {
        this.loadMoreStatus = loadMoreStatus;
    }

    public int getLoadMoreStatus() {
        return loadMoreStatus;
    }

    public void setCanLoad(boolean canLoad) {
        this.canLoad = canLoad;
    }

    public OnLoadMoreListener getOnLoadmoreListener() {
        return onLoadmoreListener;
    }

    public void setOnLoadmoreListener(OnLoadMoreListener onLoadmoreListener) {
        this.onLoadmoreListener = onLoadmoreListener;
    }

    public interface OnLoadMoreListener {
        public void onLoadmore();
    }

}
