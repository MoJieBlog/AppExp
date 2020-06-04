package com.net

import okhttp3.OkHttpClient

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020/6/4
 */
object NetUtils {

    private var httpClient: OkHttpClient? = null

    fun getHttpClient(): OkHttpClient {
        if (httpClient == null) {
            httpClient = OkHttpClient()
        }
        return httpClient as OkHttpClient
    }

    fun get() {

    }

    fun postForm() {

    }

    fun postJson() {

    }
}