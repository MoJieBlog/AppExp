package com.utils.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-05-28
 */
public final class PermissionUtils {

    /**
     * 检查权限
     *
     * @param activity
     * @param permissions 权限集合
     */
    public static boolean hasPermissions(Activity activity, String[] permissions) {
        boolean hasPer = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                hasPer = false;
                break;
            }
        }
        return hasPer;
    }

    /**
     * 检查是否拥有单一权限
     * @param context
     * @param permission
     * @return
     */
    public static boolean hasPermission(Context context, String permission){
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }


    public static void getPermission(Activity activity, String[] permissions){
        getPermission(activity,permissions,null);
    }
    /**
     * 获取权限
     *getPermission
     * @param activity
     * @param permissions
     */
    public static void getPermission(Activity activity, String[] permissions,PermissionListener listener) {
        PermissionActivity.setPermissionListener(listener);
        PermissionActivity.setPermissions(permissions);
        Intent intent = new Intent(activity,PermissionActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(0,0);
    }


    public static boolean isLocationEnable(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria cri = new Criteria();
        cri.setAccuracy(Criteria.ACCURACY_COARSE);
        cri.setAltitudeRequired(false);
        cri.setBearingRequired(false);
        cri.setCostAllowed(false);
        String bestProvider = locationManager.getBestProvider(cri, true);
        return !TextUtils.isEmpty(bestProvider);
    }
}
