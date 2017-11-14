package lanwei.com.gesture_application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import lanwei.com.gesture_application.utils.SharedPreferencesUtil;
import lanwei.com.gesture_application.view.GestureContentView;
import lanwei.com.gesture_application.view.GestureDrawline;
import lanwei.com.gesture_application.view.LockIndicator;

/**
 * Created by 蓝威科技-技术开发1 on 2017/11/6.
 */

public class Gesture_Input_Activity extends AppCompatActivity {


    private FrameLayout container;
    private TextView mTextTip;
    private TextView mTextReset;
    private GestureContentView mGestureContentView;

    private LockIndicator mLockIndicator;

    private boolean mIsFirstInput = true;
    private String mFirstPassword = null;
    private boolean isLock = true;
    private String mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesture_layout);

        container = (FrameLayout) findViewById(R.id.container);
        mLockIndicator = (LockIndicator) findViewById(R.id.lock_indicator);
        mTextTip = (TextView) findViewById(R.id.mTextTip);
        mTextReset = (TextView) findViewById(R.id.text_reset);

        // 初始化一个显示各个点的viewGroup
        mGestureContentView = new GestureContentView(this, false, "", new GestureDrawline.GestureCallBack() {
            @Override
            public void onGestureCodeInput(final String inputCode) {
                if (!isInputPassValidate(inputCode)) {
                    mTextTip.setText(Html.fromHtml("<font color='#f10707'>最少链接3个点, 请重新输入</font>"));
                    mGestureContentView.clearDrawlineState(0L);
                    return;
                }

                if (mIsFirstInput) {
                    mFirstPassword = inputCode;
                    updateCodeList(inputCode);
                    mGestureContentView.clearDrawlineState(0L);
                    mTextTip.setText("再次确认图案");
                    mTextReset.setClickable(true);
                    if (!isLock) {
                        mTextReset.setText("重新设置手势密码");
                    }
                } else {
                    if (inputCode.equals(mFirstPassword)) {
                        Toast.makeText(Gesture_Input_Activity.this, "设置成功", Toast.LENGTH_SHORT).show();
                        Toast.makeText(Gesture_Input_Activity.this, mFirstPassword, Toast.LENGTH_SHORT).show();
                        mGestureContentView.clearDrawlineState(0L);
                        SharedPreferencesUtil.savaInt(Gesture_Input_Activity.this, "failCount", 5);
                        SharedPreferencesUtil.saveBoolean(Gesture_Input_Activity.this, "isLock", true);         //保存是否加锁
                        SharedPreferencesUtil.saveString(Gesture_Input_Activity.this, "lockkey", mFirstPassword);   //保存手势密码

                        startActivity(new Intent(Gesture_Input_Activity.this,Gesture_Vertify_Activity.class));
                        finish();
                    } else {
                        mTextTip.setText(Html.fromHtml("<font color='#f10707'>与上一次绘制不一致，请重新绘制确认</font>"));
                        // 左右移动动画
                        Animation shakeAnimation = AnimationUtils.loadAnimation(Gesture_Input_Activity.this, R.anim.shake);
                        mTextTip.startAnimation(shakeAnimation);
                        // 保持绘制的线，1秒后清除
                        mGestureContentView.clearDrawlineState(1000L);
                    }
                }
                mIsFirstInput = false;
            }

            @Override
            public void checkedSuccess() {





            }

            @Override
            public void checkedFail() {

            }
        });

//        container.addView(mGestureContentView);
        mGestureContentView.setParentView(container);
        updateCodeList("");
    }



    private boolean isInputPassValidate(String inputPassword) {
        if (TextUtils.isEmpty(inputPassword) || inputPassword.length() < 3) {
            return false;
        }
        return true;
    }

    private void updateCodeList(String inputCode) {
        // 更新选择的图案
        mLockIndicator.setPath(inputCode);
    }




}
