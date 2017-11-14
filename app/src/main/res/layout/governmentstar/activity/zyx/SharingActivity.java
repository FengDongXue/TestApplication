package com.lanwei.governmentstar.activity.zyx;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.zhy.autolayout.AutoLayoutActivity;

/**
 * Created by 蓝威科技—技术部2 on 2017/3/31.
 */

public class SharingActivity extends BaseActivity implements View.OnClickListener {

    private TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
        setContentView(R.layout.activity_sharing);

        title = (TextView) findViewById(R.id.tv_address);
        ImageView back = (ImageView) findViewById(R.id.back);



        title.setText("共享说明");
        back.setVisibility(View.VISIBLE);

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
