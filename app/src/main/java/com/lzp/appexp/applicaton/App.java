package com.lzp.appexp.applicaton;

import android.app.Application;

import com.imageloader.ImageLoader;
import com.imageloader.ImageLoaderConfig;
import com.imageloader.ImageLoaderUtils;
import com.squareup.leakcanary.LeakCanary;
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

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            //You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);


        Utils.init(this, BuildConfig.DEBUG);

        ImageLoaderConfig config = new ImageLoaderConfig.ImageLoaderConfigBuilder()
                .setCacheRule(null)
                .setDiskCachePath(ImageLoaderUtils.getDefaultDiskCachePath(this))
                .setMaxDiskCacheSize(100*1024*1024)
                .setMaxMemoryCacheSize(50*1024*1024)
                .setRequestClient(null)
                .build();
        ImageLoader.get(this).init(config);
    }
}
