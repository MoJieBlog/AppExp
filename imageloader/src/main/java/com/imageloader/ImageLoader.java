package com.imageloader;

import android.app.Application;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.data.DataFetcher;
import com.imageloader.ext.GlideLoader;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-13
 */
public class ImageLoader implements IImageLoader {

    private static ImageLoader instance;
    private static Application application;

    private IImageLoader loader;

    public static ImageLoader getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }

        return instance;
    }

    private ImageLoader() {
        Glide glide = Glide.get(application);
        loader = new GlideLoader();
    }

    public ImageLoader init(Application application) {
        ImageLoader.application = application;
        return this;
    }


    @Override
    public IImageLoader into(View view) {
        return null;
    }

    @Override
    public IImageLoader placeHolder(int res) {
        return null;
    }

    @Override
    public IImageLoader errHolder(int res) {
        return null;
    }
}
