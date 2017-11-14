package com.lanwei.governmentstar.activity.spsq;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.Process2_Activity;
import com.lanwei.governmentstar.activity.spsq.adapter.FlowAdapter;
import com.lanwei.governmentstar.bean.Return_Proceed;
import com.lanwei.governmentstar.bean.SpsqFlow;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lanwei.governmentstar.R.id.number_document;

/**
 * Created by Administrator on 2017/8/31/031.
 */

public class SpsqFlowActivity extends AutoLayoutActivity implements View.OnClickListener {

    @InjectView(R.id.back)
    ImageView back;
    @InjectView(R.id.spsq_id)
    TextView spsqId;
    @InjectView(R.id.number_spsq)
    LinearLayout numberSpsq;
    @InjectView(R.id.rv_flow)
    RecyclerView rvFlow;
    private GovernmentApi api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 判断系统SDK版本，看看是否支持沉浸式
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
        }
        setContentView(R.layout.spsqflow);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        String opId = intent.getStringExtra("opId");
        back.setOnClickListener(this);

        // 为RecyclerView设置默认动画和线性布局管理器
        rvFlow.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rvFlow.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rvFlow.setAdapter(new FlowAdapter());
        getdata(userId, opId);
    }

    private void getdata(String opId, String userId) {
        api = HttpClient.getInstance().getGovernmentApi();

        Call<SpsqFlow> call = api.spsqFlow(opId, userId);

        call.enqueue(new Callback<SpsqFlow>() {
            @Override
            public void onResponse(Call<SpsqFlow> call, Response<SpsqFlow> response) {

//                if (response.body().getData() != null) {
//                    rv = (RecyclerView) findViewById(R.id.lv2);
//                    // 为RecyclerView设置默认动画和线性布局管理器
//                    rv.setItemAnimator(new DefaultItemAnimator());
//                    //设置线性布局
////                    rv.setLayoutManager(new LinearLayoutManager(Process2_Activity.this));
//
//                    //public Datas_Item(String flowTitle, String flowContent, String flowImgUrl, String flowTime, String flowStatus, String opCreateTime) {
//
////                    adaper = new Process2_Activity.Adapter_Process();
////                    rv.setAdapter(adaper);
//                }

            }

            @Override
            public void onFailure(Call<SpsqFlow> call, Throwable t) {

//                Toast.makeText(Process2_Activity.this, "网 络连接有错误", Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
