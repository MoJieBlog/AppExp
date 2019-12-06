package com.dialog.base;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.IntegerRes;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-12-06
 */
public interface IBaseDialog<T> {

    /**
     * 设置显示位置
     * @param gravity
     * @return
     */
    T setGravity(int gravity);

    /**
     * 设置title是否显示
     * @param visible
     * @return
     */
    T setTitleVisible(int visible);

    /**
     * 设置title文字
     * @param text
     * @return
     */
    T setTitleText(String text);
    /**
     * 设置title文字
     * @param text
     * @return
     */
    T setTitleText(@IntegerRes int text);

    /**
     * 设置title文字颜色
     * @param color
     * @return
     */
    T setTitleColor(@ColorInt int color);

    /**
     * 设置title文字颜色
     * @param color
     * @return
     */
    T setTitleColor(Color color);

    /**
     * 设置title文字大小
     * @param size
     * @return
     */
    T setTitleSize(int size);

    /**
     * 设置title文字是否加粗
     * @param bold
     * @return
     */
    T setTitleBold(boolean bold);
}
