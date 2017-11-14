package com.lanwei.governmentstar.activity.zyx;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.view.StatusBarUtils;

/**
 * Created by 蓝威科技-技术部3 on 2017/4/25.
 */

public class DocumentHanddownActivity extends AppCompatActivity implements View.OnClickListener{

    //中间标题
    private TextView tv_address;
    //返回键,圆角图  模糊查询
    private ImageView back,iv_contacts,document_mohu;
    //仅显示正在办理  仅显示已办结
    private RelativeLayout inprogress,tabfile;
    private View inprogress_line,tabfile_line;
    private String process_select =null;
    private String mark_select =null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.documenthanddown_layout);

        initview();
    }

    private void initview() {
        tv_address= (TextView) findViewById(R.id.tv_address);
        back= (ImageView) findViewById(R.id.back);
        iv_contacts= (ImageView) findViewById(R.id.iv_contacts);
        document_mohu= (ImageView) findViewById(R.id.document_mohu);
        inprogress= (RelativeLayout) findViewById(R.id.inprogress);
        tabfile= (RelativeLayout) findViewById(R.id.tabfile);
        inprogress_line= (View) findViewById(R.id.inprogress_line);
        tabfile_line= (View) findViewById(R.id.tabfile_line);


        // 初始化是否点击过的样式的标记
        process_select="true";  // 默认显示的内容
        mark_select="false";

        // 初始化默认的样式
        inprogress_line.setVisibility(View.VISIBLE);
        tabfile_line.setVisibility(View.INVISIBLE);
        inprogress.setSelected(true);
        tabfile.setSelected(false);
        tv_address.setVisibility(View.VISIBLE);
        tv_address.setText("公文下发");
        back.setVisibility(View.VISIBLE);
        iv_contacts.setVisibility(View.GONE);
        back.setOnClickListener(this);
        inprogress.setOnClickListener(this);
        tabfile.setOnClickListener(this);
        document_mohu.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            //仅显示正在办理
            case R.id.inprogress:
                if(process_select.equals("false")) {
                    inprogress_line.setVisibility(View.VISIBLE);
                    tabfile_line.setVisibility(View.INVISIBLE);
                    inprogress.setSelected(true);
                    tabfile.setSelected(false);
                    process_select = "true";
                    mark_select = "false";
                }
                break;
            //仅显示已办结
            case R.id.tabfile:
                if(mark_select.equals("false")) {
                    inprogress_line.setVisibility(View.INVISIBLE);
                    tabfile_line.setVisibility(View.VISIBLE);
                    inprogress.setSelected(false);
                    tabfile.setSelected(true);
                    process_select = "false";
                    mark_select = "true";
                }
                break;
            //模糊按钮
            case R.id.document_mohu:

                break;
        }
    }

}
