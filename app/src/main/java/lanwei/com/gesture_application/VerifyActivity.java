package lanwei.com.gesture_application;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

import lanwei.com.gesture_application.http.HttpClient;
import lanwei.com.gesture_application.http.SafeTypoApi;
import lanwei.com.gesture_application.utils.SharedPreferencesUtil;
import lanwei.com.gesture_application.utils.SystemUtil;
import lanwei.com.gesture_application.view.Dialog01;
import lanwei.com.gesture_application.view.Dialog02;
import lanwei.com.gesture_application.view.StatusBarUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static lanwei.com.gesture_application.R.id.et_verification;


/**
 * Created by YS on 2017/3/8.
 */
public class VerifyActivity extends AutoLayoutActivity  implements View.OnClickListener{
    private static final int GET_CODE_SUCCES = 100;//获取验证码成功
    private static final int GET_CODE_FAIL = 101;//获取验证码失败

    private static final int KEEP_TIME_MINS = 102;//保持时间递减的状态码
    private static final int RESET_TIME = 103;//重置时间为60秒

    private static final int SUBMIT_CODE_SUCCES = 104;//校验验证码成功
    private static final int SUBMIT_CODE_FAIL = 105;//校验验证码失败

    private Dialog01 dialog01;
    private EditText etVerification;

    private TextView tvBind;
    private TextView tvResend;
    private int time = 120;

    private Dialog progressDialog;
    private Dialog02 dialog02;
    private TextView tvResend1;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case KEEP_TIME_MINS:
                    tvResend.setVisibility(View.GONE);
                    tvResend1.setVisibility(View.VISIBLE);
                    tvResend1.setText("重新发送验证码(" + (time--) + ")");
                    break;
                case RESET_TIME:
                    tvResend.setVisibility(View.VISIBLE);
                    tvResend1.setVisibility(View.GONE);
                    tvResend.setText("重新发送验证码");
                    time = 120;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.activity_verify);

        App app = (App) getApplication();//获取应用程序全局的实例引用
        app.activities.add(this);    //把当前Activity放入集合中

        tvResend = (TextView) findViewById(R.id.tv_resend);
        tvResend1 = (TextView) findViewById(R.id.tv_resend1);
        tvBind = (TextView) findViewById(R.id.tv_bind);
        ImageView back = (ImageView) findViewById(R.id.back_icon);
        TextView title = (TextView) findViewById(R.id.title_text);
        title.setText("短信验证");

        back.setVisibility(View.GONE);
        back.setOnClickListener(this);
        //设置立即绑定的点击事件
        tvBind.setOnClickListener(this);
        tvResend.setOnClickListener(this);

        a();
    }

    private void a() {
        tvResend.setVisibility(View.GONE);
        tvResend1.setVisibility(View.VISIBLE);
        //设置重新发送的点击事件
        new Thread() {
            @Override
            public void run() {
                //如果time的值大于0,则说明还有计数的时间
                while (time > 0) {
                    try {
                        Thread.sleep(999);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //发送一条消息,用于减少time的时间
                    handler.sendEmptyMessage(KEEP_TIME_MINS);
                }
                //重新下发验证码短信
                handler.sendEmptyMessage(RESET_TIME);

            }
        }.start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.tv_bind:
                etVerification = (EditText) findViewById(et_verification);
                String verification = etVerification.getText().toString();
                if (TextUtils.isEmpty(verification)) {
                    Toast.makeText(getApplicationContext(), "验证码不能为空", Toast.LENGTH_SHORT).show();
                } else {

                    show("验证短信中……");
                    validateMSM(verification);
                }
                break;
            case R.id.tv_resend:
                //设置重新发送验证码的点击事件
                dialog(); //如果重新发送验证码，则弹出对话框提示发送验证码
                break;
        }
    }

    /****
     * 对话框
     */
    private void dialog() {
        //获取手机号
        Intent getIntent = getIntent();
        String mobile = getIntent.getStringExtra("mobile");

        etVerification = (EditText) findViewById(et_verification);
        String verification1 = etVerification.getText().toString();

        dialog02 = new Dialog02(VerifyActivity.this);
        dialog02.setContent("您确定要将验证码发送到" + mobile + "该号码上？", Color.parseColor("#4f4f4f"));
        dialog02.setTitle("重新发送验证码", Color.parseColor("#00a7e4"));
        dialog02.setYesOnclickListener("确定", new Dialog02.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                show("重新发送短信中……");
                sendSMS();
            }
        });
        dialog02.setNoOnclickListener("暂不发送", new Dialog02.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                dialog02.dismiss();
            }
        });

        dialog02.show();
    }

    /****
     * 重新发送验证码
     */
    private void sendSMS() {
        SafeTypoApi api = HttpClient.getInstance().getSafeTypoApi();

        //获取手机号
        Intent getIntent = getIntent();
        String mobile = getIntent.getStringExtra("mobile");

        Call<JsonObject> call = api.sendSMS(mobile);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                dismiss();
                try {
                    //取得返回的数据
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    //判断是否验证登陆成功。如果不成功！
                    if (!jsonObject.getBoolean("data")) {
                        //则弹出提示框
                        Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //如果验证成功，则弹出对话框提示发送验证码
                    a();
                    dialog02.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismiss();
            }
        });
    }

    /****
     * 验证短信
     * @param code 验证码
     */
    private void validateMSM(String code) {
        //获取手机号
        Intent getIntent = getIntent();
        final String mobile = getIntent.getStringExtra("mobile");

        SafeTypoApi api = HttpClient.getInstance().getSafeTypoApi();

        String imei = SystemUtil.getIMEI(this);
        if (TextUtils.isEmpty(imei)) {
            Toast.makeText(VerifyActivity.this, "由于未能获取到查看您手机信息的权限，暂无法继续使用该功能，请在权限管理打开该权限", Toast.LENGTH_SHORT).show();
            return;
        }
        Call<JsonObject> call = api.validateMSM(mobile, code, imei, SystemUtil.getSystemModel());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("http返回", String.valueOf(response));
                dismiss();
                try {
                    //取得返回的数据
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    //判断是否验证登陆成功。如果不成功！
                    if (!jsonObject.getBoolean("result")) {
                        //则弹出提示框
                        Log.d("tusi", jsonObject.getString("message"));
                        Intent intent = new Intent(VerifyActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }

                    // Account bean = (Account) jsonObject.get("data");
                    Gson gson = new Gson();
                    SharedPreferencesUtil.saveString(VerifyActivity.this, "jsonStr", gson.toJson(response.body().get("data")));
                    Log.d("111", gson.toJson(response.body().get("data")));
                    showDialog();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dismiss();
            }
        });
    }

    private void showDialog() {
        dialog01 = new Dialog01(VerifyActivity.this);
        dialog01.setTitle("您已绑定成功", Color.parseColor("#00a7e4"));
        dialog01.setContent("您已绑定安全宝，登录系统时可使用安全宝直接扫描登录或输入动态验证码登录", Color.parseColor("#4b4b4b"));
        dialog01.setSingleOnclickListener("立即使用", new Dialog01.onsingleOnclickListener() {
            @Override
            public void onSingleClick() {
                Intent intent = new Intent(VerifyActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                dialog01.dismiss();
            }
        });

        dialog01.show();
    }

    public void show(String message) {
        progressDialog = new Dialog(this, R.style.progress_dialog);
        progressDialog.setContentView(R.layout.dialog);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) progressDialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText(message);
        progressDialog.show();
    }

    public void dismiss() {
        progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App app = (App) getApplication();//获取应用程序全局的实例引用
        app.activities.remove(this); //把当前Activity从集合中移除
    }
}
