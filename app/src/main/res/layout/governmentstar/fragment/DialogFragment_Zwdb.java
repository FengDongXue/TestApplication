package com.lanwei.governmentstar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanwei.governmentstar.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 蓝威科技-技术开发1 on 2017/10/24.
 */

public class DialogFragment_Zwdb extends DialogFragment {


    @InjectView(R.id.iv_remind)
    ImageView ivRemind;
    @InjectView(R.id.condition_remind)
    TextView conditionRemind;
    @InjectView(R.id.type_remind)
    TextView typeRemind;
    @InjectView(R.id.title)
    TextView title;
    @InjectView(R.id.date_finish)
    TextView dateFinish;
    @InjectView(R.id.time_last)
    TextView timeLast;
    @InjectView(R.id.reminder)
    TextView reminder;
    @InjectView(R.id.deal)
    TextView deal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.layout_dialogfragment, container, false);
        setCancelable(false);
        view.findViewById(R.id.deal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Log.e("namefdsfdsf ","的广东广东省广东省");
            }
        });

        ButterKnife.inject(getActivity());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

//    @OnClick(R.id.deal)
//    public void onViewClicked() {
//
//        this.dismiss();
//        Log.e("namefdsfdsf ","的广东广东省广东省");
//
//    }
}
