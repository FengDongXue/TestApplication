package com.lanwei.governmentstar.activity.gwxf;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.lib.WheelView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;;
import com.lanwei.governmentstar.activity.Modules_Type_Activity;
import com.lanwei.governmentstar.activity.Notification_Edit_Activity;
import com.lanwei.governmentstar.activity.Process2_Activity;
import com.lanwei.governmentstar.activity.gwnz.DocumentApproveActivity;
import com.lanwei.governmentstar.activity.lll.DocumentBaseCActivity;
import com.lanwei.governmentstar.activity.zyx.DetailsFJActivity;
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

import org.json.JSONException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/8/4.
 */

public class DocuHanddown_Activity extends BaseActivity implements View.OnClickListener , MyScrollView_Focus.MyScrollListener ,View.OnLayoutChangeListener {

    private TextView theme;
    private TextView date2;
    private TextView send;
    private ImageView back;
    private TextView number_document;
    private TextView department_from;
    private TextView to_departments;
    private TextView gongwenzihao;
    private String department_from_id ="";
    private RecyclerView department_to;
    private LinearLayout date_choose;
    private LinearLayout department_choose;
    private LinearLayout department_chooseto;
    private LinearLayout department_from2;
    private LinearLayout document_fujian;
    private LinearLayout preview;
    private LinearLayout process;
    private LinearLayout zihao;
    private LinearLayout root_layout;
    private ArrayList<Return_Handdown_Comin.IssuedFileList> fileLists;
    private Logging_Success bean;
    private GovernmentApi api;
    private String opIds = "";
    private ArrayList<String> list_opIds =new ArrayList<>();
    private String dopIds_name = "";
    private Return_Handdown_Comin return_handdown_comin;
    private String zh_1 ="";
    private String zh_2 ="";
    private String zh_3 ="";
    private String zh_4 ="";
    private String zh_5 ="";
    private EditText contnet;
    private WebView webview;
    private Map<String , Object> map_list = new HashMap();
    private ArrayList<String> list_key =new ArrayList<>();
    private RecyclerView recycler_view;
    private Adapter_Addtion adapter_addtion;
    private Adapter_Addtion2 adapter_addtion2;
    private PopupWindow popupWindow;
    private String department_from_name ="";
    private FloatingActionButton enshrink;
    private FloatingActionButton enlarge;
    private MyScrollView_Focus myScrollView_focus;
    private int year;
    //Activity最外层的Layout视图
    private View activityRootView;
    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    private PopupWindowUtil popupWindowUtil;
    private ArrayList<String> list_departemnt =new ArrayList<>();
    private RelativeLayout relative;
    private WheelView wheellistview1;
    private WheelView wheellistview2;
    private WheelView wheellistview3;
    private WheelView wheellistview4;
    private WheelView wheellistview5;
//    private WheelListView wheellistview1;
//    private WheelListView wheellistview2;
//    private WheelListView wheellistview3;
//    private WheelListView wheellistview4;
//    private WheelListView wheellistview5;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_handdown);
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
        activityRootView = findViewById(R.id.root_layout);
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight/3;

        intview();


        // 获取bean;
        String defString = PreferencesManager.getInstance(DocuHanddown_Activity.this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);
        api= HttpClient.getInstance().getGovernmentApi();

        Log.e("popid",getIntent().getStringExtra("opId"));
        Call<Return_Handdown_Comin> call= api.handdown_comein(getIntent().getStringExtra("opId"),bean.getData().getOpId());
        call.enqueue(new Callback<Return_Handdown_Comin>() {
            @Override
            public void onResponse(Call<Return_Handdown_Comin> call, Response<Return_Handdown_Comin> response) {

                if(response.body().getData() != null){
                    return_handdown_comin = response.body();
                    theme.setText(return_handdown_comin.getData().getIssuedTitle());
//                    zh_1.setText(return_handdown_comin.getData().getIssuedZh1());
//                    zh_2.setText(return_handdown_comin.getData().getIssuedZh2());
//
//                    if(return_handdown_comin.getData().getIssuedZh4().equals("") || return_handdown_comin.getData().getIssuedZh4() == null){
//                        zh_4.setText(year+"");
//                    }else{
//                        zh_4.setText(return_handdown_comin.getData().getIssuedZh4());
//                    }
//
//
//
//                    zh_3.setText(return_handdown_comin.getData().getIssuedZh4());
//                    zh_5.setText(return_handdown_comin.getData().getIssuedZh5());
                    if(!return_handdown_comin.getData().getIssuedZh1().equals("")){
                        gongwenzihao.setText(return_handdown_comin.getData().getIssuedZh1() +" "+ return_handdown_comin.getData().getIssuedZh2() +" "+return_handdown_comin.getData().getIssuedZh3() +" "+"〔"+return_handdown_comin.getData().getIssuedZh4()+"〕" +" "+return_handdown_comin.getData().getIssuedZh5()+" 号");
                        zh_1=return_handdown_comin.getData().getIssuedZh1();
                        zh_2=return_handdown_comin.getData().getIssuedZh2();
                        zh_3=return_handdown_comin.getData().getIssuedZh3();
                        zh_4=return_handdown_comin.getData().getIssuedZh4();
                        zh_5=return_handdown_comin.getData().getIssuedZh5();
                        gongwenzihao.setVisibility(View.VISIBLE);
                        gongwenzihao.setTextSize(16);
                        gongwenzihao.setTextColor(getResources().getColor(R.color.color_23));
                    }
                    if(return_handdown_comin.getData().getIssuedLwdwId() == null){
                        department_from_id = "";
                        department_from_name = "";
                    }else{
                        department_from_id = return_handdown_comin.getData().getIssuedLwdwId();
                        department_from.setText(return_handdown_comin.getData().getIssuedLwdwName());
                        department_from.setTextColor(getResources().getColor(R.color.color_23));
                        department_from.setTextSize(16);
                        department_from_name = return_handdown_comin.getData().getIssuedLwdwName();
                    }

                    webview.loadUrl(return_handdown_comin.getData().getIssuedURL());

                    if(return_handdown_comin.getData().getIssuedFileList() != null && return_handdown_comin.getData().getIssuedFileList().size()>0){
                        fileLists = return_handdown_comin.getData().getIssuedFileList();
                        adapter_addtion= new Adapter_Addtion();
                        recycler_view.setAdapter(adapter_addtion);

                    }else{
                        document_fujian.setVisibility(View.GONE);
                    }

                }
            }

            @Override
            public void onFailure(Call<Return_Handdown_Comin> call, Throwable t) {
                Toast.makeText(DocuHanddown_Activity.this, "网络连接有误", Toast.LENGTH_SHORT).show();

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        //添加layout大小发生改变监听器
        activityRootView.addOnLayoutChangeListener(this);
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right,
                               int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值

//      System.out.println(oldLeft + " " + oldTop +" " + oldRight + " " + oldBottom);
//      System.out.println(left + " " + top +" " + right + " " + bottom);


        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {

//            Toast.makeText(DocuHanddown_Activity.this, "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();

            enlarge.setVisibility(View.GONE);
            enshrink.setVisibility(View.GONE);


        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {

//            Toast.makeText(DocuHanddown_Activity.this, "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();

            enlarge.setVisibility(View.VISIBLE);
            enshrink.setVisibility(View.VISIBLE);

        }

    }

    void intview(){

        theme = (TextView) findViewById(R.id.theme);
        send = (TextView) findViewById(R.id.send);
        back = (ImageView) findViewById(R.id.back);
        date2 = (TextView) findViewById(R.id.date);
        number_document = (TextView) findViewById(R.id.number_document);
        department_from = (TextView) findViewById(R.id.department_from);
        to_departments = (TextView) findViewById(R.id.to_departments);
        gongwenzihao = (TextView) findViewById(R.id.gongwenzihao);
        department_to = (RecyclerView) findViewById(R.id.department_to);
        enshrink = (FloatingActionButton) findViewById(R.id.enshrink);
        enlarge = (FloatingActionButton) findViewById(R.id.enlarge);
        relative = (RelativeLayout) findViewById(R.id.relative);

        relative.setVisibility(View.GONE);
        myScrollView_focus = (MyScrollView_Focus) findViewById(R.id.document_scrollView);
        myScrollView_focus.setMyScrollListener(this);

//        enshrink.setBackgroundColor(getResources().getColor(R.color.white));
//        enlarge.setBackgroundColor(getResources().getColor(R.color.white));
        enlarge.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        enshrink.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        date2.setText(date);

        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);

        contnet = (EditText) findViewById(R.id.contnet);
        webview = (WebView) findViewById(R.id.webview);



        initWebView();
        date_choose = (LinearLayout) findViewById(R.id.date_choose);
        department_choose = (LinearLayout) findViewById(R.id.department_choose);
        department_chooseto = (LinearLayout) findViewById(R.id.department_chooseto);
        document_fujian = (LinearLayout) findViewById(R.id.document_fujian);
        department_from2 = (LinearLayout) findViewById(R.id.department_from2);
        preview = (LinearLayout) findViewById(R.id.preview);
        process = (LinearLayout) findViewById(R.id.process);
        zihao = (LinearLayout) findViewById(R.id.zihao);
        root_layout = (LinearLayout) findViewById(R.id.root_layout);

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

        // 为RecyclerView设置默认动画和线性布局管理器
        department_to.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        department_to.setLayoutManager(new LinearLayoutManager(this){

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        date_choose.setOnClickListener(this);
        department_choose.setOnClickListener(this);
        department_chooseto.setOnClickListener(this);
        back.setOnClickListener(this);
        send.setOnClickListener(this);
        department_from2.setOnClickListener(this);
        date_choose.setOnClickListener(this);
        process.setOnClickListener(this);
        preview.setOnClickListener(this);
        enshrink.setOnClickListener(this);
        enlarge.setOnClickListener(this);
        zihao.setOnClickListener(this);
        zihao.setVisibility(View.VISIBLE);

    }


    private void initWebView() {

        // 取消webview的垂直，水平
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.getSettings().setUseWideViewPort(false);
        webview.getSettings().setLoadWithOverviewMode(true);

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

    @Override
    public void onClick(View v) {

        Intent intent =new Intent();

        switch (v.getId()){

            case R.id.back:
                Intent intent2 = new Intent();
                setResult(0, intent2);
                finish();

                break;

            case R.id.zihao:
                // 弹出popupwindow前，调暗屏幕的透明度
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha=(float) 0.8;
                getWindow().setAttributes(lp);

                // 加载popupwindow的布局
                View view=getLayoutInflater().inflate(R.layout.wheels,null ,false);
                popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                final ArrayList<String> list1 =new ArrayList<>();
                final ArrayList<String> list2 =new ArrayList<>();
                final ArrayList<String> list3 =new ArrayList<>();
                final ArrayList<String> list4 =new ArrayList<>();
                final ArrayList<String> list5 =new ArrayList<>();
                // 初始化popupwindow的点击控件
                wheellistview1 = (com.bigkoo.pickerview.lib.WheelView) view.findViewById(R.id.wheellistview1);
                wheellistview2 = (com.bigkoo.pickerview.lib.WheelView)view.findViewById(R.id.wheellistview2);
                wheellistview3 = (com.bigkoo.pickerview.lib.WheelView)view.findViewById(R.id.wheellistview3);
                wheellistview4 = (com.bigkoo.pickerview.lib.WheelView)view.findViewById(R.id.wheellistview4);
                wheellistview5 = (com.bigkoo.pickerview.lib.WheelView)view.findViewById(R.id.wheellistview5);

                TextView cancel = (TextView) view.findViewById(R.id.cancel);
                TextView identify = (TextView) view.findViewById(R.id.identify);

                identify.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();

                        gongwenzihao.setText(list1.get(wheellistview1.getCurrentItem())
                                + list2.get(wheellistview2.getCurrentItem())
                                +list3.get(wheellistview3.getCurrentItem())
                                +"〔"+list4.get(wheellistview4.getCurrentItem()) +"〕"
                                +list5.get(wheellistview5.getCurrentItem())+"号");
                        gongwenzihao.setTextSize(16);
                        gongwenzihao.setTextColor(getResources().getColor(R.color.color_23));
                        zh_1=list1.get(wheellistview1.getCurrentItem());
                        zh_2=list2.get(wheellistview2.getCurrentItem());
                        zh_3=list3.get(wheellistview3.getCurrentItem());
                        zh_4="〔"+list4.get(wheellistview4.getCurrentItem())+"〕";
                        zh_5=list5.get(wheellistview5.getCurrentItem());


                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });


                list1.add("津蓟");
                list2.add("工信");
                list2.add("发改");
                list2.add("卫计");
                list3.add("发");
                list3.add("函");
                list3.add("办");
                list3.add("字");
                list3.add("分");
                list3.add("呈");
                list3.add("报");
                list3.add("请");
                list3.add("便函");
                list3.add("党");

                for(int i=2008;i<2026;i++){
                    list4.add(i+"");
                }


                for(int i=0;i<1000;i++){
                    list5.add(i+"");
                }
                wheellistview1.setAdapter(new ArrayWheelAdapter(list1));
                wheellistview2.setAdapter(new ArrayWheelAdapter(list2));
                wheellistview3.setAdapter(new ArrayWheelAdapter(list3));
                wheellistview4.setAdapter(new ArrayWheelAdapter(list4));
                wheellistview5.setAdapter(new ArrayWheelAdapter(list5));
                wheellistview1.setCurrentItem(list1.size()/2);
                wheellistview2.setCurrentItem(list2.size()/2);
                wheellistview3.setCurrentItem(list3.size()/2);
                wheellistview4.setCurrentItem(list4.size()/2);
                wheellistview5.setCurrentItem(list5.size()/2);
                wheellistview1.setCyclic(false);
                wheellistview1.setTextSize(16);
                wheellistview2.setCyclic(false);
                wheellistview2.setTextSize(16);
                wheellistview3.setCyclic(false);
                wheellistview3.setTextSize(16);
                wheellistview4.setCyclic(false);
                wheellistview4.setTextSize(16);
                wheellistview5.setCyclic(false);
                wheellistview5.setTextSize(16);


                // 点击屏幕之外的区域可否让popupwindow消失
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new PoponDismissListener());

                View rootview = LayoutInflater.from(this).inflate(R.layout.activity_handdown, null);
                // 设置popupwindow的显示位置
                popupWindow.showAtLocation(rootview, Gravity.BOTTOM,0,0);


                break;

            case R.id.enshrink:
                webview.loadUrl("javascript:narrow()");

                break;

            case R.id.enlarge:

                webview.loadUrl("javascript:amplify()");
                break;


            case R.id.preview:


                if(theme.getText().toString().equals("")){
                    Toast.makeText(this,"请输入公文标题！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(date2.getText().toString().equals("")){
                    Toast.makeText(this,"请输入下发日期！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(department_from_name.equals("")){
                    Toast.makeText(this,"请输入来文单位！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(opIds.equals("")){
                    Toast.makeText(this,"请选择下发单位！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(contnet.getText().toString().equals("")){
                    Toast.makeText(this,"请选择备注内容！",Toast.LENGTH_SHORT).show();
                    return;
                }
//                if(zh_1.getText().toString().equals("") ||
//                        zh_2.getText().toString().equals("") ||
//                        zh_3.getText().toString().equals("") ||
//                        zh_4.getText().toString().equals("") ||
//                        zh_5.getText().toString().equals("") ){
//                    Toast.makeText(this,"请输入完整公文字号！",Toast.LENGTH_SHORT).show();
//                    return;
//                }

                String files_name ="";
                intent =new Intent(this,Documnet_Preview.class);
                intent.putExtra("zh_0",zh_1+
                        zh_2+
                        zh_3+
                        zh_4+
                        zh_5
                );
                intent.putExtra("gwxfZh1",zh_1);
                intent.putExtra("gwxfZh2",zh_2);
                intent.putExtra("gwxfZh3",zh_3);
                intent.putExtra("gwxfZh4",zh_4);
                intent.putExtra("gwxfZh5",zh_5);
                intent.putExtra("webview",return_handdown_comin.getData().getIssuedURL());
                intent.putExtra("gwxfTitle",theme.getText().toString());
                intent.putExtra("opId",getIntent().getStringExtra("opId"));
                intent.putExtra("userId",bean.getData().getOpId());
                intent.putExtra("gwxfLwdwName",department_from.getText().toString());
                intent.putExtra("gwxfLwdwId",department_from_id);
                intent.putExtra("gwxfData",date2.getText().toString());
                intent.putExtra("dopIds_name",dopIds_name);
                intent.putExtra("opIds",opIds);
                intent.putExtra("content",contnet.getText().toString());

                if(return_handdown_comin.getData().getIssuedFileList().size()>0 && return_handdown_comin.getData().getIssuedFileList() != null){

                    for(int i=0 ;i<return_handdown_comin.getData().getIssuedFileList().size(); i++){
                        files_name =files_name + return_handdown_comin.getData().getIssuedFileList().get(i).getPath()+","+
                                return_handdown_comin.getData().getIssuedFileList().get(i).getOpName()+","+return_handdown_comin.getData().getIssuedFileList().get(i).getFilePreview()+",";

                    }

                    if(!files_name.equals("")){

                        if(files_name.substring(files_name.length()-1,files_name.length()).equals(",")){
                            files_name=files_name.substring(0,files_name.length()-1);
                        }
                    }
                }
                intent.putExtra("files_name",files_name);

                startActivityForResult(intent,001);

                break;

            case R.id.process:

                intent = new Intent(this,Process2_Activity.class);
                intent.putExtra("opId",getIntent().getStringExtra("opParent"));
                startActivity(intent);

                break;


            case R.id.send:

                if(theme.getText().toString().equals("")){
                    Toast.makeText(this,"请输入公文标题！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(date2.getText().toString().equals("")){
                    Toast.makeText(this,"请输入下发日期！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(department_from_name.equals("")){
                    Toast.makeText(this,"请输入来文单位！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(opIds.equals("")){
                    Toast.makeText(this,"请选择下发单位！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(contnet.getText().toString().equals("")){
                    Toast.makeText(this,"请选择备注内容！",Toast.LENGTH_SHORT).show();
                    return;
                }
//                if(zh_1.equals("") ||
//                        zh_2.equals("") ||
//                        zh_3.equals("") ||
//                        zh_4.equals("") ||
//                        zh_5.equals("") ){
//                    Toast.makeText(this,"请输入完整公文字号！",Toast.LENGTH_SHORT).show();
//                    return;
//                }

                new DialogUtil(DocuHanddown_Activity.this, new Summit()).showConfirm("是否立即下发该公文？", "您是否确定要将此文件发送给已选择的单位吗？", "立即下发", "继续编辑");

                break;

            case R.id.date_choose:

                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        date2.setText(""+new SimpleDateFormat("yyyy-MM-dd").format(date));
                    }
                })
                        .setTitleText("选择下发时间")
                        .setSubmitColor(Color.parseColor("#00a7e4"))
                        .setCancelColor(Color.parseColor("#00a7e4"))
                        .setTitleBgColor(Color.WHITE)
                        .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                        .setRange(Calendar.getInstance().get(Calendar.YEAR) - 5, Calendar.getInstance().get(Calendar.YEAR) + 5)
                        .setLabel("年","月","日","","","")
                        .build();
                pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
                pvTime.show();


                break;

            case R.id.department_from2:

                intent = new Intent(this,Modules_Type_Activity.class);
                intent.putExtra("module_name",department_from_name);
                intent.putExtra("type","2");
                startActivityForResult(intent,003);
                break;

            case R.id.department_chooseto:

                intent = new Intent(this,Choose_Handdowntree.class);
                intent.putExtra("title","选择下发单位");

               if(list_opIds.size()<1){
                   opIds="";
               }else{
                   opIds="";
                   for(int i=0;i<list_opIds.size();i++){
                       opIds=opIds+list_opIds.get(i)+",";
                   }
                   opIds = opIds.substring(0, opIds.length() - 1);
               }

                intent.putExtra("opIds",opIds);
                startActivityForResult(intent,23);

                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 001){

            opIds=data.getStringExtra("opIds");
            dopIds_name=data.getStringExtra("dopIds_name");
            list_opIds.clear();
            list_departemnt.clear();
            if(!opIds.equals("")){

                String[] opid_list2 = data.getStringExtra("opIds").split(",");
                // 数组转为Arraylist
                ArrayList<String> list3 = new ArrayList<>(Arrays.asList(opid_list2));
                list_opIds.clear();
                for(int i=0;i<list3.size();i++){
                    list_opIds.add(list3.get(i));
                }

                String[] opid_list = data.getStringExtra("dopIds_name").split(",");
                // 数组转为Arraylist
                ArrayList<String> list2 = new ArrayList<>(Arrays.asList(opid_list));
                list_departemnt.clear();
                for(int i=0;i<list2.size();i++){
                    list_departemnt.add(list2.get(i));
                }

                if(list_departemnt.size()<=0){
                    relative.setVisibility(View.GONE);
                    department_to.setVisibility(View.GONE);
                }else{
                    relative.setVisibility(View.VISIBLE);
                    department_to.setVisibility(View.VISIBLE);
                }

                Log.e("到底有没有钱",list_departemnt.size()+"");
                Log.e("到底有没有钱fre",list_opIds.size()+"");
//            if(list_departemnt.get(0).equals("")){
//                list_departemnt.remove(0);
//            }
                adapter_addtion2= new Adapter_Addtion2();
                department_to.setAdapter(adapter_addtion2);
                adapter_addtion2.notifyDataSetChanged();
            }else{
                relative.setVisibility(View.GONE);
                department_to.setVisibility(View.VISIBLE);
                Log.e("到底有没有钱fre","没数据");

            }


        }else if(resultCode == 004){
            department_from_name=data.getStringExtra("module");
            department_from_id=data.getStringExtra("module_id");
            department_from.setText(data.getStringExtra("module"));
            department_from.setTextColor(getResources().getColor(R.color.color_23));
            department_from.setTextSize(16);
        }else if(resultCode == 005){

            Intent intent= new Intent();
            setResult(520,intent);
            finish();

        }


    }

    // 附件的adapter
    class Adapter_Addtion2 extends RecyclerView.Adapter<Adapter_Addtion2.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion2() {

        }

        @Override
        public Adapter_Addtion2.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = LayoutInflater.from(DocuHanddown_Activity.this).inflate(R.layout.addition_files, parent, false);

            return new Adapter_Addtion2.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Addtion2.MyViewHolder holder, final int position) {

            holder.addtion.setVisibility(View.GONE);
            holder.addtional.setText(list_departemnt.get(position));
            holder.remove_fj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list_departemnt.remove(position);
                    list_opIds.remove(position);
                    adapter_addtion2.notifyDataSetChanged();
                    dopIds_name="";
                    opIds="";
                    if(list_departemnt.size()>0){
                        for(int i=0;i<list_departemnt.size();i++){
                            dopIds_name =dopIds_name+list_departemnt.get(i)+",";
                            opIds =opIds+list_opIds.get(i)+",";
                        }
                        dopIds_name = dopIds_name.substring(0, dopIds_name.length() - 1);
                        opIds = opIds.substring(0, opIds.length() - 1);
                        Log.e("实打实",list_departemnt.size()+"");
                    }

                    Log.e("钱",list_departemnt.size()+"");
                    Log.e("钱fre",list_opIds.size()+"");
                    Log.e("钱fre",opIds);
                    Log.e("钱fre",dopIds_name);



                }
            });


//            if (list_departemnt.size() - 1 == position) {
//                holder.line.setVisibility(View.GONE);
//            } else {
//                holder.line.setVisibility(View.VISIBLE);
//            }


        }

        @Override
        public int getItemCount() {

            if(list_departemnt == null || list_departemnt.size()==0){
                to_departments.setText("点击右侧选择下发单位");
                return 0;
            }
            to_departments.setText("");
            return list_departemnt.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView addtion;
            LinearLayout header;
            TextView addtional;
            ImageView remove_fj;
            View line;

            public MyViewHolder(View itemView) {
                super(itemView);
                addtion = (TextView) itemView.findViewById(R.id.addtion);
                header = (LinearLayout) itemView.findViewById(R.id.header);
                addtional = (TextView) itemView.findViewById(R.id.addtional);
                remove_fj = (ImageView) itemView.findViewById(R.id.remove_fj);
                line = itemView.findViewById(R.id.line);
            }
        }
    }

    // 附件的adapter
    class Adapter_Addtion extends RecyclerView.Adapter<Adapter_Addtion.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion() {

        }

        @Override
        public Adapter_Addtion.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.addtion_layout, parent, false);

            return new Adapter_Addtion.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.addtion.setText("附件" + (position + 1) + " : ");
            holder.addtional.setText(fileLists.get(position).getOpName());

            if (fileLists.size() - 1 == position) {
                holder.line.setVisibility(View.GONE);
            } else {
                holder.line.setVisibility(View.VISIBLE);
            }

            // item的点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    File f = new File(fileLists.get(position).getOpName());
                    String fileName = f.getName();
                    String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);

                    // 判断类型，选择是在线预览还是直接下载
                    if (prefix.equals("docx") || prefix.equals("doc") || prefix.equals("xlsx") || prefix.equals("xls") || prefix.equals("jpg") || prefix.equals("png") || prefix.equals("gif") || prefix.equals("pdf")) {

                        Intent intent = new Intent(DocuHanddown_Activity.this, DetailsFJActivity.class);
                        intent.putExtra("filePreview", fileLists.get(position).getFilePreview());
                        intent.putExtra("type", "1");
                        startActivity(intent);

                    } else {

                        // 记录点击下载的位置，以便下载是拿到正确下载网址
//                        position_download = position;
                        // 弹出dailog并监听summit是监听的回掉
                        new DialogUtil(DocuHanddown_Activity.this, new Summit()).showConfirm("下载提示", "确定要下载到本地嘛？", "确定", "不用了");
                    }


                }
            });
        }


        @Override
        public int getItemCount() {

            if(fileLists == null){
                return 0;
            }
            return fileLists.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView addtion;
            TextView addtional;
            View line;

            public MyViewHolder(View itemView) {

                super(itemView);
                addtion = (TextView) itemView.findViewById(R.id.addtion);
                addtional = (TextView) itemView.findViewById(R.id.addtional);
                line = itemView.findViewById(R.id.line);
            }
        }
    }



    // 下载弹出框点击的回掉方法体
    class Summit implements DialogUtil.OnClickListenner {

        public Summit() {

        }

        @Override
        public void yesClick() {


            popupWindowUtil = new PopupWindowUtil(DocuHanddown_Activity.this, "提交中...");
            popupWindowUtil.show();

            Call<JsonObject> call=api.summit_handdown(getIntent().getStringExtra("opId"),bean.getData().getOpId(),theme.getText().toString(),
                    date2.getText().toString(),department_from_name,department_from_id,opIds,contnet.getText().toString(),
                    zh_1,zh_2,zh_3,zh_4,zh_5);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if(response.body() != null && !response.body().equals("")){

                        if(response.body().get("result").getAsBoolean()){
                            new DialogUtil(DocuHanddown_Activity.this, new Summit()).showAlert("该公文已下发！", "公文已下发，正在等待相关单位办理！", "知道了");

                        }else{

                            Toast.makeText(DocuHanddown_Activity.this, "下发失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                    popupWindowUtil.dismiss();
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    popupWindowUtil.dismiss();
                    Toast.makeText(DocuHanddown_Activity.this, "网络连接有误！", Toast.LENGTH_SHORT).show();

                }
            });
        }

        @Override
        public void noClick() {
        }

        @Override
        public void onSingleClick() {
            Intent intent= new Intent();
            setResult(520,intent);
            Log.e("执行了没，欸有","的方式感到十分");
            finish();
        }
    }






    public static class FileList{
        @Override
        public String toString() {
            return "FileList{" +
                    "path='" + path + '\'' +
                    ", opName='" + opName + '\'' +
                    '}';
        }

        String path;
        String opName;
        String filePreview;

        public String getFilePreview() {
            return filePreview;
        }

        public void setFilePreview(String filePreview) {
            this.filePreview = filePreview;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getOpName() {
            return opName;
        }

        public void setOpName(String opName) {
            this.opName = opName;
        }

        public FileList(String path, String opName) {
            this.path = path;
            this.opName = opName;
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


}
