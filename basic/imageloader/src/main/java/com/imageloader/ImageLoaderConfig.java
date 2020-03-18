package com.imageloader;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-06-25
 */
public final class ImageLoaderConfig {

    private long maxMemoryCacheSize;
    private long maxDiskCacheSize;
    private String diskCachePath;
    private Object client;
    private Object rule;

    private ImageLoaderConfig(long maxMemoryCacheSize, long maxDiskCacheSize, String diskCachePath, Object client, Object rule) {
        this.maxMemoryCacheSize = maxMemoryCacheSize;
        this.maxDiskCacheSize = maxDiskCacheSize;
        this.diskCachePath = diskCachePath;
        this.client = client;
        this.rule = rule;
    }

    public long getMaxMemoryCacheSize() {
        return maxMemoryCacheSize;
    }

    public long getMaxDiskCacheSize() {
        return maxDiskCacheSize;
    }

    public String getDiskCachePath() {
        return diskCachePath;
    }

    public Object getClient() {
        return client;
    }

    public Object getRule() {
        return rule;
    }

    public static class ImageLoaderConfigBuilder implements ConfigImp {

        private long maxMemoryCacheSize;
        private long maxDiskCacheSize;
        private String diskCachePath;
        private Object client;
        private Object rule;

        public ImageLoaderConfig build() {
            return new ImageLoaderConfig(maxMemoryCacheSize, maxDiskCacheSize, diskCachePath, client, rule);
        }


        @Override
        public ImageLoaderConfigBuilder setRequestClient(Object client) {
            this.client = client;
            return this;
        }

        @Override
        public ImageLoaderConfigBuilder setCacheRule(Object rule) {
            this.rule = rule;
            return this;
        }

        @Override
        public ImageLoaderConfigBuilder setMaxMemoryCacheSize(long size) {
            maxMemoryCacheSize = size;
            return this;
        }

        @Override
        public ImageLoaderConfigBuilder setMaxDiskCacheSize(long size) {
            maxDiskCacheSize = size;
            return this;
        }

        @Override
        public ImageLoaderConfigBuilder setDiskCachePath(String path) {
            diskCachePath = path;
            return this;
        }
    }

    public interface ConfigImp {
        //功能，设置网络请求
        ImageLoaderConfigBuilder setRequestClient(Object client);

        //功能，设置缓存策略
        ImageLoaderConfigBuilder setCacheRule(Object rule);

        //功能，设置内存缓存最大值
        ImageLoaderConfigBuilder setMaxMemoryCacheSize(long size);

        //功能，设置硬盘缓存最大值
        ImageLoaderConfigBuilder setMaxDiskCacheSize(long size);

        //功能，设置缓存路径
        ImageLoaderConfigBuilder setDiskCachePath(String path);
    }
}
