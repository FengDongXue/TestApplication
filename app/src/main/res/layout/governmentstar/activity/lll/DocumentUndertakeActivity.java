package com.lanwei.governmentstar.activity.lll;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
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
import com.lanwei.governmentstar.utils.PreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//承办公文、阅办公文
public class DocumentUndertakeActivity extends DocumentBaseCActivity implements OnClickListener, DialogUtil.OnClickListenner {
    private static final String TAG = DocumentUndertakeActivity.class.getSimpleName();
    private TextView tvReadpeople;

    private SharedPreferences change_position;
    private SharedPreferences.Editor editor;

//    private LinearLayout document_file_selectpeople;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_documentundertake;
    }

    @Override
    protected String getAction() {
        if(getIntent().getStringExtra("type").equals("0")){//承办公文
            return "swcyCb";
        }else if(getIntent().getStringExtra("type").equals("1")){//阅办公文
            return "swcyYb";
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        LogUtils.d(TAG, "onCreate()");

        init();
//        document_file_selectpeople=(LinearLayout) findViewById(R.id.document_file_selectpeople);
    }

    private void init() {
        // TODO Auto-generated method stub
        getDate();
    }

    private void getDate() {
        more.setText("完成");
        tvReadpeople = (TextView) findViewById(R.id.tv_readpeople);
        if(getIntent().getStringExtra("type").equals("0")){//承办公文
            title.setText("承办公文");

            document_guild_edit.setHint("请输入承办意见...");
            tvReadpeople.setText("选择办事人");
        }else if(getIntent().getStringExtra("type").equals("1")){//阅办公文
            title.setText("阅办公文");

            document_guild_edit.setHint("请输入阅办意见...");
            tvReadpeople.setText("选择承办人");
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
//        document_file_selectpeople.setVisibility(View.VISIBLE);
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
            case R.id.document_file_selectpeople://选择办事人

                    Intent intent= new Intent(this,ChooseReceiverNBActivity.class);
                    intent.putExtra("opId",getIntent().getStringExtra("opId"));


                if(bean.getData().getAccountDeptId().equals("0155")){
                    intent.putExtra("isNotAssist",0);
                }else{
                    intent.putExtra("isNotAssist",isNotAssist);
                }
                    if(isNotChoosed==1){
                        intent.putExtra("opIds",opIds);
                        intent.putExtra("opBsrId",opBsrId);
                        intent.putExtra("opXbrId",opXbrId);
                        Log.e("已选择人的ID  qian2",opIds);
                        Log.e("已选择人的ID43343   qian3",opXbrId);
                    }else{
                        intent.putExtra("opIds","");
                        intent.putExtra("opBsrId","");
                        intent.putExtra("opXbrId","");
                    }
                    if(getIntent().getStringExtra("type").equals("0")){//承办公文
                        intent.putExtra("title","选择办事人");
                        intent.putExtra("action","swcyCbTree");
                    }else if(getIntent().getStringExtra("type").equals("1")){//阅办公文
                        intent.putExtra("title","选择承办人");
                        intent.putExtra("action","swcyYbTree");
                    }

                    startActivityForResult(intent,10);



            break;
            case R.id.more://完成
                if(document_guild_edit.getText().toString().trim().equals("")){
                    ManagerUtils.showToast(this,"请填写意见");
                    return;
                }

                Log.e("getIntent().ge)",getIntent().getStringExtra("type")+"");

                if(getIntent().getStringExtra("type").equals("0")){//承办公文

                    if(opNames.length()>3){
                        new DialogUtil(DocumentUndertakeActivity.this, this).showConfirm("您是否完成承办工作？", "您是否已完成承办，并转给"+opNames+"进行办理？", "承办完成", "我再看看");

                    }else{
                        new DialogUtil(DocumentUndertakeActivity.this, this).showConfirm("您是否完成承办工作？", "您是否已完成承办，并继续进行办理？", "承办完成", "我再看看");

                    }

//                    if(opType.equals("1")){
//
//                        if(opNames.length()>3){
//                            new DialogUtil(DocumentUndertakeActivity.this, this).showConfirm("您是否完成承办工作？", "您是否已完成承办，并转给"+opNames+"进行办理？", "承办完成", "我再看看");
//                        }else{
//                            new DialogUtil(DocumentUndertakeActivity.this, this).showConfirm("您是否完成承办工作？", "您是否已完成承办，并继续进行办理？", "承办完成", "我再看看");
//                        }
//
//                        return;
//                    }
//                    //承办文件
//                    if (documentBaseC.getData().getDocStatus() != null && documentBaseC.getData().getDocStatus().equals("0")) {
//                        //判断是否有选择办事人
//
//                        if(bean.getData().getAccountDeptId().equals("0155") ){
//
//                            if(isNotAssist==1 || bsrNum !=0){
//
//                                if(opNames.length()>3){
//                                    new DialogUtil(DocumentUndertakeActivity.this, this).showConfirm("您是否完成承办工作？", "您是否已完成承办，并转给"+opNames+"进行办理？", "承办完成", "我再看看");
//
//                                }else{
//                                    new DialogUtil(DocumentUndertakeActivity.this, this).showConfirm("您是否完成承办工作？", "您是否已完成承办，并继续进行办理？", "承办完成", "我再看看");
//                                }
//
//                                Log.e("namedfgdd豆腐干豆腐干",opNames);
//                            }else{
//
//                                if (opIds == null || opIds.equals("") ) {
//
//                                    new DialogUtil(DocumentUndertakeActivity.this,new Vo()).showAlert("提交提醒", "承办文件必须选择办理人才能使公文继续处理", "知道了");
//
////                            new DialogUtil(DocumentUndertakeActivity.this, this).showConfirm("您未选择具体办事人？", "您是否不选择具体办事人，完成承办工作？", "不选办事人", "我点错了");
//
//                                } else {
//                                    new DialogUtil(DocumentUndertakeActivity.this, this).showConfirm("您是否完成承办工作？", "您是否已完成承办，并转给"+opNames+"进行办理？", "承办完成", "我再看看");
//                                }
//
//                            }
//
//
//                        }else{
//                            if (opIds == null || opIds.equals("") ) {
//
//                                new DialogUtil(DocumentUndertakeActivity.this,new Vo()).showAlert("提交提醒", "承办文件必须选择办理人才能使公文继续处理", "知道了");
//
//
////                            new DialogUtil(DocumentUndertakeActivity.this, this).showConfirm("您未选择具体办事人？", "您是否不选择具体办事人，完成承办工作？", "不选办事人", "我点错了");
//
//                            } else {
//                                new DialogUtil(DocumentUndertakeActivity.this, this).showConfirm("您是否完成承办工作？", "您是否已完成承办，并转给"+opNames+"进行办理？", "承办完成", "我再看看");
//                            }
//                        }
//
//
//                    } else {//轮阅文件
//
//                        if(opNames.length()>3){
//                            new DialogUtil(DocumentUndertakeActivity.this, this).showConfirm("您是否完成承办工作？", "您是否已完成承办，并转给"+opNames+"进行办理？", "承办完成", "我再看看");
//
//                        }else{
//                            new DialogUtil(DocumentUndertakeActivity.this, this).showConfirm("您是否完成承办工作？", "您是否已完成承办，并继续进行办理？", "承办完成", "我再看看");
//
//                        }
//                    }

                }else if(getIntent().getStringExtra("type").equals("1")){//阅办公文
                    if(opIds==null|| opIds.equals("")){
                        ManagerUtils.showToast(this,"请选择文件快速处理人");
                        return;
                    }
                    new DialogUtil(DocumentUndertakeActivity.this, this).showConfirm("是否完成文件阅办工作？", "您是否已经完成阅办，将此文件继续给"+opNames+"进行办理？", "阅办完成", "我再看看");
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

    /***
     * 提交数据
     */
    private void submitData() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        Call<JsonObject> call;
        if(getIntent().getStringExtra("type").equals("0")){
            call = api.swcycbEdit(uid, getIntent().getStringExtra("opId"), opIds, document_guild_edit.getText().toString(), opXbrId);

        }else{
            call = api.swcyybEdit(uid, getIntent().getStringExtra("opId"), opIds, document_guild_edit.getText().toString(), "");
        }

//        Call<JsonObject> call = api.swcyNbEdit(getIntent().getStringExtra("type").equals("0") ?  "swcyCbEdit" : "swcyYbEdit", uid, getIntent().getStringExtra("opId"), opIds, document_guild_edit.getText().toString(), "");
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
                            Log.e("提交侧滑盖南宫",change_position.getBoolean("allow",true)+"");
                            if(change_position.getBoolean("allow",true)){

                                //记录提交成功，改变标记，以便切换到待办时，刷新一下数据，获取最新的数据
                                editor=change_position.edit();
                                editor.putBoolean("summit",true);
                                editor.putBoolean("allow",false);
                                editor.commit();
                                Log.e("的人官方的如果德国","提交侧滑盖南宫");
                            }

                            if(getIntent().getStringExtra("type").equals("0")){//承办公文
                                new DialogUtil(DocumentUndertakeActivity.this, DocumentUndertakeActivity.this).showAlert("文件承办已完成", "已完成承办，该文件将在办理流程中继续办理，如需查看进程请在收文传阅首页列表中查看！", "知道了");
                            }else if(getIntent().getStringExtra("type").equals("1")){//阅办公文
                                new DialogUtil(DocumentUndertakeActivity.this, DocumentUndertakeActivity.this).showAlert("文件阅办已完成", "已完成阅办，该文件将在办理流程中继续办理，如需查看进程请在收文传阅首页列表中查看！", "知道了");
                            }
                            return;
                        }else{

                            if(getIntent().getStringExtra("type").equals("0")){//承办公文
                                new DialogUtil(DocumentUndertakeActivity.this, new DialogUtil.OnClickListenner() {
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
                    }
                    Toast.makeText(DocumentUndertakeActivity.this,"该公文处理失败，请重新尝试！",Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                popupWindowUtil.dismiss();
                Toast.makeText(DocumentUndertakeActivity.this,"网络连接有错误",Toast.LENGTH_SHORT).show();
                Log.d("LOG", t.toString());
            }
        });
    }
    @Override
    public void yesClick() {
        popupWindowUtil = new PopupWindowUtil(DocumentUndertakeActivity.this, "提交中...");
        popupWindowUtil.show();
        submitData();
    }

    @Override
    public void noClick() {

    }

    @Override
    public void onSingleClick() {
        //Log.d("TAG",jsonObject.toString());
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


    class Vo implements  DialogUtil.OnClickListenner {

        @Override
        public void yesClick() {

        }

        @Override
        public void noClick() {

        }

        @Override
        public void onSingleClick() {

        }
    }

}
