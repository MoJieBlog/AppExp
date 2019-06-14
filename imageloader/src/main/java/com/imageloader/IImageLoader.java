package com.imageloader;

import android.view.View;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-13
 */
public interface IImageLoader<T> {

   // void displayImage(Context context, String url, View view,int errHolder,int placeHolder,boolean needCache);

    IImageLoader load(String url);
    IImageLoader into(View view);
    IImageLoader placeHolder(int res);
    IImageLoader errHolder(int res);
    IImageLoader width(int width);
    IImageLoader height(int height);
    IImageLoader skipMemory(int needMemory);
    IImageLoader<T> listener(LoadListener<T> listener);
    void display();
    void load();
}
