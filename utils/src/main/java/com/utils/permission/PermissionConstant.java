package com.utils.permission;

import android.Manifest;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-05-28
 */
public final class PermissionConstant {
    public static final String[] EXTERNAL_STORAGE_GROUP = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final String CAMERA = Manifest.permission.CAMERA;

}
