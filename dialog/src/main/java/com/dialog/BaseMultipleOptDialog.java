package com.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-12-05
 */
public class BaseMultipleOptDialog<T> extends BaseDialog implements IMultipleOptDialog<T>{

    public BaseMultipleOptDialog(@NonNull Context context) {
        super(context);
    }

    public BaseMultipleOptDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    /**
     * 设置List
     *
     * @param list
     * @return
     */
    @Override
    public BaseMultipleOptDialog setList(List<T> list) {
        return null;
    }

    /**
     * 设置选项文字大小
     *
     * @param size
     * @return
     */
    @Override
    public BaseMultipleOptDialog setTextSize(int size) {
        return null;
    }

    /**
     * 设置字体
     *
     * @param typeFace
     * @return
     */
    @Override
    public BaseMultipleOptDialog setTextTypeFace(Typeface typeFace) {
        return null;
    }

    /**
     * 设置颜色
     *
     * @param color
     * @return
     */
    @Override
    public BaseMultipleOptDialog setTextColor(Color color) {
        return null;
    }

    /**
     * 设置颜色
     *
     * @param color
     * @return
     */
    @Override
    public BaseMultipleOptDialog setTextColor(int color) {
        return null;
    }

    /**
     * 设置是否加粗
     *
     * @param bold
     * @return
     */
    @Override
    public BaseMultipleOptDialog setTextBold(boolean bold) {
        return null;
    }

    /**
     * 设置填充数据
     *
     * @param position
     * @param t
     * @return
     */
    @Override
    public BaseMultipleOptDialog fillData(int position, T t) {
        return null;
    }
}
