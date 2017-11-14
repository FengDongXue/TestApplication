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
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.utils.Constant;
import com.lanwei.governmentstar.utils.CropUtils;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.ManagerUtils;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.lanwei.governmentstar.view.wheel.AgeLimitWheelDialog;
import com.lanwei.governmentstar.view.wheel.WheelDialog;
import com.zhy.autolayout.AutoLayoutActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/8/2/002.
 */

public class EnterPersonApplyActivity extends AutoLayoutActivity implements View.OnClickListener {
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
    @InjectView(R.id.ll_jobs)
    LinearLayout llJobs;
    @InjectView(R.id.ll_out)
    LinearLayout llOut;
    @InjectView(R.id.ll_leave)
    LinearLayout llLeave;
    @InjectView(R.id.ll_cadre)
    LinearLayout llCadre;
    @InjectView(R.id.person_content1)
    EditText persontypeContent1;
    @InjectView(R.id.person_content2)
    EditText persontypeContent2;
    @InjectView(R.id.person_content3)
    EditText persontypeContent3;
    @InjectView(R.id.person_content4)
    EditText persontypeContent4;
    @InjectView(R.id.person_content5)
    EditText persontypeContent5;
    @InjectView(R.id.person_content6)
    EditText persontypeContent6;
    @InjectView(R.id.person_content7)
    EditText persontypeContent7;
    @InjectView(R.id.person_content8)
    EditText persontypeContent8;
    @InjectView(R.id.person_content9)
    EditText persontypeContent9;
    @InjectView(R.id.person_content10)
    EditText persontypeContent10;
    @InjectView(R.id.person_content11)
    EditText persontypeContent11;
    @InjectView(R.id.person_content12)
    EditText persontypeContent12;
    @InjectView(R.id.person_content13)
    EditText persontypeContent13;
    @InjectView(R.id.person_content14)
    EditText persontypeContent14;
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
    @InjectView(R.id.time1)
    TextView time1;
    @InjectView(R.id.time2)
    TextView time2;
    @InjectView(R.id.time3)
    TextView time3;
    @InjectView(R.id.time4)
    TextView time4;
    //    @InjectView(R.id.tv_return3)
//    TextView tv_return3;
//    @InjectView(R.id.sqtype)
//    TextView sqType;
    @InjectView(R.id.zgyear)
    TextView zgYear;
    @InjectView(R.id.nametype)
    TextView nameType;

    private String[] typePerson;  //选择人事类型
    private WheelDialog typeDialog;
    private String typeName;
    private String typeNames;
    private AddfjAdapter fjadapter;
    private String name;
    private AgeLimitWheelDialog typeDialogs;
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

            if (msg.what == Constant.WHEEL_DIALOGS_RESULT) {
                typeNames = typeDialogs.getmCurrentName();

                if (!TextUtils.isEmpty(typeNames))
                    zgYear.setText(typeNames);
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.activity_enterpersonapply);
        ButterKnife.inject(this);

        initview();
    }

    private void initview() {
        nameType.setText("人事类型");
        tvAddress.setVisibility(View.VISIBLE);
        tvAddress.setText("人事申请");
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
        zgYear.setOnClickListener(this);

    }

    private void allTypes(String typeName) {

        if (fjadapter == null)
            fjadapter = new AddfjAdapter(fileList, EnterPersonApplyActivity.this);
        fileList.clear();     //如果类型改变了就清空选中附件
        fjadapter.notifyDataSetChanged();

        if (typeName.equals("招聘申请")) {  // TODO: 2017/4/13   这里如果类型只能为整形的话 可能会出错
            llJobs.setVisibility(View.VISIBLE);
            llOthers.setVisibility(View.GONE);
            llOut.setVisibility(View.GONE);
            llLeave.setVisibility(View.GONE);
            llCadre.setVisibility(View.GONE);

            persontypeContent3.setText("");
            persontypeContent4.setText("");
            persontypeContent5.setText("");
            persontypeContent6.setText("");
            useContent2.setText("");
            tvFj2.setText("请选择(选填)");
            rvFjname2.setVisibility(View.GONE);
        } else if (typeName.equals("其他")) {
            llJobs.setVisibility(View.GONE);
            llOthers.setVisibility(View.VISIBLE);
            llOut.setVisibility(View.GONE);
            llLeave.setVisibility(View.GONE);
            llCadre.setVisibility(View.GONE);

            persontypeContent1.setText("");
            persontypeContent2.setText("");
            useContent1.setText("");
            tvFj1.setText("请选择(选填)");
            rvFjname1.setVisibility(View.GONE);
        } else if (typeName.equals("调派申请")) {
            llJobs.setVisibility(View.GONE);
            llOthers.setVisibility(View.GONE);
            llOut.setVisibility(View.VISIBLE);
            llLeave.setVisibility(View.GONE);
            llCadre.setVisibility(View.GONE);

            persontypeContent7.setText("");
            persontypeContent8.setText("");
            persontypeContent9.setText("");
            useContent3.setText("");
            tvFj3.setText("请选择(选填)");
            rvFjname3.setVisibility(View.GONE);
        } else if (typeName.equals("离职申请")) {
            llJobs.setVisibility(View.GONE);
            llOthers.setVisibility(View.GONE);
            llOut.setVisibility(View.GONE);
            llLeave.setVisibility(View.VISIBLE);
            llCadre.setVisibility(View.GONE);

            persontypeContent10.setText("");
            persontypeContent11.setText("");
            useContent4.setText("");
            tvFj4.setText("请选择(选填)");
            rvFjname4.setVisibility(View.GONE);
        } else if (typeName.equals("后备干部申请")) {
            llJobs.setVisibility(View.GONE);
            llOthers.setVisibility(View.GONE);
            llOut.setVisibility(View.GONE);
            llLeave.setVisibility(View.GONE);
            llCadre.setVisibility(View.VISIBLE);

            persontypeContent12.setText("");
            persontypeContent13.setText("");
            persontypeContent14.setText("");
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
                typePerson = EnterPersonApplyActivity.this.getResources().getStringArray(R.array.person_type);
                typeDialog = new WheelDialog(this, mTypeHandler, typePerson, typeName);
                typeDialog.builder().show();
                break;
            case R.id.zgyear:
//                typePerson = EnterPersonApplyActivity.this.getResources().getStringArray(R.array.zg_year);
                typeDialogs = new AgeLimitWheelDialog(this, mTypeHandler);
                typeDialogs.builder().show();
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
        }
    }

    private void judge() {
        if (TextUtils.isEmpty(typeName)) {
            ManagerUtils.showToast(this, "请选择调用类型");
            return;
        }
        switch (typeName) {
            case "其他":
                if (TextUtils.isEmpty(persontypeContent1.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入请假事由");
                    return;
                }
                if (TextUtils.isEmpty(persontypeContent2.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入请假原因");
                    return;
                }
                break;
            case "招聘申请":
                if (TextUtils.isEmpty(persontypeContent3.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入需求岗位");
                    return;
                }
                if (TextUtils.isEmpty(persontypeContent4.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入需求人数");
                    return;
                }
                if (TextUtils.isEmpty(persontypeContent5.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入现有人数");
                    return;
                }
                if (TextUtils.isEmpty(persontypeContent6.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入招聘原因");
                    return;
                }
                if (TextUtils.isEmpty(useContent2.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入职责要求");
                    return;
                }
                if (!time1.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择到岗日期");
                    return;
                }
                break;

            case "调派申请":
                if (TextUtils.isEmpty(persontypeContent7.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入当前岗位");
                    return;
                }
                if (TextUtils.isEmpty(persontypeContent8.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入调派岗位");
                    return;
                }
                if (TextUtils.isEmpty(persontypeContent9.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入调派原因");
                    return;
                }
                if (time2.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择支付日期");
                    return;
                }
//                if (tv_return3.getText().toString().contains("请选择")) {
//                    ManagerUtils.showToast(this, "请选择调配类型");
//                    return;
//                }
                break;
            case "离职申请":
                if (TextUtils.isEmpty(persontypeContent10.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入当前岗位");
                    return;
                }
                if (TextUtils.isEmpty(persontypeContent11.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入离职原因");
                    return;
                }
                break;
            case "后备干部申请":
                if (TextUtils.isEmpty(persontypeContent12.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入岗位职别");
                    return;
                }
                if (TextUtils.isEmpty(persontypeContent13.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入后备职别");
                    return;
                }
                if (TextUtils.isEmpty(persontypeContent14.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入申请原因");
                    return;
                }
                break;
        }
        new DialogUtil(EnterPersonApplyActivity.this, new DialogUtil.OnClickListenner() {
            @Override
            public void yesClick() {

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
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//                    newUri = Uri.parse("file:///" + CropUtils.getPath(this, data.getData()));
                    newUri = Uri.parse(CropUtils.getPath(this, data.getData()));
                    Log.e("小于25File", String.valueOf(newUri));
                    File file = new File(String.valueOf(newUri));
                    Log.e("ccc", file.getName());
                    if (file != null) {
                        fileList.add(file);
                    }
                } else {
                    newUri = data.getData();
                    File file = new File(String.valueOf(newUri));
                    if (file != null) {
                        fileList.add(file);
                    }
                    Log.e("大于25File", file.getName() + " == " + String.valueOf(newUri));
                }

                fjadapter = new AddfjAdapter(fileList, this);
                typesAdapter();   //给附件名称的recyclerview设置adapter    不同情况不同的显示隐藏

                break;
        }
    }

    private void typesAdapter() {
        switch (typeName) {

            case "其他":
                rvFjname1.setLayoutManager(new LinearLayoutManager(EnterPersonApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
            case "招聘申请":
                rvFjname2.setLayoutManager(new LinearLayoutManager(EnterPersonApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
            case "调派申请":
                rvFjname3.setLayoutManager(new LinearLayoutManager(EnterPersonApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
            case "离职申请":
                rvFjname4.setLayoutManager(new LinearLayoutManager(EnterPersonApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
            case "后备干部申请":
                rvFjname5.setLayoutManager(new LinearLayoutManager(EnterPersonApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
                            case "招聘申请":
                                change2();
                                break;
                            case "调派申请":
                                change3();
                                break;
                            case "离职申请":
                                change4();
                                break;
                            case "后备干部申请":
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

