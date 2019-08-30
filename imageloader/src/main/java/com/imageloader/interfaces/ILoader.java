package com.imageloader.interfaces;

import java.io.File;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-14
 */
public interface ILoader{
    ILoader size(int width,int height);
    ILoader listener(IMGLoadListener<File> listener);

    void load();
}
