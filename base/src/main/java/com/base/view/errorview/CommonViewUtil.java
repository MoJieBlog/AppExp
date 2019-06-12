package com.base.view.errorview;

import android.view.View;

import com.base.view.loading.LoadingLayout;

/**
 * @author Li Xiaopeng
 * @date 2019/3/30
 */
public class CommonViewUtil {

    private NoDataView noDataView;
    private NoNetWorkView noNetWorkView;
    private FetchDataErrorView errorView;
    private LoadingLayout loadingLayout;
    private View contentView;

    public CommonViewUtil() {
    }

    public void bindView(NoDataView noDataView,
                         NoNetWorkView noNetWorkView,
                         FetchDataErrorView errorView,
                         LoadingLayout loadingLayout,
                         View contentView) {
        this.noDataView = noDataView;
        this.noNetWorkView = noNetWorkView;
        this.errorView = errorView;
        this.loadingLayout = loadingLayout;
        this.contentView = contentView;
    }

    public void release() {
        this.noDataView = null;
        this.noNetWorkView = null;
        this.errorView = null;
        this.loadingLayout = null;
        this.contentView = null;
    }

    public void showLoadingView() {
        if (noDataView != null) noDataView.setVisibility(View.GONE);
        if (noNetWorkView != null) noNetWorkView.setVisibility(View.GONE);
        if (loadingLayout != null) loadingLayout.setVisibility(View.VISIBLE);
        if (errorView != null) errorView.setVisibility(View.GONE);
        if (contentView != null) contentView.setVisibility(View.GONE);
    }

    public void showContentView() {
        if (noDataView != null) noDataView.setVisibility(View.GONE);
        if (noNetWorkView != null) noNetWorkView.setVisibility(View.GONE);
        if (loadingLayout != null) loadingLayout.setVisibility(View.GONE);
        if (errorView != null) errorView.setVisibility(View.GONE);
        if (contentView != null) contentView.setVisibility(View.VISIBLE);
    }

    public void showNoDataView() {
        if (noDataView != null) noDataView.setVisibility(View.VISIBLE);
        if (noNetWorkView != null) noNetWorkView.setVisibility(View.GONE);
        if (loadingLayout != null) loadingLayout.setVisibility(View.GONE);
        if (errorView != null) errorView.setVisibility(View.GONE);
        if (contentView != null) contentView.setVisibility(View.GONE);
    }

    public void showNoNetView(){
        if (noDataView != null) noDataView.setVisibility(View.GONE);
        if (noNetWorkView != null) noNetWorkView.setVisibility(View.VISIBLE);
        if (loadingLayout != null) loadingLayout.setVisibility(View.GONE);
        if (errorView != null) errorView.setVisibility(View.GONE);
        if (contentView != null) contentView.setVisibility(View.GONE);
    }

    public void showErrView(){
        if (noDataView != null) noDataView.setVisibility(View.GONE);
        if (noNetWorkView != null) noNetWorkView.setVisibility(View.GONE);
        if (loadingLayout != null) loadingLayout.setVisibility(View.GONE);
        if (errorView != null) errorView.setVisibility(View.VISIBLE);
        if (contentView != null) contentView.setVisibility(View.GONE);
    }

    public void hideAllView(){
        if (noDataView != null) noDataView.setVisibility(View.GONE);
        if (noNetWorkView != null) noNetWorkView.setVisibility(View.GONE);
        if (loadingLayout != null) loadingLayout.setVisibility(View.GONE);
        if (errorView != null) errorView.setVisibility(View.GONE);
        if (contentView != null) contentView.setVisibility(View.GONE);
    }

    public void showLoadingOnly(){
        if (loadingLayout != null) loadingLayout.setVisibility(View.VISIBLE);
    }

    public void hideLoadingOnly(){
        if (loadingLayout != null) loadingLayout.setVisibility(View.GONE);
    }

}
