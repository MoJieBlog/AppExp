package com.dialog;

import android.content.Context;

import com.dialog.TwoOptDialog.TwoOptDialogBuilder;

/**
 * @describe 如果怕记不住都包含了哪些弹窗，可以统一用这里，根据提示写弹窗
 * @author: lixiaopeng
 * @Date: 2019-12-06
 */
public class NBDialogBuilder {

    public static TwoOptDialogBuilder buildTwoOptDialog(Context context) {
        return new TwoOptDialogBuilder(context);
    }
}
