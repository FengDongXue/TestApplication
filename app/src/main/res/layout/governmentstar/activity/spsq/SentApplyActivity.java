package com.lanwei.governmentstar.activity.spsq;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/8/1/001.
 */

public class SentApplyActivity extends AutoLayoutActivity implements View.OnClickListener {
    @InjectView(R.id.back)
    ImageView back;
    @InjectView(R.id.iv_contacts)
    CircleImageView ivContacts;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.apply_seal)
    TextView applySeal;
    @InjectView(R.id.apply_project)
    TextView applyProject;
    @InjectView(R.id.apply_capital)
    TextView applyCapital;
    @InjectView(R.id.apply_qj)
    TextView applyQj;
    @InjectView(R.id.apply_person)
    TextView applyPerson;
    @InjectView(R.id.apply_out)
    TextView applyOut;
    @InjectView(R.id.apply_things)
    TextView applyThings;
    @InjectView(R.id.apply_other)
    TextView applyOther;

    private int tab = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.activity_sentapply);
        ButterKnife.inject(this);

        initview();
    }

    private void initview() {
        tvAddress.setVisibility(View.VISIBLE);
        tvAddress.setText("发起申请");
        back.setVisibility(View.VISIBLE);
        ivContacts.setVisibility(View.GONE);
        back.setOnClickListener(this);
        applySeal.setOnClickListener(this);
        applyProject.setOnClickListener(this);
        applyCapital.setOnClickListener(this);
        applyQj.setOnClickListener(this);
        applyPerson.setOnClickListener(this);
        applyOut.setOnClickListener(this);
        applyThings.setOnClickListener(this);
        applyOther.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.apply_seal:
                Intent intent = new Intent(this, EnterSealApplyActivity.class);
                tab = 0;
                startActivity(intent);
                break;
            case R.id.apply_project:
//                Intent intent1 = new Intent(this, EnterProjectApplyActivity.class);
//                startActivity(intent1);
                Toast.makeText(SentApplyActivity.this,"暂未开放",Toast.LENGTH_SHORT).show();
                break;
            case R.id.apply_capital:
//                Intent intent2 = new Intent(this, EnterCapitalActivity.class);
//                startActivity(intent2);
                Toast.makeText(SentApplyActivity.this,"暂未开放",Toast.LENGTH_SHORT).show();
                break;
            case R.id.apply_qj:
                Intent intent3 = new Intent(this, EnterQjApplyActivity.class);
                startActivity(intent3);
                break;
            case R.id.apply_person:
//                Intent intent4 = new Intent(this, EnterPersonApplyActivity.class);
//                startActivity(intent4);
                Toast.makeText(SentApplyActivity.this,"暂未开放",Toast.LENGTH_SHORT).show();
                break;
            case R.id.apply_out:
                Intent intent5 = new Intent(this, EnterOutApplyActivity.class);
                startActivity(intent5);
                break;
            case R.id.apply_things:
                Intent intent6 = new Intent(this, EnterThingsApplyActivity.class);
                startActivity(intent6);
                break;
            case R.id.apply_other:
                Toast.makeText(SentApplyActivity.this,"暂未开放",Toast.LENGTH_SHORT).show();
//                Intent intent7 = new Intent(this, EnterOtherApplyActivity.class);
//                startActivity(intent7);
                break;
        }
    }
}
