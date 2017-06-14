package com.allens.xutilsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.allens.library.XUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XUtils.create().setSixPermission(this)//设置6.0 需要的基本权限
                .setActivityInfo(this);//设置竖屏


    }
}
