package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by 蓝威科技-技术开发1 on 2017/7/28.
 */

public class Data2 {

    private List<ChildList3> childList ;
    private int isChoosed;
    private String opId;
    private int isTurn;
    private String opName;


    public Data2(List<ChildList3> childList, int isChoosed, String opId, int isTurn, String opName) {
        this.childList = childList;
        this.isChoosed = isChoosed;
        this.opId = opId;
        this.isTurn = isTurn;
        this.opName = opName;
    }

    public int getIsChoosed() {
        return isChoosed;
    }

    public void setIsChoosed(int isChoosed) {
        this.isChoosed = isChoosed;
    }

    public int getIsTurn() {
        return isTurn;
    }

    public void setIsTurn(int isTurn) {
        this.isTurn = isTurn;
    }

    public void setChildList(List<ChildList3> childList){
        this.childList = childList;
    }
    public List<ChildList3> getChildList(){
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
