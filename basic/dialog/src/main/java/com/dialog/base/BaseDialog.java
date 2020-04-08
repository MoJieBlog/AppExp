package com.dialog.base;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.dialog.R;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-12-05
 */
public class BaseDialog extends Dialog implements IBaseDialog<BaseDialog> {

    public BaseDialog(@NonNull Context context) {
        this(context, R.style.my_dialog);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);

        //设置透明度
        /*LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.3f;
        getWindow().setAttributes(lp);*/
    }

    @Override
    public BaseDialog setGravity(int gravity) {
        Window window = getWindow();
        if (window!=null){
            getWindow().setGravity(gravity);
        }
        return this;
    }

    @Override
    public BaseDialog setWindowAnimations(@StyleRes int animations) {
        Window window = getWindow();
        if (window!=null){
            window.setWindowAnimations(animations);
        }
        return this;
    }


}
