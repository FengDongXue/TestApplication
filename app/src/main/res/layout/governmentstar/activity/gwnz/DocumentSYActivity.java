package com.lanwei.governmentstar.activity.gwnz;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.LogUtils;
import com.lanwei.governmentstar.utils.ManagerUtils;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.view.Dialog02;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//公文审阅
public class DocumentSYActivity extends DocumentBaseActivity implements OnClickListener, DialogUtil.OnClickListenner {
    private static final String TAG = DocumentSYActivity.class.getSimpleName();
    private Dialog02 dialog02;
    private boolean isSuccess = false;
    private boolean isBule;
    private int i = 0;
    private JSONObject dataJson;
    private LinearLayout reject;
    private String nBopId;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_documentsy;
    }

    @Override
    protected String getAction() {
        return "gwnzSy";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate()");
        init();
    }

    private void init() {
        final Intent intent = getIntent();
        nBopId = intent.getStringExtra("NBopId");
        // TODO Auto-generated method stub
        reject = (LinearLayout) findViewById(R.id.reject);  //驳回
        reject.setOnClickListener(this);

        getData();
    }

    private void getData() {
        more.setVisibility(View.VISIBLE);
        more.setText("完成");
        title.setText("公文审阅");
        title.setVisibility(View.VISIBLE);
//        lnl_shenhe.setVisibility(View.INVISIBLE);
        document_guild_edit.setHint("请输入审阅意见...");
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
            case R.id.more: {//完成
                if (document_guild_edit.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写意见");
                    return;
                } else {
                    new DialogUtil(DocumentSYActivity.this, this).showConfirm("提交意见", "您确定要提交意见吗？", "确定", "取消");
                }
            }
            break;
            case R.id.reject:
                if (document_guild_edit.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写驳回意见");
                    return;
                } else {
                    getRejectData(document_guild_edit.getText().toString(),this);
                }
                break;
            case R.id.document_guild_k:
//                {//快速回复
//                WindowManager.LayoutParams lp2 = getWindow().getAttributes();
//                lp2.alpha = (float) 0.8;
//                getWindow().setAttributes(lp2);
//                // 加载popupwindow的布局
//                View view2 = getLayoutInflater().inflate(R.layout.popwindow_sy3, null);
//                ListView listView = (ListView) view2.findViewById(R.id.popwindow_sy3_list);
//                final List<String> popList = new ArrayList<>();
//
//                popList.add("审阅无误，请×××阅。");
//                popList.add("我已审阅，报请×××阅。");
//                popList.add("请××提出意见，呈××签发。");
//
//                PopAdapter popAdapter = new PopAdapter(popList, DocumentSYActivity.this);
//                listView.setAdapter(popAdapter);
//                final PopupWindow popupWindow = new PopupWindow(view2, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//                // 初始化popupwindow的点击控件
//                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        popupWindow.dismiss();
//                        document_guild_edit.setText(popList.get(position));
//                    }
//                });
//                // 点击屏幕之外的区域可否让popupwindow消失
//                popupWindow.setFocusable(true);
//                popupWindow.setBackgroundDrawable(new BitmapDrawable());
//                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                    @Override
//                    public void onDismiss() {
//                        WindowManager.LayoutParams p = getWindow().getAttributes();
//                        p.alpha = (float) 1;
//                        getWindow().setAttributes(p);
//                    }
//                });
//                int xy[] = new int[2];
//                v.getLocationOnScreen(xy);
//                // 设置popupwindow的显示位置
//                popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
//            }
            break;
            default:
                break;
        }
    }

    /**
     *
     */
    private void getData1() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        final Intent intent = getIntent();
        final String nBopId = intent.getStringExtra("NBopId");
//        (nBopId, uid, document_guild_edit.getText().toString());
        Call<JsonObject> call = api.gwnzSyEdit(nBopId, uid, document_guild_edit.getText().toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                popupWindowUtil.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (!jsonObject.isNull("data")) {
                        dataJson = jsonObject.getJSONObject("data");
                        boolean result = dataJson.getBoolean("result");
                        //Toast.makeText(DocumentSHActivity.this, result ? "修改成功" : "修改失败", Toast.LENGTH_SHORT).show();
                        if (result) {
                            new DialogUtil(DocumentSYActivity.this, DocumentSYActivity.this).showAlert("文件审阅", "您已将审阅意见提交成功", "知道了");
                            //// TODO: 2017/4/15 在此回传数据
                            //finish();
                        }
                        return;
//                    JSONObject jsonObject = new JSONObject(response.body().toString());
//                    if (!jsonObject.isNull("data")) {
//                        JSONObject data = jsonObject.getJSONObject("data");
//                        boolean result = data.getBoolean("result");bb
//                        String opState = data.getString("opState");
//                        String docStatus = data.getString("docStatus");
//                        int position = intent.getIntExtra("position",1);
//                        Toast.makeText(DocumentSYActivity.this, result ? "修改成功" : "修改失败", Toast.LENGTH_SHORT).show();
//                        if (result){
////                            Document document = (Document) intent.getSerializableExtra("Document");
////                            document.setOpState("3");
//                           SharedPreferencesUtil.saveData(DocumentSYActivity.this,"SYopState",opState);
//                            SharedPreferencesUtil.saveData(DocumentSYActivity.this,"SYdocStatus",docStatus);
//                            SharedPreferencesUtil.saveData(DocumentSYActivity.this,"position",position);
//
//                            goBack(nBopId,opState,docStatus);
//
//                            finish();
//
//                        }
//                        return;
                    }
                    Toast.makeText(DocumentSYActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                popupWindowUtil.dismiss();
            }
        });







       /* call.enqueue(new Callback<Logging_Success>() {
            @Override
            public void onResponse(Call<Logging_Success> call, Response<Logging_Success> response) {
                if (response.body().getData() == hardwork) {
//                    Toast.makeText(DocumentSHActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
//                    Toast.makeText(DocumentSHActivity.this, response.body().getMessage().toString(), Toast.LENGTH_SHORT).show();
                } else {
                    Gson gson = new Gson();
                    PreferencesManager.getInstance(DocumentSHActivity.this, "accountBean").put("jsonStr", gson.toJson(response.body()));
                    Logging_Success.Data data = response.body().getData();

                    isSuccess = true;
                    String opId = data.getOpId();
                    String dptId = data.getAccountDeptId();

                }
            }

            @Override
            public void onFailure(Call<Logging_Success> call, Throwable t) {
//                Toast.makeText(DocumentSHActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
//                Log.d("LOG", t.toString());
                isSuccess = false;
            }
        });*/
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

    private PopupWindowUtil popupWindowUtil;

    @Override
    public void yesClick() {
        popupWindowUtil = new PopupWindowUtil(DocumentSYActivity.this, "提交中...");
        popupWindowUtil.show();
        getData1();
    }

    @Override
    public void noClick() {

    }

    @Override
    public void onSingleClick() {
        Intent in = new Intent();
        in.putExtra("opId", getIntent().getStringExtra("opId"));
        try {
            in.putExtra("opState", dataJson.getString("opState"));
            in.putExtra("docStatus", dataJson.getString("docStatus"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setResult(520, in);

        finish();
    }

//    private void goBack(String nBopId,String opState,String docStatus) {
//        Intent in = new Intent();
////        in.putExtra("opId", getIntent().getStringExtra("opId"));
////        in.putExtra("aaa","传过来吗");
//        in.putExtra("opId",nBopId);
//        Log.e("boid",nBopId);
//        try {
////            in.putExtra("opState", dataJson.getString("opState"));
////            in.putExtra("docStatus", dataJson.getString("docStatus"));
//            in.putExtra("opState",opState);
//            in.putExtra("docStatus", docStatus);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        setResult(520, in);
//        setResult(520,in);
//    }

}
