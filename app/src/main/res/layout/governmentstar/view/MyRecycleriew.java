package com.lanwei.governmentstar.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by 蓝威科技-技术开发1 on 2017/8/22.
 */

public class MyRecycleriew extends RecyclerView {


    public MyRecycleriew(Context context) {
        super(context);
    }

    public MyRecycleriew(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleriew(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
