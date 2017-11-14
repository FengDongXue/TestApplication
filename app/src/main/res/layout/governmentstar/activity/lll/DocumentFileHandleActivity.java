package com.lanwei.governmentstar.activity.lll;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.ChooseReceiverNBActivity;
import com.lanwei.governmentstar.activity.ChooseReceivers_Banli_Activity;
import com.lanwei.governmentstar.activity.DocumentOpinionActivity2;
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

//文件办理
public class DocumentFileHandleActivity extends DocumentBaseCActivity implements OnClickListener,DialogUtil.OnClickListenner {
    private static final String TAG = DocumentFileHandleActivity.class.getSimpleName();
    private SharedPreferences change_position;
    private SharedPreferences.Editor editor;
    private TextView keyuan;
    private LinearLayout document_file_fank;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_documentfilehandle;
    }

    @Override
    protected String getAction() {

        document_file_fank = (LinearLayout) findViewById(R.id.document_file_fank);
        document_file_fank.setVisibility(View.GONE);
        return "swcyBs";
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
        getDate();
    }

    private void getDate() {
        more.setVisibility(View.VISIBLE);
        more.setText("完成");
        title.setText("文件办理");
        keyuan = (TextView)  findViewById(R.id.keyuan);
        keyuan.setText("转发给科员");
        document_guild_edit.setHint("请输入办理结果...");
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
            case R.id.document_file_selectpeople:{//转发给科员

                Intent intent= new Intent(this,ChooseReceivers_Banli_Activity.class);
                intent.putExtra("title","转发给科员");
                intent.putExtra("action","swcyZfTree");
                intent.putExtra("opId", getIntent().getStringExtra("opId"));
                intent.putExtra("opIds",opIds);
                intent.putExtra("opXbrId",opXbrId);

                startActivityForResult(intent,10);


//                Intent intent= new Intent(this,DocumentFileForwardActivity.class);
//                intent.putExtra("present",getIntent().getStringExtra("present"));
//                intent.putExtra("opId",getIntent().getStringExtra("opId"));
//
//                intent.putExtra("type5","zhuanfa");
//                intent.putExtra("OpState",getIntent().getStringExtra("OpState"));
//                intent.putExtra("type", "1");
//                startActivity(intent);
            }break;
            case R.id.document_file_fank:{//科员反馈
                Intent intent= new Intent(this,DocumentOpinionActivity2.class);
                intent.putExtra("opId",getIntent().getStringExtra("opId"));
                intent.putExtra("type","fankui");
                intent.putExtra("flowStatus",getIntent().getStringExtra("present")+1);
                intent.putExtra("title","科员反馈");
                startActivity(intent);
            }break;
            case R.id.more:{//完成
                if(document_guild_edit.getText().toString().trim().equals("")){
                    ManagerUtils.showToast(this,"请填写意见");
                    return;
                }
               if(opNames.length()>3){
                   new DialogUtil(DocumentFileHandleActivity.this, this).showConfirm("是否完成文件办理工作？", "您是否已经完成办理，并转办给"+opNames+"办理，是否立即完成办理？", "确定", "取消");

               }else{
                   new DialogUtil(DocumentFileHandleActivity.this, this).showConfirm("是否完成文件办理工作？", "您是否已经完成办理，文件办理结束后即可归档，是否立即完成办理？", "确定", "取消");

               }


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

    @Override
    public void yesClick() {
        popupWindowUtil = new PopupWindowUtil(this, "提交中...");
        popupWindowUtil.show();
        submitData();
    }

    /****
     * 提交数据
     */
    private void submitData() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
//        Call<JsonObject> call= api.swcyNbEdit("swcyBsEdit", uid, getIntent().getStringExtra("opId"), null, document_guild_edit.getText().toString(), opBsrId);
        Call<JsonObject> call= api.swcybsEdit(uid, getIntent().getStringExtra("opId"), opIds, document_guild_edit.getText().toString(), opBsrId,opXbrId);
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
                            // 待办跳过来会触发的方法体
                            change_position = getSharedPreferences("summit_position", 0);
                            if(change_position.getBoolean("allow",true)){

                                //记录提交成功，改变标记，以便切换到待办时，刷新一下数据，获取最新的数据
                                editor=change_position.edit();
                                editor.putBoolean("summit",true);
                                editor.putBoolean("allow",false);
                                editor.commit();
                            }
                            new DialogUtil(DocumentFileHandleActivity.this, DocumentFileHandleActivity.this).showAlert("文件已办结！", "该文件您已办理完成！", "知道了");
                            return;
                        }else{
                                new DialogUtil(DocumentFileHandleActivity.this, new DialogUtil.OnClickListenner() {
                                    @Override
                                    public void yesClick() {
                                    }

                                    @Override
                                    public void noClick() {
                                    }

                                    @Override
                                    public void onSingleClick() {
                                        Intent in2 = new Intent();
                                        setResult(530, in2);
                                        finish();
                                    }
                                }).showAlert("处理失败", "该文件已被撤回，请返回列表刷新最新数据查看！", "知道了");
                            }

                        }
                    Toast.makeText(DocumentFileHandleActivity.this,"该公文处理失败，请重新尝试！",Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                popupWindowUtil.dismiss();
                Toast.makeText(DocumentFileHandleActivity.this,"网络连接有错误",Toast.LENGTH_SHORT).show();
                Log.d("LOG", t.toString());
            }
        });
    }

    @Override
    public void noClick() {

    }

    @Override
    public void onSingleClick() {
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
