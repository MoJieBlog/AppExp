package com.utils;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntegerRes;

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/4/20
 */
public class ToastUtils {

    public static void toastText(Context context, String text) {
        TextToast.toast.show(context, text);
    }

    public static void toastIcon(Context context, @IntegerRes int iconRes, String text) {
        toastText(context, "暂不支持");
    }

    private static class TextToast {
        static TextToast toast = new TextToast();

        Toast mToast;
        TextView toastMsg;

        void show(Context context, CharSequence msg) {
            if (notMainThread()) {
                return;
            }
            if (mToast == null) {
                mToast = new Toast(context.getApplicationContext());
                int screenHeight = context.getResources().getDisplayMetrics().heightPixels;
                mToast.setGravity(Gravity.TOP, 0, screenHeight / 5);
                mToast.setDuration(Toast.LENGTH_SHORT);
                View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.common_toast, null);
                toastMsg = view.findViewById(R.id.toastMsg);

                // toast最高为屏幕高度的1/2
                toastMsg.setMaxHeight(screenHeight / 2);
                mToast.setView(view);
            }
            toastMsg.setText(msg);
            mToast.show();

        }
    }

    static boolean notMainThread() {
        return Looper.getMainLooper().getThread() != Thread.currentThread();
    }


}
