# XUtilsDemo
>0.1 版本

## 问题1 ： 这个框架干啥的？？？？？？
- Glide
- 异步查询数据库
- RxJava2  +  Retrofit2 实现GET POST 断点下载
- RxBinding
- 获取屏幕的基本信息
- 选项卡
- 完美退出 app
- 6.0的基本权限 动态获取
- 获取定位信息
- 错误日志
- 设置屏幕 竖屏



## 如何使用？？

```
 allprojects {
  		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}

```
		
	
	
```

	dependencies {
	        compile 'com.github.JiangHaiYang01:XUtils:0.8'
	}
```


## API 介绍

### RxJava + Retrofit 

- GET请求

```
   XUtils.create().retrofitGet(WeatherBean.class, baseUrl, getUrl, new OnRetrofit.OnGetListener<WeatherBean>() {
                    @Override
                    public void onSuccess(WeatherBean weatherBean) {
                        Toast.makeText(RetrofitAct.this, "bean----->" + weatherBean.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RetrofitAct.this, "error----->" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
```


- POST

```
  XUtils.create().retrofitPost(WeatherBean.class, baseUrl, new OnRetrofit.OnPostListener<WeatherBean>() {
                    @Override
                    public void onMap(HashMap<String, Object> map) {
                        map.put("appkey", "052d4c914bc2e308");
                        map.put("city", "安顺");
                    }

                    @Override
                    public void onSuccess(WeatherBean weatherBean) {
                        Toast.makeText(RetrofitAct.this, "bean----->" + weatherBean.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RetrofitAct.this, "error----->" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
```

- 下载

```
 XUtils.create().retrofitDown(RetrofitAct.this, downUrl, FilePath, new OnRetrofit.OnDownLoadListener() {
                    @Override
                    public void onSuccess(int count, boolean isStart) {
                        activityProgressDown.setProgress(count);
                        activityRetrofitTextviewDown.setText(count + " %");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
```

- 取消下载

```
    XUtils.create().retrofitStop(downUrl);
```

> 注意： 当下载的为APK 的时候  下载完成以后  直接弹出安装界面


### Glide封装

```
 //默认加载
    public <T> void loadImageView(Context mContext, T path, ImageView mImageView) {}
    
```

```
//加载指定大小
    public <T> void loadImageViewSize(Context mContext, T path, int width, int height, ImageView mImageView) {}
   
    
```

```
 //设置加载中以及加载失败图片
    public <T> void loadImageViewLoading(Context mContext, T path, ImageView mImageView, int loadingImage, int errorImageView) {}
    
```

```
//圆形图片
    public <T> void loadImageViewCircle(Context mContext, T path, ImageView mImageView, int loadingImage, int errorImageView, int borderWidth, int borderColor) {}
    
```

```
//圆角图片
    public <T> void loadImageViewRound(Context mContext, T path, ImageView mImageView, int loadingImage, int errorImageView, int dp) {}
    
```

```
//设置加载中以及加载失败图片并且指定大小
    public <T> void loadImageViewLoadingSize(Context mContext, T path, int width, int height, ImageView mImageView, int loadingImage, int errorImageView) {
  
    
```

```
//设置跳过内存缓存
    public <T> void loadImageViewCache(Context mContext, T path, ImageView mImageView) {
  
```

```
//设置下载优先级
    public <T> void loadImageViewPriority(Context mContext, T path, ImageView mImageView, int priority) {
 
 
 优先级  从上到下  默认  XConfig.PRIOROTY_NORMAL
 
 XConfig.PRIOROTY_IMMEDIATE
 XConfig.PRIOROTY_HIGH
 XConfig.PRIOROTY_NORMAL
 XConfig.PRIOROTY_LOW
 XConfig.PRIOROTY_PRIOROTY
```

```
//设置缓存策略
    public <T> void loadImageViewDiskCache(Context mContext, T path, ImageView mImageView, int diskCacheStrategy) {
 
 
 XConfig.DiskCacheStrategy_ALL       //all:缓存源资源和转换后的资源
 XConfig.DiskCacheStrategy_NONE      // none:不作任何磁盘缓存
 XConfig.DiskCacheStrategy_SOURCE    //source:缓存源资源
 XConfig.DiskCacheStrategy_RESULT    // result：缓存转换后的资源
 
    
```

```
//设置加载动画
    public <T> void loadImageViewAnim(Context mContext, T path, int anim, ImageView mImageView) {
 
```

```
//设置缩略图支持
    public <T> void loadImageViewThumbnail(Context mContext, T path, ImageView mImageView) {
 
```

```
 //设置动态转换
    public <T> void loadImageViewCrop(Context mContext, T path, ImageView mImageView) {
  
```

```
//设置动态GIF加载方式
    public <T> void loadImageViewDynamicGif(Context mContext, T path, ImageView mImageView, int loadingImage, int errorImageView) {

```

```
//设置静态GIF加载方式
    public <T> void loadImageViewStaticGif(Context mContext, T path, ImageView mImageView) {
    
```

```
 //设置监听请求接口
       public <T> void loadImageViewListener(Context mContext, T path, ImageView mImageView, RequestListener<T, GlideDrawable> requstlistener) {
     
```

```
 //设置要加载的内容
    public <T> void loadImageViewContent(Context mContext, T path, SimpleTarget<GlideDrawable> simpleTarget) {
     
```

```
 //清理磁盘缓存
    public void GuideClearDiskCache(final Context mContext) {
     
```

```
//清理内存缓存
    public void GuideClearMemory(Context mContext) {
      
```


###  Sql

- query

```
  //设置线程池大小
    public XUtils setSqlThreadSize(int size) {
   
```

```
  //异步查询数据库
    public XUtils sqlSearch(SQLiteDatabase db, final OnSql.OnSqlSearchSqlListener listener) {
  
   
```


```
 //更新数据库
    public XUtils sqlUpData(SQLiteDatabase db, final OnSql.OnSqlUpDataListener listener) {
   
   
```


```
 //插入数据
    public XUtils sqlInsert(SQLiteDatabase db, final OnSql.OnSqlInsertListener listener) {
  
   
```


```
  //删除数据
    public XUtils sqlDelete(final SQLiteDatabase db, String table, String whereClause, String[] whereArgs) {
 
   
```

```
 //删除数据库
    public XUtils sqlDeleteSQL(Context context, String dataBaseName) {
  
   
```


### RxBinding

```
  //单点
    public XUtils click(View view, final OnRxBind.OnRxBindingListener listener) {
  
```

```
//checkbox
    public XUtils click(CheckBox view, final OnRxBind.OnRxBindingBooleanListener listener) {

```


```
 //长按
    public XUtils clickLong(View view, final OnRxBind.OnRxBindingListener listener) {
   
```


```
 //listview
    public XUtils click(ListView listView, final OnRxBind.OnRxBindListViewListener listener) {
 
```


```
  //listview 长按
    public void clickLong(ListView listView, final OnRxBind.OnRxBindListViewListener listener) {
 
```

### 选项卡

```
   //点击切换碎片
    public XUtils tabControl(RadioGroup radioGroup, List<Fragment> fragmentList, FragmentManager supportFragmentManager, int layoutId) {
 
```

```
  //viewpager 切换view
    public XUtils tabControl(RadioGroup radioGroup, ViewPager viewPager, List<View> viewList) {
 
```

```
 //viewpager 切换碎片
    public XUtils tabControl(RadioGroup radioGroup, ViewPager viewPager, FragmentManager manager, List<Fragment> fragmentList) {


```

```
  //viewpager 切换碎片
    public XUtils tabControl(TabLayout mTabLayout, ViewPager viewPager, FragmentManager manager, String mTitleList[], List<Fragment> mFragmentList) {
  
```

### 退出app

```
 //添加Activity
    public XUtils addActivity(Activity activity) {
   

```



```
  //删除Activity
    public XUtils romveActivity(Activity activity) {
  
```



```
 //遍历所有Activity并finish
    public XUtils exitAllActivity() {
   

```

### 异常日志

```
    // 设置toast 弹出的时间  多久以后退出引用
    public XUtils setCrashHandlerTime(long time) {
```

```
    // log 的 TAG
    public XUtils setCrashHandlerTag(String tag) {

```

```
    // 文件目录
    public XUtils setCrashHandlerPath(String path) {
```

```
    // 初始化
    public XUtils startCrashHandler(Context context) {

```


### 完成适配器

```

  public <T> XUtils adapter(Context context, int layoutId, List<T> dataList, ListView listView, final OnAdapterListener.OnAbListListener<T> listener) {
   

```

```
   public <T> XUtils adapter(Context context, int layoutId, List<T> dataList, RecyclerView recyclerView, final OnAdapterListener.OnRvListener<T> listener) {
   
```

#### 多类型的适配器

```
public class ChatAdapter extends MultiItemTypeAdapter<ChatMessage> {
    public ChatAdapter(Context context, List<ChatMessage> datas) {
        super(context, datas);
        addItemViewDelegate(new MsgSendItemDelagate());
        addItemViewDelegate(new MsgComingItemDelagate());
    }
}



public class MsgSendItemDelagate implements ItemViewDelegate<ChatMessage> {

    @Override
    public int getItemViewLayoutId() {
        return R.layout.main_chat_send_msg;
    }

    @Override
    public boolean isForViewType(ChatMessage item, int position) {
        return !item.isComMeg();
    }

    @Override
    public void convert(ViewHolder holder, ChatMessage chatMessage, int position) {
        holder.setText(R.id.chat_send_content, chatMessage.getContent());
        holder.setText(R.id.chat_send_name, chatMessage.getName());
        holder.setImageResource(R.id.chat_send_icon, chatMessage.getIcon());
    }
}


```



### SharePreference 工具类

```
PrefUtils
```


### 获取信息的帮助类

```
  // 使用手机再带的功能  返回定位信息
    public XUtils getLocation(Context context, final LocationUtil.OnResultLocationListener listener) {
  

```

```
 //当前时间
    public String getCurrentTime() {
   
   
   
// 设置 格式  有默认的格式
     public XUtils setCurrentTimeFormat(String format) {

```

```
 // 屏幕宽高
     public XUtils getScreenWidthAndHeigth(Context context, Screen.OnScreenWidthAndHeightListener listener) {
  
```

```
// 屏幕状态栏高度
    public int getStatusHeight(Context context) {

```

```
//获取当前屏幕截图，不包含状态栏
  public XUtils getSnapBitmapOut(Activity activity, Screen.OnSnapOutListener listener) {
 

```