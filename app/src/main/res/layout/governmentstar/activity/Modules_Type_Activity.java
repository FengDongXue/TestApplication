package com.lanwei.governmentstar.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.gwxf.DocuHanddown_Activity;
import com.lanwei.governmentstar.activity.lll.SelectStateActivity;
import com.lanwei.governmentstar.bean.Condition_Shift;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.JsonUtils;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.StatusBarUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/7/27.
 */

public class Modules_Type_Activity extends BaseActivity implements View.OnClickListener{


    private RecyclerView recycler_view;
    private Adapter_Addtion adapter_addtion;
    private ArrayList<String> list_key =new ArrayList<>();
    private ImageView back;
    private Map<String , Object> map_list = new HashMap();
    private Logging_Success bean;
    private TextView title;
    Map<String, String> outMap= new HashMap();
    private GovernmentApi api;
    private Call<JsonObject> call;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modules);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        back = (ImageView) findViewById(R.id.back);
        title = (TextView) findViewById(R.id.title);
        String defString = PreferencesManager.getInstance(Modules_Type_Activity.this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);

        api = HttpClient.getInstance().getGovernmentApi();

        if(getIntent().getStringExtra("type").equals("1")){
            call=api.modules_choose();
        }else if(getIntent().getStringExtra("type").equals("2") ){
            call=api.department_from(bean.getData().getOpId());
            title.setText("选择来文单位");
        }else if(getIntent().getStringExtra("type").equals("3") ){
            call=api.gwcyDw(bean.getData().getOpId());
            title.setText("选择来文单位");
        }else if(getIntent().getStringExtra("type").equals("4")){
            call=api.gwcyDw(bean.getData().getOpId());
            title.setText("选择类别");
        }

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JSONObject jsonObj =new JSONObject();
                if(getIntent().getStringExtra("type").equals("1")){
                    try {
                        jsonObj = new JSONObject(response.body().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    map_list = JsonUtils.getMapForJson(response.body().toString());
                }else if(getIntent().getStringExtra("type").equals("2")){
                    try {
                        jsonObj = new JSONObject(response.body().get("data").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    map_list = JsonUtils.getMapForJson(response.body().get("data").toString());

                }else if(getIntent().getStringExtra("type").equals("3")){

                    try {
                        jsonObj = new JSONObject(response.body().get("lwdw").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }else if(getIntent().getStringExtra("type").equals("4")){
                    try {
                        jsonObj = new JSONObject(response.body().get("lwfl").toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Iterator<String> nameItr = jsonObj.keys();
                String name;
                outMap = new HashMap<String, String>();
                while (nameItr.hasNext()) {
                    name = nameItr.next();
                    try {
                        outMap.put(name, jsonObj.getString(name));
                        list_key.add(name);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                Log.e("modules",list_key.toString());

                adapter_addtion =new Adapter_Addtion();
                // 为RecyclerView设置默认动画和线性布局管理器
                recycler_view.setItemAnimator(new DefaultItemAnimator());
                //设置线性布局
                recycler_view.setLayoutManager(new LinearLayoutManager(Modules_Type_Activity.this));

                recycler_view.setAdapter(adapter_addtion);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(Modules_Type_Activity.this, "网络连接有误", Toast.LENGTH_SHORT).show();
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

            view = getLayoutInflater().inflate(R.layout.textview, parent, false);

            return new Adapter_Addtion.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Addtion.MyViewHolder holder, final int position) {


            if(position==outMap.size()-1){
                holder.line.setVisibility(View.INVISIBLE);
            }else{
                holder.line.setVisibility(View.VISIBLE);
            }

            if(getIntent().getStringExtra("module_name").equals(outMap.get(list_key.get(position)).toString())){
                holder.module.setTextColor(getResources().getColor(R.color.color_00a7e4));
            }else{
                holder.module.setTextColor(getResources().getColor(R.color.color_23));
            }

            holder.module.setText(outMap.get(list_key.get(position)).toString());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent();
                    intent.putExtra("module",outMap.get(list_key.get(position)).toString());
                    intent.putExtra("module_id",list_key.get(position));
                    setResult(004,intent);
                    finish();
                }
            });

        }

        @Override
        public int getItemCount() {
            return outMap.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView module;
            View line;

            public MyViewHolder(View itemView) {

                super(itemView);
                module = (TextView) itemView.findViewById(R.id.module);
                line =  itemView.findViewById(R.id.line);

            }
        }
    }
}
