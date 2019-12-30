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
     * 设置信息文字
     * @param text
     * @return
     */
    T setMessageText(String text);

    /**
     * 设置信息文字
     * @param textRes
     * @return
     */
    T setMessageText(@StringRes int textRes);

    /**
     * 设置信息文字大小
     * @param size
     * @return
     */
    T setMessageTextSize(float size);


    /**
     * 设置信息文字颜色
     * @param color
     * @return
     */
    T setMessageTextColor(int color);

    /**
     * 设置信息文字颜色
     * @param color
     * @return
     */
    T setMessageTextColorRes(@ColorRes int color);

    /**
     * 设置信息字体
     * @param typeFace
     * @return
     */
    T setMessageTextTypeFace(Typeface typeFace);

    /**
     * 设置信息文字是否加粗
     * @param bold
     * @return
     */
    T setMessageTextBold(boolean bold);

    /**
     * 设置选项文字
     * @param text
     * @return
     */
    T setOptText(String text);

    /**
     * 设置选项文字
     * @param textRes
     * @return
     */
    T setOptText(@StringRes int textRes);

    /**
     * 设置选项文字大小
     * @param size
     * @return
     */
    T setOptTextSize(float size);


    /**
     * 设置选项文字颜色
     * @param color
     * @return
     */
    T setOptTextColor(int color);

    /**
     * 设置选项文字颜色
     * @param color
     * @return
     */
    T setOptTextColorRes(@ColorRes int color);

    /**
     * 设置选项字体
     * @param typeFace
     * @return
     */
    T setOptTextTypeFace(Typeface typeFace);

    /**
     * 设置选项文字是否加粗
     * @param bold
     * @return
     */
    T setOptTextBold(boolean bold);
}
