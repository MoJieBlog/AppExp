package com.imageloader.ext;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-28
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {

    final int diskCacheSizeBytes = 1024 * 1024 * 100; // 100 MB
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //super.applyOptions(context, builder);


        Log.e("====", "applyOptions: "+getStorageDirectory(context));
        builder.setDiskCache(
                new DiskLruCacheFactory( getStorageDirectory(context)+"/GlideDisk", diskCacheSizeBytes )
        );
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }

    //外部路径
    private String sdRootPath = Environment.getExternalStorageDirectory().getPath();
    private String appRootPath = null;

    private String getStorageDirectory(Context context){
        //手机app路径
        appRootPath = context.getCacheDir().getPath();
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
                sdRootPath : appRootPath;
    }
}
