package com.lzp.copyui.message;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.base.BaseActivity;
import com.lzp.copyui.R;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-12-04
 */
public class MessageActivity extends BaseActivity implements OnClickListener {

    private MessageCardView msgCard;
    private Button btnShow;
    private Button btnDismiss;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }

    @Override
    public void findView() {
        msgCard = findViewById(R.id.msgCard);
        btnShow = findViewById(R.id.btnShow);
        btnDismiss = findViewById(R.id.btnDismiss);
    }

    @Override
    public void setListener() {
        btnShow.setOnClickListener(this);
        btnDismiss.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnDismiss){
            msgCard.dismiss(true);
        }else if(v.getId()==R.id.btnShow){
            msgCard.showWithAnimation();
        }
    }
}
