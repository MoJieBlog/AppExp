package com.imageloader;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-14
 */
public interface ILoader extends IImageLoader {

    IImageLoader load(String url);

    //功能，获取硬盘缓存路径
    String getDiskCachePath();

    //功能，清除缓存
    void clearCache();

    void init(ImageLoaderConfig config);
}
