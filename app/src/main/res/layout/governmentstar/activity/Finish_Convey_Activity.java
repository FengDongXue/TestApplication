package com.lanwei.governmentstar.activity;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
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
import android.telecom.Call;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.lanwei.governmentstar.R;

import com.lanwei.governmentstar.activity.lll.DocumentBaseCActivity;
import com.lanwei.governmentstar.activity.zyx.DetailsFJActivity;
import com.lanwei.governmentstar.bean.Addtion_List;
import com.lanwei.governmentstar.bean.DocumentBaseC;
import com.lanwei.governmentstar.bean.MyDocument;
import com.lanwei.governmentstar.bean.Return_Finish;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.DownloadUtil;
import com.lanwei.governmentstar.view.FullyLinearLayoutManager;
import com.lanwei.governmentstar.view.MyListView;
import com.lanwei.governmentstar.view.MyScrollView_Focus;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/10.
 */

public class Finish_Convey_Activity extends BaseActivity implements View.OnClickListener ,MyScrollView_Focus.MyScrollListener{

    private ImageView back;
    private TextView title;
    private TextView see;
    private TextView department;
    private TextView theme;
    private TextView theme2;
    private TextView content;
    private TextView date;
    private TextView type;
    private TextView emergency;
    private Return_Finish.Data data_return;
    private PopupWindow popupWindow;
    private ArrayList<Return_Finish.Filelist> list_add;
    private ArrayList<Return_Finish.Imagelist> list_image =new ArrayList<Return_Finish.Imagelist>();
//    private ArrayList<Addtion_List> data_list;
    private DownloadManager dm;
    private RecyclerView lv;
    private Adapter_Addtion adapter_addtion;
    private WebView webView;
    private LinearLayout condition;
    private LinearLayout addtion_layout;
//    private LinearLayout button_active;
//    private LinearLayout button_focus;
//    private TextView large_focus;
//    private TextView shrink_focus;
//    private TextView large;
//    private TextView shrink;
    private int position_addtion;
    private RecyclerView listview;
    private Adapter_Addtion2 adapter;
    private MyScrollView_Focus document_scrollView;
    private int distance_focus;
    private int distance_active;
    private FloatingActionButton enlarge;
    private FloatingActionButton enshrink;
    private int position_download;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish_niban);

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


        initweight();

        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        retrofit2.Call<Return_Finish> call= api.getInformation(getIntent().getStringExtra("opId"));
        call.enqueue(new Callback<Return_Finish>() {
            @Override
            public void onResponse(retrofit2.Call<Return_Finish> call, Response<Return_Finish> response) {
                if(response.body().getData() != null){
                    data_return=response.body().getData();
                    list_add=response.body().getData().getFileList();
                    list_image=response.body().getData().getImageList();

                    if(list_add.size()>0){
                        adapter_addtion=new Adapter_Addtion();
                        lv.setAdapter(adapter_addtion);
                        adapter_addtion.notifyDataSetChanged();
                    }else{
                        addtion_layout.setVisibility(View.GONE);
                    }

                    if(data_return.getDocType().equals("img") && list_image.size()>0){
                        Log.e("大使馆反对法国德国",""+list_image.size());
                        listview.setVisibility(View.VISIBLE);
                        webView.setVisibility(View.GONE);
                        adapter=new Adapter_Addtion2();
                        listview.setAdapter(adapter);

                        enlarge.setVisibility(View.GONE);
                        enshrink.setVisibility(View.GONE);


                    }else if(data_return.getDocType().equals("doc")){
                        webView.setVisibility(View.VISIBLE);
                        webView.loadUrl(response.body().getData().getDocUrl());
                        Log.e("gdgdfgdfghtr", "onResponse: 发个大概豆腐干反对估计要同居" );
                        webView.getSettings().setTextZoom(100);
                        webView.setOnTouchListener(new View.OnTouchListener() {

                            @Override

                            public boolean onTouch(View v, MotionEvent ev) {

                                ((WebView) v).requestDisallowInterceptTouchEvent(false);
                                return true;

                            }

                        });
                        enlarge.setVisibility(View.VISIBLE);
                        enshrink.setVisibility(View.VISIBLE);
                        enlarge.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                        enshrink.setBackgroundTintList(getResources().getColorStateList(R.color.white));

                        webView.setVerticalScrollBarEnabled(false);
                        webView.setHorizontalScrollBarEnabled(false);

//                        webView.setWebViewClient(new WebViewClient(){
//
//                            @Override
//                            public void onPageFinished(WebView view, String url) {
//                                super.onPageFinished(view, url);
//                                int webviewheight3 =  (int)(webView.getContentHeight()*webView.getScale());
//                                webView.setLayoutParams(new AutoLinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1000));
//
//                            }
//                        });


                    }

                    setViewText(department, "来文单位:", " " + response.body().getData().getDocUnit().trim());
                    setViewText(date, "接件日期:", " " + response.body().getData().getOpCreateTime().trim());
                    setViewText(theme, "公文主题:", " " + response.body().getData().getDocTheme().trim());
                    setViewText(emergency, "紧急度:", " " + response.body().getData().getDocUrgent().trim());

                    if(response.body().getData().getOpType()!= null && response.body().getData().getOpType().equals("0")){
                        setViewText(type, "类型:", "承办文件");
                    }else if(response.body().getData().getOpType()!= null && response.body().getData().getOpType().equals("1")){
                        setViewText(type, "类型:", "轮阅文件");
                    }else {
                        type.setVisibility(View.GONE);
                    }

                    theme2.setVisibility(View.VISIBLE);
                    content.setText( response.body().getData().getDocTitle().trim());

                }
            }

            @Override
            public void onFailure(retrofit2.Call<Return_Finish> call, Throwable t) {

                Toast.makeText(Finish_Convey_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();
            }
        });

    }


    //设置文字
    private void setViewText(TextView text, String first, String content) {
        SpannableString spannableString = new SpannableString(first + content);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_aaa)), 0, first.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_23)), first.length(), first.length() + content.length()
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(spannableString);
    }


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

    void initweight(){

        back=(ImageView) findViewById(R.id.back);
        title=(TextView) findViewById(R.id.title);
        department=(TextView) findViewById(R.id.department);
        content=(TextView) findViewById(R.id.content);
        theme2=(TextView) findViewById(R.id.theme2);
        theme=(TextView) findViewById(R.id.theme);
        date=(TextView) findViewById(R.id.date);
        type=(TextView) findViewById(R.id.type);
        emergency=(TextView) findViewById(R.id.emergency);
        lv=(RecyclerView) findViewById(R.id.lv);
        see=(TextView) findViewById(R.id.see);
        webView=(WebView) findViewById(R.id.web);
        addtion_layout=(LinearLayout) findViewById(R.id.addtion_layout);
        listview=(RecyclerView) findViewById(R.id.listview);
        document_scrollView=(MyScrollView_Focus) findViewById(R.id.document_scrollView);
        enlarge=(FloatingActionButton) findViewById(R.id.enlarge);
        enshrink=(FloatingActionButton) findViewById(R.id.enshrink);
        document_scrollView.setMyScrollListener(this);
        type.setVisibility(View.VISIBLE);

        // 初始化FloatingActionButton的显示状态
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

        listview.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        listview.setItemAnimator(new DefaultItemAnimator());
        // 为RecyclerView设置默认动画和线性布局管理器
        lv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        lv.setLayoutManager(new LinearLayoutManager(this){

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        lv.setFocusable(false);
        listview.setFocusable(false);
        back.setOnClickListener(this);
        see.setOnClickListener(this);
        enlarge.setOnClickListener(this);
        enshrink.setOnClickListener(this);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
//        webView.getSettings().setDomStorageEnabled(true);
//        //开启 database storage API 功能
//        webView.getSettings().setDatabaseEnabled(true);
//        //开启 Application Caches 功能
//        webView.getSettings().setAppCacheEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
        dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

//        button_active.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//
//                distance_focus = button_focus.getTop();
//                distance_active=button_active.getTop();
//
//                button_active.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//
//            }
//        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.back:

                finish();

                break;

            case R.id.enlarge:
                //放大
                webView.loadUrl("javascript:amplify()");

//                int webviewheight = (int) (webView.getContentHeight()*webView.getScale());
//
//                webView.setLayoutParams(new AutoLinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,webviewheight));

                break;


            case R.id.enshrink:
                //缩小
                webView.loadUrl("javascript:narrow()");

//                int webviewheight2 = (int) (webView.getContentHeight()*webView.getScale() );
//
//                webView.setLayoutParams(new AutoLinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,webviewheight2));

                break;


            case R.id.see:

                Intent intent=new Intent(this,Convey_Files_Activity.class);
                intent.putExtra("opId",getIntent().getStringExtra("opId"));
                startActivity(intent);

                break;
        }
    }


//    @Override
//    public void sendDistanceY(int scrollY) {
//
//        Log.d("scroll","----------------------height:"+scrollY);
//        if(scrollY >= distance_active - distance_focus){  //如果滑动的距离大于或等于位置3到位置2的距离，那么说明内部绿色的顶部在位置2上面了，我们需要显示外部绿色栏了
//            button_focus.setVisibility(View.VISIBLE);
//        }else {  //反之隐藏
//            button_focus.setVisibility(View.GONE);
//        }
//
//    }

    class Adapter_Addtion2 extends RecyclerView.Adapter<Adapter_Addtion2.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion2() {}

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.item_image, parent,false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            Picasso.with(Finish_Convey_Activity.this).load(list_image.get(position).getPath())
                    .memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.imageView);


            if(position==list_image.size()-1){
                holder.decration.setVisibility(View.GONE);
            }else{
                holder.decration.setVisibility(View.VISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Finish_Convey_Activity.this, DetailsFJActivity.class);
                    intent.putExtra("filePreview", list_image.get(position).getPath());
                    intent.putExtra("type", "0");
                    startActivity(intent);

                }
            });


        }

        @Override
        public int getItemCount() {
            return list_image.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

           ImageView imageView;
           View decration;

            public MyViewHolder(View itemView) {

                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.image_item);
                decration =  itemView.findViewById(R.id.decration);
            }
        }
    }



    class Adapter_Addtion extends RecyclerView.Adapter<Adapter_Addtion.MyViewHolder> {

        private View view = null;

        public Adapter_Addtion() {}

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.addtion_layout, parent,false);

            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.addtion.setText("附件" + (position + 1) + " : ");
            holder.addtional.setText(list_add.get(position).getOpName());

            if(list_add.size()-1==position){

                holder.line.setVisibility(View.GONE);

            }else{
                holder.line.setVisibility(View.VISIBLE);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    File f =new File(list_add.get(position).getOpName());
                    String fileName=f.getName();
                    String prefix=fileName.substring(fileName.lastIndexOf(".")+1);

                    if(prefix.equals("docx") || prefix.equals("doc") || prefix.equals("xlsx") || prefix.equals("xls") || prefix.equals("jpg") || prefix.equals("png") || prefix.equals("gif") || prefix.equals("pdf")){

                        Intent intent=new Intent(Finish_Convey_Activity.this, DetailsFJActivity.class);
                        intent.putExtra("filePreview",list_add.get(position).getFilePreview());
                        intent.putExtra("type","1");
                        startActivity(intent);

                    }else{

                        position_addtion = position;
                        // 弹出dailog并监听summit是监听的回掉
                        new DialogUtil(Finish_Convey_Activity.this, new Summit()).showConfirm("下载提示", "确定要下载到本地嘛？", "确定", "不用了");
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
                line = view.findViewById(R.id.line);
            }
        }
    }


//    public class DownloadReceiver extends BroadcastReceiver {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
//                // @TODO SOMETHING
//                Toast.makeText( Finish_Convey_Activity.this,"下载完成",Toast.LENGTH_SHORT).show();
////                long downId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
////                Log.e("放松放松放松",""+path_addtion);
////                Uri uriForDownloadedFile = dm.getUriForDownloadedFile(downId);
////                Intent intent_get=GetIntent_Utils.openFile("file:///storage/emulated/0/Android/data/your-package/files/Download/"+path_addtion);
////                Log.e("市场的方式传递方式",""+uriForDownloadedFile);
////                startActivity(intent_get);
//                unregisterReceiver(downloadReceiver);
//            }
//        }
//    }



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

    class Summit implements DialogUtil.OnClickListenner {

        public Summit() {
        }

        @Override
        public void yesClick() {

            final String path = list_add.get(position_addtion).getPath();
            final String path_addtion = list_add.get(position_addtion).getOpName();

            DownloadUtil.startDownload(dm, Finish_Convey_Activity.this, path, path_addtion, "正在下载...");

//            downloadReceiver = new DownloadReceiver();
//            IntentFilter intentFilter = new IntentFilter();
//            intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
//            registerReceiver(downloadReceiver, intentFilter);
        }

        @Override
        public void noClick() {}

        @Override
        public void onSingleClick() {

        }
    }

}
