package com.lanwei.governmentstar.activity.zyx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanwei.governmentstar.R;

/**
 * Created by 蓝威科技-技术部3 on 2017/4/26.
 */

public class YesConditionFragemnt extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.yescondition_layout,container,false);
        return view;
    }
}
