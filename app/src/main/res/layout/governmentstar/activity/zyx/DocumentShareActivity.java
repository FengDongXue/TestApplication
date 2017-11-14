package com.lanwei.governmentstar.activity.zyx;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.view.StatusBarUtils;

/**
 * Created by 蓝威科技-技术部3 on 2017/4/25.
 */

public class DocumentShareActivity extends AppCompatActivity implements View.OnClickListener{

    //中间标题   等待审批  文件快速设置
    private TextView tv_address,wait_approval,quick_setting;
    //返回键,圆角图  模糊查询
    private ImageView back,iv_contacts;
    //快速审批子条   快速设置子条
    private LinearLayout wait_layout,setting_layout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.documentshare_layout);

        initview();
    }

    private void initview() {
        tv_address= (TextView) findViewById(R.id.tv_address);
        wait_approval= (TextView) findViewById(R.id.wait_approval);
        quick_setting= (TextView) findViewById(R.id.quick_setting);
        back= (ImageView) findViewById(R.id.back);
        iv_contacts= (ImageView) findViewById(R.id.iv_contacts);
        wait_layout= (LinearLayout) findViewById(R.id.wait_layout);
        setting_layout= (LinearLayout) findViewById(R.id.setting_layout);

        tv_address.setVisibility(View.VISIBLE);
        tv_address.setText("共享审批");
        back.setVisibility(View.VISIBLE);
        iv_contacts.setVisibility(View.GONE);
        back.setOnClickListener(this);
        wait_approval.setOnClickListener(this);
        quick_setting.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.wait_approval:
                wait_approval.setTextColor(Color.parseColor("#00A7E4"));
                quick_setting.setTextColor(Color.parseColor("#333333"));
                wait_layout.setVisibility(View.VISIBLE);
                setting_layout.setVisibility(View.GONE);
                break;
            case R.id.quick_setting:
                wait_approval.setTextColor(Color.parseColor("#333333"));
                quick_setting.setTextColor(Color.parseColor("#00A7E4"));
                wait_layout.setVisibility(View.GONE);
                setting_layout.setVisibility(View.VISIBLE);
                break;
        }
    }
}
