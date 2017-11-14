package com.lanwei.governmentstar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * Created by 蓝威科技-技术开发1 on 2017/5/15.
 */

public class CustomWebView extends WebView {

    public CustomWebView(Context context) {
        super(context);
    }

    public CustomWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public CustomWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    public int getContentHeight(){
        return computeVerticalScrollRange();
    }


}
