package com.imageloader.ext;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.imageloader.ImageLoaderUtils;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-28
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {

    final int defaultDiskCacheSizeBytes = 1024 * 1024 * 200; // 100 MB
    final int defaultMemoryCacheSizeBytes = 1024 * 1024 * 50; // 50 MB

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        //super.applyOptions(context, builder);

        if (GlideImageLoader.config != null) {
            long memorySize;
            if (GlideImageLoader.config.getMaxMemoryCacheSize() == 0) {
                memorySize = defaultMemoryCacheSizeBytes;
            } else {
                memorySize = GlideImageLoader.config.getMaxMemoryCacheSize();
            }
            builder.setMemoryCache(new LruResourceCache(memorySize));

            String diskPath;
            if (TextUtils.isEmpty(GlideImageLoader.config.getDiskCachePath())) {
                diskPath = ImageLoaderUtils.getDefaultDiskCachePath(context);
            } else {
                diskPath = GlideImageLoader.config.getDiskCachePath();
            }

            long diskSize;
            if (GlideImageLoader.config.getMaxDiskCacheSize()==0){
                diskSize = defaultDiskCacheSizeBytes;
            }else{
                diskSize = GlideImageLoader.config.getMaxDiskCacheSize();
            }

            builder.setDiskCache(new DiskLruCacheFactory(diskPath,
                    diskSize));
        } else {
            builder.setMemoryCache(new LruResourceCache(defaultMemoryCacheSizeBytes));
            builder.setDiskCache(
                    new DiskLruCacheFactory(ImageLoaderUtils.getDefaultDiskCachePath(context), defaultDiskCacheSizeBytes)
            );
        }
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);
    }

    @Override
    public boolean isManifestParsingEnabled() {
        return super.isManifestParsingEnabled();
    }
}
