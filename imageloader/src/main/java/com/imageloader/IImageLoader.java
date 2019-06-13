package com.imageloader;

import android.content.Context;
import android.view.View;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-13
 */
public interface IImageLoader {

    IImageLoader into(View view);

    IImageLoader placeHolder(int res);

    IImageLoader errHolder(int res);
}
