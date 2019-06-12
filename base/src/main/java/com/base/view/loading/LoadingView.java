package com.base.view.loading;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * @author Li Xiaopeng
 * @date 2019/3/30
 */
public class LoadingView extends ProgressBar {

    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }
}
