package com.lanwei.governmentstar.activity.lll;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.utils.LogUtils;

//公文核稿\公文审阅
public class DocumentHGActivity extends DocumentBaseCActivity implements OnClickListener {
    private static final String TAG = DocumentHGActivity.class.getSimpleName();
    private ImageView ducument_sy_qianfa_image;
    private LinearLayout ducument_sy_qianfa;
    private PopupWindow popupWindow;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_documentsy;
    }

    @Override
    protected String getAction() {
            return "公文核稿";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate()");
        init();
    }

    private void init() {
        // TODO Auto-generated method stub
        ducument_sy_qianfa_image= (ImageView) findViewById(R.id.ducument_sy_qianfa_image);
        ducument_sy_qianfa= (LinearLayout) findViewById(R.id.ducument_sy_qianfa);
        ducument_sy_qianfa.setOnClickListener(this);
        getDate();
    }

    private void getDate() {
        more.setText("完成");
        title.setText("公文核稿");
        document_guild_edit.setText("请输入审阅意见...");
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        LogUtils.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        LogUtils.d(TAG, "onResume()");
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        LogUtils.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        LogUtils.d(TAG, "onDestroy()");
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        super.onClick(v);
        switch (v.getId()) {
            case R.id.more:{//完成
            }break;
            case R.id.ducument_sy_qianfa:{//默认签发
                WindowManager.LayoutParams lp2 = getWindow().getAttributes();
                lp2.alpha=(float) 0.8;
                getWindow().setAttributes(lp2);

                // 加载popupwindow的布局
                View view2=getLayoutInflater().inflate(R.layout.popwindow_sy,null);
                popupWindow=new PopupWindow(view2, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                // 初始化popupwindow的点击控件
                // 点击屏幕之外的区域可否让popupwindow消失
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams p = getWindow().getAttributes();
                        p.alpha=(float) 1;
                        getWindow().setAttributes(p);
                    }
                });
                int xy[]= new int[2];
                ducument_sy_qianfa_image.getLocationOnScreen(xy);
                // 设置popupwindow的显示位置
                int width =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                int height =View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
                view2.measure(width,height);
                int height2=view2.getMeasuredHeight();
                // 设置popupwindow的显示位置
                popupWindow.showAtLocation(ducument_sy_qianfa_image,Gravity.NO_GRAVITY,xy[0],xy[1]-height2-10);
            }break;
            default:
                break;
        }
    }

    /**
     * 接口处理
     */
    @Override
    protected void baseJsonNext(String response, String tag) {
        // TODO Auto-generated method stub
        super.baseJsonNext(response, tag);
        if (tag.equals(TAG + "xxx")) {

        }
    }
}
