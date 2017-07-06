package com.allens.library.Retrofit;

import java.util.HashMap;

/**
 * Created by allens on 2017/6/10.
 */

public interface OnRetrofit<T> {

    interface OnGetListener<T> {
        void onSuccess(T t);

        void onError(Throwable e);
    }

    interface OnPostListener<T> {

        void onMap(HashMap<String, Object> map);

        void onSuccess(T t);

        void onError(Throwable e);
    }


    interface OnDownLoadListener {

        void onSuccess(int count, int State);

        void onError(Throwable e);

        void hasDown(String path);
    }

}
