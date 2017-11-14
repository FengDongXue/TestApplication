package com.lanwei.governmentstar.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.gwnz.opinion.DocumentOpinionActivity;
import com.lanwei.governmentstar.activity.gwnz.opinion.DraftOpinionActivity;
import com.lanwei.governmentstar.bean.Datas_Item;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.ManyPeople;
import com.lanwei.governmentstar.bean.Result_Push;
import com.lanwei.governmentstar.bean.Return_Proceed;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/3.
 */

public class Process2_Activity extends BaseActivity implements View.OnClickListener{

    private RecyclerView rv;
    private ArrayList<Datas_Item> list_datas_items=new ArrayList<>();
    private Adapter_Process adaper;
    private TextView document_id;
    private ImageView back;
    private LinearLayout condition;
    int  index=0;
    Return_Proceed return_proceed;
    String userId;
    private LinearLayout number_document;
    private  GovernmentApi api;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
        setContentView(R.layout.see_process2);
        document_id=(TextView) findViewById(R.id.document_id);

        back=(ImageView) findViewById(R.id.back);
        number_document=(LinearLayout) findViewById(R.id.number_document);
        back.setOnClickListener(this);

            Log.e("dvd v dvd发光发热感人发",""+"额非分的高分v的v");



        // 获取bean;
        String defString = PreferencesManager.getInstance(Process2_Activity.this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        Logging_Success bean=gson.fromJson(defString,Logging_Success.class);

        // 获取opId和userId
        userId=bean.getData().getOpId();
        api= HttpClient.getInstance().getGovernmentApi();

        Log.e("数据", "onCreate: "+getIntent().getStringExtra("opId")+"    "+userId);
        Call<Return_Proceed> call= api.watch_proceed(getIntent().getStringExtra("opId"),userId);

        call.enqueue(new Callback<Return_Proceed>() {
            @Override
            public void onResponse(Call<Return_Proceed> call, Response<Return_Proceed> response) {

                if(response.body().getData() != null){
                    list_datas_items=response.body().getData();
                    return_proceed=response.body();

                    number_document.setVisibility(View.VISIBLE);
                    document_id.setText(response.body().getCode());
                    rv=(RecyclerView) findViewById(R.id.lv2);
                    // 为RecyclerView设置默认动画和线性布局管理器
                    rv.setItemAnimator(new DefaultItemAnimator());
                    //设置线性布局
                    rv.setLayoutManager(new LinearLayoutManager(Process2_Activity.this));


                    adaper=new Adapter_Process();
                    rv.setAdapter(adaper);
                }

            }
            @Override
            public void onFailure(Call<Return_Proceed> call, Throwable t) {

                Toast.makeText(Process2_Activity.this,"网络连接有错误",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.back){
            finish();
        }
    }

    class Adapter_Process extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private View view = null;
        private int type_normal = 000111;
        private int type_many = 111000;

        public Adapter_Process() {
        }

        @Override
        public int getItemViewType(int position) {

            if(list_datas_items.get(position).getFlowStatus().equals("6")){
                return  type_many;
            }
            return type_normal;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            if(viewType==type_many){
                view = getLayoutInflater().inflate(R.layout.items_many,parent,false);
                return new MyViewHolder2(view);
            }else{
                view = getLayoutInflater().inflate(R.layout.item_common_proceed,parent,false);
                return new MyViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            if(holder instanceof MyViewHolder){

                // 初始化控件的状态，由于RecyclerView会重用刚消失的item的布局，会默认消失的item属性，所以需要这一步
                ((MyViewHolder)holder).number.setBackground(getResources().getDrawable(R.drawable.blue01));
                ((MyViewHolder)holder).line_above.setBackgroundColor(getResources().getColor(R.color.blue));
                ((MyViewHolder)holder).line_below.setBackgroundColor(getResources().getColor(R.color.blue));
                ((MyViewHolder)holder).feedback.setVisibility(View.INVISIBLE);
                ((MyViewHolder)holder).add_number2.setVisibility(View.VISIBLE);
                ((MyViewHolder)holder).details.setVisibility(View.VISIBLE);
                ((MyViewHolder)holder).time_finish.setVisibility(View.VISIBLE);
                ((MyViewHolder)holder).feedback.setVisibility(View.INVISIBLE);
                ((MyViewHolder)holder).rebanli.setVisibility(View.GONE);

                ((MyViewHolder)holder).title.setText(list_datas_items.get(position).getFlowTitle());
                ((MyViewHolder)holder).number.setText((position+1)+"");
                ((MyViewHolder)holder).content.setText(list_datas_items.get(position).getFlowContent());
                ((MyViewHolder)holder).time_finish.setText(list_datas_items.get(position).getOpCreateTime());
                ((MyViewHolder)holder).time_during.setText(list_datas_items.get(position).getFlowTime());

                ((MyViewHolder)holder).details.setText(list_datas_items.get(position).getFlowBut());
                if(((MyViewHolder)holder).details.getText().equals("")){
                    ((MyViewHolder)holder).details.setVisibility(View.INVISIBLE);
                }

                // 判定是不是倒数第二个位置，如果是，看看倒数第一是不是多人参与是否有数据，以便设置倒数第二的下线的颜色
                if(position==list_datas_items.size()-2 &&list_datas_items.get(list_datas_items.size()-1).getFlowStatus().equals("6")) {
                    int d=0;
                    for(int m=0;m<list_datas_items.get(list_datas_items.size()-1).getManyPeople().size();m++){

                        if(list_datas_items.get(list_datas_items.size()-1).getManyPeople().get(m).getFlowContent()!=null && !list_datas_items.get(list_datas_items.size()-1).getManyPeople().get(m).getFlowContent().equals("")){
                            d=1;
                            break;
                        }
                    }

                    if(d==0){
                        ((MyViewHolder)holder).line_below.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                }else{
                    // 判定是不是倒数第二个位置，如果是，看看倒数第一是不是单人参与是否有数据，以便设置倒数第二的下线的颜色(因为最后一个是不是6，影响判定的标准)
                    if(position==list_datas_items.size()-2 && (list_datas_items.get(list_datas_items.size()-1).getFlowContent().equals("")|| list_datas_items.get(list_datas_items.size()-1).getFlowContent()==null )){
                        ((MyViewHolder)holder).line_below.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                }

                // 判定imageUrl是不是空，以免发生奔溃
                if(!list_datas_items.get(position).getFlowImgUrl().equals("") && list_datas_items.get(position).getFlowImgUrl()!= null){
                    Picasso.with(Process2_Activity.this).load(list_datas_items.get(position).getFlowImgUrl()).memoryPolicy(MemoryPolicy.NO_CACHE).into(((MyViewHolder)holder).header);
                }

                try {
                    // flowstatus转化为int类型
                    Number number= NumberFormat.getNumberInstance(Locale.FRENCH).parse(list_datas_items.get(position).getFlowStatus());
                    index=number.intValue();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // 设置点击事件
                ((MyViewHolder) holder).details.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if(list_datas_items.get(position).getFlowStatus().equals("6")){
                            Intent intent =new Intent(Process2_Activity.this, DocumentOpinionActivity.class);
                            intent.putExtra("opId",getIntent().getStringExtra("opId"));
                            intent.putExtra("flowStatus",list_datas_items.get(position).getFlowStatus());
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(Process2_Activity.this, DraftOpinionActivity.class);
                            intent.putExtra("opId", getIntent().getStringExtra("opId"));
                            intent.putExtra("flowStatus", list_datas_items.get(position).getFlowStatus());
                            startActivity(intent);
                        }
                    }
                });

                if((list_datas_items.get(position).getFlowContent()==null || list_datas_items.get(position).getFlowContent().equals("")) && position<list_datas_items.size()-1){
                    ((MyViewHolder)holder).add_number2.setVisibility(View.INVISIBLE);
                    ((MyViewHolder)holder).time_finish.setVisibility(View.INVISIBLE);
                    ((MyViewHolder)holder).time_during.setVisibility(View.INVISIBLE);
                    ((MyViewHolder)holder).number.setBackground(getResources().getDrawable(R.drawable.gray));
                    ((MyViewHolder)holder).details.setVisibility(View.INVISIBLE);
                }

                // 如果是最后一个位置，判断内容是否为空，为空的话就不可视控件，并判断是否可催办
                if(position == list_datas_items.size()-1 && (list_datas_items.get(position).getFlowContent() == null || list_datas_items.get(position).getFlowContent().equals(""))){

                    ((MyViewHolder)holder).line_below.setBackgroundColor(getResources().getColor(R.color.gray));
                    ((MyViewHolder)holder).number.setBackground(getResources().getDrawable(R.drawable.gray));
                    ((MyViewHolder)holder).add_number2.setVisibility(View.INVISIBLE);
                    ((MyViewHolder)holder).details.setVisibility(View.INVISIBLE);
                    ((MyViewHolder)holder).time_finish.setVisibility(View.INVISIBLE);
                    ((MyViewHolder)holder).time_during.setVisibility(View.INVISIBLE);
                    ((MyViewHolder)holder).line_above.setBackgroundColor(getResources().getColor(R.color.gray));


                    if(return_proceed.getCb()){
                        ((MyViewHolder)holder).feedback.setVisibility(View.VISIBLE);
                        ((MyViewHolder)holder).feedback.setText("立即催办");
                        ((MyViewHolder)holder).number.setBackground(getResources().getDrawable(R.drawable.gray));

                        ((MyViewHolder)holder).feedback.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // 催办网络请求
                                Call<Result_Push> call_push1= api.pushDrawer(userId,getIntent().getStringExtra("opId"));

                                call_push1.enqueue(new Callback<Result_Push>() {
                                    @Override
                                    public void onResponse(Call<Result_Push> call, Response<Result_Push> response) {

                                        if(response.body().getData()){

                                            // 加载popupwindow的布局
                                            View view4=getLayoutInflater().inflate(R.layout.system_notification,null);
                                            popupWindow=new PopupWindow(view4, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                                            ((MyViewHolder)holder).feedback.setVisibility(View.GONE);
                                            // 初始化popupwindow的点击控件
                                            TextView content=(TextView) view4.findViewById(R.id.content);
                                            content.setText("催办成功");

                                            // 点击屏幕之外的区域可否让popupwindow消失
                                            popupWindow.setFocusable(true);
                                            popupWindow.setBackgroundDrawable(new BitmapDrawable());
                                            popupWindow.setOnDismissListener(new PoponDismissListener());

                                            View rootview4 = LayoutInflater.from(Process2_Activity.this).inflate(R.layout.see_process2, null);
                                            // 设置popupwindow的显示位置
                                            popupWindow.showAtLocation(rootview4, Gravity.CENTER,0,0);

                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    try {
                                                        Thread.sleep(2000);
                                                        back.post(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                popupWindow.dismiss();
                                                            }
                                                        });

                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();


                                        }else{
                                            View view6=getLayoutInflater().inflate(R.layout.system_notification,null);
                                            popupWindow=new PopupWindow(view6, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                                            // 初始化popupwindow的点击控件
                                            TextView content6=(TextView) view6.findViewById(R.id.content);
                                            content6.setText("催办失败");

                                            // 点击屏幕之外的区域可否让popupwindow消失
                                            popupWindow.setFocusable(true);
                                            popupWindow.setBackgroundDrawable(new BitmapDrawable());
                                            popupWindow.setOnDismissListener(new PoponDismissListener());

                                            View rootview6 = LayoutInflater.from(Process2_Activity.this).inflate(R.layout.see_process2, null);
                                            // 设置popupwindow的显示位置
                                            popupWindow.showAtLocation(rootview6, Gravity.CENTER,0,0);

                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    try {
                                                        Thread.sleep(2000);
                                                        back.post(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                popupWindow.dismiss();
                                                            }
                                                        });

                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Result_Push> call, Throwable t) {
                                        Toast.makeText(Process2_Activity.this,"网络连接有错误",Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });

                    }
                }

                // 如果最后，下划线必为灰色
                if(position == list_datas_items.size()-1){
                    ((MyViewHolder)holder).line_below.setBackgroundColor(getResources().getColor(R.color.gray));
                }

            }else if(holder instanceof MyViewHolder2){

                // 如果这个item是 会签类 等多人参与，加载type_many的类型的布局
                ((MyViewHolder2)holder).title.setText(list_datas_items.get(position).getFlowTitle());
                ((MyViewHolder2)holder).number.setText((position+1)+"");

                if(list_datas_items.get(position).getManyPeople().get(0).getFlowContent()!=null && !list_datas_items.get(position).getManyPeople().get(0).getFlowContent().equals("")){

                    ((MyViewHolder2)holder).details.setText(list_datas_items.get(position).getManyPeople().get(0).getFlowBut());
                }

                ((MyViewHolder2)holder).details.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(list_datas_items.get(position).getFlowStatus().equals("6")){
                            Intent intent =new Intent(Process2_Activity.this, DocumentOpinionActivity.class);;
                            intent.putExtra("opId",getIntent().getStringExtra("opId"));
                            intent.putExtra("flowStatus",list_datas_items.get(position).getFlowStatus());
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(Process2_Activity.this, DraftOpinionActivity.class);
                            intent.putExtra("opId", getIntent().getStringExtra("opId"));
                            intent.putExtra("flowStatus", list_datas_items.get(position).getFlowStatus());
                            startActivity(intent);
                        }
                    }
                });

                // 为RecyclerView设置默认动画和线性布局管理器
                ((MyViewHolder2)holder).lv.setItemAnimator(new DefaultItemAnimator());
                //设置线性布局
                ((MyViewHolder2)holder).lv.setLayoutManager(new LinearLayoutManager(Process2_Activity.this));

                ((MyViewHolder2)holder).lv.addItemDecoration(new SpaceItemDecoration(40));
                // 适配数据
                Datas_Item datas_item=list_datas_items.get(position);
                Adapter_Process2 adapter_process2=new Adapter_Process2(datas_item);
                ((MyViewHolder2)holder).lv.setAdapter(adapter_process2);
                adapter_process2.notifyDataSetChanged();

                // 判定是不是倒数第二个位置，如果是，看看倒数第一是不是多人参与是否有数据，以便设置倒数第二的下线的颜色
                if(position==list_datas_items.size()-2 &&(list_datas_items.get(list_datas_items.size()-1).getFlowStatus().equals("5") || list_datas_items.get(list_datas_items.size()-1).getFlowStatus().equals("6"))) {
                    int d2=0;
                    for(int m=0;m<list_datas_items.get(list_datas_items.size()-1).getManyPeople().size();m++){

                        if(list_datas_items.get(list_datas_items.size()-1).getManyPeople().get(m).getFlowContent()!=null && !list_datas_items.get(list_datas_items.size()-1).getManyPeople().get(m).getFlowContent().equals("")){
                            d2=1;
                            break;
                        }
                    }
                    // 倒数第一为空数据，设置灰色
                    if(d2==0){
                        ((MyViewHolder2)holder).line_below.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                }else{
                    // 判定是不是倒数第二个位置，如果是，看看倒数第一是不是单人参与是否有数据，以便设置倒数第二的下线的颜色(因为最后一个是不是6，影响判定的标准)
                    // 倒数第一为空数据，设置灰色
                    if(position==list_datas_items.size()-2 && (list_datas_items.get(list_datas_items.size()-1).getFlowContent().equals("")|| list_datas_items.get(list_datas_items.size()-1).getFlowContent()==null )){
                        ((MyViewHolder2)holder).line_below.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                }
                Log.e("后通过任意发挥",list_datas_items.get(position).getFlowStatus()+"" );


                // 判定该Item的数据是不是空的，以便设置flowBut的数据
                int w=0;


                if(list_datas_items.get(position).getManyPeople() != null){

                    for(int g=0;g<list_datas_items.get(position).getManyPeople().size();g++){
                        if(list_datas_items.get(position).getManyPeople().get(g).getFlowContent()!=null && !list_datas_items.get(position).getManyPeople().get(g).getFlowContent().equals("")){
                            w=1;
                            ((MyViewHolder2)holder).details.setText(list_datas_items.get(position).getManyPeople().get(g).getFlowBut());

                            break;
                        }
                    }

                }

                // 如果查看什么详情的字符串是空的，就不显示
                if(((MyViewHolder2)holder).details.getText().equals("")){
                    ((MyViewHolder2)holder).details.setVisibility(View.INVISIBLE);
                    ((MyViewHolder2)holder).line_below.setVisibility(View.GONE);
                    ((MyViewHolder2)holder).line_below2.setVisibility(View.VISIBLE);
                    ((MyViewHolder2)holder).line_below2.setBackgroundColor(getResources().getColor(R.color.gray));
                    //  line_below下线是根据details
                    Log.e("从dvd上班v单反不过还没结婚",""+((MyViewHolder2)holder).line_below2.getHeight());

                }

                // 如果是最后一个，下面的线变成灰色
                if(position == list_datas_items.size()-1 && w==0){

                    ((MyViewHolder2)holder).line_below.setBackgroundColor(getResources().getColor(R.color.gray));
                    ((MyViewHolder2)holder).number.setBackground(getResources().getDrawable(R.drawable.gray));
                    ((MyViewHolder2)holder).lv.setVisibility(View.INVISIBLE);
                    ((MyViewHolder2)holder).details.setVisibility(View.INVISIBLE);
                    ((MyViewHolder2)holder).line_above.setBackgroundColor(getResources().getColor(R.color.gray));

                    if(return_proceed.getCb()){
                        ((MyViewHolder2)holder).feedback.setVisibility(View.VISIBLE);
                        ((MyViewHolder2)holder).feedback.setText("立即催办");
                        ((MyViewHolder2)holder).feedback.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // 催办网络请求
                                Call<Result_Push> call_push2= api.pushDrawer(userId,getIntent().getStringExtra("opId"));

                                call_push2.enqueue(new Callback<Result_Push>() {
                                    @Override
                                    public void onResponse(Call<Result_Push> call, Response<Result_Push> response) {

                                        if(response.body().getData()){

                                            // 加载popupwindow的布局
                                            View view5=getLayoutInflater().inflate(R.layout.system_notification,null);
                                            popupWindow=new PopupWindow(view5, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                                            ((MyViewHolder2)holder).feedback.setVisibility(View.GONE);
                                            // 初始化popupwindow的点击控件
                                            TextView content=(TextView) view5.findViewById(R.id.content);
                                            content.setText("催办成功");

                                            // 点击屏幕之外的区域可否让popupwindow消失
                                            popupWindow.setFocusable(true);
                                            popupWindow.setBackgroundDrawable(new BitmapDrawable());
                                            popupWindow.setOnDismissListener(new PoponDismissListener());

                                            View rootview5 = LayoutInflater.from(Process2_Activity.this).inflate(R.layout.see_process2, null);
                                            // 设置popupwindow的显示位置
                                            popupWindow.showAtLocation(rootview5, Gravity.CENTER,0,0);

                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    try {
                                                        Thread.sleep(2000);
                                                        back.post(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                popupWindow.dismiss();
                                                            }
                                                        });

                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();


                                        }else{
                                            View view7=getLayoutInflater().inflate(R.layout.system_notification,null);
                                            popupWindow=new PopupWindow(view7, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                                            // 初始化popupwindow的点击控件
                                            TextView content7=(TextView) view7.findViewById(R.id.content);
                                            content7.setText("催办失败");

                                            // 点击屏幕之外的区域可否让popupwindow消失
                                            popupWindow.setFocusable(true);
                                            popupWindow.setBackgroundDrawable(new BitmapDrawable());
                                            popupWindow.setOnDismissListener(new PoponDismissListener());

                                            View rootview7 = LayoutInflater.from(Process2_Activity.this).inflate(R.layout.see_process2, null);
                                            // 设置popupwindow的显示位置
                                            popupWindow.showAtLocation(rootview7, Gravity.CENTER,0,0);

                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    try {
                                                        Thread.sleep(2000);
                                                        back.post(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                popupWindow.dismiss();
                                                            }
                                                        });

                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }).start();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<Result_Push> call, Throwable t) {
                                        Toast.makeText(Process2_Activity.this,"网络连接有错误",Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        });
                    }

                    if(position == list_datas_items.size()-1){
                        ((MyViewHolder2)holder).line_below.setBackgroundColor(getResources().getColor(R.color.gray));
                    }
                }
            }
        }

        @Override
        public int getItemCount() {
            return list_datas_items.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView number;
            private TextView title;
            private TextView time_finish;
            private TextView time_during;
            private TextView details;
            private View line_below;
            private View line_above;
            private TextView content;
            private TextView feedback;
            private TextView rebanli;
            private CircleImageView header;
            private LinearLayout add_number2;

            public MyViewHolder(View itemView) {

                super(itemView);
                number = (TextView) itemView.findViewById(R.id.number);
                title = (TextView) itemView.findViewById(R.id.title);
                details = (TextView) itemView.findViewById(R.id.details);
                time_finish = (TextView) itemView.findViewById(R.id.time_finish);
                time_during = (TextView) itemView.findViewById(R.id.time_during);
                content = (TextView) itemView.findViewById(R.id.content);
                feedback = (TextView) itemView.findViewById(R.id.feedback);
                rebanli = (TextView) itemView.findViewById(R.id.rebanli);
                header = (CircleImageView) itemView.findViewById(R.id.header);
                line_below =  itemView.findViewById(R.id.line_below);
                line_above =  itemView.findViewById(R.id.line_above);
                add_number2 = (LinearLayout) itemView.findViewById(R.id.add_number2);
            }
        }

        class MyViewHolder2 extends RecyclerView.ViewHolder {

            private TextView number;
            private TextView title;
            private TextView details;
            private TextView feedback;
            private View line_below;
            private View line_below2;
            private View line_above;
            private RecyclerView lv;

            public MyViewHolder2(View itemView) {
                super(itemView);
                number = (TextView) itemView.findViewById(R.id.number);
                title = (TextView) itemView.findViewById(R.id.title);
                details = (TextView) itemView.findViewById(R.id.details);
                feedback = (TextView) itemView.findViewById(R.id.feedback);
                lv = (RecyclerView) itemView.findViewById(R.id.lv);
                line_below =  itemView.findViewById(R.id.line_below);
                line_below2 =  itemView.findViewById(R.id.line_below2);
                line_above =  itemView.findViewById(R.id.line_above);
            }
        }
    }

    class Adapter_Process2 extends RecyclerView.Adapter<Adapter_Process2.MyViewHolder3> {

        private View view = null;
        private Datas_Item  datas_item = null;
        private ArrayList<ManyPeople> manyPeople;

        public Adapter_Process2(Datas_Item datas_item) {
            this.datas_item = datas_item;
            manyPeople=datas_item.getManyPeople();
        }

        @Override
        public MyViewHolder3 onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.items_outs,parent,false);

            return new MyViewHolder3(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder3 holder,final int position) {


            holder.rebanli.setVisibility(View.GONE);
            holder.content.setText(manyPeople.get(position).getFlowContent());
            holder.time_during.setText(manyPeople.get(position).getFlowTime());
            holder.time_finish.setText(manyPeople.get(position).getOpCreateTime());
            if(!manyPeople.get(position).getFlowImgUrl().equals("") && manyPeople.get(position).getFlowImgUrl()!= null){
                Picasso.with(Process2_Activity.this).load(manyPeople.get(position).getFlowImgUrl()).memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.header);
            }

            if(manyPeople.get(position).getFlowContent() == null || manyPeople.get(position).getFlowContent().equals("")){
                holder.time_finish.setVisibility(View.GONE);
                holder.time_during.setVisibility(View.GONE);
                holder.add_number3.setVisibility(View.GONE);
            }

        }

        @Override
        public int getItemCount() {


            if(manyPeople == null){
                return 0;
            }
            return manyPeople.size();
        }

        class MyViewHolder3 extends RecyclerView.ViewHolder {

            private TextView time_finish;
            private TextView time_during;
            private TextView content;
            private TextView rebanli;
            private CircleImageView header;
            private LinearLayout add_number3;

            public MyViewHolder3(View itemView) {

                super(itemView);
                time_finish = (TextView) itemView.findViewById(R.id.time_finish);
                time_during = (TextView) itemView.findViewById(R.id.time_during);
                content = (TextView) itemView.findViewById(R.id.content);
                rebanli = (TextView) itemView.findViewById(R.id.rebanli);
                header = (CircleImageView) itemView.findViewById(R.id.header);
                add_number3 = (LinearLayout) itemView.findViewById(R.id.add_number2);
            }
        }
    }

    // 给RecyclerView设置item
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if(parent.getChildPosition(view) != 0)
                outRect.top = space;
        }
    }


    // popupwindow消失后触发的方法，将屏幕透明度调为1
    class PoponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            WindowManager.LayoutParams p = getWindow().getAttributes();
            p.alpha=(float) 1;
            getWindow().setAttributes(p);
        }

    }


}
