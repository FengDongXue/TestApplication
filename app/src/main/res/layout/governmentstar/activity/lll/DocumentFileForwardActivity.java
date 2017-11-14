package com.lanwei.governmentstar.activity.lll;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.ChooseReceiverNBActivity;
import com.lanwei.governmentstar.bean.Logging_Success;
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

//文件转发、承办公文
public class DocumentFileForwardActivity extends DocumentBaseCActivity implements OnClickListener, DialogUtil.OnClickListenner {
    private static final String TAG = DocumentFileForwardActivity.class.getSimpleName();

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_documentfileforward;
    }

    @Override
    protected String getAction() {
        if(getIntent().getStringExtra("type").equals("0")){//承办公文
            return "swcyCb";
        }else if(getIntent().getStringExtra("type").equals("1")){//文件转发
            return "swcyZf";
        }
        return "";
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

        more.setText("完成");
        if(getIntent().getStringExtra("type").equals("0")){//承办公文
            title.setText("承办公文");

            document_guild_edit.setHint("请输入留言信息...");
        }else if(getIntent().getStringExtra("type").equals("1")){//文件转发
            title.setText("文件转发");
            document_guild_k.setVisibility(View.GONE);

            document_guild_edit.setHint("请输入留言信息...");
        }
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
                Intent intent= new Intent(this,ChooseReceiverNBActivity.class);
                intent.putExtra("title","转发给科员");
                intent.putExtra("action","swcyZfTree");
                intent.putExtra("isNotAssist",isNotAssist);
                intent.putExtra("opId", getIntent().getStringExtra("opId"));

                intent.putExtra("opBsrId",opBsrId);
                if(isNotChoosed==1){
                    intent.putExtra("opIds",opIds);
                }else{
                    intent.putExtra("opIds","");
                }

                startActivityForResult(intent,10);
            }break;
            case R.id.more:{//完成
                if(getIntent().getStringExtra("type").equals("1")){//文件转发
                    if(document_guild_edit.getText().toString().equals("")){
                        ManagerUtils.showToast(this,"请填写转发留言");
                        return;
                    }
                    if(opIds==null||opIds.equals("")){
                        ManagerUtils.showToast(this,"请选择转发人");
                        return;
                    }

                    new DialogUtil(DocumentFileForwardActivity.this, this).showConfirm("是否立即转发该文件？", "您是否将此文件转发给科员办理？", "立即转发", "我再看看");
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

    /****
     * 提交数据
     */
    private void submitData() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
//        Call<JsonObject> call = api.swcyNbEdit("swcyZfUser", uid, getIntent().getStringExtra("opId"), opIds, document_guild_edit.getText().toString(), "");
        Call<JsonObject> call = api.swcyblEdit(uid, getIntent().getStringExtra("opId"), opIds, document_guild_edit.getText().toString(), "");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                popupWindowUtil.dismiss();
                try {
                    JSONObject dataJson = new JSONObject(response.body().toString());
                    if (dataJson.get("data") != null) {
                        boolean result = dataJson.getBoolean("data");
                        if (result) {
                            //确定框
                            new DialogUtil(DocumentFileForwardActivity.this, DocumentFileForwardActivity.this).showAlert("公文已转给科员办理！", "此公文已转发给科员办理！", "知道了");
                            return;
                        }
                    }
                    Toast.makeText(DocumentFileForwardActivity.this,"转发人员失败，请重新尝试！",Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                popupWindowUtil.dismiss();
                Toast.makeText(DocumentFileForwardActivity.this,t.toString(),Toast.LENGTH_SHORT).show();
                Log.d("LOG", t.toString());
            }
        });
    }



    @Override
    public void yesClick() {
        popupWindowUtil = new PopupWindowUtil(DocumentFileForwardActivity.this, "提交中...");
        popupWindowUtil.show();
        submitData();
    }

    @Override
    public void noClick() {

    }

    @Override
    public void onSingleClick() {
        finish();
    }
}
