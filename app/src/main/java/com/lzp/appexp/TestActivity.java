package com.lzp.appexp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.imageloader.ImageLoader;

public class TestActivity extends AppCompatActivity {
    TextView tv;

    private static final String TAG = "TestActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ImageView iv = findViewById(R.id.iv);

        ImageLoader.get(this).display("http://pic37.nipic.com/20140113/8800276_184927469000_2.png").into(iv).display();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        supportFinishAfterTransition();

    }
}
