package com.lzp.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-07-15
 */
public class LoadingView extends ProgressBar {
    public LoadingView(Context context) {
        super(context);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setIndeterminateDrawable(getResources().getDrawable(com.view.R.drawable.dialog_loading_anim_progress));
    }
}
