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


        PermissionUtils.getPermission(this, PermissionConstant.EXTERNAL_STORAGE_GROUP, new PermissionListenerAdapter() {
            @Override
            public void onGranted() {
                super.onGranted();
                LogUtils.e(TAG,"获取权限成功");
            }

            /*@Override
            public void onDenied(List<String> deniedPermissions) {
                super.onDenied(deniedPermissions);
                LogUtils.e(TAG,"获取权限失败");
            }*/
        });

        PermissionUtils.getPermission(this, PermissionConstant.EXTERNAL_STORAGE_GROUP, new PermissionListener() {
            @Override
            public void onGranted() {
                LogUtils.e(TAG,"获取权限成功");
            }

            @Override
            public void onDenied(List<String> deniedPermissions) {
                LogUtils.e(TAG,"获取权限失败");
            }
        });
    }
}
