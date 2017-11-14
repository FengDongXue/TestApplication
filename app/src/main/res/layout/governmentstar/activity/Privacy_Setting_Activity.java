package com.lanwei.governmentstar.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Return_Private;
import com.lanwei.governmentstar.bean.SendMSM;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.jar.JarInputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/23.
 */

public class Privacy_Setting_Activity extends BaseActivity implements View.OnClickListener {


    private ImageView back;
    private ImageView slider1;
    private ImageView slider2;
    private ImageView slider3;
    private PopupWindow popupWindow;
    private LinearLayout condition;
    private GovernmentApi api;
    private String first_slider = "0";
    private String secound_slider = "0";
    private String third_slider = "0";

    // 获取系统sharedpreferences，获取用户的设置信息
    private SharedPreferences userSettings;
    private SharedPreferences.Editor editor;
    private Logging_Success bean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.system_privacy);

        userSettings = getSharedPreferences("user_setting", 0);
        editor = userSettings.edit();

        // 版本大于5.0系统，支持沉浸式，隐藏系统状态栏，并适配我们的背景颜色，
        // 参照郭霖博客沉浸式效果，这里只是透明化系统状态栏而已，我们的布局会从系统状态栏的位置开始显示，所以布局中注意给系统状态栏空间
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }else {
//            condition=(LinearLayout) findViewById(R.id.condition);
//            condition.setVisibility(View.GONE);
//        }

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }


        initweight();
    }


    void initweight() {

        back = (ImageView) findViewById(R.id.back);
        slider1 = (ImageView) findViewById(R.id.slider1);
        slider2 = (ImageView) findViewById(R.id.slider2);
        slider3 = (ImageView) findViewById(R.id.slider3);

        back.setOnClickListener(this);

        slider1.setOnClickListener(this);
        slider2.setOnClickListener(this);
        slider3.setOnClickListener(this);

        // 根据用户设置，初始化设置slider的显示状态，默认为允许


        api = HttpClient.getInstance().getGovernmentApi();
        String defString = PreferencesManager.getInstance(this, "accountBean").get("jsonStr");
        Gson gson = new Gson();
        bean = gson.fromJson(defString, Logging_Success.class);
        Call<Return_Private> call = api.getPrivacy(bean.getData().getOpId());

        call.enqueue(new Callback<Return_Private>() {
            @Override
            public void onResponse(Call<Return_Private> call, Response<Return_Private> response) {

                if(response.body() != null){

                    if (response.body().getData() != null && !response.body().getData().equals("")) {
//                        Log.d("1111",response.body().getData().toString());

                        if (response.body().getData().getNotOfficeSendEmail()!= null && response.body().getData().getNotOfficeSendEmail().equals("1")) {
                            slider1.setSelected(true);
                            editor.putString("not_this", "1"); // 保存网络请求的slider的状态
                            editor.commit();
                            Log.e("是vdvd肥嘟嘟的d", "似懂非懂fred购房人");
                            first_slider = "1";  //实时保存用户对slider的操作，以便比较是否发生变化，决定是否提交状态改变
                        } else {
                            slider1.setSelected(false);
                            editor.putString("not_this", "0"); // 保存网络请求的slider的状态
                            editor.commit();
                            first_slider = "0";
                        }

                        if (response.body().getData().getDisplayPhone()!= null && response.body().getData().getDisplayPhone().equals("1")) {
                            slider2.setSelected(true);
                            editor.putString("display_mynumber", "1");
                            editor.commit();
                            secound_slider = "1";
                        } else {
                            slider2.setSelected(false);
                            editor.putString("display_mynumber", "0");
                            editor.commit();
                            secound_slider = "0";
                        }
                        if (response.body().getData().getOfficClerkSendEmail()!= null && response.body().getData().getOfficClerkSendEmail().equals("1")) {
                            slider3.setSelected(true);
                            editor.putString("part_employer", "1");
                            editor.commit();
                            third_slider = "1";
                        } else {
                            slider3.setSelected(false);
                            editor.putString("part_employer", "0");
                            editor.commit();
                            third_slider = "0";
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<Return_Private> call, Throwable t) {

                Toast.makeText(Privacy_Setting_Activity.this, "网络连接有误", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {

        View view;
        View rootview;
        TextView content;

        switch (v.getId()) {
            case R.id.back:
                finish();

                break;

            case R.id.slider1:

                // 根据用户操作，显示slider的状态，并修改sharedpreferences的保存值
                if (first_slider.equals("1")) {

                    first_slider = "0";
                    slider1.setSelected(false);

                    // 加载popupwindow的布局
                    view = getLayoutInflater().inflate(R.layout.system_notification, null);
                    popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                    // 初始化popupwindow的点击控件
                    content = (TextView) view.findViewById(R.id.content);
                    content.setText("已禁止非机关人员向我发邮件");

                    // 点击屏幕之外的区域可否让popupwindow消失
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());

                    rootview = LayoutInflater.from(this).inflate(R.layout.layout_system, null);
                    // 设置popupwindow的显示位置
                    popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(2000);
                                slider1.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        popupWindow.dismiss();
                                    }
                                });

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();


                    // todo 网络请求设置
                } else {
                    first_slider = "1";
                    slider1.setSelected(true);
                    // todo 网络请求设置

                    // 加载popupwindow的布局
                    view = getLayoutInflater().inflate(R.layout.system_notification, null);
                    popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                    // 初始化popupwindow的点击控件
                    content = (TextView) view.findViewById(R.id.content);
                    content.setText("已允许非机关人员向我发邮件");

                    // 点击屏幕之外的区域可否让popupwindow消失
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());

                    rootview = LayoutInflater.from(this).inflate(R.layout.layout_system, null);
                    // 设置popupwindow的显示位置
                    popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                                slider1.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        popupWindow.dismiss();
                                    }
                                });

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();


                }

                break;

            case R.id.slider2:

                // 根据用户操作，显示slider的状态，并修改sharedpreferences的保存值
                if (secound_slider.equals("1")) {

                    secound_slider = "0";
                    slider2.setSelected(false);

                    // 加载popupwindow的布局
                    view = getLayoutInflater().inflate(R.layout.system_notification, null);
                    popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                    // 初始化popupwindow的点击控件
                    content = (TextView) view.findViewById(R.id.content);
                    content.setText("已禁止通讯录中显示我的手机号码");

                    // 点击屏幕之外的区域可否让popupwindow消失
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());

                    rootview = LayoutInflater.from(this).inflate(R.layout.layout_system, null);
                    // 设置popupwindow的显示位置
                    popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(2000);
                                slider1.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        popupWindow.dismiss();
                                    }
                                });

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();


                    // todo 网络请求设置
                } else {

                    secound_slider = "1";
                    slider2.setSelected(true);

                    // 加载popupwindow的布局
                    view = getLayoutInflater().inflate(R.layout.system_notification, null);
                    popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                    // 初始化popupwindow的点击控件
                    content = (TextView) view.findViewById(R.id.content);
                    content.setText("已允许通讯录中显示我的手机号码");

                    // 点击屏幕之外的区域可否让popupwindow消失
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());

                    rootview = LayoutInflater.from(this).inflate(R.layout.layout_system, null);
                    // 设置popupwindow的显示位置
                    popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(2000);
                                slider1.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        popupWindow.dismiss();
                                    }
                                });

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();


                    // todo 网络请求设置
                }
                break;

            case R.id.slider3:

                // 根据用户操作，显示slider的状态，并修改sharedpreferences的保存值
                if (third_slider.equals("1")) {

                    third_slider = "0";
                    slider3.setSelected(false);

                    // 加载popupwindow的布局
                    view = getLayoutInflater().inflate(R.layout.system_notification, null);
                    popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                    // 初始化popupwindow的点击控件
                    content = (TextView) view.findViewById(R.id.content);
                    content.setText("已禁止部分机关人员可以向我发邮件");

                    // 点击屏幕之外的区域可否让popupwindow消失
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());

                    rootview = LayoutInflater.from(this).inflate(R.layout.layout_system, null);
                    // 设置popupwindow的显示位置
                    popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(2000);
                                slider1.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        popupWindow.dismiss();
                                    }
                                });

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    // todo 网络请求设置
                } else {

                    third_slider = "1";
                    slider3.setSelected(true);
                    // todo 网络请求设置

                    // 加载popupwindow的布局
                    view = getLayoutInflater().inflate(R.layout.system_notification, null);
                    popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                    // 初始化popupwindow的点击控件
                    content = (TextView) view.findViewById(R.id.content);
                    content.setText("已允许部分机关人员可以向我发邮件");

                    // 点击屏幕之外的区域可否让popupwindow消失
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());

                    rootview = LayoutInflater.from(this).inflate(R.layout.layout_system, null);
                    // 设置popupwindow的显示位置
                    popupWindow.showAtLocation(rootview, Gravity.CENTER, 0, 0);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(2000);
                                slider1.post(new Runnable() {
                                    @Override
                                    public void run() {

                                        popupWindow.dismiss();
                                    }
                                });

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();


                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (userSettings.getString("not_this", "0").equals(first_slider) && userSettings.getString("display_mynumber", "0").equals(secound_slider) && userSettings.getString("part_employer", "0").equals(third_slider)) {
            return;
        } else {

            Call<SendMSM> call = api.updatePrivacy(bean.getData().getOpId(), first_slider, third_slider, secound_slider);
            call.enqueue(new Callback<SendMSM>() {
                @Override
                public void onResponse(Call<SendMSM> call, Response<SendMSM> response) {

                    if (response.body().getData()) {

                        Toast.makeText(Privacy_Setting_Activity.this, "更改成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Privacy_Setting_Activity.this, "更改失败", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SendMSM> call, Throwable t) {
                    Toast.makeText(Privacy_Setting_Activity.this, "网络连接有误", Toast.LENGTH_SHORT).show();
                }
            });

        }


    }
}
