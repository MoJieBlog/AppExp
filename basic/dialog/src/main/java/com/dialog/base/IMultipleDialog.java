package com.dialog.base;

import android.graphics.Typeface;

import androidx.annotation.ColorRes;

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/4/1
 */
public interface IMultipleDialog<T> {
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
