package com.lanwei.governmentstar.activity.zyx;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.zyx.application.GovApplication;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.CallBackAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.CropUtils;
import com.lanwei.governmentstar.utils.DialogPermission;
import com.lanwei.governmentstar.utils.FileUtil;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.PermissionUtil;
import com.lanwei.governmentstar.utils.PhotoUtils;
import com.lanwei.governmentstar.utils.PictureUtil;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.utils.SharedPreferenceMark;
import com.lanwei.governmentstar.utils.SharedPreferencesUtil;
import com.lanwei.governmentstar.view.Dialog01;
import com.lanwei.governmentstar.view.Dialog02;
import com.lanwei.governmentstar.view.DialogCarmera;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by 蓝威科技—技术部2 on 2017/3/18.
 */

public class PersonalDataActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {

    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.dptname)
    TextView dptname;
    @InjectView(R.id.strname)
    TextView strname;
    private TextView tv_upicon;

    private static final int REQUEST_CODE_TAKE_PHOTO = 1;
    private static final int REQUEST_CODE_ALBUM = 2;
    private static final int REQUEST_CODE_CROUP_PHOTO = 3;

    protected static Uri tempUri;
    private ImageView iv_personal_icon;
    private Dialog01 dialog01;
    private TextView select_photo;
    private TextView camera;
    private Intent intent;
    //图片路径
    private String imagePath;
    private ImageView bg1;
    private ImageView bg2;
    private ImageView bg3;
    private ImageView bg4;
    private ImageView bg5;
    private ImageView bg6;
    private SharedPreferencesUtil sp;
    private Dialog02 dialog02;

    private String cardId = "";
    private Logging_Success bean = new Logging_Success();
    private EditText office;
    private EditText office_phone;
    private EditText user_mobile;
    private SharedPreferences preferences;
    private SharedPreferences.Editor ediotr_modify;

    private static final String TAG = "Picture";
    private int screen_width;
    private File file;
    private Uri uri;
    private File cover;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
        setContentView(R.layout.activity_personaldata);
        ButterKnife.inject(this);
        tv_upicon = (TextView) findViewById(R.id.tv_upicon);
        iv_personal_icon = (ImageView) findViewById(R.id.upicon);
        TextView title = (TextView) findViewById(R.id.tv_address);
        ImageView back = (ImageView) findViewById(R.id.back);
        ImageView icon = (ImageView) findViewById(R.id.iv_contacts);
        TextView apl = (TextView) findViewById(R.id.tv_apl);
        title.setVisibility(View.VISIBLE);
        TextView name = (TextView) findViewById(R.id.name);
        TextView dptname = (TextView) findViewById(R.id.dptname);
        TextView strname = (TextView) findViewById(R.id.strname);
        bg1 = (ImageView) findViewById(R.id.bg1);
        bg2 = (ImageView) findViewById(R.id.bg2);
        bg3 = (ImageView) findViewById(R.id.bg3);
        bg4 = (ImageView) findViewById(R.id.bg4);
        bg5 = (ImageView) findViewById(R.id.bg5);
        bg6 = (ImageView) findViewById(R.id.bg6);

        title.setText("个人信息");
        back.setVisibility(View.VISIBLE);
        icon.setVisibility(View.GONE);
        apl.setVisibility(View.VISIBLE);

        setOnClick(back, apl, name, dptname, strname);

        setInfo(name, dptname, strname);

    }

    private void initSelectView(String bg) {
        switch (bg) {
            case "7":
                bg1();
                break;
            case "24":
                bg2();
                break;
            case "25":
                bg3();
                break;
            case "26":
                bg4();
                break;
            case "27":
                bg5();
                break;
            case "28":
                bg6();
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_upicon:
                showChoosePicDialog();
                break;
            case R.id.tv_apl:

                dialog02 = new Dialog02(PersonalDataActivity.this);

                dialog02.setContent("您确定要修改信息吗？", Color.parseColor("#4f4f4f"));
                dialog02.setTitle("修改信息", Color.parseColor("#5184c3"));
                dialog02.setLeftBtn(R.drawable.select_button_left, Color.WHITE);
                dialog02.setRightBtn(R.drawable.select_button_right, Color.WHITE);
                dialog02.setYesOnclickListener("确定", new Dialog02.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
                        modifyData();

                        // 可能修改图像的标记，以便切换到myFragment重新加载图片
                        preferences = getSharedPreferences("modify_photo", 0);
                        ediotr_modify = preferences.edit();
                        ediotr_modify.putBoolean("isChanged", true).commit();

                        // 这里崩了
                        //Toast.makeText(PersonalDataActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                        //dialog02.dismiss();
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

                wl.y = -getWindowManager().getDefaultDisplay().getHeight() / 50;
                //设置显示位置
                dialog02.onWindowAttributesChanged(wl);//设置点击外围解散
                dialog02.setCanceledOnTouchOutside(true);


                dialog02.show();

                break;
            case R.id.back:
                finish();
                break;
            case R.id.bg1:
                bg1();
                cardId = "7";
                break;
            case R.id.bg2:
                bg2();
                cardId = "24";
                break;
            case R.id.bg3:
                bg3();
                cardId = "25";
                break;
            case R.id.bg4:
                bg4();
                cardId = "26";
                break;
            case R.id.bg5:
                bg5();
                cardId = "27";
                break;
            case R.id.bg6:
                bg6();
                cardId = "28";
                break;

        }
    }

    /**
     * 显示修改头像的对话框
     */
    protected void showChoosePicDialog() {
        initHeadPortrait();
        final DialogCarmera dialogCarmera = new DialogCarmera(this);
        dialogCarmera.builder().setOnClick(new DialogCarmera.OnSheetItemClickListener() {
            @Override
            public void onTakePhoto() {
                if (PermissionUtil.hasCameraPermission(PersonalDataActivity.this)) {
                    openCamera();
                }
                dialogCarmera.dismiss();
            }

            @Override
            public void onAlbum() {
                openAlbum();
                dialogCarmera.dismiss();
            }
        });

        dialogCarmera.show();
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("上传头像");
//        String[] items = {"从相册选择图片", "拍照"};
//        builder.setNegativeButton("取消", null);
//
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case CHOOSE_PICTURE: // 选择本地照片
//                        openAlbum();
//                        break;
//                    case TAKE_PICTURE: // 拍照
//                        if (PermissionUtil.hasCameraPermission(PersonalDataActivity.this)) {
//                            openCamera();
//                        }
//                        break;
//                }
//            }
//        });
//        builder.create().show();
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST_CODE_ALBUM);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) {
            return;
        }
        if (requestCode == REQUEST_CODE_ALBUM && data != null) {
            Uri newUri;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                newUri = Uri.parse("file:///" + CropUtils.getPath(this, data.getData()));
            } else {
                newUri = data.getData();
            }
            if (newUri != null) {
                startPhotoZoom(newUri);
            } else {
                Toast.makeText(this, "没有得到相册图片", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == REQUEST_CODE_TAKE_PHOTO) {
            startPhotoZoom(tempUri);
        } else if (requestCode == REQUEST_CODE_CROUP_PHOTO) {
            compressAndUploadAvatar(file.getPath());
        }
    }

    private void compressAndUploadAvatar(String fileSrc) {
        String compressPath = PictureUtil.compressPicture(fileSrc, PersonalDataActivity.this);  //压缩图片
        cover = new File(compressPath);
//        final File cover = FileUtil.getSmallBitmap(this, path);
        //加载本地图片
        uri = Uri.fromFile(cover);
        Bitmap photo = BitmapFactory.decodeFile(cover.getPath());

        iv_personal_icon.setImageBitmap(photo);
//        if (photo != null) {
//            //获取图片路径
//            imagePath = PhotoUtils.savePhoto(photo, Environment
//                    .getExternalStorageDirectory().getAbsolutePath(), String
//                    .valueOf(System.currentTimeMillis()));
//        }
        if (photo != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                //获取图片路径
                imagePath = PhotoUtils.savePhoto(photo, Environment
                        .getExternalStorageDirectory().getAbsolutePath(), String
                        .valueOf(System.currentTimeMillis()));
            } else {
                imagePath = fileSrc;
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


    //个人设置
    private void modifyData() {
        RequestBody office_value = RequestBody.create(MediaType.parse("multipart/form-data"), office.getText().toString());
        final RequestBody office_phone_vaule = RequestBody.create(MediaType.parse("multipart/form-data"), office_phone.getText().toString());
        final RequestBody user_mobile_vaule = RequestBody.create(MediaType.parse("multipart/form-data"), user_mobile.getText().toString());
        //模拟 d165ee59-5ef2-4fc8-8613-4ac0c932db1b
        RequestBody op_id = RequestBody.create(MediaType.parse("multipart/form-data"), bean.getData().getOpId());
        //模拟名片ID
        RequestBody card_id = RequestBody.create(MediaType.parse("multipart/form-data"), cardId);
        //图片
        RequestBody imageBody;
        MultipartBody.Part imageBodyPart = null;
        if (cover != null) {
//            //获取图片源地址
//            File file = new File(imagePath);
            imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), cover);
            Log.e("文件路径", String.valueOf(file));
            imageBodyPart = MultipartBody.Part.createFormData("imgfile", file.getName(), imageBody);
        }
        RetrofitHelper.getInstance().setModifyData(op_id, office_value, office_phone_vaule, user_mobile_vaule, card_id, imageBodyPart, new CallBackAdapter() {
            @Override
            protected void showErrorMessage(String message) {

            }

            @Override
            protected void parseJson(String data) {
                try {
                    Gson gson = new Gson();
                    PreferencesManager.getInstance(PersonalDataActivity.this, "accountBean").put("jsonStr", gson.toJson(bean));

                    bean.getData().setAccountAddress(office.getText().toString());
                    bean.getData().setAccountMobile(user_mobile.getText().toString());
                    bean.getData().setAccountPhone(office_phone.getText().toString());

                    JSONObject dataJson = new JSONObject(data);

                    //返回头像路径
                    String accountLink = dataJson.getString("accountlink");
                    boolean result = dataJson.getBoolean("result");
                    Intent in = new Intent();
                    //判断头像不为空的时候
                    if (accountLink != null && !accountLink.equals("")) {
                        //更新冬雪保存的登录头像 到时也不要忘记这边
                        in.putExtra("accountLink", accountLink);
                        bean.getData().setAccountlink(accountLink);
                    }else{
                        in.putExtra("accountLink", bean.getData().getAccountlink());
                    }

                    if (!cardId.equals("")) {
                        in.putExtra("bg", cardId);
                        bean.getData().setAccountCard(cardId);
                    }

                    if (result) {
                        Toast.makeText(PersonalDataActivity.this, "修改成功",
                                Toast.LENGTH_SHORT).show();
                        setResult(110, in);
                        finish();

                    } else {
                        Toast.makeText(PersonalDataActivity.this, "修改失败",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dialog02.dismiss();
            }
        });
    }

    @Override
    public boolean onLongClick(View v) {
        TextView textView = (TextView) v;
        ClipboardManager cmb = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(textView.getText().toString().trim()); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
        cmb.getText();//获取粘贴信息
        Toast.makeText(getApplicationContext(), "复制文本成功", Toast.LENGTH_SHORT).show();
        return false;
    }

    private void setInfo(TextView name, TextView dptname, TextView strname) {
        bean = new GetAccount(this).accountBean();
        if (bean != null) {
            String accountLink = bean.getData().getAccountlink();
            if (accountLink != null && !accountLink.equals("")) {
                Picasso.with(this).load(accountLink).memoryPolicy(MemoryPolicy.NO_CACHE).into(iv_personal_icon);
            }
            //判断名片ID是否有值，如果没有，则默认一个值
            String accountCard = bean.getData().getAccountCard() != null || !bean.getData().getAccountCard().equals("") ? bean.getData().getAccountCard() : "7";
            initSelectView(accountCard);
            //办公室
            office = (EditText) findViewById(R.id.office);
            office.setText(bean.getData().getAccountAddress());
            //办公司电话
            office_phone = (EditText) findViewById(R.id.office_phone);
            office_phone.setText(bean.getData().getAccountPhone());
            //个人手机
            user_mobile = (EditText) findViewById(R.id.user_mobile);
            user_mobile.setText(bean.getData().getAccountMobile());

            name.setText(bean.getData().getOpName());
            dptname.setText(bean.getData().getAccountDeptName());
            strname.setText(bean.getData().getAccountSectorName());
        }
    }

    private void setOnClick(ImageView back, TextView apl, TextView name, TextView dptname, TextView strname) {
        tv_upicon.setOnClickListener(this);
        back.setOnClickListener(this);
        apl.setOnClickListener(this);
        name.setOnLongClickListener(this);
        strname.setOnLongClickListener(this);
        dptname.setOnLongClickListener(this);
        bg1.setOnClickListener(this);
        bg2.setOnClickListener(this);
        bg3.setOnClickListener(this);
        bg4.setOnClickListener(this);
        bg5.setOnClickListener(this);
        bg6.setOnClickListener(this);
    }


    private void bg6() {
        bg6.setImageResource(R.drawable.choice);
        bg2.setImageResource(R.drawable.shape_transparent);
        bg3.setImageResource(R.drawable.shape_transparent);
        bg4.setImageResource(R.drawable.shape_transparent);
        bg5.setImageResource(R.drawable.shape_transparent);
        bg1.setImageResource(R.drawable.shape_transparent);
    }

    private void bg5() {
        bg5.setImageResource(R.drawable.choice);
        bg2.setImageResource(R.drawable.shape_transparent);
        bg3.setImageResource(R.drawable.shape_transparent);
        bg4.setImageResource(R.drawable.shape_transparent);
        bg1.setImageResource(R.drawable.shape_transparent);
        bg6.setImageResource(R.drawable.shape_transparent);
    }

    private void bg4() {
        bg4.setImageResource(R.drawable.choice);
        bg2.setImageResource(R.drawable.shape_transparent);
        bg3.setImageResource(R.drawable.shape_transparent);
        bg1.setImageResource(R.drawable.shape_transparent);
        bg5.setImageResource(R.drawable.shape_transparent);
        bg6.setImageResource(R.drawable.shape_transparent);
    }

    private void bg3() {
        bg3.setImageResource(R.drawable.choice);
        bg2.setImageResource(R.drawable.shape_transparent);
        bg1.setImageResource(R.drawable.shape_transparent);
        bg4.setImageResource(R.drawable.shape_transparent);
        bg5.setImageResource(R.drawable.shape_transparent);
        bg6.setImageResource(R.drawable.shape_transparent);
    }

    private void bg2() {
        bg2.setImageResource(R.drawable.choice);
        bg1.setImageResource(R.drawable.shape_transparent);
        bg3.setImageResource(R.drawable.shape_transparent);
        bg4.setImageResource(R.drawable.shape_transparent);
        bg5.setImageResource(R.drawable.shape_transparent);
        bg6.setImageResource(R.drawable.shape_transparent);
    }

    private void bg1() {
        bg1.setImageResource(R.drawable.choice);
        bg2.setImageResource(R.drawable.shape_transparent);
        bg3.setImageResource(R.drawable.shape_transparent);
        bg4.setImageResource(R.drawable.shape_transparent);
        bg5.setImageResource(R.drawable.shape_transparent);
        bg6.setImageResource(R.drawable.shape_transparent);
    }

}