package com.lanwei.governmentstar.activity.spsq;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.ChooseReceivers2_Activity;
import com.lanwei.governmentstar.activity.Convey_Files2_Activity;
import com.lanwei.governmentstar.activity.Convey_Files_Shenpi_Activity;
import com.lanwei.governmentstar.bean.SpsqOutApplyDetails;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.LogUtils;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.view.Dialog01;
import com.lanwei.governmentstar.view.Dialog02;
import com.lanwei.governmentstar.view.MyListView;
import com.lanwei.governmentstar.view.MyScrollView_Focus;
import com.lanwei.governmentstar.view.StatusBarUtils;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/8/10/010.
 */

public abstract class ApplyBaseActivity extends BaseActivity implements View.OnClickListener, MyScrollView_Focus.MyScrollListener, DialogUtil.OnClickListenner {
    private static final String TAG = ApplyBaseActivity.class.getSimpleName();

    protected TextView more, title,  applyNumsp, applyDatesp, applyInvokesp, applyUsesp, applDocumentsp,
            applySealsp, applyWjnumsp, applyInstructsp, applyPersonCs, applyBorrow, applyReturn, applyzzName, applyzzType,
            applyBorrowType, nameInstructsp, applyStartTime, applyReturnTime, applyOutType, applyStartPlace, applyUseCar,
            applyOtherRequest, namePlace, applyPublicCar, applyBusinessMoney, applyBusinessTime, applyAboutDocument, applyProof,
            applyQjType, applyThingsType, applyThingsNum, applyThingsDw, applyThingsPrice, applyJfTime,
            applyCustomType, applyPurchaseType, nameJf, applyThingsName, applyGg, applyRentStart, applyRentOver, applyRentType, applyGzf,
            applyLink, applyThingsReceive, applyKcNum, applyLyNum, applyReceiveXq, NameStartTime, NameReturnTime, NameReceive, applyWorkType,
            applyProjectName, applyNeedMoney, applyPersonMoney, applyProjectAbout, applyXzReason, applyPersonFq, applyPersonXz, applyContractNum,
            applyMyFz, applyContractType, applyWorkOther, applyOtherFz, applyQsReason, applyJjd, applyUseType, applyMoneyApply, applyMoneytype,
            nameBtime, applyMoneyBx, applyBxtype, applyPersonsType, applySsgw, applyXqGw, applyXqPerson, applyZpReason, applyDgTime, applyGwPerson,
            applyDpType, applyDqGw, applyDpGw, applyApplyType, applyGwZj, applyZgTime, applyHbZj, applySqSy, applyJdTheme, applyJdType, applyCjRy,
            applyApplyContent, applySpSq, applyHyName, applyHyType, applyZbf, applyHys, applyActivityTheme, applyActivityType, applyPayAll, applyPayType,
            applyZfPerson, applyKhh, applyYhzh, deptname, sqname,flow2 ,apply_theme;
    protected ImageView back, iv_contacts, pic_head, look, inlook;
    protected MyScrollView_Focus apply_scrollView;
    protected MyListView apply_listview;
    protected RelativeLayout sq_fj;
    protected LinearLayout llNumsp, llDatesp, llInvokesp, llUsesp, llDocumentsp, llSealsp, llWjnumsp, llInstructsp, llBorrow, llReturn,
            llzzName, llzzType, llBorrowType, llStartTime, llReturnTime, llOutType, llStartPlace, llUseCar, llOtherRequest, llPublicCar,
            llBusinessMoney, llBusinessTime, llAboutDocument, llProof, llQjType, llPictures, llThingsType, llThingsNum, llThingsDw,
            llThingsPrice, llJfTime, llCustomType, llPurchaseType, llThingsName, llGg, llRentStart, llRentOver, llRentType, llGzf, llLink,
            llThingsReceive, llKcNum, llLyNum, llReceiveXq, llWorkType, llProjectName, llNeedMoney, llPersonMoney, llProjectAbout, llXzReason,
            llPersonFq, llPersonXz, llContractNum, llMyFz, llContractType, llWorkOther, llOtherFz, llQsReason, llJjd, llUseType, llMoneyApply,
            llMoneytype, llMoneyBx, llBxtype, llPersonsType, llSsgw, llXqGw, llXqPerson, llZpReason, llDgTime, llGwPerson, llDpType, llDqGw, llDpGw,
            llApplyType, llGwZj, llZgTime, llHbZj, llSqSy, llJdTheme, llJdType, llCjRy, llApplyContent, llSpSq, llHyName, llHyType, llZbf, llHys,
            llActivityTheme, llActivityType, llPayAll, llPayType, llZfPerson, llKhh, llYhzh, rlPersonCs ,applyNo, applyYes ,flow;
    protected RecyclerView rv_fjname, recyclerView ;
    protected View inlookLine, flowLine;
    protected String opId;
    protected String userId;
    private PopupWindowUtil popupWindowUtil;
    private JSONObject jsonObject;
    private RelativeLayout document_guild_layout;

    abstract protected int getLayoutResId();

    abstract protected String getAction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate()");

        StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
        setContentView(getLayoutResId());
        init();

//        getData();

    }

    private void init() {
        // TODO Auto-generated method stub
        more = (TextView) findViewById(R.id.email_finish);
        flow = (LinearLayout) findViewById(R.id.flow);
        flow2 = (TextView) findViewById(R.id.flow2);
        title = (TextView) findViewById(R.id.tv_address);
        apply_theme = (TextView) findViewById(R.id.apply_theme);
        back = (ImageView) findViewById(R.id.back);
        iv_contacts = (ImageView) findViewById(R.id.iv_contacts);
        applyNo = (LinearLayout) findViewById(R.id.apply_no);
        applyYes = (LinearLayout) findViewById(R.id.apply_yes);
        apply_scrollView = (MyScrollView_Focus) findViewById(R.id.apply_scrollView);
        apply_scrollView.setMyScrollListener(this);
        apply_scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                return false;
            }
        });
        apply_listview = (MyListView) findViewById(R.id.apply_listview);
        apply_listview.setFocusable(false);

        pic_head = (CircleImageView) findViewById(R.id.pic_head);
        deptname = (TextView) findViewById(R.id.deptname);
        sqname = (TextView) findViewById(R.id.name);
        rv_fjname = (RecyclerView) findViewById(R.id.rv_fjname);
        look = (ImageView) findViewById(R.id.look);
        inlook = (ImageView) findViewById(R.id.inlook);
        inlookLine = findViewById(R.id.inlook_line);
        sq_fj = (RelativeLayout) findViewById(R.id.sq_fj);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        flowLine = findViewById(R.id.flowline);


        llNumsp = (LinearLayout) findViewById(R.id.ll_numsp);               //申请编号
        llDatesp = (LinearLayout) findViewById(R.id.ll_datesp);            //申请日期
        llOutType = (LinearLayout) findViewById(R.id.ll_outtype);          //外出类型
        llThingsType = (LinearLayout) findViewById(R.id.ll_thingstype);          //物品类型
        llThingsReceive = (LinearLayout) findViewById(R.id.ll_thingsreceive);          //领用物品
        llJfTime = (LinearLayout) findViewById(R.id.ll_jftime);          //交付日期
        llRentStart = (LinearLayout) findViewById(R.id.ll_rentstart);          //租用开始
        llRentOver = (LinearLayout) findViewById(R.id.ll_rentover);          //租用结束
        llRentType = (LinearLayout) findViewById(R.id.ll_renttype);          //租用类型
        llCustomType = (LinearLayout) findViewById(R.id.ll_customtype);          //定制类型
        llPurchaseType = (LinearLayout) findViewById(R.id.ll_purchasetype);          //采购类型
        llInvokesp = (LinearLayout) findViewById(R.id.ll_invokesp);       //调用类型
        llzzName = (LinearLayout) findViewById(R.id.ll_zzname);            //资质名称
        llzzType = (LinearLayout) findViewById(R.id.ll_zztype);            //资质类别
        llUsesp = (LinearLayout) findViewById(R.id.ll_usesp);              //用印文件
        llDocumentsp = (LinearLayout) findViewById(R.id.ll_documentsp);  //文件类型
        llSealsp = (LinearLayout) findViewById(R.id.ll_sealsp);           //印章类型
        llWjnumsp = (LinearLayout) findViewById(R.id.ll_wjnumsp);         //文件份数
        llBorrow = (LinearLayout) findViewById(R.id.ll_borrow);           //借用日期
        llReturn = (LinearLayout) findViewById(R.id.ll_return);           //归还日期
        llStartTime = (LinearLayout) findViewById(R.id.ll_starttime);    //开始时间
        llReturnTime = (LinearLayout) findViewById(R.id.ll_returntime);  //返回时间
        llBorrowType = (LinearLayout) findViewById(R.id.ll_borrowtype);   //借用类型
        llInstructsp = (LinearLayout) findViewById(R.id.ll_instructsp);  //备注说明
        llThingsName = (LinearLayout) findViewById(R.id.ll_thingsname);  //物品名称
        llGg = (LinearLayout) findViewById(R.id.ll_gg);  //规格
        llGzf = (LinearLayout) findViewById(R.id.ll_gzf);  //供租方
        llLink = (LinearLayout) findViewById(R.id.ll_link);  //联系方式
        llKcNum = (LinearLayout) findViewById(R.id.ll_kcnum);  //库存数量
        llLyNum = (LinearLayout) findViewById(R.id.ll_lynum);  //领用数量
        llReceiveXq = (LinearLayout) findViewById(R.id.ll_receivexq);  //领用详情
        llThingsNum = (LinearLayout) findViewById(R.id.ll_thingsnum);  //数量
        llThingsDw = (LinearLayout) findViewById(R.id.ll_thingsdw);  //单位
        llThingsPrice = (LinearLayout) findViewById(R.id.ll_thingsprice);  //价格
        rlPersonCs = (LinearLayout) findViewById(R.id.rl_personCs);     //抄送人
        llStartPlace = (LinearLayout) findViewById(R.id.ll_startplace);    //出发地点
        llUseCar = (LinearLayout) findViewById(R.id.ll_usecar);  //使用车辆
        llOtherRequest = (LinearLayout) findViewById(R.id.ll_otherrequest);   //其他要求
        llPublicCar = (LinearLayout) findViewById(R.id.ll_publiccar);   //公务用车
        llBusinessMoney = (LinearLayout) findViewById(R.id.ll_businessmoney);   //差旅费用
        llBusinessTime = (LinearLayout) findViewById(R.id.ll_businesstime);   //出差日期
        llAboutDocument = (LinearLayout) findViewById(R.id.ll_aboutdocument);   //相关文件
        llProof = (LinearLayout) findViewById(R.id.ll_proof);   //报销凭证
        llQjType = (LinearLayout) findViewById(R.id.ll_qjtype);   //请假类型
        llPictures = (LinearLayout) findViewById(R.id.ll_pictures);   //上传图像

        llWorkType = (LinearLayout) findViewById(R.id.ll_worktype);          //工作类型
        llProjectName = (LinearLayout) findViewById(R.id.ll_projectname);          //项目名称
        llNeedMoney = (LinearLayout) findViewById(R.id.ll_needmoney);          //资金需求
        llPersonMoney = (LinearLayout) findViewById(R.id.ll_needperson);          //人员需求
        llProjectAbout = (LinearLayout) findViewById(R.id.ll_projectabout);          //项目概述

        llXzReason = (LinearLayout) findViewById(R.id.ll_xzreason);          //协作事由
        llPersonFq = (LinearLayout) findViewById(R.id.ll_personfq);          //发起方
        llPersonXz = (LinearLayout) findViewById(R.id.ll_personxz);          //协作方

        llContractNum = (LinearLayout) findViewById(R.id.ll_contractnum);          //合同编号
        llMyFz = (LinearLayout) findViewById(R.id.ll_myfz);          //我方负责人
        llContractType = (LinearLayout) findViewById(R.id.ll_contracttype);          //合同类型
        llWorkOther = (LinearLayout) findViewById(R.id.ll_workother);          //对方单位
        llOtherFz = (LinearLayout) findViewById(R.id.ll_otherfz);          //对方负责人

        llQsReason = (LinearLayout) findViewById(R.id.ll_qsreason);          //请示事由
        llJjd = (LinearLayout) findViewById(R.id.ll_jjd);          //紧急度

        llUseType = (LinearLayout) findViewById(R.id.ll_usetype);          //用途类型
        llMoneyApply = (LinearLayout) findViewById(R.id.ll_moneyapply);          //申请金额
        llMoneytype = (LinearLayout) findViewById(R.id.ll_moneytype);          //资金类型

        llMoneyBx = (LinearLayout) findViewById(R.id.ll_moneybx);          //报销金额
        llBxtype = (LinearLayout) findViewById(R.id.ll_bxtype);          //报销类别

        llPersonsType = (LinearLayout) findViewById(R.id.ll_personstype);          //人事类型
        llSsgw = (LinearLayout) findViewById(R.id.ll_ssgw);          //所属岗位

        llXqGw = (LinearLayout) findViewById(R.id.ll_xqgw);          //需求岗位
        llXqPerson = (LinearLayout) findViewById(R.id.ll_xqperson);          //需求人数
        llZpReason = (LinearLayout) findViewById(R.id.ll_zpreason);          //招聘原因
        llDgTime = (LinearLayout) findViewById(R.id.ll_dgtime);          //到岗日期
        llGwPerson = (LinearLayout) findViewById(R.id.ll_gwperson);          //当前岗位人数

        llDpType = (LinearLayout) findViewById(R.id.ll_dptype);          //调派类型
        llDqGw = (LinearLayout) findViewById(R.id.ll_dqgw);          //当前岗位
        llDpGw = (LinearLayout) findViewById(R.id.ll_dpgq);          //调派岗位

        llApplyType = (LinearLayout) findViewById(R.id.ll_applytype);          //申请类型
        llGwZj = (LinearLayout) findViewById(R.id.ll_gwzj);          //岗位职级
        llZgTime = (LinearLayout) findViewById(R.id.ll_zgtime);          //在岗年限
        llHbZj = (LinearLayout) findViewById(R.id.ll_hbzj);          //后备职级

        llSqSy = (LinearLayout) findViewById(R.id.ll_sqsy);          //申请事由

        llJdTheme = (LinearLayout) findViewById(R.id.ll_jdtheme);          //接待主体
        llJdType = (LinearLayout) findViewById(R.id.ll_jdtype);          //接待类型
        llCjRy = (LinearLayout) findViewById(R.id.ll_cjry);          //参加人员

        llApplyContent = (LinearLayout) findViewById(R.id.ll_applycontent);          //申请内容
        llSpSq = (LinearLayout) findViewById(R.id.ll_spsq);          //审批申请

        llHyName = (LinearLayout) findViewById(R.id.ll_hyname);          //会议名称
        llHyType = (LinearLayout) findViewById(R.id.ll_hytype);          //会议类型
        llZbf = (LinearLayout) findViewById(R.id.ll_zbf);          //主办方
        llHys = (LinearLayout) findViewById(R.id.ll_hys);          //会议室

        llActivityTheme = (LinearLayout) findViewById(R.id.ll_activitytheme);          //活动主题
        llActivityType = (LinearLayout) findViewById(R.id.ll_activitytype);          //活动类型

        llPayAll = (LinearLayout) findViewById(R.id.ll_payall);          //付款总额
        llPayType = (LinearLayout) findViewById(R.id.ll_paytype);          //付款方式
        llZfPerson = (LinearLayout) findViewById(R.id.ll_zfperson);          //支付对象
        llKhh = (LinearLayout) findViewById(R.id.ll_khh);          //开户行
        llYhzh = (LinearLayout) findViewById(R.id.ll_yhzh);          //银行账户

        applyNumsp = (TextView) findViewById(R.id.apply_numsp);                 //申请编号
        applyDatesp = (TextView) findViewById(R.id.apply_datesp);              //申请日期
        applyThingsType = (TextView) findViewById(R.id.apply_thingstype);         //物品类型
        applyThingsReceive = (TextView) findViewById(R.id.apply_thingsreceive);          //领用物品
        applyCustomType = (TextView) findViewById(R.id.apply_customtype);          //定制类型
        applyPurchaseType = (TextView) findViewById(R.id.apply_purchasetype);          //采购类型
        applyOutType = (TextView) findViewById(R.id.apply_outtype);         //外出类型
        applyInvokesp = (TextView) findViewById(R.id.apply_invokesp);         //调用类型
        applyJfTime = (TextView) findViewById(R.id.apply_jftime);          //交付日期
        nameJf = (TextView) findViewById(R.id.name_jf);          //交付日期名
        applyRentStart = (TextView) findViewById(R.id.apply_rentstart);          //租用开始
        applyRentOver = (TextView) findViewById(R.id.apply_rentover);          //租用结束
        applyRentType = (TextView) findViewById(R.id.apply_renttype);          //租用类型
        applyzzName = (TextView) findViewById(R.id.apply_zzname);              //资质名称
        applyzzType = (TextView) findViewById(R.id.apply_zztype);              //资质类别
        applyUsesp = (TextView) findViewById(R.id.apply_usesp);                //用印文件
        applDocumentsp = (TextView) findViewById(R.id.apply_documentsp);     //文件类型
        applySealsp = (TextView) findViewById(R.id.apply_sealsp);             //印章类型
        applyWjnumsp = (TextView) findViewById(R.id.apply_wjnumsp);          //文件份数
        applyBorrow = (TextView) findViewById(R.id.apply_borrow);             //借用日期
        nameBtime = (TextView) findViewById(R.id.name_btime);             //借用日期
        applyReturn = (TextView) findViewById(R.id.apply_return);            //归还日期
        applyStartTime = (TextView) findViewById(R.id.apply_starttime);             //开始时间
        NameStartTime = (TextView) findViewById(R.id.name_starttime);    //开始时间名
        applyReturnTime = (TextView) findViewById(R.id.apply_returntime);            //返回时间
        NameReturnTime = (TextView) findViewById(R.id.name_returntime);    //开始时间名
        applyBorrowType = (TextView) findViewById(R.id.apply_borrowtype);   //借用类型
        applyInstructsp = (TextView) findViewById(R.id.apply_instructsp);   //备注说明
        nameInstructsp = (TextView) findViewById(R.id.name_instructsp);   //备注说明名
        applyThingsName = (TextView) findViewById(R.id.apply_thingsname);  //物品名称
        applyGg = (TextView) findViewById(R.id.apply_gg);  //规格
        applyGzf = (TextView) findViewById(R.id.apply_gzf);  //供租方
        applyLink = (TextView) findViewById(R.id.apply_link);  //联系方式
        applyKcNum = (TextView) findViewById(R.id.apply_kcnum);  //库存数量
        applyLyNum = (TextView) findViewById(R.id.apply_lynum);  //领用数量
        applyReceiveXq = (TextView) findViewById(R.id.apply_receivexq);  //领用详情
        NameReceive = (TextView) findViewById(R.id.name_receive);  //领用名称
        applyThingsNum = (TextView) findViewById(R.id.apply_thingsnum);  //数量
        applyThingsDw = (TextView) findViewById(R.id.apply_thingsdw);  //单位
        applyThingsPrice = (TextView) findViewById(R.id.apply_thingsprice);  //价格
        applyPersonCs = (TextView) findViewById(R.id.apply_personCs);       //抄送人
        applyStartPlace = (TextView) findViewById(R.id.apply_startplace);             //出发地点
        applyUseCar = (TextView) findViewById(R.id.apply_usecar);                     //使用车辆
        applyOtherRequest = (TextView) findViewById(R.id.apply_otherrequest);      //其他要求
        namePlace = (TextView) findViewById(R.id.nameplace);   //地方名
        applyPublicCar = (TextView) findViewById(R.id.apply_publiccar);   //公务用车
        applyBusinessMoney = (TextView) findViewById(R.id.apply_businessmoney);   //差旅费用
        applyBusinessTime = (TextView) findViewById(R.id.apply_businesstime);   //出差日期
        applyAboutDocument = (TextView) findViewById(R.id.apply_aboutdocument);   //相关文件
        applyProof = (TextView) findViewById(R.id.apply_proof);   //报销凭证
        applyQjType = (TextView) findViewById(R.id.apply_qjtype);   //请假类型
        applyWorkType = (TextView) findViewById(R.id.apply_worktype);          //工作类型
        applyProjectName = (TextView) findViewById(R.id.apply_projectname);          //项目名称
        applyNeedMoney = (TextView) findViewById(R.id.apply_needmoney);          //资金需求\
        applyPersonMoney = (TextView) findViewById(R.id.apply_needperson);          //人员需求
        applyProjectAbout = (TextView) findViewById(R.id.apply_projectabout);          //项目概述
        applyXzReason = (TextView) findViewById(R.id.apply_xzreason);          //协作事由
        applyPersonFq = (TextView) findViewById(R.id.apply_personfq);          //发起方
        applyPersonXz = (TextView) findViewById(R.id.apply_personxz);          //协作方
        applyContractNum = (TextView) findViewById(R.id.apply_contractnum);          //合同编号
        applyMyFz = (TextView) findViewById(R.id.apply_myfz);          //我方负责人
        applyContractType = (TextView) findViewById(R.id.apply_contracttype);          //合同类型
        applyWorkOther = (TextView) findViewById(R.id.apply_workother);          //对方单位
        applyOtherFz = (TextView) findViewById(R.id.apply_otherfz);          //对方负责人
        applyQsReason = (TextView) findViewById(R.id.apply_qsreason);          //请示事由
        applyJjd = (TextView) findViewById(R.id.apply_jjd);          //紧急度
        applyUseType = (TextView) findViewById(R.id.apply_usetype);          //用途类型
        applyMoneyApply = (TextView) findViewById(R.id.apply_moneyapply);          //申请金额
        applyMoneytype = (TextView) findViewById(R.id.apply_moneytype);          //资金类型
        applyMoneyBx = (TextView) findViewById(R.id.apply_moneybx);          //报销金额
        applyBxtype = (TextView) findViewById(R.id.apply_bxtype);          //报销类别
        applyPersonsType = (TextView) findViewById(R.id.apply_personstype);          //人事类型
        applySsgw = (TextView) findViewById(R.id.apply_ssgw);          //所属岗位
        applyXqGw = (TextView) findViewById(R.id.apply_xqgw);          //需求岗位
        applyXqPerson = (TextView) findViewById(R.id.apply_xqperson);          //需求人数
        applyZpReason = (TextView) findViewById(R.id.apply_zpreason);          //招聘原因
        applyDgTime = (TextView) findViewById(R.id.apply_dgtime);          //到岗日期
        applyGwPerson = (TextView) findViewById(R.id.apply_gwperson);          //当前岗位人数
        applyDpType = (TextView) findViewById(R.id.apply_dptype);          //调派类型
        applyDqGw = (TextView) findViewById(R.id.apply_dqgw);          //当前岗位
        applyDpGw = (TextView) findViewById(R.id.apply_dpgw);          //调派岗位
        applyApplyType = (TextView) findViewById(R.id.apply_applytype);          //申请类型
        applyGwZj = (TextView) findViewById(R.id.apply_gwzj);          //岗位职级
        applyZgTime = (TextView) findViewById(R.id.apply_zgtime);          //在岗年限
        applyHbZj = (TextView) findViewById(R.id.apply_hbzj);          //后备职级
        applySqSy = (TextView) findViewById(R.id.apply_sqsy);          //申请事由
        applyJdTheme = (TextView) findViewById(R.id.apply_jdtheme);          //接待主体
        applyJdType = (TextView) findViewById(R.id.apply_jdtype);          //接待类型
        applyCjRy = (TextView) findViewById(R.id.apply_cjry);          //参加人员
        applyApplyContent = (TextView) findViewById(R.id.apply_applycontent);          //申请内容
        applySpSq = (TextView) findViewById(R.id.apply_spsq);          //审批申请
        applyHyName = (TextView) findViewById(R.id.apply_hyname);          //会议名称
        applyHyType = (TextView) findViewById(R.id.apply_hytype);          //会议类型
        applyZbf = (TextView) findViewById(R.id.apply_zbf);          //主办方
        applyHys = (TextView) findViewById(R.id.apply_hys);          //会议室
        applyActivityTheme = (TextView) findViewById(R.id.apply_activitytheme);          //活动主题
        applyActivityType = (TextView) findViewById(R.id.apply_activitytype);          //活动类型
        applyPayAll = (TextView) findViewById(R.id.apply_payall);          //付款总额
        applyPayType = (TextView) findViewById(R.id.apply_paytype);          //付款方式
        applyZfPerson = (TextView) findViewById(R.id.apply_zfperson);          //支付对象
        applyKhh = (TextView) findViewById(R.id.apply_khh);          //开户行
        applyYhzh = (TextView) findViewById(R.id.apply_yhzh);          //银行账户
        document_guild_layout = (RelativeLayout) findViewById(R.id.document_guild_layout);

        Intent intent = getIntent();
        opId = intent.getStringExtra("opId");
        Log.d("sb", opId);
        userId = intent.getStringExtra("userId");

        if(getIntent().getStringExtra("type").equals("0")){
            document_guild_layout.setVisibility(View.GONE);
            flow2.setVisibility(View.VISIBLE);
        }else{
            document_guild_layout.setVisibility(View.VISIBLE);
            flow2.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        more.setVisibility(View.GONE);
    }

    protected void getRejectData() {
        final Dialog02 dialog02 = new Dialog02(this);
        dialog02.setTitle("拒绝申请", Color.parseColor("#00a7e4"));
        dialog02.setContent("您确定要拒绝该申请？", Color.parseColor("#4b4b4b"));
        dialog02.setLeftBtn(R.drawable.select_button_left, Color.WHITE);
        dialog02.setRightBtn(R.drawable.select_button_right, Color.WHITE);
        dialog02.setYesOnclickListener("确定", new Dialog02.onYesOnclickListener() {
            @Override
            public void onYesClick() {

                popupWindowUtil = new PopupWindowUtil(ApplyBaseActivity.this, "提交中...");
                popupWindowUtil.show();
                RejectData();
                dialog02.dismiss();
            }
        });
        dialog02.setNoOnclickListener("取消", new Dialog02.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                dialog02.dismiss();
            }
        });
        Window window = dialog02.getWindow();
        //设置显示动画
        window.setWindowAnimations(R.style.dialog_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;


        wl.y = -this.getWindowManager().getDefaultDisplay().getHeight() / 50;
        //设置显示位置
        dialog02.onWindowAttributesChanged(wl);//设置点击外围解散
        dialog02.setCanceledOnTouchOutside(true);


        dialog02.show();
    }


    private void RejectData() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        Call<JsonObject> call = api.spsqShjj(opId, userId, "spsqShjj");
        call.enqueue(new Callback<JsonObject>() {
            /**
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                popupWindowUtil.dismiss();
                try {
                    jsonObject = new JSONObject(response.body().toString());
                    boolean result = jsonObject.getBoolean("result");
                    Log.d("66", String.valueOf(jsonObject));
                    if (result) {
                        rejectDialog();
                        return;
                    }
                    Toast.makeText(ApplyBaseActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                popupWindowUtil.dismiss();
            }
        });
    }

    private void rejectDialog() {
        final Dialog01 dialog01 = new Dialog01(this);
        dialog01.setTitle("文件驳回", Color.parseColor("#00a7e4"));
        dialog01.setContent("您已驳回当前文件", Color.parseColor("#4b4b4b"));
        dialog01.setLineGone(true);
        dialog01.setBtnImage(R.drawable.select_button_blue);
        dialog01.setSingleOnclickListener("知道了", new Dialog01.onsingleOnclickListener() {
            @Override
            public void onSingleClick() {
                dialog01.dismiss();
                Intent i = new Intent();
                i.putExtra("opId", opId);
                try {
                    i.putExtra("opState", jsonObject.getString("state"));
                    i.putExtra("opStatus", jsonObject.getString("status"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setResult(6, i);
                finish();
            }
        });

        Window window = dialog01.getWindow();
        //设置显示动画
        window.setWindowAnimations(R.style.dialog_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;

        wl.y = -this.getWindowManager().getDefaultDisplay().getHeight() / 50;
        //设置显示位置
        dialog01.onWindowAttributesChanged(wl);//设置点击外围解散
        dialog01.setCanceledOnTouchOutside(true);

        dialog01.show();
    }

    protected void  getAgreeData(){
        new DialogUtil(ApplyBaseActivity.this, this).showConfirm("同意申请", "您是否同意该申请？", "同意申请", "我再看看");
    }

    @Override
    public void yesClick() {
        popupWindowUtil = new PopupWindowUtil(ApplyBaseActivity.this, "提交中...");
        popupWindowUtil.show();
        getData1();
    }

    private void getData1() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        Call<JsonObject> call = api.spsqShtg(opId, userId, "spsqShtg");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                popupWindowUtil.dismiss();
                try {
                    jsonObject = new JSONObject(response.body().toString());
                    boolean result = jsonObject.getBoolean("result");
                    Log.d("66", String.valueOf(jsonObject));
                    if (result) {
                        new DialogUtil(ApplyBaseActivity.this, ApplyBaseActivity.this).showAlert("申请已同意", "您已同意该申请！", "知道了！");
                        return;
                    }
                    Toast.makeText(ApplyBaseActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(ApplyBaseActivity.this, "网络连接有误", Toast.LENGTH_SHORT).show();
                popupWindowUtil.dismiss();
            }
        });
    }

    @Override
    public void noClick() {

    }

    @Override
    public void onSingleClick() {
        Intent i = new Intent();
        i.putExtra("opId", opId);
        Log.d("911",opId);
        try {
            i.putExtra("opState", jsonObject.getString("state"));
            Log.d("911",jsonObject.getString("state"));
            i.putExtra("opStatus", jsonObject.getString("status"));
            Log.d("911",jsonObject.getString("status"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setResult(6, i);

        finish();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back: //返回
                finish();
                break;
            case R.id.flow: //流程
                Intent intent= new Intent(this,Convey_Files2_Activity.class);
                intent.putExtra("opId",opId);
                intent.putExtra("userId",userId);
                Log.e("opId",opId);
                Log.e("userId",userId);
                startActivityForResult(intent,001);
                break;
            case R.id.flow2: //流程
                Intent intent2= new Intent(this,Convey_Files2_Activity.class);
                intent2.putExtra("opId",opId);
                intent2.putExtra("userId",userId);
                Log.e("opId",opId);
                Log.e("userId",userId);
                startActivityForResult(intent2,002);
                break;
        }
    }

    @Override
    public void sendDistanceY(int distance) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 555){
            Log.e("Zgdssdfsfs福如东海炎热的gds","fgdf如东海炎gdsfg ");
            Intent intent=new Intent();
            setResult(444,intent);
            finish();
        }
    }


}
