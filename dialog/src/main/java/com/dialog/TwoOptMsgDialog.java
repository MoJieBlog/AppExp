package com.dialog;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.dialog.base.BaseDialog;
import com.dialog.base.ITitle;
import com.dialog.base.ITwoOptDialog;

/**
 * @describe 两个选项带描述
 * @author: lixiaopeng
 * @Date: 2019-12-06
 */
public class TwoOptMsgDialog extends BaseDialog implements ITwoOptDialog, ITitle<TwoOptMsgDialog> {

    private ConstraintLayout rootView;
    private TextView titleTv;
    private TextView messageTv;
    private View lineMsgOpt;
    private View lineOpt;
    private TextView leftOptTv;
    private TextView rightOptTv;

    public TwoOptMsgDialog(@NonNull Context context) {
        this(context,0);
    }

    public TwoOptMsgDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        //默认中间显示
        setGravity(Gravity.CENTER);

        View inflate = LayoutInflater.from(context).inflate(R.layout.dialog_two_opt, null, false);
        setContentView(inflate);


        findView(inflate);
    }

    private void findView(View view) {
        rootView = view.findViewById(R.id.rootView);
        titleTv = view.findViewById(R.id.titleTv);
        messageTv = view.findViewById(R.id.messageTv);
        lineMsgOpt = view.findViewById(R.id.lineMsgOpt);
        lineOpt = view.findViewById(R.id.lineOpt);
        leftOptTv = view.findViewById(R.id.leftOptTv);
        rightOptTv = view.findViewById(R.id.rightOptTv);

        leftOptTv.setOnClickListener(v -> {
            if (optClickListener!=null){
                optClickListener.leftOptClick(v);
            }
        });

        rightOptTv.setOnClickListener(v->{
            if (optClickListener!=null){
                optClickListener.rightOptClick(v);
            }
        });
    }

    /**
     * 设置左侧文字
     *
     * @param text
     * @return
     */
    @Override
    public TwoOptMsgDialog setLeftText(String text) {
        leftOptTv.setText(text);
        return this;
    }

    /**
     * 设置左侧文字
     *
     * @param text
     * @return
     */
    @Override
    public TwoOptMsgDialog setLeftText(@StringRes int text) {
        leftOptTv.setText(getContext().getResources().getString(text));
        return this;
    }

    /**
     * 设置右侧文字
     *
     * @param text
     * @return
     */
    @Override
    public TwoOptMsgDialog setRightText(String text) {
        rightOptTv.setText(text);
        return this;
    }

    /**
     * 设置右侧文字
     *
     * @param text
     * @return
     */
    @Override
    public TwoOptMsgDialog setRightText(@StringRes int text) {
        rightOptTv.setText(getContext().getResources().getString(text));
        return this;
    }

    /**
     * 设置左侧文字大小
     *
     * @param size
     * @return
     */
    @Override
    public TwoOptMsgDialog setLeftTextSize(int size) {
        leftOptTv.setTextSize(size);
        return this;
    }

    /**
     * 设置右侧文字大小
     *
     * @param size
     * @return
     */
    @Override
    public TwoOptMsgDialog setRightTextSize(int size) {
        rightOptTv.setTextSize(size);
        return this;
    }

    /**
     * 设置左侧文字颜色
     *
     * @param color
     * @return
     */
    @Override
    public TwoOptMsgDialog setLeftTextColorRes(@ColorRes int color) {
        leftOptTv.setTextColor(getContext().getResources().getColor(color));
        return this;
    }

    /**
     * 设置左侧文字颜色
     *
     * @param color
     * @return
     */
    @Override
    public TwoOptMsgDialog setLeftTextColor(@ColorInt int color) {
        leftOptTv.setTextColor(color);
        return this;
    }

    /**
     * 设置右侧文字颜色
     *
     * @param color
     * @return
     */
    @Override
    public TwoOptMsgDialog setRightTextColorRes(@ColorRes int color) {
        rightOptTv.setTextColor(getContext().getResources().getColor(color));
        return this;
    }

    /**
     * 设置右侧文字颜色
     *
     * @param color
     * @return
     */
    @Override
    public TwoOptMsgDialog setRightTextColor(@ColorInt int color) {
        rightOptTv.setTextColor(color);
        return this;
    }

    /**
     * 设置左侧文字字体
     *
     * @param typeface
     * @return
     */
    @Override
    public TwoOptMsgDialog setLeftTextTypeface(Typeface typeface) {
        leftOptTv.setTypeface(typeface);
        return this;
    }

    /**
     * 设置右侧文字字体
     *
     * @param typeFace
     * @return
     */
    @Override
    public TwoOptMsgDialog setRightTextTypeFace(Typeface typeFace) {
        rightOptTv.setTypeface(typeFace);
        return this;
    }

    /**
     * 设置左侧文字是否加粗
     *
     * @param bold
     * @return
     */
    @Override
    public TwoOptMsgDialog setLeftTextBold(boolean bold) {
        if (bold){
            leftOptTv.setTypeface(Typeface.DEFAULT_BOLD);
        }else{
            leftOptTv.setTypeface(Typeface.DEFAULT);
        }
        return this;
    }

    /**
     * 设置右侧文字是否加粗
     *
     * @param bold
     * @return
     */
    @Override
    public TwoOptMsgDialog setRightTextBold(boolean bold) {
        if (bold){
            rightOptTv.setTypeface(Typeface.DEFAULT_BOLD);
        }else{
            rightOptTv.setTypeface(Typeface.DEFAULT);
        }
        return this;
    }

    /**
     * 显示选项间的分割线
     *
     * @param show
     * @return
     */
    @Override
    public TwoOptMsgDialog showOptGapLine(boolean show) {
        lineOpt.setVisibility(show?View.VISIBLE:View.GONE);
        return this;
    }

    /**
     * 显示msg和选项间的分割线
     *
     * @param show
     * @return
     */
    @Override
    public TwoOptMsgDialog showMsgOptGapLine(boolean show) {
        lineMsgOpt.setVisibility(show?View.VISIBLE:View.GONE);
        return this;
    }

    /**
     * 设置左侧选项的背景
     *
     * @param bgRes
     * @return
     */
    @Override
    public TwoOptMsgDialog setLeftBg(@DrawableRes int bgRes) {
        leftOptTv.setBackgroundResource(bgRes);
        return this;
    }

    /**
     * 设置左侧选项的背景
     *
     * @param color
     * @return
     */
    @Override
    public TwoOptMsgDialog setLeftBgColor(@ColorInt int color) {
        leftOptTv.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置左侧选项的背景
     *
     * @param color
     * @return
     */
    @Override
    public TwoOptMsgDialog setLeftBgColorRes(@ColorRes int color) {
        leftOptTv.setBackgroundColor(getContext().getResources().getColor(color));
        return this;
    }

    /**
     * 设置右侧选项的背景
     *
     * @param bgRes
     * @return
     */
    @Override
    public TwoOptMsgDialog setRightBg(@DrawableRes int bgRes) {
        rightOptTv.setBackgroundResource(bgRes);
        return this;
    }

    /**
     * 设置右侧选项的背景
     *
     * @param color
     * @return
     */
    @Override
    public TwoOptMsgDialog setRightBgColor(@ColorInt int color) {
        rightOptTv.setBackgroundColor(color);
        return this;
    }

    /**
     * 设置右侧选项的背景
     *
     * @param color
     * @return
     */
    @Override
    public TwoOptMsgDialog setRightBgColorRes(@ColorRes int color) {
        rightOptTv.setBackgroundColor(getContext().getResources().getColor(color));
        return this;
    }

    /**
     * 信息文字
     *
     * @param text
     * @return
     */
    @Override
    public TwoOptMsgDialog setMsgText(String text) {
        messageTv.setText(text);
        return this;
    }

    /**
     * 信息文字
     *
     * @param textRes
     * @return
     */
    @Override
    public TwoOptMsgDialog setMsgText(int textRes) {
        messageTv.setText(getContext().getResources().getString(textRes));
        return this;
    }

    /**
     * 信息文字大小
     *
     * @param size
     * @return
     */
    @Override
    public TwoOptMsgDialog setMsgTextSize(int size) {
        messageTv.setTextSize(size);
        return this;
    }

    /**
     * 信息文字颜色
     *
     * @param colorRes
     * @return
     */
    @Override
    public TwoOptMsgDialog setMsgTextColorRes(int colorRes) {
        messageTv.setTextColor(getContext().getResources().getColor(colorRes));
        return this;
    }

    /**
     * 信息文字颜色
     *
     * @param color
     * @return
     */
    @Override
    public TwoOptMsgDialog setMsgTextColor(int color) {
        messageTv.setTextColor(color);
        return this;
    }


    /**
     * 设置title是否显示
     *
     * @param visible
     * @return
     */
    @Override
    public TwoOptMsgDialog setTitleVisible(int visible) {
        titleTv.setVisibility(visible);
        return this;
    }

    /**
     * 设置title文字
     *
     * @param text
     * @return
     */
    @Override
    public TwoOptMsgDialog setTitleText(String text) {
        titleTv.setText(text);
        return this;
    }

    /**
     * 设置title文字
     *
     * @param text
     * @return
     */
    @Override
    public TwoOptMsgDialog setTitleText(@StringRes int text) {
        titleTv.setText(getContext().getResources().getString(text));
        return this;
    }

    /**
     * 设置title文字颜色
     *
     * @param color
     * @return
     */
    @Override
    public TwoOptMsgDialog setTitleColor(@ColorInt int color) {
        titleTv.setTextColor(color);
        return this;
    }

    /**
     * 设置title文字颜色
     *
     * @param color
     * @return
     */
    @Override
    public TwoOptMsgDialog setTitleColorRes(@ColorRes int color) {
        titleTv.setTextColor(getContext().getResources().getColor(color));
        return this;
    }

    /**
     * 设置title文字大小
     *
     * @param size
     * @return
     */
    @Override
    public TwoOptMsgDialog setTitleSize(int size) {
        titleTv.setTextSize(size);
        return this;
    }

    /**
     * 设置title文字是否加粗
     *
     * @param bold
     * @return
     */
    @Override
    public TwoOptMsgDialog setTitleBold(boolean bold) {
        if (bold){
            titleTv.setTypeface(Typeface.DEFAULT_BOLD);
        }else{
            titleTv.setTypeface(Typeface.DEFAULT);
        }
        return this;
    }

    private OnOptClickListener optClickListener;

    public TwoOptMsgDialog setOptClickListener(OnOptClickListener optClickListener) {
        this.optClickListener = optClickListener;
        return this;
    }

    public interface OnOptClickListener{
        void leftOptClick(View v);
        void rightOptClick(View v);
    }
}
