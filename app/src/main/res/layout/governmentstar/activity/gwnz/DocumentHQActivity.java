package com.lanwei.governmentstar.activity.gwnz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
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

//公文会签
public class DocumentHQActivity extends DocumentBaseActivity implements OnClickListener, DialogUtil.OnClickListenner {
    private static final String TAG = DocumentHQActivity.class.getSimpleName();
    private Dialog02 dialog02;
    private boolean isSuccess = false;
    private boolean isBule;

    private SharedPreferences change_position;
    private SharedPreferences.Editor editor;
    private JSONObject data;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_document_l;
    }

    @Override
    protected String getAction() {
        return "gwnzHq";
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
        more.setVisibility(View.VISIBLE);
        more.setText("完成");
        title.setText("公文会签");
        document_guild_edit.setHint("请输入会签意见...");
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
            // 基类中已注册
//            case R.id.document_file_look:{//查看流程
//                Intent intent= new Intent(this,Process2_Activity.class);
//                intent.putExtra("opId", getIntent().getStringExtra("opId"));
//                startActivity(intent);
//            }break;
            case R.id.more: {//完成
                if (document_guild_edit.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写意见");
                    return;
                } else {

                    new DialogUtil(DocumentHQActivity.this, this).showConfirm("是否会签该文件？", "您是否已经完成审阅，并同意会签此文件？", "立即签发", "我再看看");

                }
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
//                popList.add("同意会签。");
//                popList.add("已悉，同意会签。");
//
//                PopAdapter popAdapter = new PopAdapter(popList, DocumentHQActivity.this);
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

            case R.id.reject:
                if (document_guild_edit.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写驳回意见");
                    return;
                } else {
                    getRejectData(document_guild_edit.getText().toString(), DocumentHQActivity.this);
                }
                break;

            default:
                break;
        }
    }

    private void getData1() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        Call<JsonObject> call = api.gwnzHqEdit(getIntent().getStringExtra("opId"), uid, document_guild_edit.getText().toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                popupWindowUtil.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (!jsonObject.isNull("data")) {
                        data = jsonObject.getJSONObject("data");
                        boolean result = data.getBoolean("result");
//                        Toast.makeText(DocumentHQActivity.this, result ? "修改成功" : "修改失败", Toast.LENGTH_SHORT).show();
                        if (result) {
                            new DialogUtil(DocumentHQActivity.this, DocumentHQActivity.this).showAlert("公文已会签！", "您已会签该文件", "知道了");
                            return;
                        }
                    }
                    Toast.makeText(DocumentHQActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


//                if(response.body().getData()==hardwork){
//                    Toast.makeText(DocumentHQActivity.this,response.body().getMessage(),Toast.LENGTH_SHORT).show();
//                }else{
//                    Gson gson = new Gson();
//                    PreferencesManager.getInstance(DocumentHQActivity.this, "accountBean").put("jsonStr", gson.toJson(response.body()));
//                    Logging_Success.Data data = response.body().getData();
//                    String opId = data.getOpId();
//                    String dptId = data.getAccountDeptId();
//
//
//                    // 待办跳转过来，要执行的代码，只为提交成功做个标记，以便待办移除那个item
//                    change_position = getSharedPreferences("summit_position", 0);
//                    if(change_position.getBoolean("isOpen",false)){
//
//                        Intent intent=new Intent();
//                        setResult(520,intent);
//                        editor=change_position.edit();
//                        editor.putBoolean("isOpen",false);
//                        editor.commit();
//                    }
//                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                popupWindowUtil.dismiss();

            }

        });
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
        popupWindowUtil = new PopupWindowUtil(DocumentHQActivity.this, "提交中...");
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
            in.putExtra("opState", data.getString("opState"));
            in.putExtra("docStatus", data.getString("docStatus"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setResult(520, in);

        finish();
    }
}
