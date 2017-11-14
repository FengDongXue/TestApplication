package com.lanwei.governmentstar.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.Device_Logged;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.view.StatusBarUtils;


import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/23.
 */

public class Device_security_Activity extends BaseActivity implements View.OnClickListener{


    private ImageView back;
    private ImageView slider;
    private RecyclerView rv;
    private Adapter_Device adapter;
    private SharedPreferences userSettings;
    private SharedPreferences.Editor editor;
    private ArrayList<Device_Logged> data = null;
    private PopupWindow popupWindow;
    private LinearLayout condition;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_security);

        userSettings = getSharedPreferences("user_setting", 0);
        editor=userSettings.edit();

        // 版本大于5.0系统，支持沉浸式，隐藏系统状态栏，并适配我们的背景颜色，参照郭霖博客沉浸式效果
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


        back=(ImageView) findViewById(R.id.back);
        slider=(ImageView) findViewById(R.id.slider);
        slider.setOnClickListener(this);
        back.setOnClickListener(this);

        // 根据用户设置，初始化设置slider的显示状态，默认为允许
        if(userSettings.getString("device_security","true").equals("true")){
            slider.setSelected(true);
        }else if(userSettings.getString("device_security","true").equals("false")){
            slider.setSelected(false);
        }

        // 模拟数据
        Device_Logged device=new Device_Logged("android","2.3");
        data=new ArrayList();
        data.add(device);
        adapter=new Adapter_Device(data);

        rv=(RecyclerView) findViewById(R.id.rv);
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rv.setLayoutManager(new LinearLayoutManager(this));

        rv.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:

                finish();

                break;

            case R.id.slider:

                if(userSettings.getString("device_security","true").equals("true")){

                    slider.setSelected(false);
                    editor.putString("device_security","false");

                    // 加载popupwindow的布局
                    View view3=getLayoutInflater().inflate(R.layout.system_notification,null);
                    popupWindow=new PopupWindow(view3, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                    // 初始化popupwindow的点击控件
                    TextView content3=(TextView) view3.findViewById(R.id.content);
                    content3.setText("设备保护已关闭");

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


                    // todo 不开启设备保护
                }else if(userSettings.getString("device_security","true").equals("false")){

                    slider.setSelected(true);
                    editor.putString("device_security","true");
                    // todo 开启设备保护


                    // 加载popupwindow的布局
                    View view=getLayoutInflater().inflate(R.layout.system_notification,null);
                    popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                    // 初始化popupwindow的点击控件
                    TextView content=(TextView) view.findViewById(R.id.content);
                    content.setText("设备保护已开启");

                    // 点击屏幕之外的区域可否让popupwindow消失
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());

                    View rootview = LayoutInflater.from(this).inflate(R.layout.layout_system, null);
                    // 设置popupwindow的显示位置
                    popupWindow.showAtLocation(rootview, Gravity.CENTER,0,0);

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
                editor.commit();
                break;
        }
    }


    class Adapter_Device extends RecyclerView.Adapter<Adapter_Device.MyViewHolder> {

        private View view = null;
        private ArrayList<Device_Logged> datas = null;

        public Adapter_Device(ArrayList datas) {
            this.datas = datas;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.devices_logged,parent,false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            holder.name.setText(datas.get(position).getName());
            holder.version.setText(datas.get(position).getVersion());
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView name;
            private TextView version;

            public MyViewHolder(View itemView) {

                super(itemView);
                name = (TextView) view.findViewById(R.id.name);
                version = (TextView) view.findViewById(R.id.version);
            }
        }
    }


}
