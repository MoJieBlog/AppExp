package com.lzp.appexp;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.imageloader.IMGLoadListener;
import com.imageloader.ImageLoader;
import com.view.refresh.SwipeRefreshLayout;
import com.view.refresh.ext.NiuLoadingLayout;

import java.io.File;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    NiuLoadingLayout niuLoadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        niuLoadingLayout = findViewById(R.id.niu);


        ImageView imageView = findViewById(R.id.iv);
        final ImageView imageView1 = findViewById(R.id.iv1);
        final String url = "http://pic37.nipic.com/20140113/8800276_184927469000_2.png";

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Glide.with(MainActivity.this).asFile()
                            .load(new URL(url))
                            .listener(new RequestListener<File>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<File> target, boolean b) {
                            Log.e(TAG, "onLoadFailed: ");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(File file, Object o, Target<File> target, DataSource dataSource, boolean b) {
                            Log.e(TAG, "onResourceReady: "+file.getPath());
                            return false;
                        }
                    }).submit(100,100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();



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

        /*OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url("").tag("").build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });*/


    }
}
