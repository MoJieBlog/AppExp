package com.view.refresh.ext.moveopen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utils.SizeUtils;
import com.view.refresh.LoadingLayout;
import com.view.refresh.SwipeRefreshLayout;
import com.view.refresh.ext.GoLoadingLayout;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-30
 */
public class MoveOpenRefreshLayout extends LoadingLayout {

    private GoLoadingLayout loadingLayout;
    private TextView textView;

    private int viewWidth, viewHeight;

    private int animViewHeight,animViewWidth;

    private MoveOpenAndRefreshLayout refreshLayout;

    private int dp30;

    public MoveOpenRefreshLayout(@NonNull Context context) {
        super(context);
    }

    public MoveOpenRefreshLayout(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void init() {

        dp30 = SizeUtils.dip2px(getContext(), 30);


        textView = new TextView(getContext());
        textView.setTextSize(12);
        textView.setTextColor(0xffffffff);
        textView.setGravity(Gravity.CENTER);
        LayoutParams textLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLp.gravity = Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
        textLp.bottomMargin = dp30/4;

        viewHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200,
                mResources.getDisplayMetrics());


        animViewHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60,
                mResources.getDisplayMetrics());

        loadingLayout = new GoLoadingLayout(getContext());
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, animViewHeight);
        lp.gravity = Gravity.CENTER;

        addView(loadingLayout, lp);
        addView(textView, textLp);


    }

    /**
     * 滑动回调
     *
     * @param moveOffset   滑动距离
     * @param isRefreshing 是否正在刷新
     */
    @Override
    public void onMove(float moveOffset, boolean isRefreshing) {
        textView.setVisibility(VISIBLE);
        loadingLayout.setVisibility(GONE);
        if (moveOffset < refreshLayout.REFRESH_SIZE) {
            textView.setText("下拉刷新");
        } else if (moveOffset > refreshLayout.REFRESH_SIZE && moveOffset < refreshLayout.OPEN_ACTIVITY_SIZE) {
            textView.setText("松手刷新\n继续下拉打开车库");
        } else if (moveOffset > refreshLayout.OPEN_ACTIVITY_SIZE) {
            textView.setText("松开打开车库");
        }
    }

    /**
     * 正在刷新回调
     */
    @Override
    public void onRefreshing() {
        if (loadingLayout != null) {
            textView.setVisibility(GONE);
            loadingLayout.setVisibility(VISIBLE);
            loadingLayout.repeatAnimation();
        }
    }

    /**
     * 刷新完成回调
     */
    @Override
    public void onRefreshFinish() {
        if (loadingLayout != null) {
            textView.setVisibility(VISIBLE);
            loadingLayout.setVisibility(GONE);
            loadingLayout.stopAnimation();
        }
    }

    /**
     * 设置包含child的高度(内容child ::::: listview ,gridview, scrollview 等)
     *
     * @param height child高度
     */
    @Override
    public void setTargetViewHeight(int height) {

    }

    /**
     * 设置刷新布局的实例索引
     *
     * @param refreshLayout
     */
    @Override
    public void setRefreshLayoutInstance(SwipeRefreshLayout refreshLayout) {
        this.refreshLayout = (MoveOpenAndRefreshLayout) refreshLayout;
    }

    /**
     * 获取loadinglayout的高度
     */
    @Override
    public int getLoadingOffsetHeight() {
        return animViewHeight;
    }
}
