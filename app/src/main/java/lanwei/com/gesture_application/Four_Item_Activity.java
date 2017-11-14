package lanwei.com.gesture_application;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import lanwei.com.gesture_application.bean.Beans_Return;
import lanwei.com.gesture_application.http.HttpClient;
import lanwei.com.gesture_application.http.SafeTypoApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 蓝威科技-技术开发1 on 2017/10/26.
 */

public class Four_Item_Activity extends AppCompatActivity {

    @InjectView(R.id.rv_view)
    RecyclerView rvView;
    @InjectView(R.id.back)
    ImageView back;
    @InjectView(R.id.check)
    TextView check;
    private Beans_Return beans_return = new Beans_Return();
    private ArrayList<Bean_class> array_datas = new ArrayList<>();
    private ArrayList<Bean_class> array_true = new ArrayList<>();
    private Adapter_Handdown adapter_handdown = new Adapter_Handdown();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_four_items);

        ButterKnife.inject(this);
        rvView.setItemAnimator(new DefaultItemAnimator());
        rvView.setLayoutManager(new LinearLayoutManager(this));
        rvView.setAdapter(adapter_handdown);

        SafeTypoApi api = HttpClient.getInstance().getSafeTypoApi();
        Call<Beans_Return> call = api.receivers_notification("0155");
        call.enqueue(new Callback<Beans_Return>() {
            @Override
            public void onResponse(Call<Beans_Return> call, Response<Beans_Return> response) {

                int count = -1;
                beans_return = response.body();
                for (int i = 0; i < beans_return.getData().size(); i++) {

                    array_datas.add(new Bean_class(++count, beans_return.getData().get(i).getOpId(), beans_return.getData().get(i).getOpName(), 0, true));
                    for (int j = 0; j < beans_return.getData().get(i).getChildList().size(); j++) {
                        array_datas.add(new Bean_class(++count, beans_return.getData().get(i).getChildList().get(j).getOpId(),
                                beans_return.getData().get(i).getChildList().get(j).getOpName(),
                                1, false));
                        for (int k = 0; k < beans_return.getData().get(i).getChildList().get(j).getChildList().size(); k++) {
                            array_datas.add(new Bean_class(++count, beans_return.getData().get(i).getChildList().get(j).getChildList().get(k).getOpId(),
                                    beans_return.getData().get(i).getChildList().get(j).getChildList().get(k).getOpName(),
                                    2, false));
                            for (int m = 0; m < beans_return.getData().get(i).getChildList().get(j).getChildList().get(k).getChildList().size(); m++) {
                                array_datas.add(new Bean_class(++count, beans_return.getData().get(i).getChildList().get(j).getChildList().get(k).getChildList().get(m).getOpId(),
                                        beans_return.getData().get(i).getChildList().get(j).getChildList().get(k).getChildList().get(m).getOpName(),
                                        3, false));
                            }
                        }
                    }
                }

                initDatas();

            }

            @Override
            public void onFailure(Call<Beans_Return> call, Throwable t) {


            }
        });


    }

    @OnClick({R.id.back, R.id.check})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.check:
                String String ="";
                for(int i=0; i<array_datas.size();i++){
                    if(array_datas.get(i).getChoose()){
                        String+=array_datas.get(i).getOpName() +"  ";
                    }
                }
                Log.e("选中的人"+1 ,String);
                break;
        }
    }


    class Adapter_Handdown extends RecyclerView.Adapter<Adapter_Handdown.My_ViewHolder> {

        View view;

        @Override
        public My_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            view = getLayoutInflater().inflate(R.layout.item_four_threeitem, parent, false);
            return new My_ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final My_ViewHolder holder, final int position) {

            holder.content.setText(array_true.get(position).getOpName());

            if (array_true.get(position).getChoose()) {
                holder.isChoosed.setSelected(true);
            } else {
                holder.isChoosed.setSelected(false);
            }

            switch (array_true.get(position).getType()) {

                case 0:
                    holder.first.setVisibility(View.VISIBLE);
                    holder.second.setVisibility(View.GONE);
                    holder.third.setVisibility(View.GONE);
                    holder.four.setVisibility(View.GONE);
                    holder.isChoosed.setVisibility(View.GONE);
                    holder.shift_leaders.setVisibility(View.VISIBLE);
                    holder.shift_leaders_down.setVisibility(View.GONE);

                    break;
                case 1:
                    holder.first.setVisibility(View.VISIBLE);
                    holder.second.setVisibility(View.VISIBLE);
                    holder.third.setVisibility(View.GONE);
                    holder.four.setVisibility(View.GONE);
                    holder.isChoosed.setVisibility(View.GONE);
                    holder.shift_leaders.setVisibility(View.VISIBLE);
                    holder.shift_leaders_down.setVisibility(View.GONE);
                    break;
                case 2:
                    holder.first.setVisibility(View.VISIBLE);
                    holder.second.setVisibility(View.VISIBLE);
                    holder.third.setVisibility(View.VISIBLE);
                    holder.four.setVisibility(View.GONE);
                    holder.isChoosed.setVisibility(View.GONE);
                    holder.shift_leaders.setVisibility(View.VISIBLE);
                    holder.shift_leaders_down.setVisibility(View.GONE);
                    break;
                case 3:
                    holder.first.setVisibility(View.VISIBLE);
                    holder.second.setVisibility(View.VISIBLE);
                    holder.third.setVisibility(View.VISIBLE);
                    holder.four.setVisibility(View.VISIBLE);
                    holder.isChoosed.setVisibility(View.VISIBLE);
                    holder.shift_leaders.setVisibility(View.GONE);
                    holder.shift_leaders_down.setVisibility(View.GONE);
                    break;
            }

            if (position != array_true.size() - 1 && array_true.get(position).getType() < array_true.get(position + 1).getType()) {
                holder.shift_leaders.setVisibility(View.GONE);
                holder.shift_leaders_down.setVisibility(View.VISIBLE);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (array_true.get(position).getType() == 0 || array_true.get(position).getType() == 1 || array_true.get(position).getType() == 2) {
                        if (array_datas.get(array_true.get(position).getPosition() + 1).getVisible()) {
                            for (int n = array_true.get(position).getPosition() + 1; n < array_datas.size(); n++) {
                                if (array_datas.get(n).getType() <= array_true.get(position).getType() || n == array_datas.size() - 1) {
                                    refreshLayout();
                                    Log.e("子视图不可见，点击后", "刷新视图了");
                                    return;
                                } else {
                                    array_datas.get(n).setVisible(false);
                                    Log.e("子视图不可见，点击后", "设置可见性为false中");
                                }
                            }

                        } else {

                            for (int m = array_true.get(position).getPosition() + 1; m < array_datas.size(); m++) {

                                if (array_datas.get(m).getType() <= array_true.get(position).getType() || m == array_datas.size() - 1) {
                                    refreshLayout();
                                    Log.e("子视图可见，点击后", "刷新视图了");
                                    return;
                                } else if (array_datas.get(m).getType() == array_true.get(position).getType() + 1) {
                                    array_datas.get(m).setVisible(true);
                                    Log.e("子视图可见，点击后", "设置可见性为true中");
                                }
                            }

                        }
                    } else {
                        if (array_datas.get(array_true.get(position).getPosition()).getChoose()) {
                            array_datas.get(array_true.get(position).getPosition()).setChoose(false);
                            array_true.get(position).setChoose(false);
                            holder.isChoosed.setSelected(false);
                            Log.e("name", "点击取消选中");
                        } else {
                            array_datas.get(array_true.get(position).getPosition()).setChoose(true);
                            array_true.get(position).setChoose(true);
                            holder.isChoosed.setSelected(true);
                            Log.e("name", "点击选中");
                        }

                    }
                }
            });


        }

        @Override
        public int getItemCount() {

//            int amount =0 ;
//            for(int i=0 ;i<array_datas.size();i++){
//                if(array_datas.get(i).getVisible()){
//                    amount++;
//                }
//            }
//            Log.e("name","理论items的个数："+amount);
            Log.e("name", "真实items的个数：" + array_true.size());
            return array_true.size();
        }

        class My_ViewHolder extends RecyclerView.ViewHolder {


            private ImageView isChoosed;
            private ImageView shift_leaders;
            private ImageView shift_leaders_down;
            private TextView content;
            private View first;
            private View second;
            private View third;
            private View four;
            private LinearLayout linearLayout;

            public My_ViewHolder(View itemView) {

                super(itemView);
                isChoosed = (ImageView) itemView.findViewById(R.id.isChoosed);
                shift_leaders = (ImageView) itemView.findViewById(R.id.shift_leaders);
                shift_leaders_down = (ImageView) itemView.findViewById(R.id.shift_leaders_down);
                content = (TextView) itemView.findViewById(R.id.content);
                first = itemView.findViewById(R.id.first);
                second = itemView.findViewById(R.id.second);
                third = itemView.findViewById(R.id.third);
                four = itemView.findViewById(R.id.four);
                linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

            }

        }

    }

    void refreshLayout() {
        initDatas();
    }


    class Bean_class {

        private int position = 0;
        private String opId;
        private String opName;
        private int type = 0;
        private Boolean isVisible = false;
        private Boolean isChoose = false;

        public Bean_class(int position, String opId, String opName, int type, Boolean isVisible) {
            this.position = position;
            this.opId = opId;
            this.opName = opName;
            this.type = type;
            this.isVisible = isVisible;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public Boolean getChoose() {
            return isChoose;
        }

        public void setChoose(Boolean choose) {
            isChoose = choose;
        }

        public Boolean getVisible() {
            return isVisible;
        }

        public void setVisible(Boolean visible) {
            isVisible = visible;
        }

        public String getOpId() {
            return opId;
        }

        public void setOpId(String opId) {
            this.opId = opId;
        }

        public String getOpName() {
            return opName;
        }

        public void setOpName(String opName) {
            this.opName = opName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

    }

    void initDatas() {

        if (array_true.size() > 0) {
            array_true.clear();
        }
        for (int k = 0; k < array_datas.size(); k++) {
            if (array_datas.get(k).getVisible()) {
                array_true.add(array_datas.get(k));
            }
        }
        adapter_handdown.notifyDataSetChanged();


        for (int h = 0; h < array_datas.size(); h++) {
            Log.e("全部数据：  ", "getOpName " + array_datas.get(h).getOpName() + " getPosition  " + array_datas.get(h).getPosition() + "  getVisible " + array_datas.get(h).getVisible() + " getType " + array_datas.get(h).getType());
        }

        for (int h = 0; h < array_true.size(); h++) {
            Log.e("真实数据： ", "getOpName " + array_true.get(h).getOpName() + " getPosition  " + array_true.get(h).getPosition() + "  getVisible " + array_true.get(h).getVisible() + " getType " + array_true.get(h).getType());
        }


    }
}
