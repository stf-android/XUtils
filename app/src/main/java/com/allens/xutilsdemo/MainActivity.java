package com.allens.xutilsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.allens.library.RxBinding.OnRxBind;
import com.allens.library.XUtils;
import com.allens.xutilsdemo.Activity.GlideAct;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.activity_Glide)
    Button activityGlide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        XUtils.create().setSixPermission(this)//设置6.0 需要的基本权限
                .setActivityInfo(this);//设置竖屏


        XUtils.create().click(activityGlide, new OnRxBind.OnRxBindingListener() {
            @Override
            public void OnClickListener() {
                startActivity(new Intent(MainActivity.this, GlideAct.class));
            }
        });
    }
}
