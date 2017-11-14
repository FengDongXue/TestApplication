package com.lanwei.governmentstar.bean;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/3.
 */

public class Return_Proceed {


    private Boolean isCb;

    private String code;

    private ArrayList<Datas_Item> data;

    public ArrayList<Datas_Item> getData() {
        return data;
    }

    public void setData(ArrayList<Datas_Item> data) {
        this.data = data;
    }

    public Boolean getCb() {
        return isCb;
    }

    public void setCb(Boolean cb) {
        isCb = cb;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
