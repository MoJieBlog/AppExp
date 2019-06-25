package com.lzp.appexp.applicaton;

import android.app.Application;

import com.imageloader.ImageLoader;
import com.imageloader.ImageLoaderConfig;
import com.utils.BuildConfig;
import com.utils.Utils;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-12
 */
public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this, BuildConfig.DEBUG);

        ImageLoaderConfig config = new ImageLoaderConfig.ImageLoaderConfigBuilder()
                .setCacheRule(null)
                .setDiskCachePath("")
                .setMaxDiskCacheSize(500*1024)
                .setMaxMemoryCacheSize(50*1024)
                .setRequestClient(null)
                .build();
        ImageLoader.get(this).init(config);
    }
}
