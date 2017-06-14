package com.allens.library.SetAndGet;

import android.app.Activity;
import android.content.pm.ActivityInfo;

/**
 * Created by allens on 2017/6/10.
 */

public class SetUtil {


    private static SetUtil setUtil;

    public static SetUtil create() {
        if (setUtil == null) {
            synchronized (SetUtil.class) {
                if (setUtil == null) {
                    setUtil = new SetUtil();
                }
            }
        }
        return setUtil;
    }


    /**
     * @作者 ：  allens
     * @创建日期 ：2017/6/2 上午11:47
     * @方法作用： 设置竖屏
     */
    public void setActivityInfo(Activity activity) {
        if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

}
