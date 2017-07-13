package com.allens.library.Glide;


/**
 * Created by allens on 2017/6/2.
 */


import android.content.Context;
import android.widget.ImageView;

import com.allens.library.XConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;

public class GlideUtil {

    /**
     * Glide特点
     * 使用简单
     * 可配置度高，自适应程度高
     * 支持常见图片格式 Jpg png gif webp
     * 支持多种数据源  网络、本地、资源、Assets 等
     * 高效缓存策略    支持Memory和Disk图片缓存 默认Bitmap格式采用RGB_565内存使用至少减少一半
     * 生命周期集成   根据Activity/Fragment生命周期自动管理请求
     * 高效处理Bitmap  使用Bitmap Pool使Bitmap复用，主动调用recycle回收需要回收的Bitmap，减小系统回收压力
     * 这里默认支持Context，Glide支持Context,Activity,Fragment，FragmentActivity
     */
//    private static GlideUtil glideUtil;
//
//    public static GlideUtil create() {
//        if (glideUtil == null) {
//            synchronized (GlideUtil.class) {
//                if (glideUtil == null) {
//                    glideUtil = new GlideUtil();
//                }
//            }
//        }
//        return glideUtil;
//    }

    //默认加载
    public <T> void loadImageView(Context mContext, T path, ImageView mImageView) {
        Glide.with(mContext)
                .load(path)
                .into(mImageView);
    }

    //加载指定大小
    public <T> void loadImageViewSize(Context mContext, T path, int width, int height, ImageView mImageView) {
        Glide.with(mContext)
                .load(path)
                .override(width, height)
                .into(mImageView);
    }

    //设置加载中以及加载失败图片
    public <T> void loadImageViewLoading(Context mContext, T path, ImageView mImageView, int loadingImage, int errorImageView) {
        Glide.with(mContext).load(path)
                .placeholder(loadingImage)
                .error(errorImageView)
                .into(mImageView);
    }

    //圆形图片
    public <T> void loadImageViewCircle(Context mContext, T path, ImageView mImageView, int loadingImage, int errorImageView, int borderWidth, int borderColor) {
        Glide.with(mContext)
                .load(path)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new CircleTransform(mContext, borderWidth, borderColor))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(loadingImage)
                .error(errorImageView)
                .crossFade()
                .into(mImageView);
    }

    //圆角图片
    public <T> void loadImageViewRound(Context mContext, T path, ImageView mImageView, int loadingImage, int errorImageView, int dp) {
        Glide.with(mContext)
                .load(path)
                .placeholder(loadingImage)
                .error(errorImageView)
                .transform(new GlideRoundTransform(mContext, dp))
                .into(mImageView);
    }

    //设置加载中以及加载失败图片并且指定大小
    public <T> void loadImageViewLoadingSize(Context mContext, T path, int width, int height, ImageView mImageView, int loadingImage, int errorImageView) {
        Glide.with(mContext)
                .load(path)
                .override(width, height)
                .placeholder(loadingImage)
                .error(errorImageView)
                .into(mImageView);
    }

    //设置跳过内存缓存
    public <T> void loadImageViewCache(Context mContext, T path, ImageView mImageView) {
        Glide.with(mContext).load(path).skipMemoryCache(true).into(mImageView);
    }


    //设置下载优先级
    public <T> void loadImageViewPriority(Context mContext, T path, ImageView mImageView, int priority) {
        if (priority == XConfig.PRIOROTY_IMMEDIATE) {
            Glide.with(mContext).load(path).priority(Priority.IMMEDIATE).into(mImageView);
        } else if (priority == XConfig.PRIOROTY_HIGH) {
            Glide.with(mContext).load(path).priority(Priority.HIGH).into(mImageView);
        } else if (priority == XConfig.PRIOROTY_NORMAL) {
            Glide.with(mContext).load(path).priority(Priority.NORMAL).into(mImageView);
        } else if (priority == XConfig.PRIOROTY_LOW) {
            Glide.with(mContext).load(path).priority(Priority.LOW).into(mImageView);
        } else if (priority == XConfig.PRIOROTY_PRIOROTY) {
            Glide.with(mContext).load(path).priority(Priority.priority).into(mImageView);
        } else {
            Glide.with(mContext).load(path).priority(Priority.NORMAL).into(mImageView);
        }
    }

    /**
     * 策略解说：
     * <p>
     * all:缓存源资源和转换后的资源
     * <p>
     * none:不作任何磁盘缓存
     * <p>
     * source:缓存源资源
     * <p>
     * result：缓存转换后的资源
     */
    //设置缓存策略
    public <T> void loadImageViewDiskCache(Context mContext, T path, ImageView mImageView, int diskCacheStrategy) {
        if (diskCacheStrategy == XConfig.DiskCacheStrategy_ALL) {
            Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.ALL).into(mImageView);
        } else if (diskCacheStrategy == XConfig.DiskCacheStrategy_NONE) {
            Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.NONE).into(mImageView);
        } else if (diskCacheStrategy == XConfig.DiskCacheStrategy_SOURCE) {
            Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(mImageView);
        } else if (diskCacheStrategy == XConfig.DiskCacheStrategy_RESULT) {
            Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.RESULT).into(mImageView);
        } else {
            Glide.with(mContext).load(path).diskCacheStrategy(DiskCacheStrategy.RESULT).into(mImageView);
        }
    }

    /**
     * api也提供了几个常用的动画：比如crossFade()
     */
    //设置加载动画
    public <T> void loadImageViewAnim(Context mContext, T path, int anim, ImageView mImageView) {
        Glide.with(mContext).load(path).animate(anim).into(mImageView);
    }

    /**
     * 会先加载缩略图
     */
    //设置缩略图支持
    public <T> void loadImageViewThumbnail(Context mContext, T path, ImageView mImageView) {
        Glide.with(mContext).load(path).thumbnail(0.1f).into(mImageView);
    }

    /**
     * api提供了比如：centerCrop()、fitCenter()等
     */
    //设置动态转换
    public <T> void loadImageViewCrop(Context mContext, T path, ImageView mImageView) {
        Glide.with(mContext).load(path).centerCrop().into(mImageView);
    }

    //设置动态GIF加载方式
    public <T> void loadImageViewDynamicGif(Context mContext, T path, ImageView mImageView, int loadingImage, int errorImageView) {
        Glide.with(mContext)
                .load(path)
                .asGif()
                .placeholder(loadingImage)
                .error(errorImageView)
                .into(mImageView);
    }

    //设置静态GIF加载方式
    public <T> void loadImageViewStaticGif(Context mContext, T path, ImageView mImageView) {
        Glide.with(mContext).load(path).asBitmap().into(mImageView);
    }

    //设置监听的用处 可以用于监控请求发生错误来源，以及图片来源 是内存还是磁盘

    //设置监听请求接口
    public <T> void loadImageViewListener(Context mContext, T path, ImageView mImageView, RequestListener<T, GlideDrawable> requstlistener) {
        Glide.with(mContext).load(path).listener(requstlistener).into(mImageView);
    }

    //项目中有很多需要先下载图片然后再做一些合成的功能，比如项目中出现的图文混排

    //设置要加载的内容
    public <T> void loadImageViewContent(Context mContext, T path, SimpleTarget<GlideDrawable> simpleTarget) {
        Glide.with(mContext).load(path).centerCrop().into(simpleTarget);
    }

    //清理磁盘缓存
    public void GuideClearDiskCache(final Context mContext) {
        //理磁盘缓存 需要在子线程中执行
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(mContext).clearDiskCache();
            }
        });
        thread.start();
    }

    //清理内存缓存
    public void GuideClearMemory(Context mContext) {
        //清理内存缓存  可以在UI主线程中进行
        Glide.get(mContext).clearMemory();
    }
}