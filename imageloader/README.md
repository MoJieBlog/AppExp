## 图片加载框架
### 期待效果
#### 图片下载
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
