package com.utils.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.utils.R;
import com.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.M)
public class PermissionActivity extends Activity {

    private static PermissionListener permissionListener;
    private static String[] permissions;

    public static void setPermissionListener(PermissionListener permissionListener) {
        PermissionActivity.permissionListener = permissionListener;
    }

    public static void setPermissions(String[] permissions) {
        PermissionActivity.permissions = permissions;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        requestPermissions(permissions, Utils.getPermissionRequestCode());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Utils.getPermissionRequestCode()) {
            List<String> deniedPermissions = new ArrayList<>();
            boolean neverRequest = false;
            for (String permission : permissions) {
                neverRequest = ActivityCompat.shouldShowRequestPermissionRationale(PermissionActivity.this,permission);
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    deniedPermissions.add(permission);
                }
            }
            if (deniedPermissions.isEmpty()) {
                if (permissionListener != null) permissionListener.onGranted();
            } else {
                if (permissionListener != null) permissionListener.onDenied(deniedPermissions,neverRequest);
            }
            finish();
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void finish() {
        permissionListener = null;
        permissions = null;
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
