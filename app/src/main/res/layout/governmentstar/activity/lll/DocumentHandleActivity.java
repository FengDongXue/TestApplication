package com.lanwei.governmentstar.activity.lll;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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
import com.lanwei.governmentstar.activity.ChooseReceiverPSActivity;
import com.lanwei.governmentstar.activity.Watch_Notification_Activity;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.DownloadUtil;
import com.lanwei.governmentstar.utils.LogUtils;
import com.lanwei.governmentstar.utils.ManagerUtils;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.utils.PreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//    参与处理，撤回重批，批示公文共用
public class DocumentHandleActivity extends DocumentBaseCActivity implements OnClickListener, DialogUtil.OnClickListenner {
    private static final String TAG = DocumentHandleActivity.class.getSimpleName();
    private SharedPreferences change_position;
    private SharedPreferences.Editor editor;
    private TextView chengban_choose;
    private String tjms = "cycl";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_documenthandle;
    }

    @Override
    protected String getAction() {
        if(getIntent().getStringExtra("type").equals("0")){//参与处理
            return null;
        }else if(getIntent().getStringExtra("type").equals("1")){//撤回重批
            return null;
        }else if(getIntent().getStringExtra("type").equals("2")){//批示公文
            document_file_selectpeople = (LinearLayout) findViewById(R.id.document_file_selectpeople);
            chengban_choose = (TextView) findViewById(R.id.chengban_choose);
            chengban_choose.setText("请您选择承办人");
            tjms = getIntent().getStringExtra("tjms");
            return "swcyPs";
        }
        return null;
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
        if(getIntent().getStringExtra("type").equals("0")){//参与处理
            title.setText("参与处理");

            document_guild_edit.setHint("请输入批示意见...");
        }else if(getIntent().getStringExtra("type").equals("1")){//撤回重批
            title.setText("撤回重批");

            document_guild_edit.setHint("请输入批示意见...");
        }else if(getIntent().getStringExtra("type").equals("2")){//批示公文
            title.setText("批示公文");

            document_guild_edit.setHint("请输入批示意见...");
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
        if(opType.equals("1")){
            document_file_selectpeople.setVisibility(View.GONE);
            Log.e("发射点士大夫士大夫","发的更好发挥发货");
        }
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
            case R.id.document_file_selectpeople:{//文件转办给
                Intent intent= new Intent(this,ChooseReceiverPSActivity.class);

                if(isNotChoosed==1){
                    intent.putExtra("opIds",opIds);

                    if(!nbrId.equals("")){
                        opBsrId=opBsrId+","+nbrId;
                    }
                    intent.putExtra("opBsrId",opBsrId);

                }else{
                    intent.putExtra("opIds","");
                    intent.putExtra("opBsrId","");
                }
                if(getIntent().getStringExtra("type").equals("0")){//参与处理
                    intent.putExtra("title","文件转办给");
                }else if(getIntent().getStringExtra("type").equals("1")){//撤回重批
                    intent.putExtra("title","文件转办给");
                }else if(getIntent().getStringExtra("type").equals("2")){//批示公文
                    intent.putExtra("title","文件转办给");
                    intent.putExtra("action","swcyPsTree");
                }

                Log.e("带到下个界面的承办人",opIds);
                Log.e("带到下个界面的办事人",opBsrId);
                Log.e("带到下个界面拟办人",opNbrId);
                intent.putExtra("opNbrId", opNbrId);
                startActivityForResult(intent,10);
            }break;
            case R.id.more:{//完成
                if(document_guild_edit.getText().toString().trim().equals("")){
                    ManagerUtils.showToast(this,"请填写意见");
                    return;
                }
               /* if(opIds==hardwork||opIds.equals("")){
                    ManagerUtils.showToast(this,"请选择文件快速处理人");
                    return;
                }*/
                if(getIntent().getStringExtra("type").equals("0")){//参与处理
                }else if(getIntent().getStringExtra("type").equals("1")){//撤回重批
                }else if(getIntent().getStringExtra("type").equals("2")){//批示公文


                    if(!opBsrId.equals("")){

                        String[] opid_list=opBsrId.split(",");
                        // 数组转为Arraylist
                        ArrayList<String> list2 = new ArrayList<>(Arrays.asList(opid_list));

                        for(int m=0;m<list2.size();m++){

                            if(list2.get(m).equals(opNbrId_2)){
                                list2.remove(m);
                                nbrId=opNbrId_2;
                                break;
                            }

                        }

                        opBsrId="";
                        if(list2.size()>0){
                            for(int m=0;m<list2.size();m++){
                                opBsrId=opBsrId+list2.get(m)+",";
                            }
                        }

                    }

                    if(!opBsrId.equals("")){

                        if(opBsrId.substring(opBsrId.length()-1,opBsrId.length()).equals(",")){
                            opBsrId=opBsrId.substring(0,opBsrId.length()-1);
                        }
                    }


                    if(opType.equals("1")){
                        new DialogUtil(DocumentHandleActivity.this, this).showConfirm("是否完成文件批示工作？", "您是否已经完成批示，并让此文件继续进行办理？", "批示完成", "我再看看");

                    }else if(!opYbrId.equals("") && opIds.equals("") && nbrId.equals("")){
                        new DialogUtil(DocumentHandleActivity.this, this).showConfirm("是否完成文件批示工作？", "您是否已经完成批示，并让此文件继续进行办理？", "批示完成", "我再看看");

                    } else if(!opIds.equals("") || !nbrId.equals("")){

                        new DialogUtil(DocumentHandleActivity.this, this).showConfirm("是否完成文件批示工作？", "您是否已经完成批示，并让此文件继续让"+opNames+"进行办理？", "批示完成", "我再看看");
                    }else{
                        new DialogUtil(DocumentHandleActivity.this, new Summit()).showAlert("请选择承办人！", "您尚未选择该文件的承办人，请先选择承办人！", "知道了");
//                        Toast.makeText(DocumentHandleActivity.this,"您暂未选择承办人，不符合流程，请选择承办人或者取消办事人才能提交数据！！",Toast.LENGTH_SHORT).show();
                    }

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

    //匹配搜索的关键字
    private SpannableString matcherSearchText(int color, String text, String keyword) {
        SpannableString ss = new SpannableString(text);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    //提交数据
    protected void submitData() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
//        Call<JsonObject> call= api.swcyNbEdit("swcyPsEdit", uid, getIntent().getStringExtra("opId"), opIds, document_guild_edit.getText().toString(), opBsrId);
        Call<JsonObject> call= api.swcypsEdit(uid, getIntent().getStringExtra("opId"), opIds, document_guild_edit.getText().toString(), opBsrId, nbrId,tjms);
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

                            new DialogUtil(DocumentHandleActivity.this, DocumentHandleActivity.this).showAlert("文件已批示！", "您已完成批示，该文件将在办理流程中继续办理！", "知道了");
                            return;
                        }
                    }
                    Toast.makeText(DocumentHandleActivity.this,"该公文处理失败，请重新尝试！",Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                popupWindowUtil.dismiss();
                Toast.makeText(DocumentHandleActivity.this,"网络连接有误",Toast.LENGTH_SHORT).show();
                Log.d("LOG", t.toString());
            }
        });
    }

    @Override
    public void yesClick() {
        popupWindowUtil = new PopupWindowUtil(this, "提交中...");
        popupWindowUtil.show();
        submitData();
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

    // 下载弹出框点击的回掉方法体
    class Summit implements DialogUtil.OnClickListenner {

        public Summit() {
        }

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
