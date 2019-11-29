package com.lzp.appexp.transition.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lzp.appexp.R;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-24
 */
public class CarCardView extends CardView {

    private ImageView ivCar;
    private RelativeLayout rootView;

    public CarCardView(@NonNull Context context) {
        this(context, null);
    }

    public CarCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ivCar = findViewById(R.id.ivCar);
        rootView = findViewById(R.id.rootView);
    }

    public void setCarImgVisible(int visible) {
        ivCar.setVisibility(visible);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setRootBgColor(int color){
        rootView.setBackgroundColor(color);
    }
}
