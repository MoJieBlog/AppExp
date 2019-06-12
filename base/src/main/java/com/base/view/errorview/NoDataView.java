package com.base.view.errorview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.base.R;

/**
 * @author Li Xiaopeng
 * @date 2019/1/3
 */
public class NoDataView extends BaseErrView {


    NoDataRefreshListener noDataRefreshListener;

    public NoDataView(Context context) {
        super(context);
    }

    public NoDataView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initSelfView();
        setView();
        setListener();
    }

    private void setListener() {
        setOnBtnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noDataRefreshListener != null) {
                    noDataRefreshListener.noDataRefresh();
                }
            }
        });
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noDataRefreshListener != null) {
                    noDataRefreshListener.noDataRefresh();
                }
            }
        });
    }

    private void initSelfView() {
        getRefreshButton().setVisibility(GONE);

        defaultImageRes = defaultImageRes==0? R.drawable.illustration_task_loading:defaultImageRes;
        defaultTitleStr = TextUtils.isEmpty(defaultTitleStr)?resources.getString(R.string.have_no_data):defaultTitleStr;
        defaultContentStr =TextUtils.isEmpty(defaultContentStr)? resources.getString(R.string.find_more_funny):defaultContentStr;
        defaultBtnTextStr =TextUtils.isEmpty(defaultBtnTextStr)? resources.getString(R.string.refresh):defaultBtnTextStr;
    }

    public void setNoDataRefreshListener(NoDataRefreshListener fetchRefreshListener) {
        this.noDataRefreshListener = fetchRefreshListener;
    }

    public interface NoDataRefreshListener {
        void noDataRefresh();
    }
}
