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
public abstract class BaseTwoOptDialog extends BaseDialog implements ITwoOptDialog<BaseTwoOptDialog>{


    public BaseTwoOptDialog(@NonNull Context context) {
        super(context);
    }

    public BaseTwoOptDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 设置左侧文字
     *
     * @param text
     * @return
     */
    @Override
    public BaseTwoOptDialog setLeftText(String text) {
        return this;
    }

    @Override
    public BaseTwoOptDialog setLeftText(int text) {
        return this;
    }

    /**
     * 设置右侧文字
     *
     * @param text
     * @return
     */
    @Override
    public BaseTwoOptDialog setRightText(String text) {
        return this;
    }

    /**
     * 设置右侧文字
     *
     * @param text
     * @return
     */
    @Override
    public BaseTwoOptDialog setRightText(int text) {
        return this;
    }

    /**
     * 设置左侧文字大小
     *
     * @param size
     * @return
     */
    @Override
    public BaseTwoOptDialog setLeftTextSize(int size) {
        return this;
    }

    /**
     * 设置右侧文字大小
     *
     * @param size
     * @return
     */
    @Override
    public BaseTwoOptDialog setRightTextSize(int size) {
        return this;
    }

    /**
     * 设置左侧文字颜色
     *
     * @param color
     * @return
     */
    @Override
    public BaseTwoOptDialog setLeftTextColor(Color color) {
        return this;
    }

    /**
     * 设置左侧文字颜色
     *
     * @param color
     * @return
     */
    @Override
    public BaseTwoOptDialog setLeftTextColor(int color) {
        return this;
    }

    /**
     * 设置右侧文字颜色
     *
     * @param color
     * @return
     */
    @Override
    public BaseTwoOptDialog setRightTextColor(Color color) {
        return this;
    }

    /**
     * 设置右侧文字颜色
     *
     * @param color
     * @return
     */
    @Override
    public BaseTwoOptDialog setRightTextColor(int color) {
        return this;
    }

    /**
     * 设置左侧文字字体
     *
     * @param typeface
     * @return
     */
    @Override
    public BaseTwoOptDialog setLeftTextTypeface(Typeface typeface) {
        return this;
    }

    /**
     * 设置右侧文字字体
     *
     * @param typeFace
     * @return
     */
    @Override
    public BaseTwoOptDialog setRightTextTypeFace(Typeface typeFace) {
        return this;
    }

    /**
     * 设置左侧文字是否加粗
     *
     * @param bold
     * @return
     */
    @Override
    public BaseTwoOptDialog setLeftTextBold(boolean bold) {
        return this;
    }

    /**
     * 设置右侧文字是否加粗
     *
     * @param bold
     * @return
     */
    @Override
    public BaseTwoOptDialog setRightTextBold(boolean bold) {
        return this;
    }
}
