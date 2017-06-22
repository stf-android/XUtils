package com.allens.library.Utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Allens on 2016/9/13.
 * <!--获取设备当前位置的权限 -->
 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
 */
public class LocationUtil {
    private Context mContext;
    private String provider;// 位置提供器

    public LocationUtil(Context context) {
        this.mContext = context;
    }


    public void setLocationManger(OnResultLocationListener listener) {
        if (hasPermission()) {
            LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            // 获取所有可用的位置提供器
            List<String> providerList = locationManager.getProviders(true);
            if (providerList.contains(LocationManager.GPS_PROVIDER)) {
                //优先使用gps
                provider = LocationManager.GPS_PROVIDER;
                initGetLocation(listener, locationManager);
            } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
                provider = LocationManager.NETWORK_PROVIDER;
                initGetLocation(listener, locationManager);
            } else {
                listener.onNoProvider();
            }
        } else {
            listener.onNoPermission();
        }
    }

    private void initGetLocation(OnResultLocationListener listener, LocationManager locationManager) {
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            double mLongitude = location.getLongitude();
            double mLatitude = location.getLatitude();
            listener.onSuccess(mLongitude, mLatitude);
        } else {
            listener.onFail();
        }
    }

    public interface OnResultLocationListener {
        void onSuccess(double mLongitude, double mLatitude);

        void onFail();

        void onNoPermission();

        void onNoProvider();
    }

    private boolean hasPermission() {
        return ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}
