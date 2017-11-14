package com.lanwei.governmentstar.activity.spsq;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.spsq.adapter.FilesOutPublicAdapter;
import com.lanwei.governmentstar.activity.spsq.adapter.FilesQTAdapter;
import com.lanwei.governmentstar.activity.spsq.adapter.OutPublicFlowAdapter;
import com.lanwei.governmentstar.activity.spsq.adapter.OutQJFlowAdapter;
import com.lanwei.governmentstar.bean.SpsqOutApplyDetails;
import com.lanwei.governmentstar.bean.SpsqQTApplyDetails;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.LogUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22/022.
 */

public class OtherTyspSpActivity extends ApplyBaseActivity implements View.OnClickListener {
    private static final String TAG = SealUsesealSpActivity.class.getSimpleName();
    private LinearLayout apply_type;
    private LinearLayout apply_content;
    private LinearLayout apply_apply;
    private LinearLayout apply_details;
    private FilesQTAdapter adapter;
    private OutQJFlowAdapter flowAdapter;
    private TextView apply_content_content;
    private TextView apply_details_content;
    private WebView apply_other_content;
    private FloatingActionButton enlarge;
    private FloatingActionButton enshrink;

    @Override
    protected int getLayoutResId() {
        return R.layout.sp_applylicense;
    }

    @Override
    protected String getAction() {
        return "applylicense";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate()");
        init();
    }

    private void init() {
        final Intent intent = getIntent();
        // TODO Auto-generated method stub
        apply_details = (LinearLayout) findViewById(R.id.apply_details);
        apply_content = (LinearLayout) findViewById(R.id.apply_content);
        apply_type = (LinearLayout) findViewById(R.id.apply_type);
        apply_apply = (LinearLayout) findViewById(R.id.apply_apply);
        apply_content_content = (TextView) findViewById(R.id.apply_content_content);
        apply_details_content = (TextView) findViewById(R.id.apply_details_content);
        apply_other_content = (WebView) findViewById(R.id.apply_other_content);
        apply_other_content.getSettings().setJavaScriptEnabled(true);
        apply_other_content.setVisibility(View.VISIBLE);

        apply_type.setVisibility(View.VISIBLE);
        apply_apply.setVisibility(View.VISIBLE);
        llNumsp.setVisibility(View.GONE);
        llDatesp.setVisibility(View.GONE);

        enlarge = (FloatingActionButton) findViewById(R.id.enlarge);
        enshrink = (FloatingActionButton) findViewById(R.id.enshrink);
        enlarge.setVisibility(View.VISIBLE);
        enshrink.setVisibility(View.VISIBLE);
        enlarge.setBackgroundTintList(getResources().getColorStateList(R.color.white));
        enshrink.setBackgroundTintList(getResources().getColorStateList(R.color.white));

        getData();

    }

    private void getData() {
        more.setVisibility(View.VISIBLE);
        more.setText("转交审批");
        title.setText("通用申请审批单");
        title.setVisibility(View.VISIBLE);
        iv_contacts.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        applyNo.setOnClickListener(this);
        applyYes.setOnClickListener(this);
        flow.setOnClickListener(this);
        flow2.setOnClickListener(this);
        enshrink.setOnClickListener(this);
        enlarge.setOnClickListener(this);

        llThingsType.setVisibility(View.GONE);        //物品类型
        llThingsReceive.setVisibility(View.GONE);          //领用物品
        llOutType.setVisibility(View.GONE);        //外出类型
        llJfTime.setVisibility(View.GONE);        //交付日期
        llRentStart.setVisibility(View.GONE);         //租用开始
        llRentOver.setVisibility(View.GONE);          //租用结束
        llRentType.setVisibility(View.GONE);          //租用类型
        llCustomType.setVisibility(View.GONE);        //定制类型
        llPurchaseType.setVisibility(View.GONE);        //采购类型
        llInvokesp.setVisibility(View.GONE);        //调用类型
        llzzName.setVisibility(View.GONE);        //资质名称
        llzzType.setVisibility(View.GONE);        //资质类别
        llUsesp.setVisibility(View.GONE);        //用印文件
        llDocumentsp.setVisibility(View.GONE);        //文件类型
        llSealsp.setVisibility(View.GONE);        //印章类型
        llWjnumsp.setVisibility(View.GONE);        //文件份数
        llBorrow.setVisibility(View.GONE);        //借用日期
        llReturn.setVisibility(View.GONE);        //归还日期
        llStartTime.setVisibility(View.GONE);        //开始时间
        llReturnTime.setVisibility(View.GONE);        //返回时间
        llBorrowType.setVisibility(View.GONE);        //借用类型
        llThingsName.setVisibility(View.GONE);        //物品名称
        llGg.setVisibility(View.GONE);        //规格
        llGzf.setVisibility(View.GONE);   //供租方
        llLink.setVisibility(View.GONE);   //联系方式
        llThingsNum.setVisibility(View.GONE);        //数量
        llKcNum.setVisibility(View.GONE);  //库存数量
        llLyNum.setVisibility(View.GONE);   //领用数量
        llReceiveXq.setVisibility(View.GONE);  //领用详情
        llThingsDw.setVisibility(View.GONE);        //单位
        llThingsPrice.setVisibility(View.GONE);        //价格
        llStartPlace.setVisibility(View.GONE);             //出发地点
        llUseCar.setVisibility(View.GONE);        //使用车辆
        llOtherRequest.setVisibility(View.GONE);        //其他要求
        llPublicCar.setVisibility(View.GONE);        //公务用车
        llBusinessMoney.setVisibility(View.GONE);        //差旅费用
        llBusinessTime.setVisibility(View.GONE);        //出差日期
        llProof.setVisibility(View.GONE);    //报销凭证
        llQjType.setVisibility(View.GONE);   //请假类型
        llInstructsp.setVisibility(View.GONE);  //备注说明

        llWorkType.setVisibility(View.GONE);          //工作类型
        llProjectName.setVisibility(View.GONE);       //项目名称
        llNeedMoney.setVisibility(View.GONE);         //资金需求
        llPersonMoney.setVisibility(View.GONE);       //人员需求
        llProjectAbout.setVisibility(View.GONE);      //项目概述

        llXzReason.setVisibility(View.GONE);          //协作事由
        llPersonFq.setVisibility(View.GONE);         //发起方
        llPersonXz.setVisibility(View.GONE);          //协作方

        llContractNum.setVisibility(View.GONE);         //合同编号
        llMyFz.setVisibility(View.GONE);         //我方负责人
        llContractType.setVisibility(View.GONE);          //合同类型
        llWorkOther.setVisibility(View.GONE);         //对方单位
        llOtherFz.setVisibility(View.GONE);        //对方负责人

        llQsReason.setVisibility(View.GONE);        //请示事由
        llJjd.setVisibility(View.GONE);             //紧急度

        llUseType.setVisibility(View.GONE);          //用途类型
        llMoneyApply.setVisibility(View.GONE);         //申请金额
        llMoneytype.setVisibility(View.GONE);        //资金类型

        llMoneyBx.setVisibility(View.GONE);          //报销金额
        llBxtype.setVisibility(View.GONE);         //报销类别

        llPersonsType.setVisibility(View.GONE);          //人事类型
        llSsgw.setVisibility(View.GONE);          //所属岗位

        llXqGw.setVisibility(View.GONE);           //需求岗位
        llXqPerson.setVisibility(View.GONE);           //需求人数
        llZpReason.setVisibility(View.GONE);          //招聘原因
        llDgTime.setVisibility(View.GONE);         //到岗日期
        llGwPerson.setVisibility(View.GONE);           //当前岗位人数

        llDpType.setVisibility(View.GONE);          //调派类型
        llDqGw.setVisibility(View.GONE);          //当前岗位
        llDpGw.setVisibility(View.GONE);         //调派岗位

        llGwZj.setVisibility(View.GONE);          //岗位职级
        llZgTime.setVisibility(View.GONE);          //在岗年限
        llHbZj.setVisibility(View.GONE);         //后备职级

        llSqSy.setVisibility(View.GONE);         //申请事由

        llJdTheme.setVisibility(View.GONE);          //接待主体
        llJdType.setVisibility(View.GONE);         //接待类型
        llCjRy.setVisibility(View.GONE);          //参加人员

        llHyName.setVisibility(View.GONE);          //会议名称
        llHyType.setVisibility(View.GONE);          //会议类型
        llZbf.setVisibility(View.GONE);          //主办方
        llHys.setVisibility(View.GONE);          //会议室

        llActivityTheme.setVisibility(View.GONE);         //活动主题
        llActivityType.setVisibility(View.GONE);          //活动类型

        llPayAll.setVisibility(View.GONE);        //付款总额
        llPayType.setVisibility(View.GONE);          //付款方式
        llZfPerson.setVisibility(View.GONE);          //支付对象
        llKhh.setVisibility(View.GONE);          //开户行
        llYhzh.setVisibility(View.GONE);         //银行账户


        apply_details_content.setVisibility(View.GONE);         //
        apply_content_content.setVisibility(View.GONE);         //

        look.setOnClickListener(this);
        inlook.setOnClickListener(this);

        apply_theme.setPadding(0,0,0,30);
        initWebView();
        getdata();

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
            case R.id.look:
                rv_fjname.setVisibility(View.VISIBLE);
                look.setVisibility(View.GONE);
                inlook.setVisibility(View.VISIBLE);
                inlookLine.setVisibility(View.VISIBLE);
                break;

            case R.id.inlook:
                rv_fjname.setVisibility(View.GONE);
                look.setVisibility(View.VISIBLE);
                inlook.setVisibility(View.GONE);
                inlookLine.setVisibility(View.GONE);
                break;

            case R.id.apply_yes:
                getAgreeData();
                break;

            case R.id.apply_no:
                getRejectData();
                break;


            case R.id.enlarge:
                //放大
                apply_other_content.loadUrl("javascript:amplify()");

                break;


            case R.id.enshrink:
                //缩小
                apply_other_content.loadUrl("javascript:narrow()");

                break;
        }
    }

    private void initWebView() {
        WebSettings webSettings = apply_other_content.getSettings();
        webSettings.setJavaScriptEnabled(true);
        apply_other_content.getSettings().setTextZoom(100);

        // webview和scrollview的滑动冲突，设置webview不响应点击（包括滑动）事件
        apply_other_content.setOnTouchListener(new View.OnTouchListener() {

            @Override

            public boolean onTouch(View v, MotionEvent ev) {

                ((WebView) v).requestDisallowInterceptTouchEvent(false);
                return true;

            }

        });
        // 取消webview的垂直，水平
        apply_other_content.setVerticalScrollBarEnabled(false);
        apply_other_content.setHorizontalScrollBarEnabled(false);
    }



    private void getdata() {
        Intent intent = getIntent();
        String opId = intent.getStringExtra("opId");
        Log.d("sb", opId);
        String userId = intent.getStringExtra("userId");
        RetrofitHelper.getInstance().spsqShxx(opId, userId, "spsqShxx", new CallBackYSAdapter() {
            @Override
            protected void showErrorMessage(String message) {
                Log.e("mes", message);
            }

            @Override
            protected void parseJson(String data) {
                Log.e("data11", data);
                if (data != null) {
                    Gson gson = new Gson();
                    SpsqQTApplyDetails spsqQTApplyDetails = gson.fromJson(data, SpsqQTApplyDetails.class);
                    String opApplicationNo = spsqQTApplyDetails.getApplicationNo();
                    String opTime = spsqQTApplyDetails.getOpTime();
                    String deptName = spsqQTApplyDetails.getDeptName();
                    String opTypeName = spsqQTApplyDetails.getType();
                    String opChildTypeName = spsqQTApplyDetails.getChildType();
                    String csRlist = spsqQTApplyDetails.getCSRlist();
                    String person = spsqQTApplyDetails.getPerson();
                    String personImage = spsqQTApplyDetails.getPersonImage();

//                    apply_content_content.setText(spsqQTApplyDetails.getOtherUniversalDetail());
//                    apply_details_content.setText(spsqQTApplyDetails.getOtherUniversalReason());
                    apply_other_content.loadUrl(spsqQTApplyDetails.getDocContent());

                    Glide.with(OtherTyspSpActivity.this)
                            .load(personImage)
                            .into(pic_head);
                    deptname.setText(deptName);
                    sqname.setText(person);

                    applyNumsp.setText(opApplicationNo);
                    applyDatesp.setText(opTime);
                    applyOutType.setText(opTypeName + "-" + opChildTypeName);
                    apply_theme.setText(spsqQTApplyDetails.getBT());
                    if (csRlist.equals("")) {
                        rlPersonCs.setVisibility(View.GONE);
                    } else {
                        applyPersonCs.setText(csRlist);
                    }

                    //附件
                    if (spsqQTApplyDetails.getFiles() == null | spsqQTApplyDetails.getFiles().size() <= 0) {
                        sq_fj.setVisibility(View.GONE);
                    } else {
                        sq_fj.setVisibility(View.VISIBLE);
                        rv_fjname.setVisibility(View.VISIBLE);
                        if (spsqQTApplyDetails.getFiles().size() > 3) {
                            look.setVisibility(View.VISIBLE);
                            inlook.setVisibility(View.GONE);
                            inlookLine.setVisibility(View.GONE);
                            rv_fjname.setVisibility(View.GONE);
                        }
                        adapter = new FilesQTAdapter(OtherTyspSpActivity.this,spsqQTApplyDetails.getFiles());
                        rv_fjname.setLayoutManager(new LinearLayoutManager(OtherTyspSpActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
                        rv_fjname.setAdapter(adapter);
                    }

                    //流程
                    if (spsqQTApplyDetails.getData() == null | spsqQTApplyDetails.getData().size() <= 0) {
                        apply_listview.setVisibility(View.GONE);
                        flowLine.setVisibility(View.GONE);
                    } else {
                        apply_listview.setVisibility(View.VISIBLE);
                        flowLine.setVisibility(View.VISIBLE);
                        flowAdapter = new OutQJFlowAdapter(spsqQTApplyDetails.getData(), OtherTyspSpActivity.this);
                        apply_listview.setAdapter(flowAdapter);
                    }

                }
            }
        });


    }
}

