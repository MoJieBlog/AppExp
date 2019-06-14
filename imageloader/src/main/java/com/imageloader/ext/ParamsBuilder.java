package com.imageloader.ext;

import android.view.View;

import com.imageloader.LoadListener;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-14
 */
public class ParamsBuilder<T> {

    String url;
    View view;
    int placeHolder;
    int errHolder;
    int width;
    int height;
    boolean skipMemory;
    LoadListener<T> loadListener;
}
