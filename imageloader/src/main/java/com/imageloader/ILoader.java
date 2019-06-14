package com.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.File;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-14
 */
public interface ILoader {

    IImageLoader<Bitmap> asBitmap();
    IImageLoader<Drawable> asDrawable();
    IImageLoader<Gif> asGif();
    IImageLoader<File> asFile();


    //功能，设置网络请求
    ILoader setRequestClient();
    //功能，设置缓存策略
    ILoader setCacheRule();
    //功能，清除缓存
    ILoader  clearCache();
    //功能，设置内存缓存最大值
    ILoader setMaxMemoryCacheSize();
    //功能，设置硬盘缓存最大值
    ILoader  setMaxDiskCacheSize();
    //功能，获取硬盘缓存大小
    ILoader getDiskCachePath();
    //功能，设置缓存路径
    ILoader  setDiskCachePath();
}
