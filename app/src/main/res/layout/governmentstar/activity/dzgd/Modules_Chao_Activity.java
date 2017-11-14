package com.lanwei.governmentstar.activity.dzgd;

import android.content.Intent;
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
import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.Modules_Type_Activity;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Return_Nizhi;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.StatusBarUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/7/27.
 */

public class Modules_Chao_Activity extends BaseActivity implements View.OnClickListener{


    private RecyclerView recycler_view;
    private Adapter_Addtion adapter_addtion;
    private ImageView back;
    private Logging_Success bean;
    private TextView title;
    private GovernmentApi api;
    private Call<Return_Nizhi> call;
    private Map<String, String> deptMap;
    private ArrayList<IsChoose> list_dept =new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modules);
//         activity_select_department

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        back = (ImageView) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
        String defString = PreferencesManager.getInstance(Modules_Chao_Activity.this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);

        if(getIntent().getStringExtra("type").equals("zhu")){
            title.setText("主送机关");
        }else{
            title.setText("抄送机关");
        }

        api = HttpClient.getInstance().getGovernmentApi();

        call =api.nizhi_comin(bean.getData().getOpId(),getIntent().getStringExtra("opId"));

        call.enqueue(new Callback<Return_Nizhi>() {
            @Override
            public void onResponse(Call<Return_Nizhi> call, Response<Return_Nizhi> response) {
                if(response.body() != null){

//                    deptMap = response.body().getDeptMap();
//
//                    for (Map.Entry<String, String> entry : deptMap.entrySet()) {
//
//                        list_dept.add(new IsChoose(entry.getKey(),false));
//                        System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//
//                    }

                    adapter_addtion =new Adapter_Addtion();
                    // 为RecyclerView设置默认动画和线性布局管理器
                    recycler_view.setItemAnimator(new DefaultItemAnimator());
                    //设置线性布局
                    recycler_view.setLayoutManager(new LinearLayoutManager(Modules_Chao_Activity.this));

                    recycler_view.setAdapter(adapter_addtion);
                }
            }

            @Override
            public void onFailure(Call<Return_Nizhi> call, Throwable t) {

                Toast.makeText(Modules_Chao_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();

            }
        });


        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.back:
                finish();

                break;

        }

    }

    // 附件的adapter
    class Adapter_Addtion extends RecyclerView.Adapter<Adapter_Addtion.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion() {

        }

        @Override
        public Adapter_Addtion.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.item_in, parent, false);

            return new Adapter_Addtion.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Addtion.MyViewHolder holder, final int position) {


            holder.name.setText(deptMap.get(list_dept.get(position).getNo()));
            if(list_dept.get(position).getChoose()){
                holder.isChoose.setImageDrawable(getResources().getDrawable(R.drawable.icon_x));

            }else{
                holder.isChoose.setImageDrawable(getResources().getDrawable(R.drawable.icon_w));
            }
            holder.all_layoutc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(list_dept.get(position).getChoose()){
                        list_dept.get(position).setChoose(false);
                        holder.isChoose.setImageDrawable(getResources().getDrawable(R.drawable.icon_w));

                    }else {
                        list_dept.get(position).setChoose(true);
                        holder.isChoose.setImageDrawable(getResources().getDrawable(R.drawable.icon_x));

                    }

                }
            });



        }

        @Override
        public int getItemCount() {

            if(list_dept == null || list_dept.size()==0){
                return 0;
            }
              return list_dept.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView isChoose;
            LinearLayout all_layoutc;
            TextView name;

            public MyViewHolder(View itemView) {

                super(itemView);
                isChoose = (ImageView) view.findViewById(R.id.isChoose);
                name = (TextView) view.findViewById(R.id.name);
                all_layoutc = (LinearLayout) view.findViewById(R.id.all_layoutc);
            }
        }
    }


    class IsChoose{


        private String no;
        private Boolean isChoose;


        public IsChoose(String no, Boolean isChoose) {
            this.no = no;
            this.isChoose = isChoose;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public Boolean getChoose() {
            return isChoose;
        }

        public void setChoose(Boolean choose) {
            isChoose = choose;
        }
    }
}
