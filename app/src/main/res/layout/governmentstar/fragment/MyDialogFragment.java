package com.lanwei.governmentstar.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.lanwei.governmentstar.R;

/**
 * Created by 蓝威科技-技术开发1 on 2017/8/14.
 */

public class MyDialogFragment extends DialogFragment {

    private TextView textView;
    private Context context;

    public MyDialogFragment(Context context, String msg) {

        this.context =context;
        if(textView != null){
            textView.setText(msg);
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.loading, container,false);
        textView = (TextView) view.findViewById(R.id.jiazai);




        // 加载动画效果
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.loading_anim);
        ImageView rotate = (ImageView) view.findViewById(R.id.rotate);
        rotate.startAnimation(animation);

        return view;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }

    @Override
    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


}
