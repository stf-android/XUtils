package com.allens.library.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by allens on 2017/6/23.
 */

public class PrefUtils {

    private String path = "share_data";

    private static PrefUtils prefUtils;
    private final SharedPreferences sp;


    public PrefUtils(Context context) {
        sp = context.getSharedPreferences(path, Context.MODE_PRIVATE);
    }

    public static PrefUtils create(Context context) {
        if (prefUtils == null) {
            synchronized (PrefUtils.class) {
                if (prefUtils == null) {
                    prefUtils = new PrefUtils(context);
                }
            }
        }
        return prefUtils;
    }


    public void setPath(String path) {
        this.path = path;
    }

    public void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public void putString(String key, String value) {
        sp.edit().putString(key, value).apply();
    }

    public String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public void putInt(String key, int value) {
        sp.edit().putInt(key, value).apply();
    }

    public int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public void remove(String key) {
        sp.edit().remove(key).apply();
    }

    public void clear() {
        if (sp != null)
            sp.edit().clear().apply();
    }
}
