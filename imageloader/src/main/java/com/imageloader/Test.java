package com.imageloader;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-14
 */
public class Test {
    void test(Context context){

        ImageLoader.get(context).asBitmap().listener(new IMGLoadListener<Bitmap>() {
            @Override
            public void success(Bitmap bitmap) {

            }

            @Override
            public void fail(Exception e) {

            }
        });

        ImageLoader.get(context).load("").listener(new IMGLoadListener<Bitmap>() {
            @Override
            public void success(Bitmap bitmap) {

            }

            @Override
            public void fail(Exception e) {

            }
        });

    }



}
