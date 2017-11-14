package com.lanwei.governmentstar.http;

import android.util.Log;

import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ${END}
 * <p>
 * created by song on 2017/2/17.20:pic25
 */

public abstract class CallBackYSAdapter implements Callback<JsonObject> {

    private HashMap<String, String> errorMap;

    public CallBackYSAdapter() {
        // 初始化异常类型
        errorMap = new HashMap<>();
        errorMap.put("1", "此页数据没有更新");
        errorMap.put("2", "服务器忙，请稍候重试");
        errorMap.put("3", "请求参数异常");
    }

    @Override
    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
        //判断是有数据返回
        if (!response.body().isJsonNull()) {
            Log.e("data",response.body().toString());
            parseJson(response.body().toString());
        }
    }

    @Override
    public void onFailure(Call<JsonObject> call, Throwable t) {
        // 显示联网失败的界面
        if (t instanceof RuntimeException) {
            String message = t.getMessage();
            showErrorMessage(message);
        }
        showErrorMessage("服务器忙，请稍候重试");
    }

    protected abstract void showErrorMessage(String message);

    protected abstract void parseJson(String data);
}
