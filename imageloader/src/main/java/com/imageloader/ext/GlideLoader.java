package com.imageloader.ext;

import android.content.Context;
import android.view.View;

import com.imageloader.IImageLoader;
import com.imageloader.ILoader;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-13
 */
public class GlideLoader implements ILoader ,IImageLoader{

    @Override
    public IImageLoader get(Context context) {
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
