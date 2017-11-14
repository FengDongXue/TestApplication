package com.lanwei.governmentstar.activity.zyx;

import android.app.Notification;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jaeger.library.StatusBarUtil;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.LoggingActivity;
import com.lanwei.governmentstar.activity.Version_Update;
import com.lanwei.governmentstar.activity.spsq.view.PhotoPicker;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Return_Amount;
import com.lanwei.governmentstar.bean.Return_Many;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.fragment.AddressFragment;
import com.lanwei.governmentstar.fragment.MessageFragment;
import com.lanwei.governmentstar.fragment.MyFragment;
import com.lanwei.governmentstar.fragment.WorkFragment;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.receiver.MyReceiver;
import com.lanwei.governmentstar.service.RegisterService;
import com.lanwei.governmentstar.utils.Constant;
import com.lanwei.governmentstar.utils.DialogUtil;
import com.lanwei.governmentstar.utils.GetAccount;
import com.lanwei.governmentstar.utils.PackageUtils;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.utils.ScreenUtils;
import com.lanwei.governmentstar.utils.SharedPreferencesUtil;
import com.lanwei.governmentstar.utils.ShortcutBadger;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlTextView;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lanwei.governmentstar.R.id.iv_contacts;
import static com.lanwei.governmentstar.R.id.rb_wait;

/**
 * Created by 蓝威科技—技术部2 on 2017/3/15.
 */

public class HomeActivity extends BaseActivity implements View.OnClickListener, DialogUtil.OnClickListenner {
    private RadioGroup mButtons;

    /**
     * 该集合存放MessageFragment里面的三种Fragment
     **/
    private ArrayList<Fragment> messageFragment = new ArrayList<>();
    private RadioButton rb_message;
    private RadioButton rb_work;
    private RadioButton rb_address;
    private RadioButton rb_my;
    private TextView title;
    private DrawerLayout mDrawerLayout;
    private TextView tv_body;
    private TextView tv_cipher;
    private View titleLayout;
    private SharedPreferencesUtil sp;
    private ImageView menu_bg;

    private CircleImageView civ_myself;
    private TextView dml_name;
    private CircleImageView icon;
    private TextView tv_date;
    private RadioButton mes;
    private RadioButton wait;
    private RadioButton inform;
    private RadioGroup mButtons1;

    private PopupWindow popupWindow;
    private PopupWindow popupWindow2;
    private ImageView iv_add;
    private TextView wait_sort, inform_sort;
    private String opName;
    private String deptName;
    private String sectorName;
    private RelativeLayout rv;
    private JSONObject dataJson;
    private FragmentManager supportFragmentManager;
    private FrameLayout daiban;
    private SharedPreferences amount_ShortcutBadger;
    private Boolean shift_message = false;
    private Boolean shift_my = false;
    private Boolean is_click_my =false;
    private Boolean is_click_work =false;
    private Boolean is_click_info =false;
    private Boolean is_click_wait =false;

    /*
    * 上一次界面 onSaveInstanceState 之前的tab被选中的状态 key 和 value
    */
    private static final String PRV_SELINDEX = "PREV_SELINDEX";
    private int selindex = 0;
    /**
     * Fragment的TAG 用于解决app内存被回收之后导致的fragment重叠问题
     */
    private static final String[] FRAGMENT_TAG = {"msgfrag", "contacfrag", "actfrag", "settfrag", "wait", "info", "mes"};

    private WorkFragment workFragment;
    private AddressFragment addressFragment;
    private MyFragment myFragment;
    private MessageFragment messagFragment;
    private WaitFragment waitFragment;
    private MesFragment mesFragment;
    private InformFragment infoFragment;
    private ArrayList<Fragment> fm;
    private MyReceiver receiver;

    private static final String TAG = "MyReceiver";
    private Notification notification;
    private TextView amount_information;
    private TextView amount_daiban;
    private TextView amount_notification;
    public TextView amount_work;
    public TextView dptName;
    public TextView setName;
    // 切换用户
    public LinearLayout shift_user;
    private LinearLayout amount_linearlayout;
    private Logging_Success bean;
    private GovernmentApi api;


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        supportFragmentManager = getSupportFragmentManager();
        initFragment();
        if (savedInstanceState != null) {
            //读取上一次界面Save的时候tab选中的状态
            selindex = savedInstanceState.getInt(PRV_SELINDEX, selindex);
            messagFragment = (MessageFragment) supportFragmentManager.findFragmentByTag(FRAGMENT_TAG[0]);
            workFragment = (WorkFragment) supportFragmentManager.findFragmentByTag(FRAGMENT_TAG[1]);
            addressFragment = (AddressFragment) supportFragmentManager.findFragmentByTag(FRAGMENT_TAG[2]);
            myFragment = (MyFragment) supportFragmentManager.findFragmentByTag(FRAGMENT_TAG[3]);

            waitFragment = (WaitFragment) supportFragmentManager.findFragmentByTag(FRAGMENT_TAG[4]);
            infoFragment = (InformFragment) supportFragmentManager.findFragmentByTag(FRAGMENT_TAG[5]);
            mesFragment = (MesFragment) supportFragmentManager.findFragmentByTag(FRAGMENT_TAG[6]);
        }

        titleLayout = findViewById(R.id.layout_title);
        title = (TextView) findViewById(R.id.tv_address);
        amount_information = (TextView) findViewById(R.id.amount_information);
        amount_daiban = (TextView) findViewById(R.id.amount_daiban);
        amount_notification = (TextView) findViewById(R.id.amount_notification);
        amount_linearlayout = (LinearLayout) findViewById(R.id.amount_linearlayout);
        amount_work = (TextView) findViewById(R.id.amount_work);

        wait_sort = (TextView) findViewById(R.id.wait_sort);
        shift_user = (LinearLayout) findViewById(R.id.shift_user);
        inform_sort = (TextView) findViewById(R.id.inform_sort);
        rv = (RelativeLayout) findViewById(R.id.rv);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        iv_add.setOnClickListener(this);
        wait_sort.setOnClickListener(this);
        inform_sort.setOnClickListener(this);
        shift_user.setOnClickListener(this);

//        updateVersion();//版本更新方法
        jumpPopwindow();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        icon = (CircleImageView) findViewById(iv_contacts);
        menu_bg = (ImageView) findViewById(R.id.iv_drawer_bg);

        sp = new SharedPreferencesUtil();
        sp.saveData(this, Constant.VERSION, "1.0.0");
        //如果每个界面都可以打开侧拉菜单的话，将侧拉菜单这样写就可以      如果只是个别的fragment能打开的话，需要重新做判断
        //点击头像打开侧拉菜单的话   再 头像的点击事件里  实现该方法  如下
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        StatusBarUtil.setTranslucentForDrawerLayout(this, mDrawerLayout, 0);

        selectTab(4);
        mButtons = (RadioGroup) findViewById(R.id.home_rg_buttons);
        mButtons1 = (RadioGroup) findViewById(R.id.mes_rg_buttons);
        mes = (RadioButton) findViewById(R.id.rb_mes);
        wait = (RadioButton) findViewById(rb_wait);
        inform = (RadioButton) findViewById(R.id.rb_inform);

        rb_message = (RadioButton) findViewById(R.id.home_message);
        rb_work = (RadioButton) findViewById(R.id.home_work);
        rb_address = (RadioButton) findViewById(R.id.home_address);
        rb_my = (RadioButton) findViewById(R.id.home_my);

        initData();

        String defString = PreferencesManager.getInstance(this, "accountBean").get("jsonStr");
        Gson gson = new Gson();
        bean = gson.fromJson(defString, Logging_Success.class);

//        if(bean.getOtherLogin().equals("1")){
//            shift_user.setVisibility(View.VISIBLE);
//        }else{
//            shift_user.setVisibility(View.GONE);
//        }
        mes.setVisibility(View.VISIBLE);
        wait.setVisibility(View.VISIBLE);
        inform.setVisibility(View.VISIBLE);
        wait.setEnabled(false);//默认显示待办页
        mes.setEnabled(true);
        inform.setEnabled(true);

        rb_message.setEnabled(false);
        rb_work.setEnabled(true);
        rb_address.setEnabled(true);
        rb_my.setEnabled(true);
        wait_sort.setVisibility(View.VISIBLE);

        initView();

        setBg("7");//设置名片默认选择“7”  第一张

        api= HttpClient.getInstance().getGovernmentApi();
        civ_myself = (CircleImageView) findViewById(R.id.civ_myself);

        Logging_Success bean = new GetAccount(this).accountBean();
        if (bean != null) {
            String accountLink = bean.getData().getAccountlink();
            if (accountLink.equals("")) {
                return;
            }
            //获取头像
            Picasso.with(HomeActivity.this).load(accountLink).memoryPolicy(MemoryPolicy.NO_CACHE).into(icon);
            Picasso.with(HomeActivity.this).load(accountLink).memoryPolicy(MemoryPolicy.NO_CACHE).into(civ_myself);
            //获取名片
            setBg(bean.getData().getAccountCard());
            //获取资料
            dml_name = (TextView) findViewById(R.id.name);

            opName = new GetAccount(this).opName();//姓名
            deptName = new GetAccount(this).dptName();//机关名
            sectorName = new GetAccount(this).sectorName();//机关名

            dptName = (TextView) findViewById(R.id.unit2);
            setName = (TextView) findViewById(R.id.unit3);

            dptName.setText(deptName);
            setName.setText("-" + sectorName);
            dml_name.setText(opName);//设置当前登录的人
        }

        //启动服务
        startService(new Intent(HomeActivity.this, RegisterService.class));
        //注册广播接收器
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.lanwei.governmentstar.service");
        HomeActivity.this.registerReceiver(receiver, filter);

//        Intent intent = new Intent(this, RegisterService.class);
//        startService(intent);
        TextView tv = new TextView(this);
        Intent intent1 = getIntent();
        if (null != intent1) {
            Bundle bundle = intent1.getExtras();
        }
        addContentView(tv, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

        setAlias();//调用设置别名的方法
        setStyleBasic();//设置通知栏基础布局

    }


    @Override
    protected void onResume() {
        super.onResume();
//        return_amount();
    }

    /**
     * 设置别名
     */
    private void setAlias() {

        String alias = new GetAccount(this).login();

        // 调用 Handler 来异步设置别名
        mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
    }

    /**
     * 极光推送的处理消息的回调
     */
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
//            PersonState.showToast(logs, getApplicationContext());
        }
    };

    private static final int MSG_SET_ALIAS = 1001;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    JPushInterface.setAliasAndTags(HomeActivity.this, (String) msg.obj, null, mAliasCallback);
                    break;
                default:
                    Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };


    /**
     * 设置通知提示方式 - 基础属性
     */
    private void setStyleBasic() {
        BasicPushNotificationBuilder builder2 = new BasicPushNotificationBuilder(
                HomeActivity.this);
        builder2.statusBarDrawable = R.drawable.icon;

        if (this.notification == null) {
            this.notification = new Notification(R.drawable.icon,
                    "A new notification", System.currentTimeMillis());
        }

//        this.notification.flags |= Notification.FLAG_AUTO_CANCEL;
//        int allNum = this.notification.number += 1;
//
//        Log.e("111",allNum + "");
//
//        ShortcutBadger.applyCount(HomeActivity.this, allNum);// 发送未读消息数目广播

        builder2.notificationFlags = Notification.FLAG_AUTO_CANCEL; // 设置为自动消失
        builder2.notificationDefaults = Notification.DEFAULT_SOUND
                | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS; // 设置为铃声与震动与呼吸灯都要
        JPushInterface.setPushNotificationBuilder(2, builder2);
    }

    @Override
    protected void onDestroy() {
        //结束服务
        stopService(new Intent(HomeActivity.this, RegisterService.class));
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    private void updateVersion() {
        String version = "";
        String versionname = PackageUtils.getVersion(this);

        if (!TextUtils.isEmpty(versionname)) {
            //// TODO: 2017/5/10  在这里获取到了版本号， 自动更新在这里做判断    然后操作
            version = versionname;
        }

        //获取服务器得版本号
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        Call<JsonObject> call = api.verifiedVersion(version, "2");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (!jsonObject.isNull("data")) {
                        dataJson = jsonObject.getJSONObject("data");
                        Log.e("version1", dataJson.toString());
                        boolean result = dataJson.getBoolean("result");
                        String version2 = dataJson.getString("version");
                        String explain = dataJson.getString("explain");

                        if (!result) {
//                            弹出跟新对话框

                            new DialogUtil(HomeActivity.this, HomeActivity.this).showConfirm("版本更新",  version2 +"版更新内容" + "\n" + explain, "确定", "取消");

                        } else {
                            //不需要跟新
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

         Toast.makeText(HomeActivity.this, "网络连接有误!", Toast.LENGTH_SHORT).show();
            }
        });
    }



    void jumpPopwindow(){

        String version = "";
        String versionname = PackageUtils.getVersion(this);

        if (!TextUtils.isEmpty(versionname)) {
            //// TODO: 2017/5/10  在这里获取到了版本号， 自动更新在这里做判断    然后操作
            version = versionname;
        }

        //获取服务器得版本号
        GovernmentApi api = HttpClient.getInstance().getGovernmentApi();
        Call<JsonObject> call = api.verifiedVersion(version, "2");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());
                    if (!jsonObject.isNull("data")) {
                        dataJson = jsonObject.getJSONObject("data");
                        Log.e("version1", dataJson.toString());
                        boolean result = dataJson.getBoolean("result");
                        final String version2 = dataJson.getString("version");
                        final String explain = dataJson.getString("explain");

                        if (!result) {
                            // 加载popupwindow的布局

                            // 弹出popupwindow前，调暗屏幕的透明度
                            WindowManager.LayoutParams lp2 = getWindow().getAttributes();
                            lp2.alpha=(float) 0.8;
                            getWindow().setAttributes(lp2);

                                    View view = getLayoutInflater().inflate(R.layout.activity_update_popupwindow, null);
                                    popupWindow2 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                                    ((TextView)view.findViewById(R.id.version)).setText("最新版本："+version2);
                                    ((HtmlTextView)view.findViewById(R.id.update_log)).setHtml(explain);
                                    // 点击屏幕之外的区域可否让popupwindow消失
                                    popupWindow2.setFocusable(false);
                                    popupWindow2.setBackgroundDrawable(new BitmapDrawable());
                                    popupWindow2.setOnDismissListener(new PoponDismissListener());

                                    final View rootview = LayoutInflater.from(HomeActivity.this).inflate(R.layout.activity_home, null);
                                    // 设置popupwindow的显示位置

                                    //修正后代码
                                    findViewById(R.id.layout_title).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            popupWindow2.showAtLocation(rootview, Gravity.CENTER, 0, 0);
                                        }
                                    });

                                    view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            popupWindow2.dismiss();
                                        }
                                    });

                                    view.findViewById(R.id.vertify).setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            popupWindow2.dismiss();
                                            Intent intent = new Intent(HomeActivity.this, Version_Update.class);
                                            startActivity(intent, null);

                                        }
                                    });


//                            new DialogUtil(HomeActivity.this, HomeActivity.this).showConfirm("版本更新",  version2 +"版更新内容" + "\n" + explain, "确定", "取消");

                        } else {
                            //不需要跟新
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Toast.makeText(HomeActivity.this, "网络连接有误!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //加载侧边栏
    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        tv_body = (TextView) findViewById(R.id.tv_body);
        tv_cipher = (TextView) findViewById(R.id.tv_cipher);
        tv_date = (TextView) findViewById(R.id.tv_date);

        tv_body.setOnClickListener(this);
        tv_cipher.setOnClickListener(this);
        tv_date.setOnClickListener(this);

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {

            /**
             * @param drawerView
             * @param slideOffset   偏移(0-1)
             */
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // 导航图标渐变效果
                //ivNavigation.setAlpha(1 - slideOffset);
                // 判断是否左菜单并设置移动(如果不这样设置,则主页面的内容不会向右移动)
                if (drawerView.getTag().equals("left")) {
                    View content = mDrawerLayout.getChildAt(0);
                    int offset = (int) (drawerView.getWidth() * slideOffset);
                    content.setTranslationX(offset);

                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            /**
             * 当抽屉滑动状态改变的时候被调用
             * 状态值是STATE_IDLE（闲置-0），STATE_DRAGGING（拖拽-1），STATE_SETTLING（固定-2）中之一。
             * 抽屉打开的时候，点击抽屉，drawer的状态就会变成STATE_DRAGGING，然后变成STATE_IDLE.
             *
             * @param newState
             */
            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    /**
     * 加载数据
     */
    private void initData() {

        mButtons.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

                switch (checkedId) {
                    case R.id.home_message:
                        titleLayout.setVisibility(View.VISIBLE);
                        title.setVisibility(View.GONE);
                        mes.setVisibility(View.VISIBLE);
                        wait.setVisibility(View.VISIBLE);
                        inform.setVisibility(View.VISIBLE);
                        wait_sort.setVisibility(View.VISIBLE);
                        inform_sort.setVisibility(View.GONE);
                        amount_linearlayout.setVisibility(View.VISIBLE);
                        mes.setEnabled(true);
                        wait.setEnabled(false);//默认显示信息页
                        inform.setEnabled(true);
                        rb_message.setEnabled(false);
                        rb_work.setEnabled(true);
                        rb_address.setEnabled(true);
                        rb_my.setEnabled(true);
                        iv_add.setVisibility(View.GONE);
                        //当选择消息的时候，默认选中待办选项卡
                        mButtons1.check(R.id.rb_wait);
                        selectTab(0);
//                        return_amount();
                        break;
                    case R.id.home_work:
                        titleLayout.setVisibility(View.VISIBLE);
                        title.setVisibility(View.VISIBLE);
                        mes.setVisibility(View.GONE);
                        wait.setVisibility(View.GONE);
                        inform.setVisibility(View.GONE);
                        wait_sort.setVisibility(View.GONE);
                        inform_sort.setVisibility(View.GONE);
                        amount_linearlayout.setVisibility(View.GONE);
                        title.setText("工作");
                        rb_message.setEnabled(true);
                        rb_work.setEnabled(false);
                        rb_address.setEnabled(true);
                        rb_my.setEnabled(true);
                        iv_add.setVisibility(View.GONE);
                        selectTab(1);
                        // 标记  工作  模块是否点击过，以便决定切换用户时是否可直接调用方法
                        is_click_work = true;
                        break;
                    case R.id.home_address:
                        titleLayout.setVisibility(View.VISIBLE);
                        title.setVisibility(View.VISIBLE);
                        mes.setVisibility(View.GONE);
                        wait.setVisibility(View.GONE);
                        inform.setVisibility(View.GONE);
                        wait_sort.setVisibility(View.GONE);
                        inform_sort.setVisibility(View.GONE);
                        amount_linearlayout.setVisibility(View.GONE);
                        title.setText("通讯录");
                        rb_message.setEnabled(true);
                        rb_work.setEnabled(true);
                        rb_address.setEnabled(false);
                        rb_my.setEnabled(true);
                        iv_add.setVisibility(View.GONE);
                        amount_notification.setVisibility(View.INVISIBLE);
                        amount_daiban.setVisibility(View.INVISIBLE);
                        amount_information.setVisibility(View.INVISIBLE);
                        selectTab(2);
                        break;
                    case R.id.home_my:
                        titleLayout.setVisibility(View.GONE);
                        title.setVisibility(View.VISIBLE);
                        mes.setVisibility(View.GONE);
                        wait.setVisibility(View.GONE);
                        inform.setVisibility(View.GONE);
                        rb_message.setEnabled(true);
                        rb_work.setEnabled(true);
                        rb_address.setEnabled(true);
                        rb_my.setEnabled(false);
                        iv_add.setVisibility(View.GONE);
                        amount_linearlayout.setVisibility(View.GONE);
                        selectTab(3);
                        // 标记  我的  模块是否点击过，以便决定切换用户时是否可直接调用方法
                        is_click_my = true;
                        break;
                }

            }
        });


        mButtons1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case rb_wait:
                        titleLayout.setVisibility(View.VISIBLE);
                        mes.setVisibility(View.VISIBLE);
                        wait.setVisibility(View.VISIBLE);
                        inform.setVisibility(View.VISIBLE);
                        mes.setEnabled(true);//默认显示待办页
                        wait.setEnabled(false);
                        inform.setEnabled(true);
                        iv_add.setVisibility(View.GONE);
                        wait_sort.setVisibility(View.VISIBLE);
                        inform_sort.setVisibility(View.GONE);
                        is_click_wait = true;
                        selectTab(4);
                        break;
                    case R.id.rb_inform:
                        titleLayout.setVisibility(View.VISIBLE);
                        mes.setVisibility(View.VISIBLE);
                        wait.setVisibility(View.VISIBLE);
                        inform.setVisibility(View.VISIBLE);
                        mes.setEnabled(true);//默认显示通知页
                        wait.setEnabled(true);
                        inform.setEnabled(false);
                        iv_add.setVisibility(View.GONE);
                        wait_sort.setVisibility(View.GONE);
                        inform_sort.setVisibility(View.VISIBLE);
                        selectTab(5);
                        // 标记  信息  模块是否点击过，以便决定切换用户时是否可直接调用方法
                        is_click_info = true;
                        break;
                    case R.id.rb_mes:
                        titleLayout.setVisibility(View.VISIBLE);
                        mes.setVisibility(View.VISIBLE);
                        wait.setVisibility(View.VISIBLE);
                        inform.setVisibility(View.VISIBLE);
                        mes.setEnabled(false);//默认显示信息页
                        wait.setEnabled(true);
                        inform.setEnabled(true);
                        iv_add.setVisibility(View.VISIBLE);
                        wait_sort.setVisibility(View.GONE);
                        inform_sort.setVisibility(View.GONE);
                        selectTab(6);
                        break;
                }
            }
        });

    }

    /**
     * 切换fragment
     *
     * @param selindex
     */
    private void selectTab(int selindex) {

        this.selindex = selindex;
        final FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        hideFragment(transaction);
        switch (selindex) {
            case 0:
                if (!waitFragment.isAdded()) {
                    transaction.add(R.id.home_fl_pagers, waitFragment, FRAGMENT_TAG[4]);
//                    transaction.show(messagFragment);
                    transaction.show(waitFragment);
                } else {
//                    transaction.show(messagFragment);
                    transaction.show(waitFragment);
                }
                //
//                if(shift_message){
                    waitFragment.onRefresh();
                    Log.e("waitFragment", "二次点击waitFragment直接调用yeah");
//                }
                return_amount();
                break;
            case 1:
                if (!workFragment.isAdded()) {
                    transaction.add(R.id.home_fl_pagers, workFragment, FRAGMENT_TAG[1]);
                    transaction.show(workFragment);
                } else {
                    transaction.show(workFragment);
                }
                if(is_click_work){
                    workFragment.return_amount();
                    Log.e("workFragment", "二次点击workFragment直接调用yeah");
                }
                break;
            case 2:
                if (!addressFragment.isAdded()) {
                    transaction.add(R.id.home_fl_pagers, addressFragment, FRAGMENT_TAG[2]);
                    transaction.show(addressFragment);
                } else {
                    transaction.show(addressFragment);
                }
                break;
            case 3:
                if (!myFragment.isAdded()) {
                    transaction.add(R.id.home_fl_pagers, myFragment, FRAGMENT_TAG[3]);
                    transaction.show(myFragment);
                } else {
                    transaction.show(myFragment);
                }

                if(shift_my && is_click_my){
                    shift_my =false;
                    myFragment.loadImage();
                    Log.e("myFragment", "二次点击myFragment直接调用yeah");
                }

                break;
            case 4:
                if (!waitFragment.isAdded()) {
                    transaction.add(R.id.home_fl_pagers, waitFragment, FRAGMENT_TAG[4]);
                    transaction.show(waitFragment);
                } else {
                    transaction.show(waitFragment);
                }
                if(is_click_wait){
                    waitFragment.onRefresh();
                    Log.e("waitFragment", "二次点击waitFragment直接调用yeah");
                }
                break;
            case 5:
                if (!infoFragment.isAdded()) {
                    transaction.add(R.id.home_fl_pagers, infoFragment, FRAGMENT_TAG[5]);
                    transaction.show(infoFragment);
                } else {
                    transaction.show(infoFragment);
                }
                if(is_click_info){
                    infoFragment.onRefresh();
                    Log.e("infoFragment", "二次点击infoFragment直接调用yeah");
                }

                break;
            case 6:
                if (!mesFragment.isAdded()) {
                    transaction.add(R.id.home_fl_pagers, mesFragment, FRAGMENT_TAG[6]);
                    transaction.show(mesFragment);
                } else {
                    transaction.show(mesFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();//比commit更加能够保证提交成功
    }

    private void initFragment() {
        workFragment = new WorkFragment(HomeActivity.this);
        myFragment = new MyFragment(HomeActivity.this);
        addressFragment = new AddressFragment(HomeActivity.this);
        messagFragment = new MessageFragment(HomeActivity.this);
        waitFragment = new WaitFragment(HomeActivity.this);
        infoFragment = new InformFragment(HomeActivity.this);
        mesFragment = new MesFragment(HomeActivity.this);
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (messagFragment != null) {
            transaction.hide(messagFragment);
        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }
        if (workFragment != null) {
            transaction.hide(workFragment);
        }
        if (addressFragment != null) {
            transaction.hide(addressFragment);
        }
        if (waitFragment != null) {
            transaction.hide(waitFragment);
        }
        if (infoFragment != null) {
            transaction.hide(infoFragment);
        }
        if (mesFragment != null) {
            transaction.hide(mesFragment);
        }

    }

    /**
     * 自定义NavigationIcon设置关联DrawerLayout
     */
    private void toggle() {
        int drawerLockMode = mDrawerLayout.getDrawerLockMode(GravityCompat.START);
        if (mDrawerLayout.isDrawerVisible(GravityCompat.START) && (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_OPEN)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (drawerLockMode != DrawerLayout.LOCK_MODE_LOCKED_CLOSED) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_body:
                Intent intent1 = new Intent(getApplicationContext(), PersonalDataActivity.class);
                startActivityForResult(intent1, 100);
                break;
            case R.id.shift_user:

                Call<Logging_Success> call= api.swichLogin(bean.getData().getOpId(),bean.getData().getAccountLogin());

                call.enqueue(new Callback<Logging_Success>() {
                    @Override
                    public void onResponse(Call<Logging_Success> call, Response<Logging_Success> response) {
                        if (response.body().getData() != null){

                            Gson gson = new Gson();
                            PreferencesManager.getInstance(HomeActivity.this, "accountBean").put("jsonStr", gson.toJson(response.body()));
                            String defString = PreferencesManager.getInstance(HomeActivity.this, "accountBean").get("jsonStr");
                            Gson gson2 = new Gson();
                            bean = gson2.fromJson(defString, Logging_Success.class);
                            dptName.setText(bean.getData().getAccountDeptName());
                            setName.setText("-" + bean.getData().getAccountSectorName());
                            //获取头像
                            Picasso.with(HomeActivity.this).load(bean.getData().getAccountlink()).memoryPolicy(MemoryPolicy.NO_CACHE).into(icon);
                            Picasso.with(HomeActivity.this).load(bean.getData().getAccountlink()).memoryPolicy(MemoryPolicy.NO_CACHE).into(civ_myself);
                            Log.e("",bean.getData().getOpId());
                            Toast.makeText(HomeActivity.this, "切换用户成功!", Toast.LENGTH_SHORT).show();
                            // 判断现在是哪个Fragment在显示，以便即使改变需要改变的内容，并且做相关标记以便切换到其他fragment时刷新数据
                            if(!messagFragment.isHidden()){
                                shift_my = true;
                            }
                            if(!myFragment.isHidden()){
                                shift_message = true;
                                shift_my = true;
                                myFragment.loadImage();
                            }

                            if(!workFragment.isHidden()){
                                shift_message = true;
                                shift_my = true;
                                workFragment.return_amount();
                            }
                            if(!addressFragment.isHidden()){
                                shift_message = true;
                            }

                            if(!waitFragment.isHidden()){
                                shift_my = true;
                                waitFragment.onRefresh();
                            }
                            if(!infoFragment.isHidden()){
                                shift_my = true;
                                infoFragment.onRefresh();
                            }
                            if(!mesFragment.isHidden()){
                                shift_my = true;
                            }

                            return_amount();
                        }
                    }
                    @Override
                    public void onFailure(Call<Logging_Success> call, Throwable t) {
                        Toast.makeText(HomeActivity.this, "网络连接有误!", Toast.LENGTH_SHORT).show();

                    }
                });

                break;
            case R.id.tv_cipher:
                Intent intent2 = new Intent(getApplicationContext(), PasswordActivity.class);
                startActivity(intent2);
                break;
            case R.id.civ_contacts:
                //手动打开关闭的侧拉菜单
                toggle();
                break;
            case R.id.tv_date:
//                PhotoPicker.builder()
//                        .setShowCamera(false)
//                        .setPhotoCount(6)
//                        .setGridColumnCount(3)
//                        .start(HomeActivity.this);
//                Intent intent3 = new Intent(getApplicationContext(), SealUsesealSpActivity.class);
//                startActivity(intent3);
                Toast.makeText(getApplicationContext(), "暂未开发", Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_add:
                change();
                // 加载popupwindow的布局
                View view = getLayoutInflater().inflate(R.layout.message_addpop, null);
                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                // 点击屏幕之外的区域可否让popupwindow消失
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new PoponDismissListener());
                // 设置popupwindow的显示位置
                popupWindow.showAtLocation(iv_add, Gravity.RIGHT | Gravity.TOP, 30, rv.getMeasuredHeight() * 3 / 2);
                break;
            case R.id.wait_sort:
                change();
                // 加载popupwindow的布局
                View view1 = getLayoutInflater().inflate(R.layout.message_sortaddpop, null);
                popupWindow = new PopupWindow(view1, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                // 点击屏幕之外的区域可否让popupwindow消失
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new PoponDismissListener());
                FragmentManager fragmentManager = getSupportFragmentManager();
                TextView lastest_receive = (TextView) view1.findViewById(R.id.create_offer);
                TextView long_receive = (TextView) view1.findViewById(R.id.scan_zxing);

//                final WaitFragment fragment_wait = (WaitFragment) fragmentManager.findFragmentByTag("" + 0);

                lastest_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        waitFragment.initData("desc");
                    }
                });

                long_receive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        waitFragment.initData("asc");
                    }
                });

                // 设置popupwindow的显示位置
                popupWindow.showAtLocation(wait_sort, Gravity.RIGHT | Gravity.TOP, 30, rv.getMeasuredHeight() * 3 / 2);
                break;
            case R.id.inform_sort:
                change();
                // 加载popupwindow的布局
                View view2 = getLayoutInflater().inflate(R.layout.message_sortaddpop, null);
                popupWindow = new PopupWindow(view2, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                // 点击屏幕之外的区域可否让popupwindow消失
                int windowPos[] = calculatePopWindowPos(view2, view2);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new PoponDismissListener());
                // 设置popupwindow的显示位置
                popupWindow.showAtLocation(inform_sort, Gravity.RIGHT | Gravity.TOP, 30, rv.getMeasuredHeight() * 3 / 2);

                TextView lastest_receive2 = (TextView) view2.findViewById(R.id.create_offer);
                TextView long_receive2 = (TextView) view2.findViewById(R.id.scan_zxing);

//                final WaitFragment fragment_wait = (WaitFragment) fragmentManager.findFragmentByTag("" + 0);

                lastest_receive2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        infoFragment.initData("desc");
                    }
                });

                long_receive2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        infoFragment.initData("asc");
                    }
                });
                break;
        }
    }

    private void change() {
        // 弹出popupwindow前，调暗屏幕的透明度
        WindowManager.LayoutParams lp2 = getWindow().getAttributes();
        lp2.alpha = (float) 0.8;
        getWindow().setAttributes(lp2);
    }

    @Override
    public void yesClick() {
        Intent intent = new Intent(this, Version_Update.class);
        startActivity(intent, null);
    }

    @Override
    public void noClick() {

    }

    @Override
    public void onSingleClick() {

    }

    // popupwindow消失后触发的方法，将屏幕透明度调为1
    class PoponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            WindowManager.LayoutParams p = getWindow().getAttributes();
            p.alpha = (float) 1;
            getWindow().setAttributes(p);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 110) {
            String accountLink = data.getStringExtra("accountLink");
            Log.e("判断头像网址",accountLink);
            //判断头像网址是否为null
            if (accountLink != null && !accountLink.equals("")) {
                //如果不为null，则更新头像
                Picasso.with(HomeActivity.this).load(accountLink).memoryPolicy(MemoryPolicy.NO_CACHE).into(icon);
                Picasso.with(HomeActivity.this).load(accountLink).memoryPolicy(MemoryPolicy.NO_CACHE).into(civ_myself);
            }
            String bg = data.getStringExtra("bg");
            //判断背景图片是否为null
            if (bg != null && !bg.equals("")) {
                //如果不为null 则更新背景图片
                setBg(bg);
            }
        }
    }

    /***
     * 更换背景图
     * @param bg
     */
    private void setBg(String bg) {

        switch (bg) {
            case "7":
                menu_bg.setImageResource(R.drawable.pic1);
                break;
            case "24":
                menu_bg.setImageResource(R.drawable.pic2);
                break;
            case "25":
                menu_bg.setImageResource(R.drawable.pic3);
                break;
            case "26":
                menu_bg.setImageResource(R.drawable.pic4);
                break;
            case "27":
                menu_bg.setImageResource(R.drawable.pic5);
                break;
            case "28":
                menu_bg.setImageResource(R.drawable.pic6);
                break;
        }
    }


       void return_amount() {
        String defString3 = PreferencesManager.getInstance(HomeActivity.this, "accountBean").get("jsonStr");
        Gson gson3 = new Gson();
        Logging_Success bean3 = gson3.fromJson(defString3, Logging_Success.class);
        GovernmentApi api3 = HttpClient.getInstance().getGovernmentApi();
        Call<Return_Amount> call2 = api3.return_amount_daiban(bean3.getData().getOpId());
        call2.enqueue(new Callback<Return_Amount>() {
            @Override
            public void onResponse(Call<Return_Amount> call, Response<Return_Amount> response) {
                if (response.body().getData() != null && !response.body().getData().equals("")) {
                    if (response.body().getData().getDbsx_num() <= 0) {

                        amount_daiban.setVisibility(View.INVISIBLE);
                    } else {
                        amount_daiban.setVisibility(View.VISIBLE);
                    }

                    if (response.body().getData().getGgtz_num() <= 0) {
                        amount_notification.setVisibility(View.INVISIBLE);
                    } else {
                        amount_notification.setVisibility(View.VISIBLE);
                    }

                    if ((response.body().getData().getGgtz_num()
                            +response.body().getData().getGwnz_num()
                            + response.body().getData().getSwcy_num()
                            +response.body().getData().getZwyx_num()
                            +response.body().getData().getWjxf_num()
                            +response.body().getData().getSosq_num()) <= 0) {
                        amount_work.setVisibility(View.INVISIBLE);
                    } else {
                        amount_work.setVisibility(View.VISIBLE);
                        amount_work.setText((response.body().getData().getGgtz_num()
                                +response.body().getData().getGwnz_num()
                                + response.body().getData().getSwcy_num()
                                +response.body().getData().getZwyx_num()
                                +response.body().getData().getWjxf_num()
                                +response.body().getData().getSosq_num())+"");
                    }
                    amount_ShortcutBadger = getSharedPreferences("amount_ShortcutBadger", 0);
                    Log.e("number",amount_ShortcutBadger.getInt("number",0)+"");
                    ShortcutBadger.applyCount(HomeActivity.this, response.body().getData().getManage_num());
                    amount_ShortcutBadger.edit().putInt("number",response.body().getData().getManage_num()).commit();

                }
            }

            @Override
            public void onFailure(Call<Return_Amount> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "网络连接有误!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = ScreenUtils.getScreenHeight(anchorView.getContext());
        final int screenWidth = ScreenUtils.getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //保存tab选中的状态
        outState.putInt(PRV_SELINDEX, selindex);
        super.onSaveInstanceState(outState);
    }
}

