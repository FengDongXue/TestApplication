package com.lanwei.governmentstar.activity.lll;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.Addtion_Details_Activity;
import com.lanwei.governmentstar.activity.Process2_Activity;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.utils.LogUtils;
import com.lanwei.governmentstar.view.MyScrollView_Focus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/30.
 */
//传阅归档
public class WatchDocumentActivity extends FileBaseActivity implements View.OnClickListener, MyScrollView_Focus.MyScrollListener {
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
        title.setText("传阅归档");
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