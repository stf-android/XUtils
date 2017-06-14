package com.allens.xutilsdemo;

import android.app.Application;

import com.allens.library.XUtils;

/**
 * Created by allens on 2017/6/14.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        XUtils.create().setCrashHandlerPath("Crash")
                .setCrashHandlerTag("CrashTag")
                .setCrashHandlerTime(30000)
                .startCrashHandler(this);
    }
}
