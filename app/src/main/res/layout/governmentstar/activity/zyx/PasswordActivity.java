package com.lanwei.governmentstar.activity.zyx;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.CallBackAdapter;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.Constant;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.utils.SharedPreferencesUtil;
import com.lanwei.governmentstar.view.Dialog02;
import com.lanwei.governmentstar.view.StatusBarUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 蓝威科技—技术部2 on 2017/3/22.
 */

public class PasswordActivity extends BaseActivity {

    private String oldPwd;
    private String newPwd;
    private String qrnewPwd;
    private String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
        setContentView(R.layout.acitivity_password);

        final TextView title = (TextView) findViewById(R.id.tv_address);
        ImageView back = (ImageView) findViewById(R.id.back);
        ImageView icon = (ImageView) findViewById(R.id.iv_contacts);
        TextView apl = (TextView) findViewById(R.id.tv_apl);
        final EditText et_oldpwd = (EditText) findViewById(R.id.oldpwd);
        final EditText et_newpwd = (EditText) findViewById(R.id.newpwd);
        final EditText qr_newpwd = (EditText) findViewById(R.id.qrnewpwd);
        title.setVisibility(View.VISIBLE);

//        Intent intent = getIntent();
//        String message = intent.getStringExtra("message");
//        getData(message);

        password = PreferencesManager.getInstance(this).get(Constant.PASSWORD);  //登录系统保存的密码

        title.setText("登录密码");
        back.setVisibility(View.VISIBLE);
        icon.setVisibility(View.GONE);
        apl.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        apl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPwd = et_oldpwd.getText().toString();
                newPwd = et_newpwd.getText().toString();
                qrnewPwd = qr_newpwd.getText().toString();
                if (TextUtils.isEmpty(oldPwd)) {
                    Toast.makeText(PasswordActivity.this, "旧密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (!oldPwd.equals(password)) {
//                    Toast.makeText(PasswordActivity.this, "原密码输入不正确，请重新输入！", Toast.LENGTH_SHORT).show();
//                    et_oldpwd.setText("");
//                    return;
//                }
                if (TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(qrnewPwd)) {
                    Toast.makeText(PasswordActivity.this, "新密码不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (oldPwd.equals(newPwd) && newPwd.equals(qrnewPwd)) {
                    Toast.makeText(PasswordActivity.this, "新密码不能和原密码一致，请重新输入！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!(newPwd.equals(qrnewPwd))) {
                    Toast.makeText(PasswordActivity.this, "两次密码输入不一致，请重新输入！", Toast.LENGTH_SHORT).show();
                    return;
                }


                final Dialog02 dialog02 = new Dialog02(PasswordActivity.this);

                dialog02.setContent("您确定要修改密码吗？", Color.parseColor("#4f4f4f"));
                dialog02.setTitle("修改密码", Color.parseColor("#5184c3"));
                dialog02.setLeftBtn(R.drawable.select_button_left, Color.WHITE);
                dialog02.setRightBtn(R.drawable.select_button_right, Color.WHITE);
                dialog02.setYesOnclickListener("确定", new Dialog02.onYesOnclickListener() {
                    @Override
                    public void onYesClick() {
//                      String opid = (String) SharedPreferencesUtil.getData(PasswordActivity.this, "login", "6991ef41-594b-489a-85f2-af4251b4c7ae");
                        String opid = new GetAccount(PasswordActivity.this).opId();
                        Log.e("opid", opid);

                        if (opid != null) {
                            getData(opid, oldPwd, newPwd);  // TODO:再点确定修改密码的时候请求数据  修改密码    参1：用户的opid 参2：旧密码 参3：新密码
                        }

                        dialog02.dismiss();
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
            }
        });
    }

    /**
     * 展示数据
     *
     * @param opid
     */
    private void getData(String opid, String oldPwd, String newPwd) {
        //获取全部机关的数据
        RetrofitHelper.getInstance().getPwdInfo(opid, oldPwd, newPwd, new CallBackYSAdapter() {


            @Override
            protected void showErrorMessage(String message) {
                Toast.makeText(PasswordActivity.this, "网络异常，请稍候再试", Toast.LENGTH_SHORT).show();
            }

            @Override
            protected void parseJson(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data.toString());
                    String message = jsonObject.getString("message");
                    Toast.makeText(PasswordActivity.this, message, Toast.LENGTH_SHORT).show();
                    if (jsonObject.getBoolean("data")) {
                        PasswordActivity.this.finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });

    }


}
