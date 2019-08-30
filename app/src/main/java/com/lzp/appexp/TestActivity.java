package com.lzp.appexp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.imageloader.ImageLoader;

public class TestActivity extends AppCompatActivity {
    TextView tv;
    Button btn;

    private static final String TAG = "TestActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.WHITE);

        setContentView(R.layout.activity_test);

        ImageView iv = findViewById(R.id.iv);

        ImageLoader.get(this).display("http://pic37.nipic.com/20140113/8800276_184927469000_2.png").into(iv);

        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });
    }
    Builder notificationBuilder;

    private void showNotification() {
        NotificationManager mManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "NiuAppDownload";
            NotificationChannel channel = new NotificationChannel("app", name, NotificationManager.IMPORTANCE_DEFAULT);
            mManager.createNotificationChannel(channel);
        }
        Builder notificationBuilder = new Builder(this, "app");
        Notification mNotification = notificationBuilder
                .setContentTitle("测试")
                .setContentText("66666666")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setOngoing(true)
                .setProgress(100,0,false)
                .setAutoCancel(false)
                .setOnlyAlertOnce(true)
                .build();

        mManager.notify(1,mNotification);
        mManager.notify(2,mNotification);
        mManager.notify(3,mNotification);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        supportFinishAfterTransition();

    }
}
