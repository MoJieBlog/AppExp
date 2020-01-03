package com.view.cardView;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * @describe: copy from {@link androidx.cardview.widget.CardViewDelegate}
 * @Author: lixiaopeng
 * @Date: 2020-01-03
 */
interface CardViewDelegate {
    void setCardBackground(Drawable drawable);
    Drawable getCardBackground();
    boolean getUseCompatPadding();
    boolean getPreventCornerOverlap();
    void setShadowPadding(int left, int top, int right, int bottom);
    void setMinWidthHeightInternal(int width, int height);
    View getCardView();
}
