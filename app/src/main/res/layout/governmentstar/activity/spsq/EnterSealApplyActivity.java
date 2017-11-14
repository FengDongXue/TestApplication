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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.Convey_Files_Activity;
import com.lanwei.governmentstar.activity.Process2_Activity;
import com.lanwei.governmentstar.activity.dzgd.Chooses_Receivers_Activity;
import com.lanwei.governmentstar.activity.gwxf.Choose_Handdowntree;
import com.lanwei.governmentstar.activity.zwyx.ZwyxTreeActivity;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2017/8/1/001.
 */

//资质印章申请
public class EnterSealApplyActivity extends BaseActivity implements View.OnClickListener, DialogUtil.OnClickListenner {
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
    @InjectView(R.id.tv_go2)
    TextView tvGo2;
    @InjectView(R.id.tv_go3)
    TextView tvGo3;
    @InjectView(R.id.ll_license)
    LinearLayout llLicense;
    @InjectView(R.id.sealtype_content2)
    EditText sealtypeContent2;
    @InjectView(R.id.tv_borrow2)
    TextView tvBorrow2;
    @InjectView(R.id.tv_return2)
    TextView tvReturn2;
    @InjectView(R.id.type_borrow)
    TextView typeBorrow;
    @InjectView(R.id.tv_fj2)
    TextView tvFj2;
    @InjectView(R.id.use_content2)
    EditText useContent2;
    @InjectView(R.id.sealtype_content1)
    EditText sealtypeContent1;
    @InjectView(R.id.tv_borrow1)
    TextView tvBorrow1;
    @InjectView(R.id.tv_return1)
    TextView tvReturn1;
    @InjectView(R.id.tv_fj1)
    TextView tvFj1;
    @InjectView(R.id.use_content1)
    EditText useContent1;
    @InjectView(R.id.ll_others)
    LinearLayout llOthers;
    @InjectView(R.id.sealtype_content3)
    EditText sealtypeContent3;
    @InjectView(R.id.sealtype_content4)
    EditText sealtypeContent4;
    @InjectView(R.id.tv_fj3)
    TextView tvFj3;
    @InjectView(R.id.use_content3)
    EditText useContent3;
    @InjectView(R.id.theme_content)
    EditText theme_content;
    @InjectView(R.id.ll_useseal)
    LinearLayout llUseseal;
    @InjectView(R.id.scrollview)
    ScrollView scrollview;
    @InjectView(R.id.looking1)
    TextView looking1;
    @InjectView(R.id.inlook_line1)
    View inlookLine1;
    @InjectView(R.id.inlooking1)
    ImageView inlooking1;
    @InjectView(R.id.rv_fjname1)
    RecyclerView rvFjname1;
    @InjectView(R.id.rv_fjname2)
    RecyclerView rvFjname2;
    @InjectView(R.id.rv_fjname3)
    RecyclerView rvFjname3;
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
    @InjectView(R.id.type_wj)
    TextView typeWj;
    @InjectView(R.id.type_yz)
    TextView typeYz;


    private String[] typeSeal;  //选择资质印章类型
    private WheelDialog typeDialog;
    private String type_main = "资质印章";
    private String typeName;
    private PopupWindowUtil popupWindowUtil;
    /**
     * Dialog返回数据
     **/
    private Handler mTypeHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                typeName = typeDialog.getmCurrentName();
                allTypes(typeName);   //选择器中的不同类型的名字
                if (!TextUtils.isEmpty(typeName))
                            tvGo1.setText(typeName);
            }
            return false;
        }
    });

    private Handler mBorrowHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                String typeName = typeDialog.getmCurrentName();
                if (!TextUtils.isEmpty(typeName))
                    typeBorrow.setText(typeName);
            }
            return false;
        }
    });

    private Handler mWjHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                String typeName = typeDialog.getmCurrentName();
                if (!TextUtils.isEmpty(typeName))
                    typeWj.setText(typeName);
            }
            return false;
        }
    });

    private Handler mYzCdHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                String typeName = typeDialog.getmCurrentName();
                if (!TextUtils.isEmpty(typeName))
                    typeYz.setText(typeName);
            }
            return false;
        }
    });
    private AddfjAdapter fjadapter;
    private String name = "";
    private Intent intent;
    private GovernmentApi api;
    private Logging_Success bean;
    private String type ="04";
    private String childType ="";

    private String zhusong = "";
    private String zhusong_temp = "";
    private String caosong = "";
    private String caosong_temp = "";
    private String tong = "";
    private String tong_temp = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.activity_entersealapply);
        ButterKnife.inject(this);

        initview();
        // 获取bean;
        String defString = PreferencesManager.getInstance(EnterSealApplyActivity.this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);

        // 获取opId和userId
        api= HttpClient.getInstance().getGovernmentApi();
    }

    private void initview() {
        tvAddress.setVisibility(View.VISIBLE);
        tvAddress.setText("资质印章申请");
        back.setVisibility(View.VISIBLE);
        ivContacts.setVisibility(View.GONE);
        tvApl.setVisibility(View.VISIBLE);
        tvApl.setText("提交审批");
        back.setOnClickListener(this);
        tvApl.setOnClickListener(this);
        tvGo1.setOnClickListener(this);
        tvGo2.setOnClickListener(this);
        tvGo3.setOnClickListener(this);

        tvBorrow1.setOnClickListener(this);
        tvReturn1.setOnClickListener(this);
        tvBorrow2.setOnClickListener(this);
        tvReturn2.setOnClickListener(this);
        tvFj1.setOnClickListener(this);
        tvFj2.setOnClickListener(this);
        tvFj3.setOnClickListener(this);
        looking1.setOnClickListener(this);
        inlooking1.setOnClickListener(this);
        looking2.setOnClickListener(this);
        inlooking2.setOnClickListener(this);
        looking3.setOnClickListener(this);
        inlooking3.setOnClickListener(this);

        typeBorrow.setOnClickListener(this);
        typeWj.setOnClickListener(this);
        typeYz.setOnClickListener(this);

    }

    private void allTypes(String typeName) {

        if (fjadapter == null)
            fjadapter = new AddfjAdapter(fileList, EnterSealApplyActivity.this);
        fileList.clear();     //如果类型改变了就清空选中附件
        fjadapter.notifyDataSetChanged();

        if (typeName.equals("机构证照申请")) {  // TODO: 2017/4/13   这里如果类型只能为整形的话 可能会出错
            llLicense.setVisibility(View.VISIBLE);
            llOthers.setVisibility(View.GONE);
            llUseseal.setVisibility(View.GONE);

            sealtypeContent2.setText("");
            useContent2.setText("");
            tvBorrow2.setText("请选择(必填)");
            tvReturn2.setText("请选择(必填)");
            tvFj2.setText("请选择(选填)");
            rvFjname2.setVisibility(View.GONE);

            typeBorrow.setText("请选择(必填)");
        } else if (typeName.equals("其他")) {
            llLicense.setVisibility(View.GONE);
            llOthers.setVisibility(View.VISIBLE);
            llUseseal.setVisibility(View.GONE);

            sealtypeContent1.setText("");
            useContent1.setText("");
            tvBorrow1.setText("请选择(必填)");
            tvReturn1.setText("请选择(必填)");
            tvFj1.setText("请选择(选填)");
            rvFjname1.setVisibility(View.GONE);

        } else if (typeName.equals("用印申请")) {
            llLicense.setVisibility(View.GONE);
            llOthers.setVisibility(View.GONE);
            llUseseal.setVisibility(View.VISIBLE);

            sealtypeContent3.setText("");
            sealtypeContent4.setText("");
            useContent3.setText("");
            tvFj3.setText("请选择(选填)");
            rvFjname3.setVisibility(View.GONE);

            typeWj.setText("请选择(必填)");
            typeYz.setText("请选择(必填)");

        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_apl:
                judge();
                break;
            case R.id.tv_go1:
                typeSeal = EnterSealApplyActivity.this.getResources().getStringArray(R.array.seal_type2);
                typeDialog = new WheelDialog(this, mTypeHandler, typeSeal, typeName);
                typeDialog.builder().show();
                break;
            case R.id.tv_go2:
                Intent intent;
                if(bean.getData().getAccountDeptId().equals("0155")){
                    intent= new Intent(EnterSealApplyActivity.this,ChooseReceivers_Spsq_Activity.class);
                }else{
                    intent= new Intent(EnterSealApplyActivity.this,Chooses_ReceiversSPAQ_Activity.class);
                }
                intent.putExtra("type","zhu");
                intent.putExtra("zhusong",zhusong);
                intent.putExtra("caosong",caosong);
                intent.putExtra("tong",tong);
                startActivityForResult(intent,20);
                break;

            case R.id.tv_go3:

                Intent intent2;
                if(bean.getData().getAccountDeptId().equals("0155")){
                    intent2= new Intent(EnterSealApplyActivity.this,ChooseReceivers_Spsq_Activity.class);
                }else{
                    intent2= new Intent(EnterSealApplyActivity.this,Chooses_ReceiversSPAQ_Activity.class);
                }
                intent2.putExtra("type","cao");
                intent2.putExtra("zhusong",zhusong);
                intent2.putExtra("caosong",caosong);
                intent2.putExtra("tong",tong);
                startActivityForResult(intent2,20);
                break;


            case R.id.type_borrow:
                typeSeal = EnterSealApplyActivity.this.getResources().getStringArray(R.array.seal_borrow);
                typeDialog = new WheelDialog(this, mBorrowHandler, typeSeal, typeName);
                typeDialog.builder().show();
                break;

            case R.id.type_wj:
                typeSeal = EnterSealApplyActivity.this.getResources().getStringArray(R.array.seal_wj);
                typeDialog = new WheelDialog(this, mWjHandler, typeSeal, typeName);
                typeDialog.builder().show();
                break;

            case R.id.type_yz:
                typeSeal = EnterSealApplyActivity.this.getResources().getStringArray(R.array.seal_yz);
                typeDialog = new WheelDialog(this, mYzCdHandler, typeSeal, typeName);
                typeDialog.builder().show();
                break;

            case R.id.tv_borrow1:
                //时间选择器
                name = "b1";
                time();
                break;

            case R.id.tv_return1:
                //时间选择器
                name = "r1";
                time();
                break;

            case R.id.tv_borrow2:
                //时间选择器
                name = "b2";
                time();
                break;

            case R.id.tv_return2:
                //时间选择器
                name = "r2";
                time();
                break;

            case R.id.tv_fj1:
            case R.id.tv_fj2:
            case R.id.tv_fj3:
                opFileManager();
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
//            case R.id.apply_seal:
//                Intent intent = new Intent(this, EnterSealApplyActivity.class);
//                startActivity(intent);
//                break;
        }
    }


    private void judge() {
            if (TextUtils.isEmpty(typeName)) {
                ManagerUtils.showToast(this, "请选择调用类型");
                return;
            }
        switch (typeName) {
            case "其他":

                if (theme_content.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写申请主题");
                    return;
                }
                if (TextUtils.isEmpty(sealtypeContent1.getText().toString()) || sealtypeContent1.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写资料名称");
                    return;
                }

                if (!tvBorrow1.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择借用日期");
                    return;
                }

                if (!tvReturn1.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择归还日期");
                    return;
                }

                if (TextUtils.isEmpty(useContent1.getText().toString()) || useContent1.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写备注说明");
                    return;
                }
                break;

            case "机构证照申请":

                if (theme_content.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写申请主题");
                    return;
                }
                if (TextUtils.isEmpty(sealtypeContent2.getText().toString()) || sealtypeContent2.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写资质类别");
                    return;
                }

                if (!tvBorrow2.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择借用日期");
                    return;
                }

                if (!tvReturn2.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择归还日期");
                    return;
                }

                if (typeBorrow.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择借用类型");
                    return;
                }

                if (TextUtils.isEmpty(useContent2.getText().toString()) || useContent2.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写使用事由");
                    return;
                }

                break;
            case "用印申请":

                if (theme_content.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写申请主题");
                    return;
                }
                if (TextUtils.isEmpty(sealtypeContent3.getText().toString()) || sealtypeContent3.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写用印文件");
                    return;
                }
                if (TextUtils.isEmpty(sealtypeContent4.getText().toString()) || sealtypeContent4.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写文件份数");
                    return;
                }
                if (typeWj.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择文件类型");
                    return;
                }
                if (typeYz.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择印章类型");
                    return;
                }
                if (TextUtils.isEmpty(useContent3.getText().toString())) {
                    ManagerUtils.showToast(this, "请填写备注说明");
                    return;
                }

                break;
        }

        new DialogUtil(EnterSealApplyActivity.this, this).showConfirm("提交审批", "您确定要提交该审批吗？", "确定", "取消");

        return;
    }


    private void time() {
        TimePickerView pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String format = new SimpleDateFormat("yyyy-MM-dd").format(date);
                switch (name) {
                    case "b1":
                        tvBorrow1.setText(" " + format);
                        break;
                    case "r1":
                        tvReturn1.setText(" " + format);
                        break;
                    case "b2":
                        tvBorrow2.setText(" " + format);
                        break;
                    case "r2":
                        tvReturn2.setText(" " + format);
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

    @Override
    public void yesClick() {

        popupWindowUtil = new PopupWindowUtil(EnterSealApplyActivity.this, "提交中...");
        popupWindowUtil.show();

        if (typeName.equals("机构证照申请")) {  // TODO: 2017/4/13   这里如果类型只能为整形的话 可能会出错



        } else if (typeName.equals("其他")) {




        } else if (typeName.equals("用印申请")) {


            childType = "用印申请";
            RequestBody theme_body = RequestBody.create(MediaType.parse("multipart/form-data"), theme_content.getText().toString());  //主类型
            RequestBody type_body = RequestBody.create(MediaType.parse("multipart/form-data"), type_main);  //主类型
            RequestBody childType_body = RequestBody.create(MediaType.parse("multipart/form-data"), typeName);  //副类型
            RequestBody opIds_shenqi_body = RequestBody.create(MediaType.parse("multipart/form-data"), zhusong);  //审批人id
            RequestBody opIds_chaosong_body = RequestBody.create(MediaType.parse("multipart/form-data"), caosong);  //抄送人opid
            RequestBody sealtypeContent3_body = RequestBody.create(MediaType.parse("multipart/form-data"), sealtypeContent3.getText().toString().trim());  //用印文件
            RequestBody sealtypeContent4_body = RequestBody.create(MediaType.parse("multipart/form-data"), sealtypeContent4.getText().toString().trim());  //文件份数
            RequestBody typeWj_body = RequestBody.create(MediaType.parse("multipart/form-data"), typeWj.getText().toString());  //文件类型
            RequestBody typeYz_body = RequestBody.create(MediaType.parse("multipart/form-data"), typeYz.getText().toString());  //印章类型
            RequestBody useContent3_body = RequestBody.create(MediaType.parse("multipart/form-data"), useContent3.getText().toString());  //备注说明
            String opId = new GetAccount(this).opId();
            Log.e("opId的公司法国发",opId);
            RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), opId);  //当前登陆者的Id
            Map<String, RequestBody> paramsMap = new HashMap<>();
            for (int i = 0; i < fileList.size(); i++) {
                File file = fileList.get(i);
                RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                paramsMap.put("file\"; filename=\"" + file.getName(), fileBody);
            }
            RetrofitHelper.getInstance().doInsert_zzzz(userId, theme_body,type_body, childType_body, opIds_shenqi_body, opIds_chaosong_body, sealtypeContent3_body, sealtypeContent4_body, typeWj_body, typeYz_body, useContent3_body, paramsMap, new CallBackYSAdapter() {
                @Override
                protected void showErrorMessage(String message) {

                    if(popupWindowUtil != null){
                        popupWindowUtil.dismiss();
                    }
                    Toast.makeText(EnterSealApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();

                }

                @Override
                protected void parseJson(String data) {

                    try {
                        JSONObject jsonObject=new JSONObject(data);
                        if(jsonObject.getBoolean("data")){
                            if(popupWindowUtil != null){
                                popupWindowUtil.dismiss();
                            }
                            Toast.makeText(EnterSealApplyActivity.this,"申请提交成功",Toast.LENGTH_SHORT).show();
                            finish();

                        }else{
                            if(popupWindowUtil != null){
                                popupWindowUtil.dismiss();
                            }
                            Toast.makeText(EnterSealApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        if(popupWindowUtil != null){
                            popupWindowUtil.dismiss();
                        }
                        Toast.makeText(EnterSealApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    @Override
    public void noClick() {

    }

    @Override
    public void onSingleClick() {

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
                Uri newUri = null;
                if (data == null) {
                    return;
                }

                try {
                    newUri = Uri.parse(CropUtils.getPath(this, data.getData()));
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
                Log.e("小于25File", String.valueOf(newUri));

                break;

            case 520:

                zhusong = data.getStringExtra("zhusong");
                caosong = data.getStringExtra("caosong");

                if(!zhusong.equals("")){
                    tvGo2.setText("已选择");
                }else{
                    tvGo2.setText("请选择(选填)");
                }
                if(!caosong.equals("")){
                    tvGo3.setText("已选择");
                }else{
                    tvGo3.setText("请选择(选填)");
                }

                    Log.e("zhusong已选定，待提交",zhusong);
                    Log.e("caosong已选定，待提交",caosong);

//                if(requestCode==10){
//
//                    opIdlist_shenqi = data.getStringArrayListExtra("opIdlist");
//                    addresseNameList_shenqi = data.getStringArrayListExtra("addresseNameList");
//                    opIds_shenqi = data.getStringExtra("opIds");
//                    for(int i=0;i<opIdlist_shenqi.size();i++){
//                        Log.e("opIdlist_shenqi", opIdlist_shenqi.get(i));
//                    }
//                    for(int i=0;i<addresseNameList_shenqi.size();i++){
//                        Log.e("addresseNameList_shenqi", addresseNameList_shenqi.get(i));
//                    }
//                    Log.e("opIds_shenqi", opIds_shenqi);
//                }else{
//
//                    opIdlist_chaosong = data.getStringArrayListExtra("opIdlist");
//                    addresseNameList_chaosong = data.getStringArrayListExtra("addresseNameList");
//                    opIds_chaosong = data.getStringExtra("opIds");
//                    for(int i=0;i<opIdlist_chaosong.size();i++){
//                        Log.e("opIdlist_chaosong", opIdlist_chaosong.get(i));
//                    }
//                    for(int i=0;i<addresseNameList_chaosong.size();i++){
//                        Log.e("addresse_chaosong", addresseNameList_chaosong.get(i));
//                    }
//                    Log.e("opIds_chaosong", opIds_chaosong);
//
//                }



                // 改变isNotChoosed标记，表明已进入过快速回复，选择过处理人，以便再次进入初始化这些已经选择的人
//                isNotChoosed = 1;
//                if (addresseNameList != null || addresseNameList.size() > 0) {
//                    addressRelView.setVisibility(View.VISIBLE);

//                    if (addresseNameList.size() > 3) {
//                        look.setVisibility(View.VISIBLE);
//                        inlook.setVisibility(View.GONE);
//                        inlookLine.setVisibility(View.GONE);
//                        addressRelView.setVisibility(View.GONE);
//                    }

//                    mFlowerAdapter = new TagAdapter<>(this);
//                    addressRelView.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_NONE);
//                    addressRelView.setAdapter(mFlowerAdapter);
//                    mFlowerAdapter.setPosition(-11);
//                    mFlowerAdapter.notifyDataSetChanged();
//                    addressRelView.setOnTagClickListener(new OnTagClickListener() {
//                        @Override
//                        public void onItemClick(FlowTagLayout parent, View view, int position) {
//                            mail_title.setCursorVisible(false);//设置EditText光标隐藏
//                            // 先隐藏键盘
//                            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
//                                    getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//
//                            mFlowerAdapter.setPosition(position);
//                            mFlowerAdapter.notifyDataSetChanged();
//                        }
//                    });
//                    addressRelView.setOnTagSelectListener(new OnTagSelectListener() {
//                        @Override
//                        public void onItemSelect(final FlowTagLayout parent, final List<Integer> selectedList) {
//                            mail_title.setCursorVisible(false);//设置EditText光标隐藏
//                            // 先隐藏键盘
//                            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
//                                    getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//
//
//                            if (selectedList != null && selectedList.size() >= 0) {
//                                StringBuilder sb = new StringBuilder();
//                                for (final int i : selectedList) {
//                                    sb.append(parent.getAdapter().getItem(i));
////                                    mFlowerAdapter.getItem(i);
//                                }
//                                Snackbar.make(parent, "收件人:" + sb.toString(), Snackbar.LENGTH_LONG)
//                                        .setAction("Action", null).show();
//                            } else {
//                                Snackbar.make(parent, "没有选择标签", Snackbar.LENGTH_LONG)
//                                        .setAction("Action", null).show();
//                            }
//                        }
//                    });
//                }
                break;
        }
    }

    private void typesAdapter() {
        switch (typeName) {
            case "其他":
                rvFjname1.setLayoutManager(new LinearLayoutManager(EnterSealApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
                fjadapter.notifyDataSetChanged();
                rvFjname1.setAdapter(fjadapter);
                if (fileList != null || fileList.size() > 0) {
                    tvFj1.setText("    ");   //将添加附件选填文字设置为空格
                    rvFjname1.setVisibility(View.VISIBLE);
                    if (fileList.size() > 3) {
                        looking1.setVisibility(View.VISIBLE);
                        inlooking1.setVisibility(View.GONE);
                        inlookLine1.setVisibility(View.GONE);
                        rvFjname1.setVisibility(View.GONE);
                    }
                }
                break;
            case "机构证照申请":
                rvFjname2.setLayoutManager(new LinearLayoutManager(EnterSealApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
                fjadapter.notifyDataSetChanged();
                rvFjname2.setAdapter(fjadapter);
                if (fileList != null || fileList.size() > 0) {
                    tvFj2.setText("    ");  //将添加附件选填文字设置为空格
                    rvFjname2.setVisibility(View.VISIBLE);
                    if (fileList.size() > 3) {
                        looking2.setVisibility(View.VISIBLE);
                        inlooking2.setVisibility(View.GONE);
                        inlookLine2.setVisibility(View.GONE);
                        rvFjname2.setVisibility(View.GONE);
                    }
                }
                break;
            case "用印申请":
                rvFjname3.setLayoutManager(new LinearLayoutManager(EnterSealApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
                rvFjname3.setAdapter(fjadapter);
                if (fileList != null || fileList.size() > 0) {
                    tvFj3.setText("    "); //将添加附件选填文字设置为空格
                    rvFjname3.setVisibility(View.VISIBLE);
                    if (fileList.size() > 3) {
                        looking3.setVisibility(View.VISIBLE);
                        inlooking3.setVisibility(View.GONE);
                        inlookLine3.setVisibility(View.GONE);
                        rvFjname3.setVisibility(View.GONE);
                    }

                }
                break;
        }

        fjadapter.notifyDataSetChanged();
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
        public MyViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
            view = getLayoutInflater().inflate(R.layout.createfj_layout1, parent, false);
            MyViewHolder1 myViewHolder = new MyViewHolder1(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder1 holder, final int position) {
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
                            case "机构证照申请":
                                change2();
                                break;
                            case "用印申请":
                                change3();
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
                    tvFj1.setText("        ");   //将添加附件选填文字设置为空格
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
                    tvFj2.setText("        ");   //将添加附件选填文字设置为空格
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
                    tvFj3.setText("        ");   //将添加附件选填文字设置为空格
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

        }
    }

}

