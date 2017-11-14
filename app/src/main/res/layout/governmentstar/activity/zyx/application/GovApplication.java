package com.lanwei.governmentstar.activity.zyx.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.lanwei.governmentstar.activity.AnimActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 蓝威科技—技术部2 on 2017/5/6.
 */

public class GovApplication extends Application {

    private static GovApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        if (instance == null) {
            instance = this;
        }
        //初始化Fresco
        Fresco.initialize(this);

//        Thread.setDefaultUncaughtExceptionHandler(restartHandler); // 程序崩溃时触发线程  以下用来捕获程序崩溃异常
    }

    // 创建服务用于捕获崩溃异常
    private Thread.UncaughtExceptionHandler restartHandler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {
            restartApp();//发生崩溃异常时,重启应用
        }
    };
    public void restartApp(){
        Intent intent = new Intent(instance,AnimActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        instance.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());  //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
    }
    public static Context getInstance() {
        return instance;
    }
}