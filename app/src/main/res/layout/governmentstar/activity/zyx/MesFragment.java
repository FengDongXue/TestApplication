package com.lanwei.governmentstar.activity.zyx;

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

import com.lanwei.governmentstar.R;

/**
 * Created by 蓝威科技—技术部2 on 2017/4/6.
 */

public class MesFragment extends Fragment {
    private Activity activity;
    private View view;
    private RadioGroup mButtons;
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";


    @SuppressLint("ValidFragment")
    public MesFragment(Activity activity) {
        this.activity = activity;
    }

    @SuppressLint("ValidFragment")
    public MesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        view = getActivity().getLayoutInflater().inflate(R.layout.fragment_mes, null);

        params.setMargins(0, 0, 0, 0);
        LinearLayout linearLayout = new LinearLayout(inflater.getContext());
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(view);

        return linearLayout;

    }

}
