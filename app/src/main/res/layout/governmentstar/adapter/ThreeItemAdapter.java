package com.lanwei.governmentstar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.bean.ThreeItemInfo;
import com.lanwei.governmentstar.interfaces.MultiItemTypeSupport;

import java.util.List;


/**
 * @data 2017/4/12 0012
 * @aurher Administrator
 */

public class ThreeItemAdapter extends MultiItemRecycleViewAdapter<ThreeItemInfo> {
    private static final int TYPE_ONE = 0;
    private static final int TYPE_TWO = 1;
    private static final int TYPE_THREE = 2;
    private static final int TYPE_four = 3;

    public ThreeItemAdapter(Context context, List<ThreeItemInfo> datas) {
        super(context, datas, new MultiItemTypeSupport<ThreeItemInfo>() {
            @Override
            public int getLayoutId(int itemType) {
                int i = 0;
                switch (itemType) {
                    case TYPE_ONE:
                        i = R.layout.item_one_threeitem;
                        break;
                    case TYPE_TWO:
                        i = R.layout.item_two_threeitem;
                        break;
                    case TYPE_THREE:
                        i = R.layout.item_three_threeitem;
                        break;
                    case TYPE_four:
                        i = R.layout.item_four_threeitem;
                        break;
                }
                return i;
            }

            @Override
            public int getItemViewType(int position, ThreeItemInfo threeItemInfo) {
                String currentCity = threeItemInfo.getStatus();
                int i = 0;
                switch (currentCity) {
                    case "1":
                        i = TYPE_ONE;
                        break;
                    case "2":
                        i = TYPE_TWO;
                        break;
                    case "3":
                        i = TYPE_THREE;
                        break;
                    case "4":
                        i = TYPE_four;
                        break;

                }
                return i;
            }
        });
    }

    @Override
    public void convert(ViewHolderHelper helper, ThreeItemInfo threeItemInfo, int position) {
        switch (helper.getLayoutId()) {
            case R.layout.item_one_threeitem:
                bindViewOne(helper, threeItemInfo, position);
                break;
            case R.layout.item_two_threeitem:
                bindViewTwo(helper, threeItemInfo, position);
                break;
            case R.layout.item_three_threeitem:
                bindViewThree(helper, threeItemInfo, position);
                break;
            case R.layout.item_four_threeitem:
                bindViewFour(helper, threeItemInfo, position);
                break;
        }
    }

    private void bindViewOne(ViewHolderHelper helper, final ThreeItemInfo threeItemInfo, final int position) {
        TextView tv_1 = helper.getView(R.id.tv_1);
        LinearLayout extend_leaders = helper.getView(R.id.extend_leaders);
        final ImageView shift_leaders =helper.getView(R.id.shift_leaders);
        final ImageView shift_leaders_down =helper.getView(R.id.shift_leaders_down);

        tv_1.setText(threeItemInfo.getContent());

        if(threeItemInfo.getIsExpading()){
            shift_leaders.setVisibility(View.VISIBLE);
            shift_leaders_down.setVisibility(View.INVISIBLE);
        }else{
            shift_leaders.setVisibility(View.INVISIBLE);
            shift_leaders_down.setVisibility(View.VISIBLE);
        }

        extend_leaders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (threeItemInfo.getResults() != null && threeItemInfo.getResults().size() != 0 && threeItemInfo.getIsExpading()) {

                    shift_leaders.setVisibility(View.INVISIBLE);
                    shift_leaders_down.setVisibility(View.VISIBLE);
                    Log.e("点击后展开","点击后展开");

                    threeItemInfo.setIsExpading(false);
                    addAllAt(position + 1, threeItemInfo.getResults());


                } else if (threeItemInfo.getResults() != null & threeItemInfo.getResults().size() != 0 && !threeItemInfo.getIsExpading()) {
                    Log.e("点击后收缩","点击后收缩");
                    shift_leaders.setVisibility(View.VISIBLE);
                    shift_leaders_down.setVisibility(View.INVISIBLE);


                    for (ThreeItemInfo item : threeItemInfo.getResults()) {
                        if (!item.getIsExpading()) {
                            removeAll(item.getResults());
                        }

                        for(ThreeItemInfo item2 : item.getResults()){

                            if (!item2.getIsExpading()) {
                                removeAll(item2.getResults());
                            }
                        }
                    }
                    threeItemInfo.setIsExpading(true);

                    for (int i=0;i<threeItemInfo.getResults().size();i++){

                        threeItemInfo.getResults().get(i).setIsExpading(true);

                        for(int j=0; j<threeItemInfo.getResults().get(i).getResults().size();j++){

                            threeItemInfo.getResults().get(i).getResults().get(j).setIsExpading(true);

                        }
                    }
                    removeAll(threeItemInfo.getResults());

                }
            }
        });

    }

    private void bindViewTwo(ViewHolderHelper helper, final ThreeItemInfo threeItemInfo, final int position) {
        TextView tv_2 = helper.getView(R.id.tv_2);
        LinearLayout extend_leaders = helper.getView(R.id.extend_leaders);
        final ImageView shift_leaders =helper.getView(R.id.shift_leaders);
        final ImageView shift_leaders_down =helper.getView(R.id.shift_leaders_down);
        tv_2.setText(threeItemInfo.getContent());

        if(threeItemInfo.getIsExpading()){
            shift_leaders.setVisibility(View.VISIBLE);
            shift_leaders_down.setVisibility(View.INVISIBLE);
        }else{
            shift_leaders.setVisibility(View.INVISIBLE);
            shift_leaders_down.setVisibility(View.VISIBLE);
        }


        extend_leaders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (threeItemInfo.getResults() != null && threeItemInfo.getResults().size() != 0 && threeItemInfo.getIsExpading()) {
                    shift_leaders.setVisibility(View.INVISIBLE);
                    shift_leaders_down.setVisibility(View.VISIBLE);
                    Log.e("点击后展开","点击后展开");

                    threeItemInfo.setIsExpading(false);
                    addAllAt(position + 1, threeItemInfo.getResults());

                } else if (threeItemInfo.getResults() != null & threeItemInfo.getResults().size() != 0 && !threeItemInfo.getIsExpading()) {
                    Log.e("点击后收缩","点击后收缩");
                    shift_leaders.setVisibility(View.VISIBLE);
                    shift_leaders_down.setVisibility(View.INVISIBLE);


                    for (ThreeItemInfo item : threeItemInfo.getResults()) {
                        if (!item.getIsExpading()) {
                            removeAll(item.getResults());
                        }
                    }

                    threeItemInfo.setIsExpading(true);
                    for (int i=0;i<threeItemInfo.getResults().size();i++){

                        threeItemInfo.getResults().get(i).setIsExpading(true);

                    }
                    removeAll(threeItemInfo.getResults());

                }
            }
        });
    }

    private void bindViewThree(ViewHolderHelper helper,final ThreeItemInfo threeItemInfo, final int position) {
        TextView tv_3 = helper.getView(R.id.tv_3);
        LinearLayout extend_leaders = helper.getView(R.id.extend_leaders);
        final ImageView shift_leaders =helper.getView(R.id.shift_leaders);
        final ImageView shift_leaders_down =helper.getView(R.id.shift_leaders_down);

        if(threeItemInfo.getIsExpading()){
            shift_leaders.setVisibility(View.VISIBLE);
            shift_leaders_down.setVisibility(View.INVISIBLE);
        }else{
            shift_leaders.setVisibility(View.INVISIBLE);
            shift_leaders_down.setVisibility(View.VISIBLE);
        }


        tv_3.setText(threeItemInfo.getContent());
        extend_leaders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (threeItemInfo.getResults() != null && threeItemInfo.getResults().size() != 0 && threeItemInfo.getIsExpading()) {

                    shift_leaders.setVisibility(View.INVISIBLE);
                    shift_leaders_down.setVisibility(View.VISIBLE);


                    Log.e("点击后展开","点击后展开");
                    threeItemInfo.setIsExpading(false);
                    addAllAt(position + 1, threeItemInfo.getResults());


                } else if (threeItemInfo.getResults() != null & threeItemInfo.getResults().size() != 0 && !threeItemInfo.getIsExpading()) {

                    shift_leaders.setVisibility(View.VISIBLE);
                    shift_leaders_down.setVisibility(View.INVISIBLE);

                    Log.e("点击后收缩","点击后收缩");

                    threeItemInfo.setIsExpading(true);
                    removeAll(threeItemInfo.getResults());

                }
            }
        });
    }

    private void bindViewFour(ViewHolderHelper helper, final ThreeItemInfo threeItemInfo, final int position) {
        TextView tv_4 = helper.getView(R.id.tv_4);
        LinearLayout linearLayout = helper.getView(R.id.linearLayout);
        final ImageView isChoosed=helper.getView(R.id.isChoosed);
        tv_4.setText(threeItemInfo.getContent());

        if(threeItemInfo.getIs_choosed()){
            isChoosed.setSelected(true);
        }else{
            isChoosed.setSelected(false);
        }

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(threeItemInfo.getIs_choosed()){

                    isChoosed.setSelected(false);
                    threeItemInfo.setIs_choosed(false);

                }else{
                    isChoosed.setSelected(true);
                    threeItemInfo.setIs_choosed(true);

                }


                if (onItemThreeClickListener != null) {
                    onItemThreeClickListener.ThreeOnclickListener(position);
                }
            }
        });
    }


    public interface OnItemThreeClickListener {
        void ThreeOnclickListener(int position);
    }

    private OnItemThreeClickListener onItemThreeClickListener;

    public void setOnItemThreeClickListener(OnItemThreeClickListener onItemThreeClickListener) {
        this.onItemThreeClickListener = onItemThreeClickListener;
    }
}
