package com.dialog.base;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

/**
 * @describe 两个选项加标题，中间可以是文字，图片，输入框...
 * @author: lixiaopeng
 * @Date: 2019-12-05
 */
public abstract class BaseTwoOptDialog extends BaseDialog implements ITwoOptDialog<BaseTwoOptDialog> {


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

    public abstract static class TwoOptDialogBuilder implements ITwoOptDialog<TwoOptDialogBuilder>, IBaseDialog<TwoOptDialogBuilder> {


        protected TwoOptDialogBuilder(Context context) {
        }

        public abstract BaseTwoOptDialog getDialog();
        /**
         * 设置左侧文字
         *
         * @param text
         * @return
         */
        @Override
        public TwoOptDialogBuilder setLeftText(String text) {
            getDialog().setLeftText(text);
            return this;
        }

        @Override
        public TwoOptDialogBuilder setLeftText(int text) {
            getDialog().setLeftText(text);
            return this;
        }

        /**
         * 设置右侧文字
         *
         * @param text
         * @return
         */
        @Override
        public TwoOptDialogBuilder setRightText(String text) {
            getDialog().setRightText(text);
            return this;
        }

        /**
         * 设置右侧文字
         *
         * @param text
         * @return
         */
        @Override
        public TwoOptDialogBuilder setRightText(int text) {
            getDialog().setRightText(text);
            return this;
        }

        /**
         * 设置左侧文字大小
         *
         * @param size
         * @return
         */
        @Override
        public TwoOptDialogBuilder setLeftTextSize(int size) {
            getDialog().setLeftTextSize(size);
            return this;
        }

        /**
         * 设置右侧文字大小
         *
         * @param size
         * @return
         */
        @Override
        public TwoOptDialogBuilder setRightTextSize(int size) {
            getDialog().setRightTextSize(size);
            return this;
        }

        /**
         * 设置左侧文字颜色
         *
         * @param color
         * @return
         */
        @Override
        public TwoOptDialogBuilder setLeftTextColor(Color color) {
            getDialog().setLeftTextColor(color);
            return this;
        }

        /**
         * 设置左侧文字颜色
         *
         * @param color
         * @return
         */
        @Override
        public TwoOptDialogBuilder setLeftTextColor(@ColorInt int color) {
            getDialog().setLeftTextColor(color);
            return this;
        }

        /**
         * 设置右侧文字颜色
         *
         * @param color
         * @return
         */
        @Override
        public TwoOptDialogBuilder setRightTextColor(Color color) {
            getDialog().setRightTextColor(color);
            return this;
        }

        /**
         * 设置右侧文字颜色
         *
         * @param color
         * @return
         */
        @Override
        public TwoOptDialogBuilder setRightTextColor(int color) {
            getDialog().setRightTextColor(color);
            return this;
        }

        /**
         * 设置左侧文字字体
         *
         * @param typeface
         * @return
         */
        @Override
        public TwoOptDialogBuilder setLeftTextTypeface(Typeface typeface) {
            getDialog().setLeftTextTypeface(typeface);
            return this;
        }

        /**
         * 设置右侧文字字体
         *
         * @param typeFace
         * @return
         */
        @Override
        public TwoOptDialogBuilder setRightTextTypeFace(Typeface typeFace) {
            getDialog().setRightTextTypeFace(typeFace);
            return this;
        }

        /**
         * 设置左侧文字是否加粗
         *
         * @param bold
         * @return
         */
        @Override
        public TwoOptDialogBuilder setLeftTextBold(boolean bold) {
            getDialog().setLeftTextBold(bold);
            return this;
        }

        /**
         * 设置右侧文字是否加粗
         *
         * @param bold
         * @return
         */
        @Override
        public TwoOptDialogBuilder setRightTextBold(boolean bold) {
            getDialog().setRightTextBold(bold);
            return this;
        }

        @Override
        public TwoOptDialogBuilder setGravity(int gravity) {
            getDialog().setGravity(gravity);
            return this;
        }

        @Override
        public TwoOptDialogBuilder setTitleVisible(int visible) {
            getDialog().setTitleVisible(visible);
            return this;
        }

        @Override
        public TwoOptDialogBuilder setTitleText(String text) {
            getDialog().setTitleText(text);
            return this;
        }

        @Override
        public TwoOptDialogBuilder setTitleText(int text) {
            getDialog().setTitleText(text);
            return this;
        }

        @Override
        public TwoOptDialogBuilder setTitleColor(int color) {
            getDialog().setTitleColor(color);
            return this;
        }

        @Override
        public TwoOptDialogBuilder setTitleColor(Color color) {
            getDialog().setTitleColor(color);
            return this;
        }

        @Override
        public TwoOptDialogBuilder setTitleSize(int size) {
            getDialog().setTitleSize(size);
            return this;
        }

        @Override
        public TwoOptDialogBuilder setTitleBold(boolean bold) {
            getDialog().setTitleBold(bold);
            return this;
        }

    }
}
