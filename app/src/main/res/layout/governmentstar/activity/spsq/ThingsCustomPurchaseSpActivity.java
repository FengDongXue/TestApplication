package com.lanwei.governmentstar.activity.spsq;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.utils.LogUtils;

/**
 * Created by Administrator on 2017/8/16/016.
 */

public class ThingsCustomPurchaseSpActivity extends ApplyBaseActivity implements View.OnClickListener {
    private static final String TAG = SealUsesealSpActivity.class.getSimpleName();

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
        getData();

    }

    private void getData() {
        more.setVisibility(View.VISIBLE);
        more.setText("转交审批");
        title.setText("定制采购审批单");
        title.setVisibility(View.VISIBLE);
        iv_contacts.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        applyNo.setOnClickListener(this);
        applyYes.setOnClickListener(this);
        flow.setOnClickListener(this);
        flow2.setOnClickListener(this);

        llThingsReceive.setVisibility(View.GONE);          //领用物品
        llOutType.setVisibility(View.GONE);        //外出类型
        llRentStart.setVisibility(View.GONE);         //租用开始
        llRentOver.setVisibility(View.GONE);          //租用结束
        llRentType.setVisibility(View.GONE);          //租用类型
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
        llKcNum.setVisibility(View.GONE);  //库存数量
        llLyNum.setVisibility(View.GONE);   //领用数量
        llReceiveXq.setVisibility(View.GONE);  //领用详情
        llStartPlace.setVisibility(View.GONE);             //出发地点
        llUseCar.setVisibility(View.GONE);        //使用车辆
        llOtherRequest.setVisibility(View.GONE);        //其他要求
        llPublicCar.setVisibility(View.GONE);        //公务用车
        llBusinessMoney.setVisibility(View.GONE);        //差旅费用
        llBusinessTime.setVisibility(View.GONE);        //出差日期
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

        nameInstructsp.setText("申请事由");

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
        }
    }
}
