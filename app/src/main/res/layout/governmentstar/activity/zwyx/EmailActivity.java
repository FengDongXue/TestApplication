package com.lanwei.governmentstar.activity.zwyx;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.http.CallBackYSAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 蓝威科技-技术部3 on 2017/4/18.
 */

public class EmailActivity extends AutoLayoutActivity implements View.OnClickListener {

    //创建，中间标题
    private TextView email_create, tv_address;
    //返回键
    private ImageView back, iv_contacts;
    //各布局 收件箱，已发送，临时邮件，已删除，收藏的邮件
    private RelativeLayout inbox_layout, sent_layout, temporary_layout, delete_layout, sc_layout;
    private TextView shou;
    private TextView temp;
    private int unReadCount;
    private int tempCount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.email_layout);

        initview();
    }


    private void initview() {
        email_create= (TextView) findViewById(R.id.email_create);  //创建    暂时隐藏掉
        tv_address = (TextView) findViewById(R.id.tv_address);
        back = (ImageView) findViewById(R.id.back);
        iv_contacts = (ImageView) findViewById(R.id.iv_contacts);
        inbox_layout = (RelativeLayout) findViewById(R.id.inbox_layout);
        sent_layout = (RelativeLayout) findViewById(R.id.sent_layout);
        temporary_layout = (RelativeLayout) findViewById(R.id.temporary_layout);
        delete_layout = (RelativeLayout) findViewById(R.id.delete_layout);
        sc_layout = (RelativeLayout) findViewById(R.id.sc_layout);
        shou = (TextView) findViewById(R.id.shou);
        temp = (TextView) findViewById(R.id.temp);

        email_create.setVisibility(View.VISIBLE);
        tv_address.setVisibility(View.VISIBLE);
        tv_address.setText("政务邮箱");
        back.setVisibility(View.VISIBLE);
        iv_contacts.setVisibility(View.GONE);
        back.setOnClickListener(this);
        email_create.setOnClickListener(this);
        inbox_layout.setOnClickListener(this);
        sent_layout.setOnClickListener(this);
        temporary_layout.setOnClickListener(this);
        delete_layout.setOnClickListener(this);
        sc_layout.setOnClickListener(this);
//        email_create.setVisibility(View.GONE);
        getData();
    }

    private void getData() {
        String userId = new GetAccount(this).opId(); //当前登录者Id
        RetrofitHelper.getInstance().getNoReadInfo(userId, new CallBackYSAdapter() {
            @Override
            protected void showErrorMessage(String message) {
            }

            @Override
            protected void parseJson(String data) {
                try {
                    JSONObject dataJson = new JSONObject(data);
                    JSONObject data1 = dataJson.getJSONObject("data");
                    tempCount = Integer.parseInt(data1.getString("tempCount"));
                    unReadCount = data1.getInt("unReadCount");

                    if (unReadCount < 1){
                        shou.setVisibility(View.GONE);
                    }else {
                        shou.setVisibility(View.VISIBLE);
                        shou.setText(String.valueOf(unReadCount));
                    }
                    if (tempCount < 1){
                        temp.setVisibility(View.GONE);
                    }else {
                        temp.setVisibility(View.VISIBLE);
                        temp.setText(String.valueOf(tempCount));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            //do something...
            Intent intent2 =new Intent();
            setResult(20,intent2);
            Log.e("desfdasdf1111 ","dgfdsgdg");
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.back:
                Intent intent2 =new Intent();
                setResult(20,intent2);
                Log.e("desfdasdf1111 ","dgfdsgdg");
                finish();
                break;
            case R.id.email_create:
                intent=new Intent(this,CreateEmailActivity.class);
                startActivity(intent);
                break;
            case R.id.inbox_layout:
                intent = new Intent(this, InboxActivity.class);
                intent.putExtra("shouRead", unReadCount);
                startActivityForResult(intent,201);
                break;
            case R.id.sent_layout:
                intent = new Intent(this, SentActivity.class);
                startActivity(intent);
                break;
            case R.id.temporary_layout:
                intent = new Intent(this, TemporaryActivity.class);
                intent.putExtra("tempRead", tempCount);
                startActivityForResult(intent,201);
                break;
            case R.id.delete_layout:
                intent = new Intent(this, DeleteActivity.class);
                startActivityForResult(intent,201);
                break;
            case R.id.sc_layout:
                intent = new Intent(this, CollectActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 201) {
            getData();
        }
    }
}
