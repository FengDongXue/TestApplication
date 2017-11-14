package com.lanwei.governmentstar.activity.gwxf;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.Notification_Edit_Activity;
import com.lanwei.governmentstar.activity.lll.DocumentBaseCActivity;
import com.lanwei.governmentstar.activity.zyx.DetailsFJActivity;
import com.lanwei.governmentstar.bean.Files;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Return_Handdown_Comin;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.MyScrollView_Focus;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/8/7.
 */

public class Documnet_Preview extends BaseActivity implements View.OnClickListener , MyScrollView_Focus.MyScrollListener{


    private TextView theme;
    private TextView date2;
    private ImageView back;
    private TextView number_document;
    private TextView department_from;
    private TextView send_atonce;
    private RecyclerView departments_to;
    private ArrayList<String> list_departemnt =new ArrayList<>();
    private WebView webview;
    private LinearLayout document_fujian;
    private ArrayList<Files> issuedFileLists = new ArrayList<>();
    private MyScrollView_Focus myScrollView_focus;
    private RecyclerView recycler_view;
    private FloatingActionButton enshrink;
    private FloatingActionButton enlarge;
    private GovernmentApi api;
    private PopupWindowUtil popupWindowUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        intview();

        api= HttpClient.getInstance().getGovernmentApi();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.back:

                finish();
                break;

            case R.id.enshrink:
                webview.loadUrl("javascript:narrow()");

                break;

            case R.id.send_atonce:


                new DialogUtil(Documnet_Preview.this, new Summit()).showConfirm("下发公文", "您确定要下发该公文吗？", "确定", "取消");


                break;

            case R.id.enlarge:

                webview.loadUrl("javascript:amplify()");
                break;


        }


    }

    // 自定义的Scrollview_Focus的回掉方法，里面监听滑动，之后就通过handler每隔一定时间判断是否还在滑动，并不断回掉该方法设置FloatingActionButton的可见性
    // 悬浮按钮的显示随着webview是否在屏幕上显示而动态设置可见性，（就是这么简单，不用我们自己去监听scrollview的滑动，判断滑动值知道webview的可视性了，恩，简单的方法总是有的，尽可能去畅想是否有相关的API接口就好了，不必自己去实现）
    @Override
    public void sendDistanceY(int distance) {
        Rect scrollBounds = new Rect();
        myScrollView_focus.getHitRect(scrollBounds);
        if (webview.getLocalVisibleRect(scrollBounds)) {

            enlarge.setVisibility(View.VISIBLE);
            enshrink.setVisibility(View.VISIBLE);
            //子控件至少有一个像素在可视范围内
            // Any portion of the childView, even a single pixel, is within the visible window
        } else {

            enlarge.setVisibility(View.GONE);
            enshrink.setVisibility(View.GONE);

            //子控件完全不在可视范围内
            // NONE of the childView is within the visible window
        }
    }

    void intview(){

        theme = (TextView) findViewById(R.id.theme);
        send_atonce = (TextView) findViewById(R.id.send_atonce);
        back = (ImageView) findViewById(R.id.back);
        date2 = (TextView) findViewById(R.id.date);
        number_document = (TextView) findViewById(R.id.number_document);
        department_from = (TextView) findViewById(R.id.department_from);
        departments_to = (RecyclerView) findViewById(R.id.departments_to);
        webview = (WebView) findViewById(R.id.webview);
        document_fujian = (LinearLayout) findViewById(R.id.document_fujian);
        myScrollView_focus = (MyScrollView_Focus) findViewById(R.id.myScrollView_focus);
        myScrollView_focus.setMyScrollListener(this);
        enshrink = (FloatingActionButton) findViewById(R.id.enshrink);
        enlarge = (FloatingActionButton) findViewById(R.id.enlarge);
        // 为RecyclerView设置默认动画和线性布局管理器
        departments_to.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        departments_to.setLayoutManager(new LinearLayoutManager(this){

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });


        initWebView();
        theme.setText(getIntent().getStringExtra("gwxfTitle"));
        date2.setText(getIntent().getStringExtra("gwxfData"));

        if(!getIntent().getStringExtra("zh_0").trim().equals("")){
            number_document.setText(getIntent().getStringExtra("zh_0")+" 号");
        }

        department_from.setText(getIntent().getStringExtra("gwxfLwdwName"));
        webview.loadUrl(getIntent().getStringExtra("webview"));

        webview = (WebView) findViewById(R.id.webview);

        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        // 为RecyclerView设置默认动画和线性布局管理器
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        recycler_view.setLayoutManager(new LinearLayoutManager(this){

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        String filesname = getIntent().getStringExtra("files_name");


        if(!getIntent().getStringExtra("files_name").equals("")){

            String[] opid_list = filesname.split(",");
            // 数组转为Arraylist
            ArrayList<String> list2 = new ArrayList<>(Arrays.asList(opid_list));


            for(int i=0;i<list2.size()/3;i++){
                issuedFileLists.add(new Files(list2.get(i*3),list2.get(i*3+1),list2.get(i*3+2)));
            }
            recycler_view.setAdapter(new Adapter_Addtion());
        }else{
            document_fujian.setVisibility(View.GONE);
        }



        if(!getIntent().getStringExtra("dopIds_name").equals("")){

            String[] opid_list = getIntent().getStringExtra("dopIds_name").split(",");
            // 数组转为Arraylist
            ArrayList<String> list2 = new ArrayList<>(Arrays.asList(opid_list));

            for(int i=0;i<list2.size();i++){
                list_departemnt.add(list2.get(i));
            }
            departments_to.setAdapter(new Adapter_Addtion2());


        }else{

        }
        back.setOnClickListener(this);
        enshrink.setOnClickListener(this);
        enlarge.setOnClickListener(this);
        send_atonce.setOnClickListener(this);

    }

    // 附件的adapter
    class Adapter_Addtion extends RecyclerView.Adapter<Adapter_Addtion.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion() {

        }

        @Override
        public Adapter_Addtion.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = LayoutInflater.from(Documnet_Preview.this).inflate(R.layout.addition_files, parent, false);

            return new Adapter_Addtion.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Addtion.MyViewHolder holder, final int position) {

            holder.addtion.setText("附件" + (position + 1) + " : ");
            holder.addtional.setText(issuedFileLists.get(position).getOpName());
            holder.remove_fj.setVisibility(View.INVISIBLE);

            if (issuedFileLists.size() - 1 == position) {
                holder.line.setVisibility(View.GONE);
            } else {
                holder.line.setVisibility(View.VISIBLE);
            }

            // item的点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    File f = new File(issuedFileLists.get(position).getOpName());
                    String fileName = f.getName();
                    String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);

                    // 判断类型，选择是在线预览还是直接下载
                    if (prefix.equals("docx") || prefix.equals("doc") || prefix.equals("xlsx") || prefix.equals("xls") || prefix.equals("jpg") || prefix.equals("png") || prefix.equals("gif") || prefix.equals("pdf")) {

                        Intent intent = new Intent(Documnet_Preview.this, DetailsFJActivity.class);
                        intent.putExtra("filePreview", issuedFileLists.get(position).getFilePreview());
                        intent.putExtra("type", "1");
                        startActivity(intent);

                    }


                }
            });

        }

        @Override
        public int getItemCount() {

            return issuedFileLists.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView addtion;
            TextView addtional;
            ImageView remove_fj;
            View line;

            public MyViewHolder(View itemView) {
                super(itemView);
                addtion = (TextView) itemView.findViewById(R.id.addtion);
                addtional = (TextView) itemView.findViewById(R.id.addtional);
                remove_fj = (ImageView) itemView.findViewById(R.id.remove_fj);
                line = itemView.findViewById(R.id.line);
            }
        }
    }


    // 附件的adapter
    class Adapter_Addtion2 extends RecyclerView.Adapter<Adapter_Addtion2.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion2() {

        }

        @Override
        public Adapter_Addtion2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = LayoutInflater.from(Documnet_Preview.this).inflate(R.layout.addition_files, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Addtion2.MyViewHolder holder, final int position) {

            holder.addtion.setVisibility(View.GONE);
            holder.addtional.setText(list_departemnt.get(position));
            holder.remove_fj.setVisibility(View.GONE);

            if (list_departemnt.size() - 1 == position) {
                holder.line.setVisibility(View.GONE);
            } else {
                holder.line.setVisibility(View.VISIBLE);
            }


        }

        @Override
        public int getItemCount() {

            return list_departemnt.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView addtion;
            TextView addtional;
            ImageView remove_fj;
            View line;

            public MyViewHolder(View itemView) {
                super(itemView);
                addtion = (TextView) itemView.findViewById(R.id.addtion);
                addtional = (TextView) itemView.findViewById(R.id.addtional);
                remove_fj = (ImageView) itemView.findViewById(R.id.remove_fj);
                line = itemView.findViewById(R.id.line);
            }
        }
    }

    private void initWebView() {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.getSettings().setTextZoom(100);

        // webview和scrollview的滑动冲突，设置webview不响应点击（包括滑动）事件
        webview.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                ((WebView) v).requestDisallowInterceptTouchEvent(false);
                return true;

            }

        });

        // 取消webview的垂直，水平
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);

    }

    // 下载弹出框点击的回掉方法体
    class Summit implements DialogUtil.OnClickListenner {

        public Summit() {
        }

        @Override
        public void yesClick() {
            popupWindowUtil = new PopupWindowUtil(Documnet_Preview.this, "提交中...");
            popupWindowUtil.show();

            Call<JsonObject> call=api.summit_handdown(getIntent().getStringExtra("opId"),getIntent().getStringExtra("userId"),getIntent().getStringExtra("gwxfTitle"),
                    getIntent().getStringExtra("gwxfData"),getIntent().getStringExtra("gwxfLwdwName"),getIntent().getStringExtra("gwxfLwdwId"),getIntent().getStringExtra("opIds"),
                    getIntent().getStringExtra("content"),getIntent().getStringExtra("gwxfZh1"),getIntent().getStringExtra("gwxfZh2"),getIntent().getStringExtra("gwxfZh3"),
                    getIntent().getStringExtra("gwxfZh4"),getIntent().getStringExtra("gwxfZh5"));

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if(response.body() != null && !response.body().equals("")){

                        if(response.body().get("result").getAsBoolean()){
                            Toast.makeText(Documnet_Preview.this, "下发成功！", Toast.LENGTH_SHORT).show();

                            Intent intent= new Intent();
                            setResult(005,intent);
                            finish();

                        }else{

                            Toast.makeText(Documnet_Preview.this, "下发失败！", Toast.LENGTH_SHORT).show();
                        }
                        popupWindowUtil.dismiss();
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(Documnet_Preview.this, "网络连接有误！", Toast.LENGTH_SHORT).show();
                    popupWindowUtil.dismiss();

                }
            });

        }

        @Override
        public void noClick() {
        }

        @Override
        public void onSingleClick() {

        }
    }
}
