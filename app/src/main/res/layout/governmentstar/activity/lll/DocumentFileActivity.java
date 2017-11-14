package com.lanwei.governmentstar.activity.lll;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.utils.LogUtils;
import com.lanwei.governmentstar.view.MyScrollView_Focus;

//   查阅归档*
public class DocumentFileActivity extends FileBaseActivity implements OnClickListener, MyScrollView_Focus.MyScrollListener {
    private static final String TAG = DocumentFileActivity.class.getSimpleName();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_documentfile;
    }

    @Override
    protected String getAction() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate()");
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        getDate();
    }


    private void getDate() {
        iv_contacts.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        title.setVisibility(View.VISIBLE);
        title.setText("拟制归档");
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
    public void onClick(View v) {
        // TODO Auto-generated method stub
        super.onClick(v);
        switch (v.getId()) {
            case R.id.back: {
                finish();
            }
            break;
            default:
                break;
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
}


//    private TextView tv_address;
//    private ImageView back;
//    private ImageView iv_contacts;
//    private MyScrollView_Focus document_scrollView;
//    private WebView webView;
//    private FloatingActionButton enlarge;
//    private FloatingActionButton enshrink;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
//        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
//        setContentView(R.layout.activity_documentfile);
//
//        initViews();
//    }
//
//    private void initViews() {
//        tv_address = (TextView) findViewById(R.id.tv_address);
//        back = (ImageView) findViewById(R.id.back);
//        iv_contacts = (ImageView) findViewById(R.id.iv_contacts);
//
//        document_scrollView = (MyScrollView_Focus) findViewById(R.id.document_scrollView);
//        document_scrollView.setMyScrollListener(this);
//        webView = (WebView) findViewById(R.id.document_webview);
//        enlarge = (FloatingActionButton) findViewById(R.id.enlarge);
//        enshrink = (FloatingActionButton) findViewById(R.id.enshrink);
//
//        enshrink.setOnClickListener(this);
//        enlarge.setOnClickListener(this);
//
//        tv_address.setVisibility(View.VISIBLE);
//        back.setVisibility(View.VISIBLE);
//        iv_contacts.setVisibility(View.GONE);
//        back.setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.back:
//                finish();
//                break;
//        }
//    }
//
//    @Override
//    public void sendDistanceY(int distance) {
//        Rect scrollBounds = new Rect();
//        document_scrollView.getHitRect(scrollBounds);
//        if (webView.getLocalVisibleRect(scrollBounds)) {
//
//
//            enlarge.setVisibility(View.VISIBLE);
//            enshrink.setVisibility(View.VISIBLE);
//            //子控件至少有一个像素在可视范围内
//            // Any portion of the childView, even a single pixel, is within the visible window
//        } else {
//
//            enlarge.setVisibility(View.GONE);
//            enshrink.setVisibility(View.GONE);
//
//            //子控件完全不在可视范围内
//            // NONE of the childView is within the visible window
//        }
//    }
//
//}

