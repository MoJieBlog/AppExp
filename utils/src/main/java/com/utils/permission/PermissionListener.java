package com.utils.permission;

import java.util.List;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-05-28
 */
public interface PermissionListener {
    /**
     * 拥有权限
     */
    void onGranted();

    /**
     * 没有权限
     *
     * @param deniedPermissions 拒绝的权限的列表
     */
    void onDenied(List<String> deniedPermissions);
}
