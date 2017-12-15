package com.allens.library;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.allens.library.Screen.Screen;
import com.allens.library.Screen.ScreenUtils;
import com.allens.library.SetAndGet.GetUtil;
import com.allens.library.Utils.ActivityContainer;
import com.allens.library.Utils.CrashHandler;
import com.allens.library.Utils.LocationUtil;
import com.allens.library.TabControl.RadioGroupToViewPagerUtils;
import com.allens.library.TabControl.RgToFmUtils;
import com.allens.library.Glide.GlideUtil;
import com.allens.library.Retrofit.OnRetrofit;
import com.allens.library.Retrofit.RetrofitUtil;
import com.allens.library.RxBinding.OnRxBind;
import com.allens.library.RxBinding.RxBindUtil;
import com.allens.library.SQL.OnSql;
import com.allens.library.SQL.SqlUtil;
import com.allens.library.Utils.Permission;
import com.allens.library.adapter.AdapterUtil;
import com.allens.library.adapter.MyPagerAdapter;
import com.allens.library.adapter.OnAdapterListener;
import com.allens.library.adapter.ViewPagerAdapter;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zhy.adapter.recyclerview.CommonAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by allens on 2017/6/10.
 */

public class XUtils {

    private static XUtils xUtils;

    public static XUtils create() {
        if (xUtils == null) {
            synchronized (XUtils.class) {
                if (xUtils == null) {
                    xUtils = new XUtils();
                }
            }
        }
        return xUtils;
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/10 下午1:52
     * @方法作用： ActivityContainer  完美退出App
     */
    //添加Activity
    public XUtils addActivity(Activity activity) {
        ActivityContainer.newInstance().addActivity(activity);
        return this;
    }

    //删除Activity
    public XUtils removeActivity(Activity activity) {
        ActivityContainer.newInstance().romveActivity(activity);
        return this;
    }

    //遍历所有Activity并finish
    public XUtils exitAllActivity() {
        ActivityContainer.newInstance().exit();
        return this;
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/10 下午1:51
     * @方法作用： 加载图片的方法
     */
    //默认加载
    public <T> XUtils loadImageView(Context mContext, T path, ImageView mImageView) {
        new GlideUtil().loadImageView(mContext, path, mImageView);
        return this;
    }

    //加载指定大小
    public <T> XUtils loadImageViewSize(Context mContext, T path, int width, int height, ImageView mImageView) {
        new GlideUtil().loadImageViewSize(mContext, path, width, height, mImageView);
        return this;
    }

    //设置加载中以及加载失败图片
    public <T> XUtils loadImageViewLoading(Context mContext, T path, ImageView mImageView, int loadingImage, int errorImageView) {
        new GlideUtil().loadImageViewLoading(mContext, path, mImageView, loadingImage, errorImageView);
        return this;
    }

    //圆形图片
    public <T> XUtils loadImageViewCircle(Context mContext, T path, ImageView mImageView, int loadingImage, int errorImageView, int borderWidth, int borderColor) {
        new GlideUtil().loadImageViewCircle(mContext, path, mImageView, loadingImage, errorImageView, borderWidth, borderColor);
        return this;
    }

    //圆角图片
    public <T> XUtils loadImageViewRound(Context mContext, T path, ImageView mImageView, int loadingImage, int errorImageView, int radian) {
        new GlideUtil().loadImageViewRound(mContext, path, mImageView, loadingImage, errorImageView, radian);
        return this;
    }

    //设置加载中以及加载失败图片并且指定大小
    public <T> XUtils loadImageViewLoadingSize(Context mContext, T path, int width, int height, ImageView mImageView, int loadingImage, int errorImageView) {
        new GlideUtil().loadImageViewLoadingSize(mContext, path, width, height, mImageView, loadingImage, errorImageView);
        return this;
    }

    //设置跳过内存缓存
    public <T> XUtils loadImageViewCache(Context mContext, T path, ImageView mImageView) {
        new GlideUtil().loadImageViewCache(mContext, path, mImageView);
        return this;
    }

    //设置下载优先级
    //0 ---4   0最快
    public <T> XUtils loadImageViewPriority(Context mContext, T path, ImageView mImageView, int priority) {
        new GlideUtil().loadImageViewPriority(mContext, path, mImageView, priority);
        return this;
    }

    //设置缓存策略 all:缓存源资源和转换后的资源  none:不作任何磁盘缓存 source:缓存源资源 result：缓存转换后的资源
    public <T> XUtils loadImageViewDiskCache(Context mContext, T path, ImageView mImageView, int diskCacheStrategy) {
        new GlideUtil().loadImageViewDiskCache(mContext, path, mImageView, diskCacheStrategy);
        return this;
    }

    //设置加载动画
    public <T> XUtils loadImageViewAnim(Context mContext, T path, int anim, ImageView mImageView) {
        new GlideUtil().loadImageViewAnim(mContext, path, anim, mImageView);
        return this;
    }

    //设置缩略图支持 会先加载缩略图
    public <T> XUtils loadImageViewThumbnail(Context mContext, T path, ImageView mImageView) {
        new GlideUtil().loadImageViewThumbnail(mContext, path, mImageView);
        return this;
    }

    //设置动态GIF加载方式
    public <T> XUtils loadImageViewDynamicGif(Context mContext, T path, ImageView mImageView, int loadingImage, int errorImageView) {
        new GlideUtil().loadImageViewDynamicGif(mContext, path, mImageView, loadingImage, errorImageView);
        return this;
    }

    //设置静态GIF加载方式
    public <T> XUtils loadImageViewStaticGif(Context mContext, T path, ImageView mImageView) {
        new GlideUtil().loadImageViewStaticGif(mContext, path, mImageView);
        return this;
    }

    //设置动态转换 api提供了比如：centerCrop()、fitCenter()等
    public <T> XUtils loadImageViewCrop(Context mContext, T path, ImageView mImageView) {
        new GlideUtil().loadImageViewCrop(mContext, path, mImageView);
        return this;
    }

    //设置监听请求接口  设置监听的用处 可以用于监控请求发生错误来源，以及图片来源 是内存还是磁盘
    public <T> XUtils loadImageViewListener(Context mContext, T path, ImageView mImageView, RequestListener<T, GlideDrawable> requstlistener) {
        new GlideUtil().loadImageViewListener(mContext, path, mImageView, requstlistener);
        return this;
    }

    //设置要加载的内容  项目中有很多需要先下载图片然后再做一些合成的功能，比如项目中出现的图文混排
    public <T> XUtils loadImageViewContent(Context mContext, T path, SimpleTarget<GlideDrawable> simpleTarget) {
        new GlideUtil().loadImageViewContent(mContext, path, simpleTarget);
        return this;
    }

    //清理磁盘缓存
    public XUtils glideClearDiskCache(Context mContext) {
        new GlideUtil().GuideClearDiskCache(mContext);
        return this;
    }

    //清理内存缓存  可以在UI主线程中进行
    public XUtils glideClearMemory(Context mContext) {
        new GlideUtil().GuideClearMemory(mContext);
        return this;
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/10 下午2:45
     * @方法作用： 数据库 异步查询
     */
    //设置线程池大小
    public XUtils setSqlThreadSize(int size) {
        SqlUtil.create().setThreadSize(size);
        return this;
    }

    //异步查询数据库
    public XUtils sqlSearch(SQLiteDatabase db, final OnSql.OnSqlSearchSqlListener listener) {
        SqlUtil.create().searchdb(db, new OnSql.OnSqlSearchSqlListener() {
            @Override
            public void onThreadIO(SQLiteDatabase db, ContentValues cv) {
                listener.onThreadIO(db, cv);
            }

            @Override
            public void onThreadMain() {
                listener.onThreadMain();
            }
        });
        return this;
    }

    //更新数据库
    public XUtils sqlUpData(SQLiteDatabase db, final OnSql.OnSqlUpDataListener listener) {
        SqlUtil.create().sqlUpData(db, new OnSql.OnSqlUpDataListener() {
            @Override
            public void onUpData(SQLiteDatabase db, ContentValues cv) {
                listener.onUpData(db, cv);
            }
        });
        return this;
    }

    //插入数据
    public XUtils sqlInsert(SQLiteDatabase db, final OnSql.OnSqlInsertListener listener) {
        SqlUtil.create().sqlInsert(db, new OnSql.OnSqlInsertListener() {
            @Override
            public void onInsert(SQLiteDatabase db, ContentValues cv) {
                listener.onInsert(db, cv);
            }
        });
        return this;
    }

    //删除数据
    public XUtils sqlDelete(final SQLiteDatabase db, String table, String whereClause, String[] whereArgs) {
        SqlUtil.create().sqlDelete(db, table, whereClause, whereArgs);
        return this;
    }

    //删除数据库
    public XUtils sqlDeleteSQL(Context context, String dataBaseName) {
        SqlUtil.create().sqlDeleteSQL(context, dataBaseName);
        return this;
    }


    public XUtils sqlCloseDb(SQLiteDatabase db) {
        SqlUtil.create().closeDb(db);
        return this;
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/10 下午3:47
     * @方法作用：reftrofit 结合 Rxjava
     */
    //get
    public <T> XUtils retrofitGet(final Class<T> tClass, String baseUrl, String url, final OnRetrofit.OnGetListener<T> listener) {
        RetrofitUtil.getInstance().retrofitGet(tClass, baseUrl, url, listener);
        return this;
    }

    //post
    public <T> XUtils retrofitPost(final Class<T> tClass, String baseUrl, final OnRetrofit.OnPostListener<T> listener) {
        RetrofitUtil.getInstance().retrofitPost(tClass, baseUrl, listener);
        return this;
    }

    //下载
    public XUtils retrofitDown(Context context, String downLoadUrl, String filePath, OnRetrofit.OnDownLoadListener listener) {
        RetrofitUtil.getInstance().retrofitDownLoad(context, downLoadUrl, filePath, listener);
        return this;
    }

    //暂停
    public void retrofitStop(Context context, String downUrl) {
        RetrofitUtil.getInstance().stop(context, downUrl);
    }

    public XUtils seReadTimeout(int readTimeout) {
        RetrofitUtil.getInstance().seReadTimeout(readTimeout);
        return this;
    }

    public XUtils setWriteTimeout(int writeTimeout) {
        RetrofitUtil.getInstance().setWriteTimeout(writeTimeout);
        return this;
    }

    public XUtils setConnectTimeout(int timeDefault) {
        RetrofitUtil.getInstance().setConnectTimeout(timeDefault);
        return this;
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/10 下午5:09
     * @方法作用： RxBinding
     */
    //单点
    public XUtils click(View view, final OnRxBind.OnRxBindingListener listener) {
        new RxBindUtil().click(view, new OnRxBind.OnRxBindingListener() {
            @Override
            public void OnClickListener() {
                listener.OnClickListener();
            }
        });
        return this;
    }

    //长按
    public XUtils clickLong(View view, final OnRxBind.OnRxBindingListener listener) {
        new RxBindUtil().clicklong(view, new OnRxBind.OnRxBindingListener() {
            @Override
            public void OnClickListener() {
                listener.OnClickListener();
            }
        });
        return this;
    }

    //checkbox
    public XUtils click(CheckBox view, final OnRxBind.OnRxBindingBooleanListener listener) {

        new RxBindUtil().clickCheckBox(view, new OnRxBind.OnRxBindingBooleanListener() {
            @Override
            public void OnClickListener(Boolean mBoolean) {
                listener.OnClickListener(mBoolean);
            }
        });
        return this;
    }

    //listview
    public XUtils click(ListView listView, final OnRxBind.OnRxBindListViewListener listener) {
        new RxBindUtil().clickListView(listView, new OnRxBind.OnRxBindListViewListener() {
            @Override
            public void OnClickListener(Integer integer) {
                listener.OnClickListener(integer);
            }
        });
        return this;
    }

    //listview 长按
    public void clickLong(ListView listView, final OnRxBind.OnRxBindListViewListener listener) {
        new RxBindUtil().clickLongListView(listView, new OnRxBind.OnRxBindListViewListener() {
            @Override
            public void OnClickListener(Integer integer) {
                listener.OnClickListener(integer);
            }
        });
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/11 上午10:32
     * @方法作用： 选项卡
     */
    //点击切换碎片
    public XUtils tabControl(RadioGroup radioGroup, List<Fragment> fragmentList, FragmentManager supportFragmentManager, int layoutId) {
        new RgToFmUtils().showTabToFragment(fragmentList, radioGroup, supportFragmentManager, layoutId);
        return this;
    }

    //viewpager 切换view
    public XUtils tabControl(RadioGroup radioGroup, ViewPager viewPager, List<View> viewList) {
        new RadioGroupToViewPagerUtils().showRadioToViewPager(radioGroup, viewPager, viewList);
        return this;
    }

    //viewpager 切换碎片
    public XUtils tabControl(RadioGroup radioGroup, ViewPager viewPager, FragmentManager manager, List<Fragment> fragmentList) {
        new RadioGroupToViewPagerUtils().showRadioToViewPager(radioGroup, viewPager, manager, fragmentList);
        return this;
    }

    //viewpager 切换碎片
    public XUtils tabControl(TabLayout mTabLayout, ViewPager viewPager, FragmentManager manager, String mTitleList[], List<Fragment> mFragmentList) {
        new RadioGroupToViewPagerUtils().showTabView(mTabLayout, viewPager, manager, mTitleList, mFragmentList);
        return this;
    }


    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/11 上午11:36
     * @方法作用： CrashHandler
     */
    // 设置toast 弹出的时间  多久以后退出引用
    public XUtils setCrashHandlerTime(long time) {
        CrashHandler.getInstance().setDelayTime(time);
        return this;
    }

    // log 的 TAG
    public XUtils setCrashHandlerTag(String tag) {
        CrashHandler.getInstance().setTAG(tag);
        return this;
    }

    // 文件目录
    public XUtils setCrashHandlerPath(String path) {
        CrashHandler.getInstance().setPath(path);
        return this;
    }

    // 初始化
    public XUtils startCrashHandler(Context context) {
        CrashHandler.getInstance().init(context);
        return this;
    }


    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/13 下午9:27
     * @方法作用： 6.0 权限
     */
    public XUtils setSixPermission(Activity activity) {
        Permission permission = new Permission(activity);
        permission.initISSix();
        return this;
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/14 上午9:47
     * @方法作用： 设置竖屏
     */
    public XUtils setActivityInfo(Activity activity) {
        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        return this;
    }


    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/22 上午11:57
     * @方法作用： adapter
     */
    public <T> com.zhy.adapter.abslistview.CommonAdapter adapter(Context context, int layoutId, List<T> dataList, ListView listView, final OnAdapterListener.OnAbListListener<T> listener) {
        return AdapterUtil.create(context).adapter(context, layoutId, dataList, listView, listener);
    }

    public <T> com.zhy.adapter.abslistview.CommonAdapter adapter(Context context, int layoutId, List<T> dataList, GridView gridview, final OnAdapterListener.OnAbListListener<T> listener) {
        return AdapterUtil.create(context).adapter(context, layoutId, dataList, gridview, listener);
    }

    public <T> CommonAdapter adapter(Context context, int layoutId, List<T> dataList, RecyclerView recyclerView, final OnAdapterListener.OnRvListener<T> listener) {
        return AdapterUtil.create(context).adapter(context, layoutId, dataList, recyclerView, listener);
    }

    public ViewPagerAdapter adapter(FragmentManager fragmentManager, ViewPager viewPager, ArrayList<Fragment> fragments) {
        return AdapterUtil.create(null).adapter(fragmentManager, viewPager, fragments);
    }

    public MyPagerAdapter adapter(ViewPager viewPager, ArrayList<View> viewArrayList) {
        return AdapterUtil.create(null).adapter(viewPager, viewArrayList);
    }

    public MyPagerAdapter adapter(Context context, ViewPager viewPager, ArrayList<Integer> imgResIds) {
        return AdapterUtil.create(context).adapter(context, viewPager, imgResIds);
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return AdapterUtil.create(null).getLinearLayoutManager();
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/23 下午6:55
     * @方法作用： onBackPressed
     */
    private long lastTime;

    public void onBackPressed(Activity act, Api.OnBackListener listener) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastTime < 2 * 1000) {
            ActivityContainer.newInstance().exit();
        } else {
            listener.onBack();
            lastTime = currentTime;
        }
    }


    /**
     *
     * @ User :  allens
     * @ 创建日期 :  2017/7/13 上午10:02
     * @模块作用 ： get
     *
     *====================================================================================================================================
     *====================================================================================================================================
     * */

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/2/7 下午2:44
     * @方法作用： 获取版本号
     */
    public int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();//获取包管理器
        try {
            //通过当前的包名获取包的信息
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);//获取包对象信息
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/2/7 下午2:44
     * @方法作用： 获取版本号
     */
    public String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();//获取包管理器
        try {
            //通过当前的包名获取包的信息
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);//获取包对象信息
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/11 上午10:50
     * @方法作用：get 信息
     */
    // 使用手机再带的功能  返回定位信息
    public XUtils getLocation(Context context, final LocationUtil.OnResultLocationListener listener) {
        LocationUtil locationUtil = new LocationUtil(context);
        locationUtil.setLocationManger(new LocationUtil.OnResultLocationListener() {
            @Override
            public void onSuccess(double mLongitude, double mLatitude) {
                listener.onSuccess(mLongitude, mLatitude);
            }

            @Override
            public void onFail() {
                listener.onFail();
            }

            @Override
            public void onNoPermission() {
                listener.onNoPermission();
            }

            @Override
            public void onNoProvider() {//手机没有定位功能
                listener.onNoProvider();
            }
        });
        return this;
    }


    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/11 下午2:51
     * @方法作用： 当前时间
     */
    public String getCurrentTime() {
        return new GetUtil().getCurrentTime();
    }

    public XUtils setCurrentTimeFormat(String format) {
        new GetUtil().setFormat(format);
        return this;
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/11 下午2:52
     * @方法作用： 屏幕宽高
     */
    public XUtils getScreenWidthAndHeigth(Context context, Screen.OnScreenWidthAndHeightListener listener) {
        listener.onWidthAndHeigth(ScreenUtils.getScreenWidth(context), ScreenUtils.getScreenHeight(context));
        return this;
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/11 下午2:56
     * @方法作用： 屏幕状态栏高度
     */
    public int getStatusHeight(Context context) {
        return ScreenUtils.getStatusHeight(context);
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/11 下午3:01
     * @方法作用：获取当前屏幕截图，不包含状态栏
     */
    public XUtils getSnapBitmapOut(Activity activity, Screen.OnSnapOutListener listener) {
        listener.onSnapOut(ScreenUtils.snapShotWithoutStatusBar(activity));
        return this;
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/11 下午3:06
     * @方法作用： 获取当前屏幕截图，包含状态栏
     */
    public XUtils getSnapBitmap(Activity activity, Screen.OnSnapInListener listener) {
        listener.onSnapIn(ScreenUtils.snapShotWithStatusBar(activity));
        return this;
    }

}


