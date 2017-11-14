package com.lanwei.governmentstar.activity.spsq;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.gwnz.DocumentBaseActivity;
import com.lanwei.governmentstar.activity.gwnz.DocumentSHActivity;
import com.lanwei.governmentstar.activity.spsq.adapter.FilesQjAdapter;
import com.lanwei.governmentstar.activity.spsq.adapter.PhotoAdapter;
import com.lanwei.governmentstar.activity.spsq.adapter.PicAdapter;
import com.lanwei.governmentstar.activity.spsq.adapter.SpsqDetailsFlowAdapter;
import com.lanwei.governmentstar.bean.SpsqQjApplyDetails;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.LogUtils;
import com.lanwei.governmentstar.utils.ManagerUtils;
import com.lanwei.governmentstar.utils.PictureUtil;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.view.Dialog01;
import com.lanwei.governmentstar.view.Dialog02;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/8/16/016.
 */

public class QjApplySpActivity extends ApplyBaseActivity implements View.OnClickListener, DialogUtil.OnClickListenner {
    private static final String TAG = SealUsesealSpActivity.class.getSimpleName();
    private PopupWindowUtil popupWindowUtil;
    private FilesQjAdapter adapter;
    private DownloadManager dm;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private PicAdapter picAdapter;
    private File cover;
    private SpsqDetailsFlowAdapter flowAdapter;
    private JSONObject jsonObject;


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
        // TODO Auto-generated method stub
        initView();
        getData();

    }

    private void initView() {
        more.setVisibility(View.VISIBLE);
        more.setText("转交审批");
        title.setText("请假申请审批单");
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
        llInvokesp.setVisibility(View.GONE);        //调用类型
        llzzName.setVisibility(View.GONE);        //资质名称
        llzzType.setVisibility(View.GONE);        //资质类别
        llUsesp.setVisibility(View.GONE);        //用印文件
        llDocumentsp.setVisibility(View.GONE);        //文件类型
        llSealsp.setVisibility(View.GONE);        //印章类型
        llWjnumsp.setVisibility(View.GONE);        //文件份数
        llBorrow.setVisibility(View.GONE);        //借用日期
        llReturn.setVisibility(View.GONE);        //归还日期
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

        nameInstructsp.setText("请假事由");
        NameStartTime.setText("开始日期");
        NameReturnTime.setText("结束日期");

        look.setOnClickListener(this);
        inlook.setOnClickListener(this);

        applyYes.setOnClickListener(this);
        applyNo.setOnClickListener(this);

    }

    private void getData() {
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
                    SpsqQjApplyDetails spsqQjApplyDetails = gson.fromJson(data, SpsqQjApplyDetails.class);
                    String opApplicationNo = spsqQjApplyDetails.getApplicationNo();
                    String opTime = spsqQjApplyDetails.getOpTime();
                    String deptName = spsqQjApplyDetails.getDeptName();
                    String opTypeName = spsqQjApplyDetails.getType();
                    String opChildTypeName = spsqQjApplyDetails.getChildType();
                    String opMobileStartTime = spsqQjApplyDetails.getAskStartTime();
                    String opMobileEndTime = spsqQjApplyDetails.getAskEndTime();
                    String opReason = spsqQjApplyDetails.getAskReason();
                    String csRlist = spsqQjApplyDetails.getCSRlist();
                    String person = spsqQjApplyDetails.getPerson();
                    String personImage = spsqQjApplyDetails.getPersonImage();

                    List<SpsqQjApplyDetails.ImgfilesBean> imgfiles = spsqQjApplyDetails.getImgfiles();

                    applyNumsp.setText(opApplicationNo);
                    deptname.setText(deptName);
                    applyDatesp.setText(opTime);
                    applyQjType.setText(opTypeName + "-" + opChildTypeName);
                    applyStartTime.setText(opMobileStartTime);
                    applyReturnTime.setText(opMobileEndTime);
                    applyInstructsp.setText(opReason);
                    apply_theme.setText(spsqQjApplyDetails.getBT());
                    if (csRlist.equals("")) {
                        rlPersonCs.setVisibility(View.GONE);
                    } else {
                        applyPersonCs.setText(csRlist);
                    }
                    Glide.with(QjApplySpActivity.this)
                            .load(personImage)
                            .into(pic_head);
                    sqname.setText(person);

                    //上传图像
                    if (imgfiles.size() <= 0) {
                        llPictures.setVisibility(View.GONE);
                    }
                    picAdapter = new PicAdapter(QjApplySpActivity.this, spsqQjApplyDetails.getImgfiles());
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL){
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    });
                    recyclerView.setAdapter(picAdapter);

                    //附件
                    if (spsqQjApplyDetails.getFiles() == null | spsqQjApplyDetails.getFiles().size() <= 0) {
                        sq_fj.setVisibility(View.GONE);
                    } else {
                        sq_fj.setVisibility(View.VISIBLE);
                        rv_fjname.setVisibility(View.VISIBLE);
                        if (spsqQjApplyDetails.getFiles().size() > 3) {
                            look.setVisibility(View.VISIBLE);
                            inlook.setVisibility(View.GONE);
                            inlookLine.setVisibility(View.GONE);
                            rv_fjname.setVisibility(View.GONE);
                        }

                        adapter = new FilesQjAdapter(QjApplySpActivity.this, spsqQjApplyDetails.getFiles());
                        rv_fjname.setLayoutManager(new LinearLayoutManager(QjApplySpActivity.this, LinearLayoutManager.VERTICAL, false){
                            @Override
                            public boolean canScrollVertically() {
                                return false;
                            }
                        });   //为recyclerView指定现行垂直布局   //为recyclerView指定现行垂直布局
                        rv_fjname.setAdapter(adapter);
                    }

                    //流程
                    if (spsqQjApplyDetails.getData() == null | spsqQjApplyDetails.getData().size() <= 0) {
                        apply_listview.setVisibility(View.GONE);
                        flowLine.setVisibility(View.GONE);
                    } else {
                        apply_listview.setVisibility(View.VISIBLE);
                        flowLine.setVisibility(View.VISIBLE);
                        flowAdapter = new SpsqDetailsFlowAdapter(spsqQjApplyDetails.getData(), QjApplySpActivity.this);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 30: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意，可以做你要做的事情了。
                } else {
                    // 权限被用户拒绝了，可以提示用户,关闭界面等等。
                    Toast.makeText(this, "如果您拒绝使用读写权限，您将无法进行下载更新。", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            }
        }
    }


}
