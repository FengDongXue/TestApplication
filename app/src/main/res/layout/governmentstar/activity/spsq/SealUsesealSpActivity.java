package com.lanwei.governmentstar.activity.spsq;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.spsq.adapter.FilesUsesealAdapter;
import com.lanwei.governmentstar.activity.spsq.adapter.UsesealFlowAdapter;
import com.lanwei.governmentstar.bean.SpsqSealApplyDetails;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.LogUtils;

/**
 * Created by Administrator on 2017/8/10/010.
 */

public class SealUsesealSpActivity extends ApplyBaseActivity implements View.OnClickListener {
    private static final String TAG = SealUsesealSpActivity.class.getSimpleName();
    private FilesUsesealAdapter adapter;
    private UsesealFlowAdapter flowAdapter;


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
        initView();
        getData();
    }

    private void initView() {
        more.setVisibility(View.VISIBLE);
        more.setText("转交审批");
        title.setText("用印申请审批单");
        title.setVisibility(View.VISIBLE);
        iv_contacts.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        applyNo.setOnClickListener(this);
        applyYes.setOnClickListener(this);
        flow.setOnClickListener(this);
        flow2.setOnClickListener(this);

        llThingsType.setVisibility(View.GONE);        //物品类型
        llThingsReceive.setVisibility(View.GONE);          //领用物品
        llOutType.setVisibility(View.GONE);        //外出类型
        llJfTime.setVisibility(View.GONE);        //交付时间
        llRentStart.setVisibility(View.GONE);         //租用开始
        llRentOver.setVisibility(View.GONE);          //租用结束
        llRentType.setVisibility(View.GONE);          //租用类型
        llCustomType.setVisibility(View.GONE);        //定制类型
        llPurchaseType.setVisibility(View.GONE);        //采购类型
        llzzName.setVisibility(View.GONE);        //资质名称
        llzzType.setVisibility(View.GONE);        //资质类别
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
        llAboutDocument.setVisibility(View.GONE);   //相关文件
        llProof.setVisibility(View.GONE);    //报销凭证
        llQjType.setVisibility(View.GONE);   //请假类型
        llPictures.setVisibility(View.GONE);   //上传图像

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

        llApplyType.setVisibility(View.GONE);          //申请类型
        llGwZj.setVisibility(View.GONE);          //岗位职级
        llZgTime.setVisibility(View.GONE);          //在岗年限
        llHbZj.setVisibility(View.GONE);         //后备职级

        llSqSy.setVisibility(View.GONE);         //申请事由

        llJdTheme.setVisibility(View.GONE);          //接待主体
        llJdType.setVisibility(View.GONE);         //接待类型
        llCjRy.setVisibility(View.GONE);          //参加人员

        llApplyContent.setVisibility(View.GONE);          //申请内容
        llSpSq.setVisibility(View.GONE);           //审批申请

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

        look.setOnClickListener(this);
        inlook.setOnClickListener(this);
    }

    private void getData() {
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
                    SpsqSealApplyDetails spsqSealApplyDetails = gson.fromJson(data, SpsqSealApplyDetails.class);
                    String opApplicationNo = spsqSealApplyDetails.getApplicationNo();
                    String opTime = spsqSealApplyDetails.getOpTime();
                    String deptName = spsqSealApplyDetails.getDeptName();
                    String opTypeName = spsqSealApplyDetails.getType();
                    String opChildTypeName = spsqSealApplyDetails.getChildType();
                    String stampApplicationFilename = spsqSealApplyDetails.getStampApplicationFilename();
                    String stampApplicationFiletype = spsqSealApplyDetails.getStampApplicationFiletype();
                    String stampType = spsqSealApplyDetails.getStampType();
                    String stampApplicationFilenum = spsqSealApplyDetails.getStampApplicationFilenum();
                    String opReason = spsqSealApplyDetails.getStampApplicationReason();
                    String csRlist = spsqSealApplyDetails.getCSRlist();
                    String person = spsqSealApplyDetails.getPerson();
                    String personImage = spsqSealApplyDetails.getPersonImage();


                    Glide.with(SealUsesealSpActivity.this)
                            .load(personImage)
                            .into(pic_head);
                    deptname.setText(deptName);
                    sqname.setText(person);

                    applyNumsp.setText(opApplicationNo);
                    applyDatesp.setText(opTime);
                    applyInvokesp.setText(opTypeName + "-" + opChildTypeName);
                    applyUsesp.setText(stampApplicationFilename);
                    applDocumentsp.setText(stampApplicationFiletype);
                    applySealsp.setText(stampType);
                    applyWjnumsp.setText(stampApplicationFilenum);
                    applyInstructsp.setText(opReason);
                    apply_theme.setText(spsqSealApplyDetails.getBT());
                    if (csRlist.equals("")) {
                        rlPersonCs.setVisibility(View.GONE);
                    } else {
                        applyPersonCs.setText(csRlist);
                    }

                    //附件
                    if (spsqSealApplyDetails.getFiles() == null | spsqSealApplyDetails.getFiles().size() <= 0) {
                        sq_fj.setVisibility(View.GONE);
                    } else {
                        sq_fj.setVisibility(View.VISIBLE);
                        rv_fjname.setVisibility(View.VISIBLE);
                        if (spsqSealApplyDetails.getFiles().size() > 3) {
                            look.setVisibility(View.VISIBLE);
                            inlook.setVisibility(View.GONE);
                            inlookLine.setVisibility(View.GONE);
                            rv_fjname.setVisibility(View.GONE);
                        }

                        adapter = new FilesUsesealAdapter(SealUsesealSpActivity.this, spsqSealApplyDetails.getFiles());
                        rv_fjname.setLayoutManager(new LinearLayoutManager(SealUsesealSpActivity.this, LinearLayoutManager.VERTICAL, false){

                            @Override
                            public boolean canScrollVertically() {
                                return false;
                            }
                        });   //为recyclerView指定现行垂直布局
                        rv_fjname.setAdapter(adapter);
                    }

                    //流程
                    if (spsqSealApplyDetails.getData() == null | spsqSealApplyDetails.getData().size() <= 0) {
                        apply_listview.setVisibility(View.GONE);
                        flowLine.setVisibility(View.GONE);
                    }else {
                        apply_listview.setVisibility(View.VISIBLE);
                        flowLine.setVisibility(View.VISIBLE);
                        flowAdapter = new UsesealFlowAdapter(spsqSealApplyDetails.getData(), SealUsesealSpActivity.this);
                        apply_listview.setAdapter(flowAdapter);
                    }

                }
            }
        });

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
        }
    }


}
