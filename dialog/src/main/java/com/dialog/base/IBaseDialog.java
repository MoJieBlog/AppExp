package com.dialog.base;

import androidx.annotation.StyleRes;

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

    //动画
    T setWindowAnimations(@StyleRes int animations);
}
