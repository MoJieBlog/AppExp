package com.dialog.base;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.IntegerRes;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-12-05
 */
public interface ITwoOptDialog<T> {
    /**
     * 设置左侧文字
     *
     * @param text
     * @return
     */
    T setLeftText(String text);

    /**
     * 设置左侧文字
     *
     * @param text
     * @return
     */
    T setLeftText(@IntegerRes int text);

    /**
     * 设置右侧文字
     *
     * @param text
     * @return
     */
    T setRightText(String text);

    /**
     * 设置右侧文字
     *
     * @param text
     * @return
     */
    T setRightText(@IntegerRes int text);

    /**
     * 设置左侧文字大小
     *
     * @param size
     * @return
     */
    T setLeftTextSize(int size);

    /**
     * 设置右侧文字大小
     *
     * @param size
     * @return
     */
    T setRightTextSize(int size);

    /**
     * 设置左侧文字颜色
     *
     * @param color
     * @return
     */
    T setLeftTextColor(Color color);

    /**
     * 设置左侧文字颜色
     *
     * @param color
     * @return
     */
    T setLeftTextColor(@ColorInt int color);

    /**
     * 设置右侧文字颜色
     *
     * @param color
     * @return
     */
    T setRightTextColor(Color color);

    /**
     * 设置右侧文字颜色
     *
     * @param color
     * @return
     */
    T setRightTextColor(@ColorInt int color);

    /**
     * 设置左侧文字字体
     *
     * @param typeface
     * @return
     */
    T setLeftTextTypeface(Typeface typeface);

    /**
     * 设置右侧文字字体
     *
     * @param typeFace
     * @return
     */
    T setRightTextTypeFace(Typeface typeFace);

    /**
     * 设置左侧文字是否加粗
     *
     * @param bold
     * @return
     */
    T setLeftTextBold(boolean bold);

    /**
     * 设置右侧文字是否加粗
     *
     * @param bold
     * @return
     */
    T setRightTextBold(boolean bold);
}
