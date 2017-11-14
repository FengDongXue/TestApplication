package com.lanwei.governmentstar.activity.lll;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.AddCalendar_Activity;
import com.lanwei.governmentstar.activity.ChooseReceiverNBActivity;

import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.LogUtils;
import com.lanwei.governmentstar.utils.ManagerUtils;
import com.lanwei.governmentstar.utils.PopupWindowUtil;


import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//拟办公文
public class DocumentToDoActivity extends DocumentBaseCActivity implements OnClickListener, DialogUtil.OnClickListenner{
    private static final String TAG = DocumentToDoActivity.class.getSimpleName();
    private SharedPreferences change_position;
    private SharedPreferences.Editor editor;
    private String state = "cb";
    private TextView name;
    private Boolean is_chengban = true;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_documenttodo;
    }

    @Override
    protected String getAction() {
        return "swcyNb";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate()");
        init();
        name = (TextView) findViewById(R.id.name);
        name.setText("轮阅选项");
    }

    private void init() {
        // TODO Auto-generated method stub
        getData();

    }

    private void getData() {
        more.setText("完成");
        title.setText("拟办公文");
        title.setVisibility(View.VISIBLE);

        document_guild_edit.setHint("请输入拟办意见...");
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
            case R.id.document_file_selectpeople:{//文件快速处理
//                Intent intent= new Intent(this,ChooseReceiverNBActivity.class);
//                intent.putExtra("title","文件快速处理");
//                intent.putExtra("action","swcyNbTree");
//                intent.putExtra("isNotAssist",isNotAssist);
//
//                if(isNotChoosed==1){
//                    intent.putExtra("opIds",opIds);
//                    intent.putExtra("opBsrId",opBsrId);
//                }else{
//                    intent.putExtra("opIds","");
//                    intent.putExtra("opBsrId","");
//                }
//
//                startActivityForResult(intent,10);

                // 弹出popupwindow前，调暗屏幕的透明度
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha=(float) 0.8;
                getWindow().setAttributes(lp);

                // 加载popupwindow的布局
                View view=getLayoutInflater().inflate(R.layout.importance_reminder,null ,false);
                popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);

                // 初始化popupwindow的点击控件
                TextView general=(TextView) view.findViewById(R.id.general);
                TextView very=(TextView) view.findViewById(R.id.very);

                general.setText("承办文件");
                very.setText("轮阅文件");
                very.setTextColor(getResources().getColor(R.color.special));

                if(is_chengban){
                    general.setTextColor(getResources().getColor(R.color.blue));
                }else{
                    very.setTextColor(getResources().getColor(R.color.blue));
                }

                general.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        state="cb";
                        popupWindow.dismiss();
                        name.setText("承办文件");
                        is_chengban=true;
                    }
                });


                very.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        state="ly";
                        name.setText("轮阅文件");
                        popupWindow.dismiss();
                        is_chengban=false;
                    }
                });

                // 点击屏幕之外的区域可否让popupwindow消失
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new PoponDismissListener());

                View rootview = LayoutInflater.from(this).inflate(R.layout.activity_documenttodo, null);
                // 设置popupwindow的显示位置
                popupWindow.showAtLocation(rootview, Gravity.BOTTOM,0,0);


            }break;
            case R.id.more:{//完成
                //判断意见框是否为null
                if(document_guild_edit.getText().toString().trim().equals("")){
                    ManagerUtils.showToast(this,"请填写意见");
                    return;
                }
                //new DialogUtil(DocumentToDoActivity.this, DocumentToDoActivity.this).showAlert("文件拟办已完成", "已完成拟办，该文件将在办理流程中继续办理，如需查看进程请在收文传阅首页列表中查看！", "知道了");

                //快速处理
                if (opIds != null && !opIds.equals("")) {
                    //对话框
                    new DialogUtil(DocumentToDoActivity.this, this).showConfirm("是否快速处理拟办工作？", "您是否快速处理拟办工作，将此文件继续进行办理？", "拟办完成", "我再看看");
                } else {
                    new DialogUtil(DocumentToDoActivity.this, this).showConfirm("是否完成文件拟办工作？", "您是否已经完成拟办，将此文件继续进行办理？", "拟办完成", "我再看看");
                }
            }
                break;
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
    //提交数据
    private void submitData() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
//        Call<JsonObject> call = api.swcyNbEdit("swcyNbEdit", uid, getIntent().getStringExtra("opId"), opIds, document_guild_edit.getText().toString(), "");
        Call<JsonObject> call = api.swcynbEdit(uid, getIntent().getStringExtra("opId"),state, opIds, document_guild_edit.getText().toString(), "");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                popupWindowUtil.dismiss();

                try {
                    JSONObject dataJson = new JSONObject(response.body().toString());
                    if (dataJson.get("data") != null) {
                        jsonObject= (JSONObject) dataJson.get("data");
                        boolean result = jsonObject.getBoolean("result");
                        if (result) {
                            //确定框
                            Log.e("成都v得分v天人合一图溃疡",document_guild_edit.getText().toString());
                            opIds=null;
                            // 收文传阅跳过来执行的方法体，记录提交成功的标记，以便切换到待办重新刷新一下数据。当然待办跳过来的话不会执行这个
                            change_position = getSharedPreferences("summit_position", 0);
                            if(change_position.getBoolean("allow",true)){

                                //记录提交成功，改变标记，以便切换到待办时，刷新一下数据，获取最新的数据（去掉已经办理的数据条目）
                                change_position = getSharedPreferences("summit_position", 0);
                                editor=change_position.edit();
                                editor.putBoolean("summit",true);
                                editor.commit();
                            }



                            new DialogUtil(DocumentToDoActivity.this, DocumentToDoActivity.this).showAlert("文件拟办已完成", "已完成拟办，该文件将在办理流程中继续办理，如需查看进程请在收文传阅首页列表中查看！", "知道了");
                            return;
                        }
                    }
                    Toast.makeText(DocumentToDoActivity.this,"该公文处理失败，请重新尝试！",Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                popupWindowUtil.dismiss();
                Toast.makeText(DocumentToDoActivity.this,"网络连接有错误",Toast.LENGTH_SHORT).show();
                Log.d("LOG", t.toString());
            }
        });
    }

    @Override
    public void yesClick() {
        popupWindowUtil = new PopupWindowUtil(DocumentToDoActivity.this, "提交中...");
        popupWindowUtil.show();
        submitData();
    }

    @Override
    public void noClick() {

    }

    @Override
    public void onSingleClick() {
        // 点击弹窗 知道了 的回调
        Intent in = new Intent();
        in.putExtra("opId", getIntent().getStringExtra("opId"));
        try {
            in.putExtra("opState", jsonObject.getString("opState"));
            in.putExtra("docStatus", jsonObject.getString("docStatus"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setResult(520, in);

        finish();
    }
}