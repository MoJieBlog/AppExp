package com.imageloader;

import android.graphics.drawable.Drawable;
import android.view.View;

import java.io.File;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-13
 */
public interface IImageLoader {

    IImageLoader into(View view);
    IImageLoader placeHolder(int res);
    IImageLoader errHolder(int res);
    IImageLoader width(int width);
    IImageLoader height(int height);
    IImageLoader skipMemory(boolean needMemory);

    void display();
    void display(IMGLoadListener<Drawable> listener);

    void download();
    void download(IMGLoadListener<File> listener);

}
