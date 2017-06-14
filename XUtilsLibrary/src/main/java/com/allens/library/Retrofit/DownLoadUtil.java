package com.allens.library.Retrofit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    private FileOutputStream fos;
    private BufferedInputStream bis;
    private InputStream inputStream;
    private Handler handler = new Handler();
    private boolean isStop = false;
    private Context context;
    private long currentLength;
    private static DownLoadUtil mInstance;
    private long FileLength;
    private int baiFenBi;

    public static DownLoadUtil getInstance() {
        if (mInstance == null) {
            synchronized (DownLoadUtil.class) {
                mInstance = new DownLoadUtil();
            }
        }
        return mInstance;
    }

    public void download(Context context, final String downLoadUrl, final String filePath, final boolean isAPK, final OnRetrofit.OnDownLoadListener listener) {
        this.isStop = false;
        this.context = context;
        long startIndex = FileLengthShareUtil.getInstance(context).getStartIndex();//获取我们保存在  share里面的  开始位置
        FileLength = FileLengthShareUtil.getInstance(context).getLength();// 获取  文件的大小
        if (startIndex == -1) {
            currentLength = 0;
        } else {
            currentLength = startIndex;
        }
        RetrofitUtil.getInstance()
                .build("http://allens/")
                .getService(ApiService.class)
                .downloadFile("bytes=" + startIndex + "-" + FileLength, downLoadUrl)
                .subscribeOn(Schedulers.io())//在子线程取数据
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody response) {
                        FileDownLoad(downLoadUrl, response, filePath, isAPK, listener);
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

    private void FileDownLoad(String downLoadUrl, ResponseBody response, String filePath, final boolean isAPK, final OnRetrofit.OnDownLoadListener listener) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + filePath + File.separator;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        inputStream = response.byteStream();
        try {
            bis = new BufferedInputStream(inputStream);
            final String file_path = path + getFileName(new URL(downLoadUrl));
            fos = new FileOutputStream(file_path);
            byte[] buffer = new byte[1024 * 8];//创建一个缓冲的字节数组
            int len;//用于保存read的返回值
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                fos.flush();
                currentLength += len;
                if (FileLength == -1) {// 如果在SHARE 里面没有 保存 文件大小  说明是第一次点击下载  直接获取
                    baiFenBi = (int) (((float) currentLength) / (response.contentLength()) * 100);
                } else {
                    baiFenBi = (int) (((float) currentLength) / FileLength * 100);// 将保存的文件大小拿出来
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (isStop) {// 如果点击停止    返回  false
                            listener.onSuccess(baiFenBi, false);
                        } else {
                            listener.onSuccess(baiFenBi, true);
                        }

                        if (baiFenBi == 100) {
                            FileLengthShareUtil.getInstance(context).clearLength();
                            FileLengthShareUtil.getInstance(context).clearStartIndex();
                            if (isAPK)
                                isAPK(file_path);
                        }
                    }
                });

                if (isStop) {// 停止的时候  将长度保存
                    FileLengthShareUtil.getInstance(context).putStartIndex(currentLength);
                    long length = FileLengthShareUtil.getInstance(context).getLength();
                    if (length == -1)// 说明是第一次  就将文件的大小保存
                        FileLengthShareUtil.getInstance(context).putLength(response.contentLength());
                    break;
                }
            }
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

    //判断是不是APK
    private void isAPK(String file_path) {
        File file = new File(file_path);
        if (file.getName().endsWith(".apk")) {
            Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(android.content.Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            context.startActivity(install);
        }
    }

    //获取下载文件的名称
    private String getFileName(URL url) {
        String filename = url.getFile();
        return filename.substring(filename.lastIndexOf("/") + 1);
    }

    // 关闭下载
    public void stop() {
        this.isStop = true;
    }
}
