package com.dialog;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.IntegerRes;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-12-05
 */
public interface ITwoOptDialog {
    /**
     * 设置左侧文字
     *
     * @param text
     * @return
     */
    BaseTwoOptDialog setLeftText(String text);

    BaseTwoOptDialog setLeftText(@IntegerRes int text);

    /**
     * 设置右侧文字
     *
     * @param text
     * @return
     */
    BaseTwoOptDialog setRightText(String text);

    /**
     * 设置右侧文字
     *
     * @param text
     * @return
     */
    BaseTwoOptDialog setRightText(@IntegerRes int text);

    /**
     * 设置左侧文字大小
     *
     * @param size
     * @return
     */
    BaseTwoOptDialog setLeftTextSize(int size);

    /**
     * 设置右侧文字大小
     *
     * @param size
     * @return
     */
    BaseTwoOptDialog setRightTextSize(int size);

    /**
     * 设置左侧文字颜色
     *
     * @param color
     * @return
     */
    BaseTwoOptDialog setLeftTextColor(Color color);

    /**
     * 设置左侧文字颜色
     *
     * @param color
     * @return
     */
    BaseTwoOptDialog setLeftTextColor(@ColorInt int color);

    /**
     * 设置右侧文字颜色
     *
     * @param color
     * @return
     */
    BaseTwoOptDialog setRightTextColor(Color color);

    /**
     * 设置右侧文字颜色
     *
     * @param color
     * @return
     */
    BaseTwoOptDialog setRightTextColor(@ColorInt int color);

    /**
     * 设置左侧文字字体
     *
     * @param typeface
     * @return
     */
    BaseTwoOptDialog setLeftTextTypeface(Typeface typeface);

    /**
     * 设置右侧文字字体
     *
     * @param typeFace
     * @return
     */
    BaseTwoOptDialog setRightTextTypeFace(Typeface typeFace);

    /**
     * 设置左侧文字是否加粗
     *
     * @param bold
     * @return
     */
    BaseTwoOptDialog setLeftTextBold(boolean bold);

    /**
     * 设置右侧文字是否加粗
     *
     * @param bold
     * @return
     */
    BaseTwoOptDialog setRightTextBold(boolean bold);
}
