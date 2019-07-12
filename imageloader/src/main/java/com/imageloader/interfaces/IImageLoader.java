package com.imageloader.interfaces;

import com.imageloader.ImageLoaderConfig;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-13
 */
public interface IImageLoader {


    ILoader load(String url);
    IDisplay display(String url);

    //功能，获取硬盘缓存路径
    String getDiskCachePath();

    //功能，清除缓存
    void clearCache();

    void init(ImageLoaderConfig config);




}
