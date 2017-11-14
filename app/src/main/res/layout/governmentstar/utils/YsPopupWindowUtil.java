package com.lanwei.governmentstar.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lanwei.governmentstar.R;

/**
 * Created by Administrator on 2017/4/11.
 */

public class YsPopupWindowUtil {
    private PopupWindow popupWindow;
    private Context context;
    private String msg = "";
    public YsPopupWindowUtil(Context context, String msg) {
        this.context = context;
        this.msg = msg;
    }

    public void show() {

        View view = LayoutInflater.from(context).inflate(R.layout.loading,null);
        TextView textView = (TextView) view.findViewById(R.id.jiazai);
        if (msg != null && !msg.equals("")){
            textView.setText(msg);
        } else {
            textView.setText("加载中....");
        }
        popupWindow=new PopupWindow(view, 500, ViewGroup.LayoutParams.WRAP_CONTENT, true);

        // 点击屏幕之外的区域可否让popupwindow消失
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        View rootview = LayoutInflater.from(context).inflate(R.layout.logginglayout, null);
        // 设置popupwindow的显示位置
        popupWindow.showAtLocation(rootview, Gravity.CENTER,0,0);

        // 加载动画效果
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.loading_anim);
        ImageView rotate=(ImageView) view.findViewById(R.id.rotate);
        rotate.startAnimation(animation);
    }

    public void dismiss(){
        popupWindow.dismiss();
    }
}
