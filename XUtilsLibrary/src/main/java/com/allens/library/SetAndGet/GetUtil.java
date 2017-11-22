package com.allens.library.SetAndGet;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by allens on 2017/6/10.
 */

public class GetUtil {

//    private static GetUtil setUtil;
//
//    public static GetUtil create() {
//        if (setUtil == null) {
//            synchronized (SetUtil.class) {
//                if (setUtil == null) {
//                    setUtil = new GetUtil();
//                }
//            }
//        }
//        return setUtil;
//    }


    boolean isChange = false;

    /**
     * @作者 ：  allens
     * @创建日期 ：2017/3/9 下午5:02
     * @方法作用： 返回当前时间
     */
    private String format = "yyyy-MM-dd HH:mm";

    public GetUtil setFormat(String format) {
        this.format = format;
        isChange = true;
        return this;
    }

    public String getCurrentTime() {
        SimpleDateFormat formatter;
        if (isChange) {
            formatter = new SimpleDateFormat(format);
        }else {
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
        long date = System.currentTimeMillis();
        Date curDate = new Date(date);//获取当前时间
        isChange = false;
        return formatter.format(curDate);
    }
}
