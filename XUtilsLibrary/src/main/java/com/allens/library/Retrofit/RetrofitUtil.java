package com.allens.library.Retrofit;


import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

/**
 * Created by allens on 2017/6/5.
 */

public class RetrofitUtil {

    private int connectTimeout = 5;
    private int readTimeout = 5;
    private int writeTimeout = 5;
    private Retrofit mRetrofit;
    private static RetrofitUtil mInstance;

    public static RetrofitUtil getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                mInstance = new RetrofitUtil();
            }
        }
        return mInstance;
    }

    public RetrofitUtil build(String BaseUrl) {
        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .readTimeout(readTimeout, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)//设置写的超时时间
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)//设置连接超时时间
                .build();
        mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BaseUrl)
//                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return this;
    }

    public void seReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setWriteTimeout(int writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    public void setConnectTimeout(int timeDefault) {
        this.connectTimeout = timeDefault;
    }

    public <T> T getService(Class<T> tClass) {
        return mRetrofit.create(tClass);
    }

    public <T> void retrofitGet(final Class<T> tClass, String baseUrl, String url, final OnRetrofit.OnGetListener<T> listener) {

        RetrofitUtil.getInstance()
                .build(baseUrl)
                .getService(ApiService.class)
                .getGetData(url)
                .subscribeOn(Schedulers.io())//在子线程取数据
                .observeOn(AndroidSchedulers.mainThread())//在主线程显示ui
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            Gson gson = new Gson();
                            Log.e("JSON",responseBody.toString());
                            T t = gson.fromJson(responseBody.string(), tClass);
                            listener.onSuccess(t);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public <T> void retrofitPost(final Class<T> tClass, String baseUrl, final OnRetrofit.OnPostListener<T> listener) {
        HashMap<String, Object> map = new HashMap<>();
        listener.onMap(map);
        RetrofitUtil.getInstance()
                .build(baseUrl)
                .getService(ApiService.class)
                .getPostData(map)
                .subscribeOn(Schedulers.io())//在子线程取数据
                .observeOn(AndroidSchedulers.mainThread())//在主线程显示ui
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            Gson gson = new Gson();
                            Log.e("JSON",responseBody.toString());
                            T t = gson.fromJson(responseBody.string(), tClass);
                            listener.onSuccess(t);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void retrofitDownLoad(Context context, final String downLoadUrl, final String filePath, final OnRetrofit.OnDownLoadListener listener) {
        DownLoadUtils.getInstance(context).downLoad(downLoadUrl, filePath, listener);
    }

    public void stop(Context context, String downUrl) {
        DownLoadUtils.getInstance(context).stop(downUrl);
    }
}