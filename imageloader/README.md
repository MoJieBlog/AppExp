## 图片加载框架
### 1.1 实现功能
* 图片下载(done)
* 图片展示(done)
* 设置缓存路径(done)
* 设置网络请求（等待网络封装完成）
* 缓存策略(暂时不支持)
* 设置最大内存缓存(done)
* 设置最大磁盘缓存(done)
* 清除缓存(done)

### 1.2 书写方式
#### 1.2.1 配置选项，初始化
可以自由搭配选项，不搭配使用默认选项
```java
        //Application的onCreate中
        ImageLoaderConfig config = new ImageLoaderConfig.ImageLoaderConfigBuilder()
                .setCacheRule(null)//Glide使用二级缓存，基本上满足需求，暂时不打算完善
                .setDiskCachePath(path)
                .setMaxDiskCacheSize(500*1024)
                .setMaxMemoryCacheSize(50*1024)
                .setRequestClient(null)//等待封装okhttp之后完善，基本上确定传httpClient对象
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
                  Log.d(TAG, "success: "+file.getPath());
              }

              @Override
             public void fail(Exception e) {
                  Log.d(TAG, "fail: ");
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


