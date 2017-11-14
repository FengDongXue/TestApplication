package com.lanwei.governmentstar.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/1.
 */

public class Preview_File_Activity extends BaseActivity implements View.OnClickListener{

    private ImageView back;
    private TextView publish;
    private TextView title;
    private TextView type;
    private TextView name;
    private TextView time;
    private RecyclerView rv;
    private Adapter_Addtion adapter;
    private ArrayList<String> data = null;
    private WebView web;
    private LinearLayout condition;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_file);

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

        web=(WebView) findViewById(R.id.wv);
        back=(ImageView)findViewById(R.id.back);
        publish=(TextView) findViewById(R.id.publish);
        type=(TextView) findViewById(R.id.publish);
        name=(TextView) findViewById(R.id.publish);
        time=(TextView) findViewById(R.id.publish);
        rv=(RecyclerView) findViewById(R.id.rv);
        title=(TextView) findViewById(R.id.title);
        title.setText("预览发布");

        back.setOnClickListener(this);
        publish.setOnClickListener(this);

        data=new ArrayList<>();
        data.add("20152352.jpg");
        data.add("20152352.jpg");
        data.add("20152352.jpg");
//        adapter=new DetailsAdapter();

        // 为RecyclerView设置默认动画和线性布局管理器
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter=new Adapter_Addtion(data);

        rv.setAdapter(adapter);


    }

    class Adapter_Addtion extends RecyclerView.Adapter<Adapter_Addtion.MyViewHolder> {

        private View view = null;
        private ArrayList<String> datas = null;

        public Adapter_Addtion(ArrayList datas) {
            this.datas = datas;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.addtion_layout, null);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.addtion.setText("附件"+(position+1)+" : ");

            holder.addtional.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO 跳去附件详情界面

                    Intent intent=new Intent(Preview_File_Activity.this,Addtion_Details_Activity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView addtion;
            TextView addtional;

            public MyViewHolder(View itemView) {

                super(itemView);
                addtion = (TextView) view.findViewById(R.id.addtion);
                addtional = (TextView) view.findViewById(R.id.addtional);
            }
        }
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.back){

            finish();
        }


        if(v.getId()==R.id.publish){


        }

    }


}
