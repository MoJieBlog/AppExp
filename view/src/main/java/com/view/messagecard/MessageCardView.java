package com.view.messagecard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.view.R;
import com.view.RoundRelativeLayout;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-11-13
 */
public class MessageCardView extends RoundRelativeLayout {
    public MessageCardView(@NonNull Context context) {
        this(context,null);
    }

    public MessageCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundResource(R.drawable.rect_gradient_ffa240_ff8c49);
        inflate(context, R.layout.message_car_layout,this);
    }


}
