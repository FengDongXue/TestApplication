package com.lanwei.governmentstar.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lanwei.governmentstar.utils.Constant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/3/21.
 */

public class HttpClient {


    private static HttpClient dataClent;

    public static HttpClient getInstance() {
        if (dataClent == null) {
            dataClent = new HttpClient();
        }
        return dataClent;
    }

    private OkHttpClient okHttpClient;
    private Retrofit retrofit;
    protected GovernmentApi api;

    private HttpClient() {
        //日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //构建OkHttp
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)//日志拦截器
                .build();

        // 让Gson能将bomb返回的时间戳自动转为date对象
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        //构建Retrofit
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                // bomb服务器baseurl
                .baseUrl(Constant.BASEURL)
                // Gson转换器
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    //GovernmentApi
    public GovernmentApi getGovernmentApi() {
        if (api == null) {
            api = retrofit.create(GovernmentApi.class);
        }
        return api;
    }
}


