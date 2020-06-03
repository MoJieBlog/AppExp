package com.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/4/20
 */
public class ToastUtils {

    private static Toast toast;

    private static Toast getToast() {
        if (toast == null) {
            toast = new Toast(Utils.getApp());
        }
        return toast;
    }

    public static void toastText(String text) {
        Toast toast = getToast();
        toast.setText(text);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void toastIcon(String text) {

    }


}
