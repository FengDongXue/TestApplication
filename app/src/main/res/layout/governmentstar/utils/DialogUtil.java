package com.lanwei.governmentstar.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.Window;
import android.view.WindowManager;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.view.Dialog01;
import com.lanwei.governmentstar.view.Dialog02;

/**
 * Created by Administrator on 2017/4/11.
 */

public class DialogUtil {
    private Activity activity;
    private OnClickListenner listenner;
    private String leftButText = "确定";
    private String rightButText = "取消";
    private boolean result = false;

    public DialogUtil(Activity activity, String title, String content) {
        this.activity = activity;

    }

    public DialogUtil(Activity activity, OnClickListenner listenner) {
        this.activity = activity;

        this.listenner = listenner;
    }

    public DialogUtil(Activity activity, OnClickListenner listenner, boolean result) {
        this.activity = activity;
        this.result = result;
        this.listenner = listenner;
    }

    /****
     *
     * @param title 标题
     * @param content 内容
     * @param leftButText 左边按钮文字
     * @param rightButText 右边按钮文字
     */
    public void showConfirm(String title, String content, String leftButText, String rightButText) {
        if (leftButText != null && !leftButText.equals("")) {
            this.leftButText = leftButText;
        }
        if (rightButText != null && !rightButText.equals("")) {
            this.rightButText = rightButText;
        }

        final Dialog02 dialog02 = new Dialog02(activity);
        if (result) {
            dialog02.setContent(content, Color.parseColor("#4f4f4f"));
        } else {
            dialog02.setContent(content, Color.parseColor("#4f4f4f"));
        }

        dialog02.setTitle(title, Color.parseColor("#00a7e4"));
        dialog02.setYesOnclickListener(this.leftButText, new Dialog02.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                dialog02.dismiss();
                //显示加载框
                if (listenner != null) {
                    listenner.yesClick();
                }
            }
        });
        dialog02.setNoOnclickListener(this.rightButText, new Dialog02.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                dialog02.dismiss();
            }
        });


        dialog02.show();
    }

    //提示框（一个按钮）
    public void showAlert(String title, String content, String butText) {
        if (butText == null || butText.equals("")) {
            butText = "确定";
        }
        final Dialog01 dialog01 = new Dialog01(activity);
        dialog01.setTitle(title, Color.parseColor("#00a7e4"));
        dialog01.setContent(content, Color.parseColor("#4b4b4b"));
        dialog01.setSingleOnclickListener(butText, new Dialog01.onsingleOnclickListener() {
            @Override
            public void onSingleClick() {
                dialog01.dismiss();
                if (listenner != null) {
                    listenner.onSingleClick();
                }
            }
        });

        dialog01.show();
    }

    public interface OnClickListenner {
        void yesClick();

        void noClick();

        void onSingleClick();
    }


}
