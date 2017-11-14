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

//项目申请
public class EnterProjectApplyActivity extends AutoLayoutActivity implements View.OnClickListener {
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
    @InjectView(R.id.ll_project)
    LinearLayout llProject;
    @InjectView(R.id.ll_contract)
    LinearLayout llContract;
    @InjectView(R.id.ll_point)
    LinearLayout llPoint;
    @InjectView(R.id.ll_department)
    LinearLayout llDepartment;
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
    @InjectView(R.id.use_content6)
    EditText useContent6;
    @InjectView(R.id.use_content7)
    EditText useContent7;
    @InjectView(R.id.use_content8)
    EditText useContent8;
    @InjectView(R.id.use_content9)
    EditText useContent9;
    @InjectView(R.id.use_content10)
    EditText useContent10;
    @InjectView(R.id.projectype_content1)
    EditText projecttypeContent1;
    @InjectView(R.id.projectype_content2)
    EditText projecttypeContent2;
    @InjectView(R.id.projectype_content3)
    EditText projecttypeContent3;
    @InjectView(R.id.projectype_content4)
    EditText projecttypeContent4;
    @InjectView(R.id.projectype_content5)
    EditText projecttypeContent5;
    @InjectView(R.id.projectype_content6)
    EditText projecttypeContent6;
    @InjectView(R.id.projectype_content7)
    EditText projecttypeContent7;
    @InjectView(R.id.projectype_content8)
    EditText projecttypeContent8;
    @InjectView(R.id.need1)
    EditText need1;
    @InjectView(R.id.need2)
    EditText need2;
    @InjectView(R.id.time1)
    TextView time1;
    @InjectView(R.id.time2)
    TextView time2;
    @InjectView(R.id.time3)
    TextView time3;
    @InjectView(R.id.time4)
    TextView time4;
    @InjectView(R.id.contracttype)
    TextView contractType;
    @InjectView(R.id.jjcd)
    TextView jjCd;
    @InjectView(R.id.nametype)
    TextView nameType;

    private String[] typeProject;  //选择项目类型
    private String[] typeContract;  //选择合同类型
    private String[] typeJjCd;   //紧急程度
    private WheelDialog typeDialog;
    private WheelDialog typeDialog1;
    private WheelDialog typeDialog2;
    private String typeName;
    private String typeName1;
    private String typeName2;
    private String name;
    private AddfjAdapter fjadapter;
    private int type;

    /**
     * Dialog返回数据
     **/
    private Handler mTypeHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                typeName = typeDialog.getmCurrentName();
                allTypes(typeName);   //选择器中的不同类型的名字    || !TextUtils.isEmpty(typeName1) || !TextUtils.isEmpty(typeName2)
                if (!TextUtils.isEmpty(typeName))
                    tvGo1.setText(typeName);

            }
            return false;
        }
    });

    private Handler mContractHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                String typeName = typeDialog.getmCurrentName();

                if (!TextUtils.isEmpty(typeName))
                    contractType.setText(typeName);
            }
            return false;
        }
    });

    private Handler mJjCdHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                String typeName = typeDialog.getmCurrentName();
                if (!TextUtils.isEmpty(typeName))
                    jjCd.setText(typeName);
            }
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.activity_enterprojectapply);
        ButterKnife.inject(this);

        initview();
    }

    private void initview() {
        nameType.setText("工作类型");
        tvAddress.setVisibility(View.VISIBLE);
        tvAddress.setText("项目申请");
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
        contractType.setOnClickListener(this);
        jjCd.setOnClickListener(this);


    }

    private void allTypes(String typeName) {

        if (fjadapter == null)
            fjadapter = new AddfjAdapter(fileList, EnterProjectApplyActivity.this);
        fileList.clear();     //如果类型改变了就清空选中附件
        fjadapter.notifyDataSetChanged();


        if (typeName.equals("立项申请")) {  // TODO: 2017/4/13   这里如果类型只能为整形的话 可能会出错
            llProject.setVisibility(View.VISIBLE);
            llOthers.setVisibility(View.GONE);
            llContract.setVisibility(View.GONE);
            llPoint.setVisibility(View.GONE);
            llDepartment.setVisibility(View.GONE);

            useContent3.setText("");
            useContent4.setText("");
            useContent5.setText("");
            projecttypeContent1.setText("");
            need1.setText("");
            need2.setText("");
            time1.setText("请选择(必填)");
            tvFj2.setText("请选择(选填)");
            rvFjname2.setVisibility(View.GONE);
        } else if (typeName.equals("其他")) {
            llProject.setVisibility(View.GONE);
            llOthers.setVisibility(View.VISIBLE);
            llContract.setVisibility(View.GONE);
            llPoint.setVisibility(View.GONE);
            llDepartment.setVisibility(View.GONE);

            useContent1.setText("");
            useContent2.setText("");
            tvFj1.setText("请选择(选填)");
            rvFjname1.setVisibility(View.GONE);
        } else if (typeName.equals("合同审批")) {
            llProject.setVisibility(View.GONE);
            llOthers.setVisibility(View.GONE);
            llContract.setVisibility(View.VISIBLE);
            llPoint.setVisibility(View.GONE);
            llDepartment.setVisibility(View.GONE);

            projecttypeContent2.setText("");
            projecttypeContent3.setText("");
            projecttypeContent4.setText("");
            projecttypeContent5.setText("");
            useContent6.setText("");
            time2.setText("请选择(必填)");
            tvFj3.setText("请选择(选填)");
            rvFjname3.setVisibility(View.GONE);
        } else if (this.typeName.equals("工作指示")) {
            llProject.setVisibility(View.GONE);
            llOthers.setVisibility(View.GONE);
            llContract.setVisibility(View.GONE);
            llPoint.setVisibility(View.VISIBLE);
            llDepartment.setVisibility(View.GONE);

            useContent7.setText("");
            useContent8.setText("");
            time3.setText("请选择(必填)");
            tvFj4.setText("请选择(选填)");
            rvFjname4.setVisibility(View.GONE);
        } else if (this.typeName.equals("部门协作")) {
            llProject.setVisibility(View.GONE);
            llOthers.setVisibility(View.GONE);
            llContract.setVisibility(View.GONE);
            llPoint.setVisibility(View.GONE);
            llDepartment.setVisibility(View.VISIBLE);

            projecttypeContent6.setText("");
            projecttypeContent7.setText("");
            projecttypeContent8.setText("");
            useContent9.setText("");
            useContent10.setText("");
            time4.setText("请选择(必填)");
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
                typeProject = EnterProjectApplyActivity.this.getResources().getStringArray(R.array.project_type);
                typeDialog = new WheelDialog(this, mTypeHandler, typeProject, typeName);
                typeDialog.builder().show();
                break;
            case R.id.contracttype:
                typeContract = EnterProjectApplyActivity.this.getResources().getStringArray(R.array.contract_type);
                typeDialog = new WheelDialog(this, mContractHandler, typeContract, typeName);
                typeDialog.builder().show();
                break;
            case R.id.jjcd:
                typeJjCd = EnterProjectApplyActivity.this.getResources().getStringArray(R.array.jjcd_type);
                typeDialog = new WheelDialog(this, mJjCdHandler, typeJjCd, typeName);
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
        }
    }

    private void judge() {
        if (TextUtils.isEmpty(typeName)) {
            ManagerUtils.showToast(this, "请选择调用类型");
            return;
        }
        switch (typeName) {
            case "立项申请":
                if (TextUtils.isEmpty(projecttypeContent1.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入资料名称");
                    return;
                }
                if (TextUtils.isEmpty(useContent3.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入项目概述");
                    return;
                }

                if (TextUtils.isEmpty(useContent4.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入预期目标");
                    return;
                }

                if (TextUtils.isEmpty(need1.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入人员需求");
                    return;
                }

                if (TextUtils.isEmpty(need2.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入资金需求");
                    return;
                }

                if (!time1.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择期望启动日期");
                    return;
                }

                if (TextUtils.isEmpty(useContent5.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入使用事由");
                    return;
                }

                break;
            case "其他":
                if (TextUtils.isEmpty(useContent1.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入申请事由");
                    return;
                }
                if (TextUtils.isEmpty(useContent2.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入具体说明");
                    return;
                }
                break;
            case "合同审批":
                if (TextUtils.isEmpty(projecttypeContent2.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入合同编号");
                    return;
                }
//                请选择(必填)
                if (contractType.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择合同类型");
                    return;
                }
                if (!time2.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择签约日期");
                    return;
                }
                if (TextUtils.isEmpty(projecttypeContent3.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入我方负责人");
                    return;
                }
                if (TextUtils.isEmpty(projecttypeContent5.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入对方负责人");
                    return;
                }
                if (TextUtils.isEmpty(useContent6.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入合同条款");
                    return;
                }

                break;
            case "工作指示":
                if (jjCd.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择紧急程度");
                    return;
                }
                if (TextUtils.isEmpty(useContent7.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入请示事由");
                    return;
                }
                if (!time3.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择期望启动日期");
                    return;
                }
                if (TextUtils.isEmpty(useContent8.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入具体内容");
                    return;
                }
                break;
            case "部门协作":
                if (TextUtils.isEmpty(projecttypeContent6.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入协作事由");
                    return;
                }
                if (TextUtils.isEmpty(useContent9.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入预期目标");
                    return;
                }
                if (TextUtils.isEmpty(projecttypeContent7.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入发起方");
                    return;
                }
                if (TextUtils.isEmpty(projecttypeContent8.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入协作方");
                    return;
                }
                if (TextUtils.isEmpty(useContent10.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入协作事项");
                    return;
                }
                if (time4.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择完成日期");
                    return;
                }
                break;
        }


        new DialogUtil(EnterProjectApplyActivity.this, new DialogUtil.OnClickListenner() {
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
        return;
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
                rvFjname1.setLayoutManager(new LinearLayoutManager(EnterProjectApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
            case "立项申请":
                rvFjname2.setLayoutManager(new LinearLayoutManager(EnterProjectApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
            case "合同审批":
                rvFjname3.setLayoutManager(new LinearLayoutManager(EnterProjectApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
            case "工作指示":
                rvFjname4.setLayoutManager(new LinearLayoutManager(EnterProjectApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
            case "部门协作":
                rvFjname5.setLayoutManager(new LinearLayoutManager(EnterProjectApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
                            case "立项申请":
                                change2();
                                break;
                            case "合同审批":
                                change3();
                                break;
                            case "工作指示":
                                change4();
                                break;
                            case "部门协作":
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

