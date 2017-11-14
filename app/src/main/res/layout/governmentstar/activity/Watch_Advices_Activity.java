package com.lanwei.governmentstar.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.Data_Many;
import com.lanwei.governmentstar.bean.Item_Collection;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Return_Many;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by 蓝威科技-技术开发1 on 2017/4/21.
 */

public class Watch_Advices_Activity extends BaseActivity implements View.OnClickListener{


    private ImageView back;
    private GovernmentApi api;
    private RecyclerView rv;
    private Adapter_Collection adapter_collection;
    private LinearLayout condition;
//    private Return_Many return_many =new Return_Many();
    private ArrayList<Data_Many> datas;
    private TextView title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advice_chengban);


        // 版本大于5.0系统，支持沉浸式，隐藏系统状态栏，并适配我们的背景颜色，参照郭霖博客沉浸式效果
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }else {
//            condition=(LinearLayout) findViewById(R.id.condition);
//            condition.setVisibility(View.GONE);
//        }

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }


        back=(ImageView) findViewById(R.id.back);
        rv=(RecyclerView) findViewById(R.id.rv);
        title=(TextView) findViewById(R.id.title);
        back.setOnClickListener(this);
        adapter_collection=new Adapter_Collection();
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rv.setLayoutManager(new LinearLayoutManager(Watch_Advices_Activity.this));

        api= HttpClient.getInstance().getGovernmentApi();

        if(getIntent().getStringExtra("flowStatus").equals("5")){
            title.setText("承办意见");
            title.setVisibility(View.VISIBLE);
        }else if(getIntent().getStringExtra("flowStatus").equals("6")){
            title.setText("办理意见");
            title.setVisibility(View.VISIBLE);
        }else if(getIntent().getStringExtra("flowStatus").equals("7")){
            title.setText("协办意见");
            title.setVisibility(View.VISIBLE);
        }else if(getIntent().getStringExtra("flowStatus").equals("9")){
            title.setText("转发意见");
            title.setVisibility(View.VISIBLE);
        }

        Call<Return_Many> call= api.watch_advices(getIntent().getStringExtra("opId"),getIntent().getStringExtra("flowStatus"));

        call.enqueue(new Callback<Return_Many>() {
            @Override
            public void onResponse(Call<Return_Many> call, Response<Return_Many> response) {

                if(response.body().getData()!=null && !response.body().getData().equals("")){

//                    return_many=response.body();
                    datas=response.body().getData();
                    for(int i=0;i<datas.size();i++){
                        if(datas.get(i).getOpCreateTime().equals("") || datas.get(i).getOpCreateTime()==null){
                            datas.remove(i);
                            i--;
                        }
                    }
                    rv.setAdapter(adapter_collection);
                }else{
                    rv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Return_Many> call, Throwable t) {

                Toast.makeText(Watch_Advices_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();

            }
        });


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.back:
                finish();
                break;

        }
    }

    class Adapter_Collection extends RecyclerView.Adapter<Adapter_Collection.MyViewHolder> {


        public Adapter_Collection() {}


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = getLayoutInflater().inflate(R.layout.item_chengban,parent,false);

            return new MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {


            holder.content.setText(datas.get(position).getOpOpinion());

            if(getIntent().getStringExtra("flowStatus").equals("5")){
                holder.name.setText("承办人："+datas.get(position).getOpCreateName());
                holder.time.setText("承办时间："+datas.get(position).getOpCreateTime());

            }else if(getIntent().getStringExtra("flowStatus").equals("6")){
                holder.name.setText("办理人："+datas.get(position).getOpCreateName());
                holder.time.setText("办理时间："+datas.get(position).getOpCreateTime());
            }else if(getIntent().getStringExtra("flowStatus").equals("7")){
                holder.name.setText("协办人："+datas.get(position).getOpCreateName());
                holder.time.setText("协办时间："+datas.get(position).getOpCreateTime());
            }else if(getIntent().getStringExtra("flowStatus").equals("9")){
                holder.name.setText("转发人："+datas.get(position).getOpCreateName());
                holder.time.setText("转发时间："+datas.get(position).getOpCreateTime());
            }

            if(holder.content.getText().equals("")){
                holder.linear.setVisibility(View.GONE);
            }

            if(position==datas.size()-1){

                holder.line.setVisibility(View.GONE);
            }else{

                holder.line.setVisibility(View.VISIBLE);
            }


        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView content;
            private TextView name;
            private TextView time;
            private LinearLayout linear;
            private View line;

            public MyViewHolder(View itemView) {

                super(itemView);
                content = (TextView) itemView.findViewById(R.id.content);
                name = (TextView) itemView.findViewById(R.id.name);
                time= (TextView) itemView.findViewById(R.id.time);
                line=  itemView.findViewById(R.id.line);
                linear= (LinearLayout) itemView.findViewById(R.id.linear);
            }
        }
    }
}
