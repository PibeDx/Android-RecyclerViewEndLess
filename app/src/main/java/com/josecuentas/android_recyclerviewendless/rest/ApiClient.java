package com.josecuentas.android_recyclerviewendless.rest;

import com.josecuentas.android_recyclerviewendless.rest.entity.JobEntity;
import com.josecuentas.android_recyclerviewendless.rest.response.BaseResponse;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by jcuentas on 23/12/16.
 */

public class ApiClient {
    private static final String TAG = ApiClient.class.getSimpleName();
    private static final String PATH = "https://api.backendless.com";
    private static Retrofit retrofit;

    public static ACInterface getACInterface() {
        retrofit = new Retrofit.Builder()
                .baseUrl(PATH)
                .client(getBasicClientInterceptor())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ACInterface acInterface = retrofit.create(ACInterface.class);
        return  acInterface;
    }

    public interface ACInterface {
        /*Reemplazar [application-id, secret-key] de backendless por las tuyas*/
        @Headers({"application-id: 762DF520-14F3-8CCF-FF3A-77FCDB9B6F00",
                "secret-key: DB8ACA6C-0CD3-22AC-FF57-459D5762C800"})
        @GET("v1/data/Job")
        Call<BaseResponse<JobEntity>> getAllJobByPage(@Query("pageSize") int pageSize,
                                                      @Query("offset") int offset);
    }


    public static OkHttpClient getBasicClientInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(logging);
        builder.readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();
        return client;
    }
}
