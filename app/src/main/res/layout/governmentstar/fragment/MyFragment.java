package com.lanwei.governmentstar.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.CenterActivity;
import com.lanwei.governmentstar.activity.MyCollection_Activity;
import com.lanwei.governmentstar.activity.MyDocument_Acvity;
import com.lanwei.governmentstar.activity.Reminder_Activity;
import com.lanwei.governmentstar.activity.System_Activity;
import com.lanwei.governmentstar.activity.TemporaryActivity;
import com.lanwei.governmentstar.activity.lll.DocumentBaseCActivity;
import com.lanwei.governmentstar.activity.zyx.HomeActivity;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.PackageUtils;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by xong2 on 2017/3/16.
 */
@SuppressLint("ValidFragment")
public class MyFragment extends BaseHomeFragment implements View.OnClickListener {
    private Activity activity;
    private TextView tv_title;
    private ImageView header;
    private  TextView name;
    private TextView government;
    private ImageView ring;
    private TextView amount;
    private TextView amount_convey;

    private LinearLayout document;
    private LinearLayout collect;
    private LinearLayout file;
    private LinearLayout linearLayout;
    private RelativeLayout service;
    private LinearLayout system;
    private Logging_Success bean;
    private SharedPreferences preferences;
    private SharedPreferences.Editor ediotr_modify;
    private JSONObject dataJson;
    private View view2;

    public MyFragment() {
    }

    public MyFragment(Activity activity) {
        this.activity = activity;
    }

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );

       view2 = inflater.inflate( R.layout.fragment_my, container,false);

//        params.setMargins(0, 0, 0, 0);
//        linearLayout = new LinearLayout(inflater.getContext());
//        linearLayout.setLayoutParams(params);
//
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.addView(view2);
        initweight();
        loadImage();
        updateVersion();//版本更新方法
        return view2;
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//
//        Log.e("大师傅", "电风扇大师傅似的是否似懂非懂的是");
//
//    }


    public void loadImage(){
        String defString = PreferencesManager.getInstance(getActivity(), "accountBean").get("jsonStr");
        Gson gson = new Gson();
        bean = gson.fromJson(defString, Logging_Success.class);
        name.setText(bean.getData().getOpName());
        government.setText(bean.getData().getAccountDeptName() + "-" + bean.getData().getAccountSectorName());

        if (!bean.getData().getAccountlink().equals("") && bean.getData().getAccountlink() != null) {
            Picasso.with(getActivity()).load(bean.getData().getAccountlink()).memoryPolicy(MemoryPolicy.NO_CACHE).into(header);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        preferences = getActivity().getSharedPreferences("modify_photo", 0);
        Log.e("方的放的个v发的", "是否堵塞发给多个条件已经");

        // 重新上传头像时，切换到MyFragment时重新加载最新的头像
        if (preferences.getBoolean("isChanged", false)) {

            String defString = PreferencesManager.getInstance(getActivity(), "accountBean").get("jsonStr");
            Gson gson = new Gson();
            bean = gson.fromJson(defString, Logging_Success.class);

            if (!bean.getData().getAccountlink().equals("") && bean.getData().getAccountlink() != null) {
                Picasso.with(getActivity()).load(bean.getData().getAccountlink()).memoryPolicy(MemoryPolicy.NO_CACHE).into(header);
            }
            ediotr_modify = preferences.edit();
            ediotr_modify.putBoolean("isChanged", false).commit();
            Log.e("方的放的个v发的", bean.getData().getAccountlink());
        }
    }

    void initweight() {

        header = (ImageView) view2.findViewById(R.id.header);
        name = (TextView) view2.findViewById(R.id.name);
        government = (TextView) view2.findViewById(R.id.government);
        ring = (ImageView) view2.findViewById(R.id.ring);
        amount = (TextView) view2.findViewById(R.id.amount);
        ring.setVisibility(View.GONE);
        document = (LinearLayout) view2.findViewById(R.id.document);
        collect = (LinearLayout) view2.findViewById(R.id.collect);
        file = (LinearLayout) view2.findViewById(R.id.file);
        service = (RelativeLayout) view2.findViewById(R.id.service);
        system = (LinearLayout) view2.findViewById(R.id.system);
        amount_convey = (TextView) view2.findViewById(R.id.amount_convey);

        document.setOnClickListener(this);
        collect.setOnClickListener(this);
        file.setOnClickListener(this);
        service.setOnClickListener(this);
        system.setOnClickListener(this);
        ring.setOnClickListener(this);
        amount.setOnClickListener(this);

        // todo 初始化名字，和所属部门
        // todo 提醒的消息数目
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {

            case R.id.document:

                intent = new Intent(getActivity(), MyDocument_Acvity.class);
                getActivity().startActivity(intent);

                break;
            case R.id.collect:

                intent = new Intent(getActivity(), MyCollection_Activity.class);
                getActivity().startActivity(intent);
                // todo 点击我的收藏事件

                break;

            case R.id.file:

                intent = new Intent(getActivity(), TemporaryActivity.class);
                getActivity().startActivity(intent);
                break;

            case R.id.service:

                intent = new Intent(getActivity(), CenterActivity.class);
                getActivity().startActivity(intent);

                break;

            case R.id.system:

                intent = new Intent(getActivity(), System_Activity.class);
                getActivity().startActivity(intent);

                break;

            case R.id.ring:
            case R.id.amount:

                intent = new Intent(getActivity(), Reminder_Activity.class);
                getActivity().startActivity(intent);

                break;

        }
    }

    private void updateVersion() {
        String version = "";
        String versionname = PackageUtils.getVersion(getActivity());

        if (!TextUtils.isEmpty(versionname)) {
            // TODO: 2017/5/10  在这里获取到了版本号， 自动更新在这里做判断    然后操作
            version = versionname;
        }

        //获取服务器得版本号
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        Call<JsonObject> call = api.verifiedVersion(version, "1");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (!jsonObject.isNull("data")) {
                        dataJson = jsonObject.getJSONObject("data");
                        Log.e("version1", dataJson.toString());
                        boolean result = dataJson.getBoolean("result");

                        if (result == true) {
                            amount_convey.setVisibility(View.GONE);
                        } else {
                            amount_convey.setVisibility(View.VISIBLE);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(getActivity(), "网络连接有误", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
