package com.view.cardView;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.RequiresApi;

/**
 * @describe: copy from {@link androidx.cardview.widget.CardViewApi17Impl}
 * @Author: lixiaopeng
 * @Date: 2020-01-03
 */
@RequiresApi(17)
class CardViewApi17Impl extends CardViewBaseImpl {

    @Override
    public void initStatic() {
        RoundRectDrawableWithShadow.sRoundRectHelper =
                new RoundRectDrawableWithShadow.RoundRectHelper() {
                    @Override
                    public void drawRoundRect(Canvas canvas, RectF bounds, float cornerRadius,
                                              Paint paint) {
                        canvas.drawRoundRect(bounds, cornerRadius, cornerRadius, paint);
                    }
                };
    }
}
