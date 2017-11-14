package com.lanwei.governmentstar.activity.zyx;

import android.content.Intent;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.Process2_Activity;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by YS on 2017/5/15.
 */

public class DetailsFJActivity extends AutoLayoutActivity implements View.OnClickListener {
    private WebView webView;
    private TextView title;

    private ImageView back2;
    private LinearLayout father;
    private LinearLayout linearLayout2;
    private FrameLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
        setContentView(R.layout.activity_details);
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
        title = (TextView) findViewById(R.id.title);

        back2 = (ImageView) findViewById(R.id.back2);
        father = (LinearLayout) findViewById(R.id.father);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        linearLayout = (FrameLayout) findViewById(R.id.layout_title);
        linearLayout2.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);

        title.setVisibility(View.VISIBLE);
        back2.setVisibility(View.VISIBLE);

        Intent intent=getIntent();

        String type=intent.getStringExtra("type");

        if(type.equals("0")){
            title.setText("图文详情");
        }else if (type.equals("1")){
            title.setText("附件详情");
        } else if (type.equals("2")){
            title.setText("预览");
        }

        back2.setOnClickListener(this);

        webView = (WebView) findViewById(R.id.details_webview);
        String downLoadUrl = intent.getStringExtra("filePreview");

//        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.getCacheMode();
        //声明WebSettings子类
        webSettings.setJavaScriptEnabled(true);

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式


        if (Build.VERSION.SDK_INT <= 22){
            father.setLayerType(View.LAYER_TYPE_HARDWARE,null);
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
            Log.e("软件加速","硬件加速");
//            father.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
//            webView.setLayerType(View.LAYER_TYPE_HARDWARE,null);

        }

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);   //在2.3上面不加这句话，可以加载出页面，在4.0上面必须要加入，不然出现白屏
                return true;
            }

            //加载https时候，需要加入 下面代码

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

                handler.proceed();  //接受所有证书

            }

        });

//        webView.getSettings().setTextZoom(100);


//        webView.getSettings().setJavaScriptEnabled(true);// 设置支持javascript脚本
//
//        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setSupportZoom(true);
//        webView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //设置 缓存模式
//        webView.getSettings().setDomStorageEnabled(true);
//        webView.getSettings().setAllowFileAccess(true); //设置可以访问文件
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
//        webView.getSettings().setLoadsImagesAutomatically(true); //支持自动加载图片
//        webView.getSettings().setDefaultTextEncodingName("utf-8");//设置编码格式
//        //开启 database storage API 功能
//        webView.getSettings().setDatabaseEnabled(true);
//        webView.getSettings().setLoadWithOverviewMode(true); // 自适应
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        //开启 Application Caches 功能
//        webView.getSettings().setAppCacheEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.getSettings().setBuiltInZoomControls(true); // 显示放大缩小 controler
//        webView.getSettings().setSupportZoom(true); // 可以缩放
//        webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.CLOSE);// 默认缩放模式


        webView.loadUrl(downLoadUrl);
//        webView.loadDataWithBaseURL(null, downLoadUrl, "text/html", "UTF-8", null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        webView.pauseTimers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.resumeTimers();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back2:
                finish();
                break;
        }
    }
}
