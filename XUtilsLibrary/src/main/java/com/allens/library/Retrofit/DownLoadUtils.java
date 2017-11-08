package com.allens.library.Retrofit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.allens.library.XConfig;
import com.orhanobut.logger.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by allens on 2017/6/19.
 */

@SuppressWarnings("WeakerAccess")
public class DownLoadUtils {

    private Context context;
    private static DownLoadUtils mInstance;
    private HashMap<String, DwonInfo> hashMap = new HashMap<>();
    private Handler handler = new Handler();
    private OnRetrofit.OnDownLoadListener loadListener;
    private String filePath;

    private String downLoadUrl;
    private FileOutputStream fos;

    public DownLoadUtils(Context context) {
        this.context = context;
    }

    public static DownLoadUtils getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DownLoadUtils.class) {
                mInstance = new DownLoadUtils(context);
            }
        }
        return mInstance;
    }


    public void downLoad(String downLoadUrl, final String filePath, OnRetrofit.OnDownLoadListener loadListener) {
        this.downLoadUrl = downLoadUrl;
        this.filePath = filePath;
        this.loadListener = loadListener;
        initSwitch(getState(), loadListener);
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/19 下午2:34
     * @方法作用： 不同状态 不同处理方式
     */
    private void initSwitch(int state, OnRetrofit.OnDownLoadListener loadListener) {
        if (state == XConfig.DWONLOAD_START) {
            Logger.i("DWONLOAD_START");
            initStart();
        } else if (state == XConfig.DWONLOAD_STOP) {
            Logger.i("DWONLOAD_STOP");
            initStop();
        } else if (state == XConfig.DWONLOAD_FINSH) {
            String path = Environment.getExternalStorageDirectory().getPath() + File.separator + filePath + File.separator;
            try {
                final String file_path = path + getFileName(new URL(downLoadUrl));

                File file = new File(file_path);
                if (file.exists()) {
                    loadListener.hasDown(file_path);
                } else {
                    initStart();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/19 下午3:33
     * @方法作用： 暂定以后再次点击
     */
    private void initStop() {
        final DwonInfo dwonInfo = hashMap.get(downLoadUrl);
        dwonInfo.setState(XConfig.DWONLOAD_START);

        RetrofitUtil.getInstance()
                .build("http://allens/")
                .getService(ApiService.class)
                .downloadFile("bytes=" + dwonInfo.getStartlength() + "-" + dwonInfo.getLength(), downLoadUrl)
                .subscribeOn(Schedulers.io())//在子线程取数据
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        initDownLoadStop(responseBody, dwonInfo);
                    }

                    @Override
                    public void onError(@NonNull final Throwable e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                loadListener.onError(e);
                            }
                        });
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/19 下午3:38
     * @方法作用：继续下载
     */
    private void initDownLoadStop(ResponseBody responseBody, final DwonInfo dwonInfo) {
        InputStream inputStream = responseBody.byteStream();
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + filePath + File.separator;
        try {
            final String file_path = path + getFileName(new URL(downLoadUrl));
            fos = new FileOutputStream(file_path, true);
            byte[] buffer = new byte[1024 * 8];//创建一个缓冲的字节数组
            int len;//用于保存read的返回值
            long startlength = dwonInfo.getStartlength();
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                dwonInfo.setStartlength(startlength += len);


                final int baiFenBi = (int) (((float) startlength) / (dwonInfo.getLength()) * 100);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (baiFenBi == 100) {
                            dwonInfo.setState(XConfig.DWONLOAD_FINSH);
                            isAPK(file_path);
                        }
                        loadListener.onSuccess(baiFenBi, XConfig.DWONLOAD_START);
                    }
                });


                if (dwonInfo.getState() == XConfig.DWONLOAD_STOP) {
                    loadListener.onSuccess(baiFenBi, XConfig.DWONLOAD_STOP);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initDownLoad(ResponseBody responseBody, final DwonInfo dwonInfo) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + filePath + File.separator;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }

        InputStream inputStream = responseBody.byteStream();
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        try {
            final String file_path = path + getFileName(new URL(downLoadUrl));
            fos = new FileOutputStream(file_path);
            byte[] buffer = new byte[1024 * 8];//创建一个缓冲的字节数组
            int len;//用于保存read的返回值
            int startleng = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                dwonInfo.setStartlength(startleng += len);


                final int baiFenBi = (int) (((float) startleng) / (responseBody.contentLength()) * 100);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (baiFenBi == 100) {
                            dwonInfo.setState(XConfig.DWONLOAD_FINSH);
                            isAPK(file_path);
                        }
                        loadListener.onSuccess(baiFenBi, XConfig.DWONLOAD_START);

                    }
                });

                if (dwonInfo.getState() == XConfig.DWONLOAD_STOP) {
                    loadListener.onSuccess(baiFenBi, XConfig.DWONLOAD_STOP);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
                fos.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/19 下午2:43
     * @方法作用： 第一次下载
     */
    private void initStart() {
        DwonInfo dwonInfo = new DwonInfo();
        dwonInfo.setFilepath(filePath);
        dwonInfo.setStartlength(0);
        dwonInfo.setUrl(downLoadUrl);
        dwonInfo.setState(XConfig.DWONLOAD_START);
        hashMap.put(downLoadUrl, dwonInfo);
        initToGetIntentData(dwonInfo);
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/19 下午2:49
     * @方法作用： 第一次获取数据
     */
    private void initToGetIntentData(final DwonInfo dwonInfo) {
        RetrofitUtil.getInstance()
                .build("http://allens/")
                .getService(ApiService.class)
                .downloadFile(downLoadUrl)
                .subscribeOn(Schedulers.io())//在子线程取数据
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        dwonInfo.setLength(responseBody.contentLength());
                        initDownLoad(responseBody, dwonInfo);
                    }

                    @Override
                    public void onError(@NonNull final Throwable e) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                loadListener.onError(e);
                            }
                        });
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/19 下午2:33
     * @方法作用： 获取这个下载任务的状态
     */
    private int getState() {
        DwonInfo dwonInfo = hashMap.get(downLoadUrl);
        if (dwonInfo == null) {
            return XConfig.DWONLOAD_START;
        } else {
            return dwonInfo.getState();
        }
    }

    //获取下载文件的名称
    private String getFileName(URL url) {
        String filename = url.getFile();
        return filename.substring(filename.lastIndexOf("/") + 1);
    }

    public void stop(String downLoadUrl) {
        DwonInfo dwonInfo = hashMap.get(downLoadUrl);
        Logger.i("dwonInfo---->" + dwonInfo);
        if (dwonInfo != null)
            dwonInfo.setState(XConfig.DWONLOAD_STOP);
    }


    //判断是不是APK
    private void isAPK(String file_path) {
        File file = new File(file_path);
        if (file.getName().endsWith(".apk")) {
//            Intent install = new Intent();
//            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            install.setAction(android.content.Intent.ACTION_VIEW);
            Uri uri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);//通过FileProvider创建一个content类型的Uri
            } else {
                uri = Uri.fromFile(file);
            }
            Intent intent = new Intent();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
//            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            context.startActivity(intent);
        }
    }
}
