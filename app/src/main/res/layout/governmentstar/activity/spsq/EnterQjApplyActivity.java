package com.lanwei.governmentstar.activity.spsq;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
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
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
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
import com.lanwei.governmentstar.activity.spsq.view.PhotoPicker;
import com.lanwei.governmentstar.activity.zwyx.ZwyxTreeActivity;
import com.lanwei.governmentstar.activity.zyx.application.GovApplication;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.Constant;
import com.lanwei.governmentstar.utils.CropUtils;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.FileUtil;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.ManagerUtils;
import com.lanwei.governmentstar.utils.PermissionUtil;
import com.lanwei.governmentstar.utils.PhotoUtils;
import com.lanwei.governmentstar.utils.PictureUtil;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.utils.SharedPreferencesUtil;
import com.lanwei.governmentstar.view.DialogCarmera;
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

public class EnterQjApplyActivity extends AutoLayoutActivity implements View.OnClickListener, DialogUtil.OnClickListenner {
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
    @InjectView(R.id.ll_sick)
    LinearLayout llSick;
    @InjectView(R.id.tv_borrow1)
    TextView tvBorrow1;
    @InjectView(R.id.tv_return1)
    TextView tvReturn1;
    @InjectView(R.id.tv_fj1)
    TextView tvFj1;
    @InjectView(R.id.rv_fjname1)
    RecyclerView rvFjname1;
    @InjectView(R.id.looking1)
    TextView looking1;
    @InjectView(R.id.inlook_line1)
    View inlookLine1;
    @InjectView(R.id.tv_go2)
    TextView tv_go2;
    @InjectView(R.id.tv_go3)
    TextView tv_go3;
    @InjectView(R.id.inlooking1)
    ImageView inlooking1;
    @InjectView(R.id.use_content1)
    EditText useContent1;
    @InjectView(R.id.hasSelect_content)
    TextView hasSelectContent;
    @InjectView(R.id.nametype)
    TextView nameType;
    @InjectView(R.id.inbox_layout_shenqi)
    RelativeLayout inbox_layout_shenqi;
    @InjectView(R.id.inbox_layout2)
    RelativeLayout inbox_layout2;
    @InjectView(R.id.inbox_layout_caosong)
    RelativeLayout inbox_layout_caosong;
    @InjectView(R.id.iv_pic)
    ImageView ivPic;
    @InjectView(R.id.theme_content)
    EditText theme_content;

    private String[] typeQj;  //选择资金类型
    private WheelDialog typeDialog;
    private String typeName;
    private AddfjAdapter fjadapter;
    private String name;
    private String type = "请假申请" ;
    private String childType = "" ;
    private Intent intent;

    private ArrayList<String> opIdlist_shenqi;
    private ArrayList<String> opIdlist_chaosong;
    private ArrayList<String> addresseNameList_shenqi;
    private ArrayList<String> addresseNameList_chaosong;

    private String zhusong = "";
    private String zhusong_temp = "";
    private String caosong = "";
    private String caosong_temp = "";
    private String tong = "";
    private String tong_temp = "";
    private PopupWindowUtil popupWindowUtil;
    protected static Uri tempUri;
    private File file;
    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private RecyclerView recyclerView;
    private PhotoAdapter photoAdapter;
    private static final int REQUEST_CODE_CROUP_PHOTO = 3;
    private String opId2 = "";
    private Logging_Success bean;

    private File cover;
    private Uri uri;
    private String imagePath;


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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.activity_enterqjapply);
        ButterKnife.inject(this);

        initview();
        // 获取bean;
        String defString = PreferencesManager.getInstance(EnterQjApplyActivity.this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);

        RetrofitHelper.getInstance().spsqCreate(new CallBackYSAdapter() {
            @Override
            protected void showErrorMessage(String message) {


            }

            @Override
            protected void parseJson(String data) {

                try {
                    JSONObject jsonObject =new JSONObject(data);
                    opId2 = jsonObject.getString("opId");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void initview() {
        nameType.setText("请假类型");
        tvAddress.setVisibility(View.VISIBLE);
        tvAddress.setText("请假申请");
        back.setVisibility(View.VISIBLE);
        ivContacts.setVisibility(View.GONE);
        tvApl.setVisibility(View.VISIBLE);
        tvApl.setText("提交审批");
        tvApl.setOnClickListener(this);
        back.setOnClickListener(this);
        tvGo1.setOnClickListener(this);
        inbox_layout2.setOnClickListener(this);
        tvBorrow1.setOnClickListener(this);
        tvReturn1.setOnClickListener(this);
        looking1.setOnClickListener(this);
        inlooking1.setOnClickListener(this);
        tvFj1.setOnClickListener(this);
        ivPic.setOnClickListener(this);
        inbox_layout_shenqi.setOnClickListener(this);
        inbox_layout_caosong.setOnClickListener(this);

        selectText();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(EnterQjApplyActivity.this, selectedPhotos);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, OrientationHelper.VERTICAL){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
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
                                    .start(EnterQjApplyActivity.this);

                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(selectedPhotos)
                                    .setCurrentItem(position)
                                    .start(EnterQjApplyActivity.this);
                        }
                    }
                }));

    }

    String b1;

    private void selectText() {
        tvBorrow1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!(tvBorrow1.equals("请选择(必填)"))) {
                    String s = tvBorrow1.getText().toString();
                    b1 = s.replace("-", "/");
                    String r1 = String.valueOf(SharedPreferencesUtil.getData(EnterQjApplyActivity.this, "tvReturn1", ""));
                    if (r1.equals("") && r1.isEmpty()) {
                        hasSelectContent.setText(b1);
                    } else {
                        hasSelectContent.setText(b1 + " - " + r1);
                    }
                }
            }
        });
        tvReturn1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!tvReturn1.equals("请选择(必填)")) {
                    hasSelectContent.setText(b1);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!(tvReturn1.equals(""))) {
                    String s = tvReturn1.getText().toString();
                    String r1 = s.replace("-", "/");
                    SharedPreferencesUtil.saveData(EnterQjApplyActivity.this, "tvReturn1", r1);
                    String s1 = hasSelectContent.getText().toString();
                    hasSelectContent.setText(s1 + " -" + r1);
                }
            }
        });
    }

    private void allTypes(String typeName) {
        if (fjadapter == null)
            fjadapter = new AddfjAdapter(fileList, EnterQjApplyActivity.this);
        fileList.clear();     //如果类型改变了就清空选中附件
        fjadapter.notifyDataSetChanged();

        if (typeName.equals("病假")) {  // TODO: 2017/4/13   这里如果类型只能为整形的话 可能会出错
            llSick.setVisibility(View.VISIBLE);
            childType = "病假";
            SharedPreferencesUtil.saveData(EnterQjApplyActivity.this, "tvReturn1", "");
            useContent1.setText("");
            tvBorrow1.setText("请选择(必填)");
            tvReturn1.setText("请选择(必填)");
            hasSelectContent.setText("开始日期 - 结束日期");
            tvFj1.setText("请选择(选填)");
            rvFjname1.setVisibility(View.GONE);

        } else if (typeName.equals("事假")) {  // TODO: 2017/4/13   这里如果类型只能为整形的话 可能会出错
            llSick.setVisibility(View.VISIBLE);
            childType = "事假";
            SharedPreferencesUtil.saveData(EnterQjApplyActivity.this, "tvReturn1", "");
            useContent1.setText("");
            tvBorrow1.setText("请选择(必填)");
            tvReturn1.setText("请选择(必填)");
            hasSelectContent.setText("开始日期 - 结束日期");
            tvFj1.setText("请选择(选填)");
            rvFjname1.setVisibility(View.GONE);

        } else if (typeName.equals("婚假")) {  // TODO: 2017/4/13   这里如果类型只能为整形的话 可能会出错
            llSick.setVisibility(View.VISIBLE);
            childType = "婚假";
            SharedPreferencesUtil.saveData(EnterQjApplyActivity.this, "tvReturn1", "");
            useContent1.setText("");
            tvBorrow1.setText("请选择(必填)");
            tvReturn1.setText("请选择(必填)");
            hasSelectContent.setText("开始日期 - 结束日期");
            tvFj1.setText("请选择(选填)");
            rvFjname1.setVisibility(View.GONE);

        } else if (typeName.equals("丧假")) {  // TODO: 2017/4/13   这里如果类型只能为整形的话 可能会出错
            llSick.setVisibility(View.VISIBLE);
            childType = "丧假";
            SharedPreferencesUtil.saveData(EnterQjApplyActivity.this, "tvReturn1", "");
            useContent1.setText("");
            tvBorrow1.setText("请选择(必填)");
            tvReturn1.setText("请选择(必填)");
            hasSelectContent.setText("开始日期 - 结束日期");
            tvFj1.setText("请选择(选填)");
            rvFjname1.setVisibility(View.GONE);

        } else if (typeName.equals("产假")) {  // TODO: 2017/4/13   这里如果类型只能为整形的话 可能会出错
            llSick.setVisibility(View.VISIBLE);
            childType = "产假";
            SharedPreferencesUtil.saveData(EnterQjApplyActivity.this, "tvReturn1", "");
            useContent1.setText("");
            tvBorrow1.setText("请选择(必填)");
            tvReturn1.setText("请选择(必填)");
            hasSelectContent.setText("开始日期 - 结束日期");
            tvFj1.setText("请选择(选填)");
            rvFjname1.setVisibility(View.GONE);

        } else if (typeName.equals("年休假")) {  // TODO: 2017/4/13   这里如果类型只能为整形的话 可能会出错
            llSick.setVisibility(View.VISIBLE);
            childType = "年休假";
            SharedPreferencesUtil.saveData(EnterQjApplyActivity.this, "tvReturn1", "");
            useContent1.setText("");
            tvBorrow1.setText("请选择(必填)");
            tvReturn1.setText("请选择(必填)");
            hasSelectContent.setText("开始日期 - 结束日期");
            tvFj1.setText("请选择(选填)");
            rvFjname1.setVisibility(View.GONE);

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


            case R.id.iv_pic:

                PhotoPicker.builder()
                        .setShowCamera(true)
                        .setPhotoCount(6)
                        .setGridColumnCount(3)
                        .start(EnterQjApplyActivity.this);

//                final DialogCarmera dialogCarmera = new DialogCarmera(this);
//                dialogCarmera.builder().setOnClick(new DialogCarmera.OnSheetItemClickListener() {
//                    @Override
//                    public void onTakePhoto() {
//                        dialogCarmera.dismiss();
//                        initHeadPortrait();
//
//                        if (PermissionUtil.hasCameraPermission(EnterQjApplyActivity.this)) {
//                            openCamera();
//                        }
////                        Toast.makeText(getApplicationContext(), "相机", Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onAlbum() {
//                        dialogCarmera.dismiss();
//
//                        PhotoPicker.builder()
//                                .setShowCamera(false)
//                                .setPhotoCount(6)
//                                .setGridColumnCount(3)
//                                .start(EnterQjApplyActivity.this);
//                    }
//
//                });
//                dialogCarmera.show();
                break;



            case R.id.inbox_layout_shenqi:

                if(bean.getData().getAccountDeptId().equals("0155")){
                    intent= new Intent(EnterQjApplyActivity.this,ChooseReceivers_Spsq_Activity.class);
                }else{
                    intent= new Intent(EnterQjApplyActivity.this,Chooses_ReceiversSPAQ_Activity.class);
                }
                intent.putExtra("type","zhu");
                intent.putExtra("zhusong",zhusong);
                intent.putExtra("caosong",caosong);
                intent.putExtra("tong",tong);
                startActivityForResult(intent,20);

                break;
            case R.id.inbox_layout_caosong:

                if(bean.getData().getAccountDeptId().equals("0155")){
                    intent= new Intent(EnterQjApplyActivity.this,ChooseReceivers_Spsq_Activity.class);
                }else{
                    intent= new Intent(EnterQjApplyActivity.this,Chooses_ReceiversSPAQ_Activity.class);
                }
                intent.putExtra("type","cao");
                intent.putExtra("zhusong",zhusong);
                intent.putExtra("caosong",caosong);
                intent.putExtra("tong",tong);
                startActivityForResult(intent,20);

                break;

            case R.id.tv_go1:
                typeQj = EnterQjApplyActivity.this.getResources().getStringArray(R.array.qj_type);
                typeDialog = new WheelDialog(this, mTypeHandler, typeQj, typeName);
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

            case R.id.tv_fj1:
                opFileManager();
                break;

//            case R.id.apply_seal:
//                Intent intent = new Intent(this, EnterSealApplyActivity.class);
//                startActivity(intent);
//                break;
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

    private void judge() {

        if (TextUtils.isEmpty(typeName)) {
            ManagerUtils.showToast(this, "请选择调用类型");
            return;
        }

        if (theme_content.getText().toString().equals("")) {
            ManagerUtils.showToast(this, "请填写申请主题");
            return;
        }

        if (!tvBorrow1.getText().toString().contains("-")) {
            ManagerUtils.showToast(this, "请选择开始日期");
            return;
        }

        if (!tvReturn1.getText().toString().contains("-")) {
            ManagerUtils.showToast(this, "请选择结束日期");
            return;
        }

        if (TextUtils.isEmpty(useContent1.getText().toString())) {
            ManagerUtils.showToast(this, "请输入请假事由");
            return;
        }
        new DialogUtil(EnterQjApplyActivity.this, this).showConfirm("提交审批", "您确定要提交该审批吗？", "确定", "取消");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:

                switch (requestCode){

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

                        for(int i =0 ;i<selectedPhotos.size();i++){
                            Log.e("file_path地方大师傅赶得上发的",selectedPhotos.get(i).toString());
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
                    case  FILE_SELECT_CODE:
                    Uri newUri = null;
                    if (data == null) {
                        return;
                    }

                        try {
                            newUri = Uri.parse(CropUtils.getPath(this, data.getData()));
                            File file2 = new File(String.valueOf(newUri));
                            Log.e("ccc", file2.getName());
                            if (file2 != null) {
                                fileList.add(file2);
                            }
                            fjadapter = new AddfjAdapter(fileList, this);
                            typesAdapter();   //给附件名称的recyclerview设置adapter    不同情况不同的显示隐藏
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(this, "请通过其他方式选择该附件", Toast.LENGTH_SHORT).show();
                        }
                    Log.e("小于25File", String.valueOf(newUri));


                    break;


                    case REQUEST_CODE_TAKE_PHOTO:
                        startPhotoZoom(tempUri);
                        break;
                    case REQUEST_CODE_CROUP_PHOTO:
                        compressAndUploadAvatar(file.getPath());
                        break;

                }


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
//                }else if(requestCode==20){
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
//                }

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


    private void compressAndUploadAvatar(String fileSrc) {
        String compressPath = PictureUtil.compressPicture(fileSrc, EnterQjApplyActivity.this);  //压缩图片
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
    private void typesAdapter() {
        rvFjname1.setLayoutManager(new LinearLayoutManager(EnterQjApplyActivity.this, LinearLayoutManager.VERTICAL, false));   //为recyclerView指定现行垂直布局
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
        fjadapter.notifyDataSetChanged();
    }

    @Override
    public void yesClick() {
        popupWindowUtil = new PopupWindowUtil(EnterQjApplyActivity.this, "提交中...");
        popupWindowUtil.show();
        RequestBody type_body = RequestBody.create(MediaType.parse("multipart/form-data"), type);  //主类型
        RequestBody theme_body = RequestBody.create(MediaType.parse("multipart/form-data"), theme_content.getText().toString());  //主类型
        RequestBody childType_body = RequestBody.create(MediaType.parse("multipart/form-data"), childType);  //副类型
        RequestBody opIds_shenqi_body = RequestBody.create(MediaType.parse("multipart/form-data"), zhusong);  //审批人id
        RequestBody opIds_chaosong_body = RequestBody.create(MediaType.parse("multipart/form-data"), caosong);  //抄送人opid
        RequestBody tvBorrow1_body = RequestBody.create(MediaType.parse("multipart/form-data"), tvBorrow1.getText().toString());  //开始日期
        RequestBody tvReturn1_body = RequestBody.create(MediaType.parse("multipart/form-data"), tvReturn1.getText().toString());  //结束日期
        RequestBody useContent1_body = RequestBody.create(MediaType.parse("multipart/form-data"), useContent1.getText().toString());  //请假事由
        String opId = new GetAccount(EnterQjApplyActivity.this).opId();
        RequestBody userId = RequestBody.create(MediaType.parse("multipart/form-data"), opId);  //当前登陆者的Id
        Map<String, RequestBody> paramsMap = new HashMap<>();
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            paramsMap.put("file\"; filename=\"" + file.getName(), fileBody);
        }

        RetrofitHelper.getInstance().doInsert_Qjsq(opId2 ,userId, theme_body,type_body, childType_body, opIds_shenqi_body, opIds_chaosong_body, tvBorrow1_body, tvReturn1_body, useContent1_body, paramsMap, new CallBackYSAdapter() {
            @Override
            protected void showErrorMessage(String message) {

                if(popupWindowUtil != null){
                    popupWindowUtil.dismiss();
                    Toast.makeText(EnterQjApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void parseJson(String data) {

                try {
                    JSONObject jsonObject=new JSONObject(data);
                    if(jsonObject.getBoolean("data")){

                        Map<String, RequestBody> paramsMap2 = new HashMap<>();

                        ArrayList<File> list =new ArrayList<>();
                        if(selectedPhotos.size()>0){
                            for (int i = 0; i < selectedPhotos.size(); i++) {
                                list.add(new File(selectedPhotos.get(i)));
                            }

                            for (int i = 0; i < list.size(); i++) {
                                File file = list.get(i);
                                RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                                paramsMap2.put("file\"; filename=\"" + file.getName(), fileBody);
                            }

                            RetrofitHelper.getInstance().dospsqImagLoad(opId2,paramsMap2, new CallBackYSAdapter() {
                                @Override
                                protected void showErrorMessage(String message) {

                                    if(popupWindowUtil != null){
                                        popupWindowUtil.dismiss();
                                        Toast.makeText(EnterQjApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                protected void parseJson(String data) {

                                    try {
                                        JSONObject jsonObject=new JSONObject(data);
                                        if(jsonObject.getBoolean("data")){

                                            if(popupWindowUtil != null){
                                                popupWindowUtil.dismiss();
                                                Toast.makeText(EnterQjApplyActivity.this,"申请提交成功",Toast.LENGTH_SHORT).show();
                                                finish();
                                            }

                                        }else{
                                            if(popupWindowUtil != null){
                                                popupWindowUtil.dismiss();
                                                Toast.makeText(EnterQjApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        if(popupWindowUtil != null){
                                            popupWindowUtil.dismiss();
                                            Toast.makeText(EnterQjApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(EnterQjApplyActivity.this,"申请提交成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }


                    }else{
                        if(popupWindowUtil != null){
                            popupWindowUtil.dismiss();
                            Toast.makeText(EnterQjApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    if(popupWindowUtil != null){
                        popupWindowUtil.dismiss();
                        Toast.makeText(EnterQjApplyActivity.this,"申请提交失败",Toast.LENGTH_SHORT).show();

                    }
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

                        change();

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
        }
    }
}

