package com.lzp.appexp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.utils.LogUtils;
import com.utils.permission.PermissionConstant;
import com.utils.permission.PermissionListener;
import com.utils.permission.PermissionListenerAdapter;
import com.utils.permission.PermissionUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
}
