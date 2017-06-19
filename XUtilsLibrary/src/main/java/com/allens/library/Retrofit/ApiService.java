package com.allens.library.Retrofit;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by allens on 2017/6/5.
 */

public interface ApiService {
    @GET
    Observable<ResponseBody> getGetData(@Url String url);

    @FormUrlEncoded
    @POST(".")
    Observable<ResponseBody> getPostData(@FieldMap Map<String, Object> map);

    @Streaming //大文件时要加不然会OOM
    @GET
    Observable<ResponseBody> downloadFile(@Header("Range") String range, @Url String fileUrl);

    @Streaming //大文件时要加不然会OOM
    @GET
    Observable<ResponseBody> downloadFile(@Url String fileUrl);
}
