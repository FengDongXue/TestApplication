package com.lanwei.governmentstar.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lanwei.governmentstar.R;

/**
 * Created by YS on 2017/3/6.
 */

public class Dialog01 extends Dialog {
    private Button single;//绑定按钮
    private TextView Tv_title;//内容标题文本
    private TextView TV_content;//内容提示文本
    private String Str_title;//从外界设置的title文本
    private String Str_content;//从外界设置的内容文本
    //确定文本和取消文本的显示内容
    private String Str_single;

    private onsingleOnclickListener singleOnclickListener;//绑定按钮被点击了的监听器
    private int resid;   //传进来得点击得背景图片
    private View line;
    private int titColor;  //标题颜色
    private int conColor;  //内容
    private boolean isGone = false;

    /**
     * 设置绑定按钮的显示内容和监听
     *
     * @param str
     * @param onSingleOnclickListener
     */
    public void setSingleOnclickListener(String str, onsingleOnclickListener onSingleOnclickListener) {
        if (str != null) {
            Str_single = str;
        }
        this.singleOnclickListener = onSingleOnclickListener;
    }

    public Dialog01(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog01);

        setCanceledOnTouchOutside(false);//按空白处不能取消动画
        setOnKeyListener();//点击返回键不消失
        setCancelable(false);//点击返回键不消失
        setBtnImage(R.drawable.select_button_blue);  //设置单按钮为蓝色

        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

        Window window = getWindow();
        //设置显示动画
        window.setWindowAnimations(R.style.dialog_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = -this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / 50;
        //设置显示位置
        onWindowAttributesChanged(wl);//设置点击dialog消失后外围解散的动画效果


    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置绑定按钮被点击后，向外界提供监听
        single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (singleOnclickListener != null) {
                    singleOnclickListener.onSingleClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message
        if (Str_title != null) {
            Tv_title.setText(Str_title);
            Tv_title.setTextColor(titColor);
        }
        if (Str_content != null) {
            TV_content.setText(Str_content);
            TV_content.setTextColor(conColor);
        }
        //如果设置按钮的文字
        if (Str_single != null) {
            single.setText(Str_single);
            single.setBackgroundResource(resid);  //设置点击事件得背景
        }

        if (isGone) {
            line.setVisibility(View.GONE);
            isGone = false;
        }

    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        single = (Button) findViewById(R.id.single);
        Tv_title = (TextView) findViewById(R.id.title);
        TV_content = (TextView) findViewById(R.id.content);
        line = findViewById(R.id.line);
    }

    public void setBtnImage(int resid) {
        this.resid = resid;
    }

    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title
     */
    public void setTitle(String title, int titColor) {
        Str_title = title;
        this.titColor = titColor;
    }

    /**
     * 设置分割线隐藏
     */
    public void setLineGone(boolean isGone) {
        this.isGone = isGone;
    }

    /**
     * 从外界Activity为Dialog设置dialog的content
     *
     * @param content
     */
    public void setContent(String content, int conColor) {
        Str_content = content;
        this.conColor = conColor;
    }

    /**
     * 设置绑定按钮被点击的接口
     */
    public interface onsingleOnclickListener {
        public void onSingleClick();
    }

    public void setOnKeyListener() {
        OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    return true;
                } else {
                    return false;//默认返回 false，这里false不能屏蔽返回键，改成true就可以了
                }
            }
        };
    }

}
