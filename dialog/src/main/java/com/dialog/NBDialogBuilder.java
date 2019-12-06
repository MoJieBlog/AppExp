package com.dialog;

import android.content.Context;

import com.dialog.TwoOptMsgDialog.TwoOptMsgDialogBuilder;

/**
 * @describe 整体管理类
 * @author: lixiaopeng
 * @Date: 2019-12-06
 */
public class NBDialogBuilder {

    public static TwoOptMsgDialogBuilder buildTwoOptMsgDialog(Context context) {
        return new TwoOptMsgDialogBuilder(context);
    }
}
