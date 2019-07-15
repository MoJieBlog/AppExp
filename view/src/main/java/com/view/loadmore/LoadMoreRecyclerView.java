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

    /**
     * 加载成功
     */
    public static final int LM_LOAD_SUCCESS = 1;
    /**
     * 加载失败
     */
    public static final int LM_LOAD_FAILURE = 0;
    /**
     * 加载完成
     */
    public static final int LM_LOAD_COMPLETE = 2;
    /**
     * 加载中
     */
    public static final int LM_LOADING = 3;
    /**
     * 距离底部条目个数（触发预加载）
     */
    static final int PRE_LOAD_COUNT = 5;
    private int loadmoreStatus = LM_LOAD_SUCCESS;
    private OnLoadmoreListener onLoadmoreListener;
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

    public void stopLoadmore(){
        stopLoadmore(LM_LOAD_SUCCESS);
    }

    public void stopLoadmore(int loadmoreStatus){
        setLoadmoreStatus(loadmoreStatus);
        if (getAdapter() != null){
            getAdapter().notifyItemChanged(getAdapter().getItemCount() - 1);
        }
    }

    public void setLoadmoreStatus(int loadmoreStatus) {
        this.loadmoreStatus = loadmoreStatus;
    }

    public int getLoadmoreStatus() {
        return loadmoreStatus;
    }

    public boolean isPreLoad() {
        return isPreLoad;
    }

    public void setPreLoad(boolean preLoad) {
        isPreLoad = preLoad;
    }

    public OnLoadmoreListener getOnLoadmoreListener() {
        return onLoadmoreListener;
    }

    public void setOnLoadmoreListener(OnLoadmoreListener onLoadmoreListener) {
        this.onLoadmoreListener = onLoadmoreListener;
    }

    public Drawable getLoadmoreBgDrawable() {
        return loadmoreBgDrawable;
    }

    public void setLoadmoreBgDrawable(Drawable loadmoreBgDrawable) {
        this.loadmoreBgDrawable = loadmoreBgDrawable;
    }

    public interface OnLoadmoreListener{
        public void onLoadmore();
    }

}
