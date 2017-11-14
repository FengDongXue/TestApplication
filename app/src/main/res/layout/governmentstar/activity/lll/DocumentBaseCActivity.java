package com.lanwei.governmentstar.activity.lll;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.CenterActivity;
import com.lanwei.governmentstar.activity.Convey_Files_Activity;
import com.lanwei.governmentstar.activity.Details_Document_Activity;
import com.lanwei.governmentstar.activity.MyDocument_Acvity;
import com.lanwei.governmentstar.activity.gwnz.DocumentBaseActivity;
import com.lanwei.governmentstar.activity.gwnz.DocumentHQActivity;
import com.lanwei.governmentstar.activity.zyx.DetailsFJActivity;
import com.lanwei.governmentstar.bean.Bean_QuickReply;
import com.lanwei.governmentstar.bean.Bean_Reply_Add;
import com.lanwei.governmentstar.bean.DocumentBaseC;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.MyDocument;
import com.lanwei.governmentstar.bean.Result_Message;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.receiver.DownloadCompleteReceiver;
import com.lanwei.governmentstar.receiver.MyReceiver;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.DownloadUtil;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.LogUtils;

import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.CircleImageView;
import com.lanwei.governmentstar.view.CustomWebView;
import com.lanwei.governmentstar.view.DragImageView;
import com.lanwei.governmentstar.view.FullyLinearLayoutManager;
import com.lanwei.governmentstar.view.MyListView;
import com.lanwei.governmentstar.view.MyScrollView_Focus;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.lanwei.governmentstar.view.WpsModel;
import com.lanwei.governmentstar.view.WrapContentLinearLayoutManager;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.utils.AutoUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//import uk.co.senab.photoview.PhotoView;
//import uk.co.senab.photoview.PhotoViewAttacher;


/**
 *
 */
//收文传阅
@SuppressLint("NewApi")
public abstract class DocumentBaseCActivity extends BaseActivity implements OnClickListener ,MyScrollView_Focus.MyScrollListener{
    private static final String TAG = DocumentBaseCActivity.class.getSimpleName();
    protected TextView document_qicao, document_qicaotime, document_gongwen, document_huiqian, more, title;
    protected MyListView document_shenpi;
    protected DocumentAdapter documentAdapter;
    protected List<DocumentBaseC.FlowList> list;
    protected android.webkit.WebView webView;
    protected ImageView back, document_guild_k;
    protected RecyclerView rv;
    protected ArrayList<String> data_file = null;
    protected Adapter_Addtion adapter;
    protected LinearLayout linearLayout, document_guild_layout,document_file_selectpeople;
    protected MyScrollView_Focus document_scrollView;
    protected EditText document_guild_edit;
    protected TextView type_receive;
    protected String opIds="";
    protected String opXbrId="";
    protected LinearLayout document_fujian;
    protected LinearLayout document_file_look;
    protected LinearLayout type;
    protected String uid, did;
    protected View title_line;
    protected String opNbrId = "";
    protected String opNbrId_2 = "";
    protected String opYbrId = "";
    protected String opBsrId = "";
    protected String opNames = "";
    protected String nbrId = "";
    protected String opType = "";
    protected int bsrNum =2;
    protected PopupWindow popupWindow;
    protected PopupWindowUtil popupWindowUtil;
    DocumentBaseC documentBaseC;
    DownloadManager dm;
    DownloadCompleteReceiver downloadReceiver;
    String path_addtion;
    private LinearLayout condition;
    private String present;
    protected JSONObject jsonObject;
    private int position_download;
    protected int isNotChoosed = 0;
    int webviewContentWidth;
    protected String isChoosed = "";
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private RecyclerView imame_list;
    private List<DocumentBaseC.FileList> ImageList;
    private Adapter_Image adapter_image;
    private FloatingActionButton enlarge;
    private FloatingActionButton enshrink;
    private TextView document_title;
    private TextView lwdw;
    private TextView jjrq;
    private TextView gwzt;
    private TextView jjd;
    private List<DocumentBaseC.FileList> fileLists;
    protected int isNotAssist = 0 ;
    Logging_Success bean;
    Map<String, String> outMap= new HashMap();
    private ArrayList<String> list_key =new ArrayList<>();
    private GovernmentApi api;
    private Adapter_Document popAdapter;
    private PopupWindow popupWindow2;

    abstract protected int getLayoutResId();

    abstract protected String getAction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate()");
        setContentView(getLayoutResId());

        // 判断系统SDK版本，看看是否支持沉浸式
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        init();
    }

    // 初始化基类中公共的控件
    private void init() {
        // TODO Auto-generated method stub
        more = (TextView) findViewById(R.id.more);
        more.setVisibility(View.VISIBLE);
        title = (TextView) findViewById(R.id.title);
        back = (ImageView) findViewById(R.id.back);
        document_scrollView = (MyScrollView_Focus) findViewById(R.id.document_scrollView);

        document_scrollView.setMyScrollListener(this);
        lwdw = (TextView) findViewById(R.id.lwdw);
        jjrq = (TextView) findViewById(R.id.jjrq);
        gwzt = (TextView) findViewById(R.id.gwzt);
        jjd = (TextView) findViewById(R.id.jjd);
        String defString = PreferencesManager.getInstance(this, "accountBean").get("jsonStr");
        Gson gson = new Gson();
        bean = gson.fromJson(defString, Logging_Success.class);

        document_qicao = (TextView) findViewById(R.id.document_qicao);
        document_title = (TextView) findViewById(R.id.document_title);
        document_title.setVisibility(View.VISIBLE);
//        document_shenhe = (TextView) findViewById(R.id.document_shenhe);
        document_qicaotime = (TextView) findViewById(R.id.document_qicaotime);
        document_gongwen = (TextView) findViewById(R.id.document_gongwen);
//        document_hongguan = (TextView) findViewById(R.id.document_hongguan);
        document_huiqian = (TextView) findViewById(R.id.document_huiqian);
        document_shenpi = (MyListView) findViewById(R.id.document_shenpi);
        document_fujian = (LinearLayout) findViewById(R.id.document_fujian);
        type = (LinearLayout) findViewById(R.id.type);
        imame_list = (RecyclerView) findViewById(R.id.image_list);
        enlarge = (FloatingActionButton) findViewById(R.id.enlarge);
        enshrink = (FloatingActionButton) findViewById(R.id.enshrink);

        enlarge.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        enshrink.setBackgroundTintList(getResources().getColorStateList(R.color.white));

        webView = (android.webkit.WebView) findViewById(R.id.document_webview);
        rv = (RecyclerView) findViewById(R.id.rv);
        document_guild_layout = (LinearLayout) findViewById(R.id.document_guild_layout);
        document_guild_edit = (EditText) findViewById(R.id.document_guild_edit);
        type_receive = (TextView) findViewById(R.id.type_receive);
        document_guild_k = (ImageView) findViewById(R.id.document_guild_k);
        linearLayout = (LinearLayout) findViewById(R.id.document_linear);
        document_shenpi.setFocusable(false);
        imame_list.setFocusable(false);

        document_guild_k.setOnClickListener(this);
        enshrink.setOnClickListener(this);
        enlarge.setOnClickListener(this);

        more.setOnClickListener(this);
        back.setOnClickListener(this);
        list = new ArrayList<>();
        document_scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                Log.e("到底怎么吗不能222222","sdsdssd");

                return false;
            }
        });
        rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                Log.e("到底怎么吗不能","sdsdssd");
                return false;
            }
        });
        initWebView();
        getDate();
    }

    private void getDate() {
        uid = new GetAccount(this).opId();
        did = new GetAccount(this).dptId();

        // 为RecyclerView设置默认动画和线性布局管理器
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rv.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

       api = HttpClient.getInstance().getGovernmentApi();
        Call<JsonObject> call = api.inCirculation(getAction(), getIntent().getStringExtra("opId"), uid);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().getAsJsonObject("data").toString());
                    if(bean.getData().getAccountDeptId().equals("0155") && getAction().equals("swcyCb")){
                        bsrNum = response.body().getAsJsonPrimitive("bsrNum").getAsInt();
                    }


                    if (jsonObject == null || jsonObject.equals("")) {
                        Toast.makeText(DocumentBaseCActivity.this, "空数据", Toast.LENGTH_SHORT).show();
                    } else {
                        // 解析数据
                        documentBaseC = new DocumentBaseC();
                        DocumentBaseC.Data dData = new DocumentBaseC.Data();
                        dData.setOpId(jsonObject.getString("opId"));
                        dData.setDocUnit(jsonObject.getString("docUnit"));
                        dData.setOpCreateTime(jsonObject.getString("opCreateTime"));
                        dData.setDocTheme(jsonObject.getString("docTheme"));
                        dData.setDocUrgent(jsonObject.getString("docUrgent"));
                        dData.setDocUrl(jsonObject.getString("docUrl"));
                        dData.setDocStatus(jsonObject.getString("docStatus"));
                        dData.setDocType(jsonObject.getString("docType"));
                        dData.setDocTitle(jsonObject.getString("docTitle"));
                        opNbrId_2 = jsonObject.getString("opNbrId");
                        if (!jsonObject.isNull("opYbrId")) {
                            opYbrId = jsonObject.getString("opYbrId");
                        }
                        Log.e("发射点士大夫士大夫热热热 34222","发的更好发挥发货5645665");

                        opType = jsonObject.getString("opType");

                        if(!getAction().equals("swcyNb")){
                            type.setVisibility(View.VISIBLE);
                            if(opType.equals("1")){
                                type_receive.setText("轮阅文件");
                            }else{
                                type_receive.setText("承办文件");
                            }
                        }


                        if(opType.equals("1")){

                            if(document_file_selectpeople != null){
                                document_file_selectpeople.setVisibility(View.GONE);
                            }

                        }
                        Log.e("发射点士大夫士大夫热热热 34222",opType);
                        JSONArray jsonArray = jsonObject.getJSONArray("flowList");
                        List<DocumentBaseC.FlowList> flowList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            flowList.add(new DocumentBaseC.FlowList(jsonArray.getJSONObject(i).getString("flowStatus"),
                                    jsonArray.getJSONObject(i).getString("flowName"),
                                    jsonArray.getJSONObject(i).getString("flowImageUrl"),
                                    jsonArray.getJSONObject(i).getString("flowContent"),
                                    jsonArray.getJSONObject(i).getString("flowTime")));
                        }

                        // 补齐后续的流程数据，并从前一个activity获取下一个阶段标记（present），便于后续显示


                        if(!flowList.get(flowList.size()-1).equals("9")){
                            for (int i = Integer.parseInt(flowList.get(flowList.size()-1).getFlowStatus())+1; i < 10; i++) {//补齐八位
                                flowList.add(new DocumentBaseC.FlowList(i + "", "", "", "", ""));
                            }
                        }

                        dData.setFlowList(flowList);
                        fileLists = new Gson().
                                fromJson(jsonObject.getJSONArray("fileList").toString(), new TypeToken<List<DocumentBaseC.FileList>>() {
                                }.getType());
                        dData.setFileList(fileLists);
                        dData.setDocStatus(jsonObject.get("docStatus").toString());
                        documentBaseC.setData(dData);
                        documentAdapter = new DocumentAdapter(documentBaseC.getData().getFlowList(), DocumentBaseCActivity.this);
                        document_shenpi.setAdapter(documentAdapter);
                        data_file = new ArrayList<>();
                        Log.e("的无私粉丝", "" + documentBaseC.getData().getFileList().size());
                        for (int i = 0; i < documentBaseC.getData().getFileList().size(); i++) {
                            data_file.add(documentBaseC.getData().getFileList().get(i).getOpName());
                            Log.e("的无私粉丝", "" + documentBaseC.getData().getFileList().get(i).getOpName());
                        }
                        adapter = new Adapter_Addtion();
                        rv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        lwdw.setText("来文单位 : ");
                        jjrq.setText("接件日期 : ");
                        gwzt.setText("公文主题 : ");
                        jjd.setText("紧急度 : ");
                        document_qicao.setText(documentBaseC.getData().getDocUnit().trim());
                        document_qicaotime.setText(documentBaseC.getData().getOpCreateTime().trim());
                        document_gongwen.setText(documentBaseC.getData().getDocTheme().trim());
                        document_huiqian.setText(documentBaseC.getData().getDocUrgent().trim());
                        document_title.setText(documentBaseC.getData().getDocTitle().trim());

//                        document_shenhe.setVisibility(View.INVISIBLE);
                        if (fileLists.size()<=0) {
                            document_fujian.setVisibility(View.GONE);
                        }else{
                            document_fujian.setVisibility(View.VISIBLE);
                        }

                        enlarge.setVisibility(View.GONE);
                        enshrink.setVisibility(View.GONE);
//                        document_fujian.setVisibility(View.VISIBLE);

                        // 判断公文主题类型，并选择性的加载不同的视图数据
                        if (dData.getDocType().equals("doc")) {
                            webView.loadUrl(documentBaseC.getData().getDocUrl());
                            webView.setVisibility(View.VISIBLE);

                                    // 初始化判断webview是否在可显示区域，以便设置FloatingActionButton的可视性

                                    document_scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                        @Override
                                        public void onGlobalLayout() {

                                            webView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                                                @Override
                                                public void onGlobalLayout() {

                                                    Rect scrollBounds = new Rect();
                                                    document_scrollView.getHitRect(scrollBounds);
                                                    if (webView.getLocalVisibleRect(scrollBounds)) {

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

                                                    webView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                                }
                                            });


                                            document_scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                        }
                                    });


                            imame_list.setVisibility(View.GONE);
                        } else if (dData.getDocType().equals("img")) {
                            webView.setVisibility(View.GONE);
                            imame_list.setVisibility(View.VISIBLE);
                            adapter_image = new Adapter_Image();
                            JSONArray jsonArray2 = jsonObject.getJSONArray("imageList");

                            // scrollview和recyclerview,防止滑动冲突,设置recyclerview在垂直方向不可滑动
                            imame_list.setLayoutManager(new LinearLayoutManager(DocumentBaseCActivity.this){
                                @Override
                                public boolean canScrollVertically() {
                                    return false;
                                }
                            });

                            imame_list.setItemAnimator(new DefaultItemAnimator());

                            enlarge.setVisibility(View.GONE);
                            enshrink.setVisibility(View.GONE);

                            ImageList = new ArrayList<>();

                            for (int i = 0; i < jsonArray2.length(); i++) {
                                ImageList.add(new DocumentBaseC.FileList(jsonArray2.getJSONObject(i).getString("path"), jsonArray2.getJSONObject(i).getString("opName")));
                            }
                            imame_list.setAdapter(adapter_image);

                        }

                        isChoosed = jsonObject.getString("docStatus");

                        // 流程逻辑的判断，
                        if (getIntent().getStringExtra("type") != null) {
                            //承办
                            if (getIntent().getStringExtra("type").equals("0")) {
                                //当docStatus状态为1的时候
                                if (jsonObject.getString("docStatus").equals("1")) {
//                                    //隐藏选择办事人按钮
                                    //  承办跳过来，不需要隐去选择办事人
                                    isNotAssist=1;
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DocumentBaseCActivity.this, "网络连接有误", Toast.LENGTH_SHORT).show();
                Log.d("LOG", t.toString());
            }
        });
        more.setText("完成");
        title.setText("公文签发");


        Call<Bean_QuickReply> call2 = api.getQuickReply(bean.getData().getOpId(),getIntent().getStringExtra("state"));

       call2.enqueue(new Callback<Bean_QuickReply>() {
           @Override
           public void onResponse(Call<Bean_QuickReply> call, Response<Bean_QuickReply> response) {

               JSONObject jsonObj =new JSONObject();
               try {
                   jsonObj = new JSONObject(response.body().getData().toString());

               } catch (JSONException e) {
                   e.printStackTrace();
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

           }

           @Override
           public void onFailure(Call<Bean_QuickReply> call, Throwable t) {
               Toast.makeText(DocumentBaseCActivity.this, "网络连接有误", Toast.LENGTH_SHORT).show();

           }
       });

    }


    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setTextZoom(100);

        // webview和scrollview的滑动冲突，设置webview不响应点击（包括滑动）事件
        webView.setOnTouchListener(new View.OnTouchListener() {

            @Override

            public boolean onTouch(View v, MotionEvent ev) {

                ((WebView) v).requestDisallowInterceptTouchEvent(false);
                return true;

            }

        });

        // 取消webview的垂直，水平
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);


        dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
    }


    // 自定义的Scrollview_Focus的回掉方法，里面监听滑动，之后就通过handler每隔一定时间判断是否还在滑动，并不断回掉该方法设置FloatingActionButton的可见性
    // 悬浮按钮的显示随着webview是否在屏幕上显示而动态设置可见性，（就是这么简单，不用我们自己去监听scrollview的滑动，判断滑动值知道webview的可视性了，恩，简单的方法总是有的，尽可能去畅想是否有相关的API接口就好了，不必自己去实现）
    @Override
    public void sendDistanceY(int distance) {
        Rect scrollBounds = new Rect();
        document_scrollView.getHitRect(scrollBounds);
        if (webView.getLocalVisibleRect(scrollBounds)) {

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
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        LogUtils.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        LogUtils.d(TAG, "onResume()");
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        LogUtils.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        LogUtils.d(TAG, "onDestroy()");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                break;
                //do something
            case KeyEvent.KEYCODE_VOLUME_UP:
                break;
                //do something
            case KeyEvent.KEYCODE_BACK:

                Intent intent2 = new Intent();
                setResult(0, intent2);
                finish();

                break;

                //do something
            case KeyEvent.KEYCODE_MENU:
                break;
                //do something
            case KeyEvent.KEYCODE_HOME:
                break;
                //invalid...
        }


        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.document_file_look: {//查看流程
                Intent intent = new Intent(this, Convey_Files_Activity.class);
                intent.putExtra("opId", getIntent().getStringExtra("opId"));
                startActivity(intent);
            }
            break;

            // 放大缩小的方法（webview的height="wrap_content"，自适应高度，随着放大缩小自适应高度，坑爹！！！ 自己也试过wrap可当初没有效果，还苦苦纠结如何实现自适应，程序员就是这样，有时就钻牛角尖，学会放空自己，从零开始）
            case R.id.enlarge:
                //放大
                webView.loadUrl("javascript:amplify()");

                break;


            case R.id.enshrink:
                //缩小
                webView.loadUrl("javascript:narrow()");

                break;


            case R.id.back: {
                Intent intent2 = new Intent();
                setResult(0, intent2);
                finish();
            }
            break;
            case R.id.document_guild_k: {//快速回复
                // 设置屏幕的透明度
                WindowManager.LayoutParams lp2 = getWindow().getAttributes();
                lp2.alpha = (float) 0.8;
                getWindow().setAttributes(lp2);
                // 加载popupwindow的布局
                final View view2 = getLayoutInflater().inflate(R.layout.popwindow_sy3, null);
                final RecyclerView listView = (RecyclerView) view2.findViewById(R.id.popwindow_sy3_list);
                final ArrayList<Map<String,String>> popList = new ArrayList<Map<String,String>>();
                view2.findViewById(R.id.click).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(list_key.size()>7){
                            Toast.makeText(DocumentBaseCActivity.this,"快捷回复不能超过8条",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!((EditText)view2.findViewById(R.id.add_content)).getText().toString().equals("")){

                            Call<Bean_Reply_Add> call = api.setQuickReply(bean.getData().getOpId(),getIntent().getStringExtra("state"),((EditText)view2.findViewById(R.id.add_content)).getText().toString());

                            call.enqueue(new Callback<Bean_Reply_Add>() {
                                @Override
                                public void onResponse(Call<Bean_Reply_Add> call, Response<Bean_Reply_Add> response) {

                                    if(response.body().getResult()){
                                        list_key.add(response.body().getOpId());
                                        outMap.put(response.body().getOpId(),((EditText)view2.findViewById(R.id.add_content)).getText().toString());
                                        ((EditText) view2.findViewById(R.id.add_content)).setText("");
                                        Toast.makeText(DocumentBaseCActivity.this,"添加成功！",Toast.LENGTH_SHORT).show();
                                        popAdapter.notifyDataSetChanged();

                                    }else{
                                        Toast.makeText(DocumentBaseCActivity.this,"添加失败！",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<Bean_Reply_Add> call, Throwable t) {
                                    Toast.makeText(DocumentBaseCActivity.this,"网络连接有误！",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else{

                            Toast.makeText(DocumentBaseCActivity.this,"快捷内容不能为空",Toast.LENGTH_SHORT).show();
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
            }
            break;
            case R.id.more: {//完成

            }
            break;
            default:
                break;
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
            holder.adapter_approve_item_text.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow2.dismiss();
                    document_guild_edit.setText(outMap.get(list_key.get(position)));
                    document_guild_edit.setSelection(document_guild_edit.getText().length());
                }
            });
            holder.remove.setOnClickListener(new OnClickListener() {
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
                                Toast.makeText(DocumentBaseCActivity.this,"删除成功！",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(DocumentBaseCActivity.this,"删除失败！",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Result_Message> call, Throwable t) {
                            Toast.makeText(DocumentBaseCActivity.this,"网络连接有误！",Toast.LENGTH_SHORT).show();
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


    // 弹出快捷回复的adapter
    public class PopAdapter extends BaseAdapter {

        ArrayList<Map<String,String>> strs = null;
        LayoutInflater inflater = null;

        public PopAdapter(ArrayList<Map<String,String>> strs, Context context) {
            this.strs = strs;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list_key.size();
        }

        @Override
        public Object getItem(int arg0) {
            return outMap.get(list_key.get(arg0));
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int arg0, View convertView, ViewGroup arg2) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.adapter_approve_item, arg2, false);
                holder.adapter_approve_item_text = (TextView) convertView.findViewById(R.id.adapter_approve_item_text);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
            }
            holder.adapter_approve_item_text.setText(outMap.get(list_key.get(arg0)));
            return convertView;
        }

        class ViewHolder {
            TextView adapter_approve_item_text = null;
            ImageView imageView = null;
        }

    }

    /**
     * 接口处理
     */
    @Override
    protected void baseJsonNext(String response, String tag) {
        // TODO Auto-generated method stub
        super.baseJsonNext(response, tag);
        if (tag.equals(TAG + "xxx")) {

        }
    }

    // 附件的adapter
    class Adapter_Addtion extends RecyclerView.Adapter<Adapter_Addtion.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion() {

        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.addtion_layout, parent, false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.addtion.setText("附件" + (position + 1) + " : ");
            holder.addtional.setText(fileLists.get(position).getOpName());

            if (data_file.size() - 1 == position) {
                holder.line.setVisibility(View.GONE);
            } else {
                holder.line.setVisibility(View.VISIBLE);
            }

            // item的点击事件
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    File f = new File(fileLists.get(position).getOpName());
                    String fileName = f.getName();
                    String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);

                    // 判断类型，选择是在线预览还是直接下载
                    if (prefix.equals("docx")
                            || prefix.equals("doc")
                            || prefix.equals("xlsx")
                            || prefix.equals("xls")
                            || prefix.equals("jpg")
                            || prefix.equals("png")
                            || prefix.equals("gif")
                            || prefix.equals("pdf")) {

                        Intent intent = new Intent(DocumentBaseCActivity.this, DetailsFJActivity.class);
                        intent.putExtra("filePreview", fileLists.get(position).getFilePreview());
                        intent.putExtra("type", "1");
                        startActivity(intent);

                    } else {

                        // 记录点击下载的位置，以便下载是拿到正确下载网址
                        position_download = position;
                        // 弹出dailog并监听summit是监听的回掉
                        new DialogUtil(DocumentBaseCActivity.this, new Summit()).showConfirm("下载提示", "确定要下载到本地嘛？", "确定", "不用了");
                    }


                }
            });
        }


        @Override
        public int getItemCount() {
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

    //  公文处理流程的adapter
    public class DocumentAdapter extends BaseAdapter {

        List<DocumentBaseC.FlowList> strs = null;
        LayoutInflater inflater = null;

        public DocumentAdapter(List<DocumentBaseC.FlowList> strs, Context context) {
            this.strs = strs;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return strs.size();
        }

        @Override
        public Object getItem(int arg0) {
            return strs.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(final int arg0, View convertView, ViewGroup arg2) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.adapter_document_item, arg2, false);
                holder.adapter_document_texticon = (TextView) convertView.findViewById(R.id.adapter_document_texticon);
                holder.adapter_document_name = (TextView) convertView.findViewById(R.id.adapter_document_name);
                holder.adapter_document_time = (TextView) convertView.findViewById(R.id.adapter_document_time);
                holder.adapter_document_content = (TextView) convertView.findViewById(R.id.adapter_document_content);
                holder.adapter_document_icon = (CircleImageView) convertView.findViewById(R.id.adapter_document_icon);
                holder.adapter_document_line1 = convertView.findViewById(R.id.adapter_document_line1);
                holder.adapter_document_line2 = convertView.findViewById(R.id.adapter_document_line2);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
            }

            // 初始化itemview的初始状态，避免item视图的复用造成的影响
            holder.adapter_document_content.setText("");


            holder.adapter_document_line1.setVisibility(View.INVISIBLE);
            holder.adapter_document_line2.setVisibility(View.INVISIBLE);
            AutoRelativeLayout.LayoutParams layoutParams = (AutoRelativeLayout.LayoutParams) holder.adapter_document_icon.getLayoutParams();
            layoutParams.topMargin = AutoUtils.getPercentWidthSize(38);
            holder.adapter_document_icon.setLayoutParams(layoutParams);
            if (strs.get(arg0).getFlowName().equals("")) {//判断条件改变颜色如果没到//如果内容为null那是自己加的未审核
                holder.adapter_document_line2.setBackgroundColor(getResources().getColor(R.color.color_bb));
                holder.adapter_document_line1.setBackgroundColor(getResources().getColor(R.color.color_bb));
                holder.adapter_document_texticon.setBackgroundResource(R.drawable.gray);
                holder.adapter_document_name.setVisibility(View.INVISIBLE);
                holder.adapter_document_time.setVisibility(View.INVISIBLE);
                holder.adapter_document_content.setVisibility(View.INVISIBLE);
                holder.adapter_document_icon.setVisibility(View.INVISIBLE);
                if (!strs.get(arg0 - 1).getFlowName().equals("")) {//如果上一条有数据
                    holder.adapter_document_line1.setBackgroundColor(getResources().getColor(R.color.color_43a7e1));
                }
            } else {

                // 后台传过来的有数据的item,初始化状态和数据
                holder.adapter_document_line2.setBackgroundColor(getResources().getColor(R.color.color_43a7e1));
                holder.adapter_document_texticon.setBackgroundResource(R.drawable.blue01);
                holder.adapter_document_name.setVisibility(View.VISIBLE);
                holder.adapter_document_time.setVisibility(View.VISIBLE);
                holder.adapter_document_content.setVisibility(View.VISIBLE);
                holder.adapter_document_icon.setVisibility(View.VISIBLE);
                holder.adapter_document_name.setText(strs.get(arg0).getFlowName());
                holder.adapter_document_time.setText(strs.get(arg0).getFlowTime());
                holder.adapter_document_content.setText(strs.get(arg0).getFlowContent());
                Picasso.with(DocumentBaseCActivity.this).load(strs.get(arg0).getFlowImageUrl())
                        .memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.adapter_document_icon);
            }
            ViewTreeObserver vto2 = holder.adapter_document_content.getViewTreeObserver();
            // 视图绘制之前会回掉执行的方法体，
            vto2.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    // 根据content的内容的行数，动态设置item下面线的长度，（因为下面的线总是需要与item最下面的控件底部对齐）
                    if (holder.adapter_document_content.getLineCount() == 1) {
                        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) holder.adapter_document_line2.getLayoutParams();
                        layoutParams1.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.adapter_document_name);
                        holder.adapter_document_line2.setLayoutParams(layoutParams1);
                    } else {
                        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) holder.adapter_document_line2.getLayoutParams();
                        layoutParams1.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.adapter_document_time);
                        holder.adapter_document_line2.setLayoutParams(layoutParams1);
                    }
                    RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) holder.adapter_document_line1.getLayoutParams();
                    layoutParams1.addRule(RelativeLayout.ABOVE, R.id.adapter_document_texticon);
                    holder.adapter_document_line1.setLayoutParams(layoutParams1);
                    holder.adapter_document_texticon.setVisibility(View.VISIBLE);
                    if (arg0 == 0) {//首页
                        holder.adapter_document_line1.setVisibility(View.INVISIBLE);
                        holder.adapter_document_line2.setVisibility(View.VISIBLE);
                    } else if (arg0 == (strs.size() - 1)) {//尾页
                        holder.adapter_document_line1.setVisibility(View.VISIBLE);
                        holder.adapter_document_line2.setVisibility(View.INVISIBLE);
                    } else {
                        holder.adapter_document_line2.setVisibility(View.VISIBLE);
                        holder.adapter_document_line1.setVisibility(View.VISIBLE);
                        if ((arg0 > 1 && strs.get(arg0 - 1).getFlowStatus().equals("5") && strs.get(arg0).getFlowStatus().equals("5")) ||
                                (arg0 > 1 && strs.get(arg0 - 1).getFlowStatus().equals("6") && strs.get(arg0).getFlowStatus().equals("6") ||
                                        (arg0 > 1 && strs.get(arg0 - 1).getFlowStatus().equals("7") && strs.get(arg0).getFlowStatus().equals("7")) ||
                                        (arg0 > 1 && strs.get(arg0 - 1).getFlowStatus().equals("8") && strs.get(arg0).getFlowStatus().equals("8")))) {
                            //如果上一个就是会签人,并且这个也是
                            holder.adapter_document_texticon.setVisibility(View.GONE);
                            holder.adapter_document_line2.setVisibility(View.INVISIBLE);
                            if (holder.adapter_document_content.getLineCount() == 1) {
                                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) holder.adapter_document_line1.getLayoutParams();
                                layoutParams1.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.adapter_document_name);
                                holder.adapter_document_line1.setLayoutParams(layoutParams2);
                            } else {
                                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) holder.adapter_document_line1.getLayoutParams();
                                layoutParams1.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.adapter_document_time);
                                holder.adapter_document_line1.setLayoutParams(layoutParams2);
                            }
                        }
//                        else if (arg0 > 1 && strs.get(arg0 - 1).getFlowStatus().equals("6") && strs.get(arg0).getFlowStatus().equals("6")) {
//                            //如果上一个就是会签人,并且这个也是
//                            holder.adapter_document_texticon.setVisibility(View.GONE);
//                            holder.adapter_document_line2.setVisibility(View.INVISIBLE);
//                            if (holder.adapter_document_content.getLineCount() == 1) {
//                                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) holder.adapter_document_line1.getLayoutParams();
//                                layoutParams1.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.adapter_document_name);
//                                holder.adapter_document_line1.setLayoutParams(layoutParams2);
//                            } else {
//                                RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) holder.adapter_document_line1.getLayoutParams();
//                                layoutParams1.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.adapter_document_time);
//                                holder.adapter_document_line1.setLayoutParams(layoutParams2);
//                            }
//                        }


                    }
                    // 移除监听，防止无限监听回掉该方法
                    holder.adapter_document_content.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
            // 设置texticon
            switch (Integer.parseInt(strs.get(arg0).getFlowStatus()) - 1) {
                case 0: {
                    holder.adapter_document_texticon.setText("收");
                    Log.e("撒地方生产方式", "收" + strs.get(arg0).getFlowStatus());
                }
                break;
                case 1: {
                    holder.adapter_document_texticon.setText("拟");
                    Log.e("撒地方生产方式", "拟" + strs.get(arg0).getFlowStatus());
                }
                break;
                case 2: {
                    holder.adapter_document_texticon.setText("批");
                    Log.e("撒地方生产方式", "批" + strs.get(arg0).getFlowStatus());
                }
                break;
                case 3: {
                    holder.adapter_document_texticon.setText("阅");
                    Log.e("撒地方生产方式", "阅" + strs.get(arg0).getFlowStatus());
                }
                break;
                case 4: {
                    holder.adapter_document_texticon.setText("承");
                }
                break;
                case 5: {
                    holder.adapter_document_texticon.setText("办");
                }
                break;
                case 6: {
                    holder.adapter_document_texticon.setText("协");
                }
                break;
                case 7: {
                    holder.adapter_document_texticon.setText("转");
                }
                break;
                case 8: {
                    holder.adapter_document_texticon.setText("档");
                }
                break;

            }
            return convertView;
        }

        class ViewHolder {
            TextView adapter_document_texticon, adapter_document_name, adapter_document_time, adapter_document_content = null;
            CircleImageView adapter_document_icon = null;
            View adapter_document_line1, adapter_document_line2, adapter_document_line3;//线段上下
        }
    }

    // 快速处理保存返回人的ID集合
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10) {//收取选择的人员ID
            opIds = data.getStringExtra("opIds");
            opBsrId = data.getStringExtra("opBsrId");
            nbrId = data.getStringExtra("opNbrId");
            opXbrId = data.getStringExtra("opXbrId");
            opNames = data.getStringExtra("opNames") +"等人";
            Log.e("namesds分数得分",opNames);
            // 改变isNotChoosed标记，表明已进入过快速回复，选择过处理人，以便再次进入初始化这些已经选择的人
            isNotChoosed = 1;
        }
    }


    // 下载弹出框点击的回掉方法体
    class Summit implements DialogUtil.OnClickListenner {

        public Summit() {
        }

        @Override
        public void yesClick() {

            final String path = documentBaseC.getData().getFileList().get(position_download).getPath();
            path_addtion = documentBaseC.getData().getFileList().get(position_download).getOpName();

            // downloadmanager下载文件的工具类，下载文件，把下载交给系统去做
            DownloadUtil.startDownload(dm, DocumentBaseCActivity.this, path, path_addtion, "正在下载...");


        }

        @Override
        public void noClick() {
        }

        @Override
        public void onSingleClick() {

        }
    }

    // 图文公文的adapter
    public class Adapter_Image extends RecyclerView.Adapter<Adapter_Image.ViewHolder> {

        private View view;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

            view = getLayoutInflater().inflate(R.layout.item_image, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder,final int i) {
//
//            PhotoView webView=null;
//
//            webView= viewHolder.image_item;


            if(i==ImageList.size()-1){
                viewHolder.decration.setVisibility(View.GONE);
            }else{
                viewHolder.decration.setVisibility(View.VISIBLE);
            }



//            webView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

//            webView.setInitialScale(0);
//            webView.setScaleY(1);
//            webView.getSettings().setUseWideViewPort(true);
//            webView.getSettings().setJavaScriptEnabled(true);// 设置支持javascript脚本
//            webView.loadUrl(ImageList.get(i).getPath());
////            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
//            webView.getSettings().setDomStorageEnabled(true);
//            //开启 database storage API 功能
//            webView.getSettings().setDatabaseEnabled(true);
//            // 取消webview的垂直，水平
//            webView.setVerticalScrollBarEnabled(false);
//            webView.setHorizontalScrollBarEnabled(false);
//            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//            //开启 Application Caches 功能
//            webView.getSettings().setAppCacheEnabled(true);
//            webView.setWebViewClient(new WebViewClient());
//            webView.getSettings().setBuiltInZoomControls(true); // 是否支持放大缩小 controller
//            webView.getSettings().setDisplayZoomControls(false); // 是否显示  controller
//            webView.getSettings().setLoadWithOverviewMode(true); // 自适应
//
//            webView.getSettings().setSupportZoom(true); // 可以缩放
//            webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);// 默认缩放模式
//            webView.getSettings().setUseWideViewPort(true);


            Picasso.with(DocumentBaseCActivity.this).load(ImageList.get(i).getPath())
                    .memoryPolicy(MemoryPolicy.NO_CACHE).into(viewHolder.image_item);



            viewHolder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(DocumentBaseCActivity.this, DetailsFJActivity.class);
                    intent.putExtra("filePreview", ImageList.get(i).getPath());
                    intent.putExtra("type", "0");
                    startActivity(intent);

                }
            });


//            PhotoViewAttacher photoViewAttacher=new PhotoViewAttacher(viewHolder.image_item);

        }

        @Override
        public int getItemCount() {
            return ImageList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView image_item;
            private View decration;


            public ViewHolder(View itemView) {
                super(itemView);
                image_item = (ImageView) itemView.findViewById(R.id.image_item);
                decration =  itemView.findViewById(R.id.decration);
            }
        }

    }


}


