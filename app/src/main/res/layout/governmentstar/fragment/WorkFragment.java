package com.lanwei.governmentstar.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.dzgd.Classify_Document_Activity;
import com.lanwei.governmentstar.activity.gwxf.DocuHanddown_Activity;
import com.lanwei.governmentstar.activity.gwxf.DocuHanddown_List_Actitivty;
import com.lanwei.governmentstar.activity.spsq.ApplyActivity;
import com.lanwei.governmentstar.activity.zwyx.EmailActivity;
import com.lanwei.governmentstar.activity.gwnz.DocumentActivity;
import com.lanwei.governmentstar.activity.zyx.DocumentHanddownActivity;
import com.lanwei.governmentstar.activity.zyx.DocumentShareActivity;
import com.lanwei.governmentstar.activity.zyx.GgNoticeActivity;
import com.lanwei.governmentstar.activity.zyx.HomeActivity;
import com.lanwei.governmentstar.activity.zyx.MyApplyActivity;
import com.lanwei.governmentstar.activity.zyx.MyhandActivity;
import com.lanwei.governmentstar.activity.zzdb.Document_Push_Activity;
import com.lanwei.governmentstar.bean.Inbox;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Return_Amount;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.PreferencesManager;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技—技术部2 on 2017/3/17.
 */
@SuppressLint("ValidFragment")
public class WorkFragment extends BaseHomeFragment implements View.OnClickListener {
    private Activity activity;
    private View view;

    private TextView notification;
    private TextView amount_notification;
    private TextView icloud;
    private TextView document_draw;
    private TextView amount_draw;
    private TextView file_convey;
    private TextView amount_convey;
    private TextView file_classify;
    private TextView amount_classify;
    private TextView amount_handdown;
    private TextView document_handdown;
    private TextView push;
    private TextView document_share;
    private TextView work_plan;
    private TextView apply;
    private TextView amount_apply;
    private TextView summary;
    private TextView share;
    private TextView email;
    private TextView upload;
    private TextView shaungwei;
    private TextView common;
    private Inbox inbox;
    private String tempCount;
    private int unReadCount;
    private TextView amount_email;


    public WorkFragment(Activity activity) {
        this.activity = activity;
    }

    public WorkFragment() {
    }

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );

        view = inflater.inflate(R.layout.fragment_jobs, container,false);

        initweight();


        // 获取bean;
//        String defString = PreferencesManager.getInstance(getActivity(),"accountBean").get("jsonStr");
//        Gson gson=new Gson();
//        Logging_Success bean=gson.fromJson(defString,Logging_Success.class);
//
//        // 获取opId和userId
//        String userId=bean.getData().getOpId();
//        GovernmentApi api= HttpClient.getInstance().getGovernmentApi();
//
//        Call<ArrayList<Return_Work>> call=api.main_work(userId);
//
//        call.enqueue(new Callback<ArrayList<Return_Work>>() {
//            @Override
//            public void onResponse(Call<ArrayList<Return_Work>> call, Response<ArrayList<Return_Work>> response) {
//
//                if(response.body()!=hardwork){
//                    list=response.body();
//                }
//                initweight();
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<Return_Work>> call, Throwable t) {
//
//                Toast.makeText(getActivity(),t.toString(),Toast.LENGTH_SHORT).show();
//                leadership = (TextView) view.findViewById(R.id.leadership);
//                notification = (TextView) view.findViewById(R.id.notification);
//                document_draw = (TextView) view.findViewById(R.id.document_draw);
//                file_convey = (TextView) view.findViewById(R.id.file_convey);
//
//                notification.setOnClickListener(WorkFragment.this);
//                document_draw.setOnClickListener(WorkFragment.this);
//                file_convey.setOnClickListener(WorkFragment.this);
//                leadership.setOnClickListener(WorkFragment.this);
//            }
//        });

//        params.setMargins(100, 100, 0, 0);

        getData();
        return_amount();
//        params.setMargins(0, 0, 0, 0);
//        LinearLayout linearLayout = new LinearLayout(inflater.getContext());
//        linearLayout.setLayoutParams(params);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.addView(view);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getData() {
        String userId = new GetAccount(activity).opId(); //当前登录者Id
        RetrofitHelper.getInstance().getNoReadInfo(userId, new CallBackYSAdapter() {
            @Override
            protected void showErrorMessage(String message) {

            }

            @Override
            protected void parseJson(String data) {
                try {
                    JSONObject dataJson = new JSONObject(data);
                    JSONObject data1 = dataJson.getJSONObject("data");
                    tempCount = data1.getString("tempCount");
                    unReadCount = data1.getInt("unReadCount");
                    int count = Integer.parseInt(tempCount) + unReadCount;      //总未读个数
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    void initweight() {


        notification = (TextView) view.findViewById(R.id.notification);
        notification.setSelected(true);
        amount_notification = (TextView) view.findViewById(R.id.amount_notification);

        icloud = (TextView) view.findViewById(R.id.icloud);

        document_draw = (TextView) view.findViewById(R.id.document_draw);
        amount_draw = (TextView) view.findViewById(R.id.amount_drawer);
        document_draw.setSelected(true);

        file_convey = (TextView) view.findViewById(R.id.file_convey);
        amount_convey = (TextView) view.findViewById(R.id.amount_convey);
        file_convey.setSelected(true);

        file_classify = (TextView) view.findViewById(R.id.file_classify);
        amount_classify = (TextView) view.findViewById(R.id.amount_classify);
        file_classify.setSelected(true);

        document_handdown = (TextView) view.findViewById(R.id.document_handdown);
        amount_handdown = (TextView) view.findViewById(R.id.amount_handdown);
        document_handdown.setSelected(true);

        push = (TextView) view.findViewById(R.id.push);
        push.setSelected(true);

        document_share = (TextView) view.findViewById(R.id.document_share);
//        document_share.setSelected(true);

        work_plan = (TextView) view.findViewById(R.id.work_plan);

        apply = (TextView) view.findViewById(R.id.apply);
        amount_apply = (TextView) view.findViewById(R.id.amount_apply);
        apply.setSelected(true);
        summary = (TextView) view.findViewById(R.id.summary);

        email = (TextView) view.findViewById(R.id.email);
        email.setSelected(true);
        amount_email = (TextView) view.findViewById(R.id.amount_email);

        upload = (TextView) view.findViewById(R.id.up_load);
        shaungwei = (TextView) view.findViewById(R.id.shuangwei);


        common = (TextView) view.findViewById(R.id.common);
        share = (TextView) view.findViewById(R.id.share);
//        jiance.setSelected(true);

        notification.setOnClickListener(this);
        document_draw.setOnClickListener(this);
        file_convey.setOnClickListener(this);
        icloud.setOnClickListener(this);
        file_classify.setOnClickListener(this);
        document_handdown.setOnClickListener(this);
        push.setOnClickListener(this);
        document_share.setOnClickListener(this);
        work_plan.setOnClickListener(this);
        apply.setOnClickListener(this);
        summary.setOnClickListener(this);
        email.setOnClickListener(this);
        upload.setOnClickListener(this);
        shaungwei.setOnClickListener(this);
        common.setOnClickListener(this);
        share.setOnClickListener(this);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 20){
            return_amount();
            Log.e("desfdasdf34444 ","dgfdsgdg");
        }
    }

    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()) {
            //公告通知
//            case R.id.notification:
//
////                intent = new Intent(activity, GgNoticeActivity.class);
////                startActivity(intent);
//                break;

            //公文拟制
            case R.id.document_draw:

                intent = new Intent(getActivity(), DocumentActivity.class);
                startActivityForResult(intent,001);

                break;
            //收文传阅
            case R.id.file_convey:

                intent = new Intent(getActivity(), MyhandActivity.class);
                startActivityForResult(intent,001);

                break;
            //公告通知
            case R.id.notification:

                intent = new Intent(getActivity(), GgNoticeActivity.class);
                startActivityForResult(intent,001);

                break;

            case R.id.email:
                intent = new Intent(getActivity(), EmailActivity.class);
                if (tempCount != null) {
                    intent.putExtra("tempCount", Integer.parseInt(tempCount));
                }
                intent.putExtra("shouCount", unReadCount);
                startActivityForResult(intent,001);
                break;

            case R.id.document_handdown:

                intent = new Intent(activity, DocuHanddown_List_Actitivty.class);
                startActivityForResult(intent,001);

                break;

            case R.id.document_share:

//                intent = new Intent(activity, DocumentShareActivity.class);
//                startActivity(intent);
                break;

            case R.id.apply:
                intent = new Intent(activity, ApplyActivity.class);
                startActivityForResult(intent,001);
//                Toast.makeText(getActivity(), "该功能尚未开放", Toast.LENGTH_SHORT).show();
                break;

            case R.id.push:
                intent = new Intent(activity, Document_Push_Activity.class);
                startActivityForResult(intent,001);
                break;

            case R.id.file_classify:
//
                intent = new Intent(activity ,Classify_Document_Activity.class);
                startActivity(intent);
//                Toast.makeText(getActivity(), "该功能尚未开放", Toast.LENGTH_SHORT).show();
                break;
            case R.id.icloud:
            case R.id.work_plan:

            case R.id.up_load:
            case R.id.summary:
            case R.id.shuangwei:
            case R.id.common:
            case R.id.share:

                Toast.makeText(getActivity(), "该功能尚未开放", Toast.LENGTH_SHORT).show();

                break;


        }

    }

    public void return_amount() {

        String defString3 = PreferencesManager.getInstance(getActivity(), "accountBean").get("jsonStr");
        Gson gson3 = new Gson();
        Logging_Success bean3 = gson3.fromJson(defString3, Logging_Success.class);

        GovernmentApi api3 = HttpClient.getInstance().getGovernmentApi();
        Call<Return_Amount> call2 = api3.return_amount_daiban(bean3.getData().getOpId());

        call2.enqueue(new Callback<Return_Amount>() {
            @Override
            public void onResponse(Call<Return_Amount> call, Response<Return_Amount> response) {

                if (response.body().getData() != null && !response.body().getData().equals("")) {

                    if (response.body().getData().getGgtz_num() <= 0) {
                        amount_notification.setVisibility(View.INVISIBLE);
                    } else {
                        amount_notification.setVisibility(View.VISIBLE);
                        amount_notification.setText(response.body().getData().getGgtz_num() + "");
                    }

                    if (response.body().getData().getGwnz_num() <= 0) {
                        amount_draw.setVisibility(View.INVISIBLE);
                    } else {
                        amount_draw.setVisibility(View.VISIBLE);
                        amount_draw.setText(response.body().getData().getGwnz_num() + "");
                    }

                    if (response.body().getData().getSwcy_num() <= 0) {
                        amount_convey.setVisibility(View.INVISIBLE);
                    } else {
                        amount_convey.setVisibility(View.VISIBLE);
                        amount_convey.setText(response.body().getData().getSwcy_num() + "");
                    }

                    if (response.body().getData().getZwyx_num() <= 0) {
                        amount_email.setVisibility(View.INVISIBLE);
                    } else {
                        amount_email.setVisibility(View.VISIBLE);
                        amount_email.setText(response.body().getData().getZwyx_num() + "");
                    }
                    if (response.body().getData().getSosq_num() <= 0) {
                        amount_apply.setVisibility(View.INVISIBLE);
                    } else {
                        amount_apply.setVisibility(View.VISIBLE);
                        amount_apply.setText(response.body().getData().getSosq_num() + "");
                    }

                    if ((response.body().getData().getGgtz_num()
                            +response.body().getData().getGwnz_num()
                            + response.body().getData().getSwcy_num()
                            +response.body().getData().getZwyx_num()
                            +response.body().getData().getWjxf_num()
                            +response.body().getData().getSosq_num()) <= 0) {
                        ((HomeActivity)getActivity()).amount_work.setVisibility(View.INVISIBLE);
                    } else {
                        ((HomeActivity)getActivity()).amount_work.setVisibility(View.VISIBLE);
                        ((HomeActivity)getActivity()).amount_work.setText((response.body().getData().getGgtz_num()
                                +response.body().getData().getGwnz_num()
                                + response.body().getData().getSwcy_num()
                                +response.body().getData().getZwyx_num()
                                +response.body().getData().getWjxf_num()
                                +response.body().getData().getSosq_num())+"");
                    }

                    if (response.body().getData().getWjxf_num() <= 0) {
                        amount_handdown.setVisibility(View.INVISIBLE);
                    } else {
                        amount_handdown.setVisibility(View.VISIBLE);
                        amount_handdown.setText(response.body().getData().getWjxf_num() + "");
                        Log.e("dfdsfsd","fdsfdsfdsfds");
                    }


                }

            }

            @Override
            public void onFailure(Call<Return_Amount> call, Throwable t) {

                Toast.makeText(getActivity(), "网络连接有误", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
