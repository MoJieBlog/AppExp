package com.imageloader.interfaces;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-07-11
 */
public interface IDisplay {
    void into(View view);
    IDisplay placeHolder(int res);
    IDisplay errHolder(int res);
    IDisplay size(int width,int height);
    IDisplay skipMemory(boolean needMemory);
    IDisplay listener(IMGLoadListener<Drawable> listener);
}
