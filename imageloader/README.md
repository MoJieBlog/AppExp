## 图片加载框架
## 期待效果
### 期待功能
* 图片下载
* 图片展示
* 设置缓存路径
* 清除缓存路径
* 设置网络请求（httpClient）
* 缓存策略
* 设置最大内存缓存
* 设置最大磁盘缓存
* 清除缓存

### 期待书写方式
#### 配置选项，初始化
可以自由搭配选项，不搭配使用默认选项
```java
        ImageLoaderConfig config = new ImageLoaderConfig.ImageLoaderConfigBuilder()
                .setCacheRule(null)
                .setDiskCachePath("")
                .setMaxDiskCacheSize(500*1024)
                .setMaxMemoryCacheSize(50*1024)
                .setRequestClient(null)
                .build();
        ImageLoader.get(this).init(config);

```
#### 图片下载
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
#### 图片展示
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

### 实现过程

