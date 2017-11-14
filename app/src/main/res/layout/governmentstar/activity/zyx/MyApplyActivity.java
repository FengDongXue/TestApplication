package com.lanwei.governmentstar.activity.zyx;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.view.StatusBarUtils;

/**
 * Created by 蓝威科技-技术部3 on 2017/4/28.
 */

public class MyApplyActivity extends AppCompatActivity implements View.OnClickListener{

    //中间标题
    private TextView tv_address;
    //返回键
    private ImageView back,iv_contacts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.myapply_layout);

        initview();
    }

    private void initview() {
        tv_address= (TextView) findViewById(R.id.tv_address);
        back= (ImageView) findViewById(R.id.back);
        iv_contacts= (ImageView) findViewById(R.id.iv_contacts);

        back.setVisibility(View.VISIBLE);
        tv_address.setVisibility(View.VISIBLE);
        tv_address.setText("我的申请");
        iv_contacts.setVisibility(View.GONE);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
