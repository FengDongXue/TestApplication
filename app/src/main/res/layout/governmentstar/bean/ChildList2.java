package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by 蓝威科技-技术开发1 on 2017/7/28.
 */

public class ChildList2 {

    private List<ChildList> childList ;

    private String opId;

    private String opName;

    public void setChildList(List<ChildList> childList){
        this.childList = childList;
    }
    public List<ChildList> getChildList(){
        return this.childList;
    }
    public void setOpId(String opId){
        this.opId = opId;
    }
    public String getOpId(){
        return this.opId;
    }
    public void setOpName(String opName){
        this.opName = opName;
    }
    public String getOpName(){
        return this.opName;
    }



}
