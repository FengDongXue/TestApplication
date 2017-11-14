package com.lanwei.governmentstar.demo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.utils.Exit_System;
import com.lanwei.governmentstar.utils.LogUtils;
import com.lanwei.governmentstar.utils.ManagerUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/12/27 0027.
 */
public class BaseActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        Exit_System.addActivity(this);
        Log.e("cdc饭店吃饭v的",""+this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 清楚所有的极光推送
        JPushInterface.clearAllNotifications(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Exit_System.removeActivity(this);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.fontScale != 1)//非默认值
            getResources();
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }

    /**
     * @param response  接口返回数据
     * @param tag	哪个接口的调用
     */

    protected void baseJson(String response,String tag){
        try {
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.getString("result").equals("0")){//判断是否正确
                baseJsonNext(jsonObject.getString("data"),tag);
            }else{//不正确打印信息
                ManagerUtils.showToast(BaseActivity.this, jsonObject.getString("message"));
                baseJsonErr(jsonObject.getString("message"));
            }
            ManagerUtils.showToast(BaseActivity.this, jsonObject.getString("message"));
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ManagerUtils.showToast(BaseActivity.this, getResources().getString(R.string.Network_jsonfailure));
            baseJsonErr(getResources().getString(R.string.Network_jsonfailure));
        }
    }
    /**
     * @param response  接口返回数据
     * @param tag	哪个接口的调用
     */
    protected void baseJsonALL(String response,String tag){
        try {
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.getString("result").equals("0")){//判断是否正确
                baseJsonNext(jsonObject.toString(),tag);
            }else{//不正确打印信息
                ManagerUtils.showToast(BaseActivity.this, jsonObject.getString("message"));
                baseJsonErr(jsonObject.getString("message"));
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ManagerUtils.showToast(BaseActivity.this, getResources().getString(R.string.Network_jsonfailure));
            baseJsonErr(getResources().getString(R.string.Network_jsonfailure));
        }
    }
    /**
     * 无数据请求的接口返回没有data
     * @param response
     * @param tag
     */
    protected void baseJsonText(String response,String tag){
        try {
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.getString("result").equals("0")){//判断是否正确
                baseJsonNext("",tag);
            }else{//不正确打印信息
                ManagerUtils.showToast(BaseActivity.this, jsonObject.getString("message"));
                baseJsonErr(jsonObject.getString("message"));
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ManagerUtils.showToast(BaseActivity.this,getResources().getString(R.string.Network_jsonfailure));
            baseJsonErr(getResources().getString(R.string.Network_jsonfailure));
        }
    }

    /**
     * 如果加載出現錯誤提示觸發
     * @param err
     */
    protected void baseJsonErr(String err){
    }
    protected void baseJsonNext(String response,String tag){
        LogUtils.d(tag, response);
    }
}
