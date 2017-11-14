package com.lanwei.governmentstar.activity.dzgd;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.icarus.entity.Image;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.Process2_Activity;
import com.lanwei.governmentstar.activity.zyx.HomeActivity;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Return_Nizhidan;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/9/4.
 */

public class Classify_Nizhidan_Activity extends BaseActivity implements View.OnClickListener{

    private Logging_Success bean;
    private GovernmentApi api;
    private ImageView back;
    private RecyclerView flow_recyclerview;
    private Adapter_Addtion2 adapter_addtion2;
    private TextView doctitle;
    private TextView zh;
    private TextView num;
    private TextView type;
    private TextView theme;
    private LinearLayout root;
    private LinearLayout share_type;
    private LinearLayout share_instruction;
    private LinearLayout flow_consult;
    private Call<Return_Nizhidan> call;
    private Map<String ,String> gxState;
    private ArrayList<Bean> list =new ArrayList<>();
    private PopupWindow popupWindow;
    private String content;
    private String type_share = "";
    private String type_share_id = "";

    // 有关流程
    private String docQcrName = "";
    private String docQcrTime = "";
    private String docShName = "";
    private String docShTime = "";
    private String docSyName = "";
    private String docSyTime = "";
    private String docJdName = "";
    private String docJdTime = "";
    private String docQfName = "";
    private String docQfTime = "";
    private String docHqName = "";
    private String docHfName = "";
    private String docHfTime = "";
    private String docGdName = "";
    private String docGdTime = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_nizhidan);
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        initweights();

        type_share = getIntent().getStringExtra("gxState");
        call = api.nizhi_chulidan(bean.getData().getOpId(),getIntent().getStringExtra("opId"));
        call.enqueue(new Callback<Return_Nizhidan>() {
            @Override
            public void onResponse(Call<Return_Nizhidan> call, Response<Return_Nizhidan> response) {

                if(response.body().getData()!= null &&  !response.body().getData().equals("")){
                    gxState = response.body().getData().getGxState();
                    doctitle.setText(response.body().getData().getDocTitle());
                    zh.setText(response.body().getData().getGwzh());
                    num.setText(response.body().getData().getDocCode());
                    type.setText(response.body().getData().getDocGzlx());
                    theme.setText(response.body().getData().getDocGwzt());
                    content=response.body().getData().getGxSm();

                    for (Map.Entry<String, String> entry : gxState.entrySet()) {

                        if(entry.getValue().equals(type_share)){
                            type_share_id = entry.getKey();
                        }
                        list.add(new Bean(entry.getValue(),entry.getKey()));
                        System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                    }

                    docQcrName = response.body().getData().getDocQcrName();
                    docQcrTime = response.body().getData().getDocQcrTime();
                    docShName = response.body().getData().getDocShName();
                    docShTime = response.body().getData().getDocShTime();
                    docSyName = response.body().getData().getDocSyName();
                    docSyTime = response.body().getData().getDocSyTime();
                    docJdName = response.body().getData().getDocJdName();
                    docJdTime = response.body().getData().getDocJdTime();
                    docQfName = response.body().getData().getDocQfName();
                    docQfTime = response.body().getData().getDocQfTime();
                    docHqName = response.body().getData().getDocHqName();
                    docHfName = response.body().getData().getDocHfName();
                    docHfTime = response.body().getData().getDocHfTime();
                    docGdName = response.body().getData().getDocGdName();
                    docGdTime = response.body().getData().getDocGdTime();

                    adapter_addtion2 =new Adapter_Addtion2();
                    flow_recyclerview.setAdapter(adapter_addtion2);

                }

            }

            @Override
            public void onFailure(Call<Return_Nizhidan> call, Throwable t) {
            Toast.makeText(Classify_Nizhidan_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();

            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {

            Intent intent2 =new Intent();
            intent2.putExtra("type_share",type_share);
            setResult(510,intent2);
            finish();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.back:

                Intent intent2 =new Intent();
                intent2.putExtra("type_share",type_share);
                setResult(510,intent2);
                finish();

            break;
            case R.id.flow_consult:

                Intent intent= new Intent(this,Process2_Activity.class);
                intent.putExtra("opId", getIntent().getStringExtra("opId"));
                startActivity(intent);

            break;
            case R.id.share_instruction:

                Intent intent1= new Intent(this,Share_Instruction_Activity.class);
                intent1.putExtra("content",content);
                startActivity(intent1);


            break;
            case R.id.share_type:

                // 弹出popupwindow前，调暗屏幕的透明度
                WindowManager.LayoutParams lp2 = getWindow().getAttributes();
                lp2.alpha = (float) 0.8;
                getWindow().setAttributes(lp2);

                View view1 = getLayoutInflater().inflate(R.layout.popup_three_share, null ,false);
                popupWindow = new PopupWindow(view1, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                // 点击屏幕之外的区域可否让popupwindow消失
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new PoponDismissListener());
                LinearLayout three_items = (LinearLayout) view1.findViewById(R.id.three_items);

                for(int i=0 ;i<list.size();i++){
                    TextView textView =new TextView(this);
                    View view =new View(this);
                    textView.setText(list.get(i).getType_share());
                    textView.setTextSize(16);
                    LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT , 0, 1.0f);
                    textView.setGravity(Gravity.CENTER);
                    if(type_share_id.equals(list.get(i).getType_share_id())){
                        textView.setTextColor(getResources().getColor(R.color.blue));
                    }else{
                        textView.setTextColor(getResources().getColor(R.color.color_23));
                    }
                    textView.setLayoutParams(lparams);
                    textView.setGravity(Gravity.CENTER);

                    LinearLayout.LayoutParams lparams2 = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT , 1);
                    view.setBackgroundColor(getResources().getColor(R.color.color_999));
                    view.setLayoutParams(lparams2);

                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {

                            type_share = ((TextView) v).getText().toString().trim();
                            for(int i=0 ;i<list.size();i++){
                                if(type_share.equals(list.get(i).getType_share())){
                                    type_share_id=list.get(i).getType_share_id();
                                }
                            }

                            Call<JsonObject> call2 =api.nizhi_share(getIntent().getStringExtra("opId"),bean.getData().getOpId(),type_share_id);
                            call2.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                    if(response.body() != null && !response.body().equals("")){

                                        if(response.body().get("result").getAsBoolean()){

                                           Toast.makeText(Classify_Nizhidan_Activity.this,"更改成功",Toast.LENGTH_SHORT).show();

                                        }else{

                                            Toast.makeText(Classify_Nizhidan_Activity.this, "更改失败！", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                     Toast.makeText(Classify_Nizhidan_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();

                                }
                            });

                            Log.e("type_share" ,type_share);
                            Log.e("type_share_id" ,type_share_id);
                            if(popupWindow != null){
                                popupWindow.dismiss();
                            }
                        }
                    });
                    three_items.addView(textView);
                    if(i != list.size()-1){
                        three_items.addView(view);
                    }

                }

                // 设置popupwindow的显示位置
                popupWindow.showAtLocation(root, Gravity.LEFT | Gravity.BOTTOM , 90, 90);

            break;
        }

    }


    // popupwindow消失后触发的方法，将屏幕透明度调为1
    class PoponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            WindowManager.LayoutParams p = getWindow().getAttributes();
            p.alpha = (float) 1;
            getWindow().setAttributes(p);
        }
    }

    void initweights(){

        String defString = PreferencesManager.getInstance(this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);
        api= HttpClient.getInstance().getGovernmentApi();


        back = (ImageView) findViewById(R.id.back);
        flow_recyclerview = (RecyclerView) findViewById(R.id.flow_recyclerview);

        // 为RecyclerView设置默认动画和线性布局管理器
        flow_recyclerview.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        flow_recyclerview.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        doctitle = (TextView) findViewById(R.id.doctitle);
        zh = (TextView) findViewById(R.id.zh);
        num = (TextView) findViewById(R.id.num);
        type = (TextView) findViewById(R.id.type);
        theme = (TextView) findViewById(R.id.theme);
        root = (LinearLayout) findViewById(R.id.root);
        share_type = (LinearLayout) findViewById(R.id.share_type);
        share_instruction = (LinearLayout) findViewById(R.id.share_instruction);
        flow_consult = (LinearLayout) findViewById(R.id.flow_consult);

        back.setOnClickListener(this);
        flow_consult.setOnClickListener(this);
        share_instruction.setOnClickListener(this);
        share_type.setOnClickListener(this);
    }


    class Adapter_Addtion2 extends RecyclerView.Adapter<Adapter_Addtion2.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion2() {

        }

        @Override
        public Adapter_Addtion2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.item_layout_nizhidan, parent, false);

            return new Adapter_Addtion2.MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final Adapter_Addtion2.MyViewHolder holder, final int position) {


            if(position==0){
                holder.line_up.setVisibility(View.INVISIBLE);
                holder.line_bottom.setVisibility(View.VISIBLE);
                holder.name_rank.setText("起草人：");
                holder.time_rank.setText("起草时间：");
                holder.name.setText(docQcrName);
                holder.bottom.setVisibility(View.VISIBLE);
                holder.time.setText(docQcrTime);
                holder.decoration.setVisibility(View.VISIBLE);
            }

            if(position==1){
                holder.line_up.setVisibility(View.VISIBLE);
                holder.line_bottom.setVisibility(View.VISIBLE);
                holder.name_rank.setText("审核人：");
                holder.time_rank.setText("审核时间：");
                holder.name.setText(docShName);
                holder.bottom.setVisibility(View.VISIBLE);
                holder.time.setText(docShTime);
                holder.decoration.setVisibility(View.VISIBLE);
            }

            if(position==2){
                holder.line_up.setVisibility(View.VISIBLE);
                holder.line_bottom.setVisibility(View.VISIBLE);
                holder.name_rank.setText("审阅人：");
                holder.time_rank.setText("审阅时间：");
                holder.name.setText(docSyName);
                holder.bottom.setVisibility(View.VISIBLE);
                holder.time.setText(docSyTime);
                holder.decoration.setVisibility(View.VISIBLE);
            }

            if(position==3){
                holder.line_up.setVisibility(View.VISIBLE);
                holder.line_bottom.setVisibility(View.VISIBLE);
                holder.name_rank.setText("校对人：");
                holder.time_rank.setText("校对时间：");
                holder.name.setText(docJdName);
                holder.bottom.setVisibility(View.VISIBLE);
                holder.time.setText(docJdTime);
                holder.decoration.setVisibility(View.VISIBLE);
            }

            if(position==4){
                holder.line_up.setVisibility(View.VISIBLE);
                holder.line_bottom.setVisibility(View.VISIBLE);
                holder.name_rank.setText("签发人：");
                holder.time_rank.setText("签发时间：");
                holder.name.setText(docQfName);
                holder.bottom.setVisibility(View.VISIBLE);
                holder.time.setText(docQfTime);
                holder.decoration.setVisibility(View.VISIBLE);
            }

            if(position==5){
                holder.line_up.setVisibility(View.VISIBLE);
                holder.line_bottom.setVisibility(View.VISIBLE);
                holder.name_rank.setText("会签人：");
                holder.name.setText(docHqName);
                holder.time.setText("notnull");
                holder.bottom.setVisibility(View.GONE);
                holder.decoration.setVisibility(View.VISIBLE);
            }

            if(position==6){
                holder.line_up.setVisibility(View.VISIBLE);
                holder.line_bottom.setVisibility(View.VISIBLE);
                holder.name_rank.setText("核发人：");
                holder.time_rank.setText("核发时间：");
                holder.name.setText(docHfName);
                holder.bottom.setVisibility(View.VISIBLE);
                holder.time.setText(docHfTime);
                holder.decoration.setVisibility(View.INVISIBLE);
            }
           if(position==7){
                holder.line_up.setVisibility(View.VISIBLE);
                holder.line_bottom.setVisibility(View.INVISIBLE);
                holder.name_rank.setText("归档人：");
                holder.time_rank.setText("归档时间：");
                holder.name.setText(docGdName);
                holder.bottom.setVisibility(View.VISIBLE);
                holder.time.setText(docGdTime);
                holder.decoration.setVisibility(View.INVISIBLE);
            }

            if(holder.time.getText().toString().trim().equals("")){
               holder.name.setVisibility(View.INVISIBLE);
            }else{
                holder.name.setVisibility(View.VISIBLE);
            }


        }

        @Override
        public int getItemCount() {

//            if( list_gx== null || list_gx.size()==0){
//                return 0;
//            }
//            return list_gx.size();
            return 8;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView name_rank;
            TextView name;
            TextView time_rank;
            TextView time;
            View line_up;
            View line_bottom;
            View decoration;
            LinearLayout bottom;

            public MyViewHolder(View itemView) {

                super(itemView);
                name_rank = (TextView) view.findViewById(R.id.name_rank);
                name = (TextView) view.findViewById(R.id.name);
                time_rank = (TextView) view.findViewById(R.id.time_rank);
                time = (TextView) view.findViewById(R.id.time);
                line_up =  view.findViewById(R.id.line_up);
                line_bottom =  view.findViewById(R.id.line_bottom);
                decoration =  view.findViewById(R.id.decoration);
                bottom = (LinearLayout) view.findViewById(R.id.bottom);
            }
        }
    }


    class Bean{

        private String type_share;
        private String type_share_id;


        public Bean(String type_share, String type_share_id) {
            this.type_share = type_share;
            this.type_share_id = type_share_id;
        }

        public String getType_share() {
            return type_share;
        }

        public void setType_share(String type_share) {
            this.type_share = type_share;
        }

        public String getType_share_id() {
            return type_share_id;
        }

        public void setType_share_id(String type_share_id) {
            this.type_share_id = type_share_id;
        }
    }




}
