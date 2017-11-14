package com.lanwei.governmentstar.activity.zyx;

import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.view.StatusBarUtils;

/**
 * Created by 蓝威科技-技术部3 on 2017/4/17.
 */

public class ReleaseActivity extends AppCompatActivity implements View.OnClickListener{

    //返回键，头像,添加接收者箭头，选择模板箭头
    private ImageView back,iv_contacts,add_receiver_image,send_template_image;
    //中间标题,右边发送，添加接收者text，选择模板text
    private TextView tv_address,release_send,add_receiver_name,send_template_name;
    //添加接收者，选择模板
    private LinearLayout add_receiver,send_template;
    private PopupWindow popupWindow;
    //添加接收者弹窗
    private View view1;
    //选择模板弹窗
    private View view2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.release_layout);

        initviews();
    }

    private void initviews() {
        back= (ImageView) findViewById(R.id.back);
        iv_contacts= (ImageView) findViewById(R.id.iv_contacts);
        tv_address= (TextView) findViewById(R.id.tv_address);
        release_send= (TextView) findViewById(R.id.release_send);
        add_receiver= (LinearLayout) findViewById(R.id.add_receiver);
        send_template= (LinearLayout) findViewById(R.id.send_template);
        add_receiver_image= (ImageView) findViewById(R.id.add_receiver_image);
        send_template_image= (ImageView) findViewById(R.id.send_template_image);
        add_receiver_name= (TextView) findViewById(R.id.add_receiver_name);
        send_template_name= (TextView) findViewById(R.id.send_template_name);

        iv_contacts.setVisibility(View.GONE);
        tv_address.setText("公告编辑");
        tv_address.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        release_send.setVisibility(View.VISIBLE);

        back.setOnClickListener(this);
        release_send.setOnClickListener(this);
        add_receiver.setOnClickListener(this);
        send_template.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Boolean isChoose=false;
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            //发送
            case R.id.release_send:
                break;
            //添加接收者
            case R.id.add_receiver:
                isChoose=true;
                if(isChoose){
                    add_receiver_image.setImageResource(R.mipmap.xy);
                    send_template_image.setImageResource(R.mipmap.xx);
                    // 加载popupwindow的布局
                    view1 = getLayoutInflater().inflate(R.layout.add_receiver_layout, null);
                    popupWindow = new PopupWindow(view1, ViewGroup.LayoutParams.MATCH_PARENT, 800, true);
                    // 点击屏幕之外的区域可否让popupwindow消失
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupWindow.setOnDismissListener(new PoponDismissListener());
                    // 设置popupwindow的显示位置
                    popupWindow.showAsDropDown(add_receiver, 0, 0);
                }else {
                    add_receiver_image.setImageResource(R.mipmap.xx);
                }
                break;
            //选择模板
            case R.id.send_template:
                isChoose=true;
                if(isChoose){
                    send_template_image.setImageResource(R.mipmap.xy);
                    add_receiver_image.setImageResource(R.mipmap.xx);
                    // 加载popupwindow的布局
                    view2 = getLayoutInflater().inflate(R.layout.send_template_layout, null);
                    popupWindow = new PopupWindow(view2, ViewGroup.LayoutParams.MATCH_PARENT, 800, true);
                    // 点击屏幕之外的区域可否让popupwindow消失
                    popupWindow.setFocusable(true);
                    popupWindow.setBackgroundDrawable(new BitmapDrawable());
                    popupWindow.setOnDismissListener(new PoponDismissListener());
                    // 设置popupwindow的显示位置
                    popupWindow.showAsDropDown(send_template, 0, 0);
                }else {
                    send_template_image.setImageResource(R.mipmap.xx);
                }
                break;
        }
    }

    // popupwindow消失后触发的方法
    class PoponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            add_receiver_image.setImageResource(R.mipmap.xx);
            send_template_image.setImageResource(R.mipmap.xx);
        }
    }

}
