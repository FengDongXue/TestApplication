package com.lanwei.governmentstar.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/24.
 */

public class ForgetPassword_Activity extends BaseActivity implements View.OnClickListener{


    private ImageView back;
    private EditText new_password1;
    private EditText new_password2;
    private EditText userName;
    private TextView submit;
    private LinearLayout condition;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_password);

        // 版本大于5.0系统，支持沉浸式，隐藏系统状态栏，并适配我们的背景颜色，参照郭霖博客沉浸式效果
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

        back=(ImageView) findViewById(R.id.back);
        submit=(TextView) findViewById(R.id.submit);
        new_password1=(EditText) findViewById(R.id.new_password1);
        new_password2=(EditText) findViewById(R.id.new_password2);
        userName=(EditText) findViewById(R.id.userName);

        back.setOnClickListener(this);
        submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.back){
               finish();
        }
        if(v.getId()==R.id.submit){

            if(new_password1.getText().toString().trim().equals(new_password2.getText().toString().trim())){

                // todo 网络氢气
                GovernmentApi api2= HttpClient.getInstance().getGovernmentApi();

                Call<Service_Content> call2= api2.resetPassword(userName.getText().toString(),new_password1.getText().toString());

                call2.enqueue(new Callback<Service_Content>() {
                    @Override
                    public void onResponse(Call<Service_Content> call, Response<Service_Content> response) {
                        if(response.body().getData().compareTo(true)==0){

                            Toast.makeText(ForgetPassword_Activity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(ForgetPassword_Activity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Service_Content> call, Throwable t) {

                        Toast.makeText(ForgetPassword_Activity.this,t.toString(),Toast.LENGTH_SHORT).show();
                        Log.d("LOG", t.toString());
                    }
                });


            }else{
                Toast.makeText(this,"两次输入的密码比一致",Toast.LENGTH_SHORT).show();
            }


        }
    }
}
