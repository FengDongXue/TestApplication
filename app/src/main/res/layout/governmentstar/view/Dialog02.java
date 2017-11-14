package com.lanwei.governmentstar.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lanwei.governmentstar.R;

import org.sufficientlysecure.htmltextview.HtmlTextView;


/**
 * Created by YS on 2017/3/6.
 */

public class Dialog02 extends Dialog {
    private Button yes;//确定按钮
    private Button no;//取消按钮
    private TextView Tv_title;//内容标题文本
    private HtmlTextView TV_content;//内容提示文本
    private String Str_title;//从外界设置的title文本
    private String Str_content;//从外界设置的内容文本
    //确定文本和取消文本的显示内容
    private String Str_yes, Str_no;

    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器
    private int titleColor;   //标题颜色
    private int contentColor;  //内容颜色
    private int leftBg;   //左按钮背景
    private int leftColor;  //左按钮字体颜色
    private int rightBg;  //右按钮背景
    private int rightColor; //右按钮字体颜色
    private View line;
    public static LinearLayout view1;
    public boolean isBlack = false;
    private boolean isGone = false;

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param str
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            Str_no = str;
        }
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param str
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(String str, onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            Str_yes = str;
        }
        this.yesOnclickListener = onYesOnclickListener;
    }

    public Dialog02(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog02);

        setCanceledOnTouchOutside(false);//按空白处不能取消动画
        setLeftBtn(R.drawable.select_button_left, Color.WHITE);    //设置左按钮为蓝色，字体为白色
        setRightBtn(R.drawable.select_button_right, Color.WHITE);  //设置右按钮为蓝色，字体为白色
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
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noOnclickListener != null) {
                    noOnclickListener.onNoClick();
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
            Tv_title.setTextColor(titleColor);
        }
        if (Str_content != null) {
//            TV_content.setText(Str_content);
            TV_content.setHtml(Str_content);//用textview展示富文本
            TV_content.setTextColor(contentColor);
        }
        //如果设置按钮的文字 背景 字体
        if (Str_yes != null && leftBg != 0 && leftColor != 0) {
            yes.setText(Str_yes);
            yes.setBackgroundResource(leftBg);
            yes.setTextColor(leftColor);
        }
        //设置取消按钮得文字 背景 字体
        if (Str_no != null && rightBg != 0 && rightColor != 0) {
            no.setText(Str_no);
            no.setBackgroundResource(rightBg);
            no.setTextColor(rightColor);
        }

        if (isBlack) {
            view1.setBackgroundResource(R.drawable.shape_dialog_black);
            isBlack = false;
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
        yes = (Button) findViewById(R.id.yes);
        no = (Button) findViewById(R.id.no);
        Tv_title = (TextView) findViewById(R.id.title);
        TV_content = (HtmlTextView) findViewById(R.id.content);
        line = findViewById(R.id.line);
        view1 = (LinearLayout) findViewById(R.id.ll);
    }

    /**
     * 从外界Activity为Dialog设置标题  以及字体颜色
     *
     * @param title
     */
    public void setTitle(String title, int color) {
        Str_title = title;
        this.titleColor = color;
    }

    /**
     * 从外界Activity为Dialog设置dialog的content  以及字体颜色
     *
     * @param content
     * @param color   字体颜色
     */
    public void setContent(String content, int color) {
        Str_content = content;
        this.contentColor = color;
    }

    /**
     * 设置为黑色
     *
     * @param isBlack
     */
    public void setBgBlack(boolean isBlack) {
        this.isBlack = isBlack;
    }

    public void setLineGone(boolean isGone) {
        this.isGone = isGone;
    }

    /**
     * 设置左边按钮得属性
     *
     * @param leftBg    按钮背景
     * @param textColor 按钮字体颜色
     */
    public void setLeftBtn(int leftBg, int textColor) {
        this.leftBg = leftBg;
        this.leftColor = textColor;
    }

    /**
     * 设置左边按钮得属性
     *
     * @param //leftBg  按钮背景
     * @param textColor 按钮字体颜色
     */
    public void setRightBtn(int rightBg, int textColor) {
        this.rightBg = rightBg;
        this.rightColor = textColor;
    }

    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }
}
