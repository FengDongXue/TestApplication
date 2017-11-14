package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by 蓝威科技-技术开发1 on 2017/7/28.
 */

public class ChildList3 {


    private List<ChildList2> childList ;
    private String opId;
    private String opName;
    private int isChoosed;
    private int isTurn;

    public ChildList3(List<ChildList2> childList, String opId, String opName, int isChoosed, int isTurn) {
        this.childList = childList;
        this.opId = opId;
        this.opName = opName;
        this.isChoosed = isChoosed;
        this.isTurn = isTurn;
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

    public void setChildList(List<ChildList2> childList){
        this.childList = childList;
    }
    public List<ChildList2> getChildList(){
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
