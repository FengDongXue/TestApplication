package com.lanwei.governmentstar.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
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
import com.icarus.entity.Image;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.gwxf.DocuHanddown_Activity;
import com.lanwei.governmentstar.activity.lll.DocumentBaseCActivity;
import com.lanwei.governmentstar.activity.spsq.EnterSealApplyActivity;
import com.lanwei.governmentstar.activity.zyx.DetailsFJActivity;
import com.lanwei.governmentstar.bean.Bean_Reply_Add;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Result_Message;
import com.lanwei.governmentstar.bean.Return_Handdown_Comin;
import com.lanwei.governmentstar.bean.Signup;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.ManagerUtils;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.MyScrollView_Focus;
import com.lanwei.governmentstar.view.StatusBarUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.nio.channels.CancelledKeyException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Query;


/**
 * Created by 蓝威科技-技术开发1 on 2017/10/12.
 */

public class SignUp_Document_Activity extends BaseActivity implements View.OnClickListener , MyScrollView_Focus.MyScrollListener, DialogUtil.OnClickListenner {

    private ImageView back;
    private ImageView right2;
    private ImageView right1;
    private ImageView document_guild_k;
    private TextView title;
    private TextView sign;
    private TextView theme;
    private TextView number;
    private LinearLayout gongwen_zihao;
    private TextView gongwenzihao;
    private TextView department_from;
    private TextView date_receive;
    private TextView urgent;
    private String urgent_id ="1";
    private TextView category;
    private TextView deadline;
    private TextView addtiona2;
    private TextView addtion_content;
    private EditText feedback;
    private EditText summary_content;
    private EditText addition_content;
    private LinearLayout department_from2;
    private LinearLayout gwqs1;
    private LinearLayout gwqs2;
    private LinearLayout gwqs4;
    private RelativeLayout gwqs5;
    private RelativeLayout gwqs6;
    private RelativeLayout bottom;
    private LinearLayout gwqs7;
    private LinearLayout document_fujian;
    private LinearLayout gwcy1;
//    private RelativeLayout gwcy2;
    private List<Signup.DataBean.FileListBean> fileLists;
    private FloatingActionButton enshrink;
    private FloatingActionButton enlarge;
    private MyScrollView_Focus myScrollView_focus;
    private WebView webview;
    private RecyclerView recycler_view;
    private Adapter_Addtion adapter_addtion;
    private Logging_Success bean;
    private GovernmentApi api;

    private WheelView wheellistview1;
    private WheelView wheellistview2;
    private WheelView wheellistview3;
    private WheelView wheellistview4;
    private WheelView wheellistview5;
    private PopupWindow popupWindow;
    private String zh_1 ="";
    private String zh_2 ="";
    private String zh_3 ="";
    private String zh_4 ="";
    private String zh_5 ="";

    private String type="";
    private String department_from_name = "";
    private String department_from_id = "";
    private String category_name = "";
    private String category_id = "";
    private Signup signup = new Signup();
    private PopupWindowUtil popupWindowUtil;

    private View inlook_line1;
//    private View inlook_line2;
    private View inlook_line3;
    private View inlook_line4;
    private View inlook_line5;
    private View inlook_line6;
    private View inlook_line7;

    private Adapter_Document popAdapter;
    private PopupWindow popupWindow2;

    Map<String, String> outMap= new HashMap();
    private ArrayList<String> list_key =new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_qianshou_activity);
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
        intiweights();
        if(getIntent().getStringExtra("type").equals("gwcy")){
//            gwcy2.setVisibility(View.GONE);
//            inlook_line2.setVisibility(View.GONE);
            gongwen_zihao.setOnClickListener(this);
            department_from2.setOnClickListener(this);
            gongwen_zihao.setOnClickListener(this);
            gwqs7.setOnClickListener(this);
            bottom.setVisibility(View.GONE);
        }else{
            gwqs1.setVisibility(View.GONE);
            inlook_line3.setVisibility(View.GONE);
            gwqs2.setVisibility(View.GONE);
            inlook_line4.setVisibility(View.GONE);
            gwqs4.setVisibility(View.GONE);
            inlook_line5.setVisibility(View.GONE);
            gwqs5.setVisibility(View.GONE);
            inlook_line6.setVisibility(View.GONE);
            gwqs6.setVisibility(View.GONE);
            inlook_line7.setVisibility(View.GONE);


            bottom.setVisibility(View.VISIBLE);
            addtiona2.setVisibility(View.GONE);
            right1.setVisibility(View.INVISIBLE);
            right2.setVisibility(View.INVISIBLE);
        }

        // 获取bean;
        String defString = PreferencesManager.getInstance(SignUp_Document_Activity.this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);
        api= HttpClient.getInstance().getGovernmentApi();
        Call<Signup> call= api.sydwQsxx(bean.getData().getOpId(),getIntent().getStringExtra("opId"));
        call.enqueue(new Callback<Signup>() {
            @Override
            public void onResponse(Call<Signup> call, Response<Signup> response) {

                signup = response.body();
                theme.setText(signup.getData().getDocTitle());
                number.setText(signup.getData().getDocCode());

                addtion_content.setText(signup.getData().getDocRemark());
                if(!addtion_content.equals("")){
                    addtion_content.setTextColor(getResources().getColor(R.color.color_23));
                    addtion_content.setTextSize(16);
                }
                urgent.setText(signup.getData().getDocUrgent());
                if(!urgent.equals("")){
                    urgent.setTextColor(getResources().getColor(R.color.color_23));
                    urgent.setTextSize(16);
                }

                SimpleDateFormat  sDateFormat =  new SimpleDateFormat("yyyy-MM-dd");
                String date = sDateFormat.format(new java.util.Date());
                date_receive.setText(date);
                date_receive.setTextColor(getResources().getColor(R.color.color_23));
                department_from_name = signup.getData().getDocUnit();
                department_from.setText(signup.getData().getDocUnit());
                if(!department_from.equals("")){
                    department_from.setTextColor(getResources().getColor(R.color.color_23));
                    department_from.setTextSize(16);
                }else{
                    if(!getIntent().getStringExtra("type").equals("gwcy")){
                        department_from.setText("");
                    }

                }
                zh_1=signup.getData().getGwzh1();
                zh_2=signup.getData().getGwzh2();
                zh_3=signup.getData().getGwzh3();
                zh_4=signup.getData().getGwzh4();
                zh_5=signup.getData().getGwzh5();

                if(!zh_2.equals("")){
                    gongwenzihao.setTextColor(getResources().getColor(R.color.color_23));
                    gongwenzihao.setTextSize(16);
                    gongwenzihao.setText(signup.getData().getGwzh1()+signup.getData().getGwzh2()+signup.getData().getGwzh3()+signup.getData().getGwzh4()+signup.getData().getGwzh5()+"号");
                }else{
                    if(!getIntent().getStringExtra("type").equals("gwcy")){
                        gongwenzihao.setHint("");
                    }
                }

                webview.loadUrl(signup.getData().getDocUrl());

                if(signup.getData().getFileList() != null && signup.getData().getFileList().size()>0){
                    fileLists = signup.getData().getFileList();
                    adapter_addtion= new Adapter_Addtion();
                    recycler_view.setAdapter(adapter_addtion);
                }else{
                    document_fujian.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Signup> call, Throwable t) {
                Toast.makeText(SignUp_Document_Activity.this, "网络连接有误", Toast.LENGTH_SHORT).show();

            }
        });


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
    void intiweights(){

        webview =  (WebView) findViewById(R.id.webview);
        back =  (ImageView) findViewById(R.id.back);
        document_guild_k =  (ImageView) findViewById(R.id.document_guild_k);
        right1 =  (ImageView) findViewById(R.id.right1);
        right2 =  (ImageView) findViewById(R.id.right2);
        title =  (TextView) findViewById(R.id.title);
        sign =  (TextView) findViewById(R.id.sign);
        deadline =  (TextView) findViewById(R.id.deadline);
        addtiona2 =  (TextView) findViewById(R.id.addtiona2);
        theme =  (TextView) findViewById(R.id.theme);
        number =  (TextView) findViewById(R.id.number);
        gongwenzihao =  (TextView) findViewById(R.id.gongwenzihao);
        gongwen_zihao =  (LinearLayout) findViewById(R.id.gongwen_zihao);
        department_from =  (TextView) findViewById(R.id.department_from);
        date_receive =  (TextView) findViewById(R.id.date_receive);
        urgent =  (TextView) findViewById(R.id.urgent);
        category =  (TextView) findViewById(R.id.category);
        addtion_content =  (TextView) findViewById(R.id.addtion_content);
        feedback =  (EditText) findViewById(R.id.feedback);
        summary_content =  (EditText) findViewById(R.id.summary_content);
        addition_content =  (EditText) findViewById(R.id.addition_content);


        inlook_line1 =   findViewById(R.id.inlook_line1);
//        inlook_line2 =   findViewById(R.id.inlook_line2);
        inlook_line3 =   findViewById(R.id.inlook_line3);
        inlook_line4 =   findViewById(R.id.inlook_line4);
        inlook_line5 =   findViewById(R.id.inlook_line5);
        inlook_line6 =   findViewById(R.id.inlook_line6);
        inlook_line7 =   findViewById(R.id.inlook_line7);

        department_from2 =  (LinearLayout) findViewById(R.id.department_from2);
        document_fujian =  (LinearLayout) findViewById(R.id.document_fujian);
        gwqs1 =  (LinearLayout) findViewById(R.id.gwqs1);
        gwcy1 =  (LinearLayout) findViewById(R.id.gwcy1);
        gwqs2 =  (LinearLayout) findViewById(R.id.gwqs2);
        gwqs4 =  (LinearLayout) findViewById(R.id.gwqs4);
//        gwcy2 =  (RelativeLayout) findViewById(R.id.gwcy2);
        gwqs5 =  (RelativeLayout) findViewById(R.id.gwqs5);
        gwqs6 =  (RelativeLayout) findViewById(R.id.gwqs6);
        gwqs7 =  (LinearLayout) findViewById(R.id.gwqs7);
        recycler_view =  (RecyclerView) findViewById(R.id.recycler_view);
        bottom =  (RelativeLayout) findViewById(R.id.bottom);

        initWebView();

        // 为RecyclerView设置默认动画和线性布局管理器
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        recycler_view.setLayoutManager(new LinearLayoutManager(this){

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        adapter_addtion = new Adapter_Addtion();

        enshrink = (FloatingActionButton) findViewById(R.id.enshrink);
        enlarge = (FloatingActionButton) findViewById(R.id.enlarge);
        myScrollView_focus = (MyScrollView_Focus) findViewById(R.id.document_scrollView);
        myScrollView_focus.setMyScrollListener(this);
        enlarge.setOnClickListener(this);
        enshrink.setOnClickListener(this);
        sign.setOnClickListener(this);
        gwqs4.setOnClickListener(this);
        gwqs2.setOnClickListener(this);
        back.setOnClickListener(this);
        gwqs1.setOnClickListener(this);
//        document_guild_k.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {


        Intent intent;


        switch (v.getId()){

            case R.id.back:
                finish();
                break;
            case R.id.document_guild_k:

                // 设置屏幕的透明度
                WindowManager.LayoutParams lp2 = getWindow().getAttributes();
                lp2.alpha = (float) 0.8;
                getWindow().setAttributes(lp2);
                // 加载popupwindow的布局
                final View view2 = getLayoutInflater().inflate(R.layout.popwindow_sy3, null);
                final RecyclerView listView = (RecyclerView) view2.findViewById(R.id.popwindow_sy3_list);
                final ArrayList<Map<String,String>> popList = new ArrayList<Map<String,String>>();
                view2.findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(list_key.size()>7){
                            Toast.makeText(SignUp_Document_Activity.this,"快捷回复不能超过8条",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!((EditText)view2.findViewById(R.id.add_content)).getText().toString().equals("")){

                            Call<Bean_Reply_Add> call = api.setQuickReply(bean.getData().getOpId(),"收文归档",((EditText)view2.findViewById(R.id.add_content)).getText().toString());

                            call.enqueue(new Callback<Bean_Reply_Add>() {
                                @Override
                                public void onResponse(Call<Bean_Reply_Add> call, Response<Bean_Reply_Add> response) {

                                    if(response.body().getResult()){
                                        list_key.add(response.body().getOpId());
                                        outMap.put(response.body().getOpId(),((EditText)view2.findViewById(R.id.add_content)).getText().toString());
                                        ((EditText) view2.findViewById(R.id.add_content)).setText("");
                                        Toast.makeText(SignUp_Document_Activity.this,"添加成功！",Toast.LENGTH_SHORT).show();
                                        popAdapter.notifyDataSetChanged();

                                    }else{
                                        Toast.makeText(SignUp_Document_Activity.this,"添加失败！",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Bean_Reply_Add> call, Throwable t) {
                                    Toast.makeText(SignUp_Document_Activity.this,"网络连接有误！",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else{

                            Toast.makeText(SignUp_Document_Activity.this,"快捷内容不能为空",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                // 为RecyclerView设置默认动画和线性布局管理器
                listView.setItemAnimator(new DefaultItemAnimator());
                //设置线性布局
                listView.setLayoutManager(new LinearLayoutManager(this));

                popAdapter = new Adapter_Document();
                listView.setAdapter(popAdapter);
                popupWindow2 = new PopupWindow(view2, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                // 点击屏幕之外的区域可否让popupwindow消失
                popupWindow2.setFocusable(true);
                popupWindow2.setBackgroundDrawable(new BitmapDrawable());
                popupWindow2.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams p = getWindow().getAttributes();
                        p.alpha = (float) 1;
                        getWindow().setAttributes(p);
                    }
                });
                int xy[] = new int[2];
                v.getLocationOnScreen(xy);
                // 设置popupwindow的显示位置
                popupWindow2.showAtLocation(v, Gravity.BOTTOM, 0, 0);



                break;
            case R.id.gwqs2:
                type="urgent";
                deadline();
                break;
            case R.id.department_from2:

                intent = new Intent(this,Modules_Type_Activity.class);
                intent.putExtra("module_name",department_from_name);
                intent.putExtra("type","3");
                startActivityForResult(intent,003);

                break;
            case R.id.gwqs4:

                intent = new Intent(this,Modules_Type_Activity.class);
                intent.putExtra("module_name",category_name);
                intent.putExtra("type","4");
                startActivityForResult(intent,004);

                break;
            case R.id.gwqs1:

                //时间选择器
                TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        date_receive.setText(""+new SimpleDateFormat("yyyy-MM-dd").format(date));
                        date_receive.setTextColor(getResources().getColor(R.color.color_23));
                        date_receive.setTextSize(16);
                    }
                })
                        .setTitleText("更改收文时间")
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

            case R.id.gwqs7:
                type="deadline";
                deadline();
                break;
            case R.id.gongwen_zihao:
                zihao();
                break;
            case R.id.sign:

                if(getIntent().getStringExtra("type").equals("gwcy")){

                    if(summary_content.getText().toString().trim().equals("")){
                        Toast.makeText(SignUp_Document_Activity.this,"请输入文件摘要",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(category.getText().toString().trim().equals("选择所属类别")){
                        Toast.makeText(SignUp_Document_Activity.this,"请选择公文类别",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(addition_content.getText().toString().trim().equals("")){
                        Toast.makeText(SignUp_Document_Activity.this,"请输入备注信息",Toast.LENGTH_SHORT).show();
                        return;
                    }

                }else{
                    if(feedback.getText().toString().trim().equals("")){
                        Toast.makeText(SignUp_Document_Activity.this,"请填写签收反馈",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                new DialogUtil(SignUp_Document_Activity.this, this).showConfirm("提交签收", "您确定要签收该文件吗？", "确定", "取消");
                break;
            case R.id.enshrink:
                webview.loadUrl("javascript:narrow()");
                break;
            case R.id.enlarge:
                webview.loadUrl("javascript:amplify()");
                break;

        }
    }

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


    class Adapter_Document extends RecyclerView.Adapter<Adapter_Document.MyViewHolder> {

        private View view = null;

        public Adapter_Document() {}

        @Override
        public Adapter_Document.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.adapter_approve_item, parent,false);

            return new Adapter_Document.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Document.MyViewHolder holder, final int position) {

            holder.adapter_approve_item_text.setText(outMap.get(list_key.get(position)));
            holder.adapter_approve_item_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow2.dismiss();
                    feedback.setText(outMap.get(list_key.get(position)));
                    feedback.setSelection(feedback.getText().length());
                }
            });
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Call<Result_Message> call =api.delQuickReply(list_key.get(position));
                    call.enqueue(new Callback<Result_Message>() {
                        @Override
                        public void onResponse(Call<Result_Message> call, Response<Result_Message> response) {

                            if(response.body().getResult()){
                                outMap.remove(list_key.get(position));
                                list_key.remove(position);
                                popAdapter.notifyDataSetChanged();
                                Toast.makeText(SignUp_Document_Activity.this,"删除成功！",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(SignUp_Document_Activity.this,"删除失败！",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Result_Message> call, Throwable t) {
                            Toast.makeText(SignUp_Document_Activity.this,"网络连接有误！",Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            });
        }

        @Override
        public int getItemCount() {
            return list_key.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView adapter_approve_item_text;
            private ImageView remove;

            public MyViewHolder(View itemView) {

                super(itemView);
                adapter_approve_item_text = (TextView) itemView.findViewById(R.id.adapter_approve_item_text);
                remove = (ImageView) itemView.findViewById(R.id.remove);

            }
        }
    }

    @Override
    public void yesClick() {

        popupWindowUtil = new PopupWindowUtil(SignUp_Document_Activity.this, "提交中...");
        popupWindowUtil.show();

        if(getIntent().getStringExtra("type").equals("gwcy")){
//                    String userId ,String opId , String wjzy, String wjbz,String lwdw, String jjcd, String wjfl, String clqx,String gwzh1, String gwzh2, String gwzh3 , String gwzh4 , String gwzh5
            Call<JsonObject> call = api.zfjgQs(bean.getData().getOpId(),getIntent().getStringExtra("opId"),summary_content.getText().toString().trim(),addition_content.getText().toString().trim(),department_from.getText().toString().trim(),urgent.getText().toString().trim(),category_id,deadline.getText().toString().trim(),zh_1,zh_2,zh_3,zh_4,zh_5);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    try {
                        JSONObject jsonObject=new JSONObject(response.body().toString());
                        if(jsonObject.getString("result").equals("true")){
                            if(popupWindowUtil != null){
                                popupWindowUtil.dismiss();
                            }
                            Toast.makeText(SignUp_Document_Activity.this,"签收成功",Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent();
                            intent.putExtra("opId",getIntent().getStringExtra("opId"));
                            intent.putExtra("opState","1");
                            intent.putExtra("docStatus","");
                            setResult(520,intent);
                            finish();

                        }else{
                            if(popupWindowUtil != null){
                                popupWindowUtil.dismiss();
                            }
                            Toast.makeText(SignUp_Document_Activity.this,"签收失败",Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        if(popupWindowUtil != null){
                            popupWindowUtil.dismiss();
                        }
                        Toast.makeText(SignUp_Document_Activity.this,"签收失败",Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(SignUp_Document_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Call<JsonObject> call = api.sydwQs(bean.getData().getOpId(),getIntent().getStringExtra("opId"),addtion_content.getText().toString().trim());

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    try {
                        JSONObject jsonObject=new JSONObject(response.body().toString());
                        if(jsonObject.getString("result").equals("true")){
                            if(popupWindowUtil != null){
                                popupWindowUtil.dismiss();
                            }
                            Toast.makeText(SignUp_Document_Activity.this,"签收成功",Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent();
                            intent.putExtra("opId",getIntent().getStringExtra("opId"));
                            intent.putExtra("opState","-2");
                            intent.putExtra("docStatus","");
                            setResult(520,intent);
                            finish();

                        }else{
                            if(popupWindowUtil != null){
                                popupWindowUtil.dismiss();
                            }
                            Toast.makeText(SignUp_Document_Activity.this,"签收失败",Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        if(popupWindowUtil != null){
                            popupWindowUtil.dismiss();
                        }
                        Toast.makeText(SignUp_Document_Activity.this,"签收失败",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(SignUp_Document_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();
                }
            });


        }

    }

    @Override
    public void noClick() {

    }

    @Override
    public void onSingleClick() {

    }


    // 附件的adapter
    class Adapter_Addtion extends RecyclerView.Adapter<Adapter_Addtion.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion() {}

        @Override
        public Adapter_Addtion.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.addtion_layout, parent, false);

            return new Adapter_Addtion.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final Adapter_Addtion.MyViewHolder holder, final int position) {

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

                        Intent intent = new Intent(SignUp_Document_Activity.this, DetailsFJActivity.class);
                        intent.putExtra("filePreview", fileLists.get(position).getFilePreview());
                        intent.putExtra("type", "1");
                        startActivity(intent);

                    } else {

                        // 记录点击下载的位置，以便下载是拿到正确下载网址
//                        position_download = position;
                        // 弹出dailog并监听summit是监听的回掉
                        new DialogUtil(SignUp_Document_Activity.this, new Summit()).showConfirm("下载提示", "确定要下载到本地嘛？", "确定", "不用了");
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode== 003  && resultCode == 004){
            department_from_name=data.getStringExtra("module");
            department_from_id=data.getStringExtra("module_id");
            department_from.setText(data.getStringExtra("module"));
            department_from.setTextColor(getResources().getColor(R.color.color_23));
            department_from.setTextSize(16);
        }else if( requestCode== 004  && resultCode == 004){
            category_name=data.getStringExtra("module");
            category_id=data.getStringExtra("module_id");
            category.setText(data.getStringExtra("module"));
            category.setTextColor(getResources().getColor(R.color.color_23));
            category.setTextSize(16);

        }


    }

    // 下载弹出框点击的回掉方法体
    class Summit implements DialogUtil.OnClickListenner {

        public Summit() {

        }

        @Override
        public void yesClick() {


        }

        @Override
        public void noClick() {
        }

        @Override
        public void onSingleClick() {

            //Log.d("TAG",jsonObject.toString());
            Intent in = new Intent();
            in.putExtra("opId", getIntent().getStringExtra("opId"));
             in.putExtra("opState", "1");
             in.putExtra("docStatus", "");
            setResult(520, in);

            finish();

        }
    }

    void zihao(){

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

        View rootview = LayoutInflater.from(this).inflate(R.layout.layout_qianshou_activity, null);
        // 设置popupwindow的显示位置
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM,0,0);

    }

    void deadline(){

        // 弹出popupwindow前，调暗屏幕的透明度
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha=(float) 0.8;
        getWindow().setAttributes(lp);

        // 加载popupwindow的布局
        View view=getLayoutInflater().inflate(R.layout.layout_deadline,null ,false);
        popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        final ArrayList<String> list1 =new ArrayList<>();

        // 初始化popupwindow的点击控件
        wheellistview1 = (com.bigkoo.pickerview.lib.WheelView) view.findViewById(R.id.wheellistview1);


        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView identify = (TextView) view.findViewById(R.id.identify);
        TextView title = (TextView) view.findViewById(R.id.title);
        if(!type.equals("deadline")){
            title.setText("选择紧急度");
        }else{
            title.setText("选择处理期限");
        }

        identify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();

                if(type.equals("deadline")){
                    deadline.setTextColor(getResources().getColor(R.color.color_23));
                    deadline.setText(list1.get(wheellistview1.getCurrentItem()));
                }else{
                    urgent.setText(list1.get(wheellistview1.getCurrentItem()));
                    urgent.setTextColor(getResources().getColor(R.color.color_23));

                    if(list1.get(wheellistview1.getCurrentItem()).equals("一般公文")){
                        urgent_id = "0";
                    }else{
                        urgent_id = "1";
                    }

                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        int position = 0;

        if(type.equals("deadline")){

            list1.add("无限");
            for(int i=1 ; i<100 ;i++){
                list1.add(i+"");
                if((i+"").equals(deadline.getText().toString().trim())){
                    position = i;
                }
            }
        }else{
            list1.add("一般公文");
            list1.add("紧急公文");

            if(deadline.getText().toString().trim().equals("一般公文")){
                position = 0;
            }else{
                position = 1;
            }

        }


        wheellistview1.setAdapter(new ArrayWheelAdapter(list1));
        wheellistview1.setCurrentItem(position);
        wheellistview1.setCyclic(false);
        wheellistview1.setTextSize(16);

        // 点击屏幕之外的区域可否让popupwindow消失
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PoponDismissListener());

        View rootview = LayoutInflater.from(this).inflate(R.layout.layout_qianshou_activity, null);
        // 设置popupwindow的显示位置
        popupWindow.showAtLocation(rootview, Gravity.BOTTOM,0,0);

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
