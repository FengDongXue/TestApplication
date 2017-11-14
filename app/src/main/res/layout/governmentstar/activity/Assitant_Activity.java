package com.lanwei.governmentstar.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.Return_Finish;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.view.MyScrollView_Focus;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/6/28.
 */

public class Assitant_Activity extends BaseActivity implements View.OnClickListener ,MyScrollView_Focus.MyScrollListener{

    private ImageView back;
    private TextView title;
    private TextView see;
    private TextView department;
    private TextView theme;
    private TextView theme2;
    private TextView content;
    private TextView date;
    private TextView emergency;
    private Return_Finish.Data data_return;
    private PopupWindow popupWindow;
    private ArrayList<Return_Finish.Filelist> list_add;
    private ArrayList<Return_Finish.Imagelist> list_image =new ArrayList<Return_Finish.Imagelist>();
    //    private ArrayList<Addtion_List> data_list;
    private DownloadManager dm;
    private RecyclerView lv;
    private Finish_Convey_Activity.Adapter_Addtion adapter_addtion;
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
    private Finish_Convey_Activity.Adapter_Addtion2 adapter;
    private MyScrollView_Focus document_scrollView;
    private int distance_focus;
    private int distance_active;
    private FloatingActionButton enlarge;
    private FloatingActionButton enshrink;
    private int position_download;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assist);

        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        initweight();


        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        retrofit2.Call<Return_Finish> call= api.swcyXb(getIntent().getStringExtra("opId"));
        call.enqueue(new Callback<Return_Finish>() {
            @Override
            public void onResponse(Call<Return_Finish> call, Response<Return_Finish> response) {



            }

            @Override
            public void onFailure(Call<Return_Finish> call, Throwable t) {



            }

        });






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
        dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

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
}
