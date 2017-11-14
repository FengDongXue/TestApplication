package com.lanwei.governmentstar.view;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/13.
 */

public class MyTextView extends AppCompatTextView {


    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSpecifiedTextsColor(String text, String specifiedTexts, int color)
    {
        List<Integer> sTextsStartList = new ArrayList<>();

        int sTextLength = specifiedTexts.length();
        String temp = text;
        int lengthFront = 0;//记录被找出后前面的字段的长度
        int start = -1;
        do
        {
            start = temp.indexOf(specifiedTexts);

            if(start != -1)
            {
                start = start + lengthFront;
                sTextsStartList.add(start);
                lengthFront = start + sTextLength;
                temp = text.substring(lengthFront);
            }

        }while(start != -1);

        SpannableStringBuilder styledText = new SpannableStringBuilder(text);
        for(Integer i : sTextsStartList)
        {
            styledText.setSpan(
                    new ForegroundColorSpan(color),
                    i,
                    i + sTextLength,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        setText(styledText);
    }
}
