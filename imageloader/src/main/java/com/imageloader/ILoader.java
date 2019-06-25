package com.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import java.io.File;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-14
 */
public interface ILoader extends IImageLoader {

    IImageLoader<Bitmap> load(String url);

    IImageLoader<Bitmap> asBitmap();

    IImageLoader<Drawable> asDrawable();

    IImageLoader<Gif> asGif();

    IImageLoader<File> asFile();

    //功能，获取硬盘缓存路径
    String getDiskCachePath();

    //功能，清除缓存
    void clearCache();

    void init(ImageLoaderConfig config);
}
