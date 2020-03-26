package com.lzp.appexp;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.base.BaseActivity;
import com.lzp.customview.kline.KLineView;
import com.lzp.customview.kline.Point;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-09-25
 */
public class KLineActivity extends BaseActivity {

    private static final String TAG = "KLineActivity";

    private KLineView kLinView;

    ArrayList<Point> point = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_k_line);
        initView();

        doTest();

    }

    public void initView() {
        kLinView = findViewById(R.id.kLinView);

        kLinView.refreshPoint(point);
    }

    public void getData() {
        for (int i = 0; i < 1000; i++) {
            if (i==200){
                Point point = new Point(System.currentTimeMillis(), 3.3f, true);
                point.setShowDate(true);
                this.point.add(point);
            }else
            if (i % 2 == 0) {
                point.add(new Point(System.currentTimeMillis(),1.3f,false));
            } else if (i % 3 == 0) {
                Point point = new Point(System.currentTimeMillis(), 3.3f, false);
                this.point.add(point);
            }else {
                point.add(new Point(System.currentTimeMillis(),2.3f,false));
            }
        }
    }

    void doTest(){

        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.await(3, TimeUnit.SECONDS);

                    Log.e(TAG, "run: all tasks have done.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.countDown();
                    Log.e(TAG, "run: task 1 has done.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(5000);
                    countDownLatch.countDown();
                    Log.e(TAG, "run: task 2 has done.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }


}
