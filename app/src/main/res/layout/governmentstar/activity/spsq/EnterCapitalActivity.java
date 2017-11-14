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
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.lanwei.governmentstar.activity.spsq.utils.BankCardTextWatcher;
import com.lanwei.governmentstar.utils.BankInfo;
import com.lanwei.governmentstar.utils.Constant;
import com.lanwei.governmentstar.utils.CropUtils;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.ManagerUtils;
import com.lanwei.governmentstar.utils.MoneyUtil;
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

public class EnterCapitalActivity extends AutoLayoutActivity implements View.OnClickListener {
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
    @InjectView(R.id.ll_pay)
    LinearLayout llPay;
    @InjectView(R.id.ll_reserve)
    LinearLayout llReserve;
    @InjectView(R.id.ll_otherspay)
    LinearLayout llOtherspay;
    @InjectView(R.id.rv_fjname1)
    RecyclerView rvFjname1;
    @InjectView(R.id.rv_fjname2)
    RecyclerView rvFjname2;
    @InjectView(R.id.rv_fjname3)
    RecyclerView rvFjname3;
    @InjectView(R.id.tv_fj1)
    TextView tvFj1;
    @InjectView(R.id.tv_fj2)
    TextView tvFj2;
    @InjectView(R.id.tv_fj3)
    TextView tvFj3;
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
    @InjectView(R.id.capital_content1)
    EditText capitaltypeContent1;
    @InjectView(R.id.capital_content2)
    EditText capitaltypeContent2;
    @InjectView(R.id.capital_content3)
    EditText capitaltypeContent3;
    @InjectView(R.id.capital_content4)
    EditText capitaltypeContent4;
    @InjectView(R.id.capital_content5)
    EditText capitaltypeContent5;
    @InjectView(R.id.capital_content6)
    EditText capitaltypeContent6;
    @InjectView(R.id.use_content1)
    EditText useContent1;
    @InjectView(R.id.use_content2)
    EditText useContent2;
    @InjectView(R.id.use_content3)
    EditText useContent3;
    @InjectView(R.id.time1)
    TextView time1;
    @InjectView(R.id.tv_borrow1)
    TextView tvBorrow1;
    @InjectView(R.id.tv_return1)
    TextView tvReturn1;
    @InjectView(R.id.big_money)
    TextView bigMoney;
    @InjectView(R.id.big_money5)
    TextView bigMoney5;
    @InjectView(R.id.big_money6)
    TextView bigMoney6;
    @InjectView(R.id.paytype)
    TextView payType;
    @InjectView(R.id.borrowtype)
    TextView borrowType;
    @InjectView(R.id.bxtype)
    TextView bxType;
    @InjectView(R.id.nametype)
    TextView nameType;


    private String[] typeCapital;  //选择资金类型
    private WheelDialog typeDialog;
    private String typeName;
    private AddfjAdapter fjadapter;
    private String name;
    private String[] typePay;
    private String[] typeBorrow;


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
    /**
     * 用印申请 - 印章申请
     **/
    private Handler mPayHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                if (!TextUtils.isEmpty(typeDialog.getmCurrentName()))
                    payType.setText(typeDialog.getmCurrentName());
            }
            return false;
        }
    });

    private Handler mBorrowHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                if (!TextUtils.isEmpty(typeDialog.getmCurrentName()))
                    borrowType.setText(typeDialog.getmCurrentName());
            }
            return false;
        }
    });

    private Handler mBXHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                if (!TextUtils.isEmpty(typeDialog.getmCurrentName()))
                    bxType.setText(typeDialog.getmCurrentName());
            }
            return false;
        }
    });
    private String[] typeBx;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.activity_entercapitalapply);
        ButterKnife.inject(this);

        initview();
    }

    private void initview() {
        nameType.setText("用途类型");
        tvAddress.setVisibility(View.VISIBLE);
        tvAddress.setText("资金申请");
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
        looking1.setOnClickListener(this);
        inlooking1.setOnClickListener(this);
        looking2.setOnClickListener(this);
        inlooking2.setOnClickListener(this);
        looking3.setOnClickListener(this);
        inlooking3.setOnClickListener(this);
        time1.setOnClickListener(this);
        tvBorrow1.setOnClickListener(this);
        tvReturn1.setOnClickListener(this);
        payType.setOnClickListener(this);
        borrowType.setOnClickListener(this);
        bxType.setOnClickListener(this);

        BankCardTextWatcher.bind(capitaltypeContent4);  //添加EditText的输入监听
        capitaltypeContent4.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
//                // 在输入数据时监听
                String huoqucc = capitaltypeContent4.getText().toString();
                String s1 = huoqucc.replaceAll(" ", "");
                String name = BankInfo.getNameOfBank(s1);// 获取银行卡的信息
                if (name.equals("无法识别银行卡所属银行名称")){
                    capitaltypeContent3.setEnabled(true);
                } else {
                    capitaltypeContent3.setEnabled(false);
                    capitaltypeContent3.setText(name);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // 在输入数据前监听
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 在输入数据后监听
            }
        });


        capitaltypeContent1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        capitaltypeContent5.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        capitaltypeContent6.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        bigMoney1();
        bigMoney5();
        bigMoney6();


    }

    private void bigMoney1() {
        capitaltypeContent1.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        capitaltypeContent1.setText(s);
                        capitaltypeContent1.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    capitaltypeContent1.setText(s);
                    capitaltypeContent1.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        capitaltypeContent1.setText(s.subSequence(0, 1));
                        capitaltypeContent1.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = capitaltypeContent1.getText().toString();
                if (s.equals("") && s.isEmpty()) {
                    bigMoney.setText("零");
                }
                Log.d("111", MoneyUtil.toChinese(s));
                bigMoney.setText(MoneyUtil.toChinese(s));

            }
        });
    }

    private void bigMoney5() {
        capitaltypeContent5.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        capitaltypeContent5.setText(s);
                        capitaltypeContent5.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    capitaltypeContent5.setText(s);
                    capitaltypeContent5.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        capitaltypeContent5.setText(s.subSequence(0, 1));
                        capitaltypeContent5.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = capitaltypeContent5.getText().toString();
                if (s.equals("") && s.isEmpty()) {
                    bigMoney5.setText("零");
                }
                Log.d("111", MoneyUtil.toChinese(s));
                bigMoney5.setText(MoneyUtil.toChinese(s));

            }
        });
    }

    private void bigMoney6() {
        capitaltypeContent6.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        capitaltypeContent6.setText(s);
                        capitaltypeContent6.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    capitaltypeContent6.setText(s);
                    capitaltypeContent6.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        capitaltypeContent6.setText(s.subSequence(0, 1));
                        capitaltypeContent6.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = capitaltypeContent6.getText().toString();
                if (s.equals("") && s.isEmpty()) {
                    bigMoney6.setText("零");
                }
                Log.d("111", MoneyUtil.toChinese(s));
                bigMoney6.setText(MoneyUtil.toChinese(s));

            }
        });
    }

    private void allTypes(String typeName) {
        if (fjadapter == null)
            fjadapter = new AddfjAdapter(fileList, EnterCapitalActivity.this);
        fileList.clear();     //如果类型改变了就清空选中附件
        fjadapter.notifyDataSetChanged();


        if (typeName.equals("付款申请")) {  // TODO: 2017/4/13   这里如果类型只能为整形的话 可能会出错
            llPay.setVisibility(View.VISIBLE);
            llReserve.setVisibility(View.GONE);
            llOtherspay.setVisibility(View.GONE);

            useContent1.setText("");
            capitaltypeContent1.setText("");
            capitaltypeContent2.setText("");
            capitaltypeContent3.setText("");
            capitaltypeContent4.setText("");
            bigMoney.setText("零");
            rvFjname1.setVisibility(View.GONE);
            tvFj1.setText("请选择(选填)");

        } else if (typeName.equals("备用金申请")) {
            llPay.setVisibility(View.GONE);
            llReserve.setVisibility(View.VISIBLE);
            llOtherspay.setVisibility(View.GONE);

            useContent2.setText("");
            capitaltypeContent5.setText("");
            bigMoney5.setText("零");
            rvFjname2.setVisibility(View.GONE);
            tvFj2.setText("请选择(选填)");

        } else if (typeName.equals("报销申请")) {
            llPay.setVisibility(View.GONE);
            llReserve.setVisibility(View.GONE);
            llOtherspay.setVisibility(View.VISIBLE);

            useContent3.setText("");
            capitaltypeContent6.setText("");
            bigMoney6.setText("零");
            rvFjname3.setVisibility(View.GONE);
            tvFj3.setText("请选择(选填)");

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.tv_go1:
                typeCapital = EnterCapitalActivity.this.getResources().getStringArray(R.array.capital_type);
                typeDialog = new WheelDialog(this, mTypeHandler, typeCapital, typeName);
                typeDialog.builder().show();
                break;
            case R.id.paytype:
                typePay = EnterCapitalActivity.this.getResources().getStringArray(R.array.pay_type);
                typeDialog = new WheelDialog(this, mPayHandler, typePay, typeName);
                typeDialog.builder().show();
                break;
            case R.id.borrowtype:
                typeBorrow = EnterCapitalActivity.this.getResources().getStringArray(R.array.borrow_type);
                typeDialog = new WheelDialog(this, mBorrowHandler, typeBorrow, typeName);
                typeDialog.builder().show();
                break;
            case R.id.bxtype:
                typeBx = EnterCapitalActivity.this.getResources().getStringArray(R.array.bx_type);
                typeDialog = new WheelDialog(this, mBXHandler, typeBx, typeName);
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
            case R.id.tv_fj1:
            case R.id.tv_fj2:
            case R.id.tv_fj3:
                opFileManager();
                break;
            case R.id.time1:
                //时间选择器
                name = "t1";
                time();
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
            case "付款申请":
                if (TextUtils.isEmpty(capitaltypeContent1.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入付款金额");
                    return;
                }
                if (TextUtils.isEmpty(capitaltypeContent3.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入开户行");
                    return;
                }
                if (TextUtils.isEmpty(capitaltypeContent2.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入支付对象");
                    return;
                }
                if (TextUtils.isEmpty(capitaltypeContent4.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入银行账户");
                    return;
                }
                if (!time1.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择支付日期");
                    return;
                }
                if (borrowType.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择借用类型");
                    return;
                }
                if (payType.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择付款方式");
                    return;
                }
                break;

            case "备用金申请":
                if (TextUtils.isEmpty(capitaltypeContent5.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入申请金额");
                    return;
                }
                if (TextUtils.isEmpty(useContent2.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入用途事由");
                    return;
                }
                if (tvBorrow1.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择借资日期");
                    return;
                }
                if (tvReturn1.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择归还日期");
                    return;
                }
                break;
            case "报销申请":
                if (TextUtils.isEmpty(capitaltypeContent6.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入报销金额");
                    return;
                }
                if (TextUtils.isEmpty(useContent3.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入费用明细");
                    return;
                }
                if (bxType.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择报销类别");
                    return;
                }
                break;
        }


        new DialogUtil(EnterCapitalActivity.this, new DialogUtil.OnClickListenner() {
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
                    case "b1":
                        tvBorrow1.setText(" " + format);
                        break;
                    case "r1":
                        tvReturn1.setText(" " + format);
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
            case "付款申请":
                rvFjname1.setLayoutManager(new LinearLayoutManager(EnterCapitalActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
            case "备用金申请":
                rvFjname2.setLayoutManager(new LinearLayoutManager(EnterCapitalActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
            case "报销申请":
                rvFjname3.setLayoutManager(new LinearLayoutManager(EnterCapitalActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
                            case "付款申请":
                                change();
                                break;
                            case "备用金申请":
                                change2();
                                break;
                            case "报销申请":
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

        }
    }

}

