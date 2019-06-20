package com.imageloader.ext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.imageloader.Gif;
import com.imageloader.IImageLoader;
import com.imageloader.ILoader;
import com.imageloader.IMGLoadListener;

import java.io.File;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-13
 */
public class GlideLoader implements ILoader,IImageLoader {


    public static GlideLoader get(Context context){
        //paramBuilder.context(context);
        return new GlideLoader();
    }

    private GlideLoader() {
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

    @Override
    public IImageLoader width(int width) {
        return null;
    }

    @Override
    public IImageLoader height(int height) {
        return null;
    }

    @Override
    public IImageLoader skipMemory(int needMemory) {
        return null;
    }

    @Override
    public IImageLoader listener(IMGLoadListener listener) {
        return null;
    }

    @Override
    public void display() {

    }

    @Override
    public void load() {

    }

    @Override
    public IImageLoader<Bitmap> asBitmap() {
        return null;
    }

    @Override
    public IImageLoader<Drawable> asDrawable() {
        return null;
    }

    @Override
    public IImageLoader<Gif> asGif() {
        return null;
    }

    @Override
    public IImageLoader<File> asFile() {
        return null;
    }

    @Override
    public ILoader load(String url) {
        return null;
    }

    @Override
    public ILoader setRequestClient() {
        return null;
    }

    @Override
    public ILoader setCacheRule() {
        return null;
    }

    @Override
    public ILoader clearCache() {
        return null;
    }

    @Override
    public ILoader setMaxMemoryCacheSize() {
        return null;
    }

    @Override
    public ILoader setMaxDiskCacheSize() {
        return null;
    }

    @Override
    public ILoader getDiskCachePath() {
        return null;
    }

    @Override
    public ILoader setDiskCachePath() {
        return null;
    }
}
