package com.dialog;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;

import java.util.List;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-12-05
 */
public interface IMultipleOptDialog<T,D> {

    /**
     * 设置List
     *
     * @param list
     * @return
     */
    T setList(List<D> list);

    /**
     * 设置选项文字大小
     *
     * @param size
     * @return
     */
    T setTextSize(int size);

    /**
     * 设置字体
     *
     * @param typeFace
     * @return
     */
    T setTextTypeFace(Typeface typeFace);

    /**
     * 设置颜色
     *
     * @param color
     * @return
     */
    T setTextColor(Color color);

    /**
     * 设置颜色
     *
     * @param color
     * @return
     */
    T setTextColor(@ColorInt int color);

    /**
     * 设置是否加粗
     *
     * @param bold
     * @return
     */
    T setTextBold(boolean bold);

    /**
     * 设置填充数据
     *
     * @param position
     * @param t
     * @return
     */
    T fillData(int position, D t);
}
