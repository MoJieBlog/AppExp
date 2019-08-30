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
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.imageloader.ImageLoaderConfig;
import com.imageloader.ImageLoaderUtils;
import com.imageloader.interfaces.IDisplay;
import com.imageloader.interfaces.IImageLoader;
import com.imageloader.interfaces.ILoader;
import com.imageloader.interfaces.IMGLoadListener;

import java.io.File;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-13
 */

@SuppressLint("CheckResult")
public class GlideImageLoader implements IImageLoader {

    private Context context;

    public static ImageLoaderConfig config;

    public static GlideImageLoader get(Context context) {
        return new GlideImageLoader(context);
    }

    private GlideImageLoader(Context context) {
        this.context = context;
    }

    @Override
    public ILoader load(String url) {
        return new GlideLoader(context, url);
    }

    @Override
    public IDisplay display(String url) {
        return new GlideDisplay(context, url);
    }

    @Override
    public void clearCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (context != null)
                    Glide.get(context).clearDiskCache();
            }
        }).start();
        Glide.get(context).clearMemory();
    }

    @Override
    public void init(ImageLoaderConfig config) {
        GlideImageLoader.config = config;
    }

    @Override
    public String getDiskCachePath() {
        if (config != null) {
            return config.getDiskCachePath();
        } else {
            return ImageLoaderUtils.getDefaultDiskCachePath(context);
        }
    }


    class GlideDisplay implements IDisplay {

        private Context context;
        private String url;

        private int placeHolder = 0;
        private int errHolder = 0;
        private int width = 0, height = 0;
        private boolean needMemory = true;
        private IMGLoadListener<Drawable> listener;


        public GlideDisplay(Context context, String url) {
            this.url = url;
            this.context = context;
        }


        @Override
        public IDisplay placeHolder(int res) {
            this.placeHolder = res;
            return this;
        }

        @Override
        public IDisplay errHolder(int res) {
            this.errHolder = res;
            return this;
        }

        @Override
        public IDisplay size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        @Override
        public IDisplay needMemory(boolean needMemory) {
            this.needMemory = needMemory;
            return this;
        }

        @Override
        public IDisplay listener(IMGLoadListener<Drawable> listener) {
            this.listener = listener;
            return this;
        }

        @Override
        public void into(View view) {
            RequestOptions opt = new RequestOptions();
            opt.diskCacheStrategy(needMemory ? DiskCacheStrategy.RESOURCE : DiskCacheStrategy.NONE);
            opt.skipMemoryCache(!needMemory);
            if (placeHolder != 0) {
                opt.placeholder(placeHolder);
            }

            if (errHolder != 0) {
                opt.error(errHolder);
            }

            if (width != 0 && height != 0) {
                opt.override(width, height);
            }

            RequestBuilder<Drawable> apply = Glide.with(context)
                    .asDrawable()
                    .load(url)
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

            if (view instanceof ImageView) {
                apply.into((ImageView) view);
            } else {
                apply.into(new CustomViewTarget<View, Drawable>(view) {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        view.setBackground(errorDrawable);
                    }

                    @Override
                    protected void onResourceCleared(@Nullable Drawable placeholder) {
                        view.setBackground(placeholder);
                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable drawable, @Nullable Transition transition) {
                        view.setBackground(drawable);
                    }
                });
            }
        }
    }

    class GlideLoader implements ILoader {

        private Context context;
        private String url;
        private int width = 0, height = 0;
        private IMGLoadListener<File> listener;

        public GlideLoader(Context context, String url) {
            this.url = url;
            this.context = context;
        }

        @Override
        public ILoader size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        @Override
        public ILoader listener(IMGLoadListener<File> listener) {
            this.listener = listener;
            return this;
        }

        @Override
        public void load() {
            RequestOptions opt = new RequestOptions();
            if (width != 0 && height != 0) {
                opt.override(width, height);
            }

            RequestBuilder<File> apply = Glide.with(context)
                    .asFile()
                    .apply(opt)
                    .load(url);

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
            apply.submit();
        }
    }

}
