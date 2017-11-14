package com.lanwei.governmentstar.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.SendMSM;
import com.lanwei.governmentstar.bean.Service_Content;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.view.MyTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/16.
 */

public class ServiceCenter_Activity extends BaseActivity implements View.OnClickListener{

    private ImageView back;
//    private MyTextView spacial;
    private LinearLayout condition;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else {
            condition=(LinearLayout) findViewById(R.id.condition);
            condition.setVisibility(View.GONE);
        }

        intweight();

    }

    void intweight() {

        back = (ImageView) findViewById(R.id.back);
//        spacial = (MyTextView) findViewById(R.id.spacial);
//        String content2="       政务之星OA系统用户帐户是一种服务，可让您登录到政务之星系统以及系统相关的网站和服务。\\n       创建用户帐户时我方经您所在机关部门同意，获得您的个人姓名、联系电话及职务，并为您的单位提供的信息进行保密。只要您的用户帐户仍处于活动状态，您的用户帐户以及电子邮件地址或用户名对您是唯一的。如果我公司发现您的用户账户出现违法违规行为，依据本协议的条款我公司将关闭您的用户帐户，并向贵单位进行通报，依法追究责任。\\n       若要访问服务网站和系统后台，必须使用用户帐户登录（用户名和密码）。您负责保持帐户信息和密码的机密性，并对您的用户帐户下发生的所有活动负责。可以使用您的用户帐户来访问蓝威科技其他产品、网站或服务。如果这样做，在适用于这些产品、网站或服务的条款或条件（包括其各自的隐私声明）与本协议不同时，它们也将适用于您对该产品、网站或服务的使用。如果您的服务被取消，我方将删除与您的用户帐户出现违法违规相关联的信息或内容，除非法律要求我方保留它作为证据保存。";
//        spacial.setSpecifiedTextsColor(content2,"如果您的服务被取消，我方将删除与您的用户帐户出现违法违规相关联的信息或内容，除非法律要求我方保留它作为证据保存。",Color.parseColor("#232323"));

        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.back){
            finish();
        }
    }
}
