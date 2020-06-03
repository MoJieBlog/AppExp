package com.utils.permission;

import com.utils.LogUtils;

import java.util.List;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-05-28
 */
public interface PermissionListener {
    String TAG = "PermissionListener";

    /**
     * 拥有权限
     */
    default void onGranted() {
        LogUtils.e(TAG, "onGranted: 权限获取成功");
    }

    /**
     * 没有权限
     *
     * @param deniedPermissions 拒绝的权限的列表
     * @param neverTips         拒绝并且不再提醒
     */
    default void onDenied(List<String> deniedPermissions, boolean neverTips) {
        LogUtils.e(TAG, "onDenied: 权限获取失败" + (neverTips ? "且用户选择不再提示" : ""));
    }
}
