package com.view.cardView;

import android.content.Context;
import android.content.res.ColorStateList;

import androidx.annotation.Nullable;

/**
 * @describe: copy from {@link androidx.cardview.widget.CardViewImpl}
 * @Author: lixiaopeng
 * @Date: 2020-01-03
 */
interface CardViewImpl {

    //modify 增加阴影颜色，和光来的方向
    void initialize(CardViewDelegate cardView, Context context, ColorStateList backgroundColor,
                    float radius, float elevation, float maxElevation,int shadowStartColor,int shadowEndColor,float shadowDelegate);

    void initialize(CardViewDelegate cardView, Context context, ColorStateList backgroundColor,
                    float radius, float elevation, float maxElevation);

    void setRadius(CardViewDelegate cardView, float radius);

    float getRadius(CardViewDelegate cardView);

    void setElevation(CardViewDelegate cardView, float elevation);

    float getElevation(CardViewDelegate cardView);

    void initStatic();

    void setMaxElevation(CardViewDelegate cardView, float maxElevation);

    float getMaxElevation(CardViewDelegate cardView);

    float getMinWidth(CardViewDelegate cardView);

    float getMinHeight(CardViewDelegate cardView);

    void updatePadding(CardViewDelegate cardView);

    void onCompatPaddingChanged(CardViewDelegate cardView);

    void onPreventCornerOverlapChanged(CardViewDelegate cardView);

    void setBackgroundColor(CardViewDelegate cardView, @Nullable ColorStateList color);

    ColorStateList getBackgroundColor(CardViewDelegate cardView);
}
