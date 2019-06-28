package com.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.imageloader.ext.GlideLoader;

import java.io.File;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-13
 */
public class ImageLoader implements ILoader{

    private ILoader loader;



    public static ImageLoader get(Context context){
        return new ImageLoader(context);
    }


    private ImageLoader(Context context) {
        loader = GlideLoader.get(context);
    }

    @Override
    public void init(ImageLoaderConfig config) {
        loader.init(config);
    }

    @Override
    public void clearCache() {
        loader.clearCache();
    }

    @Override
    public IImageLoader load(String url) {
        return loader.load(url);
    }

    @Override
    public String getDiskCachePath() {
        return loader.getDiskCachePath();
    }

    @Override
    public IImageLoader into(View view) {
        return loader.into(view);
    }

    @Override
    public IImageLoader placeHolder(int res) {
        return loader.placeHolder(res);
    }

    @Override
    public IImageLoader errHolder(int res) {
        return loader.errHolder(res);
    }

    @Override
    public IImageLoader width(int width) {
        return loader.width(width);
    }

    @Override
    public IImageLoader height(int height) {
        return loader.height(height);
    }

    @Override
    public IImageLoader skipMemory(boolean needMemory) {
        return loader.skipMemory(needMemory);
    }

    @Override
    public void display() {
        loader.display();
    }

    @Override
    public void download() {
        loader.download();
    }

    @Override
    public void download(IMGLoadListener<File> listener) {
        loader.download(listener);
    }

    @Override
    public void display(IMGLoadListener<Drawable> listener) {
        loader.display(listener);
    }
}
