package com.lanwei.governmentstar.bean;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/1.
 */

public class Expanable_Bean2 {

    private int isChoosed;
    private InCirculationTree2.DeptList content;
    private ZwyxTreeBean.DataBean context1;
    private int isTurn;

    public Expanable_Bean2(int isChoosed, InCirculationTree2.DeptList content, int isTurn) {
        this.isChoosed = isChoosed;
        this.isTurn = isTurn;
        this.content = content;

    }

    public Expanable_Bean2(int isChoosed, ZwyxTreeBean.DataBean context1, int isTurn) {
        this.isChoosed = isChoosed;
        this.isTurn = isTurn;
        this.context1 = context1;

    }

    public int getIsTurn() {
        return isTurn;
    }

    public void setIsTurn(int isTurn) {
        this.isTurn = isTurn;
    }

    public int getIsChoosed() {
        return isChoosed;
    }

    public void setIsChoosed(int isChoosed) {
        this.isChoosed = isChoosed;
    }

    public InCirculationTree2.DeptList getContent() {
        return content;
    }

    public void setContent(InCirculationTree2.DeptList content) {
        this.content = content;
    }

    public ZwyxTreeBean.DataBean getContext1() {
        return context1;
    }

    public void setContext1(ZwyxTreeBean.DataBean context1) {
        this.context1 = context1;
    }
}
