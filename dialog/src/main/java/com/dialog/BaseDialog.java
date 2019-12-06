package com.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.WindowManager.LayoutParams;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-12-05
 */
public abstract class BaseDialog extends Dialog implements IBaseDialog<BaseDialog> {

    public BaseDialog(@NonNull Context context) {
        this(context, 0);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);

        //设置透明度
        LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);
    }

    @Override
    public BaseDialog setGravity(int gravity) {
        getWindow().setGravity(gravity);
        return this;
    }
}
