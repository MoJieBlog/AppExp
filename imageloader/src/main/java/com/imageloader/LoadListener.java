package com.imageloader;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-14
 */
public interface LoadListener<T> {
    void success(T t);
    void fail(Exception e);
}
