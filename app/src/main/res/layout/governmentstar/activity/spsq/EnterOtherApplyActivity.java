package com.lanwei.governmentstar.activity.spsq;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.icarus.Callback;
import com.icarus.Icarus;
import com.icarus.TextViewToolbar;
import com.icarus.Toolbar;
import com.icarus.button.Button;
import com.icarus.button.FontScaleButton;
import com.icarus.button.TextViewButton;
import com.icarus.entity.Html;
import com.icarus.entity.Options;
import com.icarus.popover.FontScalePopoverImpl;
import com.lanwei.governmentstar.R;
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
import java.util.Arrays;
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
 */

public class EnterOtherApplyActivity extends AutoLayoutActivity implements View.OnClickListener {
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
    @InjectView(R.id.ll_tysp)
    LinearLayout llTysp;
    @InjectView(R.id.ll_reception)
    LinearLayout llReception;
    @InjectView(R.id.ll_activity)
    LinearLayout llActivity;
    @InjectView(R.id.ll_hysp)
    LinearLayout llHysp;
    @InjectView(R.id.use_content1)
    EditText useContent1;
    @InjectView(R.id.inbox_layout_shenqi)
    RelativeLayout inbox_layout_shenqi;
    @InjectView(R.id.inbox_layout_caosong)
    RelativeLayout inbox_layout_caosong;
    @InjectView(R.id.use_content2)
    EditText useContent2;
    @InjectView(R.id.use_content3)
    EditText useContent3;
    @InjectView(R.id.use_content4)
    EditText useContent4;
    @InjectView(R.id.rv_fjname1)
    RecyclerView rvFjname1;
    @InjectView(R.id.rv_fjname2)
    RecyclerView rvFjname2;
    @InjectView(R.id.rv_fjname3)
    RecyclerView rvFjname3;
    @InjectView(R.id.rv_fjname4)
    RecyclerView rvFjname4;
    @InjectView(R.id.tv_fj1)
    TextView tvFj1;
    @InjectView(R.id.tv_fj2)
    TextView tvFj2;
    @InjectView(R.id.tv_go2)
    TextView tv_go2;
    @InjectView(R.id.tv_go3)
    TextView tv_go3;
    @InjectView(R.id.tv_fj3)
    TextView tvFj3;
    @InjectView(R.id.tv_fj4)
    TextView tvFj4;
    @InjectView(R.id.looking1)
    TextView looking1;
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
    @InjectView(R.id.other_content1)
    EditText otherContent1;
    @InjectView(R.id.other_conten2)
    EditText otherContent2;
    @InjectView(R.id.other_conten3)
    EditText otherContent3;
    @InjectView(R.id.other_conten4)
    EditText otherContent4;
    @InjectView(R.id.other_conten5)
    EditText otherContent5;
    @InjectView(R.id.other_conten6)
    EditText otherContent6;
    @InjectView(R.id.other_conten7)
    EditText otherContent7;
    @InjectView(R.id.other_conten8)
    EditText otherContent8;
    @InjectView(R.id.other_conten9)
    EditText otherContent9;
    @InjectView(R.id.use_content1)
    EditText use_content1;
    @InjectView(R.id.time1)
    TextView time1;
    @InjectView(R.id.time2)
    TextView time2;
    @InjectView(R.id.time3)
    TextView time3;
    @InjectView(R.id.iv_pic)
    ImageView ivPic;
    @InjectView(R.id.jdtype)
    TextView jdType;
    @InjectView(R.id.hdtype)
    TextView hdType;
    @InjectView(R.id.hytype)
    TextView hyType;
    @InjectView(R.id.nametype)
    TextView nameType;
    @InjectView(R.id.theme_content)
    EditText theme_content;
    @InjectView(R.id.editor)
    WebView webView;
    protected Icarus icarus;
    private String content = "";


    private String[] typeOther;  //选择物品类型
    private WheelDialog typeDialog;
    private String typeName;
    private PopupWindowUtil popupWindowUtil;
    private String type_main = "其他申请";
    private String childType;
    private Intent intent;
    private String zhusong = "";
    private String tong = "";
    private String zhusong_temp = "";
    private String caosong = "";
    private String caosong_temp = "";
    private Logging_Success bean;
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
    private Handler mJDHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                if (!TextUtils.isEmpty(typeDialog.getmCurrentName()))
                    jdType.setText(typeDialog.getmCurrentName());
            }
            return false;
        }
    });
    private Handler mHDHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                if (!TextUtils.isEmpty(typeDialog.getmCurrentName()))
                    hdType.setText(typeDialog.getmCurrentName());
            }
            return false;
        }
    });
    private Handler mHYHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                if (!TextUtils.isEmpty(typeDialog.getmCurrentName()))
                    hyType.setText(typeDialog.getmCurrentName());
            }
            return false;
        }
    });
    private String name;
    private AddfjAdapter fjadapter;
    private String[] typeJd;
    private String[] typeHd;
    private String[] typeHy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.activity_enterotherapply);
        ButterKnife.inject(this);

        initview();
        TextViewToolbar toolbar = new TextViewToolbar();
        Options options = new Options();
        options.setPlaceholder("请输入申请内容 ： ");
        options.addAllowedAttributes("img", Arrays.asList("data-type", "data-id", "class", "src", "alt", "width", "height"));
        options.addAllowedAttributes("iframe", Arrays.asList("data-type", "data-id", "class", "src", "width", "height"));
        options.addAllowedAttributes("a", Arrays.asList("data-type", "data-id", "class", "href", "target", "title"));

        icarus = new Icarus(toolbar, options, webView);
        prepareToolbar(toolbar, icarus);
        icarus.loadCSS("file:///android_asset/editor.css");
        icarus.loadJs("file:///android_asset/test.js");
        icarus.render();
        // 获取bean;
        String defString = PreferencesManager.getInstance(EnterOtherApplyActivity.this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);
    }

    private void initview() {
        nameType.setText("申请类型");
        tvAddress.setVisibility(View.VISIBLE);
        tvAddress.setText("其他申请");
        back.setVisibility(View.VISIBLE);
        ivContacts.setVisibility(View.GONE);
        tvApl.setVisibility(View.VISIBLE);
        tvApl.setText("提交审批");
        tvApl.setOnClickListener(this);
        back.setOnClickListener(this);
        tvGo1.setOnClickListener(this);
        tvGo1.setOnClickListener(this);
        tvFj1.setOnClickListener(this);
        tvFj2.setOnClickListener(this);
        tvFj3.setOnClickListener(this);
        tvFj4.setOnClickListener(this);
        looking1.setOnClickListener(this);
        inlooking1.setOnClickListener(this);
        looking2.setOnClickListener(this);
        inlooking2.setOnClickListener(this);
        looking3.setOnClickListener(this);
        inlooking3.setOnClickListener(this);
        looking4.setOnClickListener(this);
        inlooking4.setOnClickListener(this);
        time1.setOnClickListener(this);
        time2.setOnClickListener(this);
        time3.setOnClickListener(this);
        inbox_layout_shenqi.setOnClickListener(this);
        inbox_layout_caosong.setOnClickListener(this);
        jdType.setOnClickListener(this);
        hdType.setOnClickListener(this);
        hyType.setOnClickListener(this);

    }


    private Toolbar prepareToolbar(TextViewToolbar toolbar, Icarus icarus) {
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "Simditor.ttf");
        HashMap<String, Integer> generalButtons = new HashMap<>();
        generalButtons.put(Button.NAME_BOLD, R.id.button_bold);
        generalButtons.put(Button.NAME_OL, R.id.button_list_ol);
        generalButtons.put(Button.NAME_BLOCKQUOTE, R.id.button_blockquote);
        generalButtons.put(Button.NAME_UL, R.id.button_list_ul);
        generalButtons.put(Button.NAME_ALIGN_LEFT, R.id.button_align_left);
        generalButtons.put(Button.NAME_ALIGN_CENTER, R.id.button_align_center);
        generalButtons.put(Button.NAME_ALIGN_RIGHT, R.id.button_align_right);
        generalButtons.put(Button.NAME_ITALIC, R.id.button_italic);
        generalButtons.put(Button.NAME_UNDERLINE, R.id.button_underline);
        generalButtons.put(Button.NAME_STRIKETHROUGH, R.id.button_strike_through);

        for (String name : generalButtons.keySet()) {
            TextView textView = (TextView) findViewById(generalButtons.get(name));
            if (textView == null) {
                continue;
            }
            textView.setTypeface(iconfont);
            TextViewButton button = new TextViewButton(textView, icarus);
            button.setName(name);
            toolbar.addButton(button);
        }

//        imageButtonTextView = (TextView) findViewById(R.id.button_image);
//        imageButtonTextView.setOnClickListener(this);
//        imageButtonTextView.setTypeface(iconfont);
//        TextViewButton imageButton = new TextViewButton(imageButtonTextView, icarus);
//        imageButton.setName(Button.NAME_IMAGE);
//        imageButton.setPopover(new ImagePopoverImpl(imageButtonTextView, icarus));
//        toolbar.addButton(imageButton);  //不注释掉的话不能自定义点击事件

        TextView fontScaleTextView = (TextView) findViewById(R.id.button_font_scale);
        fontScaleTextView.setTypeface(iconfont);
        TextViewButton fontScaleButton = new FontScaleButton(fontScaleTextView, icarus);
        fontScaleButton.setPopover(new FontScalePopoverImpl(fontScaleTextView, icarus));
        toolbar.addButton(fontScaleButton);

        return toolbar;
    }

    private void allTypes(String typeName) {

        if (fjadapter == null)
            fjadapter = new AddfjAdapter(fileList, EnterOtherApplyActivity.this);
        fileList.clear();     //如果类型改变了就清空选中附件
        fjadapter.notifyDataSetChanged();

        if (typeName.equals("通用审批")) {  // TODO: 2017/4/13   这里如果类型只能为整形的话 可能会出错
            llTysp.setVisibility(View.VISIBLE);
            llReception.setVisibility(View.GONE);
            llActivity.setVisibility(View.GONE);
            llHysp.setVisibility(View.GONE);

            otherContent1.setText("");
            useContent1.setText("");
            tvFj1.setText("请选择(选填)");
            rvFjname1.setVisibility(View.GONE);
        } else if (typeName.equals("接待申请")) {
            llTysp.setVisibility(View.GONE);
            llReception.setVisibility(View.VISIBLE);
            llActivity.setVisibility(View.GONE);
            llHysp.setVisibility(View.GONE);

            otherContent2.setText("");
            otherContent3.setText("");
            otherContent4.setText("");
            useContent2.setText("");
            tvFj2.setText("请选择(选填)");
            rvFjname2.setVisibility(View.GONE);
        } else if (typeName.equals("活动申请")) {
            llTysp.setVisibility(View.GONE);
            llReception.setVisibility(View.GONE);
            llActivity.setVisibility(View.VISIBLE);
            llHysp.setVisibility(View.GONE);
            otherContent5.setHint("请输入活动主题");
            otherContent5.setText("");
            otherContent6.setText("");
            otherContent7.setText("");
            useContent3.setText("");
            tvFj3.setText("请选择(选填)");
            rvFjname3.setVisibility(View.GONE);
        } else if (typeName.equals("会议审批")) {
            llTysp.setVisibility(View.GONE);
            llReception.setVisibility(View.GONE);
            llActivity.setVisibility(View.GONE);
            llHysp.setVisibility(View.VISIBLE);

            otherContent8.setText("");
            otherContent9.setText("");
            useContent4.setText("");
            tvFj4.setText("请选择(选填)");
            rvFjname4.setVisibility(View.GONE);
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
            case "通用审批":
//                if (TextUtils.isEmpty(otherContent1.getText().toString())) {
//                    ManagerUtils.showToast(this, "请输入购置内容");
//                    return;
//                }

                    icarus.getContent(new Callback() {
                        @Override
                        public void run(String params) {
                            Gson gson = new Gson();
                            Html html = gson.fromJson(params, Html.class);
                            content = html.getContent();
                            Log.e("发送通用审批Content gotten", content);

                        }
                    });


                break;
            case "接待申请":
                if (TextUtils.isEmpty(otherContent2.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入接待主题");
                    return;
                }
                if (TextUtils.isEmpty(useContent2.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入接待行程");
                    return;
                }
                break;
            case "活动申请":
                if (TextUtils.isEmpty(otherContent5.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入活动主题");
                    return;
                }
                if (hdType.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择活动类型");
                    return;
                }
                if (!time2.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择开始日期");
                    return;
                }
                if (TextUtils.isEmpty(useContent2.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入接待行程");
                    return;
                }
                break;
            case "会议审批":
                if (TextUtils.isEmpty(otherContent8.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入会议名称");
                    return;
                }
                if (hyType.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择会议类型");
                    return;
                }
                if (!time3.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择开始日期");
                    return;
                }
                if (TextUtils.isEmpty(useContent4.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入会议议程");
                    return;
                }
                break;
        }
        new DialogUtil(EnterOtherApplyActivity.this, new DialogUtil.OnClickListenner() {
            @Override
            public void yesClick() {

                popupWindowUtil = new PopupWindowUtil(EnterOtherApplyActivity.this, "提交中...");
                popupWindowUtil.show();

                if(typeName.equals("通用审批")){
                    RequestBody type_body = RequestBody.create(MediaType.parse("multipart/form-data"), type_main);  //主类型
                    RequestBody childType_body = RequestBody.create(MediaType.parse("multipart/form-data"), typeName);  //副类型
                    RequestBody theme_body = RequestBody.create(MediaType.parse("multipart/form-data"), theme_content.getText().toString());
                    RequestBody opIds_chaosong_body = RequestBody.create(MediaType.parse("multipart/form-data"), zhusong);  //抄送人opid
                    RequestBody docContent = RequestBody.create(MediaType.parse("multipart/form-data"), content);  //新建邮件的内容
                    RequestBody opIds_tongxing_body = RequestBody.create(MediaType.parse("multipart/form-data"), caosong);  //抄送人opid
                    RequestBody otherContent1_body = RequestBody.create(MediaType.parse("multipart/form-data"), otherContent1.getText().toString());  //过路费
                    RequestBody use_content1_body = RequestBody.create(MediaType.parse("multipart/form-data"), use_content1.getText().toString());  //其他费用
                    String opId = new GetAccount(EnterOtherApplyActivity.this).opId();
                    RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), opId);  //当前登陆者的Id
                    Map<String, RequestBody> paramsMap = new HashMap<>();
                    for (int i = 0; i < fileList.size(); i++) {
                        File file = fileList.get(i);
                        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        paramsMap.put("file\"; filename=\"" + file.getName(), fileBody);
                    }

                    RetrofitHelper.getInstance().doInsert_Qjsq2(userId, theme_body,type_body, childType_body, opIds_chaosong_body, opIds_tongxing_body,docContent
                            , paramsMap, new CallBackYSAdapter() {
                        @Override
                        protected void showErrorMessage(String message) {
                            if(popupWindowUtil != null){
                                popupWindowUtil.dismiss();
                            }
                            Toast.makeText(EnterOtherApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();
                            Log.e("发送申请公务失败", message);

                        }

                        @Override
                        protected void parseJson(String data) {

                            try {
                                JSONObject jsonObject=new JSONObject(data);
                                if(jsonObject.getBoolean("data")){
                                    if(popupWindowUtil != null){
                                        popupWindowUtil.dismiss();
                                    }
                                    Toast.makeText(EnterOtherApplyActivity.this,"申请提交成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    if(popupWindowUtil != null){
                                        popupWindowUtil.dismiss();
                                    }
                                    Toast.makeText(EnterOtherApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                if(popupWindowUtil != null){
                                    popupWindowUtil.dismiss();
                                }
                                Toast.makeText(EnterOtherApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();
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
        }).showConfirm("提交审批", "您确定要提交该审批吗？", "确定", "取消");
        return;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.inbox_layout_shenqi:

                if(bean.getData().getAccountDeptId().equals("0155")){
                    intent= new Intent(EnterOtherApplyActivity.this,ChooseReceivers_Spsq_Activity.class);
                }else{
                    intent= new Intent(EnterOtherApplyActivity.this,Chooses_ReceiversSPAQ_Activity.class);
                }
                intent.putExtra("type","zhu");
                intent.putExtra("zhusong",zhusong);
                intent.putExtra("caosong",caosong);
                intent.putExtra("tong",tong);
                startActivityForResult(intent,20);

                break;
            case R.id.inbox_layout_caosong:

                if(bean.getData().getAccountDeptId().equals("0155")){
                    intent= new Intent(EnterOtherApplyActivity.this,ChooseReceivers_Spsq_Activity.class);
                }else{
                    intent= new Intent(EnterOtherApplyActivity.this,Chooses_ReceiversSPAQ_Activity.class);
                }
                intent.putExtra("type","cao");
                intent.putExtra("zhusong",zhusong);
                intent.putExtra("caosong",caosong);
                intent.putExtra("tong",tong);
                startActivityForResult(intent,20);


                break;


            case R.id.tv_go1:
                typeOther = EnterOtherApplyActivity.this.getResources().getStringArray(R.array.other_type2);
                typeDialog = new WheelDialog(this, mTypeHandler, typeOther, typeName);
                typeDialog.builder().show();
                break;
            case R.id.jdtype:
                typeJd = EnterOtherApplyActivity.this.getResources().getStringArray(R.array.jd_type);
                typeDialog = new WheelDialog(this, mJDHandler, typeJd, typeName);
                typeDialog.builder().show();
                break;
            case R.id.hdtype:
                typeHd = EnterOtherApplyActivity.this.getResources().getStringArray(R.array.hd_type);
                typeDialog = new WheelDialog(this, mHDHandler, typeHd, typeName);
                typeDialog.builder().show();
                break;
            case R.id.hytype:
                typeHy = EnterOtherApplyActivity.this.getResources().getStringArray(R.array.hy_type);
                typeDialog = new WheelDialog(this, mHYHandler, typeHy, typeName);
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
            case R.id.tv_fj1:
            case R.id.tv_fj2:
            case R.id.tv_fj3:
            case R.id.tv_fj4:
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
//            case R.id.apply_seal:
//                Intent intent = new Intent(this, EnterSealApplyActivity.class);
//                startActivity(intent);
//                break;
            case R.id.tv_apl:
                judge();
                break;
        }
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
                Log.e("zhusong已选定，待提交",zhusong);
                Log.e("caosong已选定，待提交",caosong);

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
        }
    }

    private void typesAdapter() {
        switch (typeName) {

            case "通用审批":
                rvFjname1.setLayoutManager(new LinearLayoutManager(EnterOtherApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
            case "接待申请":
                rvFjname2.setLayoutManager(new LinearLayoutManager(EnterOtherApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
            case "活动申请":
                rvFjname3.setLayoutManager(new LinearLayoutManager(EnterOtherApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
            case "会议审批":
                rvFjname4.setLayoutManager(new LinearLayoutManager(EnterOtherApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
                            case "通用审批":
                                change();
                                break;
                            case "接待申请":
                                change2();
                                break;
                            case "活动申请":
                                change3();
                                break;
                            case "会议审批":
                                change4();
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

        }
    }
}

