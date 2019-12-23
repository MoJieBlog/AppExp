package com.dialog.base;

import android.graphics.Color;
import android.graphics.Typeface;

import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;

/**
 * @describe 单选项弹窗
 * @author: lixiaopeng
 * @Date: 2019-12-05
 */
public interface IOneOptDialog<T> {

    /**
     * 设置选项文字
     * @param text
     * @return
     */
    T setText(String text);

    /**
     * 设置选项文字
     * @param textRes
     * @return
     */
    T setText(@StringRes int textRes);

    /**
     * 设置选项文字大小
     * @param size
     * @return
     */
    T setTextSize(int size);


    /**
     * 设置选项文字颜色
     * @param color
     * @return
     */
    T setTextColor(Color color);

    /**
     * 设置选项文字颜色
     * @param color
     * @return
     */
    T setTextColor(@ColorRes int color);

    /**
     * 设置选项字体
     * @param typeFace
     * @return
     */
    T setTextTypeFace(Typeface typeFace);

    /**
     * 设置选项文字是否加粗
     * @param bold
     * @return
     */
    T setTextBold(boolean bold);
}
