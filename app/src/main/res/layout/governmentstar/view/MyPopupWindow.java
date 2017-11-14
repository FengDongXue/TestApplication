package com.lanwei.governmentstar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

import com.lanwei.governmentstar.interfaces.OnDismiss;

/**
 * Created by 蓝威科技-技术开发1 on 2017/8/31.
 */

public class MyPopupWindow extends PopupWindow {


    public MyPopupWindow(Context context) {
        super(context);
    }

    public MyPopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyPopupWindow() {
    }

    public MyPopupWindow(View contentView) {
        super(contentView);
    }

    public MyPopupWindow(int width, int height) {
        super(width, height);
    }

    public MyPopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public MyPopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    private OnDismiss ondismiss;


    public void setDismiss(OnDismiss ondismiss){
        this.ondismiss = ondismiss;
    }


    @Override
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        ondismiss.dismiss_soft();
        super.setOnDismissListener(onDismissListener);
    }




}
