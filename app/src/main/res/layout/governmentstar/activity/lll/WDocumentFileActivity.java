package com.lanwei.governmentstar.activity.lll;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.Addtion_Details_Activity;
import com.lanwei.governmentstar.activity.Process2_Activity;
import com.lanwei.governmentstar.demo.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/30.
 */
//公文归档
public class WDocumentFileActivity extends BaseActivity implements View.OnClickListener{
    private ImageView back;
    private ImageView details;
    private WebView webView;
    private RecyclerView rv;
    private Adapter_Addtion adapter;
    private ArrayList<String> data = null;
    private TextView title,more;
    private TextView watchdocument_1,watchdocument_2,watchdocument_3,watchdocument_4,watchdocument_5,watchdocument_6,watchdocument_7,watchdocument_8,
    watchdocument_9,watchdocument_10,watchdocument_11,watchdocument_12,watchdocument_13,watchdocument_14,watchdocument_15;
    private List<String > listText;
    private LinearLayout linearLayout,document_guild_layout;
    private ScrollView document_scrollView;
    private EditText document_guild_edit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchdocumentfile);
        initweight();
    }
    void initweight(){
        title= (TextView) findViewById(R.id.title);
        more= (TextView) findViewById(R.id.more);
        back=(ImageView) findViewById(R.id.back);
        details=(ImageView) findViewById(R.id.details);
        webView=(WebView) findViewById(R.id.watchdocument_web);
        rv= (RecyclerView) findViewById(R.id.rv);
        document_scrollView= (ScrollView) findViewById(R.id.document_scrollView);
        linearLayout= (LinearLayout) findViewById(R.id.document_linear);
        document_guild_layout= (LinearLayout) findViewById(R.id.document_guild_layout);
        watchdocument_1= (TextView) findViewById(R.id.watchdocument_1);
        watchdocument_2= (TextView) findViewById(R.id.watchdocument_2);
        watchdocument_3= (TextView) findViewById(R.id.watchdocument_3);
        watchdocument_4= (TextView) findViewById(R.id.watchdocument_4);
        watchdocument_5= (TextView) findViewById(R.id.watchdocument_5);
        watchdocument_6= (TextView) findViewById(R.id.watchdocument_6);
        watchdocument_7= (TextView) findViewById(R.id.watchdocument_7);
        watchdocument_8= (TextView) findViewById(R.id.watchdocument_8);
        watchdocument_9= (TextView) findViewById(R.id.watchdocument_9);
        watchdocument_10= (TextView) findViewById(R.id.watchdocument_10);
        watchdocument_11= (TextView) findViewById(R.id.watchdocument_11);
        watchdocument_12= (TextView) findViewById(R.id.watchdocument_12);
        watchdocument_13= (TextView) findViewById(R.id.watchdocument_13);
        watchdocument_14= (TextView) findViewById(R.id.watchdocument_14);
        watchdocument_15= (TextView) findViewById(R.id.watchdocument_15);
        document_guild_edit= (EditText) findViewById(R.id.document_guild_edit);
        back.setOnClickListener(this);
        more.setOnClickListener(this);
        initWebView();
        initDate();
        initSet();
    }

    private void initSet() {//软键盘弹出问题
        linearLayout.getViewTreeObserver().addOnGlobalLayoutListener( new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //取得 rootView 可视区域
                linearLayout.getWindowVisibleDisplayFrame(rect);
                //取得 rootView 不可视区域高度 (被其他View遮挡的区域高度)
                int rootInvisibleHeight = linearLayout.getRootView().getHeight() - rect.bottom;
                //要是不可视区域高度大于100，则输入键盘就显示
                if (rootInvisibleHeight > 100) {
                    int[] location = new int[2];
                    //取得 scrollToInput 的坐标
                    document_guild_layout.getLocationInWindow(location);
                    //计算滚动高度(rootView)，这样 (scrollToInput)在可视区域
                    int srollHeight = (location[1] + document_guild_layout.getHeight()) - rect.bottom;
                    linearLayout.scrollTo(0, srollHeight);
                } else {
                    //隐藏软键盘
                    linearLayout.scrollTo(0, 0);
                }
            }
        });
        document_scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                return false;
            }
        });
        rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0); //强制隐藏键盘
                return false;
            }
        });
    }

    private void initDate() {
        document_guild_edit.setHint("请填写文件共享类型...");
        setViewText(watchdocument_1,"公文标题:"," 论可持续发展重要性");
        setViewText(watchdocument_2,"来文单位:"," 天津市人民政府");
        setViewText(watchdocument_3,"来文类型:"," 决议");
        setViewText(watchdocument_4,"主题分类:"," 应急管理");
        setViewText(watchdocument_5,"处理类型:"," 一般公文");
        setViewText(watchdocument_6,"签收时间:"," 2017/03/06 15:55");
        setViewText(watchdocument_7,"结办时间:"," 2017/03/06 15:55");
        setViewText(watchdocument_8,"接件人:"," 张浩宁");
        setViewText(watchdocument_9,"拟办人:"," 张一凡");
        setViewText(watchdocument_10,"批示人:"," 吴小军");
        setViewText(watchdocument_11,"阅办人:"," 吴小军、吴小军、吴小军、吴小军、吴小军、吴小军、" +
                "吴小军、吴小军、吴小军、吴小军、吴小军、吴小军、吴小军、吴小军、吴小军");
        setViewText(watchdocument_12,"承办人:"," 陈小春");
        setViewText(watchdocument_13,"办理人:"," 闻君孝");
        setViewText(watchdocument_14,"参与人:"," 李倩");
        setViewText(watchdocument_15,"归档人:"," 张浩宁");
        rv.setItemAnimator(new DefaultItemAnimator());
        //设置线性布局
        rv.setLayoutManager(new LinearLayoutManager(this));
        title.setText("公文归档");
        more.setText("归档");
        data=new ArrayList<>();
        data.add("20152352.jpg");
        data.add("20152352.jpg");
        data.add("20152352.jpg");
        data.add("20152352.jpg");
        data.add("20152352.jpg");
        data.add("20152352.jpg");
        adapter=new Adapter_Addtion(data);
        rv.setAdapter(adapter);
        listText=new ArrayList<>();
        listText.add("10年");
        listText.add("20年");
        listText.add("30年");
        listText.add("50年");
        listText.add("永久");
    }

    //设置文字
    private void setViewText(TextView text, String first, String content) {
        SpannableString spannableString = new SpannableString(first + content);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_aaa)), 0, first.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color_23)), first.length(), first.length() + content.length()
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(spannableString);
    }
    private void initWebView() {
        WebSettings webSettings =   webView .getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式
        webView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        webView.getSettings().setDatabaseEnabled(true);
        //开启 Application Caches 功能
        webView.getSettings().setAppCacheEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.baidu.com");
    }
    class Adapter_Addtion extends RecyclerView.Adapter<Adapter_Addtion.MyViewHolder> {

        private View view = null;
        private ArrayList<String> datas = null;

        public Adapter_Addtion(ArrayList datas) {
            this.datas = datas;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.addtion_layout, null);

            return new MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {



            holder.addtion.setText("附件"+(position+1)+" : ");

            holder.addtional.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO 跳去附件详情界面

                    Intent intent=new Intent(WDocumentFileActivity.this,Addtion_Details_Activity.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView addtion;
            TextView addtional;

            public MyViewHolder(View itemView) {

                super(itemView);
                addtion = (TextView) view.findViewById(R.id.addtion);
                addtional = (TextView) view.findViewById(R.id.addtional);
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.document_file_time:{//选择文字
                WindowManager.LayoutParams lp2 = getWindow().getAttributes();
                lp2.alpha=(float) 0.8;
                getWindow().setAttributes(lp2);
                // 加载popupwindow的布局
                View view2=getLayoutInflater().inflate(R.layout.popwindow_sy4,null);
                ExpandableListView listView= (ExpandableListView ) view2.findViewById(R.id.expandablelistview);
                listView.setGroupIndicator(null);
                parentList=new String[3];
                for (int i = 0; i < 3; i++) {
                    final List<Details > popList=new ArrayList<>();
                    for (int j = 0; j < 5; j++) {//数据
                        popList.add(new Details(j+"---",false));
                    }
                    parentList[i]=i+"";
                    dataset.put(i+"",popList);
                }
                MyExpandableListViewAdapter myExpandableListViewAdapter=new MyExpandableListViewAdapter();
                listView.setAdapter(myExpandableListViewAdapter);
                final PopupWindow popupWindow=new PopupWindow(view2, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                // 初始化popupwindow的点击控件
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        popupWindow.dismiss();
                    }
                });
                // 点击屏幕之外的区域可否让popupwindow消失
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new BitmapDrawable());
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        WindowManager.LayoutParams p = getWindow().getAttributes();
                        p.alpha=(float) 1;
                        getWindow().setAttributes(p);
                    }
                });
                int xy[]= new int[2];
                v.getLocationOnScreen(xy);
                // 设置popupwindow的显示位置
                popupWindow.showAtLocation(v, Gravity.BOTTOM,0,0);
            }break;
            case R.id.document_file_look:{//查看流程
                Intent intent= new Intent(this,Process2_Activity.class);
                intent.putExtra("opId", getIntent().getStringExtra("opId"));
                startActivity(intent);
            }break;
            case R.id.back:{//返回
                finish();
            }break;
            case R.id.more:{//归档

            }break;

        }
    }
    class Details{
        String name;
        boolean isSelect;

        @Override
        public String toString() {
            return "Details{" +
                    "name='" + name + '\'' +
                    ", isSelect=" + isSelect +
                    '}';
        }

        public Details() {
        }

        public Details(String name, boolean isSelect) {
            this.name = name;
            this.isSelect = isSelect;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }
    }
    private Map<String, List<Details>> dataset = new HashMap<>();
    private String[] parentList = new String[]{};
    private class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

        //  获得某个父项的某个子项
        @Override
        public Object getChild(int parentPos, int childPos) {
            return dataset.get(parentList[parentPos]).get(childPos);
        }

        //  获得父项的数量
        @Override
        public int getGroupCount() {
            return dataset.size();
        }

        //  获得某个父项的子项数目
        @Override
        public int getChildrenCount(int parentPos) {
            return dataset.get(parentList[parentPos]).size();
        }

        //  获得某个父项
        @Override
        public Object getGroup(int parentPos) {
            return dataset.get(parentList[parentPos]);
        }

        //  获得某个父项的id
        @Override
        public long getGroupId(int parentPos) {
            return parentPos;
        }

        //  获得某个父项的某个子项的id
        @Override
        public long getChildId(int parentPos, int childPos) {
            return childPos;
        }

        //  按函数的名字来理解应该是是否具有稳定的id，这个方法目前一直都是返回false，没有去改动过
        @Override
        public boolean hasStableIds() {
            return false;
        }

        //  获得父项显示的view
        @Override
        public View getGroupView(int parentPos, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) WDocumentFileActivity
                        .this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.adapter_popwindow_sy4_group, viewGroup,false);
            }
            TextView text = (TextView) view.findViewById(R.id.adapter_popwindow_sy4_group_text);
            text.setText(parentList[parentPos]);
            ImageView imageView= (ImageView) view.findViewById(R.id.adapter_pop4_j);
            if(b){
                imageView.setImageResource(R.drawable.icon_down);
            }else{
                imageView.setImageResource(R.drawable.go);
            }

            return view;
        }

        //  获得子项显示的view
        @Override
        public View getChildView(final int parentPos, final int childPos, boolean b, View view, ViewGroup viewGroup) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) WDocumentFileActivity
                        .this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.adapter_popwindow_sy4_child, viewGroup,false);
            }
            TextView text = (TextView) view.findViewById(R.id.adapter_popwindow_sy4_child_text);
            text.setText(dataset.get(parentList[parentPos]).get(childPos).getName());
            ImageView imageView= (ImageView) view.findViewById(R.id.adapter_pop4_c_j);
            if(dataset.get(parentList[parentPos]).get(childPos).isSelect()){
                imageView.setImageResource(R.drawable.icon_x);
            }else{
                imageView.setImageResource(R.drawable.icon_w);
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dataset.get(parentList[parentPos]).get(childPos).setSelect(!dataset.get(parentList[parentPos]).get(childPos).isSelect());
                    notifyDataSetChanged();
                }
            });
            View adapter_popwindow_sy4_child_line=view.findViewById(R.id.adapter_popwindow_sy4_child_line);
            if(childPos==dataset.get(parentList[parentPos]).size()){
                adapter_popwindow_sy4_child_line.setVisibility(View.INVISIBLE);
            }else{
                adapter_popwindow_sy4_child_line.setVisibility(View.VISIBLE);
            }
            return view;
        }

        //  子项是否可选中，如果需要设置子项的点击事件，需要返回true
        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }
    }
    public class PopAdapter extends BaseAdapter {

        List<String> strs = null;
        LayoutInflater inflater = null;

        public PopAdapter(List<String> strs, Context context) {
            this.strs = strs;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return strs.size();
        }

        @Override
        public Object getItem(int arg0) {
            return strs.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int arg0, View convertView, ViewGroup arg2) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView=inflater.inflate(R.layout.adapter_approve_item,arg2,false);
                holder.adapter_approve_item_text= (TextView) convertView.findViewById(R.id.adapter_approve_item_text);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
            }
            holder.adapter_approve_item_text.setText(strs.get(arg0));
            return convertView;
        }
        class ViewHolder {
            TextView adapter_approve_item_text = null;
            ImageView imageView=null;
        }

    }
}
