package com.lzp.appexp.car.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.lzp.appexp.R;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-24
 */
public class CarCardView extends FrameLayout {

    private ImageView ivCar;

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
    }

    public void setCarImgVisible(int visible) {
        ivCar.setVisibility(visible);
    }

}
