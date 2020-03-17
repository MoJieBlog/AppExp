package com.utils.permission;

import android.Manifest;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-05-28
 */
public final class PermissionConstant {
    //存储相关
    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String[] EXTERNAL_STORAGE_GROUP = {READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};

    //相机
    public static final String CAMERA = Manifest.permission.CAMERA;

}
