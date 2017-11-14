package com.lanwei.governmentstar.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lanwei.governmentstar.R;


/**
 * @ClassName: DialogCarmera
 * @Description: TODO(底部对话框)
 * @date 2015-5-18 下午3:14:50
 */
public class DialogCarmera {
    private Context context;
    private Dialog dialog;
    private Display display;
    private TextView take_photo;
    private TextView album;
    private TextView cancel;
    private OnSheetItemClickListener listner;

    public DialogCarmera(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public DialogCarmera builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_camera, null);

        // 设置Dialog最小宽度为屏幕宽度
        view.setMinimumWidth(display.getWidth());

        take_photo = (TextView) view.findViewById(R.id.take_photo);
        album = (TextView) view.findViewById(R.id.album);
        cancel = (TextView) view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        take_photo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onTakePhoto();
            }
        });
        album.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listner.onAlbum();
            }
        });
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.LEFT | Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.x = 0;
        lp.y = 0;
        dialogWindow.setAttributes(lp);

        dialog.dismiss();
        return this;
    }

    public DialogCarmera setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void setOnClick(OnSheetItemClickListener listener) {
        this.listner = listener;
    }

    public void dismiss() {
        dialog.dismiss();
    }

    public void show() {
        dialog.show();
    }

    public interface OnSheetItemClickListener {
        void onTakePhoto();

        void onAlbum();
    }
}
