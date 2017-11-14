package com.lanwei.governmentstar.activity.gwnz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
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
import com.lanwei.governmentstar.view.Dialog01;
import com.lanwei.governmentstar.view.Dialog02;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//公文签发
public class DocumentQFActivity extends DocumentBaseActivity implements OnClickListener, DialogUtil.OnClickListenner {
    private static final String TAG = DocumentQFActivity.class.getSimpleName();
    private Dialog02 dialog02;
    private boolean isSuccess = false;
    private boolean isBule;

    private SharedPreferences change_position;
    private SharedPreferences.Editor editor;
    private JSONObject data;
    private LinearLayout reject;
    private LinearLayout documentStop;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_documentqf;
    }

    @Override
    protected String getAction() {
        return "gwnzQf";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate()");
        init();
    }

    private void init() {
        reject = (LinearLayout) findViewById(R.id.reject);  //驳回
        reject.setOnClickListener(this);

//        documentStop = (LinearLayout) findViewById(R.id.ducument_sy_qianfa);  //终止
//        documentStop.setOnClickListener(this);
        // TODO Auto-generated method stub
        getData();
    }

    private void getData() {
        more.setVisibility(View.VISIBLE);
        more.setText("完成");
        title.setText("公文签发");
        document_guild_edit.setHint("请输入签发意见/驳回意见...");
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
                    new DialogUtil(DocumentQFActivity.this, this).showConfirm("是否立即签发该文件？", "您是否已完成审阅，并同意签发该文件？", "立即签发", "我再看看");
                }
            }
            break;
            case R.id.reject:
                if (document_guild_edit.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写驳回意见");
                    return;
                } else {
                    getRejectData(document_guild_edit.getText().toString(), this);
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
//                popList.add("同意，速发。");
//                popList.add("缓发。");
//                popList.add("请××阅后发。");
//                popList.add("同意，请××，××会签。");
//                popList.add("同意，请××审签。");
//
//                PopAdapter popAdapter = new PopAdapter(popList, DocumentQFActivity.this);
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

            case R.id.ducument_sy_qianfa:
                stopDialog();
                break;

            default:
                break;
        }
    }

    /**
     * 友情提示
     */
    private void stopDialog() {
//        new DialogUtil(DocumentApproveActivity.this, DocumentApproveActivity.this).showAlert("文件核发", "客户端暂不支持公文字号的导入", "知道了");
        final Dialog02 dialog02 = new Dialog02(this);
        dialog02.setContent("您确定终止当前文件吗？", Color.parseColor("#4f4f4f"));
        dialog02.setTitle("文件终止", Color.parseColor("#5184c3"));
        dialog02.setYesOnclickListener("确定", new Dialog02.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                popupWindowUtil = new PopupWindowUtil(DocumentQFActivity.this, "提交中...");
                popupWindowUtil.show();
                dialog02.dismiss();
                stopData();
            }
        });
        dialog02.setNoOnclickListener("取消", new Dialog02.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                dialog02.dismiss();
            }
        });

        dialog02.show();
    }

    private void stopData() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        final Intent intent = getIntent();
        String nBopId = intent.getStringExtra("NBopId");
        Call<JsonObject> call = api.gwnzTermination(nBopId, uid);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                popupWindowUtil.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (!jsonObject.isNull("data")) {
                        boolean data = jsonObject.getBoolean("data");
//                        Log.e("data",data+"");
//                        Toast.makeText(DocumentQFActivity.this, data ? "成功" : "失败", Toast.LENGTH_SHORT).show();
                        if (data)
                            okDialog();
                    }
                    Toast.makeText(DocumentQFActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                popupWindowUtil.dismiss();
            }
        });
    }

    private void okDialog() {
        final Dialog01 dialog01 = new Dialog01(this);
        dialog01.setTitle("文件终止", Color.parseColor("#31a739"));
        dialog01.setContent("您已终止当前文件", Color.parseColor("#4b4b4b"));
        dialog01.setLineGone(true);
        dialog01.setBtnImage(R.drawable.select_button_blue);
        dialog01.setSingleOnclickListener("知道了", new Dialog01.onsingleOnclickListener() {
            @Override
            public void onSingleClick() {
                dialog01.dismiss();
                Intent i = new Intent();
                i.putExtra("opId", getIntent().getStringExtra("opId"));
                i.putExtra("opState", "9");
                i.putExtra("docStatus", "1");
                setResult(520, i);

                finish();

            }
        });


        Window window = dialog01.getWindow();
        //设置显示动画
        window.setWindowAnimations(R.style.dialog_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;


        wl.y = -this.getWindowManager().getDefaultDisplay().getHeight() / 50;
        //设置显示位置
        dialog01.onWindowAttributesChanged(wl);//设置点击外围解散
        dialog01.setCanceledOnTouchOutside(true);


        dialog01.show();
    }

    /**
     *
     */
    private void getData1() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        final Intent intent = getIntent();
        String nBopId = intent.getStringExtra("NBopId");
        Call<JsonObject> call = api.gwnzQfEdit(nBopId, uid, document_guild_edit.getText().toString());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                popupWindowUtil.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (!jsonObject.isNull("data")) {
                        data = jsonObject.getJSONObject("data");
                        boolean result = data.getBoolean("result");
//                        Toast.makeText(DocumentQFActivity.this, result ? "修改成功" : "修改失败", Toast.LENGTH_SHORT).show();
                        if (result) {
                            new DialogUtil(DocumentQFActivity.this, DocumentQFActivity.this).showAlert("文件签发已完成！", "文件已签发，该文件将进行核发(会签)！", "知道了");
                            return;
                        }
                    }
                    Toast.makeText(DocumentQFActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
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
        popupWindowUtil = new PopupWindowUtil(DocumentQFActivity.this, "提交中...");
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
