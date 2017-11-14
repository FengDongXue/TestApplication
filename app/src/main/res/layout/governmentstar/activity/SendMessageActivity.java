package com.lanwei.governmentstar.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;

/**
 * Created by YS on 2017/3/26.
 */

public class SendMessageActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cantacts_details);

        TextView title = (TextView) findViewById(R.id.tv_address);

        ImageView back = (ImageView) findViewById(R.id.back);
        ImageView icon = (ImageView) findViewById(R.id.iv_contacts);
        back.setVisibility(View.VISIBLE);
        icon.setVisibility(View.GONE);
        title.setText("详情资料");
        back.setVisibility(View.VISIBLE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
