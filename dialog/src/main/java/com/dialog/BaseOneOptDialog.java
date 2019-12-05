package com.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-12-05
 */
public class BaseOneOptDialog extends BaseDialog implements IOneOptDialog {

    public BaseOneOptDialog(@NonNull Context context) {
        super(context);
    }

    public BaseOneOptDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 设置选项文字
     *
     * @param text
     * @return
     */
    @Override
    public BaseOneOptDialog setText(String text) {
        return null;
    }

    /**
     * 设置选项文字
     *
     * @param textRes
     * @return
     */
    @Override
    public BaseOneOptDialog setText(int textRes) {
        return null;
    }

    /**
     * 设置选项文字大小
     *
     * @param size
     * @return
     */
    @Override
    public BaseOneOptDialog setTextSize(int size) {
        return null;
    }

    /**
     * 设置选项文字颜色
     *
     * @param color
     * @return
     */
    @Override
    public BaseOneOptDialog setTextColor(Color color) {
        return null;
    }

    /**
     * 设置选项文字颜色
     *
     * @param color
     * @return
     */
    @Override
    public BaseOneOptDialog setTextColor(int color) {
        return null;
    }

    /**
     * 设置选项字体
     *
     * @param typeFace
     * @return
     */
    @Override
    public BaseOneOptDialog setTextTypeFace(Typeface typeFace) {
        return null;
    }

    /**
     * 设置选项文字是否加粗
     *
     * @param bold
     * @return
     */
    @Override
    public BaseOneOptDialog setTextBold(boolean bold) {
        return null;
    }
}
