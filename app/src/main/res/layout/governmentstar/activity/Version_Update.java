package com.lanwei.governmentstar.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.zwyx.CheckMail;
import com.lanwei.governmentstar.bean.Content;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.receiver.DownloadCompleteReceiver;
import com.lanwei.governmentstar.utils.Constant;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.DownloadUtil;
import com.lanwei.governmentstar.utils.FileUtils;
import com.lanwei.governmentstar.utils.PackageUtils;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.utils.SharedPreferencesUtil;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/23.
 */

@SuppressLint("NewApi")
public class Version_Update extends AutoLayoutActivity implements View.OnClickListener, DialogUtil.OnClickListenner {


    private ImageView back;
    private Button update;
    private ArrayList<Content> data = null;
    private WebView content;
    private LinearLayout condition;
    private PopupWindowUtil popupWindowUtil;
    private JSONObject dataJson;
    private DownloadManager manager;
    private String appUrl;
    private String opCreateTime;
    private String explain;
    private PackageInfo packageInfo;
    private int localVersion;
    private TextView version;
    private String version1;
    public boolean result;
    private DownloadCompleteReceiver down;
    private TextView isnew;
    private String version2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.version_update);
        back = (ImageView) findViewById(R.id.back);
        update = (Button) findViewById(R.id.update);
        isnew = (TextView) findViewById(R.id.isnew);

        content = (WebView) findViewById(R.id.content);
        version = (TextView) findViewById(R.id.version);

        back.setOnClickListener(this);
        update.setOnClickListener(this);

        // todo  网络获取内容
        getData();
    }


    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:

                finish();

                break;

            case R.id.update:
                // todo 网络请求update新版本

                if (result == false) {
                    update.setEnabled(false);
//                    if (FileUtils.checkFileExists(appUrl)) {
//                        File externalStorageDirectory = Environment.getExternalStorageDirectory();
//                        FileUtils.openFile(externalStorageDirectory.getPath() + "/Download/" + appUrl, CheckMail.this);
//                    } else {
                        new DialogUtil(Version_Update.this, this).showConfirm("版本更新", "您确定要升级吗？", "确定", "取消");
//                    }
                    return;
                } else {
                    //不需要跟新
                    update.setText("已是最新版本");
                    Toast.makeText(Version_Update.this, "当前版本已是最新版本", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    @Override
    public void yesClick() {
        update.setVisibility(View.GONE);
        if (ActivityCompat.checkSelfPermission(Version_Update.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(Version_Update.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 30);
            return;
        }
        try {
            downLoading();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "如果您拒绝使用读写权限，您将无法进行下载更新。", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 30: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意，可以做你要做的事情了。
                } else {
                    // 权限被用户拒绝了，可以提示用户,关闭界面等等。
                    Toast.makeText(this, "如果您拒绝使用读写权限，您将无法进行下载更新。", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            }
        }
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
        if (!TextUtils.isEmpty(versionname)) {
            version1 = versionname;
        }
        Log.e("version1", version1);
        Call<JsonObject> call = api.verifiedVersion(version1, "1");
        call.enqueue(new Callback<JsonObject>() {

            /**
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (!jsonObject.isNull("data")) {
                        dataJson = jsonObject.getJSONObject("data");
                        Log.e("version1", dataJson.toString());
                        appUrl = dataJson.getString("appUrl");
                        opCreateTime = dataJson.getString("opCreateTime");
                        explain = dataJson.getString("explain");
                        version2 = dataJson.getString("version");
                        SharedPreferencesUtil.saveData(Version_Update.this, Constant.VERSION, Version_Update.this.version2);    //将获取到的版本保存

                        boolean result = dataJson.getBoolean("result");
                        version.setText("v" + version2 + "  (" + opCreateTime + ")");
                        content.loadDataWithBaseURL(null, explain, "text/html", "UTF-8", null);
                        Version_Update.this.result = result;

                        if (result == false) {
                            update.setVisibility(View.VISIBLE);
                            isnew.setVisibility(View.VISIBLE);
                        } else {
                            //不需要跟新
                            update.setVisibility(View.VISIBLE);
                            update.setEnabled(false);
                            isnew.setVisibility(View.GONE);
                            update.setText("已是最新版本");
                        }
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

    private long downloadId;
    static final String DOWNLOAD_FILE_NAME = "zhengwuzhixing.apk";

    /**
     *
     */
    private void downLoading() {
        //下载逻辑
//        if (result == false) {
////            可以下载
//            update.setVisibility(View.VISIBLE);
//            isnew.setVisibility(View.VISIBLE);

        update.setEnabled(false);
        //下载逻辑
        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);//获取系统下载的service
        DownloadUtil.startDownload(manager, Version_Update.this, appUrl, "政务之星", "gom_oa.apk", "正在下载...");

    }

    @Override
    public void noClick() {

    }

    @Override
    public void onSingleClick() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
