package com.lanwei.governmentstar.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.zyx.PasswordActivity;
import com.lanwei.governmentstar.bean.SendMSM;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.TelephoneUtil;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/16.
 */

public class Forget_Activity extends BaseActivity implements View.OnClickListener{

    private ImageView back;
    private TextView send;
    private EditText number;
    private EditText vertify;
    private TextView vertify2;
    private ImageView connect;
    private Timer timer;
    private LinearLayout condition;
    TimerTask timerTask;
    private int rest=60;
    String isQuested ="false";
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget);

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

        intweight();
    }

    void intweight() {

        back = (ImageView) findViewById(R.id.back);
        send = (TextView) findViewById(R.id.send);
        number = (EditText) findViewById(R.id.number);
        vertify = (EditText) findViewById(R.id.vertify);
        vertify2 = (TextView) findViewById(R.id.vertify2);
        connect = (ImageView) findViewById(R.id.connect);
        condition = (LinearLayout) findViewById(R.id.condition);

        back.setOnClickListener(this);
        vertify2.setOnClickListener(this);
        connect.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(rest != 0){
                vertify2.setText("验证登证码("+rest+"s)");
            }
            if(rest==0){
                rest= 60;
                timer.cancel();
                timer=null;
                timerTask=null;
                vertify2.setText("验证登证码("+rest+"s)");
                isQuested="false";
            }

        }
    };



    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.back:

                finish();

                break;

            case R.id.vertify2:


                String name=number.getText().toString().trim();
                String verty=vertify.getText().toString().trim();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(verty)){
                    Toast.makeText(this,"号码或验证码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                GovernmentApi api2= HttpClient.getInstance().getGovernmentApi();

                Call<SendMSM> call2= api2.getCode(name,verty);

                call2.enqueue(new Callback<SendMSM>() {
                    @Override
                    public void onResponse(Call<SendMSM> call, Response<SendMSM> response) {

                        if(response.body().getData().compareTo(true)==0){

                            //  验证成功，跳到重新设定密码界面

                            Toast.makeText(Forget_Activity.this,"验证成功",Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(Forget_Activity.this,PasswordActivity.class);
                            startActivity(intent);
                            finish();

                        }else{

                            Toast.makeText(Forget_Activity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<SendMSM> call, Throwable t) {

                        Toast.makeText(Forget_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();
                    }
                });

                //  todo  后台交互

                break;

            case R.id.connect:

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                } else {
                    callPhone();
                }


                break;

            case R.id.send:

                if(isQuested.equals("true")){
                    return;
                }

                if(number.getText().toString().trim().equals("")){
                    Toast.makeText(Forget_Activity.this, "请输入手机号码！", Toast.LENGTH_SHORT).show();
                }else{
                    timer=new Timer();

                    timerTask=new TimerTask() {
                        @Override
                        public void run() {
                            rest--;
                            Message message=new Message();
                            handler.sendMessage(message);
                        }
                    };

                    String telephone=number.getText().toString().trim();
                    GovernmentApi api= HttpClient.getInstance().getGovernmentApi();

                    Call<SendMSM> call= api.sendMSM(telephone);
                    call.enqueue(new Callback<SendMSM>() {
                        @Override
                        public void onResponse(Call<SendMSM> call,Response<SendMSM> response) {
                            if(response.body().getData()){

                                isQuested="true";
                                timer.schedule(timerTask,0,1000);
                                Toast.makeText(Forget_Activity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();

                            }else{

                                Toast.makeText(Forget_Activity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<SendMSM> call, Throwable t) {

                            Toast.makeText(Forget_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();

                        }
                    });

                }


                break;

        }

    }


    // 拨打电话权限的申请
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    callPhone();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(this, "您已拒绝了授权打电话权限,如需重新授权请到手机设置里更改", Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
            }
        }
    }


    //拨打电话操作
    public void callPhone() {
        Intent intent2 = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "4006970960");
        intent2.setData(data);
        try {
            startActivity(intent2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
