package com.lanwei.governmentstar.activity.zyx;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术部3 on 2017/4/26.
 */

public class ShareDataActivity extends AppCompatActivity implements View.OnClickListener{

    //我的申请
    private TextView myapply;
    //返回键
    private ImageView back,iv_contacts;
    //有条件共享   无条件共享
    private Button btn_head_a, btn_head_b;
    private LinearLayout conditionshare_layout;
    private ArrayList<Fragment> fragments;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.sharedata_layout);

        initview();
//        initfragemnt();
    }

    private void initview() {
        myapply= (TextView) findViewById(R.id.myapply);
        back= (ImageView) findViewById(R.id.back);
        iv_contacts= (ImageView) findViewById(R.id.iv_contacts);
        btn_head_a= (Button) findViewById(R.id.btn_head_a);
        btn_head_b= (Button) findViewById(R.id.btn_head_b);
        btn_head_a.setSelected(true);
        conditionshare_layout= (LinearLayout) findViewById(R.id.conditionshare_layout);

        back.setVisibility(View.VISIBLE);
        myapply.setVisibility(View.VISIBLE);
        conditionshare_layout.setVisibility(View.VISIBLE);
        iv_contacts.setVisibility(View.GONE);
        back.setOnClickListener(this);
        myapply.setOnClickListener(this);
        btn_head_a.setOnClickListener(this);
        btn_head_b.setOnClickListener(this);

        switchfragment(new NoConditionFragemnt());
    }

    private void initfragemnt() {
        fragments=new ArrayList<>();
        NoConditionFragemnt nocondition=new NoConditionFragemnt();
        YesConditionFragemnt yescondition=new YesConditionFragemnt();
        fragments.add(nocondition);
        fragments.add(yescondition);
    }

    public void switchfragment(Fragment f){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction trans=manager.beginTransaction();
        trans.replace(R.id.condition_relplace,f);
        trans.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_head_a:
                if (!btn_head_a.isSelected()) {
                    btn_head_a.setSelected(true);
                    btn_head_b.setSelected(false);
                }
                switchfragment(new NoConditionFragemnt());
                break;
            case R.id.btn_head_b:
                if (!btn_head_b.isSelected()) {
                    btn_head_a.setSelected(false);
                    btn_head_b.setSelected(true);
                }
                switchfragment(new YesConditionFragemnt());
                break;
        }
    }
}
