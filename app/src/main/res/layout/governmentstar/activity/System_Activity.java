package com.lanwei.governmentstar.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.Exit_System;
import com.lanwei.governmentstar.utils.ShortcutBadger;
import com.lanwei.governmentstar.view.Dialog02;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/20.
 */

public class System_Activity extends BaseActivity implements View.OnClickListener{

    private ImageView back;
    private RelativeLayout security;
    private ImageView slider;
    private RelativeLayout privacy;
    private RelativeLayout clear;
    private RelativeLayout VPN;
    private TextView quit;
    private TextView isProtected;
    private PopupWindow popupWindow;
    private SharedPreferences userSettings;
    private SharedPreferences userSettings2;
    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor editor_changed;
    private LinearLayout condition;
    private Dialog02 dialog02;

    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        setContentView(R.layout.layout_system);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        userSettings = getSharedPreferences("user_setting", 0);
        userSettings2 = getSharedPreferences("user_changed", 0);

        editor=userSettings.edit();
        editor_changed=userSettings2.edit();

        initWedge();
        // 根据用户设置，初始化设置slider的显示状态，默认为允许
        if(userSettings.getString("device_security","true").equals("true")){
            isProtected.setText("已保护");
        }else {
            isProtected.setText("未保护");
        }

        if(userSettings.getString("system_notify","true").equals("true")){
            slider.setSelected(true);
        }else {
            slider.setSelected(false);
        }

//        // 版本大于5.0系统，支持沉浸式，隐藏系统状态栏，并适配我们的背景颜色，参照郭霖博客沉浸式效果
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

        //ButterKnife.bind(this);
    }

    void initWedge(){

        back=(ImageView) findViewById(R.id.back);
        security=(RelativeLayout) findViewById(R.id.security);
        privacy=(RelativeLayout) findViewById(R.id.privacy);
        clear=(RelativeLayout) findViewById(R.id.clear);
        VPN=(RelativeLayout) findViewById(R.id.VPN);
        slider=(ImageView) findViewById(R.id.slider);
        quit=(TextView ) findViewById(R.id.quit);
        isProtected=(TextView ) findViewById(R.id.isProtected);

        back.setOnClickListener(this);
        security.setOnClickListener(this);
        VPN.setOnClickListener(this);
        privacy.setOnClickListener(this);
        clear.setOnClickListener(this);
        slider.setOnClickListener(this);
        quit.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.back:

                finish();

                break;

            case R.id.VPN:



                break;

            case R.id.security:

                // todo 安全设备
                Intent intent1=new Intent(this,Device_security_Activity.class);
                startActivity(intent1);

                break;

            case R.id.privacy:

                Intent intent=new Intent(this,Privacy_Setting_Activity.class);
                startActivity(intent);

                break;

            case R.id.clear1:

                popupWindow.dismiss();

                Toast.makeText(this,"清除成功",Toast.LENGTH_SHORT).show();

                break;

            case R.id.cancel:

                popupWindow.dismiss();

                break;

            case R.id.clear:

                // todo  清除缓存

                // 弹出popupwindow前，调暗屏幕的透明度
                WindowManager.LayoutParams lp2 = getWindow().getAttributes();
                lp2.alpha=(float) 0.8;
                getWindow().setAttributes(lp2);

                // 加载popupwindow的布局
                View view2=getLayoutInflater().inflate(R.layout.cache_clear,null);
                popupWindow=new PopupWindow(view2, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                // 初始化popupwindow的点击控件
                TextView clear1=(TextView) view2.findViewById(R.id.clear1);
                TextView cancel=(TextView) view2.findViewById(R.id.cancel);

                // 点击屏幕之外的区域可否让popupwindow消失
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new PoponDismissListener());

                // 注册popupwindow里面的点击事件
                clear1.setOnClickListener(this);
                cancel.setOnClickListener(this);
                View rootview2 = LayoutInflater.from(this).inflate(R.layout.layout_system, null);
                // 设置popupwindow的显示位置
                popupWindow.showAtLocation(rootview2, Gravity.BOTTOM,0,0);

                // todo 清除缓存

                break;


            case R.id.dia_wrong:

                popupWindow.dismiss();

                break;

            case R.id.slider:

                // 根据用户操作，显示slider的状态，并修改sharedpreferences的保存值
                if(userSettings.getString("system_notify","true").equals("true")){
                    slider.setSelected(false);
                    editor.putString("system_notify","false");
                    editor.commit();

                    // 加载popupwindow的布局
                    View view3=getLayoutInflater().inflate(R.layout.system_notification,null);
                    popupWindow=new PopupWindow(view3, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                    // 初始化popupwindow的点击控件
                    TextView content=(TextView) view3.findViewById(R.id.content);
                    content.setText("系统通知已禁止");

                    // 点击屏幕之外的区域可否让popupwindow消失
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());

                    View rootview3 = LayoutInflater.from(this).inflate(R.layout.layout_system, null);
                    // 设置popupwindow的显示位置
                    popupWindow.showAtLocation(rootview3, Gravity.CENTER,0,0);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(2000);
                                slider.post(new Runnable() {
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


                    // todo 网络请求设置系统通知状态为false
                }else if(userSettings.getString("system_notify","false").equals("false")){
                    slider.setSelected(true);
                    editor.putString("system_notify","true");
                    editor.commit();
                    // todo 网络请求设置系统通知状态为true

                    // 加载popupwindow的布局
                    View view4=getLayoutInflater().inflate(R.layout.system_notification,null);
                    popupWindow=new PopupWindow(view4, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                    // 初始化popupwindow的点击控件
                    TextView content=(TextView) view4.findViewById(R.id.content);
                    content.setText("系统通知已开启");

                    // 点击屏幕之外的区域可否让popupwindow消失
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupWindow.setOnDismissListener(new PoponDismissListener());

                    View rootview4 = LayoutInflater.from(this).inflate(R.layout.layout_system, null);
                    // 设置popupwindow的显示位置
                    popupWindow.showAtLocation(rootview4, Gravity.CENTER,0,0);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                Thread.sleep(2000);
                                slider.post(new Runnable() {
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

            case R.id.quit:

                // 弹出dailog并监听summit是监听的回掉
                new DialogUtil(System_Activity.this,new Summit()).showConfirm("退出系统？", "您确定要退出系统吗？", "确定", "取消");

//                dialog02 = new Dialog02(System_Activity.this);
//
//                dialog02.setContent("您确定要退出系统吗？", Color.parseColor("#4f4f4f"));
//                dialog02.setTitle("退出系统", Color.parseColor("#5184c3"));
//                dialog02.setLeftBtn(R.drawable.select_button_left, Color.WHITE);
//                dialog02.setRightBtn(R.drawable.select_button_right, Color.WHITE);
//                dialog02.setYesOnclickListener("退出", new Dialog02.onYesOnclickListener() {
//                    @Override
//                    public void onYesClick() {
//                        modifyData();          // 这里崩了
//
//                    }
//                });
//                dialog02.setNoOnclickListener("取消", new Dialog02.onNoOnclickListener() {
//                    @Override
//                    public void onNoClick() {
//                        dialog02.dismiss();
//                    }
//                });
//
//                Window window = dialog02.getWindow();
//                //设置显示动画
//                window.setWindowAnimations(R.style.dialog_animstyle);
//                WindowManager.LayoutParams wl = window.getAttributes();
//                wl.x = 0;
//
//                wl.y = -getWindowManager().getDefaultDisplay().getHeight() / 50;
//                //设置显示位置
//                dialog02.onWindowAttributesChanged(wl);//设置点击外围解散
//                dialog02.setCanceledOnTouchOutside(true);
//
//                dialog02.show();

                // todo  网络请求退出系统，消除账号

                break;
        }
    }

    // popupwindow消失后触发的方法，将屏幕透明度调为1
    class PoponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            WindowManager.LayoutParams p = getWindow().getAttributes();
            p.alpha=(float) 1;
            getWindow().setAttributes(p);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(userSettings.getString("device_security","true").equals("true")){
            isProtected.setText("已保护");
        }else {
            isProtected.setText("未保护");
        }
    }

    class Summit implements DialogUtil.OnClickListenner {

        public Summit() {
        }

        @Override
        public void yesClick() {

            for(int i = 0; i< Exit_System.list_system.size(); i++){
                Exit_System.list_system.get(i).finish();
                Log.e("cdc饭店吃饭v的",""+Exit_System.list_system.get(i).getLocalClassName());
            }
            // 清空历史数据，保留user_changed的数据，loggingActivity判断用
            editor_changed.putString("change","false");
            getSharedPreferences("accountBean", Context.MODE_PRIVATE).edit().clear().commit();
            getSharedPreferences("user_setting", Context.MODE_PRIVATE).edit().clear().commit();
            editor_changed.commit();
            JPushInterface.setAlias(System_Activity.this, "",new TagAliasCallback(){

                @Override
                public void gotResult(int i, String s, Set<String> set) {

                }
            });
            ShortcutBadger.applyCount(System_Activity.this, 0);
            Intent intent2=new Intent(System_Activity.this,LoggingActivity.class);
            startActivity(intent2);
        }

        @Override
        public void noClick() {}

        @Override
        public void onSingleClick() {

        }
    }


}
