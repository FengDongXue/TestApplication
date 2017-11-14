package com.lanwei.governmentstar.http;

/**
 * Created by 蓝威科技—技术部2 on 2017/3/21.
 */
import android.os.Handler;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HttpHelper {

    private static HttpHelper mInstance  = new HttpHelper();
    OkHttpClient client;
    Handler handler = new Handler();
    private HttpHelper(){
        //创建okhttpclient对象
        client = new OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    public static HttpHelper create(){
        return mInstance;
    }

    //执行get请求的方法
    public void execGet(String url,final HttpCallback callback){
        //创建请求体
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFail(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(string);
                    }
                });
            }
        });
    }



    public interface HttpCallback{
        void onSuccess(String result);
        void onFail(Exception e);
    }
}
