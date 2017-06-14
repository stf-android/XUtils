package com.allens.xutilsdemo.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.allens.library.XUtils;
import com.allens.xutilsdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by allens on 2017/6/14.
 */

public class GlideAct extends AppCompatActivity {

    @BindView(R.id.activity_Glide_Img)
    ImageView activityGlideImg;
    @BindView(R.id.activity_Glide_btn3)
    Button activityGlideBtn3;
    private String imgUrl = "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4176766613,851262956&fm=26&gp=0.jpg";
    private String gitUrl = "http://img0.imgtn.bdimg.com/it/u=3642474168,2705726260&fm=26&gp=0.jpg";

    @BindView(R.id.activity_Glide_btn1)
    Button activityGlideBtn1;
    @BindView(R.id.activity_Glide_btn2)
    Button activityGlideBtn2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.activity_Glide_btn1, R.id.activity_Glide_btn2, R.id.activity_Glide_btn3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_Glide_btn1:
                XUtils.create().loadImageViewCircle(this, imgUrl, activityGlideImg, R.mipmap.loading, R.mipmap.error, 2, Color.RED);
                break;
            case R.id.activity_Glide_btn2:
                XUtils.create().loadImageViewRound(this, imgUrl, activityGlideImg, R.mipmap.loading, R.mipmap.error, 3);
                break;
            case R.id.activity_Glide_btn3:
                XUtils.create().loadImageViewDynamicGif(this, gitUrl, activityGlideImg, R.mipmap.loading, R.mipmap.error);
                break;
        }
    }
}
