package com.lanwei.governmentstar.activity.spsq;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.zwyx.ZwyxTreeActivity;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.Constant;
import com.lanwei.governmentstar.utils.CropUtils;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.ManagerUtils;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.lanwei.governmentstar.view.wheel.WheelDialog;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/8/2/002.
 * 物品申请
 */

public class EnterThingsApplyActivity extends AutoLayoutActivity implements View.OnClickListener {
    @InjectView(R.id.back)
    ImageView back;
    @InjectView(R.id.iv_contacts)
    CircleImageView ivContacts;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.tv_apl)
    TextView tvApl;
    @InjectView(R.id.tv_go1)
    TextView tvGo1;
    @InjectView(R.id.scrollview)
    ScrollView scrollview;
    @InjectView(R.id.ll_others)
    LinearLayout llOthers;
    @InjectView(R.id.ll_rent)
    LinearLayout llRent;
    @InjectView(R.id.ll_receive)
    LinearLayout llReceive;
    @InjectView(R.id.ll_purchase)
    LinearLayout llPurchase;
    @InjectView(R.id.ll_buy)
    LinearLayout llBuy;
    @InjectView(R.id.use_content1)
    EditText useContent1;
    @InjectView(R.id.use_content2)
    EditText useContent2;
    @InjectView(R.id.use_content3)
    EditText useContent3;
    @InjectView(R.id.use_content4)
    EditText useContent4;
    @InjectView(R.id.use_content5)
    EditText useContent5;
    @InjectView(R.id.rv_fjname1)
    RecyclerView rvFjname1;
    @InjectView(R.id.rv_fjname2)
    RecyclerView rvFjname2;
    @InjectView(R.id.rv_fjname3)
    RecyclerView rvFjname3;
    @InjectView(R.id.rv_fjname4)
    RecyclerView rvFjname4;
    @InjectView(R.id.rv_fjname5)
    RecyclerView rvFjname5;
    @InjectView(R.id.tv_fj1)
    TextView tvFj1;
    @InjectView(R.id.tv_fj2)
    TextView tvFj2;
    @InjectView(R.id.tv_fj3)
    TextView tvFj3;
    @InjectView(R.id.tv_fj4)
    TextView tvFj4;
    @InjectView(R.id.tv_fj5)
    TextView tvFj5;
    @InjectView(R.id.looking1)
    TextView looking1;
    @InjectView(R.id.tv_go2)
    TextView tv_go2;
    @InjectView(R.id.tv_go3)
    TextView tv_go3;
    @InjectView(R.id.inlook_line1)
    View inlookLine1;
    @InjectView(R.id.inlooking1)
    ImageView inlooking1;
    @InjectView(R.id.looking2)
    TextView looking2;
    @InjectView(R.id.inlook_line2)
    View inlookLine2;
    @InjectView(R.id.inlooking2)
    ImageView inlooking2;
    @InjectView(R.id.looking3)
    TextView looking3;
    @InjectView(R.id.inlook_line3)
    View inlookLine3;
    @InjectView(R.id.inlooking3)
    ImageView inlooking3;
    @InjectView(R.id.looking4)
    TextView looking4;
    @InjectView(R.id.inlook_line4)
    View inlookLine4;
    @InjectView(R.id.inlooking4)
    ImageView inlooking4;
    @InjectView(R.id.looking5)
    TextView looking5;
    @InjectView(R.id.inlook_line5)
    View inlookLine5;
    @InjectView(R.id.inlooking5)
    ImageView inlooking5;
    @InjectView(R.id.things_content1)
    EditText thingsContent1;
    @InjectView(R.id.things_content2)
    EditText thingsContent2;
    @InjectView(R.id.things_content3)
    EditText thingsContent3;
    @InjectView(R.id.things_content4)
    EditText thingsContent4;
    @InjectView(R.id.things_content5)
    EditText thingsContent5;
    @InjectView(R.id.things_content6)
    EditText thingsContent6;
    @InjectView(R.id.things_content7)
    EditText thingsContent7;
    @InjectView(R.id.things_content8)
    EditText thingsContent8;
    @InjectView(R.id.things_content9)
    EditText thingsContent9;
    @InjectView(R.id.things_content10)
    EditText thingsContent10;
    @InjectView(R.id.things_content11)
    EditText thingsContent11;
    @InjectView(R.id.things_content12)
    EditText thingsContent12;
    @InjectView(R.id.things_content13)
    EditText thingsContent13;
    @InjectView(R.id.things_content14)
    EditText thingsContent14;
    @InjectView(R.id.things_content15)
    EditText thingsContent15;
    @InjectView(R.id.things_content16)
    EditText thingsContent16;
    @InjectView(R.id.things_content17)
    EditText thingsContent17;
    @InjectView(R.id.things_content18)
    EditText thingsContent18;
    @InjectView(R.id.things_content19)
    EditText thingsContent19;
    @InjectView(R.id.things_content20)
    EditText thingsContent20;
    @InjectView(R.id.time1)
    TextView time1;
    @InjectView(R.id.time2)
    TextView time2;
    @InjectView(R.id.time3)
    TextView time3;
    @InjectView(R.id.time4)
    TextView time4;
    @InjectView(R.id.select_receive)
    TextView selectReceive;
    @InjectView(R.id.select_renttype)
    TextView selectRentType;
    @InjectView(R.id.select_buytype)
    TextView selectBuyType;
    @InjectView(R.id.select_maketype)
    TextView selectMakeType;
    @InjectView(R.id.tv_return3)
    TextView tv_return3;
    @InjectView(R.id.tv_return31)
    TextView tv_return31;
    @InjectView(R.id.nametype)
    TextView nameType;
    @InjectView(R.id.reason_apply)
    EditText reason_apply;
    @InjectView(R.id.inbox_layout_shenqi)
    RelativeLayout inbox_layout_shenqi;
    @InjectView(R.id.inbox_layout_caosong)
    RelativeLayout inbox_layout_caosong;
    @InjectView(R.id.reason_apply2)
    RelativeLayout reason_apply2;
    @InjectView(R.id.theme_content)
    EditText theme_content;

    private String[] typeThings;  //选择物品类型
    private String[] receiveThings;
    private String[] rentTypeThings;
    private WheelDialog typeDialog;
    private String typeName;
    private String type="物品申请";
    private String childType="申请采购";
    private Intent intent;

    private ArrayList<String> opIdlist_shenqi;
    private ArrayList<String> opIdlist_chaosong;
    private ArrayList<String> addresseNameList_shenqi;
    private ArrayList<String> addresseNameList_chaosong;
    private String opIds_shenqi="";
    private String opIds_chaosong="";

    private Logging_Success bean;
    private String zhusong = "";
    private String zhusong_temp = "";
    private String caosong = "";
    private String caosong_temp = "";
    private String tong = "";
    private String tong_temp = "";
    private PopupWindowUtil popupWindowUtil;

    /**
     * Dialog返回数据
     **/
    private Handler mTypeHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                typeName = typeDialog.getmCurrentName();
                allTypes(typeName);
                if (!TextUtils.isEmpty(typeName))
                            tvGo1.setText(typeName);
            }
            return false;
        }
    });
    private Handler mReceiveHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                String typeName = typeDialog.getmCurrentName();
                if (!TextUtils.isEmpty(typeName))
                    selectReceive.setText(typeName);
            }
            return false;
        }
    });
    private Handler mRentHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                String typeName = typeDialog.getmCurrentName();
                if (!TextUtils.isEmpty(typeName))
                    selectRentType.setText(typeName);
            }
            return false;
        }
    });
    private Handler mBuyHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                String typeName = typeDialog.getmCurrentName();
                if (!TextUtils.isEmpty(typeName))
                    selectBuyType.setText(typeName);
            }
            return false;
        }
    });
    private Handler mMakeHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                String typeName = typeDialog.getmCurrentName();
                if (!TextUtils.isEmpty(typeName))
                    selectMakeType.setText(typeName);
            }
            return false;
        }
    });
    private AddfjAdapter fjadapter;
    private String name;
    private String[] buyTypeThings;
    private String[] makeTypeThings;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.activity_enterthingsapply);
        ButterKnife.inject(this);

        initview();
        // 获取bean;
        String defString = PreferencesManager.getInstance(EnterThingsApplyActivity.this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);
    }

    private void initview() {
        nameType.setText("物品类型");
        tvAddress.setVisibility(View.VISIBLE);
        tvAddress.setText("物品申购");
        back.setVisibility(View.VISIBLE);
        ivContacts.setVisibility(View.GONE);
        tvApl.setVisibility(View.VISIBLE);
        tvApl.setText("提交审批");
        tvApl.setOnClickListener(this);
        back.setOnClickListener(this);
        tvGo1.setOnClickListener(this);
        tvFj1.setOnClickListener(this);
        tvFj2.setOnClickListener(this);
        tvFj3.setOnClickListener(this);
        tvFj4.setOnClickListener(this);
        tvFj5.setOnClickListener(this);
        looking1.setOnClickListener(this);
        inlooking1.setOnClickListener(this);
        looking2.setOnClickListener(this);
        inlooking2.setOnClickListener(this);
        looking3.setOnClickListener(this);
        inlooking3.setOnClickListener(this);
        looking4.setOnClickListener(this);
        inlooking4.setOnClickListener(this);
        looking5.setOnClickListener(this);
        inlooking5.setOnClickListener(this);
        time1.setOnClickListener(this);
        time2.setOnClickListener(this);
        time3.setOnClickListener(this);
        time4.setOnClickListener(this);
        selectReceive.setOnClickListener(this);
        selectRentType.setOnClickListener(this);
        selectBuyType.setOnClickListener(this);
        selectMakeType.setOnClickListener(this);
        inbox_layout_shenqi.setOnClickListener(this);
        inbox_layout_caosong.setOnClickListener(this);

        thingsContent4.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        thingsContent12.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        thingsContent17.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        thingsContent20.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

    }

    private void allTypes(String typeName) {
        if (fjadapter == null)
            fjadapter = new AddfjAdapter(fileList, EnterThingsApplyActivity.this);
        fileList.clear();     //如果类型改变了就清空选中附件
        fjadapter.notifyDataSetChanged();

        if (typeName.equals("租用申请")) {  // TODO: 2017/4/13   这里如果类型只能为整形的话 可能会出错
            llRent.setVisibility(View.VISIBLE);
            llOthers.setVisibility(View.GONE);
            llReceive.setVisibility(View.GONE);
            llPurchase.setVisibility(View.GONE);
            llBuy.setVisibility(View.GONE);

            thingsContent5.setText("");
            thingsContent6.setText("");
            thingsContent7.setText("");
            thingsContent8.setText("");
            thingsContent9.setText("");
            useContent2.setText("");
            selectRentType.setText("请选择(选填)");
            tvFj2.setText("请选择(选填)");
            rvFjname2.setVisibility(View.GONE);
        } else if (typeName.equals("其他")) {
            llRent.setVisibility(View.GONE);
            llOthers.setVisibility(View.VISIBLE);
            llReceive.setVisibility(View.GONE);
            llPurchase.setVisibility(View.GONE);
            llBuy.setVisibility(View.GONE);

            thingsContent1.setText("");
            thingsContent2.setText("");
            thingsContent3.setText("");
            thingsContent4.setText("");
            useContent1.setText("");
            tvFj1.setText("请选择(选填)");
            rvFjname1.setVisibility(View.GONE);
        } else if (typeName.equals("物品领用")) {
            llRent.setVisibility(View.GONE);
            llOthers.setVisibility(View.GONE);
            llReceive.setVisibility(View.VISIBLE);
            llPurchase.setVisibility(View.GONE);
            llBuy.setVisibility(View.GONE);

            thingsContent10.setText("");
            thingsContent11.setText("");
            thingsContent12.setText("");
            useContent3.setText("");
            selectReceive.setText("请选择(选填)");
            tvFj3.setText("请选择(选填)");
            rvFjname3.setVisibility(View.GONE);
        } else if (typeName.equals("申请采购")) {
            llRent.setVisibility(View.GONE);
            llOthers.setVisibility(View.GONE);
            llReceive.setVisibility(View.GONE);
            llPurchase.setVisibility(View.VISIBLE);
            llBuy.setVisibility(View.GONE);
            childType ="申请采购";
            thingsContent13.setText("");
            thingsContent14.setText("");
            thingsContent15.setText("");
            thingsContent16.setText("");
            thingsContent17.setText("");
            useContent4.setText("");
            tvFj4.setText("请选择(选填)");
            rvFjname4.setVisibility(View.GONE);
            reason_apply2.setVisibility(View.GONE);
        } else if (typeName.equals("定制申请")) {
            llRent.setVisibility(View.GONE);
            llOthers.setVisibility(View.GONE);
            llReceive.setVisibility(View.GONE);
            llPurchase.setVisibility(View.GONE);
            llBuy.setVisibility(View.VISIBLE);

            thingsContent18.setText("");
            thingsContent19.setText("");
            thingsContent20.setText("");
            useContent5.setText("");
            tvFj5.setText("请选择(选填)");
            rvFjname5.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_go1:
                typeThings = EnterThingsApplyActivity.this.getResources().getStringArray(R.array.things_type2);
                typeDialog = new WheelDialog(this, mTypeHandler, typeThings, typeName);
                typeDialog.builder().show();
                break;
            case R.id.select_receive:
                receiveThings = EnterThingsApplyActivity.this.getResources().getStringArray(R.array.things_receive);
                typeDialog = new WheelDialog(this, mReceiveHandler, receiveThings, typeName);
                typeDialog.builder().show();
                break;
            case R.id.select_renttype:
                rentTypeThings = EnterThingsApplyActivity.this.getResources().getStringArray(R.array.things_renttype);
                typeDialog = new WheelDialog(this, mRentHandler, rentTypeThings, typeName);
                typeDialog.builder().show();
                break;
            case R.id.select_buytype:
                buyTypeThings = EnterThingsApplyActivity.this.getResources().getStringArray(R.array.things_buytype);
                typeDialog = new WheelDialog(this, mBuyHandler, buyTypeThings, typeName);
                typeDialog.builder().show();
                break;
            case R.id.select_maketype:
                makeTypeThings = EnterThingsApplyActivity.this.getResources().getStringArray(R.array.things_maketype);
                typeDialog = new WheelDialog(this, mMakeHandler, makeTypeThings, typeName);
                typeDialog.builder().show();
                break;
            case R.id.looking1:
                rvFjname1.setVisibility(View.VISIBLE);
                looking1.setVisibility(View.GONE);
                inlooking1.setVisibility(View.VISIBLE);
                inlookLine1.setVisibility(View.VISIBLE);
                break;

            case R.id.inlooking1:
                scrollview.scrollTo(0, 0);
                rvFjname1.setVisibility(View.GONE);
                looking1.setVisibility(View.VISIBLE);
                inlooking1.setVisibility(View.GONE);
                inlookLine1.setVisibility(View.GONE);
                break;

            case R.id.looking2:
                rvFjname2.setVisibility(View.VISIBLE);
                looking2.setVisibility(View.GONE);
                inlooking2.setVisibility(View.VISIBLE);
                inlookLine2.setVisibility(View.VISIBLE);
                break;

            case R.id.inlooking2:
                scrollview.scrollTo(0, 0);
                rvFjname2.setVisibility(View.GONE);
                looking2.setVisibility(View.VISIBLE);
                inlooking2.setVisibility(View.GONE);
                inlookLine2.setVisibility(View.GONE);
                break;

            case R.id.looking3:
                rvFjname3.setVisibility(View.VISIBLE);
                looking3.setVisibility(View.GONE);
                inlooking3.setVisibility(View.VISIBLE);
                inlookLine3.setVisibility(View.VISIBLE);
                break;

            case R.id.inlooking3:
                scrollview.scrollTo(0, 0);
                rvFjname3.setVisibility(View.GONE);
                looking3.setVisibility(View.VISIBLE);
                inlooking3.setVisibility(View.GONE);
                inlookLine3.setVisibility(View.GONE);
                break;

            case R.id.looking4:
                rvFjname4.setVisibility(View.VISIBLE);
                looking4.setVisibility(View.GONE);
                inlooking4.setVisibility(View.VISIBLE);
                inlookLine4.setVisibility(View.VISIBLE);
                break;

            case R.id.inlooking4:
                scrollview.scrollTo(0, 0);
                rvFjname4.setVisibility(View.GONE);
                looking4.setVisibility(View.VISIBLE);
                inlooking4.setVisibility(View.GONE);
                inlookLine4.setVisibility(View.GONE);
                break;

            case R.id.looking5:
                rvFjname5.setVisibility(View.VISIBLE);
                looking5.setVisibility(View.GONE);
                inlooking5.setVisibility(View.VISIBLE);
                inlookLine5.setVisibility(View.VISIBLE);
                break;

            case R.id.inlooking5:
                scrollview.scrollTo(0, 0);
                rvFjname5.setVisibility(View.GONE);
                looking5.setVisibility(View.VISIBLE);
                inlooking5.setVisibility(View.GONE);
                inlookLine5.setVisibility(View.GONE);
                break;

            case R.id.tv_fj1:
            case R.id.tv_fj2:
            case R.id.tv_fj3:
            case R.id.tv_fj4:
            case R.id.tv_fj5:
                opFileManager();
                break;
            case R.id.time1:
                //时间选择器
                name = "t1";
                time();
                break;
            case R.id.time2:
                //时间选择器
                name = "t2";
                time();
                break;
            case R.id.time3:
                //时间选择器
                name = "t3";
                time();
                break;
            case R.id.time4:
                //时间选择器
                name = "t4";
                time();
                break;
//            case R.id.apply_seal:
//                Intent intent = new Intent(this, EnterSealApplyActivity.class);
//                startActivity(intent);
//                break;
            case R.id.tv_apl:
                judge();
                break;
            case R.id.inbox_layout_shenqi:

                if(bean.getData().getAccountDeptId().equals("0155")){
                    intent= new Intent(EnterThingsApplyActivity.this,ChooseReceivers_Spsq_Activity.class);
                }else{
                    intent= new Intent(EnterThingsApplyActivity.this,Chooses_ReceiversSPAQ_Activity.class);
                }
                intent.putExtra("type","zhu");
                intent.putExtra("zhusong",zhusong);
                intent.putExtra("caosong",caosong);
                intent.putExtra("tong",tong);
                startActivityForResult(intent,20);

                break;
            case R.id.inbox_layout_caosong:

                if(bean.getData().getAccountDeptId().equals("0155")){
                    intent= new Intent(EnterThingsApplyActivity.this,ChooseReceivers_Spsq_Activity.class);
                }else{
                    intent= new Intent(EnterThingsApplyActivity.this,Chooses_ReceiversSPAQ_Activity.class);
                }
                intent.putExtra("type","cao");
                intent.putExtra("zhusong",zhusong);
                intent.putExtra("caosong",caosong);
                intent.putExtra("tong",tong);
                startActivityForResult(intent,20);

                break;
        }
    }

    private void judge() {
        if (TextUtils.isEmpty(typeName)) {
            ManagerUtils.showToast(this, "请选择调用类型");
            return;
        }

        if (theme_content.getText().toString().equals("")) {
            ManagerUtils.showToast(this, "请填写申请主题");
            return;
        }
        switch (typeName) {
            case "其他":
                if (TextUtils.isEmpty(thingsContent1.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入购置内容");
                    return;
                }
                if (TextUtils.isEmpty(thingsContent2.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入购置数量");
                    return;
                }
                if (TextUtils.isEmpty(thingsContent4.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入购置价格");
                    return;
                }
                if (!time1.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择调派日期");
                    return;
                }
                break;

            case "租用申请":
                if (TextUtils.isEmpty(thingsContent8.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入供租方");
                    return;
                }
                if (TextUtils.isEmpty(thingsContent9.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入联系方式");
                    return;
                }
                if (TextUtils.isEmpty(thingsContent10.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入数量");
                    return;
                }
                if (tv_return31.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择租用开始时间");
                    return;
                }
                if (tv_return3.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择租用结束时间");
                    return;
                }
                if (time2.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择调派日期");
                    return;
                }
                break;
            case "申请采购":
                if (selectBuyType.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择采购类型");
                    return;
                }
                if (time3.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择期望交付日期");
                    return;
                }
                if (reason_apply.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请输入申请事由");
                    return;
                }
                 if (thingsContent13.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请输入名称");
                    return;
                }
                 if (thingsContent14.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请输入规格");
                    return;
                }
                 if (thingsContent15.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请输入数量");
                    return;
                }
                 if (thingsContent16.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请输入单位");
                    return;
                }
                if (thingsContent17.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请输入价格");
                    return;
                }
                break;
            case "物品领用":
                if (TextUtils.isEmpty(thingsContent5.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入物品用途");
                    return;
                }
                if (TextUtils.isEmpty(thingsContent6.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入库存数量");
                    return;
                }
                if (TextUtils.isEmpty(thingsContent7.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入领用数量");
                    return;
                }
                if (TextUtils.isEmpty(useContent2.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入领用详情");
                    return;
                }
                break;
            case "物品申购":
                if (TextUtils.isEmpty(useContent5.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入申请事由");
                    return;
                }
                break;
            case "定制申请":
                if (selectMakeType.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择定制类型");
                    return;
                }
                if (time4.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择交付日期");
                    return;
                }
                break;
        }
        new DialogUtil(EnterThingsApplyActivity.this, new DialogUtil.OnClickListenner() {
            @Override
            public void yesClick() {

                popupWindowUtil = new PopupWindowUtil(EnterThingsApplyActivity.this, "提交中...");
                popupWindowUtil.show();

                RequestBody type_body = RequestBody.create(MediaType.parse("multipart/form-data"), type);  //主类型
                RequestBody childType_body = RequestBody.create(MediaType.parse("multipart/form-data"), childType);  //副类型
                RequestBody theme_body = RequestBody.create(MediaType.parse("multipart/form-data"), theme_content.getText().toString());  //主类型
                RequestBody opIds_shenqi_body = RequestBody.create(MediaType.parse("multipart/form-data"), zhusong);  //审批人id
                RequestBody opIds_chaosong_body = RequestBody.create(MediaType.parse("multipart/form-data"), caosong);  //抄送人opid
                RequestBody selectBuyType_body = RequestBody.create(MediaType.parse("multipart/form-data"), selectBuyType.getText().toString());  //采购类型
                RequestBody reason_apply_body = RequestBody.create(MediaType.parse("multipart/form-data"), reason_apply.getText().toString());  //申请事由
                RequestBody time3_body = RequestBody.create(MediaType.parse("multipart/form-data"), time3.getText().toString());  //期望交付日期
                RequestBody thingsContent13_body = RequestBody.create(MediaType.parse("multipart/form-data"), thingsContent13.getText().toString());  //名称
                RequestBody thingsContent14_body = RequestBody.create(MediaType.parse("multipart/form-data"), thingsContent14.getText().toString());  //规格
                RequestBody thingsContent15_body = RequestBody.create(MediaType.parse("multipart/form-data"), thingsContent15.getText().toString());  //数量
                RequestBody thingsContent16_body = RequestBody.create(MediaType.parse("multipart/form-data"), thingsContent16.getText().toString());  //单位
                RequestBody thingsContent17_body = RequestBody.create(MediaType.parse("multipart/form-data"), thingsContent17.getText().toString());  //价格
                String opId = new GetAccount(EnterThingsApplyActivity.this).opId();
                RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), opId);  //当前登陆者的Id
                Map<String, RequestBody> paramsMap = new HashMap<>();
                for (int i = 0; i < fileList.size(); i++) {
                    File file = fileList.get(i);
                    RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    paramsMap.put("file\"; filename=\"" + file.getName(), fileBody);
                }

                RetrofitHelper.getInstance().doInsert_Sqcg(userId, theme_body,type_body, childType_body, opIds_shenqi_body, opIds_chaosong_body, selectBuyType_body, reason_apply_body, time3_body, thingsContent13_body, thingsContent14_body, thingsContent15_body, thingsContent16_body, thingsContent17_body, paramsMap, new CallBackYSAdapter() {
                    @Override
                    protected void showErrorMessage(String message) {
                        if(popupWindowUtil != null){
                            popupWindowUtil.dismiss();
                        }
                        Toast.makeText(EnterThingsApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void parseJson(String data) {

                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            if(jsonObject.getBoolean("data")){
                                if(popupWindowUtil != null){
                                    popupWindowUtil.dismiss();
                                }
                                Toast.makeText(EnterThingsApplyActivity.this,"申请提交成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                if(popupWindowUtil != null){
                                    popupWindowUtil.dismiss();
                                }
                                Toast.makeText(EnterThingsApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            if(popupWindowUtil != null){
                                popupWindowUtil.dismiss();
                            }
                            Toast.makeText(EnterThingsApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }

            @Override
            public void noClick() {

            }

            @Override
            public void onSingleClick() {

            }
        }).showConfirm("提交审批", "您确定要提交该审批吗？", "确定", "取消");
    }

    private void time() {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
                switch (name) {
                    case "t1":
                        time1.setText(" " + format);
                        break;
                    case "t2":
                        time2.setText(" " + format);
                        break;
                    case "t3":
                        time3.setText(" " + format);
                        break;
                    case "t4":
                        time4.setText(" " + format);
                        break;
                }
            }
        }).setSubmitColor(Color.parseColor("#00a7e4"))
                .setCancelColor(Color.parseColor("#00a7e4"))
                .setSubCalSize(16)
                .setContentSize(16)
                .setDividerColor(Color.parseColor("#e5e5e5"))
                .setTitleBgColor(Color.WHITE)
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .setRange(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.YEAR) + 5)
                .setLabel("年", "月", "日", "", "", "")
                .build();
        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();
    }

    /**
     * 打开文件管理
     */
    private static final int FILE_SELECT_CODE = 0X111;

    private void opFileManager() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "请选择文件!"),
                    FILE_SELECT_CODE);
        } catch (ActivityNotFoundException ex) {
            Toast.makeText(this, "请安装文件管理器", Toast.LENGTH_SHORT).show();
        }

    }

    ArrayList<File> fileList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                Uri newUri;
                if (data == null) {
                    return;
                }

                try {
                    newUri = Uri.parse(CropUtils.getPath(this, data.getData()));
                    Log.e("小于25File", String.valueOf(newUri));
                    File file = new File(String.valueOf(newUri));
                    Log.e("ccc", file.getName());
                    if (file != null) {
                        fileList.add(file);
                    }

                    fjadapter = new AddfjAdapter(fileList, this);
                    typesAdapter();   //给附件名称的recyclerview设置adapter    不同情况不同的显示隐藏
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, "请通过其他方式选择该附件", Toast.LENGTH_SHORT).show();
                }

                break;

            case 520:

                zhusong = data.getStringExtra("zhusong");
                caosong = data.getStringExtra("caosong");
                tong = data.getStringExtra("tong");
                Log.e("zhusong已选定，待提交",zhusong);
                Log.e("caosong已选定，待提交",caosong);
                Log.e("tong已选定，待提交",tong);

                if(!zhusong.equals("")){
                    tv_go2.setText("已选择");
                }else{
                    tv_go2.setText("请选择(选填)");
                }
                if(!caosong.equals("")){
                    tv_go3.setText("已选择");
                }else{
                    tv_go3.setText("请选择(选填)");
                }


        break;
        }
    }

    private void typesAdapter() {
        switch (typeName) {

            case "其他":
                rvFjname1.setLayoutManager(new LinearLayoutManager(EnterThingsApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
                rvFjname1.setAdapter(fjadapter);
                fjadapter.notifyDataSetChanged();

                if (fileList != null || fileList.size() > 0) {
                    tvFj1.setText("        ");   //将添加附件选填文字设置为空格
                    rvFjname1.setVisibility(View.VISIBLE);
                    if (fileList.size() > 3) {
                        looking1.setVisibility(View.VISIBLE);
                        inlooking1.setVisibility(View.GONE);
                        inlookLine1.setVisibility(View.GONE);
                        rvFjname1.setVisibility(View.GONE);
                    }

                }
                break;
            case "租用申请":
                rvFjname2.setLayoutManager(new LinearLayoutManager(EnterThingsApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
                rvFjname2.setAdapter(fjadapter);
                fjadapter.notifyDataSetChanged();

                if (fileList != null || fileList.size() > 0) {
                    tvFj2.setText("        ");  //将添加附件选填文字设置为空格
                    rvFjname2.setVisibility(View.VISIBLE);
                    if (fileList.size() > 3) {
                        looking2.setVisibility(View.VISIBLE);
                        inlooking2.setVisibility(View.GONE);
                        inlookLine2.setVisibility(View.GONE);
                        rvFjname2.setVisibility(View.GONE);
                    }

                }
                break;
            case "物品领用":
                rvFjname3.setLayoutManager(new LinearLayoutManager(EnterThingsApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
                rvFjname3.setAdapter(fjadapter);
                fjadapter.notifyDataSetChanged();

                if (fileList != null || fileList.size() > 0) {
                    tvFj3.setText("        "); //将添加附件选填文字设置为空格
                    rvFjname3.setVisibility(View.VISIBLE);
                    if (fileList.size() > 3) {
                        looking3.setVisibility(View.VISIBLE);
                        inlooking3.setVisibility(View.GONE);
                        inlookLine3.setVisibility(View.GONE);
                        rvFjname3.setVisibility(View.GONE);
                    }

                }
                break;
            case "申请采购":
                rvFjname4.setLayoutManager(new LinearLayoutManager(EnterThingsApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
                rvFjname4.setAdapter(fjadapter);
                fjadapter.notifyDataSetChanged();

                if (fileList != null || fileList.size() > 0) {
                    tvFj4.setText("        ");  //将添加附件选填文字设置为空格
                    rvFjname4.setVisibility(View.VISIBLE);
                    if (fileList.size() > 3) {
                        looking4.setVisibility(View.VISIBLE);
                        inlooking4.setVisibility(View.GONE);
                        inlookLine4.setVisibility(View.GONE);
                        rvFjname4.setVisibility(View.GONE);
                    }

                }
                break;
            case "定制申请":
                rvFjname5.setLayoutManager(new LinearLayoutManager(EnterThingsApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
                rvFjname5.setAdapter(fjadapter);
                fjadapter.notifyDataSetChanged();

                if (fileList != null || fileList.size() > 0) {
                    tvFj5.setText("        "); //将添加附件选填文字设置为空格
                    rvFjname5.setVisibility(View.VISIBLE);
                    if (fileList.size() > 3) {
                        looking5.setVisibility(View.VISIBLE);
                        inlooking5.setVisibility(View.GONE);
                        inlookLine5.setVisibility(View.GONE);
                        rvFjname5.setVisibility(View.GONE);
                    }

                }
                break;
        }
    }

    class AddfjAdapter extends RecyclerView.Adapter<AddfjAdapter.MyViewHolder1> {

        private Activity activity;
        private ArrayList<File> fileArrayList;
        private View view = null;

        public AddfjAdapter(ArrayList<File> fileList, Activity activity) {
            this.fileArrayList = fileList;
            this.activity = activity;
        }

        @Override
        public AddfjAdapter.MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
            view = getLayoutInflater().inflate(R.layout.createfj_layout1, parent, false);
            AddfjAdapter.MyViewHolder1 myViewHolder = new AddfjAdapter.MyViewHolder1(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final AddfjAdapter.MyViewHolder1 holder, final int position) {
            if (fileArrayList != null && fileArrayList.size() > 0)
                holder.addtional1.setText(fileArrayList.get(position).getName());
            holder.setPosition(position);
        }

        @Override
        public int getItemCount() {
            if (fileArrayList != null && fileArrayList.size() > 0)
                return fileArrayList.size();
            return 0;
        }

        class MyViewHolder1 extends RecyclerView.ViewHolder {

            TextView addtional1;
            ImageView removeFj1;
            int position;

            public MyViewHolder1(View itemView) {

                super(itemView);
                addtional1 = (TextView) itemView.findViewById(R.id.addtional1);
                removeFj1 = (ImageView) itemView.findViewById(R.id.remove_fj1);

                removeFj1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (fileList.size() >= 1) {
                            fileList.remove(position);  //将所对应的附件移除
                        }
                        notifyDataSetChanged();

                        switch (typeName) {
                            case "其他":
                                change();
                                break;
                            case "租用申请":
                                change2();
                                break;
                            case "物品领用":
                                change3();
                                break;
                            case "申请采购":
                                change4();
                                break;
                            case "定制申请":
                                change5();
                                break;
                        }
                    }
                });
            }

            public void setPosition(int position) {
                this.position = position;
            }

            public void change() {
                if (fileList != null && fileList.size() > 0) {     // TODO: 2017/8/4/004   既然是非空判断，就得用     &&
                    tvFj1.setText("    ");   //将添加附件选填文字设置为空格
                    rvFjname1.setVisibility(View.VISIBLE);   //附件名称的recyclerview
                    if (fileList.size() > 3) {  //如果收件人的集合大于三
                        looking1.setVisibility(View.VISIBLE);
                        inlooking1.setVisibility(View.GONE);
                        inlookLine1.setVisibility(View.GONE);
                        rvFjname1.setVisibility(View.GONE);
                    } else {
                        looking1.setVisibility(View.GONE);
                        inlooking1.setVisibility(View.GONE);
                        inlookLine1.setVisibility(View.GONE);
                    }
                }
                if (fileList.size() <= 0) {                           // TODO: 2017/8/4/004   上面是做非空判断的，你不能把判断是否为0跟在非空判断后面，你得重新写if
                    rvFjname1.setVisibility(View.GONE);
                    tvFj1.setText("请选择(选填)");
                }
            }

            public void change2() {
                if (fileList != null && fileList.size() > 0) {
                    tvFj2.setText("    ");   //将添加附件选填文字设置为空格
                    rvFjname2.setVisibility(View.VISIBLE);
                    if (fileList.size() > 3) {  //如果收件人的集合大于三
                        looking2.setVisibility(View.VISIBLE);
                        inlooking2.setVisibility(View.GONE);
                        inlookLine2.setVisibility(View.GONE);
                        rvFjname2.setVisibility(View.GONE);
                    } else {
                        looking2.setVisibility(View.GONE);
                        inlooking2.setVisibility(View.GONE);
                        inlookLine2.setVisibility(View.GONE);
                    }
                }
                if (fileList.size() <= 0) {
                    rvFjname2.setVisibility(View.GONE);
                    tvFj2.setText("请选择(选填)");
                }
            }

            public void change3() {
                if (fileList != null && fileList.size() > 0) {
                    tvFj3.setText("    ");   //将添加附件选填文字设置为空格
                    rvFjname3.setVisibility(View.VISIBLE);
                    if (fileList.size() > 3) {  //如果收件人的集合大于三
                        looking3.setVisibility(View.VISIBLE);
                        inlooking3.setVisibility(View.GONE);
                        inlookLine3.setVisibility(View.GONE);
                        rvFjname3.setVisibility(View.GONE);
                    } else {
                        looking3.setVisibility(View.GONE);
                        inlooking3.setVisibility(View.GONE);
                        inlookLine3.setVisibility(View.GONE);
                    }
                }
                if (fileList.size() <= 0) {
                    rvFjname3.setVisibility(View.GONE);
                    tvFj3.setText("请选择(选填)");
                }
            }

            public void change4() {
                if (fileList != null && fileList.size() > 0) {
                    tvFj4.setText("    ");   //将添加附件选填文字设置为空格
                    rvFjname4.setVisibility(View.VISIBLE);
                    if (fileList.size() > 3) {  //如果收件人的集合大于三
                        looking4.setVisibility(View.VISIBLE);
                        inlooking4.setVisibility(View.GONE);
                        inlookLine4.setVisibility(View.GONE);
                        rvFjname4.setVisibility(View.GONE);
                    } else {
                        looking4.setVisibility(View.GONE);
                        inlooking4.setVisibility(View.GONE);
                        inlookLine4.setVisibility(View.GONE);
                    }
                }
                if (fileList.size() <= 0) {
                    rvFjname4.setVisibility(View.GONE);
                    tvFj4.setText("请选择(选填)");
                }
            }

            public void change5() {
                if (fileList != null && fileList.size() > 0) {
                    tvFj5.setText("    ");   //将添加附件选填文字设置为空格
                    rvFjname5.setVisibility(View.VISIBLE);
                    if (fileList.size() > 3) {  //如果收件人的集合大于三
                        looking5.setVisibility(View.VISIBLE);
                        inlooking5.setVisibility(View.GONE);
                        inlookLine5.setVisibility(View.GONE);
                        rvFjname5.setVisibility(View.GONE);
                    } else {
                        looking5.setVisibility(View.GONE);
                        inlooking5.setVisibility(View.GONE);
                        inlookLine5.setVisibility(View.GONE);
                    }
                }
                if (fileList.size() <= 0) {
                    rvFjname5.setVisibility(View.GONE);
                    tvFj5.setText("请选择(选填)");
                }
            }


        }
    }


}

