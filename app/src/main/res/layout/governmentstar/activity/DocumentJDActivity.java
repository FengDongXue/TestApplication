package com.lanwei.governmentstar.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.gwnz.DocumentBaseActivity;
import com.lanwei.governmentstar.utils.LogUtils;
import com.lanwei.governmentstar.utils.ManagerUtils;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/10.
 */

public class DocumentJDActivity extends DocumentBaseActivity implements View.OnClickListener{

    private static final String TAG = DocumentJDActivity.class.getSimpleName();
    private ImageView ducument_sy_qianfa_image;
    private LinearLayout ducument_sy_qianfa;
    private PopupWindow popupWindow;
    private int i=0;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_documentsy;
    }

    @Override
    protected String getAction() {
        return  "gwnzSy";  // todo  校对action
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

        getDate();
    }

    private void getDate() {
        more.setText("完成");
        title.setText("公文校对");
        lnl_shenhe.setVisibility(View.INVISIBLE);
        document_guild_edit.setHint("请输入校对意见...");
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
            case R.id.document_file_look:{//查看流程
                Intent intent= new Intent(this,Process2_Activity.class);
                intent.putExtra("opId", getIntent().getStringExtra("opId"));
                startActivity(intent);
            }break;
            case R.id.more:{//完成
                if(document_guild_edit.getText().toString().equals("")){
                    ManagerUtils.showToast(this,"请填写意见");
                    return;
                }

                //  todo 提交校对
//                GovernmentApi api= HttpClient.getInstance().getGovernmentApi();
//                Call<Logging_Success> call= api.gwnzSyEdit(getIntent().getStringExtra("opId"),uid,
//                        document_guild_edit.getText().toString(),i+"");
//                call.enqueue(new Callback<Logging_Success>() {
//                    @Override
//                    public void onResponse(Call<Logging_Success> call, Response<Logging_Success> response) {
//                        if(response.body().getData()==hardwork){
//                            Toast.makeText(DocumentJDActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
//                        }else{
//                            Gson gson = new Gson();
//                            PreferencesManager.getInstance(DocumentJDActivity.this, "accountBean").put("jsonStr", gson.toJson(response.body()));
//                            Logging_Success.Data data = response.body().getData();
//                            String opId = data.getOpId();
//                            String dptId = data.getAccountDeptId();
//
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Logging_Success> call, Throwable t) {
//                        Toast.makeText(DocumentJDActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
//                        Log.d("LOG", t.toString());
//                    }
//                });
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
                popupWindow.showAtLocation(ducument_sy_qianfa_image, Gravity.NO_GRAVITY,xy[0],xy[1]-height2-10);
            }break;
            case R.id.popwindow_syk:{//快速处理
                popupWindow.dismiss();
                i=1;
            }break;
            case R.id.popwindow_sym:{//默认处理
                popupWindow.dismiss();
                i=0;
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
