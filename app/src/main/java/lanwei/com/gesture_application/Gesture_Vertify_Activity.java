package lanwei.com.gesture_application;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import lanwei.com.gesture_application.http.HttpClient;
import lanwei.com.gesture_application.http.SafeTypoApi;
import lanwei.com.gesture_application.utils.SharedPreferencesUtil;
import lanwei.com.gesture_application.view.Dialog02;
import lanwei.com.gesture_application.view.GestureContentView;
import lanwei.com.gesture_application.view.GestureDrawline;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/10/20.
 */

public class Gesture_Vertify_Activity extends AppCompatActivity {


    private FrameLayout gesture_container;
    private GestureContentView mGestureContentView;
    private TextView remind;
    private TextView warn;
    private TextView forget_gesture;
    private int failCount = 5;
    private boolean isCancel = false;
    private Dialog02 dialog02;
    private Dialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vertify_gesture_layout);

        gesture_container = (FrameLayout) findViewById(R.id.gesture_container);
        remind = (TextView) findViewById(R.id.remind);
        warn = (TextView) findViewById(R.id.warn);
        forget_gesture = (TextView) findViewById(R.id.forget_gesture);
        forget_gesture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog();
            }
        });

        // 初始化一个显示各个点的viewGroup
        mGestureContentView = new GestureContentView(this, true, SharedPreferencesUtil.getString(this, "lockkey", ""),
                new GestureDrawline.GestureCallBack() {

                    @Override
                    public void onGestureCodeInput(String inputCode) {

                    }

                    @Override
                    public void checkedSuccess() {
                        if(failCount == 0){
                            mGestureContentView.clearDrawlineState(1000L);
                            Toast.makeText(Gesture_Vertify_Activity.this,"密码错过5次，请点击忘记手势进行验证",Toast.LENGTH_SHORT).show();
                        }else{

                            mGestureContentView.clearDrawlineState(0L);
                            //// TODO: 2017/7/18    验证成功后的逻辑
//                            SharedPreferencesUtil.savaInt(GestureVerifyActivity.this, "faildCount", 1);
                            SharedPreferencesUtil.savaInt(Gesture_Vertify_Activity.this, "failCount", 5);
                            Log.e("手势锁", "gesture");
                            if (isCancel) {
                                Toast.makeText(Gesture_Vertify_Activity.this, "取消成功", Toast.LENGTH_SHORT).show();
                                SharedPreferencesUtil.saveBoolean(Gesture_Vertify_Activity.this, "isLock", false);  //不加锁
                                finish();
                            } else {
                                Toast.makeText(Gesture_Vertify_Activity.this, "密码正确", Toast.LENGTH_SHORT).show();
//                                SharedPreferencesUtil.saveBoolean(getApplicationContext(), "isCheck", false);
//                            Intent intent = new Intent(Gesture_Vertify_Activity.this, TokenActivity.class);
//                            finish();
//                            startActivity(intent);
                            }

                        }

                    }

                    @Override
                    public void checkedFail() {
                        Log.e("手势失败","手势失败手势失败手势失败手势失败");
                        failCount -= 1;
                        if (failCount <= 0)
                            failCount = 0;
                        SharedPreferencesUtil.savaInt(Gesture_Vertify_Activity.this, "failCount", failCount);
                        if (failCount < 0) {
//                            //如果大于5次，则重新登录
//                            Intent intent = new Intent(Gesture_Vertify_Activity.this, ForgetPassword.class);
//                            intent.putExtra("mobile", mobile);
//                            finish();
//                            startActivity(intent);

                            Log.e("登录成功","登录成功登录成功登录成功");

                        } else {
                            mGestureContentView.clearDrawlineState(1000L);
                            remind.setVisibility(View.VISIBLE);
                            remind.setText(Html
                                    .fromHtml("<font color='#f10707'>密码错误，您还有</font>" + failCount + "<font color='#f10707'>次机会。</font>"));
                            remind.setTextColor(Color.parseColor("#f10707"));
                            warn.setVisibility(View.VISIBLE);
                            // 左右移动动画
                            Animation shakeAnimation = AnimationUtils.loadAnimation(Gesture_Vertify_Activity.this, R.anim.shake);
                            remind.startAnimation(shakeAnimation);
                        }
                    }
                });
        // 设置手势解锁显示到哪个布局里面
        FrameLayout mGestureContainer = this.gesture_container;
        mGestureContentView.setParentView(gesture_container);

    }


    /****
     * 对话框
     */
    private void dialog() {
        dialog02 = new Dialog02(Gesture_Vertify_Activity.this);
        dialog02.setContent("您确定要将短信验证码发送到" + "18322595845" + "号码上吗？", Color.parseColor("#4f4f4f"));
        dialog02.setTitle("发送短信验证码", Color.parseColor("#00a7e4"));
        dialog02.setYesOnclickListener("发送", new Dialog02.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                show("发送短信中……");
                // 发送验证码
//                sendSMS();
                startActivity(new Intent(Gesture_Vertify_Activity.this,VerifyActivity.class));
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


    /****
     * 发送验证码
     */
    private void sendSMS() {
        SafeTypoApi api = HttpClient.getInstance().getSafeTypoApi();
        Call<JsonObject> call = api.sendSMS("18322595845");
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
                    }else
                    //如果验证成功，则弹出对话框提示发送验证码
//                    Intent intent = new Intent(Gesture_Vertify_Activity.this, VerifyActivity.class);
//                    intent.putExtra("mobile", "18322595845");
//                    SharedPreferencesUtil.saveString(Gesture_Vertify_Activity.this, "mobile", "18322595845");         //保存手机的值
//                    finish();
//                    startActivity(intent);
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

    public void dismiss() {
        progressDialog.dismiss();
    }

}
