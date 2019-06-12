package com.base.view.loading;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.R;
import com.base.Utils;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-12
 */
public class LoadingLayout extends LinearLayout {

    private LoadingView loadingView;
    private TextView descView;

    private int descTextColor = 0xff333333;
    private int descTextSize = 15;
    private String descContent;

    public LoadingLayout(Context context) {
        this(context, null);
    }

    public LoadingLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        descContent = context.getResources().getString(R.string.loading);
        createLoadingView(context);
        createDescView(context);
    }

    private void createDescView(Context context) {
        descView = new TextView(context);
        MarginLayoutParams params = new MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.topMargin = Utils.dip2px(getContext(),15f);
        descView.setText(descContent);
        descView.setTextColor(descTextColor);
        descView.setTextSize(descTextSize);
        descView.setLayoutParams(params);
        addView(descView);
    }

    private void createLoadingView(Context context) {
        loadingView = new LoadingView(context);
        LayoutParams params = new  LayoutParams(Utils.dip2px(getContext(),100f),Utils.dip2px(getContext(),100f));
        params.gravity = Gravity.CENTER;
        loadingView.setLayoutParams(params);
        addView(loadingView);
    }

    public void setDescTextColor(int descTextColor) {
        this.descTextColor = descTextColor;
    }

    public void setDescTextSize(int descTextSize) {
        this.descTextSize = descTextSize;
    }

    public void setDescContent(String descContent) {
        this.descContent = descContent;
    }

    public void setDescViewVisible(int visible){
        descView.setVisibility(visible);
    }
}
