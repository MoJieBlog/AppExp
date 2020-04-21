package com.dialog.base;

import android.graphics.Typeface;

import androidx.annotation.ColorRes;
import androidx.annotation.StringRes;

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/4/21
 */
public interface IMessage<T> {

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
}
