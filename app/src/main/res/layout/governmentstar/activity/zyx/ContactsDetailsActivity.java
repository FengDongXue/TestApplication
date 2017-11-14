package com.lanwei.governmentstar.activity.zyx;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.CenterActivity;
import com.lanwei.governmentstar.bean.Contacts;
import com.lanwei.governmentstar.bean.ContactsDetails;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.CallBackAdapter;
import com.lanwei.governmentstar.http.RetrofitHelper;
import com.lanwei.governmentstar.view.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContactsDetailsActivity extends BaseActivity implements View.OnClickListener, View.OnLongClickListener {
    @InjectView(R.id.back)
    ImageView back;
    @InjectView(R.id.iv_contacts)
    CircleImageView ivContacts;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.tv_apl)
    TextView tvApl;
    @InjectView(R.id.civ_contacts)
    CircleImageView civContacts;
    @InjectView(R.id.name)
    TextView name;
    @InjectView(R.id.rank)
    TextView rank;
    @InjectView(R.id.unit)
    TextView unit;
    @InjectView(R.id.dpt)
    TextView dpt;
    @InjectView(R.id.gov)
    TextView gov;
    @InjectView(R.id.dq)
    TextView dq;
    @InjectView(R.id.email)
    TextView email;
    @InjectView(R.id.notice)
    TextView notice;
    @InjectView(R.id.phone)
    TextView phone;
    @InjectView(R.id.mobile)
    TextView mobile;
    @InjectView(R.id.sendmessage)
    Button sendmessage;
    private ContactsDetails contactsDetails;
    private final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private LinearLayout lnlMobile;
    private LinearLayout lnlPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4")); //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff
        setContentView(R.layout.activity_cantacts_details);
        ButterKnife.inject(this);
        //设置沉浸式状态栏,必须在setContentView方法之后执行
        ImageView icon = (ImageView) findViewById(R.id.iv_contacts);
        lnlMobile = (LinearLayout) findViewById(R.id.lnl_mobile);
        lnlPhone = (LinearLayout) findViewById(R.id.lnl_phone);
        back.setVisibility(View.VISIBLE);
        icon.setVisibility(View.GONE);
        tvAddress.setText("详情资料");
        ivContacts.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        tvAddress.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        email.setOnClickListener(this);
        notice.setOnClickListener(this);
        sendmessage.setOnClickListener(this);
        mobile.setOnClickListener(this);

        name.setOnLongClickListener(this);
        rank.setOnLongClickListener(this);
        unit.setOnLongClickListener(this);
        gov.setOnLongClickListener(this);
        dpt.setOnLongClickListener(this);
        dq.setOnLongClickListener(this);
        phone.setOnLongClickListener(this);
        mobile.setOnLongClickListener(this);


//        mobile.setTextColor(Color.parseColor("#00a7e4"));


        Intent intent = getIntent();
        // Contacts contacts = (Contacts) intent.getSerializableExtra("contacts");
        //  String opid = contacts.getOpId();
        String contactId = intent.getStringExtra("contactId");
        getData(contactId);
    }

    /**
     * 展示数据
     *
     * @param opid
     */
    private void getData(String opid) {
        //获取全部机关的数据
        RetrofitHelper.getInstance().getAccountDetailInfo(opid, new CallBackAdapter() {
            @Override
            protected void showErrorMessage(String message) {

            }

            @Override
            protected void parseJson(String data) {
                Gson gson = new Gson();
//                dataList = gson.fromJson(data, new TypeToken<List<ContactsDetails>>() {
//                }.getType())
                contactsDetails = gson.fromJson(data, ContactsDetails.class);

                showData(contactsDetails);
            }
        });

    }

    /**
     * 展示数据
     *
     * @param
     */
    private void showData(ContactsDetails contactsDetails) {
        // dataList.get(0).get
        Glide.with(this)
                .load(contactsDetails.getAccountlink())
                .into(civContacts);
//        name.setText("wangsss");
        name.setText(contactsDetails.getOpName());
        unit.setText(contactsDetails.getAccountDeptName());
        rank.setText("(" + contactsDetails.getAccountRoleName() + ")");
        gov.setText("天津市-" + contactsDetails.getAccountDeptName());
        dpt.setText(contactsDetails.getAccountSectorName());

        if (contactsDetails.getAccountPhone().equals("")) {
            phone.setText("<无>");
        } else {
            lnlPhone.setVisibility(View.VISIBLE);
            phone.setText(contactsDetails.getAccountPhone());
        }

        if (contactsDetails.getAccountMobile().equals("")) {
            mobile.setText("<无>");
        } else {
            lnlMobile.setVisibility(View.VISIBLE);
            mobile.setText(contactsDetails.getAccountMobile());
        }

        if (contactsDetails.getAccountAddress().equals("")) {
            dq.setText("<无>");
        } else {
            dq.setText(contactsDetails.getAccountAddress());
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.email:
                Toast.makeText(getApplicationContext(), "暂未开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.notice:
                Toast.makeText(getApplicationContext(), "暂未开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sendmessage:
                Toast.makeText(getApplicationContext(), "暂未开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mobile:

                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mobile.getText()));//跳转到拨号界面，同时传递电话号码
                startActivity(dialIntent);

                break;

        }
    }

    @Override
    public boolean onLongClick(View v) {
        TextView textView = (TextView) v;
        ClipboardManager cmb = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(textView.getText().toString().trim()); //将内容放入粘贴管理器,在别的地方长按选择"粘贴"即可
        cmb.getText();//获取粘贴信息
        Toast.makeText(getApplicationContext(), "复制文本成功", Toast.LENGTH_SHORT).show();
        return false;
    }
}
