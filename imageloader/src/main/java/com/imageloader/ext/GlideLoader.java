package com.imageloader.ext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.imageloader.Gif;
import com.imageloader.IImageLoader;
import com.imageloader.ILoader;
import com.imageloader.IMGLoadListener;
import com.imageloader.ImageLoaderConfig;

import java.io.File;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-13
 */
public class GlideLoader implements ILoader,IImageLoader {


    private ParamsBuilder paramsBuilder;

    public static GlideLoader get(Context context){
        return new GlideLoader(context);
    }

    private GlideLoader(Context context) {
        paramsBuilder = new ParamsBuilder();
        paramsBuilder.context = context;
    }


    @Override
    public IImageLoader into(View view) {
        paramsBuilder.view = view;
        return this;
    }

    @Override
    public IImageLoader placeHolder(int res) {
        paramsBuilder.placeHolder = res;
        return this;
    }

    @Override
    public IImageLoader errHolder(int res) {
        paramsBuilder.errHolder = res;
        return this;
    }

    @Override
    public IImageLoader width(int width) {
        paramsBuilder.width = width;
        return this;
    }

    @Override
    public IImageLoader height(int height) {
        paramsBuilder.height = height;
        return this;
    }

    @Override
    public IImageLoader skipMemory(boolean needMemory) {
        paramsBuilder.skipMemory = needMemory;
        return this;
    }

    @Override
    public IImageLoader listener(IMGLoadListener listener) {
        paramsBuilder.loadListener = listener;
        return this;
    }

    @Override
    public void display() {

    }

    @Override
    public void load() {

    }

    @Override
    public IImageLoader<Bitmap> load(String url) {
        paramsBuilder.url = url;
        return this;
    }

    @Override
    public IImageLoader<Bitmap> asBitmap() {
        paramsBuilder.imgType = ParamsBuilder.ImgType.Bitmap;
        return this;
    }

    @Override
    public IImageLoader<Drawable> asDrawable() {
        paramsBuilder.imgType = ParamsBuilder.ImgType.Drawable;
        return this;
    }

    @Override
    public IImageLoader<Gif> asGif() {
        paramsBuilder.imgType = ParamsBuilder.ImgType.Gif;
        return this;
    }

    @Override
    public IImageLoader<File> asFile() {
        paramsBuilder.imgType = ParamsBuilder.ImgType.File;
        return this;
    }

    @Override
    public void clearCache() {
        Glide.get(paramsBuilder.context).clearDiskCache();
        Glide.get(paramsBuilder.context).clearMemory();
    }

    @Override
    public void init(ImageLoaderConfig config) {
        //TODO 初始化
    }


    @Override
    public String getDiskCachePath() {
        return null;
    }


}
