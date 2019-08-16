package com.view.loadmore;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 加载更多RecyclerView
 */
public class LoadMoreRecyclerView extends RecyclerView {

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
    static final int PRE_LOAD_COUNT = 5;
    private int loadMoreStatus = LM_LOAD_NON;
    private OnLoadMoreListener onLoadmoreListener;
    // 是否预加载
    private boolean isPreLoad = true;
    private Drawable loadmoreBgDrawable;


    public LoadMoreRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public void stopLoadMore(){
        stopLoadMore(LM_LOAD_SUCCESS);
    }

    public void stopLoadMore(int loadmoreStatus){
        setLoadMoreStatus(loadmoreStatus);
        if (getAdapter() != null){
            getAdapter().notifyItemChanged(getAdapter().getItemCount() - 1);
        }
    }

    public void setLoadMoreStatus(int loadMoreStatus) {
        this.loadMoreStatus = loadMoreStatus;
    }

    public int getLoadMoreStatus() {
        return loadMoreStatus;
    }

    public boolean isPreLoad() {
        return isPreLoad;
    }

    public void setPreLoad(boolean preLoad) {
        isPreLoad = preLoad;
    }

    public OnLoadMoreListener getOnLoadmoreListener() {
        return onLoadmoreListener;
    }

    public void setOnLoadmoreListener(OnLoadMoreListener onLoadmoreListener) {
        this.onLoadmoreListener = onLoadmoreListener;
    }

    public Drawable getLoadmoreBgDrawable() {
        return loadmoreBgDrawable;
    }

    public void setLoadMoreBgDrawable(Drawable loadmoreBgDrawable) {
        this.loadmoreBgDrawable = loadmoreBgDrawable;
    }

    public interface OnLoadMoreListener {
        public void onLoadmore();
    }

}
