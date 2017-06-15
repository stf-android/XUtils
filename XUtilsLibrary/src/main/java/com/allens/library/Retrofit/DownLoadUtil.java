package com.allens.library.Retrofit;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;

import com.orhanobut.logger.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;

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
    private DownLoadInfo downLoadInfo;
    private Long startLong;

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
        if (downLoadInfo == null) {
            downLoadInfo = new DownLoadInfo();
            downLoadInfo.setLength((long) 0);
            downLoadInfo.setStartLong((long) 0);
            downLoadInfo.setStop(false);
        }
        downLoadInfo.setUrl(downLoadUrl);
        RetrofitUtil.getInstance()
                .build("http://allens/")
                .getService(ApiService.class)
                .downloadFile("bytes=" + downLoadInfo.getStartLong() + "-" + downLoadInfo.getLength(), downLoadUrl)
                .subscribeOn(Schedulers.io())//在子线程取数据
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody response) {
                        Logger.i("获取需要下载的信息----》" + response.contentLength());
                        initDown(downLoadUrl, response, filePath, listener);
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

    private void initDown(final String downLoadUrl, ResponseBody response, String filePath, final OnRetrofit.OnDownLoadListener listener) {
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
            RandomAccessFile raf = new RandomAccessFile(file_path, "rw");
            byte[] buffer = new byte[1024 * 8];//创建一个缓冲的字节数组
            int len;//用于保存read的返回值
            startLong = downLoadInfo.getStartLong();
            Logger.i("startLong---->" + startLong);
            while ((len = bis.read(buffer)) != -1) {
                raf.write(buffer, 0, len);
                startLong += len;
                downLoadInfo.setStartLong(startLong);
                Logger.i("length--->" + downLoadInfo.getLength());
                if (downLoadInfo.getLength() == 0) {// 如果在SHARE 里面没有 保存 文件大小  说明是第一次点击下载  直接获取
                    int baiFenBi = (int) (((float) startLong) / (response.contentLength()) * 100);
                    downLoadInfo.setLength(response.contentLength());
                    downLoadInfo.setBaiFen(baiFenBi);
                    downLoadInfo.setStop(false);
                } else {
                    int baiFenBi = (int) (((float) startLong) / downLoadInfo.getLength() * 100);// 将保存的文件大小拿出来
                    downLoadInfo.setBaiFen(baiFenBi);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (downLoadInfo.isStop()) {// 如果点击停止    返回  false
                            listener.onSuccess(downLoadInfo.getBaiFen(), false);
                        } else {
                            listener.onSuccess(downLoadInfo.getBaiFen(), true);
                        }
                    }
                });
                if (downLoadInfo.isStop()) {// 停止的时候  将长度保存
                    break;
                }

                break;
            }
            raf.close();
            if (downLoadInfo.getBaiFen() == 100) {
                downLoadInfo = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
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
//    // 关闭下载
//    public void stop(String downUrl) {
//        stopMap.put(downUrl, true);
//    }
}
