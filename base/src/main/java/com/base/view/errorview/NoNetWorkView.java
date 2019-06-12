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
public class NoNetWorkView extends BaseErrView {

    private NoNetRefreshListener noNetRefreshListener;

    public NoNetWorkView(Context context) {
        super(context);
    }

    public NoNetWorkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initSelfView();
        setView();
        setListener();
    }

    private void setListener() {

        setOnBtnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noNetRefreshListener != null) {
                    noNetRefreshListener.noNetRefresh();
                }
            }
        });
    }

    private void initSelfView() {

        defaultImageRes = defaultImageRes==0? R.drawable.wifi_icon:defaultImageRes;
        defaultTitleStr = TextUtils.isEmpty(defaultTitleStr)?resources.getString(R.string.net_invalid):defaultTitleStr;
        defaultContentStr =TextUtils.isEmpty(defaultContentStr)? resources.getString(R.string.open_net):defaultContentStr;
        defaultBtnTextStr =TextUtils.isEmpty(defaultBtnTextStr)? resources.getString(R.string.refresh):defaultBtnTextStr;

    }

    public void setNoNetRefreshListener(NoNetRefreshListener noNetRefreshListener) {
        this.noNetRefreshListener = noNetRefreshListener;
    }

    public interface NoNetRefreshListener {
        void noNetRefresh();
    }
}