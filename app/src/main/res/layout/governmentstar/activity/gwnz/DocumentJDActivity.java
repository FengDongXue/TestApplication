package com.lanwei.governmentstar.activity.gwnz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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

/**
 * Created by 蓝威科技—技术部2 on 2017/4/13.
 */

//公文校对
public class DocumentJDActivity extends DocumentBaseActivity implements View.OnClickListener, DialogUtil.OnClickListenner {
    private static final String TAG = DocumentJDActivity.class.getSimpleName();
    private ImageView ducument_sy_qianfa_image;
    private LinearLayout ducument_sy_qianfa;
    private PopupWindow popupWindow;
    private Dialog02 dialog02;
    private boolean isSuccess = false;
    private boolean isBule;
    private PopupWindowUtil popupWindowUtil;
    private JSONObject data;
    private LinearLayout reject;
    private EditText contetn;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_documentjd;
    }

    @Override
    protected String getAction() {
        return "gwnzJd";
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

        // TODO Auto-generated method stub
        ducument_sy_qianfa = (LinearLayout) findViewById(R.id.ducument_sy_qianfa);
        ducument_sy_qianfa.setOnClickListener(this);
        contetn = (EditText) findViewById(R.id.document_guild_edit);

        getData();
    }

    private void getData() {
        more.setVisibility(View.VISIBLE);
        more.setText("完成");
        title.setText("公文校对");
        title.setVisibility(View.VISIBLE);
//        lnl_shenhe.setVisibility(View.INVISIBLE);
        document_guild_edit.setHint("请输入校对意见/驳回意见...");
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
                    new DialogUtil(DocumentJDActivity.this, this).showConfirm("是否完成文件审阅？", "您是否已经完成审阅，并将此文件发送进行校对？", "审阅完成", "我再看看");
                }
            }
            break;

            case R.id.reject:
                if (contetn.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写驳回意见");
                    return;
                } else {
                    getRejectData(contetn.getText().toString(),this);
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
//                popList.add("已校对，请领导签发。");
//                popList.add("请按照以下修改：xxxxx。");
//                popList.add("已阅，无异议。");
//
//                PopAdapter popAdapter = new PopAdapter(popList, DocumentJDActivity.this);
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

            case R.id.ducument_sy_qianfa :
                if (document_guild_edit.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写意见");
                    return;
                }
                    jumpDialog();
                break;

            default:
                break;
        }
    }

    private void jumpDialog() {
        final Dialog02 dialog02 = new Dialog02(this);
        dialog02.setContent("您确定略过签发文件吗？", Color.parseColor("#4f4f4f"));
        dialog02.setTitle("略过签发", Color.parseColor("#5184c3"));
        dialog02.setLeftBtn(R.drawable.select_button_left, Color.WHITE);
        dialog02.setRightBtn(R.drawable.select_button_right, Color.WHITE);
        dialog02.setYesOnclickListener("确定", new Dialog02.onYesOnclickListener() {
            @Override
            public void onYesClick() {
                popupWindowUtil = new PopupWindowUtil(DocumentJDActivity.this, "提交中...");
                popupWindowUtil.show();
                dialog02.dismiss();
                jumpData();
            }
        });
        dialog02.setNoOnclickListener("取消", new Dialog02.onNoOnclickListener() {
            @Override
            public void onNoClick() {
                dialog02.dismiss();
            }
        });

        Window window = dialog02.getWindow();
        //设置显示动画
        window.setWindowAnimations(R.style.dialog_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;

        wl.y = -this.getWindowManager().getDefaultDisplay().getHeight() / 50;
        //设置显示位置
        dialog02.onWindowAttributesChanged(wl);//设置点击外围解散
        dialog02.setCanceledOnTouchOutside(true);

        dialog02.show();
    }

    private void jumpData() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        final Intent intent = getIntent();
        String nBopId = intent.getStringExtra("NBopId");
        Call<JsonObject> call = api.gwnzJdEdit(nBopId, uid, document_guild_edit.getText().toString(), "1");
        call.enqueue(new Callback<JsonObject>() {
            /**
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                dialog02.dismiss();
                popupWindowUtil.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (!jsonObject.isNull("data")) {
                        data = jsonObject.getJSONObject("data");
                        boolean result = data.getBoolean("result");
//                        Toast.makeText(DocumentJDActivity.this, result ? "修改成功" : "修改失败", Toast.LENGTH_SHORT).show();
                        if (result) {
                            new DialogUtil(DocumentJDActivity.this, DocumentJDActivity.this).showAlert("略过签发", "您已略过签发文件", "知道了");
                            return;
                        }
                    }
                    Toast.makeText(DocumentJDActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable throwable) {
                popupWindowUtil.dismiss();

            }

        });
    }

    private void getData1() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        final Intent intent = getIntent();
        String nBopId = intent.getStringExtra("NBopId");
        Call<JsonObject> call = api.gwnzJdEdit(nBopId, uid, document_guild_edit.getText().toString(), "0");
        call.enqueue(new Callback<JsonObject>() {
            /**
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                popupWindowUtil.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (!jsonObject.isNull("data")) {
                        data = jsonObject.getJSONObject("data");
                        boolean result = data.getBoolean("result");
//                        Toast.makeText(DocumentJDActivity.this, result ? "修改成功" : "修改失败", Toast.LENGTH_SHORT).show();
                        if (result) {
                            new DialogUtil(DocumentJDActivity.this, DocumentJDActivity.this).showAlert("文件校对完成！", "您已完成文件校对工作，请等待签发！", "知道了");
                            return;
                        }
                    }
                    Toast.makeText(DocumentJDActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
        popupWindowUtil = new PopupWindowUtil(DocumentJDActivity.this, "提交中...");
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
