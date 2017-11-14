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
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/5.
 */

public class Convey_Files2_Activity extends BaseActivity implements View.OnClickListener{

    private RecyclerView rv;
    private ArrayList<Datas_Item> list_datas_items=new ArrayList<>();
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
    private String createId;

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
        String defString = PreferencesManager.getInstance(Convey_Files2_Activity.this,"accountBean").get("jsonStr");
        Gson gson=new Gson();
        bean=gson.fromJson(defString,Logging_Success.class);

        // 获取opId和userId
        userId=bean.getData().getOpId();
        api= HttpClient.getInstance().getGovernmentApi();

        Call<Bean_Return_Flow>  call= api.spsqFlow(userId,getIntent().getStringExtra("opId"),"spsqFlow");

        call.enqueue(new Callback<Bean_Return_Flow>() {
            @Override
            public void onResponse(Call<Bean_Return_Flow> call, Response<Bean_Return_Flow> response) {

                if(response.body().getData() !=null){
                    createId = response.body().getCreateId();
                    number_document.setVisibility(View.VISIBLE);
                    document_id.setText(response.body().getOpNo());
                    dataBean1= new Bean_Return_Flow.DataBean();
                    dataBean2= new Bean_Return_Flow.DataBean();
                    for(int i=0 ;i<response.body().getData().size();i++){
                        if(response.body().getData().get(i).getOpstate().equals("0")){
                            beanReturnFlow.add(response.body().getData().get(i));
                        }else if(response.body().getData().get(i).getOpstate().equals("1")){
                            beanReturnFlow.add(response.body().getData().get(i));
                        }else if(response.body().getData().get(i).getOpstate().equals("2")){
                            dataBean1.getList().add(response.body().getData().get(i));
                        }
                    }

                    if(dataBean1 != null && dataBean1.getList().size()>0){
                        beanReturnFlow.add(dataBean1);
                    }
                    for(int i=0 ;i<response.body().getData().size();i++){
                      if(response.body().getData().get(i).getOpstate().equals("3")){
                            beanReturnFlow.add(response.body().getData().get(i));
                        }else if(response.body().getData().get(i).getOpstate().equals("4")){
                            dataBean2.getList().add(response.body().getData().get(i));
                        }
                    }

                    if(dataBean2 != null && dataBean2.getList().size()>0){
                        beanReturnFlow.add(dataBean2);
                    }
                    for(int j=0;j<beanReturnFlow.size();j++){
                        Log.e("符合规范的规划",""+beanReturnFlow.get(j).getList().size()+"官方的人各地方刚");
                    }

                    rv=(RecyclerView) findViewById(R.id.lv2);
                    Log.e("是犯法的事发愁",""+getIntent().getStringExtra("opId"));
                    // 为RecyclerView设置默认动画和线性布局管理器
                    rv.setItemAnimator(new DefaultItemAnimator());
                    //设置线性布局
                    rv.setLayoutManager(new LinearLayoutManager(Convey_Files2_Activity.this));

                    adaper=new Adapter_Convey();
                    rv.setAdapter(adaper);

                }
            }

            @Override
            public void onFailure(Call<Bean_Return_Flow> call, Throwable t) {

                Toast.makeText(Convey_Files2_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();

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
        private int type_normal = 000111;
        private int type_many = 111000;

        public Adapter_Convey() {
        }

        @Override
        public int getItemCount() {
            return beanReturnFlow.size();
        }

        @Override
        public int getItemViewType(int position) {

            // 根据类型加载不同布局
            if(beanReturnFlow.get(position).getList().size()>0){
                Log.e("dataBean1.list",beanReturnFlow.get(position).getList().size()+"发士大夫大师傅");
                return  type_many;
            }
            return type_normal;
        }
        // 根据不同类型返回不同ViewHolder
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == type_many){
                view = getLayoutInflater().inflate(R.layout.items_many,parent,false);
                return new MyViewHolder2(view);
            }else{
                view = getLayoutInflater().inflate(R.layout.item_common_proceed,parent,false);
                return new MyViewHolder(view);
            }
        }
        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            // 判断不同ViewHolder,加载相应数据
            if(holder instanceof MyViewHolder){
                ((MyViewHolder)holder).title.setText(beanReturnFlow.get(position).getHead());
                ((MyViewHolder)holder).content.setText(beanReturnFlow.get(position).getTitle());
                ((MyViewHolder)holder).time_finish.setText(beanReturnFlow.get(position).getOpTime());
                ((MyViewHolder)holder).number.setText((position+1)+"");
                ((MyViewHolder)holder).rebanli.setVisibility(View.INVISIBLE);
                ((MyViewHolder)holder).details.setVisibility(View.INVISIBLE);

                if(!beanReturnFlow.get(position).getOpImg().equals("") && beanReturnFlow.get(position).getOpImg()!= null){
                    Picasso.with(Convey_Files2_Activity.this).load(beanReturnFlow.get(position).getOpImg()).memoryPolicy(MemoryPolicy.NO_CACHE).into(((MyViewHolder) holder).header);
                }

                if(position == 0 && createId.equals(bean.getData().getOpId())){

                    ((MyViewHolder)holder).rebanli.setVisibility(View.VISIBLE);
                    ((MyViewHolder)holder).rebanli.setText("撤回申请");
                    ((MyViewHolder)holder).rebanli.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Call<JsonObject> call=api.spsqSHch(bean.getData().getOpId(),getIntent().getStringExtra("opId"),"spsqSHch");
                            call.enqueue(new Callback<JsonObject>() {
                                @Override
                                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                    if( response.body().get("result").getAsBoolean()){
                                        Toast.makeText(Convey_Files2_Activity.this,"撤回成功",Toast.LENGTH_SHORT).show();

                                        Intent intent =new Intent();
                                        setResult(555,intent);
                                        finish();

                                    }else{
                                        Toast.makeText(Convey_Files2_Activity.this,"撤回失败",Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<JsonObject> call, Throwable t) {
                                    Toast.makeText(Convey_Files2_Activity.this,"网络连接有误",Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                    });
                }

                if(position == beanReturnFlow.size()-1 && beanReturnFlow.get(position).getTitle().equals("")){
                    ((MyViewHolder)holder).content.setVisibility(View.INVISIBLE);
                    ((MyViewHolder)holder).time_finish.setVisibility(View.INVISIBLE);
                    ((MyViewHolder)holder).add_number2.setVisibility(View.INVISIBLE);
                }else{
                    ((MyViewHolder)holder).content.setVisibility(View.VISIBLE);
                    ((MyViewHolder)holder).time_finish.setVisibility(View.VISIBLE);
                    ((MyViewHolder)holder).add_number2.setVisibility(View.VISIBLE);
                }


            }else if(holder instanceof MyViewHolder2){

                // 如果这个item是 会签类 等多人参与，加载type_many的类型的布局
                ((MyViewHolder2)holder).title.setText(beanReturnFlow.get(position).getHead());
                ((MyViewHolder2)holder).number.setText((position+1)+"");
                ((MyViewHolder2)holder).title.setText(beanReturnFlow.get(position).getList().get(beanReturnFlow.get(position).getList().size()-1).getHead());
                ((MyViewHolder2)holder).details.setVisibility(View.INVISIBLE);
                ArrayList<Bean_Return_Flow.DataBean> dataBean = new ArrayList<>();
                dataBean = beanReturnFlow.get(position).getList();

                // 为RecyclerView设置默认动画和线性布局管理器
                ((MyViewHolder2)holder).lv.setItemAnimator(new DefaultItemAnimator());
                //设置线性布局
                ((MyViewHolder2)holder).lv.setLayoutManager(new LinearLayoutManager(Convey_Files2_Activity.this));

                ((MyViewHolder2)holder).lv.addItemDecoration(new SpaceItemDecoration(40));

                ((MyViewHolder2)holder).lv.setAdapter(new Adapter_Process2(dataBean));

                Log.e("没进来","没进来");
            }
        }

        // 普通类型的ViewHolder
        class MyViewHolder extends RecyclerView.ViewHolder {

            private TextView number;
            private TextView title;
            private TextView time_finish;
            private TextView time_during;
            private TextView details;
            private View line_below;
            private View line_above;
            private TextView content;
            private TextView feedback;
            private TextView rebanli;
            private CircleImageView header;
            private LinearLayout add_number2;

            public MyViewHolder(View itemView) {

                super(itemView);
                number = (TextView) itemView.findViewById(R.id.number);
                title = (TextView) itemView.findViewById(R.id.title);
                details = (TextView) itemView.findViewById(R.id.details);
                time_finish = (TextView) itemView.findViewById(R.id.time_finish);
                time_during = (TextView) itemView.findViewById(R.id.time_during);
                content = (TextView) itemView.findViewById(R.id.content);
                feedback = (TextView) itemView.findViewById(R.id.feedback);
                rebanli = (TextView) itemView.findViewById(R.id.rebanli);
                header = (CircleImageView) itemView.findViewById(R.id.header);
                line_below =  itemView.findViewById(R.id.line_below);
                line_above =  itemView.findViewById(R.id.line_above);
                add_number2 = (LinearLayout) itemView.findViewById(R.id.add_number2);
            }
        }

        // 多人参与的ViewHolder
        class MyViewHolder2 extends RecyclerView.ViewHolder {

            private TextView number;
            private TextView title;
            private TextView details;
            private View line_below;
            private View line_below2;
            private View line_above;
            private TextView feedback;
            private RecyclerView lv;

            public MyViewHolder2(View itemView) {
                super(itemView);
                number = (TextView) itemView.findViewById(R.id.number);
                title = (TextView) itemView.findViewById(R.id.title);
                details = (TextView) itemView.findViewById(R.id.details);
                feedback = (TextView) itemView.findViewById(R.id.feedback);
                lv = (RecyclerView) itemView.findViewById(R.id.lv);
                line_below =  itemView.findViewById(R.id.line_below);
                line_below2 =  itemView.findViewById(R.id.line_below2);
                line_above =  itemView.findViewById(R.id.line_above);
            }
        }
    }

    // 多人参与的Adapter
    class Adapter_Process2 extends RecyclerView.Adapter<Adapter_Process2.MyViewHolder3> {

        private View view = null;
        private  ArrayList<Bean_Return_Flow.DataBean> dataBeen;

        public Adapter_Process2( ArrayList<Bean_Return_Flow.DataBean>  dataBeen) {
            this.dataBeen = dataBeen;
        }

        @Override
        public MyViewHolder3 onCreateViewHolder(ViewGroup parent, int viewType) {

            view = getLayoutInflater().inflate(R.layout.items_outs,parent,false);

            return new MyViewHolder3(view);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder3 holder,final int position) {

            holder.rebanli.setVisibility(View.GONE);
            holder.time_finish.setText(dataBeen.get(position).getOpTime());
            holder.content.setText(dataBeen.get(position).getTitle());
            if(!dataBeen.get(position).getOpImg().equals("") && dataBeen.get(position).getOpImg()!= null){
                Picasso.with(Convey_Files2_Activity.this).load(dataBeen.get(position).getOpImg()).memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.header);
            }

            if(dataBeen.get(position).getTitle().equals("")){
                holder.time_finish.setVisibility(View.INVISIBLE);
                holder.add_number3.setVisibility(View.INVISIBLE);
            }else{
                holder.time_finish.setVisibility(View.VISIBLE);
                holder.add_number3.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public int getItemCount() {

            if(dataBeen == null){
                return 0;
            }
            return dataBeen.size();
        }

        class MyViewHolder3 extends RecyclerView.ViewHolder {

            private TextView time_finish;
            private TextView time_during;
            private TextView rebanli;
            private TextView content;
            private CircleImageView header;
            private LinearLayout add_number3;
            public MyViewHolder3(View itemView) {

                super(itemView);
                time_finish = (TextView) itemView.findViewById(R.id.time_finish);
                time_during = (TextView) itemView.findViewById(R.id.time_during);
                rebanli = (TextView) itemView.findViewById(R.id.rebanli);
                content = (TextView) itemView.findViewById(R.id.content);
                header = (CircleImageView) itemView.findViewById(R.id.header);
                add_number3 = (LinearLayout) itemView.findViewById(R.id.add_number2);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 520){
            finish();
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

            if(parent.getChildPosition(view) != 0) {
                outRect.top = space;
            }
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
