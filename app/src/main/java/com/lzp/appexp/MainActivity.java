package com.lzp.appexp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.imageloader.ImageLoader;
import com.view.refresh.SwipeRefreshLayout;
import com.view.refresh.ext.NiuLoadingLayout;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    NiuLoadingLayout niuLoadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        niuLoadingLayout = findViewById(R.id.niu);


        ImageView imageView = findViewById(R.id.iv);

        ImageLoader.get(this)
                .load("http://pic37.nipic.com/20140113/8800276_184927469000_2.png")
                .placeHolder(R.mipmap.ic_launcher_round)
                .errHolder(R.mipmap.ic_launcher_round)
                .into(imageView)
                .display();

        ImageLoader.get(this)
                .asBitmap()
                .load("")
                .listener(null)
                .load();


        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // niuLoadingLayout.start();
            }
        });

        final SwipeRefreshLayout refreshLayout = findViewById(R.id.refresh);

        refreshLayout.setCanRefresh(true);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.stopRefresh();
                    }
                }, 5000);
            }
        });

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url("").tag("").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


    }
}
