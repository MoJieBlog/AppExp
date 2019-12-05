package com.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-12-05
 */
public class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context) {
        this(context, 0);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public void showBottom(){
        getWindow().setGravity(Gravity.BOTTOM);
        show();
    }

    public void showTop(){
        getWindow().setGravity(Gravity.TOP);
        show();
    }

    public void showCenter(){
        getWindow().setGravity(Gravity.CENTER);
        show();
    }
}
