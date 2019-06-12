package com.lzp.appexp.applicaton;

import android.app.Application;

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
    }
}
