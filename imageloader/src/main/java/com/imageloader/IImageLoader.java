package com.imageloader;

import android.view.View;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-13
 */
public interface IImageLoader<T> {

    IImageLoader<T> load(String url);
    IImageLoader into(View view);
    IImageLoader placeHolder(int res);
    IImageLoader errHolder(int res);
    IImageLoader width(int width);
    IImageLoader height(int height);
    IImageLoader skipMemory(boolean needMemory);

    IImageLoader<T> listener(IMGLoadListener<T> listener);
    void display();
    void load();

}
