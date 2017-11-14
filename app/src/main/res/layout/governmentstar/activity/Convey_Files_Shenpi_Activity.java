package com.lanwei.governmentstar.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.lll.DocumentHandleActivity;
import com.lanwei.governmentstar.activity.zyx.opinion.ReadOpinionActivity;
import com.lanwei.governmentstar.bean.Bean_Return_Flow;
import com.lanwei.governmentstar.bean.Datas_Item;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.ManyPeople;
import com.lanwei.governmentstar.bean.Result_Push;
import com.lanwei.governmentstar.bean.Return_Proceed;
import com.lanwei.governmentstar.demo.BaseActivity;
import com.lanwei.governmentstar.http.GovernmentApi;
import com.lanwei.governmentstar.http.HttpClient;
import com.lanwei.governmentstar.utils.PreferencesManager;
import com.lanwei.governmentstar.view.StatusBarUtils;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/5.
 */

public class Convey_Files_Shenpi_Activity extends BaseActivity implements View.OnClickListener{


    private RecyclerView rv;
    private List<Bean_Return_Flow.DataBean> list_datas_items=new ArrayList<>();
    private Adapter_Convey adaper;
    private LinearLayout condition;
    private ImageView back;
    // 将flowStatu的字符串改为int
    private int index=0;
    private TextView document_id;
    private Return_Proceed return_proceed;
    private String userId;
    private LinearLayout number_document;
    private GovernmentApi api;
    private PopupWindow popupWindow;
    int mont=0;
    ArrayList<String> list_number=new ArrayList<>();
    private  Logging_Success bean;
        private ArrayList<Bean_Return_Flow.DataBean> beanReturnFlow = new ArrayList<>();
    private Bean_Return_Flow.DataBean dataBean1;
    private Bean_Return_Flow.DataBean dataBean2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.see_process2);
        number_document=(LinearLayout) findViewById(R.id.number_document);

        back=(ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        document_id=(TextView) findViewById(R.id.document_id);

        // 判断系统SDK版本，看看是否支持沉浸式
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarUtils.compat(this, Color.parseColor("#00a7e4"));
        }

        list_number.add(0+"");
        // 获取bean;
        String defString = PreferencesManager.getInstance(Convey_Files_Shenpi_Activity.this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);

        // 获取opId和userId
        userId=bean.getData().getOpId();
        api= HttpClient.getInstance().getGovernmentApi();

        Call<Bean_Return_Flow>  call= api.spsqFlow(userId,getIntent().getStringExtra("opId"),"spsqFlow");

        call.enqueue(new Callback<Bean_Return_Flow>() {
            @Override
            public void onResponse(Call<Bean_Return_Flow> call, Response<Bean_Return_Flow> response) {

                    number_document.setVisibility(View.VISIBLE);
                    rv=(RecyclerView) findViewById(R.id.lv2);
                    document_id.setText(response.body().getOpNo());
                    list_datas_items = response.body().getData();

                for(int i=0 ;i<response.body().getData().size();i++){
                    if(response.body().getData().get(i).getOpstate().equals("0")){
                        beanReturnFlow.add(response.body().getData().get(i));
                    }else if(response.body().getData().get(i).getOpstate().equals("1")){
                        beanReturnFlow.add(response.body().getData().get(i));
                    }else if(response.body().getData().get(i).getOpstate().equals("2")){
                        dataBean1= new Bean_Return_Flow.DataBean();
                        dataBean1.getList().add(response.body().getData().get(i));
                    }else if(response.body().getData().get(i).getOpstate().equals("3")){
                        beanReturnFlow.add(dataBean1);
                        beanReturnFlow.add(response.body().getData().get(i));
                    }else if(response.body().getData().get(i).getOpstate().equals("4")){
                        dataBean2= new Bean_Return_Flow.DataBean();
                        dataBean2.getList().add(response.body().getData().get(i));
                    }
                    if(dataBean2 != null){
                        beanReturnFlow.add(dataBean2);
                    }
                }
                    Log.e("是犯法的事发愁",""+getIntent().getStringExtra("opId"));
                    // 为RecyclerView设置默认动画和线性布局管理器
                    rv.setItemAnimator(new DefaultItemAnimator());
                    //设置线性布局
                    rv.setLayoutManager(new LinearLayoutManager(Convey_Files_Shenpi_Activity.this));
                    adaper=new Adapter_Convey();
                    rv.setAdapter(adaper);

            }

            @Override
            public void onFailure(Call<Bean_Return_Flow> call, Throwable t) {

                Toast.makeText(Convey_Files_Shenpi_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    // 流程的adapter
    class Adapter_Convey extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private View view = null;
        public Adapter_Convey() {
        }

        @Override
        public int getItemCount() {
            return list_datas_items.size();
        }

        // 根据不同类型返回不同ViewHolder
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                view = getLayoutInflater().inflate(R.layout.layout_item_spsq,parent,false);
                return new MyViewHolder(view);
        }
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

                // 初始化每个item的状态，防止itemview的布局复用机制造成影响
                ((MyViewHolder)holder).number.setBackground(getResources().getDrawable(R.drawable.blue01));
                ((MyViewHolder)holder).line_above.setBackgroundColor(getResources().getColor(R.color.blue));
                ((MyViewHolder)holder).line_below.setBackgroundColor(getResources().getColor(R.color.blue));
                ((MyViewHolder)holder).add_number2.setVisibility(View.VISIBLE);
                ((MyViewHolder)holder).time_finish.setVisibility(View.VISIBLE);
                ((MyViewHolder)holder).title.setText(list_datas_items.get(position).getTitle());
                ((MyViewHolder)holder).content.setText(list_datas_items.get(position).getName());

            if(list_datas_items.get(position).getTitle().equals("")){



            }


            if(list_datas_items.get(position).getOpstate().equals("2") && list_datas_items.get(position-1).getOpstate().equals("2")){
                ((MyViewHolder) holder).add_number2.setVisibility(View.GONE);
            }

                ((MyViewHolder)holder).time_finish.setText(list_datas_items.get(position).getName()+list_datas_items.get(position).getOpTime());

                if(position==0 && list_datas_items.get(position).getOpId() !=null && list_datas_items.get(position).getOpId().equals(bean.getData().getOpId())){
                    ((MyViewHolder)holder).rebanli.setText("");
                }else{
                    ((MyViewHolder)holder).rebanli.setVisibility(View.GONE);
                }
                // 判定imageUrl是不是空，以免发生奔溃
                if(!list_datas_items.get(position).getOpImg().equals("") && list_datas_items.get(position).getOpImg()!= null){
                    Log.e("和风格化规范化规划他以后",list_datas_items.get(position).getOpImg());
                    Picasso.with(Convey_Files_Shenpi_Activity.this).load(list_datas_items.get(position).getOpImg()).memoryPolicy(MemoryPolicy.NO_CACHE).into(((MyViewHolder)holder).header);
                }else{
                    ((MyViewHolder)holder).header.setVisibility(View.GONE);
                }

        }

        // 普通类型的ViewHolder
        class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView number_shu;
            private FrameLayout number;

            private TextView title;
            private TextView time_finish;

            private View line_below;
            private View line_above;
            private View line_middle;

            private TextView content;
            private TextView rebanli;
            private CircleImageView header;
            private LinearLayout add_number2;

            public MyViewHolder(View itemView) {

                super(itemView);
                number = (FrameLayout) itemView.findViewById(R.id.number);
                number_shu = (TextView) itemView.findViewById(R.id.number_shu);
                title = (TextView) itemView.findViewById(R.id.title);
                time_finish = (TextView) itemView.findViewById(R.id.time_finish);
                content = (TextView) itemView.findViewById(R.id.content);
                rebanli = (TextView) itemView.findViewById(R.id.rebanli);
                header = (CircleImageView) itemView.findViewById(R.id.header);
                line_below =  itemView.findViewById(R.id.line_below);
                line_above =  itemView.findViewById(R.id.line_above);
                line_middle =  itemView.findViewById(R.id.line_middle);
                add_number2 = (LinearLayout) itemView.findViewById(R.id.add_number2);
            }
        }

    }
    // 给RecyclerView设置item
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            if(parent.getChildPosition(view) != 0)
                outRect.top = space;
        }
    }


    // popupwindow消失后触发的方法，将屏幕透明度调为1
    class PoponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            WindowManager.LayoutParams p = getWindow().getAttributes();
            p.alpha=(float) 1;
            getWindow().setAttributes(p);
        }

    }


}
