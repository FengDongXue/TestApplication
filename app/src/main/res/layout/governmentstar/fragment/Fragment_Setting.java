package com.lanwei.governmentstar.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lanwei.governmentstar.R;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/17.
 */

public class Fragment_Setting extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.layout_base,container);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
