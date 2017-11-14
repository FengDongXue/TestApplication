package com.lanwei.governmentstar.activity.zwyx;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.lll.refresh.OnItemClickListener;
import com.lanwei.governmentstar.bean.Inbox;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.zhy.autolayout.AutoLayoutActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/5/31/031.
 */

public class EmailBaseActivity extends AutoLayoutActivity implements View.OnClickListener {
    //编辑，中间标题,编辑
    public TextView email_edit;
    public TextView tv_address;
    public TextView email_finish;
    //返回键,圆角图
    private ImageView back, iv_contacts;
    public LinearLayout edit_bottom;
    private JSONObject dataJson;
    public String search = "";
    public EditText tvsearch;
    public String searchCt;
    public int pageNo = 1;   //当前是第几页
    public boolean aBoolean = false;//是否开启加载
    public int pageCount = 1;   //总页数
    //刷新框架
    public Activity mContext;
    public SwipeMenuRecyclerView mSwipeMenuRecyclerView;
    public int size = 30;
    public List<Inbox.DataBean> mDataList = new ArrayList<>();
    public MenuAdapter mMenuAdapter;
    public List<Inbox.DataBean> dataList;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    public ImageView checkBox1;
    public View view;
    public int shouRead;
    public int tempRead;
    public TextView select;
    public TextView del;
    public TextView collect;
    public boolean isEdit = false;   //是否在編輯狀態
    private SwipeMenuRecyclerView rv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Color.parseColor()  !  这个方法可以是指定的颜色代码，例如：#ffffff 设置沉浸式
        StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        setContentView(R.layout.inbox_layout);
        view = View.inflate(getApplicationContext(), R.layout.inbox_list_item, null);

        initview();
    }

    public void initview() {
        email_edit = (TextView) findViewById(R.id.email_edit);
        email_finish = (TextView) findViewById(R.id.email_finish);
        tv_address = (TextView) findViewById(R.id.tv_address);
        back = (ImageView) findViewById(R.id.back);
        iv_contacts = (ImageView) findViewById(R.id.iv_contacts);
        checkBox1 = (ImageView) view.findViewById(R.id.checkbox1);

        edit_bottom = (LinearLayout) findViewById(R.id.edit_bottom);
        select = (TextView) findViewById(R.id.select);
        del = (TextView) findViewById(R.id.del);
        collect = (TextView) findViewById(R.id.collect);

        tvsearch = (EditText) findViewById(R.id.inbox_search);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);

        rv = (SwipeMenuRecyclerView) findViewById(R.id.recycler_view);

        edit_bottom.setVisibility(View.GONE);
        email_edit.setVisibility(View.VISIBLE);
        email_finish.setVisibility(View.GONE);
        tv_address.setVisibility(View.VISIBLE);

        select.setOnClickListener(this);
        del.setOnClickListener(this);
        collect.setOnClickListener(this);
        back.setVisibility(View.VISIBLE);
        iv_contacts.setVisibility(View.GONE);
        back.setOnClickListener(this);
        email_edit.setOnClickListener(this);
        email_finish.setOnClickListener(this);

        rv.setItemAnimator(null);
    }

    /**
     * 左滑删除菜单
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    public SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.item_height);
//            int width1 = getResources().getDimensionPixelSize(R.dimen.item_height1);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                        .setBackgroundDrawable(R.drawable.selector_red)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextSize(15)
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);

//                SwipeMenuItem readItem = new SwipeMenuItem(mContext)
//                        .setBackgroundDrawable(R.drawable.selector_gray)
//                        .setText("标为已读")// 文字，还可以设置文字颜色，大小等。。
//                        .setTextSize(15)
//                        .setTextColor(Color.WHITE)
//                        .setWidth(width1)
//                        .setHeight(height);

//                swipeRightMenu.addMenuItem(readItem);// 添加一个按钮到右侧侧菜单。
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。

            }
        }
    };

    public OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(int position) {
//            Toast.makeText(mContext, "我是第" + position + "条。", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    //已发送适配器
    public class MenuAdapter extends SwipeMenuAdapter<MenuAdapter.DefaultViewHolder> {
        private String search;
        private List<Inbox.DataBean> datalist;
        private int thisPosition = -1;
        private int clickPosition = -1;   //定义一个索引记录点击的条目
        private OnItemClickListener mOnItemClickListener;
        TextView title;
        TextView content;
        TextView time;
        TextView amountRead;
        CircleImageView contacts;
        private List<Boolean> isChecks = new ArrayList<>();
        public int type = 0;        // 0 收件箱  // 1 已发送  //  2 临时邮件  // 3 已删除  // 4 收藏的邮件
        public boolean isGone = true;  //选项框
        public boolean isRead = true;  //已读未读标识
        public boolean isSelect = false;

        public MenuAdapter(List<Inbox.DataBean> datalist, String s, int type) {

            this.datalist = datalist;
            this.search = s;
            this.type = type;
            initCheck(1);   //初始化为全不选中
        }

        public void goneView(boolean isGone) {
            this.isGone = isGone;
        }

        public void readView(boolean isRead) {
            this.isRead = isRead;
        }


        public void initCheck(int isCheck) {
            for (int i = 0; i < datalist.size(); i++) {
                datalist.get(i).setIsCheck(isCheck);
            }
        }


        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        @Override
        public View onCreateContentView(ViewGroup parent, int viewType) {
            return LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_list_item, parent, false);
        }

        @Override
        public DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
            title = (TextView) realContentView.findViewById(R.id.inbox_title);
            content = (TextView) realContentView.findViewById(R.id.inbox_content);
            time = (TextView) realContentView.findViewById(R.id.inbox_time);
            contacts = (CircleImageView) realContentView.findViewById(R.id.iv_contacts);
            amountRead = (TextView) realContentView.findViewById(R.id.amount_read);
            View line = realContentView.findViewById(R.id.line);

            if (datalist.size() > 1 ) {
                line.setVisibility(View.VISIBLE);
                if (datalist.size() == datalist.size()){
                }
            }


            DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
            viewHolder.mOnItemClickListener = mOnItemClickListener;
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final DefaultViewHolder holder, final int position) {
            holder.title.setText(datalist.get(position).getMailtitle()); //邮件标题
            holder.content.setText(datalist.get(position).getMailcontent()); //邮件内容
            holder.time.setText(datalist.get(position).getOpcreatetime()); //发件日期
            if (type == 0) {
                if (datalist.get(position).getMailState() != null) {
                    if (datalist.get(position).getMailState().equals("0")) {
                        //未读展示
                        holder.amountRead.setVisibility(View.VISIBLE);
                        holder.title.setTextColor(Color.parseColor("#00a7e4"));
                    } else if (datalist.get(position).getMailState().equals("1")) {
                        //已读展示
                        holder.amountRead.setVisibility(View.GONE);
                        holder.title.setTextColor(Color.parseColor("#666666"));
                    }
                }
            }

            String tag = (String) holder.contacts.getTag();

            if (!TextUtils.equals(datalist.get(position).getMailpath(), tag)) {
                holder.contacts.setImageResource(R.drawable.addtk);
            }
            Glide.with(EmailBaseActivity.this)
                    .load(datalist.get(position).getMailpath())
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            if (datalist.get(position).getMailpath() != null)
                                holder.contacts.setTag(datalist.get(position).getMailpath());
                            holder.contacts.setImageDrawable(resource);
                        }
                    });

            Log.e("title", datalist.get(position).getMailtitle() + "头像" + datalist.get(position).getMailpath());

            // TODO: 2017/5/13 改变关键字的颜色
            String tit = datalist.get(position).getMailtitle();
            SpannableString spannableString = matcherSearchText(Color.parseColor("#df1214"), tit, search);
            holder.title.setText(spannableString);
            holder.chekbox.setSelected(datalist.get(position).getIsCheck() == 0 ? true : false);


            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (isEdit) {    //编辑状态
                        if (datalist.get(position).getIsCheck() == 0)
                            datalist.get(position).setIsCheck(1);
                        else
                            datalist.get(position).setIsCheck(0);
                        notifyDataSetChanged();
                    } else {   //非编辑状态

                        if (type == 0) {
                            datalist.get(position).setMailState("1");  //条目被点击MailState就从0变成1
                            notifyDataSetChanged();
                        }
                        Intent intent = new Intent(EmailBaseActivity.this, CheckMail.class);
                        intent.putExtra("opid", datalist.get(position).getOpid());
                        intent.putExtra("type", type);

                        startActivityForResult(intent, 21);
                    }
                }
            });

            holder.chekbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (datalist.get(position).getIsCheck() == 0)
                        datalist.get(position).setIsCheck(1);
                    else
                        datalist.get(position).setIsCheck(0);
                    notifyDataSetChanged();
                }
            });
            if (isGone) {
                holder.chekbox.setVisibility(View.GONE);
            } else {
                holder.chekbox.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return datalist == null ? 0 : datalist.size();
        }


        //已发送ViewHolder
        class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            OnItemClickListener mOnItemClickListener;
            ImageView chekbox;
            TextView title;
            TextView content;
            TextView time;
            TextView amountRead;
            CircleImageView contacts;

            public DefaultViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);

                title = (TextView) itemView.findViewById(R.id.inbox_title);
                content = (TextView) itemView.findViewById(R.id.inbox_content);
                time = (TextView) itemView.findViewById(R.id.inbox_time);
                contacts = (CircleImageView) itemView.findViewById(R.id.iv_contacts);
                chekbox = (ImageView) itemView.findViewById(R.id.checkbox1);
                amountRead = (TextView) itemView.findViewById(R.id.amount_read);
            }

            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(getAdapterPosition());
                }
            }
        }
    }


    //匹配搜索的关键字
    private SpannableString matcherSearchText(int color, String text, String keyword) {
        SpannableString ss = new SpannableString(text);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

}
