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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.ChooseReceivers_Activity;
import com.lanwei.governmentstar.activity.ChooseReceivers_Banli_Activity;
import com.lanwei.governmentstar.activity.DocumentOpinionActivity2;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.LogUtils;
import com.lanwei.governmentstar.utils.ManagerUtils;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.utils.PreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lanwei.governmentstar.R.id.document_file_fank;

//转发的文件
public class DocumentSelectActivity extends DocumentBaseCActivity implements OnClickListener,DialogUtil.OnClickListenner {
    private static final String TAG = DocumentSelectActivity.class.getSimpleName();
    private SharedPreferences change_position;
    private SharedPreferences.Editor editor;
    LinearLayout document_file_fank2;
    private TextView textView10;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_documentselect;
    }

    @Override
    protected String getAction() {

        textView10 = (TextView) findViewById(R.id.textView10);
        if(getIntent().getStringExtra("type5").equals("zhuanfa")){
            return "swcyZf";
        }else{
            return "swcyXb";
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate()");
        document_file_fank2=(LinearLayout) findViewById(R.id.document_file_fank);
        document_file_fank2.setOnClickListener(this);
        if(getIntent().getStringExtra("type5").equals("xieban")){
            document_file_fank2.setVisibility(View.GONE);
        }
        init();

    }

    private void init() {
        // TODO Auto-generated method stub
        getDate();
    }

    private void getDate() {

        more.setText("完成");
        if(getIntent().getStringExtra("type5").equals("zhuanfa")){

            title.setText("转发的文件");
        }else{
            title.setText("协办的文件");
        }
        document_guild_edit.setHint("请输回复的内容...");
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
            case document_file_fank:{//查看留言
                Intent intent= new Intent(this,ChooseReceivers_Activity.class);
                intent.putExtra("opId",getIntent().getStringExtra("opId"));
                intent.putExtra("opXbrId",opXbrId);
                Log.e("opXbrId前",opXbrId);
                startActivityForResult(intent,01);
//                Intent intent= new Intent(this,ChooseReceivers_Banli_Activity.class);
//                intent.putExtra("title","转发给科员");
//                intent.putExtra("action","swcyZfTree");
//                intent.putExtra("opId", getIntent().getStringExtra("opId"));
//                intent.putExtra("opIds",opIds);
//                intent.putExtra("opXbrId",opXbrId);


//                startActivityForResult(intent,01);
            }break;
            case R.id.more:{//完成

                if(document_guild_edit.getText().toString().trim().equals("")){
                    ManagerUtils.showToast(this,"请填写意见");
                    return;
                }

                if(getIntent().getStringExtra("type5").equals("zhuanfa")){

                    new DialogUtil(DocumentSelectActivity.this, this).showConfirm("是否办结该公文？", "您是否已完成该文件的办理？", "我已办结", "还没办完");
                }else{

                    new DialogUtil(DocumentSelectActivity.this, this).showConfirm("是否将协办意见反馈给领导？", "您是否将您的协办意见反馈给领导？", "我已办结", "还没办完");
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

    /***
     * 提交数据
     */
    private void submitData() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
//        Call<JsonObject> call = api.swcyNbEdit("swcyZfEdit", uid, getIntent().getStringExtra("opId"), null, document_guild_edit.getText().toString(), "");
        Call<JsonObject> call=null;
        String defString = PreferencesManager.getInstance(this, "accountBean").get("jsonStr");
        Gson gson = new Gson();
        Logging_Success bean = gson.fromJson(defString, Logging_Success.class);

        if(getIntent().getStringExtra("type5").equals("zhuanfa")){

           call = api.swcyzfEdit(uid, getIntent().getStringExtra("opId"), null, document_guild_edit.getText().toString(), "",opXbrId);
        }else{

            call = api.swcyXbEdit(bean.getData().getOpId(), getIntent().getStringExtra("opId"), document_guild_edit.getText().toString());
        }
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                popupWindowUtil.dismiss();

                try {
                    JSONObject dataJson = new JSONObject(response.body().toString());

                    if (dataJson.get("data") != null) {
                        jsonObject = (JSONObject) dataJson.get("data");
                        boolean result = jsonObject.getBoolean("result");
                        if (result) {
                            //确定框

                            change_position = getSharedPreferences("summit_position", 0);
                            if(change_position.getBoolean("allow",true)){

                                //记录提交成功，改变标记，以便切换到待办时，刷新一下数据，获取最新的数据
                                editor=change_position.edit();
                                editor.putBoolean("summit",true);
                                editor.putBoolean("allow",false);
                                editor.commit();
                            }

                            new DialogUtil(DocumentSelectActivity.this, DocumentSelectActivity.this).showAlert("该文件已办理完成！", "您已完成该文件的办理！", "知道了");
                            return;
                        }
                    }
                    Toast.makeText(DocumentSelectActivity.this,"该公文处理失败，请重新尝试！",Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                popupWindowUtil.dismiss();
                Toast.makeText(DocumentSelectActivity.this,"网络连接有错误",Toast.LENGTH_SHORT).show();
                Log.d("LOG", t.toString());
            }
        });
    }

    @Override
    public void yesClick() {
        popupWindowUtil = new PopupWindowUtil(DocumentSelectActivity.this, "提交中...");
        popupWindowUtil.show();
        submitData();
    }

    @Override
    public void noClick() {

    }

    @Override
    public void onSingleClick() {
        /*Log.d("TAG",jsonObject.toString());*/
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
