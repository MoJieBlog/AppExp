## 图片加载框架
## 1 期待效果
### 1.1 期待功能
* 图片下载
* 图片展示
* 设置缓存路径
* 清除缓存路径
* 设置网络请求（httpClient）
* 缓存策略
* 设置最大内存缓存
* 设置最大磁盘缓存
* 清除缓存

### 1.2期待书写方式
#### 1.2.1 配置选项，初始化
可以自由搭配选项，不搭配使用默认选项
```java
        //Application的onCreate中
        ImageLoaderConfig config = new ImageLoaderConfig.ImageLoaderConfigBuilder()
                .setCacheRule(null)
                .setDiskCachePath("")
                .setMaxDiskCacheSize(500*1024)
                .setMaxMemoryCacheSize(50*1024)
                .setRequestClient(null)
                .build();
        ImageLoader.get(this).init(config);

```
#### 1.2.2 图片下载
可以自由搭配，不搭配使用默认
```java
ImageLoader.get(this)
           .load(url)
           .listener(new IMGLoadListener<File>() {
              @Override
              public void success(File file) {
                  Log.e(TAG, "success: "+file.getPath());
              }

              @Override
             public void fail(Exception e) {
                  Log.e(TAG, "fail: ");
              }
       }).load();
```
#### 1.2.3 图片展示
自由搭配
```java
ImageLoader.get(context)
           .display(url)
           .size(100,100)
           .skipMemory(false)//是否需要缓存
           .placeHolder(R.mipmap.ic_launcher_round)
           .errHolder(R.mipmap.ic_launcher)
           .listener(new IMGLoadListener<Drawable>() {
                  @Override
                  public void success(Drawable drawable) {

                  }

                  @Override
                  public void fail(Exception e) {

                  }
           })
           .into(holder.testIv);
```

## 2 实现过程

### 2.1 初始化，配置ImageLoader。
要创建ImageLoaderConfig。肯定是要用单例模式，工厂模式和建造者模式。因为工厂模式注重的是整体，单例模式注重的是单一。而建造者模式注重的是部件构造的过程。所以选择建造者模式。

#### 2.1.1 初始化的功能
* 设置网络请求
* 设置缓存策略
* 设置内存缓存最大值
* 设置硬盘缓存最大值
* 设置缓存路径

所以有以下代码
```java
        public interface ConfigImp {
           //功能，设置网络请求，先用Object。记得Glide是需要一个HttpClient对象。因为还没封装Okhttp,所以先这样写
           ImageLoaderConfigBuilder setRequestClient(Object client);

           //功能，设置缓存策略，先用Object，因为Glide本身就是二级缓存，所以这里先不改。
           ImageLoaderConfigBuilder setCacheRule(Object rule);

           //功能，设置内存缓存最大值
           ImageLoaderConfigBuilder setMaxMemoryCacheSize(long size);

           //功能，设置硬盘缓存最大值
           ImageLoaderConfigBuilder setMaxDiskCacheSize(long size);

           //功能，设置缓存路径
           ImageLoaderConfigBuilder setDiskCachePath(String path);
       }
```
然后就是具体的建造者实现了

#### 2.2 图片加载实现