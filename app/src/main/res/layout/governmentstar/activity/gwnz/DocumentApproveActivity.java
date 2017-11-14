package com.lanwei.governmentstar.activity.gwnz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.DocumentBase;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.LogUtils;
import com.lanwei.governmentstar.utils.ManagerUtils;
import com.lanwei.governmentstar.utils.PopupWindowUtil;
import com.lanwei.governmentstar.utils.YsPopupWindowUtil;
import com.lanwei.governmentstar.view.Dialog01;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//公文核发
//没有分送单位列表的数据格式没法解析需要解析一下
//docZh5只能模拟接受
public class DocumentApproveActivity extends DocumentBaseActivity implements OnClickListener, DialogUtil.OnClickListenner {
    private static final String TAG = DocumentApproveActivity.class.getSimpleName();
    private ImageView document_approve_select_fensong_icon;
    private LinearLayout document_approve_select_fensong;
    private List<String> listText;
    private EditText document_approve_select_text1, document_approve_select_text2, document_approve_select_text4, document_approve_select_text5;
    private TextView document_approve_select_text3;
    private PopupWindow popupWindow;
    private String docZh5;//第个数的显示问题
    private String deptList;//分送单位
    DocumentBase.Data dDataApp;
    YsPopupWindowUtil pop;

    private SharedPreferences change_position;
    private SharedPreferences.Editor editor;
    private JSONObject dataJson;
    private String docStatus;
    private LinearLayout gwzh_ll;
    private LinearLayout document_guild_layout;
    private String deptId = "";
    private TextView see;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_documentapprove;
    }

    @Override
    protected String getAction() {
        return "gwnzHf";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate()");
        init();
    }

    private void init() {

        document_approve_select_text1 = (EditText) findViewById(R.id.document_approve_select_text1);
        document_approve_select_text2 = (EditText) findViewById(R.id.document_approve_select_text2);
        document_approve_select_text3 = (TextView) findViewById(R.id.document_approve_select_text3);
        document_approve_select_text4 = (EditText) findViewById(R.id.document_approve_select_text4);
        document_approve_select_text5 = (EditText) findViewById(R.id.document_approve_select_text5);
        document_approve_select_fensong = (LinearLayout) findViewById(R.id.document_approve_select_fensong);
        document_approve_select_fensong_icon = (ImageView) findViewById(R.id.document_approve_select_fensong_icon);
        gwzh_ll = (LinearLayout) findViewById(R.id.gwzh_ll);
        document_guild_layout = (LinearLayout) findViewById(R.id.document_guild_layout);


        if (document_approve_select_fensong != null) {
            document_approve_select_fensong.setOnClickListener(this);
        }
        document_approve_select_text3.setOnClickListener(this);
        listText = new ArrayList<>();
        listText.add("发");
        listText.add("函");
        listText.add("办");
        listText.add("字");
        listText.add("分");
        listText.add("呈");
        listText.add("报");
        listText.add("请");
        getData();
    }

    /**
     *
     */
    private void getData() {
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        Call<JsonObject> call = api.documentFiction(getAction(), getIntent().getStringExtra("opId"));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response.body().getAsJsonObject("data").toString());

                    if (jsonObject == null || jsonObject.equals("")) {
                        Toast.makeText(DocumentApproveActivity.this, "空数据", Toast.LENGTH_SHORT).show();
                    } else {
                        DocumentBase documentBase = new DocumentBase();
                        dDataApp = new DocumentBase.Data();
                        dDataApp.setOpId(jsonObject.getString("opId"));
                        dDataApp.setDocTitle(jsonObject.getString("docTitle"));
                        dDataApp.setOpCreateName(jsonObject.getString("opCreateName"));
                        dDataApp.setDocType(jsonObject.getString("docType"));
                        dDataApp.setDocTheme(jsonObject.getString("docTheme"));
                        dDataApp.setIsHq(jsonObject.getString("isHq"));
                        dDataApp.setOpShName(jsonObject.getString("opShName"));
                        dDataApp.setOpCreateTime(jsonObject.getString("opCreateTime"));
                        dDataApp.setDocUrl(jsonObject.getString("docUrl"));
                        JSONArray jsonArray = jsonObject.getJSONArray("flowList");
                        List<DocumentBase.FlowList> flowList = new ArrayList<DocumentBase.FlowList>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            flowList.add(new DocumentBase.FlowList(jsonArray.getJSONObject(i).getString("flowStatus"),
                                    jsonArray.getJSONObject(i).getString("flowName"),
                                    jsonArray.getJSONObject(i).getString("flowImageUrl"),
                                    jsonArray.getJSONObject(i).getString("flowContent"),
                                    jsonArray.getJSONObject(i).getString("flowTime")));
                        }
                        for (int i = Integer.parseInt(flowList.get(flowList.size() - 1).getFlowStatus()) + 1; i < 9; i++) {//补齐八位
                            flowList.add(new DocumentBase.FlowList(i + "", "", "", "", ""));
                        }
                        dDataApp.setFlowList(flowList);
                        List<DocumentBase.DocStatus> fileLists = new Gson().
                                fromJson(jsonObject.getJSONArray("fileList").toString(), new TypeToken<List<DocumentBase.DocStatus>>() {
                                }.getType());
                        dDataApp.setFileList(fileLists);
                        dDataApp.setDocStatus(jsonObject.get("docStatus").toString());
                        documentBase.setData(dDataApp);

                        documentAdapter = new DocumentAdapter(documentBase.getData().getFlowList(), DocumentApproveActivity.this);
                        document_shenpi.setAdapter(documentAdapter);
                        data = new ArrayList<>();
                        for (int i = 0; i < documentBase.getData().getFileList().size(); i++) {
                            data.add(documentBase.getData().getFileList().get(i).getOpName());
                        }
                        adapter = new Adapter_Addtion();
                        rv.setAdapter(adapter);
                        document_title.setText(documentBase.getData().getDocTitle());
                        document_qicao.setText(documentBase.getData().getOpCreateName());
                        document_shenhe.setText(documentBase.getData().getOpShName());
                        document_qicaotime.setText(documentBase.getData().getOpCreateTime());
                        document_gongwen.setText(documentBase.getData().getDocType());
                        document_huiqian.setText(documentBase.getData().getIsHq());
                        document_shenhe.setText("审核人: " + documentBase.getData().getOpShName());
                        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String date = sDateFormat.format(new java.util.Date());
                        document_approve_select_text4.setText(date.split("-")[0]);
                        if (data.size() == 0) {
                            document_fujian.setVisibility(View.GONE);
                        }

                        webView.setVisibility(View.VISIBLE);

                        webView.getSettings().setJavaScriptEnabled(true);// 设置支持javascript脚本
                        webView.loadUrl(documentBase.getData().getDocUrl());
                        webView.setOnTouchListener(new View.OnTouchListener() {

                            @Override

                            public boolean onTouch(View v, MotionEvent ev) {

                                ((WebView) v).requestDisallowInterceptTouchEvent(false);
                                return false;

                            }

                        });
                        webView.setVerticalScrollBarEnabled(false);
                        webView.setHorizontalScrollBarEnabled(false);

                        //判断DocStatus，觉得是否隐藏
                        docStatus = dDataApp.getDocStatus();
                        if (docStatus.equals("1")) {
                            more.setVisibility(View.VISIBLE);
                            gwzh_ll.setVisibility(View.GONE);
                            document_guild_layout.setVisibility(View.VISIBLE);
                        }

                        if (docStatus.equals("0")) {
                            document_guild_layout.setVisibility(View.GONE);
                            more.setVisibility(View.GONE);
                            showDialog();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(DocumentApproveActivity.this, "getdata111" + t.toString(), Toast.LENGTH_SHORT).show();
                Log.d("LOG", t.toString());
            }
        });
        more.setText("完成");
        title.setText("公文核发");

    }


    /**
     * 友情提示客户端问题
     */
    private void showDialog() {
        final Dialog01 dialog01 = new Dialog01(this);
        dialog01.setTitle("文件核发", Color.parseColor("#00a7e4"));
        dialog01.setContent("移动端暂不支持公文字号写入，如需写入文件字号请使用电脑端处理。当前不支持预览！", Color.parseColor("#4b4b4b"));
        dialog01.setSingleOnclickListener("知道了", new Dialog01.onsingleOnclickListener() {
            @Override
            public void onSingleClick() {
                dialog01.dismiss();
            }
        });

        dialog01.show();
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


    /**
     * @param v
     */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        super.onClick(v);
        switch (v.getId()) {
            case R.id.more: {//完成
                Log.d("TAG", document_guild_edit.getText().toString());
                if (document_guild_edit.getText().toString().equals("")) {
                    ManagerUtils.showToast(this, "请填写意见");
                    return;
                }
                //有公文字号公文
                if (docStatus.equals("0")) {
                    if (document_approve_select_text1.getText().toString().equals("")) {
                        ManagerUtils.showToast(this, "请填写公文字号1");
                        return;
                    }
                    if (document_approve_select_text2.getText().toString().equals("")) {
                        ManagerUtils.showToast(this, "请填写公文字号2");
                        return;
                    }
                    if (document_approve_select_text3.getText().toString().equals("")) {
                        ManagerUtils.showToast(this, "请填写公文字号3");
                        return;
                    }
                    if (document_approve_select_text4.getText().toString().equals("")) {
                        ManagerUtils.showToast(this, "请填写公文字号4");
                        return;
                    }
                    if (document_approve_select_text5.getText().toString().equals("")) {
                        ManagerUtils.showToast(this, "请填写公文字号5");
                        return;
                    }
                }

                new DialogUtil(DocumentApproveActivity.this, this).showConfirm("是否完成公文核发？", "您是否已完成公文核发工作，公文核发归档后，文件起草部门方可转发！", "立即核发", "我再看看");


            }
            break;
            case R.id.document_approve_select_text3: {//选择文字
                OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int option2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        document_approve_select_text3.setText(listText.get(options1));
                    }
                }).setCancelText("取消").setSubmitText("确定").setContentTextSize(22).build();
                pvOptions.setPicker(listText);
                pvOptions.show();
            }
            break;
            case R.id.document_approve_select_fensong: {//选择政府
                Toast.makeText(this, "暂未开发该功能", Toast.LENGTH_SHORT).show();
//                deptId = "";
//                Intent intent = new Intent(DocumentApproveActivity.this, SelectListActivity.class);
//                startActivityForResult(intent, 18);
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
//                popList.add("核发完成，归档共享类型请设定xx有条件共享。");
//                popList.add("核发完成，主送至××，××。抄送至××，××。");
//                popList.add("核发完成，归档共享类型请设定不共享。");
//
//                PopAdapter popAdapter = new PopAdapter(popList, DocumentApproveActivity.this);
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

    private String gwzh1 = "";
    private String gwzh2 = "";
    private String gwzh3 = "";
    private String gwzh4 = "";
    private String gwzh5 = "";

    private void getdata2() {
        if (docStatus.equals("0")) {
            gwzh1 = document_approve_select_text1.getText().toString();
            gwzh2 = document_approve_select_text2.getText().toString();
            gwzh3 = document_approve_select_text3.getText().toString();
            gwzh4 = document_approve_select_text4.getText().toString();
            gwzh5 = document_approve_select_text5.getText().toString();
        }
        String uid = new GetAccount(this).opId();
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        Call<JsonObject> call = api.gwnzHfEdit(getIntent().getStringExtra("NBopId"), uid,
                document_guild_edit.getText().toString(), dDataApp.getDocStatus(), gwzh1, gwzh2, gwzh3, gwzh4, gwzh5, deptId);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                popupWindowUtil.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (!jsonObject.isNull("data")) {
                        dataJson = jsonObject.getJSONObject("data");
                        Log.e("核发提交", dataJson.toString());
                        boolean result = dataJson.getBoolean("result");
                        if (result) {
                            new DialogUtil(DocumentApproveActivity.this, DocumentApproveActivity.this).showAlert("文件核发", "您已将核发意见提交成功", "知道了");
                        }
                    }
                    Toast.makeText(DocumentApproveActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
        popupWindowUtil = new PopupWindowUtil(DocumentApproveActivity.this, "提交中...");
        popupWindowUtil.show();
        getdata2();
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
            Log.e("核发", getIntent().getStringExtra("opId") + "===" + dataJson.getString("opState") + "====" + dataJson.getString("docStatus"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setResult(520, in);

        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 18) {//收取选择的政府ID
            ArrayList<String> dptIds = data.getStringArrayListExtra("dptId");
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < dptIds.size(); i++) {
                if (dptIds.size() <= 0) {
                    deptId = "";
                    return;
                }
                if (dptIds.size() == 1) {
                    stringBuilder.append(dptIds.get(0));
                } else {
                    if (dptIds.size() == i + 1) {
                        stringBuilder.append(dptIds.get(i));
                    } else {
                        stringBuilder.append(dptIds.get(i)).append("，");
                    }
                }
            }
            deptId = stringBuilder.toString();
        }
    }

}
