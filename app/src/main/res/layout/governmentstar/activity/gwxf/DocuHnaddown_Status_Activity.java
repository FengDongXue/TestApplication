package com.lanwei.governmentstar.activity.gwxf;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.icarus.entity.Image;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.AddCalendar_Activity;
import com.lanwei.governmentstar.activity.Convey_Files_Activity;
import com.lanwei.governmentstar.activity.Notification_Edit_Activity;
import com.lanwei.governmentstar.activity.Process2_Activity;
import com.lanwei.governmentstar.activity.TemporaryActivity;
import com.lanwei.governmentstar.bean.Data_Handdown;
import com.lanwei.governmentstar.bean.HandDown;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.KeyBoardUtils;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/8/4.
 */

public class DocuHnaddown_Status_Activity extends BaseActivity implements View.OnClickListener ,View.OnLayoutChangeListener {

    private ImageView back;
    private RecyclerView recyclerView;
    private GovernmentApi api;
    private Logging_Success bean;
    private String pageNo ="1";
    private int pageCount;
    private SwipeRefreshLayout swipe_layout;
    private ProgressBar not_loading;
    private HandDown handDown;
    private ArrayList<Data_Handdown> list =new ArrayList<>();
    private PopupWindow popupWindow;
    private String message ="";
    private Adapter_Addtion adapter_addtion;
    private ImageView withdrawal;
    //Activity最外层的Layout视图
    private View activityRootView;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    // 键盘是否弹出的状态
    private Boolean is_appearance = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handdown_status);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
        initweights();

        activityRootView = findViewById(R.id.root_layout);
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight/3;
        onrefresh();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //添加layout大小发生改变监听器
        activityRootView.addOnLayoutChangeListener(this);
    }

    // 监听是否弹出键盘，并做标记
    @Override
    public void onLayoutChange(View v, int left, int top, int right,
                               int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {

            is_appearance =true;

        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {

            is_appearance =false;

        }

    }


    void initweights(){
        back = (ImageView) findViewById(R.id.back);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipe_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        not_loading = (ProgressBar) findViewById(R.id.not_loading);
        withdrawal = (ImageView) findViewById(R.id.withdrawal);
        if(getIntent().getStringExtra("state").equals("1")){
            withdrawal.setVisibility(View.VISIBLE);
            withdrawal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // 弹出popupwindow前，调暗屏幕的透明度
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.alpha=(float) 0.8;
                    getWindow().setAttributes(lp);

                    // 加载popupwindow的布局
                    View view=getLayoutInflater().inflate(R.layout.cancel_reason,null,false);
                    popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                    // 初始化popupwindow的点击控件
                    final TextView reason_cancel=(TextView) view.findViewById(R.id.reason_cancel);
                    TextView cancel=(TextView) view.findViewById(R.id.cancel);

                    // 点击屏幕之外的区域可否让popupwindow消失
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupWindow.setOnDismissListener(new PoponDismissListener());
                    popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
                    popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                    // 注册popupwindow里面的点击事件
                    View rootview = LayoutInflater.from(DocuHnaddown_Status_Activity.this).inflate(R.layout.activity_handdown_status, null);
                    // 设置popupwindow的显示位置
                    popupWindow.showAtLocation(rootview, Gravity.BOTTOM,0,0);

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {


                            if(reason_cancel.getText().toString().equals("")){
                                Toast.makeText(DocuHnaddown_Status_Activity.this, "撤回理由不能为空！", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            Call<JsonObject> call= api.handdown_withdrawal(getIntent().getStringExtra("opId"),bean.getData().getOpId(),reason_cancel.getText().toString().trim(),"1");

                            call.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                    popupWindow.dismiss();
                                    if(response != null){
                                        JsonObject jsonObject =response.body();
                                        if(jsonObject.get("result").getAsBoolean()){

                                            Toast.makeText(DocuHnaddown_Status_Activity.this, "撤回成功", Toast.LENGTH_SHORT).show();

                                            list.clear();
                                            adapter_addtion.notifyDataSetChanged();

                                            Intent intent =new Intent();
                                            setResult(520,intent);
                                            finish();

                                        }else{

                                            Toast.makeText(DocuHnaddown_Status_Activity.this, "撤回失败", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    popupWindow.dismiss();
                                    Toast.makeText(DocuHnaddown_Status_Activity.this, "网络连接有误", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });


                }
            });
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        back.setOnClickListener(this);
        swipe_layout.setColorSchemeColors(getResources().getColor(R.color.color_00a7e4));
        adapter_addtion =new Adapter_Addtion();
        recyclerView.setAdapter(adapter_addtion);
        // 获取bean;
        String defString = PreferencesManager.getInstance(DocuHnaddown_Status_Activity.this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);

        swipe_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                pageNo="1";
                onrefresh();

            }
        });

        Mugen.with(recyclerView, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                pageNo = String.valueOf(Integer.parseInt(pageNo) +1);
                onloadmore();
            }

            @Override
            public boolean isLoading() {

                Log.e("boolean",(not_loading.getVisibility()==View.VISIBLE)+"");
                return not_loading.getVisibility()==View.VISIBLE;
            }

            @Override
            public boolean hasLoadedAllItems() {

                Log.e("boolean","pageCount="+pageCount+"        pageNo="+pageNo);
                return pageCount <= Integer.parseInt(pageNo);
            }
        }).start();

    }

    void onrefresh(){

        api= HttpClient.getInstance().getGovernmentApi();

        Call<HandDown> call= api.departments_list(getIntent().getStringExtra("opId"),pageNo);

        call.enqueue(new Callback<HandDown>() {
            @Override
            public void onResponse(Call<HandDown> call, Response<HandDown> response) {

                if(response.body() != null){
                    handDown=response.body();
                    list =handDown.getData();
                    pageCount = handDown.getPageCount();
                    pageNo = String.valueOf(handDown.getPageNo());
                    adapter_addtion.notifyDataSetChanged();
                }else{
                    recyclerView.setVisibility(View.GONE);
                }
                swipe_layout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<HandDown> call, Throwable t) {
                swipe_layout.setRefreshing(false);
                Toast.makeText(DocuHnaddown_Status_Activity.this, "网络连接有误", Toast.LENGTH_SHORT).show();

            }
        });
    }


    void onloadmore(){

        api= HttpClient.getInstance().getGovernmentApi();

        Call<HandDown> call= api.departments_list(getIntent().getStringExtra("opId"),pageNo);

        call.enqueue(new Callback<HandDown>() {
            @Override
            public void onResponse(Call<HandDown> call, Response<HandDown> response) {

                if(response.body() != null){
                    handDown=response.body();
                    if(handDown.getData() != null){
                        list.addAll(handDown.getData());
                    }
                    pageCount = handDown.getPageCount();
                    pageNo = String.valueOf(handDown.getPageNo());
                    if(adapter_addtion != null){
                       adapter_addtion.notifyDataSetChanged();
                    }
                }else{
                    recyclerView.setVisibility(View.GONE);
                }
                swipe_layout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<HandDown> call, Throwable t) {
                swipe_layout.setRefreshing(false);
                Toast.makeText(DocuHnaddown_Status_Activity.this, "网络连接有误", Toast.LENGTH_SHORT).show();

            }
        });
    }

    // 附件的adapter
    class Adapter_Addtion extends RecyclerView.Adapter<Adapter_Addtion.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion() {

        }

        @Override
        public Adapter_Addtion.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.status_handdown, parent, false);

            return new Adapter_Addtion.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Addtion.MyViewHolder holder, final int position) {


            if(position == list.size()-1){
                holder.line.setVisibility(View.INVISIBLE);
            }else{
                holder.line.setVisibility(View.VISIBLE);
            }

            holder.title.setText(list.get(position).getIssuedDeptName());
            holder.status.setText("状态: "+list.get(position).getIssuedStatus());
            if(getIntent().getStringExtra("state").equals("2")){
                holder.status.setTextColor(getResources().getColor(R.color.blue));
            }else if(getIntent().getStringExtra("state").equals("1")){
                holder.status.setTextColor(getResources().getColor(R.color.theme_change));
            }

            holder.time.setText(list.get(position).getIssuedTime());

            holder.more_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View view1 = getLayoutInflater().inflate(R.layout.layout_twomenu, null ,false);
                    popupWindow = new PopupWindow(view1, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    // 点击屏幕之外的区域可否让popupwindow消失
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupWindow.setOnDismissListener(new PoponDismissListener());
                    TextView track = (TextView) view1.findViewById(R.id.track);
                    TextView withdrawal = (TextView) view1.findViewById(R.id.withdrawal);
                    track.setText("跟踪");

                    if(getIntent().getStringExtra("state").equals("2")){
                        withdrawal.setText("失效");
                    }else if(getIntent().getStringExtra("state").equals("1")){
                        withdrawal.setText("撤回");
                    }

                    track.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            popupWindow.dismiss();
                            Intent intent = new Intent(DocuHnaddown_Status_Activity.this,Convey_Files_Activity.class);
                            intent.putExtra("opId",list.get(position).getIssuedChildren());
                            startActivity(intent);

                        }
                    });

                    withdrawal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {

                            if(getIntent().getStringExtra("state").equals("2")){

                                if(holder.status.getText().toString().equals("状态: 已失效")){
                                    popupWindow.dismiss();
                                    Toast.makeText(DocuHnaddown_Status_Activity.this, "已失效", Toast.LENGTH_SHORT).show();

                                    return;
                                }

                                Call<JsonObject> call= api.handdown_cancel(list.get(position).getOpId(),bean.getData().getOpId());

                                call.enqueue(new Callback<JsonObject>() {
                                    @Override
                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                        popupWindow.dismiss();
                                        if(response != null){
                                            JsonObject jsonObject =response.body();
                                            if(jsonObject.get("result").getAsBoolean()){

                                                Toast.makeText(DocuHnaddown_Status_Activity.this, "失效成功", Toast.LENGTH_SHORT).show();
                                                holder.status.setText("状态: 已失效");

                                            }else{

                                                Toast.makeText(DocuHnaddown_Status_Activity.this, "失效失败", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<JsonObject> call, Throwable t) {
                                        Toast.makeText(DocuHnaddown_Status_Activity.this, "网络连接有误", Toast.LENGTH_SHORT).show();

                                    }
                                });


                            }else if(getIntent().getStringExtra("state").equals("1")){

                                if(holder.status.getText().toString().equals("状态: 已撤回")){

                                    popupWindow.dismiss();
                                    Toast.makeText(DocuHnaddown_Status_Activity.this, "已撤回", Toast.LENGTH_SHORT).show();

                                    return;
                                }

                                popupWindow.dismiss();
                                // 弹出popupwindow前，调暗屏幕的透明度
                                WindowManager.LayoutParams lp = getWindow().getAttributes();
                                lp.alpha=(float) 0.8;
                                getWindow().setAttributes(lp);

                                // 加载popupwindow的布局
                                View view=getLayoutInflater().inflate(R.layout.cancel_reason,null,false);
                                popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                                // 初始化popupwindow的点击控件
                                final EditText reason_cancel=(EditText) view.findViewById(R.id.reason_cancel);
                                final TextView cancel=(TextView) view.findViewById(R.id.cancel);

                                // 点击屏幕之外的区域可否让popupwindow消失
                                popupWindow.setFocusable(true);
                                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                                popupWindow.setOnDismissListener(new PoponDismissListener());
                                popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
                                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                                // 注册popupwindow里面的点击事件
                                View rootview = LayoutInflater.from(DocuHnaddown_Status_Activity.this).inflate(R.layout.activity_handdown_status, null);
                                // 设置popupwindow的显示位置
                                popupWindow.showAtLocation(rootview, Gravity.BOTTOM,0,0);

                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(final View v) {


                                        if(reason_cancel.getText().toString().equals("")){
                                            Toast.makeText(DocuHnaddown_Status_Activity.this, "撤回理由不能为空！", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        Call<JsonObject> call= api.handdown_withdrawal(list.get(position).getOpId(),bean.getData().getOpId(),reason_cancel.getText().toString().trim(),"0");

                                        call.enqueue(new Callback<JsonObject>() {
                                            @Override
                                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                                popupWindow.dismiss();
                                                if(response != null){
                                                    JsonObject jsonObject =response.body();
                                                    if(jsonObject.get("result").getAsBoolean()){

                                                        Toast.makeText(DocuHnaddown_Status_Activity.this, "撤回成功", Toast.LENGTH_SHORT).show();
//                                                        Toast.makeText(DocuHnaddown_Status_Activity.this, "pageNo="+pageNo+"pagenum="+pageCount, Toast.LENGTH_SHORT).show();

                                                        list.remove(position);

                                                        // 保证移除数据后，还有足够多分数据量，可以上拉滑动，不然没法触发mugen了
                                                        if(list.size()<9){
                                                            onrefresh();
                                                        }else{
                                                            adapter_addtion.notifyDataSetChanged();
                                                        }

//                                                        holder.status.setText("状态: 已撤回");
                                                    }else{

                                                        Toast.makeText(DocuHnaddown_Status_Activity.this, "撤回失败", Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                                popupWindow.dismiss();
                                                Toast.makeText(DocuHnaddown_Status_Activity.this, "网络连接有误", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                });
                            }
                        }
                    });

                    int width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    int height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                    view1.measure(width,height);
                    int height2=view1.getMeasuredHeight();
                    int width2=view1.getMeasuredWidth();

                    //获取需要在其上方显示的控件的位置信息
                    int[] location = new int[2];
                    v.getLocationOnScreen(location);
                    //在控件上方显示
                    popupWindow.showAtLocation(v, Gravity.NO_GRAVITY, (location[0] + v.getWidth() / 2) - width2 , location[1] - height2);


                }
            });
        }

        @Override
        public int getItemCount() {

            if(list == null){
                recyclerView.setVisibility(View.GONE);
                return 0;
            }

            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView title;
            TextView status;
            TextView time;
            LinearLayout more_layout;
            View line;

            public MyViewHolder(View itemView) {

                super(itemView);
                title = (TextView) itemView.findViewById(R.id.title);
                status = (TextView) itemView.findViewById(R.id.status);
                time = (TextView) itemView.findViewById(R.id.time);
                more_layout = (LinearLayout) itemView.findViewById(R.id.more_layout);
                line = itemView.findViewById(R.id.line);
            }
        }

    }


    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.back){
            finish();
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

            if(is_appearance){
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                Log.e("到底怎么吗不能6666666666","sdsdssd");
            }
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
