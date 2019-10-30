package com.view.refresh;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-14
 */
public interface ILoadingLayout {

    /**
     * 滑动回调
     *
     * @param moveOffset 滑动距离
     * @param isRefreshing 是否正在刷新
     */
    public void onMove(float moveOffset, boolean isRefreshing);

    /**
     * 正在刷新回调
     */
    public void onRefreshing();

    /**
     * 刷新完成回调
     */
    public void onRefreshFinish();

    /**
     * 设置包含child的高度(内容child ::::: listview ,gridview, scrollview 等)
     *
     * @param height child高度
     */
    public void setTargetViewHeight(int height);

    /**
     * 设置刷新布局的实例索引
     */
    public void setRefreshLayoutInstance(SwipeRefreshLayout refreshLayout);

    /**
     * 获取loadinglayout的高度
     */
    public int getLoadingOffsetHeight();

}
