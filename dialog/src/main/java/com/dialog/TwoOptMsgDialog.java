package com.dialog;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.Gravity;

import com.dialog.base.BaseTwoOptDialog;

/**
 * @describe 两个选项带描述
 * @author: lixiaopeng
 * @Date: 2019-12-06
 */
public class TwoOptMsgDialog extends BaseTwoOptDialog{
    private TwoOptMsgDialog(@NonNull Context context) {
        super(context);
    }

    private TwoOptMsgDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        //默认中间显示
        setGravity(Gravity.CENTER);
        setContentView(R.layout.dialog_two_opt);
    }

    public TwoOptMsgDialog setNeedMsg(boolean needMsg) {
        return null;
    }

    public TwoOptMsgDialog setMsgSize(int size) {
        return null;
    }

    public TwoOptMsgDialog setMsgText(String text) {
        return null;
    }

    public TwoOptMsgDialog setMsgText(int text) {
        return null;
    }

    public TwoOptMsgDialog setMsgColor(Color color) {
        return null;
    }

    public TwoOptMsgDialog setMsgColor(int color) {
        return null;
    }

    public static class TwoOptMsgDialogBuilder extends TwoOptDialogBuilder{
        private TwoOptMsgDialog twoOptMsgDialog;
        protected TwoOptMsgDialogBuilder(Context context) {
            super(context);
            twoOptMsgDialog = new TwoOptMsgDialog(context);
        }

        @Override
        public BaseTwoOptDialog getDialog() {
            return twoOptMsgDialog;
        }

        public TwoOptMsgDialogBuilder setNeedMsg(boolean needMsg) {
            twoOptMsgDialog.setNeedMsg(needMsg);
            return this;
        }

        public TwoOptMsgDialogBuilder setMsgSize(int size) {
            twoOptMsgDialog.setMsgSize(size);
            return this;
        }

        public TwoOptMsgDialogBuilder setMsgText(String text) {
            twoOptMsgDialog.setMsgText(text);
            return this;
        }

        public TwoOptMsgDialogBuilder setMsgText(int text) {
            twoOptMsgDialog.setMsgText(text);
            return this;
        }

        public TwoOptMsgDialogBuilder setMsgColor(Color color) {
            twoOptMsgDialog.setMsgColor(color);
            return this;
        }

        public TwoOptMsgDialogBuilder setMsgColor(int color) {
            twoOptMsgDialog.setMsgColor(color);
            return this;
        }

        @Override
        public void show() {
            twoOptMsgDialog.show();
        }
    }
}
