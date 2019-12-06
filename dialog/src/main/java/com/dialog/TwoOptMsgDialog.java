package com.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.view.Gravity;

import com.dialog.base.BaseTwoOptDialog;

/**
 * @describe 两个选项带描述
 * @author: lixiaopeng
 * @Date: 2019-12-06
 */
public class TwoOptMsgDialog extends BaseTwoOptDialog {
    private TwoOptMsgDialog(@NonNull Context context) {
        super(context);
    }

    private TwoOptMsgDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        //默认中间显示
        setGravity(Gravity.CENTER);
        setContentView(R.layout.dialog_two_opt);
    }

    /**
     * 是否需要描述
     *
     * @param needMsg
     */
    public void setNeedMsg(boolean needMsg) {

    }

    public void setMsgSize(int size) {

    }

    interface ITwoOptMsgDialog {
        void setNeedMsg(boolean needMsg);

        void setMsgSize(int size);

        void setMsgText(String text);

        void setMsgText(@IntegerRes int text);

        void setMsgColor(Color color);

        void setMsgColor(@ColorInt int color);
    }

    public static class TwoOptMsgDialogBuilder extends TwoOptDialogBuilder {

        protected TwoOptMsgDialogBuilder(Context context) {
            super(context);
        }

        @Override
        protected BaseTwoOptDialog createDialog(Context context) {
            return new TwoOptMsgDialog(context);
        }

        @Override
        public BaseTwoOptDialog build() {
            return twoOptDialog;
        }
    }
}
