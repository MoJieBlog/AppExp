package com.utils.permission;

import com.utils.LogUtils;

import java.util.List;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-05-28
 */
public abstract class PermissionListenerAdapter implements PermissionListener{
    private static final String TAG = "PermissionListenerAdapt";
    @Override
    public void onGranted() {
        LogUtils.e(TAG, "onGranted: 权限获取成功");
    }

    @Override
    public void onDenied(List<String> deniedPermissions,boolean neverTips) {
        LogUtils.e(TAG, "onDenied: 权限获取失败");
    }
}
