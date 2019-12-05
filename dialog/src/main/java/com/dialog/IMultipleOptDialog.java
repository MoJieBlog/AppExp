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
public interface IMultipleOptDialog<T> {

    /**
     * 设置List
     *
     * @param list
     * @return
     */
    BaseMultipleOptDialog setList(List<T> list);

    /**
     * 设置选项文字大小
     *
     * @param size
     * @return
     */
    BaseMultipleOptDialog setTextSize(int size);

    /**
     * 设置字体
     *
     * @param typeFace
     * @return
     */
    BaseMultipleOptDialog setTextTypeFace(Typeface typeFace);

    /**
     * 设置颜色
     *
     * @param color
     * @return
     */
    BaseMultipleOptDialog setTextColor(Color color);

    /**
     * 设置颜色
     *
     * @param color
     * @return
     */
    BaseMultipleOptDialog setTextColor(@ColorInt int color);

    /**
     * 设置是否加粗
     *
     * @param bold
     * @return
     */
    BaseMultipleOptDialog setTextBold(boolean bold);

    /**
     * 设置填充数据
     *
     * @param position
     * @param t
     * @return
     */
    BaseMultipleOptDialog fillData(int position, T t);
}
