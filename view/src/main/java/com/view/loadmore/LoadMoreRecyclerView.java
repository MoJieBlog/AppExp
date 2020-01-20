package com.view.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.view.damp.DampRecyclerView;

/**
 * 加载更多RecyclerView
 */
public class LoadMoreRecyclerView extends DampRecyclerView {

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

    //文字相关参数
    private String completeText = "已加载全部";//加载完成的文字
    private String clickLoadText = "点击加载下一页";//不自动加载时点击加载的文字
    private String failText = "数据加载失败";//加载失败的文字
    private String loadingText = "加载中...";//加载中的文字

    public LoadMoreRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public LoadMoreRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setNeedStartDamp(false);
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LayoutManager layoutManager = getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && layoutManager != null) {//静止的时候再加载更多
                    int itemCount = layoutManager.getItemCount();
                    //因为有底部
                    if (canLoadMore(itemCount)) {
                        Log.e(TAG, "onScrollStateChanged: 开始加载更多");
                        isLoading = true;
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
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    lastPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    int[] last = new int[staggeredGridLayoutManager.getSpanCount()];
                    int[] lastCompletelyVisibleItemPositions = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(last);
                    lastPosition = Math.max(lastCompletelyVisibleItemPositions[0], lastCompletelyVisibleItemPositions[1]);
                }
            }
        });
    }

    private boolean canLoadMore(int itemCount) {
        Log.e(TAG, "canLoadMore: " + lastPosition);
        return !isLoading &&
                loadMoreStatus == LoadMoreRecyclerView.LM_AUTO_LOAD
                && itemCount > 1
                && lastPosition >= (itemCount - 1 - PRE_LOAD_COUNT);
    }

    public void setLoadMoreStatus(int loadMoreStatus) {
        this.loadMoreStatus = loadMoreStatus;
    }

    public void setLoadMoreStatus(int loadMoreStatus,String failText) {
        this.loadMoreStatus = loadMoreStatus;
        this.failText = failText;
    }

    public void stopLoad(){
        isLoading = false;
    }

    public void setAdapter(@Nullable LoadMoreAdapter adapter) {
        super.setAdapter(adapter);
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

    public void onFailClick(){
        setLoadMoreStatus(LoadMoreRecyclerView.LM_AUTO_LOAD);
        if(getAdapter()!=null){
            getAdapter().notifyItemChanged(getAdapter().getItemCount() - 1);
        }
        if (this.onLoadmoreListener!=null){
            this.onLoadmoreListener.onRecyclerViewFailClick();
        }
    }

    public void onLoadMore(){
        //显示加载中的动画
        setLoadMoreStatus(LoadMoreRecyclerView.LM_AUTO_LOAD);
        if(getAdapter()!=null){
            getAdapter().notifyItemChanged(getAdapter().getItemCount() - 1);
        }
        //触发回调
        if (this.onLoadmoreListener!=null){
            this.onLoadmoreListener.onLoadMore();
        }
    }
    public interface OnLoadMoreListener {
        void onLoadMore();
        //失败被点击时要执行的操作
        default void onRecyclerViewFailClick(){}
    }

    /********************文案设置***********************/
    public String getCompleteText(){
        return completeText;
    }

    public String getClickLoadText(){
        return clickLoadText;
    }

    public String getFailText(){
        return failText;
    }

    public String getLoadingText(){
        return loadingText;
    }

    public void setCompleteText(String completeText) {
        this.completeText = completeText;
    }

    public void setClickLoadText(String clickLoadText) {
        this.clickLoadText = clickLoadText;
    }

    public void setFailText(String failText) {
        this.failText = failText;
    }

    public void setLoadingText(String loadingText) {
        this.loadingText = loadingText;
    }

}
