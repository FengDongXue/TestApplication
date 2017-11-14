package com.lanwei.governmentstar.view.wheel;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.utils.Constant;

/**
 * @author shangjy 选择器对话框
 * @ClassName: WheelDialog
 * @date 2015-5-18 下午3:14:50
 */
public class WheelDialog implements OnClickListener, OnWheelChangedListener {
    private Context context;
    private Dialog dialog;
    private Display display;
    private Handler mHandler;
    private WheelView mWheelView;
    private TextView btnConfirmBtn;
    private String[] mDatas;
    private String mCurrentName;
    private TextView btnCancleBtn;
    private String currentName;

    public String getmCurrentName() {
        return mCurrentName;
    }

    public String getmCurrentLocation() {
        return String.valueOf(mWheelView.getCurrentItem());
    }

    /**
     * @param context
     * @param handler
     * @param datas   要显示的数据
     */
    public WheelDialog(Context context, Handler handler, String[] datas, String currentName) {
        this.context = context;
        this.mHandler = handler;
        this.mDatas = datas;
        this.currentName = currentName;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }


    public WheelDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_wheel_city, null);
        mWheelView = (WheelView) view.findViewById(R.id.dialog_wheelview);

        btnConfirmBtn = (TextView) view.findViewById(R.id.btn_confirm);
        btnConfirmBtn.setOnClickListener(this);
        btnCancleBtn = (TextView) view
                .findViewById(R.id.dialog_wheel_cancle);
        btnCancleBtn.setOnClickListener(this);

        // 添加change事件
        mWheelView.addChangingListener(this);

        mWheelView
                .setViewAdapter(new ArrayWheelAdapter<String>(context, mDatas));
        // 设置可见条目数量
        mWheelView.setVisibleItems(7);
        // mViewDistrict.setVisibleItems(7);
        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth());

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);
        return this;
    }

    public WheelDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public WheelDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        dialog.show();
        if (!TextUtils.isEmpty(currentName)) {
            boolean hasValue = false;
            for (int i = 0; i < mDatas.length; i++) {
                if (mDatas[i].equals(currentName)) {
                    mWheelView.setCurrentItem(i);
                    mCurrentName = mDatas[i];
                    hasValue = true;
                    break;
                }
            }
            if (!hasValue) {
                mWheelView.setCurrentItem(0);
                mCurrentName = mDatas[0];
            }
        } else {
            mWheelView.setCurrentItem(0);
            mCurrentName = mDatas[0];
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                Message msg = mHandler.obtainMessage();
                msg.what = Constant.WHEEL_DIALOG_RESULT;
                mHandler.sendMessage(msg);
                dialog.cancel();
                break;
            case R.id.dialog_wheel_cancle:
                dialog.cancel();
                break;
            default:
                break;
        }

    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mWheelView) {
            mCurrentName = mDatas[newValue];
        }
    }

    public void setCurrentName(String currentName) {
        this.currentName = currentName;
    }

}
