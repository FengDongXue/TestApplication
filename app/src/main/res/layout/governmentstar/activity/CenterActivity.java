package com.lanwei.governmentstar.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.Constant;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.Exit_System;
import com.lanwei.governmentstar.utils.PackageUtils;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.utils.SharedPreferencesUtil;
import com.lanwei.governmentstar.view.StatusBarUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/18.
 */

public class CenterActivity extends BaseActivity implements View.OnClickListener {


    private TextView version;
    private ImageView back;
    private RelativeLayout introduction;
    private RelativeLayout instruction;
    private RelativeLayout feedback;
    private RelativeLayout update;
    private RelativeLayout information;
    private TextView telephone;
    private TextView amount_convey;
    private TextView item;
    private PopupWindow popupWindow;
    private LinearLayout condition;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private PackageInfo packageInfo;
    private int localVersion;
    private JSONObject dataJson;
    private String opCreateTime;
//    private PopupWindowUtil popupWindowUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_center);
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }
        initweight();
        getData();
        // todo 版本更新是更改版本号
    }

    private void getData() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        try {
            packageInfo = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), 0);
            //当前的版本号
            localVersion = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version1 = "";
        String versionname = PackageUtils.getVersion(this);
//        int versionCode = PackageUtils.getVersionCode(this);
//        if (!TextUtils.isEmpty(versionname) && versionCode > 0) {
//            version1 = versionname + "." + versionCode;
//        }
        if (!TextUtils.isEmpty(versionname)) {
            version1 = versionname;
        }
        Log.e("version1", version1);
        Call<JsonObject> call = api.verifiedVersion(version1, "0");
        final String finalVersion = version1;
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (!jsonObject.isNull("data")) {
                        dataJson = jsonObject.getJSONObject("data");
                        opCreateTime = dataJson.getString("opCreateTime");
                        boolean result = dataJson.getBoolean("result");

                        if (result == true){
                            amount_convey.setVisibility(View.GONE);
                        }else{
                            amount_convey.setVisibility(View.VISIBLE);
                        }

                        version.setText("v" + finalVersion + "  ("+opCreateTime+")");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
//                popupWindowUtil.dismiss();
            }
        });
    }

    void initweight() {
        version = (TextView) findViewById(R.id.version);
        back = (ImageView) findViewById(R.id.back);
        telephone = (TextView) findViewById(R.id.telephone);
        item = (TextView) findViewById(R.id.item);
        amount_convey = (TextView) findViewById(R.id.amount_convey);
        introduction = (RelativeLayout) findViewById(R.id.introduction);
        feedback = (RelativeLayout) findViewById(R.id.feedback);
        update = (RelativeLayout) findViewById(R.id.update);
        information = (RelativeLayout) findViewById(R.id.information);
        instruction = (RelativeLayout) findViewById(R.id.instruction);

        introduction.setOnClickListener(this);
        feedback.setOnClickListener(this);
        update.setOnClickListener(this);
        information.setOnClickListener(this);
        instruction.setOnClickListener(this);
        item.setOnClickListener(this);
        telephone.setOnClickListener(this);
        back.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        Intent intent = null;

        switch (v.getId()) {
            case R.id.introduction:

                intent = new Intent(this, Version_Introduction_Activity.class);
                startActivity(intent);

                break;

            case R.id.feedback:

                intent = new Intent(this, Feedback_Activity.class);
                startActivity(intent);

                break;

            case R.id.update:
                intent = new Intent(this, Version_Update.class);
                startActivity(intent);
                break;

            case R.id.information:

                intent = new Intent(this, System_Information_Activity.class);
                startActivity(intent);
                break;

            case R.id.instruction:

                Toast.makeText(this,"暂未开发",Toast.LENGTH_SHORT).show();
//                intent = new Intent(this, State_Operation_Activity.class);
//                startActivity(intent);

                break;

            case R.id.item:

                intent = new Intent(this, ServiceItem_Activity.class);
                startActivity(intent);

                break;

            case R.id.telephone:

                // 在屏幕中央弹出popupwindow

                new DialogUtil(CenterActivity.this, new Summit2()).showConfirm("是否拨打服务电话", "请您选择是否拨打4006-970-960服务电话！我们的服务时间为周一至周五(早9：00-17：00)紧急/应急联系电话(24小时)听到语音转9,投诉举报转8.", "立即拨打", "点错了");

                break;

            case R.id.back:

                finish();

                break;

        }
    }

    private class Summit2 implements DialogUtil.OnClickListenner {

        @Override
        public void yesClick() {

            if (ContextCompat.checkSelfPermission(CenterActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CenterActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);

            } else {
                callPhone();
            }

        }

        @Override
        public void noClick() {

        }

        @Override
        public void onSingleClick() {

        }
    }


    // 拨打电话权限的申请
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    callPhone();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(CenterActivity.this, "您已拒绝了授权打电话权限,如需重新授权请到手机设置里更改", Toast.LENGTH_SHORT).show();

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                break;
            }
        }
    }


    //拨打电话操作
    public void callPhone() {
        Intent intent2 = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + "4006970960");
        intent2.setData(data);
        try {
            startActivity(intent2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
