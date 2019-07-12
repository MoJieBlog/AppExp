package com.imageloader;

import android.content.Context;
import android.os.Environment;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-07-12
 */
public final class ImageLoaderUtils {

    public static String getStorageDirectory(Context context) {
        //手机app路径
        String sdRootPath = Environment.getExternalStorageDirectory().getPath();
        String appRootPath = context.getCacheDir().getPath();
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
                sdRootPath : appRootPath;
    }
}
