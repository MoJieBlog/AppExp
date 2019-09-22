package com.view.loadmore;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    /**
     * 不可加载更多
     */
    public static final int LM_LOAD_NON = 0;
    /**
     * 点击加载
     */
    public static final int LM_CLICK_LOAD = 1;

    /**
     * 自动加载
     */
    public static final int LM_AUTO_LOAD = 2;

    /**
     * 加载失败
     */
    public static final int LM_LOAD_FAILURE = 3;
    /**
     * 加载完成
     */
    public static final int LM_LOAD_COMPLETE = 4;

    /**
     * 距离底部条目个数（触发预加载）
     */
    static final int PRE_LOAD_COUNT = 0;

    private int loadMoreStatus = LM_LOAD_NON;

    private OnLoadMoreListener onLoadmoreListener;

    private int lastPosition = 0;

    private boolean isLoading = false;

    public LoadMoreRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                LayoutManager layoutManager = getLayoutManager();
                int itemCount = layoutManager.getItemCount();

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {//静止的时候再加载更多
                    //因为有底部
                    if (canLoadMore(itemCount)) {
                        isLoading = true;
                        Log.e(TAG, "onScrollStateChanged: 开始加载更多");
                        setLoadMoreStatus(LoadMoreRecyclerView.LM_AUTO_LOAD);
                        if (getAdapter() != null) {
                            getAdapter().notifyItemChanged(getAdapter().getItemCount() - 1);
                        }
                        if (getOnLoadmoreListener() != null) {
                            onLoadmoreListener.onLoadMore();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LayoutManager layoutManager = getLayoutManager();
                int itemCount = layoutManager.getItemCount();

                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();

                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    int[] last = new int[staggeredGridLayoutManager.getSpanCount()];

                    int[] lastCompletelyVisibleItemPositions = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(last);
                    int max = Math.max(lastCompletelyVisibleItemPositions[0], lastCompletelyVisibleItemPositions[1]);
                    lastPosition = max;
                }
            }
        });

    }

    private boolean canLoadMore(int itemCount) {
        Log.e(TAG, "canLoadMore: " + lastPosition);
        return !isLoading &&
                getLoadMoreStatus() == LoadMoreRecyclerView.LM_AUTO_LOAD
                && itemCount > 1
                && lastPosition >= (itemCount - 1 - PRE_LOAD_COUNT);
    }

    public void stopLoad(){
        isLoading = false;
    }

    public void setLoadMoreStatus(int loadMoreStatus) {
        this.loadMoreStatus = loadMoreStatus;
    }

    public int getLoadMoreStatus() {
        return loadMoreStatus;
    }

    public OnLoadMoreListener getOnLoadmoreListener() {
        return onLoadmoreListener;
    }

    public void setOnLoadmoreListener(OnLoadMoreListener onLoadmoreListener) {
        this.onLoadmoreListener = onLoadmoreListener;
    }

    public interface OnLoadMoreListener {
        public void onLoadMore();
    }

}
