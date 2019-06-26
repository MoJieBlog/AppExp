package com.imageloader.ext;

import android.content.Context;
import android.view.View;

import com.imageloader.IMGLoadListener;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-14
 */
public class Params {

    Context context;
    String url;
    View view;
    int placeHolder = 0;
    int errHolder = 0;
    int width;
    int height;
    boolean skipMemory = false;
    IMGLoadListener loadListener;
    int imgType = ImgType.Drawable;


    public Params() {

    }

    public Context getContext() {
        return context;
    }

    public String getUrl() {
        return url;
    }

    public View getView() {
        return view;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public int getErrHolder() {
        return errHolder;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isSkipMemory() {
        return skipMemory;
    }

    public IMGLoadListener getLoadListener() {
        return loadListener;
    }

    public int getImgType() {
        return imgType;
    }

    interface ImgType{
        int Bitmap = 1;
        int Drawable = 2;
        int Gif = 3;
        int File = 4;
    }
}
