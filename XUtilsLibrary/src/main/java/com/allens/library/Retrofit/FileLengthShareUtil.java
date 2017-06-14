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

    public long getStartIndex() {
        return getSharedPreferences().getLong(startIndex, -1);
    }

    public void clearStartIndex() {
        getSharedPreferences().edit().remove(startIndex).apply();
    }

    public void putStartIndex(long Index) {
        getSharedPreferences().edit().putLong(startIndex, Index).apply();
    }


    public long getLength() {
        return getSharedPreferences().getLong(length, -1);
    }

    public void clearLength() {
        getSharedPreferences().edit().remove(length).apply();
    }

    public void putLength(long Index) {
        getSharedPreferences().edit().putLong(length, Index).apply();
    }

}
