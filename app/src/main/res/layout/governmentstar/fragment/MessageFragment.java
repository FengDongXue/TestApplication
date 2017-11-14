package com.lanwei.governmentstar.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lanwei.governmentstar.R;
import com.lanwei.governmentstar.activity.zyx.InformFragment;
import com.lanwei.governmentstar.activity.zyx.MesFragment;
import com.lanwei.governmentstar.activity.zyx.WaitFragment;

import java.util.ArrayList;

/**
 * Created by 蓝威科技—技术部2 on 2017/3/17.
 */

@SuppressLint("ValidFragment")
public class MessageFragment extends Fragment {
    public Activity activity;
    private TextView tv_title;

    private View view;
    private RadioGroup mButtons;
    private View titleLayout;
    private TextView mes;
    private TextView wait;
    private TextView inform;

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    /**
     * 该集合存放MessageFragment里面的三种Fragment
     **/
    private ArrayList<Fragment> messageFragment = new ArrayList<>();

    public MessageFragment(Activity activity) {
        this.activity = activity;

    }

    @SuppressLint("ValidFragment")
    public MessageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );

        view = inflater.inflate(R.layout.fragment_message, container ,false);


//        params.setMargins(0, 0, 0, 0);
//        LinearLayout linearLayout = new LinearLayout(inflater.getContext());
//        linearLayout.setLayoutParams(params);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearLayout.addView(view);

        initFragments1();

        return view;
    }


    private void initFragments1() {
        messageFragment.add(new WaitFragment(activity));
        messageFragment.add(new InformFragment(activity));
        messageFragment.add(new MesFragment(activity));
    }
}
