package com.lanwei.governmentstar.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蓝威科技-技术开发1 on 2017/7/28.
 */

public class Root {

    private List<Data2> data ;
    private ArrayList<String> accIds;

    public ArrayList<String> getAccIds() {
        return accIds;
    }

    public void setAccIds(ArrayList<String> accIds) {
        this.accIds = accIds;
    }

    public void setData(List<Data2> data){
        this.data = data;
    }
    public List<Data2> getData(){
        return this.data;
    }

}
