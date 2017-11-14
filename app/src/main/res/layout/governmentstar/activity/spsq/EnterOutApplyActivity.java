package com.lanwei.governmentstar.activity.spsq;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.spsq.adapter.PhotoAdapter;
import com.lanwei.governmentstar.activity.spsq.event.RecyclerItemClickListener;
import com.lanwei.governmentstar.activity.spsq.fragment.ImagePagerFragment;
import com.lanwei.governmentstar.activity.spsq.view.PhotoPicker;
import com.lanwei.governmentstar.activity.zwyx.ZwyxTreeActivity;
import com.lanwei.governmentstar.activity.zyx.application.GovApplication;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.Constant;
import com.lanwei.governmentstar.utils.CropUtils;
import com.lanwei.governmentstar.utils.DialogPermission;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.FileUtil;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.ManagerUtils;
import com.lanwei.governmentstar.utils.MoneyUtil;
import com.lanwei.governmentstar.utils.PermissionUtil;
import com.lanwei.governmentstar.utils.PhotoUtils;
import com.lanwei.governmentstar.utils.PictureUtil;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.utils.SharedPreferenceMark;
import com.lanwei.governmentstar.utils.SharedPreferencesUtil;
import com.lanwei.governmentstar.view.DialogCarmera;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.lanwei.governmentstar.view.wheel.WheelDialog;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/8/2/002.
 */

public class EnterOutApplyActivity extends AutoLayoutActivity implements View.OnClickListener, DialogUtil.OnClickListenner {
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
    @InjectView(R.id.ll_other)
    LinearLayout llOther;
    @InjectView(R.id.ll_car)
    LinearLayout llCar;
    @InjectView(R.id.ll_public)
    LinearLayout llPublic;
    @InjectView(R.id.ll_reimburse)
    LinearLayout llReimburse;
    @InjectView(R.id.use_content1)
    EditText useContent1;
    @InjectView(R.id.use_content2)
    EditText useContent2;
    @InjectView(R.id.use_content3)
    EditText useContent3;
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
    @InjectView(R.id.out_content1)
    EditText outContent1;
    @InjectView(R.id.out_content2)
    EditText outContent2;
    @InjectView(R.id.out_content3)
    EditText outContent3;
    @InjectView(R.id.out_content4)
    EditText outContent4;
    @InjectView(R.id.out_content5)
    EditText outContent5;
    @InjectView(R.id.out_content6)
    EditText outContent6;
    @InjectView(R.id.tv_go2)
    TextView tvGo2;
    @InjectView(R.id.tv_go3)
    TextView tvGo3;
    @InjectView(R.id.time1)
    TextView time1;
    @InjectView(R.id.time2)
    TextView time2;
    @InjectView(R.id.time3)
    TextView time3;
    @InjectView(R.id.time4)
    TextView time4;
    @InjectView(R.id.time5)
    TextView time5;
    @InjectView(R.id.time6)
    TextView time6;
    @InjectView(R.id.time7)
    TextView time7;
    @InjectView(R.id.time8)
    TextView time8;
    @InjectView(R.id.tv_go2)
    TextView tv_go2;
    @InjectView(R.id.tv_go3)
    TextView tv_go3;
    @InjectView(R.id.time111)
    TextView time111;
    @InjectView(R.id.iv_pic)
    ImageView ivPic;
    @InjectView(R.id.hasSelect_content)
    TextView hasSelectContent;
    @InjectView(R.id.carnumber)
    EditText carNumber;
    @InjectView(R.id.type_go)
    TextView typeGo;
    @InjectView(R.id.click)
    TextView click;
    @InjectView(R.id.click1)
    TextView click1;
    @InjectView(R.id.clickmoney)
    LinearLayout clickmoney;
    @InjectView(R.id.big_money)
    TextView bigMoney;
    @InjectView(R.id.nametype)
    TextView nameType;

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
    @InjectView(R.id.inbox_layout_re)
    RelativeLayout inbox_layout_re;
    @InjectView(R.id.theme_content)
    EditText theme_content;


    private String[] typeOther;  //选择物品类型
    private WheelDialog typeDialog;
    private String typeName;
    private String name;
    private AddfjAdapter fjadapter;
    private int type;
    private PhotoAdapter photoAdapter;
    private RecyclerView recyclerView;
    protected static Uri tempUri;
    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private static final int REQUEST_CODE_CROUP_PHOTO = 3;
    private String type_main = "外出申请";
    private String childType = "";
    private Intent intent;
    private String zhusong = "";
    private String tong = "";
    private String zhusong_temp = "";
    private String caosong = "";
    private String caosong_temp = "";
    private PopupWindowUtil popupWindowUtil;
    private String txrNum ="0";
    private Logging_Success bean;

    /**
     * Dialog返回数据
     **/
    private Handler mTypeHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == Constant.WHEEL_DIALOG_RESULT) {
                if (!TextUtils.isEmpty(typeDialog.getmCurrentName()))
                    switch (type) {
                        case 0:
                            typeName = typeDialog.getmCurrentName();
                            tvGo1.setText(typeName);
                            allTypes(typeName);
                            break;
                        case 1:
                            typeGo.setText(typeDialog.getmCurrentName());
                            break;
                    }
            }
            return false;
        }
    });

    private AddfjAdapter xgwj_adapter;
    private AddfjAdapter tjfj_adapter;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private File file;
    private File cover;
    private Uri uri;
    private String imagePath;
    private ImagePagerFragment pagerFragment;
    private double i = 0.00;
    private double i1 = 0.00;
    private double i2 = 0.00;
    private double i3 = 0.00;
    private double i4 = 0.00;
    private DecimalFormat df;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.activity_enteroutapply);
        ButterKnife.inject(this);

        initview();
        // 获取bean;
        String defString = PreferencesManager.getInstance(EnterOutApplyActivity.this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);


    }

    private void initview() {
        nameType.setText("外出类型");
        tvAddress.setVisibility(View.VISIBLE);
        tvAddress.setText("外出申请");
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
        time5.setOnClickListener(this);
        time6.setOnClickListener(this);
        time7.setOnClickListener(this);
        time8.setOnClickListener(this);
        tvGo2.setOnClickListener(this);
        tvGo3.setOnClickListener(this);
        selectText();
        typeGo.setOnClickListener(this);
        click.setOnClickListener(this);
        click1.setOnClickListener(this);
        ivPic.setOnClickListener(this);
        inbox_layout_re.setOnClickListener(this);

        df = new DecimalFormat("#.00");

        outContent5.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        money5();
        thingsContent13.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        money13();
        thingsContent14.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        money14();
        thingsContent15.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        money15();
        thingsContent16.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        money16();
        thingsContent17.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        money17();


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(EnterOutApplyActivity.this, selectedPhotos);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(6)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(selectedPhotos)
                                    .start(EnterOutApplyActivity.this);

                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(EnterOutApplyActivity.this);
                        }
                    }
                }));

    }


    String t1;

    private void selectText() {
        time7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!(time7.equals("")) && !(time7.equals("请选择(必填)"))) {
                    String s = time7.getText().toString();
                    t1 = s.replace("-", "/");
                    String t2 = String.valueOf(SharedPreferencesUtil.getData(EnterOutApplyActivity.this, "t2", ""));
                    if (t2.equals("") && t2.isEmpty()) {
                        hasSelectContent.setText(t1);
                    } else {
                        hasSelectContent.setText(t1 + " - " + t2);
                    }
                }
            }
        });
        time8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Toast.makeText(EnterOutApplyActivity.this, "开始时间不能晚于结束时间1", Toast.LENGTH_SHORT);
                if (!time8.equals("")) {
                    hasSelectContent.setText(t1);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Toast.makeText(EnterOutApplyActivity.this, "开始时间不能晚于结束时间221", Toast.LENGTH_SHORT);
            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!(time8.equals("")) && !(time8.equals("请选择(必填)"))) {
                    String s = time8.getText().toString();
                    String t2 = s.replace("-", "/");
                    SharedPreferencesUtil.saveData(EnterOutApplyActivity.this, "t2", t2);
                    String s1 = hasSelectContent.getText().toString();
                    hasSelectContent.setText(s1 + " -" + t2);
                }
            }
        });

    }


    private void allTypes(String typeName) {

        if (fjadapter == null)
            fjadapter = new AddfjAdapter(fileList, EnterOutApplyActivity.this);
        fileList.clear();     //如果类型改变了就清空选中附件
        fjadapter.notifyDataSetChanged();

        if (xgwj_adapter == null)
            xgwj_adapter = new AddfjAdapter(xgwjList, EnterOutApplyActivity.this);
        xgwjList.clear();     //如果类型改变了就清空选中附件
        xgwj_adapter.notifyDataSetChanged();

        if (tjfj_adapter == null)
            tjfj_adapter = new AddfjAdapter(tjfjList, EnterOutApplyActivity.this);
        tjfjList.clear();     //如果类型改变了就清空选中附件
        tjfj_adapter.notifyDataSetChanged();

        if (photoAdapter == null)
            photoAdapter = new PhotoAdapter(EnterOutApplyActivity.this, selectedPhotos);
        selectedPhotos.clear();
        photoAdapter.notifyDataSetChanged();

        if (typeName.equals("其他")) {  // TODO: 2017/4/13   这里如果类型只能为整形的话 可能会出错
            llOther.setVisibility(View.VISIBLE);
            llCar.setVisibility(View.GONE);
            llPublic.setVisibility(View.GONE);
            llReimburse.setVisibility(View.GONE);

            useContent1.setText("");
            tvFj1.setText("请选择(选填)");
            rvFjname1.setVisibility(View.GONE);
        } else if (typeName.equals("用车申请")) {
            llOther.setVisibility(View.GONE);
            llCar.setVisibility(View.VISIBLE);
            llPublic.setVisibility(View.GONE);
            llReimburse.setVisibility(View.GONE);

            useContent2.setText("");
            tvFj2.setText("请选择(选填)");
            rvFjname2.setVisibility(View.GONE);
        } else if (typeName.equals("公务出行申请")) {
            llOther.setVisibility(View.GONE);
            llCar.setVisibility(View.GONE);
            llPublic.setVisibility(View.VISIBLE);
            llReimburse.setVisibility(View.GONE);

            useContent3.setText("");
            tvFj3.setText("请选择(选填)");
            rvFjname3.setVisibility(View.GONE);

            typeGo.setText("请选择(必填)");
        } else if (typeName.equals("出差报销申请")) {
            llOther.setVisibility(View.GONE);
            llCar.setVisibility(View.GONE);
            llPublic.setVisibility(View.GONE);
            llReimburse.setVisibility(View.VISIBLE);

            SharedPreferencesUtil.saveData(EnterOutApplyActivity.this, "t2", "");
            time7.setText("请选择(必填)");
            time8.setText("请选择(必填)");
            hasSelectContent.setText("开始日期 - 结束日期");
            outContent5.setHint("请输入差旅费用(必填)");
            bigMoney.setText("零");
            recyclerView.setVisibility(View.GONE);
            tvFj4.setText("请选择(选填)");
            rvFjname4.setVisibility(View.GONE);
            tvFj5.setText("请选择(选填)");
            rvFjname5.setVisibility(View.GONE);
        }
    }

    public void check() {
        if (typeGo.getText().toString().equals("请选择(必填)") || !typeGo.getText().toString().contains("-")) {
            ManagerUtils.showToast(this, "请选择");
            return;
        }
        if (outContent5.getText().toString().equals("请输入差旅费用(必填)") || !outContent5.getText().toString().contains("-")) {
            ManagerUtils.showToast(this, "请输入差旅费用");
            return;
        }
        if (time8.getText().toString().equals("请选择(必填)") || !time8.getText().toString().contains("-")) {
            ManagerUtils.showToast(this, "请选择");
            return;
        }
        if (time8.getText().toString().equals("请选择(必填)") || !time8.getText().toString().contains("-")) {
            ManagerUtils.showToast(this, "请选择");
            return;
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
            case R.id.tv_go2:
                if(bean.getData().getAccountDeptId().equals("0155")){
                    intent= new Intent(EnterOutApplyActivity.this,ChooseReceivers_Spsq_Activity.class);
                }else{
                    intent= new Intent(EnterOutApplyActivity.this,Chooses_ReceiversSPAQ_Activity.class);
                }
                intent.putExtra("type","zhu");
                intent.putExtra("zhusong",zhusong);
                intent.putExtra("caosong",caosong);
                intent.putExtra("tong",tong);
                startActivityForResult(intent,20);
                break;

            case R.id.tv_go3:
                if(bean.getData().getAccountDeptId().equals("0155")){
                    intent= new Intent(EnterOutApplyActivity.this,ChooseReceivers_Spsq_Activity.class);
                }else{
                    intent= new Intent(EnterOutApplyActivity.this,Chooses_ReceiversSPAQ_Activity.class);
                }
                intent.putExtra("type","cao");
                intent.putExtra("zhusong",zhusong);
                intent.putExtra("caosong",caosong);
                intent.putExtra("tong",tong);
                startActivityForResult(intent,20);
                break;
            case R.id.tv_go1:
                type = 0;
                typeOther = EnterOutApplyActivity.this.getResources().getStringArray(R.array.out_type2);
                typeDialog = new WheelDialog(this, mTypeHandler, typeOther, typeName);
                typeDialog.builder().show();
                break;
            case R.id.type_go:
                type = 1;
                typeOther = EnterOutApplyActivity.this.getResources().getStringArray(R.array.out_gotype);
                typeDialog = new WheelDialog(this, mTypeHandler, typeOther, typeName);
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
                opFileManager();
                break;
            case R.id.tv_fj4:
                name = "fj1";
                opFileManager();
                break;

            case R.id.tv_fj5:
                name = "fj2";
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

            case R.id.inbox_layout_re:
                if(bean.getData().getAccountDeptId().equals("0155")){
                    intent= new Intent(EnterOutApplyActivity.this,ChooseReceivers_Spsq_Activity.class);
                }else{
                    intent= new Intent(EnterOutApplyActivity.this,Chooses_ReceiversSPAQ_Activity.class);
                }
                intent.putExtra("type","tong");
                intent.putExtra("zhusong",zhusong);
                intent.putExtra("caosong",caosong);
                intent.putExtra("tong",tong);
                startActivityForResult(intent,20);

                break;
            case R.id.time4:
                //时间选择器
                name = "t4";
                time();
                break;
            case R.id.time5:
                //时间选择器
                name = "t5";
                time();
                break;
            case R.id.time6:
                //时间选择器
                name = "t6";
                time();
                break;
            case R.id.time7:
                //时间选择器
                name = "t7";
                time();
                break;
            case R.id.time8:
                //时间选择器
                name = "t8";
                time();
                break;
            case R.id.click:
                clickmoney.setVisibility(View.VISIBLE);
                click.setVisibility(View.GONE);
                click1.setVisibility(View.VISIBLE);
                rvFjname3.setVisibility(View.VISIBLE);
                break;
            case R.id.click1:
                clickmoney.setVisibility(View.GONE);
                click1.setVisibility(View.GONE);
                click.setVisibility(View.VISIBLE);
                rvFjname3.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_pic:
                final DialogCarmera dialogCarmera = new DialogCarmera(this);
                dialogCarmera.builder().setOnClick(new DialogCarmera.OnSheetItemClickListener() {
                    @Override
                    public void onTakePhoto() {
                        dialogCarmera.dismiss();
                        initHeadPortrait();

                        if (PermissionUtil.hasCameraPermission(EnterOutApplyActivity.this)) {
                            openCamera();
                        }
//                        Toast.makeText(getApplicationContext(), "相机", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onAlbum() {
                        dialogCarmera.dismiss();

                        PhotoPicker.builder()
                                .setShowCamera(false)
                                .setPhotoCount(6)
                                .setGridColumnCount(3)
                                .start(EnterOutApplyActivity.this);
                    }

                });
                dialogCarmera.show();
                break;
        }
    }

    /**
     * 准备上传头像的文件
     */
    private void initHeadPortrait() {
        file = new File(FileUtil.getCachePath(this), "gov_icon.jpg");
        //工具类
//        file = new FileStorage().createIconFile();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            tempUri = Uri.fromFile(file);
//            tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg"));
        } else {
            //通过FileProvider创建一个content类型的Uri(android 7.0需要这样的方法跨应用访问)
            tempUri = FileProvider.getUriForFile(GovApplication.getInstance(), "com.lanwei.governmentstar", file);//通过FileProvider创建一个content类型的Uri，进行封装

        }
    }

    /**
     * 打开相机
     */
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);  //设置Action为拍照
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);   //将拍取的照片保存到指定URI
        startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO);  //启动拍照
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case PermissionUtil.REQUEST_SHOWCAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    openCamera();

                } else {
                    if (!SharedPreferenceMark.getHasShowCamera()) {
                        SharedPreferenceMark.setHasShowCamera(true);
                        new DialogPermission(this, "关闭摄像头权限影响扫描功能");

                    } else {
                        Toast.makeText(this, "未获取摄像头权限", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
                    case "t4":
                        time4.setText(" " + format);
                        break;
                    case "t5":
                        time5.setText(" " + format);
                        break;
                    case "t6":
                        time6.setText(" " + format);
                    case "t7":
                        time7.setText(" " + format);
                        break;
                    case "t8":
                        time8.setText(" " + format);
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

    private void judge() {
        if (type == 0)
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
                if (!time1.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择开始日期");
                    return;
                }

                if (!time2.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择返回日期");
                    return;
                }
                if (TextUtils.isEmpty(useContent1.getText().toString())) {
                    ManagerUtils.showToast(this, "请填写申请事由");
                    return;
                }

                break;
            case "用车申请":
                if (!time3.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择出行日期");
                    return;
                }

                if (!time4.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择返回日期");
                    return;
                }

                if (TextUtils.isEmpty(outContent1.getText().toString())) {
                    ManagerUtils.showToast(this, "请填写出发地点");
                    return;
                }
                if (TextUtils.isEmpty(useContent2.getText().toString())) {
                    ManagerUtils.showToast(this, "请填写外出事由");
                    return;
                }
                break;
            case "公务出行申请":
                if (!time5.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择出行日期");
                    return;
                }

                if (!time6.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择返回日期");
                    return;
                }

                if (typeGo.getText().toString().contains("请选择")) {
                    ManagerUtils.showToast(this, "请选择出行方式");
                    return;
                }
                if (TextUtils.isEmpty(useContent3.getText().toString())) {
                    ManagerUtils.showToast(this, "请填写公务事由");
                    return;
                }
                if (TextUtils.isEmpty(outContent3.getText().toString())) {
                    ManagerUtils.showToast(this, "请填写出行地点");
                    return;
                }
                break;
            case "出差报销申请":
                if (!time7.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择开始日期");
                    return;
                }
                if (!time8.getText().toString().contains("-")) {
                    ManagerUtils.showToast(this, "请选择结束日期");
                    return;
                }
                if (selectedPhotos.size() <= 0) {
                    ManagerUtils.showToast(this, "请添加相关票据");
                    return;
                }
                if (TextUtils.isEmpty(outContent6.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入出差地点");
                    return;
                }
                if (TextUtils.isEmpty(outContent5.getText().toString())) {
                    ManagerUtils.showToast(this, "请输入差旅费用");
                    return;
                }
                break;
        }
        new DialogUtil(EnterOutApplyActivity.this, this).showConfirm("提交审批", "您确定要提交该审批吗？", "确定", "取消");
        return;

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
    ArrayList<File> xgwjList = new ArrayList<>();
    ArrayList<File> tjfjList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (resultCode) {
            case Activity.RESULT_OK:

                switch (requestCode) {
                    case PhotoPicker.REQUEST_CODE:
                    case PhotoPreview.REQUEST_CODE:
                        List<String> photos = null;
                        if (data != null) {
                            photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                        }
                        selectedPhotos.clear();


                        if (photos != null) {
                            if (!selectedPhotos.contains(photos))
                                selectedPhotos.addAll(photos);
                        }

                        if (selectedPhotos.size() <= 0 || selectedPhotos.isEmpty()) {
                            ivPic.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                        } else {
                            ivPic.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                        photoAdapter.notifyDataSetChanged();

                        break;
                    case FILE_SELECT_CODE:
                        Uri newUri;
                        if (data == null) {
                            return;
                        }

                        try {
                            newUri = Uri.parse(CropUtils.getPath(this, data.getData()));
                            Log.e("小于25File", String.valueOf(newUri));
                            File file2 = new File(String.valueOf(newUri));
                            Log.e("ccc", file2.getName());
                            if (file2 != null) {
                                fileList.add(file2);
                            }

                            if (typeName.equals("出差报销申请")) {
                                if (name.equals("fj1")) {
                                    xgwjList.add(file2);
                                } else if (name.equals("fj2")) {
                                    tjfjList.add(file2);
                                }
                            }
                            typesAdapter();   //给附件名称的recyclerview设置adapter    不同情况不同的显示隐藏
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(this, "请通过其他方式选择该附件", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case REQUEST_CODE_TAKE_PHOTO:
                        startPhotoZoom(tempUri);
                        break;
                    case REQUEST_CODE_CROUP_PHOTO:
                        compressAndUploadAvatar(file.getPath());
                        break;

        }
                break;
            case 520:

                zhusong = data.getStringExtra("zhusong");
                caosong = data.getStringExtra("caosong");
                tong = data.getStringExtra("tong");
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
                if(!tong.equals("")){

                    if(!tong.equals("")){
                        String[] zhusong_array = tong.split(",");
                        // 数组转为Arraylist
                        ArrayList list_zhu = new ArrayList<>(Arrays.asList(zhusong_array));
                        txrNum = list_zhu.size()+"";
                        time111.setText("已选择"+txrNum+"人");
                    }


                }else{
                    time111.setText("请选择(选填)");
                }


                break;
    }}

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra("crop", "true");// crop=true 有这句才能出来最后的裁剪页面.
        intent.putExtra("aspectX", 1);// 这两项为裁剪框的比例.
        intent.putExtra("aspectY", 1);// x:y=1:1
        intent.putExtra("output", Uri.fromFile(file));
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        startActivityForResult(intent, REQUEST_CODE_CROUP_PHOTO);
    }

    private void compressAndUploadAvatar(String fileSrc) {
        String compressPath = PictureUtil.compressPicture(fileSrc, EnterOutApplyActivity.this);  //压缩图片
        cover = new File(compressPath);
//        final File cover = FileUtil.getSmallBitmap(this, path);
        //加载本地图片
        uri = Uri.fromFile(cover);
        Bitmap photo = BitmapFactory.decodeFile(cover.getPath());

        if (photo != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                //获取图片路径
                imagePath = PhotoUtils.savePhoto(photo, Environment
                        .getExternalStorageDirectory().getAbsolutePath(), String
                        .valueOf(System.currentTimeMillis()));
            } else {
                imagePath = fileSrc;
            }

            if (imagePath != null) {

                if (!selectedPhotos.contains(imagePath)) {

                    selectedPhotos.add(imagePath);
                }
            }

            if (selectedPhotos.size() <= 0 || selectedPhotos.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                ivPic.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                ivPic.setVisibility(View.GONE);
            }

        }
    }


    private void typesAdapter() {
        switch (typeName) {
            case "其他":
                fjadapter = new AddfjAdapter(fileList, this);
                rvFjname1.setLayoutManager(new LinearLayoutManager(EnterOutApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
            case "用车申请":
                fjadapter = new AddfjAdapter(fileList, this);
                rvFjname2.setLayoutManager(new LinearLayoutManager(EnterOutApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
            case "公务出行申请":
                fjadapter = new AddfjAdapter(fileList, this);
                rvFjname3.setLayoutManager(new LinearLayoutManager(EnterOutApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
            case "出差报销申请":
                switch (name) {
                    case "fj1":
                        xgwj_remove();
                        break;
                    case "fj2":
                        tjfj_remove();
                        break;
                }
                break;
        }
    }

    private void tjfj_remove() {
        tjfj_adapter = new AddfjAdapter(tjfjList, EnterOutApplyActivity.this);
        fjadapter.notifyDataSetChanged();
        rvFjname5.setLayoutManager(new LinearLayoutManager(EnterOutApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
        rvFjname5.setAdapter(tjfj_adapter);
        tjfj_adapter.notifyDataSetChanged();

        if (tjfjList != null || tjfjList.size() > 0) {
            tvFj5.setText("        ");  //将添加附件选填文字设置为空格
            rvFjname5.setVisibility(View.VISIBLE);
            if (tjfjList.size() > 3) {
                looking5.setVisibility(View.VISIBLE);
                inlooking5.setVisibility(View.GONE);
                inlookLine5.setVisibility(View.GONE);
                rvFjname5.setVisibility(View.GONE);
            }
        }
        tjfj_adapter.setOnRemoveItem(new OnRemoveItemListner() {
            @Override
            public void onRemove(int position) {
                if (tjfjList.size() >= 1) {
                    tjfjList.remove(position);  //将所对应的附件移除
                }
                tjfj_adapter.notifyDataSetChanged();
                if (tjfjList != null && tjfjList.size() > 0) {
                    tvFj5.setText("    ");   //将添加附件选填文字设置为空格
                    rvFjname5.setVisibility(View.VISIBLE);
                    if (tjfjList.size() > 3) {  //如果收件人的集合大于三
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
                if (tjfjList.size() <= 0) {
                    rvFjname5.setVisibility(View.GONE);
                    tvFj5.setText("请选择(选填)");
                }
            }
        });
    }

    private void xgwj_remove() {
        xgwj_adapter = new AddfjAdapter(xgwjList, EnterOutApplyActivity.this);
        rvFjname4.setLayoutManager(new LinearLayoutManager(EnterOutApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
        rvFjname4.setAdapter(xgwj_adapter);
        xgwj_adapter.notifyDataSetChanged();

        if (xgwjList != null || xgwjList.size() > 0) {
            tvFj4.setText("        ");  //将添加附件选填文字设置为空格
            rvFjname4.setVisibility(View.VISIBLE);
            if (xgwjList.size() > 3) {
                looking4.setVisibility(View.VISIBLE);
                inlooking4.setVisibility(View.GONE);
                inlookLine4.setVisibility(View.GONE);
                rvFjname4.setVisibility(View.GONE);
            }
        }

        xgwj_adapter.setOnRemoveItem(new OnRemoveItemListner() {
            @Override
            public void onRemove(int position) {
                if (xgwjList.size() >= 1) {
                    xgwjList.remove(position);  //将所对应的附件移除
                }
                xgwj_adapter.notifyDataSetChanged();
                if (typeName.equals("出差报销申请") && name.equals("fj1")) {
                    if (xgwjList != null && xgwjList.size() > 0) {
                        tvFj4.setText("    ");   //将添加附件选填文字设置为空格
                        rvFjname4.setVisibility(View.VISIBLE);
                        if (xgwjList.size() > 3) {  //如果收件人的集合大于三
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
                    if (xgwjList.size() <= 0) {
                        rvFjname4.setVisibility(View.GONE);
                        tvFj4.setText("请选择(选填)");
                    }
                }
            }
        });
    }

    @Override
    public void yesClick() {

        switch (tvGo1.getText().toString()) {
            case "其他":

                break;

            case "用车申请":

                break;

            case "出差报销申请":

                break;

            case "公务出行申请":
                popupWindowUtil = new PopupWindowUtil(EnterOutApplyActivity.this, "提交中...");
                popupWindowUtil.show();

                childType ="执行公务申请";
//                userId, Type, childtype, SHRIds, CSRlds,txrids,startTime,endTime,vehicleType,place,vehicleCharge,foodCharge,hotalCharge,roadCharge,otherCharge,allCharge,reason, params
                RequestBody type_body = RequestBody.create(MediaType.parse("multipart/form-data"), type_main);  //主类型
                RequestBody childType_body = RequestBody.create(MediaType.parse("multipart/form-data"), childType);  //副类型
                RequestBody theme_body = RequestBody.create(MediaType.parse("multipart/form-data"), theme_content.getText().toString());  //主类型
                RequestBody tong_body = RequestBody.create(MediaType.parse("multipart/form-data"), tong);  //审批人id
                RequestBody opIds_chaosong_body = RequestBody.create(MediaType.parse("multipart/form-data"), zhusong);  //抄送人opid
                RequestBody opIds_tongxing_body = RequestBody.create(MediaType.parse("multipart/form-data"), caosong);  //抄送人opid
                RequestBody time5_body = RequestBody.create(MediaType.parse("multipart/form-data"), time5.getText().toString());  //出行日期
                RequestBody time6_body = RequestBody.create(MediaType.parse("multipart/form-data"), time6.getText().toString());  //返回日期
                RequestBody typeGo_body = RequestBody.create(MediaType.parse("multipart/form-data"), typeGo.getText().toString());  //返回日期
                RequestBody priceall_body = RequestBody.create(MediaType.parse("multipart/form-data"), outContent4.getText().toString());  //总补贴费用
                RequestBody transparent_body = RequestBody.create(MediaType.parse("multipart/form-data"), thingsContent13.getText().toString());  //交通费用
                RequestBody food_body = RequestBody.create(MediaType.parse("multipart/form-data"), thingsContent14.getText().toString());  //餐饮费用
                RequestBody live_body = RequestBody.create(MediaType.parse("multipart/form-data"), thingsContent15.getText().toString());  //住宿费用
                RequestBody express_body = RequestBody.create(MediaType.parse("multipart/form-data"), thingsContent16.getText().toString());  //过路费
                RequestBody other_body = RequestBody.create(MediaType.parse("multipart/form-data"), thingsContent17.getText().toString());  //其他费用
                RequestBody place = RequestBody.create(MediaType.parse("multipart/form-data"), outContent3.getText().toString());  //出行地点
                RequestBody content = RequestBody.create(MediaType.parse("multipart/form-data"), useContent3.getText().toString());  //备注说明
                RequestBody txrNum_body = RequestBody.create(MediaType.parse("multipart/form-data"), txrNum);  //同行数量
                String opId = new GetAccount(this).opId();
                RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), opId);  //当前登陆者的Id
                Map<String, RequestBody> paramsMap = new HashMap<>();
                for (int i = 0; i < fileList.size(); i++) {
                    File file = fileList.get(i);
                    RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    paramsMap.put("file\"; filename=\"" + file.getName(), fileBody);
                }
                     //  userId, type,  childtype,  SHRIds,  CSRlds, Buytype, Reason, Deliverydatetime, Name,  standard,   num,  price,  unit
                RetrofitHelper.getInstance().doInsert_gwcx(userId,theme_body,type_body, childType_body,  opIds_chaosong_body, opIds_tongxing_body,tong_body,time5_body, time6_body, typeGo_body,place,transparent_body,food_body,live_body,express_body,other_body,priceall_body,content, txrNum_body,paramsMap, new CallBackYSAdapter() {
                    @Override
                    protected void showErrorMessage(String message) {
                        Log.e("发送申请公务失败", message);
                        if(popupWindowUtil != null){
                            popupWindowUtil.dismiss();
                        }
                        Toast.makeText(EnterOutApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    protected void parseJson(String data) {
                        popupWindowUtil.dismiss();
                        try {
                            JSONObject jsonObject=new JSONObject(data);
                            if(jsonObject.getBoolean("data")){

                                if(popupWindowUtil != null){
                                    popupWindowUtil.dismiss();
                                }
                                Toast.makeText(EnterOutApplyActivity.this,"申请提交成功",Toast.LENGTH_SHORT).show();
                                finish();

                            }else{
                                if(popupWindowUtil != null){
                                    popupWindowUtil.dismiss();
                                }
                                Toast.makeText(EnterOutApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();

                            if(popupWindowUtil != null){
                                popupWindowUtil.dismiss();
                            }
                            Toast.makeText(EnterOutApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                break;

        }

    }

    @Override
    public void noClick() {

    }

    @Override
    public void onSingleClick() {

    }

    class AddfjAdapter extends RecyclerView.Adapter<AddfjAdapter.MyViewHolder1> {

        private Activity activity;
        private ArrayList<File> fileArrayList;
        private View view = null;

        private OnRemoveItemListner listner;

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
//                        listner.onRemove(position);
                        switch (typeName) {
                            case "其他":
                                change();
                                break;
                            case "用车申请":
                                change2();
                                break;
                            case "公务出行申请":
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


        public void setOnRemoveItem(OnRemoveItemListner listner) {
            this.listner = listner;
        }
    }

    interface OnRemoveItemListner {
        void onRemove(int position);
    }

    private void money5() {
        outContent5.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        outContent5.setText(s);
                        outContent5.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    outContent5.setText(s);
                    outContent5.setSelection(2);
                }

                if (s.toString().equals("")) {
                    outContent5.setHint("请输入差旅费用(必填)");
                    bigMoney.setText("零");
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        outContent5.setText(s.subSequence(0, 1));
                        outContent5.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = outContent5.getText().toString();
                if (!s.equals("") && !s.isEmpty()) {
                    bigMoney.setText(MoneyUtil.toChinese(s));
                }
                Log.d("111", MoneyUtil.toChinese(s));

            }
        });
    }

    private void money13() {
        thingsContent13.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        thingsContent13.setText(s);
                        thingsContent13.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    thingsContent13.setText(s);
                    thingsContent13.setSelection(2);
                }


                if (s.toString().equals("")) {
                    thingsContent13.setHint("请输入交通费(必填)");
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        thingsContent13.setText(s.subSequence(0, 1));
                        thingsContent13.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = thingsContent13.getText().toString();
                if (s.equals("")) {
                    s = "0.00";
                }
                try {
                    i = Double.parseDouble(s.trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                double sum = i + i1 + i2 + i3 + i4;
                SharedPreferencesUtil.saveData(getApplicationContext(), "i", i);
                SharedPreferencesUtil.saveData(getApplicationContext(), "sum", sum);
                if (sum == 0.0 || sum == 0.00) {
                    outContent4.setText("0.00");
                } else {
                    outContent4.setText("" + df.format(sum));
                }
            }
        });
    }

    private void money14() {
        thingsContent14.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        thingsContent14.setText(s);
                        thingsContent14.setSelection(s.length());
                    }
                }


                if (s.toString().equals("")) {
                    thingsContent14.setHint("请输入餐饮费(必填)");
                }


                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    thingsContent14.setText(s);
                    thingsContent14.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        thingsContent14.setText(s.subSequence(0, 1));
                        thingsContent14.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = thingsContent14.getText().toString();
                if (s.equals("")) {
                    s = "0.00";
                }
                try {
                    i1 = Double.parseDouble(s.trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                double sum = i + i1 + i2 + i3 + i4;
                SharedPreferencesUtil.saveData(getApplicationContext(), "i1", i1);
                SharedPreferencesUtil.saveData(getApplicationContext(), "sum", sum);
                if (sum == 0.0 || sum == 0.00) {
                    outContent4.setText("0.00");
                } else {
                    outContent4.setText("" + df.format(sum));
                }
            }
        });
    }

    private void money15() {
        thingsContent15.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        thingsContent15.setText(s);
                        thingsContent15.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    thingsContent15.setText(s);
                    thingsContent15.setSelection(2);
                }

                if (s.toString().equals("")) {
                    thingsContent15.setHint("请输入住宿费(必填)");
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        thingsContent15.setText(s.subSequence(0, 1));
                        thingsContent15.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = thingsContent15.getText().toString();
                if (s.equals("")) {
                    s = "0.00";
                }
                try {
                    i2 = Double.parseDouble(s.trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                double sum = i + i1 + i2 + i3 + i4;
                SharedPreferencesUtil.saveData(getApplicationContext(), "i2", i2);
                SharedPreferencesUtil.saveData(getApplicationContext(), "sum", sum);
                if (sum == 0.0 || sum == 0.00) {
                    outContent4.setText("0.00");
                } else {
                    outContent4.setText("" + df.format(sum));
                }
            }
        });
    }

    private void money16() {
        thingsContent16.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        thingsContent16.setText(s);
                        thingsContent16.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    thingsContent16.setText(s);
                    thingsContent16.setSelection(2);
                }

                if (s.toString().equals("")) {
                    thingsContent16.setHint("请输入过路费(必填)");
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        thingsContent16.setText(s.subSequence(0, 1));
                        thingsContent16.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = thingsContent16.getText().toString();
                if (s.equals("")) {
                    s = "0.00";
                }
                try {
                    i3 = Double.parseDouble(s.trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                double sum = i + i1 + i2 + i3 + i4;
                SharedPreferencesUtil.saveData(getApplicationContext(), "i3", i3);
                SharedPreferencesUtil.saveData(getApplicationContext(), "sum", sum);
                if (sum == 0.0 || sum == 0.00) {
                    outContent4.setText("0.00");
                } else {
                    outContent4.setText("" + df.format(sum));
                }
            }
        });
    }

    private void money17() {
        thingsContent17.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        thingsContent17.setText(s);
                        thingsContent17.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    thingsContent17.setText(s);
                    thingsContent17.setSelection(2);
                }

                if (s.toString().equals("")) {
                    thingsContent17.setHint("请输入其他(必填)");
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        thingsContent17.setText(s.subSequence(0, 1));
                        thingsContent17.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = thingsContent17.getText().toString();
                if (s.equals("")) {
                    s = "0.00";
                }
                try {
                    i4 = Double.parseDouble(s.trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                double sum = i + i1 + i2 + i3 + i4;
                SharedPreferencesUtil.saveData(getApplicationContext(), "i4", i4);
                SharedPreferencesUtil.saveData(getApplicationContext(), "sum", sum);
                if (sum == 0.0 || sum == 0.00) {
                    outContent4.setText("0.00");
                } else {
                    outContent4.setText("" + df.format(sum));
                }
            }
        });
    }
}