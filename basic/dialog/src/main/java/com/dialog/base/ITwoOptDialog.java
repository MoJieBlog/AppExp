package com.dialog.base;

import android.graphics.Typeface;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.dialog.TwoOptMsgDialog;

/**
 * @describe 两个选项的弹窗
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
    TwoOptMsgDialog setLeftText(String text);

    /**
     * 设置左侧文字
     *
     * @param text
     * @return
     */
    TwoOptMsgDialog setLeftText(@StringRes int text);

    /**
     * 设置右侧文字
     *
     * @param text
     * @return
     */
    TwoOptMsgDialog setRightText(String text);

    /**
     * 设置右侧文字
     *
     * @param text
     * @return
     */
    TwoOptMsgDialog setRightText(@StringRes int text);

    /**
     * 设置左侧文字大小
     *
     * @param size
     * @return
     */
    TwoOptMsgDialog setLeftTextSize(int size);

    /**
     * 设置右侧文字大小
     *
     * @param size
     * @return
     */
    TwoOptMsgDialog setRightTextSize(int size);

    /**
     * 设置左侧文字颜色
     *
     * @param color
     * @return
     */
    TwoOptMsgDialog setLeftTextColorRes(@ColorRes int color);

    /**
     * 设置左侧文字颜色
     *
     * @param color
     * @return
     */
    TwoOptMsgDialog setLeftTextColor(@ColorInt int color);

    /**
     * 设置右侧文字颜色
     *
     * @param color
     * @return
     */
    TwoOptMsgDialog setRightTextColorRes(@ColorRes int color);

    /**
     * 设置右侧文字颜色
     *
     * @param color
     * @return
     */
    TwoOptMsgDialog setRightTextColor(@ColorInt int color);

    /**
     * 设置左侧文字字体
     *
     * @param typeface
     * @return
     */
    TwoOptMsgDialog setLeftTextTypeface(Typeface typeface);

    /**
     * 设置右侧文字字体
     *
     * @param typeFace
     * @return
     */
    TwoOptMsgDialog setRightTextTypeFace(Typeface typeFace);

    /**
     * 设置左侧文字是否加粗
     *
     * @param bold
     * @return
     */
    TwoOptMsgDialog setLeftTextBold(boolean bold);

    /**
     * 设置右侧文字是否加粗
     *
     * @param bold
     * @return
     */
    TwoOptMsgDialog setRightTextBold(boolean bold);

    /**
     * 显示选项间的分割线
     * @param show
     * @return
     */
    TwoOptMsgDialog showOptGapLine(boolean show);

    /**
     * 显示msg和选项间的分割线
     * @param show
     * @return
     */
    TwoOptMsgDialog showMsgOptGapLine(boolean show);

    /**
     * 设置左侧选项的背景
     * @param bgRes
     * @return
     */
    TwoOptMsgDialog setLeftBg(@DrawableRes int bgRes);

    /**
     * 设置左侧选项的背景
     * @param color
     * @return
     */
    TwoOptMsgDialog setLeftBgColor(@ColorInt int color);

    /**
     * 设置左侧选项的背景
     * @param color
     * @return
     */
    TwoOptMsgDialog setLeftBgColorRes(@ColorRes int color);

    /**
     * 设置右侧选项的背景
     * @param bgRes
     * @return
     */
    TwoOptMsgDialog setRightBg(@DrawableRes int bgRes);

    /**
     * 设置右侧选项的背景
     * @param color
     * @return
     */
    TwoOptMsgDialog setRightBgColor(@ColorInt int color);

    /**
     * 设置右侧选项的背景
     * @param color
     * @return
     */
    TwoOptMsgDialog setRightBgColorRes(@ColorRes int color);
}
