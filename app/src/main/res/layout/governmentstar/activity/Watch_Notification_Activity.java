package com.lanwei.governmentstar.activity;

import android.app.DownloadManager;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.lll.DocumentBaseCActivity;
import com.lanwei.governmentstar.activity.lll.SelectStateActivity;
import com.lanwei.governmentstar.activity.zyx.DetailsFJActivity;
import com.lanwei.governmentstar.activity.zyx.GgNoticeActivity;
import com.lanwei.governmentstar.bean.Collection_Return;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Notification_Item;
import com.lanwei.governmentstar.bean.Return_Amount;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.receiver.MyReceiver;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.DownloadUtil;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.utils.ShortcutBadger;
import com.lanwei.governmentstar.view.MyScrollView_Focus;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/1.
 */

public class Watch_Notification_Activity extends BaseActivity implements View.OnClickListener ,MyScrollView_Focus.MyScrollListener ,View.OnLayoutChangeListener{

    private ImageView back;
    private ImageView more;
    private FrameLayout right;

    private TextView publish;
    private TextView type;
    private TextView noticetitle;
    private TextView name;
    private TextView time;
    private RecyclerView rv;
    private PopupWindow popupWindow;
    private PopupWindow popupWindow2;
    private Adapter_Addtion adapter;
    private ArrayList<String> data_list = null;
    private RelativeLayout rv2;
    private GovernmentApi api;
    private String userId;
    private String opId;
    //  根据网络请求的数据，加载不同是否显示作废文件的标志
    private int mark = 0;
    private LinearLayout condition;
    private LinearLayout end;
    private Notification_Item notification_item;
    private Notification_Item.Data data_content;
    private ArrayList<Notification_Item.Filelist> list_add;
    private WebView webview;
    private FloatingActionButton enshrink;
    private FloatingActionButton enlarge;
    private MyScrollView_Focus scroll_view;
    private String content_edit;
    private boolean is_collected;
    private Logging_Success bean;
    private TextView see_condition;
    DownloadManager dm;
    private int position_download;
    String path_addtion;
    private SharedPreferences amount_ShortcutBadger;

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
        setContentView(R.layout.preview_file);

//        // 版本大于5.0系统，支持沉浸式，隐藏系统状态栏，并适配我们的背景颜色，参照郭霖博客沉浸式效果
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

        activityRootView = findViewById(R.id.root_layout);
        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight/3;

        back=(ImageView)findViewById(R.id.back);
        more=(ImageView)findViewById(R.id.more);
        right=(FrameLayout) findViewById(R.id.right);
        rv2=(RelativeLayout) findViewById(R.id.rv2);
        end=(LinearLayout) findViewById(R.id.end);
        scroll_view=(MyScrollView_Focus) findViewById(R.id.scroll_view);
        webview=(WebView) findViewById(R.id.webview);
        enshrink=(FloatingActionButton) findViewById(R.id.enshrink);
        enlarge=(FloatingActionButton) findViewById(R.id.enlarge);

        enlarge.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        enshrink.setBackgroundTintList(getResources().getColorStateList(R.color.white));

        webview.setVisibility(View.VISIBLE);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webview.getSettings().setTextZoom(100);
        publish=(TextView) findViewById(R.id.publish);
        type=(TextView) findViewById(R.id.type);
        type.setVisibility(View.VISIBLE);
        name=(TextView) findViewById(R.id.name);
        noticetitle=(TextView) findViewById(R.id.noticetitle);
        name.setVisibility(View.VISIBLE);
        time=(TextView) findViewById(R.id.time);
        time.setVisibility(View.VISIBLE);
        publish.setVisibility(View.GONE);
        more.setVisibility(View.VISIBLE);
        rv=(RecyclerView) findViewById(R.id.rv);

        dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        String defString = PreferencesManager.getInstance(this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);
//        amount_ShortcutBadger = getSharedPreferences("amount_ShortcutBadger", 0);
//        Log.e("number",amount_ShortcutBadger.getInt("number",0)+"");
//        ShortcutBadger.applyCount(this, amount_ShortcutBadger.getInt("number",0)-1);
//        amount_ShortcutBadger.edit().putInt("number",amount_ShortcutBadger.getInt("number",0)-1).commit();

        userId=bean.getData().getOpId();

        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(true);
        webview.getSettings().setUseWideViewPort(false);
        webview.getSettings().setLoadWithOverviewMode(true);
//        webview.setOnTouchListener(new View.OnTouchListener() {
//
//            @Override
//
//            public boolean onTouch(View v, MotionEvent ev) {
//
//                ((WebView) v).requestDisallowInterceptTouchEvent(false);
//                return true;
//
//            }
//        });

        back.setOnClickListener(this);
        publish.setOnClickListener(this);
        right.setOnClickListener(this);
        enlarge.setOnClickListener(this);
        enshrink.setOnClickListener(this);
        scroll_view.setMyScrollListener(this);
//        adapter=new DetailsAdapter();

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

        opId=getIntent().getStringExtra("opId");

        noticetitle.setText(getIntent().getStringExtra("noticeTitle"));
        Call<Notification_Item> call=api.watch_notification(opId,userId);

        call.enqueue(new Callback<Notification_Item>() {
           @Override
           public void onResponse(Call<Notification_Item> call, Response<Notification_Item> response) {

               notification_item=response.body();
               data_content=response.body().getData();
               list_add=response.body().getData().getFileList();
               type.setText(data_content.getNoticeType());
               name.setText(data_content.getOpCreateName());
               time.setText(data_content.getOpCreateTime());
               is_collected=data_content.getNoticeCollectionState();

               if(data_content.getOpCreateName().equals(bean.getData().getOpName())){
                   mark=1;
               }
               return_amount();
               webview.loadUrl(data_content.getNoticeUrl());

               if(list_add != null && list_add.size()>0){
                   end.setVisibility(View.VISIBLE);
                   adapter=new Adapter_Addtion();
                   rv.setVisibility(View.VISIBLE);
                   rv.setAdapter(adapter);
               }else{
                   end.setVisibility(View.GONE);
               }

           }

           @Override
           public void onFailure(Call<Notification_Item> call, Throwable throwable) {
               end.setVisibility(View.GONE);
               Toast.makeText(Watch_Notification_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();
           }
       });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //添加layout大小发生改变监听器
        activityRootView.addOnLayoutChangeListener(this);
    }

    void return_amount() {
        String defString3 = PreferencesManager.getInstance(Watch_Notification_Activity.this, "accountBean").get("jsonStr");
        Gson gson3 = new Gson();
        Logging_Success bean3 = gson3.fromJson(defString3, Logging_Success.class);
        GovernmentApi api3 = HttpClient.getInstance().getGovernmentApi();
        Call<Return_Amount> call2 = api3.return_amount_daiban(bean3.getData().getOpId());
        call2.enqueue(new Callback<Return_Amount>() {
            @Override
            public void onResponse(Call<Return_Amount> call, Response<Return_Amount> response) {
                if (response.body().getData() != null && !response.body().getData().equals("")) {

                    amount_ShortcutBadger = getSharedPreferences("amount_ShortcutBadger", 0);
                    Log.e("number",amount_ShortcutBadger.getInt("number",0)+"");
                    ShortcutBadger.applyCount(Watch_Notification_Activity.this, response.body().getData().getManage_num());
                    amount_ShortcutBadger.edit().putInt("number",response.body().getData().getManage_num()).commit();

                }
            }

            @Override
            public void onFailure(Call<Return_Amount> call, Throwable t) {
                Toast.makeText(Watch_Notification_Activity.this, "网络连接有误!", Toast.LENGTH_SHORT).show();
            }
        });
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

    // 自定义的Scrollview_Focus的回掉方法，里面监听滑动，之后就通过handler每隔一定时间判断是否还在滑动，并不断回掉该方法设置FloatingActionButton的可见性
    // 悬浮按钮的显示随着webview是否在屏幕上显示而动态设置可见性，（就是这么简单，不用我们自己去监听scrollview的滑动，判断滑动值知道webview的可视性了，恩，简单的方法总是有的，尽可能去畅想是否有相关的API接口就好了，不必自己去实现）
    @Override
    public void sendDistanceY(int distance) {
        Rect scrollBounds = new Rect();
        scroll_view.getHitRect(scrollBounds);
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

    class Adapter_Addtion extends RecyclerView.Adapter<Adapter_Addtion.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion() {
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.addtion_layout, parent ,false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.addtion.setText("附件"+(position+1)+" : ");

            holder.addtional.setText(list_add.get(position).getOpName());

            if(position==list_add.size()-1){
                holder.line.setVisibility(View.GONE);
            }else{
                holder.line.setVisibility(View.VISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO 跳去附件详情界面

                    File f = new File(list_add.get(position).getOpName());
                    String fileName = f.getName();
                    String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);

                    if (prefix.equals("docx")
                            || prefix.equals("doc") || prefix.equals("xlsx") || prefix.equals("xls") || prefix.equals("jpg") || prefix.equals("png") || prefix.equals("gif")
                            || prefix.equals("pdf")) {


                        Intent intent = new Intent(Watch_Notification_Activity.this, DetailsFJActivity.class);
                        intent.putExtra("filePreview", list_add.get(position).getFilePreview());
                        intent.putExtra("type", "1");
                        startActivity(intent);
                    }else{

                        // 记录点击下载的位置，以便下载是拿到正确下载网址
                        position_download = position;
                        // 弹出dailog并监听summit是监听的回掉
                        new DialogUtil(Watch_Notification_Activity.this, new Summit()).showConfirm("下载提示", "确定要下载到本地嘛？", "确定", "不用了");


                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return list_add.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView addtion;
            TextView addtional;
            View line;

            public MyViewHolder(View itemView) {

                super(itemView);
                addtion = (TextView) view.findViewById(R.id.addtion);
                addtional = (TextView) view.findViewById(R.id.addtional);
                line =  view.findViewById(R.id.line);
            }
        }
    }


    // 下载弹出框点击的回掉方法体
    class Summit implements DialogUtil.OnClickListenner {

        public Summit() {
        }

        @Override
        public void yesClick() {

            final String path = list_add.get(position_download).getPath();
            path_addtion = list_add.get(position_download).getOpName();

            // downloadmanager下载文件的工具类，下载文件，把下载交给系统去做
            DownloadUtil.startDownload(dm, Watch_Notification_Activity.this, path, path_addtion, "正在下载...");


        }

        @Override
        public void noClick() {
        }

        @Override
        public void onSingleClick() {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(getIntent().getStringExtra("readState").equals("")){

        }else{
            if(keyCode == KeyEvent.KEYCODE_BACK){
                Intent intent3 = new Intent();
                setResult(520, intent3);
                finish();

            }
        }



        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.back){

            if(getIntent().getStringExtra("readState").equals("")){
                finish();
            }else{

                    Intent intent3 = new Intent();
                    setResult(520, intent3);
                    finish();

            }
        }

        if(v.getId()==R.id.collect){

            popupWindow.dismiss();
            WindowManager.LayoutParams lp2 = getWindow().getAttributes();
            lp2.alpha=(float) 0.8;
            getWindow().setAttributes(lp2);

            View view2=getLayoutInflater().inflate(R.layout.cancel_file,null);

            popupWindow2=new PopupWindow(view2, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);

            // 初始化popupwindow的点击控件

            TextView cancel=(TextView) view2.findViewById(R.id.cancel);
            final EditText content=(EditText) view2.findViewById(R.id.content);
            // 点击屏幕之外的区域可否让popupwindow消失

            popupWindow2.setFocusable(true);
            popupWindow2.setBackgroundDrawable(new BitmapDrawable());
            popupWindow2.setOnDismissListener(new PoponDismissListener());
            popupWindow2.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            popupWindow2.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            // 注册popupwindow里面的点击事件
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    content_edit=content.getText().toString();
                    Log.e("content_edit的内容",content_edit);
                    if(TextUtils.isEmpty(content_edit)){

                        Toast.makeText(Watch_Notification_Activity.this,"作废内容不能为空",Toast.LENGTH_SHORT).show();
                    }else {
                        Call<Collection_Return> call = api.delete(opId, userId, content_edit);

                        call.enqueue(new Callback<Collection_Return>() {
                            @Override
                            public void onResponse(Call<Collection_Return> call, Response<Collection_Return> response) {
                                popupWindow2.dismiss();

                                if(response.body().getData()){

                                    Toast.makeText(Watch_Notification_Activity.this,"作废成功",Toast.LENGTH_SHORT).show();

                                }else{

                                    Toast.makeText(Watch_Notification_Activity.this,"作废失败",Toast.LENGTH_SHORT).show();
                                }



                            }

                            @Override
                            public void onFailure(Call<Collection_Return> call, Throwable throwable) {
                                popupWindow2.dismiss();

                                Toast.makeText(Watch_Notification_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();
                            }
                        });

                    }


                }
            });
            View rootview = LayoutInflater.from(Watch_Notification_Activity.this).inflate(R.layout.preview_file, null);
            // 设置popupwindow的显示位置
            popupWindow2.showAtLocation(rootview,Gravity.BOTTOM,0,0);


        }

        if(v.getId()==R.id.enlarge){

            webview.loadUrl("javascript:amplify()");

        }

        if(v.getId()==R.id.enshrink){

            webview.loadUrl("javascript:narrow()");

        }

        if(v.getId()==R.id.see_condition){

            popupWindow.dismiss();

                if(!is_collected){

                    Log.e("userId：",userId);
                    Call<Collection_Return> call = api.collect_notification(opId, userId);

                    call.enqueue(new Callback<Collection_Return>() {
                        @Override
                        public void onResponse(Call<Collection_Return> call, Response<Collection_Return> response) {

                            if(response.body().getData()){

                                Toast.makeText(Watch_Notification_Activity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                                is_collected=true;

                            }else{

                                Toast.makeText(Watch_Notification_Activity.this,"收藏失败",Toast.LENGTH_SHORT).show();

                            }


                        }

                        @Override
                        public void onFailure(Call<Collection_Return> call, Throwable throwable) {

                            Toast.makeText(Watch_Notification_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();

                        }
                    });


                }else{

                    Call<Collection_Return> call = api.collect_cancel(opId, userId);
                    call.enqueue(new Callback<Collection_Return>() {
                        @Override
                        public void onResponse(Call<Collection_Return> call, Response<Collection_Return> response) {

                            if(response.body().getData()){

                                Toast.makeText(Watch_Notification_Activity.this,"取消收藏成功",Toast.LENGTH_SHORT).show();
                                is_collected=false;
                            }else{

                                Toast.makeText(Watch_Notification_Activity.this,"取消收藏失败",Toast.LENGTH_SHORT).show();

                            }


                        }

                        @Override
                        public void onFailure(Call<Collection_Return> call, Throwable throwable) {

                            Toast.makeText(Watch_Notification_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();
                        }
                    });


                }


        }

        if(v.getId()==R.id.shift_file){
            popupWindow.dismiss();

            if(mark==1){

                // 查看接收人

                Intent intent = new Intent(Watch_Notification_Activity.this, SelectStateActivity.class);
                intent.putExtra("opId",data_content.getOpId());
                intent.putExtra("userId",userId);
                startActivity(intent);

            }else{

                // 转发通知
                Intent intent = new Intent(Watch_Notification_Activity.this, Shift_Notification.class);
                intent.putExtra("opId",data_content.getOpId());
                intent.putExtra("userId",userId);
                intent.putExtra("noticeTitle",getIntent().getStringExtra("noticeTitle"));
                startActivity(intent);
            }




        }

        if(v.getId()==R.id.right){

            // 弹出popupwindow前，调暗屏幕的透明度
            WindowManager.LayoutParams lp2 = getWindow().getAttributes();
            lp2.alpha=(float) 0.8;
            getWindow().setAttributes(lp2);

            View view ;

            if(mark==1){
                // 加载popupwindow的布局
               view =getLayoutInflater().inflate(R.layout.items_three_notification,null);
                see_condition=(TextView) view.findViewById(R.id.see_condition);
                TextView collect=(TextView) view.findViewById(R.id.collect);
                TextView shift_file=(TextView) view.findViewById(R.id.shift_file);
                TextView shift_baby= (TextView) view.findViewById(R.id.shfit);

                collect.setText("作废文件");
                shift_file.setText("查看接收人");

                if(is_collected){
                    see_condition.setText("取消收藏");
                }else{
                    see_condition.setText("收  藏");
                }

                see_condition.setOnClickListener(this);
                collect.setOnClickListener(this);
                shift_file.setOnClickListener(this);
                shift_baby.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        popupWindow.dismiss();
                        // 转发通知
                        Intent intent = new Intent(Watch_Notification_Activity.this, Shift_Notification.class);
                        intent.putExtra("opId",data_content.getOpId());
                        intent.putExtra("userId",userId);
                        intent.putExtra("noticeTitle",getIntent().getStringExtra("noticeTitle"));
                        startActivity(intent);

                    }
                });

            }else{

                view =getLayoutInflater().inflate(R.layout.items_two,null);

                see_condition=(TextView) view.findViewById(R.id.see_condition);
                TextView shift_file=(TextView) view.findViewById(R.id.shift_file);

                see_condition.setOnClickListener(this);
                shift_file.setOnClickListener(this);

                if(is_collected){
                    see_condition.setText("取消收藏");
                }else{
                    see_condition.setText("收  藏");
                }

                shift_file.setText("转发通知");
            }

            popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);

            // 点击屏幕之外的区域可否让popupwindow消失
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOnDismissListener(new PoponDismissListener());
            // 注册popupwindow里面的点击事件


            // 设置popupwindow的显示位置
            popupWindow.showAtLocation(more, Gravity.RIGHT | Gravity.TOP,20,rv2.getMeasuredHeight()*3/2);
//            popupWindow.showAsDropDown(more,-270,35);

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

            if(is_appearance){
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                Log.e("到底怎么吗不能6666666666","sdsdssd");
            }
        }

    }


}
