package com.imageloader.ext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.imageloader.IImageLoader;
import com.imageloader.ILoader;
import com.imageloader.IMGLoadListener;
import com.imageloader.ImageLoaderConfig;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-13
 */

@SuppressLint("CheckResult")
public class GlideLoader implements ILoader, IImageLoader {


    private Params params;

    public static GlideLoader get(Context context) {
        return new GlideLoader(context);
    }

    private GlideLoader(Context context) {
        params = new Params();
        params.context = context;
    }


    @Override
    public IImageLoader into(View view) {
        params.view = view;
        return this;
    }

    @Override
    public IImageLoader placeHolder(int res) {
        params.placeHolder = res;
        return this;
    }

    @Override
    public IImageLoader errHolder(int res) {
        params.errHolder = res;
        return this;
    }

    @Override
    public IImageLoader width(int width) {
        params.width = width;
        return this;
    }

    @Override
    public IImageLoader height(int height) {
        params.height = height;
        return this;
    }

    @Override
    public IImageLoader skipMemory(boolean needMemory) {
        params.skipMemory = needMemory;
        return this;
    }

    @Override
    public void display() {
        display(null);
    }


    @Override
    public void display(final IMGLoadListener<Drawable> listener) {
        RequestOptions opt = new RequestOptions();
        opt.diskCacheStrategy(params.skipMemory ? DiskCacheStrategy.NONE : DiskCacheStrategy.RESOURCE);
        opt.skipMemoryCache(params.skipMemory);
        if (params.placeHolder != 0) {
            opt.placeholder(params.placeHolder);
        }

        if (params.errHolder != 0) {
            opt.error(params.errHolder);
        }

        if (params.width != 0 && params.height != 0) {
            opt.override(params.width, params.height);
        }

        RequestBuilder<Drawable> apply = Glide.with(params.context)
                .asDrawable()
                .load(params.url)
                .apply(opt);

        if (listener != null) {
            apply.listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<Drawable> target, boolean b) {
                    listener.fail(e);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable drawable, Object o, Target<Drawable> target, DataSource dataSource, boolean b) {
                    listener.success(drawable);
                    return false;
                }
            });
        }

        if (params.view instanceof ImageView) {
            apply.into((ImageView) params.view);
        } else {
            apply.into(new ViewTarget<View, Drawable>(params.view) {
                @Override
                public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition transition) {
                    params.view.setBackground(drawable);
                }
            });
        }
    }

    @Override
    public void download() {
        download(null);
    }

    @Override
    public void download(final IMGLoadListener<File> listener) {

        RequestBuilder<File> apply = Glide.with(params.context)
                .asFile()
                .load(params.url);

        if (listener != null) {
            apply.listener(new RequestListener<File>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object o, Target<File> target, boolean b) {
                    listener.fail(e);
                    return false;
                }

                @Override
                public boolean onResourceReady(File file, Object o, Target<File> target, DataSource dataSource, boolean b) {
                    listener.success(file);
                    return false;
                }
            });
        }

        try {
            if (params.width != 0 && params.height != 0) {
                apply.submit(params.width, params.height).get();
            } else {
                apply.submit().get();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IImageLoader load(String url) {
        params.url = url;
        return this;
    }

    @Override
    public void clearCache() {
        Glide.get(params.context).clearDiskCache();
        Glide.get(params.context).clearMemory();
    }

    @Override
    public void init(ImageLoaderConfig config) {
        //TODO 初始化
    }


    @Override
    public String getDiskCachePath() {
        return "";
    }


}
