package com.imageloader;

import android.content.Context;

import com.imageloader.ext.GlideImageLoader;
import com.imageloader.interfaces.IDisplay;
import com.imageloader.interfaces.IImageLoader;
import com.imageloader.interfaces.ILoader;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-13
 */
public class ImageLoader implements IImageLoader{

    private IImageLoader imageLoader;

    public static IImageLoader get(Context context){
        return new ImageLoader(context);
    }

    private ImageLoader(Context context) {
        imageLoader = GlideImageLoader.get(context);
    }


    @Override
    public ILoader load(String url) {
        return imageLoader.load(url);
    }

    @Override
    public IDisplay display(String url) {
        return imageLoader.display(url);
    }

    @Override
    public String getDiskCachePath() {
        return imageLoader.getDiskCachePath();
    }

    @Override
    public void clearCache() {
        imageLoader.clearCache();
    }

    @Override
    public void init(ImageLoaderConfig config) {
        imageLoader.init(config);
    }

}
