package com.lanwei.governmentstar.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lanwei.governmentstar.R;

/**
 * Created by xong2 on 2017/3/16.
 */

public class BaseFragment extends Fragment {


    public TextView title;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View baseTitle = inflater.inflate(R.layout.base_title, null);
        title = (TextView) baseTitle.findViewById(R.id.tv_address);

        return initView();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        activity = getActivity();

//        ((HomeActivity) activity)
        super.onCreate(savedInstanceState);
        initData();
    }

    /**
     * 加载界面
     */
    public View initView() {
        return null;
    }
    /**
     * 加载数据
     */
    public void initData() {
    }
}
