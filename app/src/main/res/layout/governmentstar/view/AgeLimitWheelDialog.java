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
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.utils.Constant;

import java.util.ArrayList;

/**
 * 双列选择器  不联动
 */
public class AgeLimitWheelDialog implements OnClickListener, OnWheelChangedListener {
    private Context context;
    private Dialog dialog;
    private Display display;
    private Handler mHandler;
    private WheelView mWheelView;
    private TextView btnConfirmBtn;
    private String mCurrentName;
    private TextView btnCancleBtn;
    private WheelView mDayView;
    private String[] mYear;
    private String[] mDay;


    public String getmCurrentName() {
        if (!TextUtils.isEmpty(mCurrentName)) {
            return mCurrentName;
        } else {
            return "无工作经验";
        }
    }

    /**
     * @param context
     * @param handler
     */
    public AgeLimitWheelDialog(Context context, Handler handler) {
        this.context = context;
        this.mHandler = handler;

        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }


    public AgeLimitWheelDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_wheel_city, null);
        mWheelView = (WheelView) view.findViewById(R.id.dialog_wheelview);
        mDayView = (WheelView) view.findViewById(R.id.dialog_wheelview2);
        btnConfirmBtn = (TextView) view.findViewById(R.id.btn_confirm);
        mDayView.setVisibility(View.VISIBLE);
        btnConfirmBtn.setOnClickListener(this);
        btnCancleBtn = (TextView) view
                .findViewById(R.id.dialog_wheel_cancle);
        btnCancleBtn.setOnClickListener(this);

        // 添加change事件
        mWheelView.addChangingListener(this);
        mDayView.addChangingListener(this);
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
        initData();
        return this;
    }

    ArrayList<String> mYearList;
    ArrayList<String> mDayList;

    private void initData() {
        mYearList = new ArrayList<>();
        mDayList = new ArrayList<>();
        for (int i = 0; i < 61; i++) {
            mYearList.add(String.valueOf(i));
        }
        for (int i = 0; i < 12; i++) {
            mDayList.add(String.valueOf(i));
        }
        mYear = new String[mYearList.size()];
        for (int i = 0; i < mYearList.size(); i++) {
            mYear[i] = mYearList.get(i) + "年";

        }
        mDay = new String[mDayList.size()];
        for (int i = 0; i < mDayList.size(); i++) {
            mDay[i] = mDayList.get(i) + "个月";

        }

        mWheelView.setViewAdapter(new ArrayWheelAdapter<String>(context, mYear));
        mWheelView.setVisibleItems(7);

        mDayView.setViewAdapter(new ArrayWheelAdapter<String>(context, mDay));
        mDayView.setVisibleItems(7);
    }

    public AgeLimitWheelDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public AgeLimitWheelDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void show() {
        dialog.show();
        mDayView.setCurrentItem(0);
        mWheelView.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                Message msg = mHandler.obtainMessage();
                msg.what = Constant.WHEEL_DIALOGS_RESULT;
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

    String mYearName = "0年";
    String mDayName = "0个月";

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {

        if (wheel == mWheelView) {
            mYearName = mYear[newValue];
        }

        if (wheel == mDayView) {
            mDayName = mDay[newValue];
        }

        if (mYearName.equals("0年") && !mDayName.equals("0个月")) {
            mCurrentName = mDayName;
        } else if (!mYearName.equals("0年") && mDayName.equals("0个月")) {
            mCurrentName = mYearName;
        } else if (!mYearName.equals("0年") && !mDayName.equals("0个月")) {
            mCurrentName = mYearName + mDayName;
        } else {
            mCurrentName = "无工作经验";
        }

    }
}
