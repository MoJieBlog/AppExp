package com.imageloader.interfaces;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-14
 */
public interface IMGLoadListener<T> {

    void success(T t);
    void fail(Exception e);
}
