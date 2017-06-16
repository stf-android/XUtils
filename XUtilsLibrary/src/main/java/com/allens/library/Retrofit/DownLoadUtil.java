package com.allens.library.Retrofit;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.orhanobut.logger.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by allens on 2017/6/13.
 */

public class DownLoadUtil {
    private BufferedInputStream bis;
    private Handler handler = new Handler();
    private Context context;
    private static DownLoadUtil mInstance;
    private Long startLong;
    private HashMap<String, DownLoadInfo> hashMap = new HashMap<>();
    private FileOutputStream fos;

    public static DownLoadUtil getInstance() {
        if (mInstance == null) {
            synchronized (DownLoadUtil.class) {
                mInstance = new DownLoadUtil();
            }
        }
        return mInstance;
    }

    public void download(Context context, final String downLoadUrl, final String filePath, final OnRetrofit.OnDownLoadListener listener) {
        this.context = context;
        DownLoadInfo info = hashMap.get(downLoadUrl);
        if (info == null) {
            info = new DownLoadInfo();
            hashMap.put(downLoadUrl, info);
            info.setLength(Long.valueOf(-1));
            info.setStartLong((long) 0);
            info.setUrl(downLoadUrl);
        }
        info.setStop(false);
        Logger.i("start---->" + info.getStartLong() + "    length---->" + info.getLength());
        final DownLoadInfo finalInfo = info;
        RetrofitUtil.getInstance()
                .build("http://allens/")
                .getService(ApiService.class)
                .downloadFile("bytes=" + info.getStartLong() + "-" + info.getLength(), downLoadUrl)
                .subscribeOn(Schedulers.io())//在子线程取数据
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull final ResponseBody response) {
                        initDown(downLoadUrl, response, filePath, finalInfo, listener);
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

    private void initDown(final String downLoadUrl, ResponseBody response, String filePath, DownLoadInfo finalInfo, final OnRetrofit.OnDownLoadListener listener) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + filePath + File.separator;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        InputStream inputStream = response.byteStream();
        try {
            bis = new BufferedInputStream(inputStream);
            final String file_path = path + getFileName(new URL(downLoadUrl));
            fos = new FileOutputStream(file_path, true);
            byte[] buffer = new byte[1024 * 8];//创建一个缓冲的字节数组
            int len;//用于保存read的返回值
            startLong = finalInfo.getStartLong();
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                startLong += len;
                finalInfo.setStartLong(startLong);
                if (finalInfo.getLength() == -1) {// 如果在SHARE 里面没有 保存 文件大小  说明是第一次点击下载  直接获取
                    int baiFenBi = (int) (((float) startLong) / (response.contentLength()) * 100);
                    finalInfo.setLength(response.contentLength());
                    finalInfo.setBaiFen(baiFenBi);
                } else {
                    int baiFenBi = (int) (((float) startLong) / finalInfo.getLength() * 100);// 将保存的文件大小拿出来
                    finalInfo.setBaiFen(baiFenBi);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (hashMap.get(downLoadUrl) != null)
                            listener.onSuccess(hashMap.get(downLoadUrl).getBaiFen(), hashMap.get(downLoadUrl).isStop());
                        else
                            listener.onSuccess(100, false);
                    }
                });
                if (finalInfo.isStop()) {// 停止
                    break;
                }
            }
            if (finalInfo.getBaiFen() == 100)
                hashMap.remove(downLoadUrl);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
                bis.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //
//    //判断是不是APK
//    private void isAPK(String file_path) {
//        File file = new File(file_path);
//        if (file.getName().endsWith(".apk")) {
//            Intent install = new Intent();
//            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            install.setAction(android.content.Intent.ACTION_VIEW);
//            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//            context.startActivity(install);
//        }
//    }
//
    //获取下载文件的名称
    private String getFileName(URL url) {
        String filename = url.getFile();
        return filename.substring(filename.lastIndexOf("/") + 1);
    }

    //
    // 关闭下载
    public void stop(String downUrl) {
        DownLoadInfo downLoadInfo = hashMap.get(downUrl);
        if (downLoadInfo != null) {
            downLoadInfo.setStop(true);
        }
    }
}
