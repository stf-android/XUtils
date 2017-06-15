package com.allens.library.Retrofit;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by allens on 2017/6/13.
 */

public class FileLengthShareUtil {

    private static FileLengthShareUtil mInstance;
    private Context mContext;

    public FileLengthShareUtil(Context context) {
        this.mContext = context;
    }

    public static FileLengthShareUtil getInstance(Context context) {
        if (mInstance == null) {
            synchronized (FileLengthShareUtil.class) {
                mInstance = new FileLengthShareUtil(context);
            }
        }
        return mInstance;
    }

    private SharedPreferences getSharedPreferences() {
        return mContext.getSharedPreferences("SharePref", Context.MODE_PRIVATE);
    }

    private String startIndex = "startIndex";
    private String length = "length";

    public long getStartIndex(String key) {
        return getSharedPreferences().getLong(key + startIndex, -1);
    }

    public void clearStartIndex(String key) {
        getSharedPreferences().edit().remove(key + startIndex).apply();
    }

    public void putStartIndex(String key, long Index) {
        getSharedPreferences().edit().putLong(key + startIndex, Index).apply();
    }


    public long getLength(String key) {
        return getSharedPreferences().getLong(key + length, -1);
    }

    public void clearLength(String key) {
        getSharedPreferences().edit().remove(key + length).apply();
    }

    public void putLength(String key, long Index) {
        getSharedPreferences().edit().putLong(key + length, Index).apply();
    }

}
