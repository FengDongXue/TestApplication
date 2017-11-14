package com.lanwei.governmentstar.view;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.lanwei.governmentstar.R;

/**
 * 
 * @ClassName: AlertDialog
 * @Description: TODO(加载中对话框)
 * @author shangjy
 * @date 2015年6月9日 下午3:02:42
 *
 */
public class LoadingDialog {
	private Context context;
	private Dialog dialog;
	private Display display;
	private TextView txt_msg;
	private View lLayout_bg;

	public LoadingDialog(Context context) {
		this.context = context;
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		display = windowManager.getDefaultDisplay();
		builder();
	}

	public LoadingDialog builder() {
		// 获取Dialog布局
		View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading_layout, null);

		txt_msg = (TextView) view.findViewById(R.id.loading_text_tv);

		// 定义Dialog布局和参数
		dialog = new Dialog(context, R.style.DialogStyle);
		dialog.setContentView(view);
		dialog.setCanceledOnTouchOutside(false);
		// 调整dialog背景大小
		view.setLayoutParams(
				new FrameLayout.LayoutParams((int) (display.getWidth() * 0.55), LayoutParams.WRAP_CONTENT));

		return this;
	}

	public void setMessage(int msgId) {
		if (msgId > 0) {
			setMessage(context.getString(msgId));
		}
	}

	public void setMessage(String msg) {
		if (!TextUtils.isEmpty(msg)) {
			txt_msg.setVisibility(View.VISIBLE);
			txt_msg.setText(msg);
		}
	}

	public void show() {
		dialog.show();
	}

	public void dismiss() {
		try {
			dialog.dismiss();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isShowing() {

		if (dialog == null) {
			return false;
		}
		return dialog.isShowing();
	}

	public void isHandleKey(final boolean isHandleKey) {
		dialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (isHandleKey)
					return true;
				return false;
			}
		});
	}
}
