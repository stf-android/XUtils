package com.allens.xutilsdemo.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.allens.library.Retrofit.OnRetrofit;
import com.allens.library.XUtils;
import com.allens.xutilsdemo.R;
import com.allens.xutilsdemo.bean.WeatherBean;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by allens on 2017/6/14.
 */

public class RetrofitAct extends AppCompatActivity {
    @BindView(R.id.activity_get)
    Button activityGet;
    @BindView(R.id.activity_post)
    Button activityPost;
    @BindView(R.id.activity_down)
    Button activityDown;
    @BindView(R.id.activity_downapk)
    Button activityDownapk;
    @BindView(R.id.activity_progress_down)
    ProgressBar activityProgressDown;
    @BindView(R.id.activity_progress_downapk)
    ProgressBar activityProgressDownapk;
    @BindView(R.id.activity_Retrofit_textview_down)
    TextView activityRetrofitTextviewDown;
    @BindView(R.id.activity_Retrofit_textview_downapk)
    TextView activityRetrofitTextviewDownapk;
    @BindView(R.id.activity_down_stop)
    Button activityDownStop;
    @BindView(R.id.activity_downapk_stop)
    Button activityDownapkStop;

    private String baseUrl = "http://api.jisuapi.com/weather/query/";

    private String getUrl = "http://api.jisuapi.com/weather/query?appkey=052d4c914bc2e308&city=安顺";

    private String downUrl = "http://192.168.1.108/Download/01xl.mp4";
    private String downApkUrl = "http://releases.b0.upaiyun.com/hoolay.apk";
    private String downApkUrl1 = "http://192.168.1.108/App/app-debug.apk";



    private String FilePath = "A_XUtils";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.activity_get, R.id.activity_post, R.id.activity_down, R.id.activity_down_stop, R.id.activity_downapk, R.id.activity_downapk_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_get:
                XUtils.create().retrofitGet(WeatherBean.class, baseUrl, getUrl, new OnRetrofit.OnGetListener<WeatherBean>() {
                    @Override
                    public void onSuccess(WeatherBean weatherBean) {
                        Toast.makeText(RetrofitAct.this, "bean----->" + weatherBean.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RetrofitAct.this, "error----->" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.activity_post:
                XUtils.create().retrofitPost(WeatherBean.class, baseUrl, new OnRetrofit.OnPostListener<WeatherBean>() {
                    @Override
                    public void onMap(HashMap<String, Object> map) {
                        map.put("appkey", "052d4c914bc2e308");
                        map.put("city", "安顺");
                    }

                    @Override
                    public void onSuccess(WeatherBean weatherBean) {
                        Toast.makeText(RetrofitAct.this, "bean----->" + weatherBean.getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RetrofitAct.this, "error----->" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.activity_down:
                XUtils.create().retrofitDown(RetrofitAct.this, downApkUrl, FilePath, new OnRetrofit.OnDownLoadListener() {
                    @Override
                    public void onSuccess(int count, boolean isStart) {
                        activityProgressDown.setProgress(count);
                        activityRetrofitTextviewDown.setText(count + " %");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
                break;
            case R.id.activity_down_stop:
                XUtils.create().retrofitStop(downApkUrl);
                break;
            case R.id.activity_downapk:
                XUtils.create().retrofitDown(RetrofitAct.this, downApkUrl1, FilePath, new OnRetrofit.OnDownLoadListener() {
                    @Override
                    public void onSuccess(int count, boolean isStart) {
                        activityRetrofitTextviewDownapk.setText(count + " %");
                        activityProgressDownapk.setProgress(count);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
                break;
            case R.id.activity_downapk_stop:
                XUtils.create().retrofitStop(downApkUrl1);
                break;
        }
    }
}
