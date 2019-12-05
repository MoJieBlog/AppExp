package com.dialog;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.IntegerRes;

/**
 * @describe 单选项弹窗
 * @author: lixiaopeng
 * @Date: 2019-12-05
 */
public interface IOneOptDialog {

    /**
     * 设置选项文字
     * @param text
     * @return
     */
    BaseOneOptDialog setText(String text);

    /**
     * 设置选项文字
     * @param textRes
     * @return
     */
    BaseOneOptDialog setText(@IntegerRes int textRes);

    /**
     * 设置选项文字大小
     * @param size
     * @return
     */
    BaseOneOptDialog setTextSize(int size);


    /**
     * 设置选项文字颜色
     * @param color
     * @return
     */
    BaseOneOptDialog setTextColor(Color color);

    /**
     * 设置选项文字颜色
     * @param color
     * @return
     */
    BaseOneOptDialog setTextColor(@IntegerRes int color);

    /**
     * 设置选项字体
     * @param typeFace
     * @return
     */
    BaseOneOptDialog setTextTypeFace(Typeface typeFace);

    /**
     * 设置选项文字是否加粗
     * @param bold
     * @return
     */
    BaseOneOptDialog setTextBold(boolean bold);
}
