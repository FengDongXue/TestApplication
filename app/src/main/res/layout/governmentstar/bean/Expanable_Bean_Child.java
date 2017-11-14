package com.lanwei.governmentstar.bean;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/1.
 */

public class Expanable_Bean_Child {

    private ZwyxTreeBean.ChildListBean content1;
    private int isChoosed;
    private InCirculationTree.ChildList content;
    private int isTurn;

    public Expanable_Bean_Child(int isChoosed, InCirculationTree.ChildList content, int isTurn) {
        this.isChoosed = isChoosed;
        this.isTurn = isTurn;
        this.content = content;

    }
    public Expanable_Bean_Child(int isChoosed, ZwyxTreeBean.ChildListBean content, int isTurn) {
        this.isChoosed = isChoosed;
        this.isTurn = isTurn;
        this.content1 = content;

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

    public InCirculationTree.ChildList getContent() {
        return content;
    }

    public void setContent(InCirculationTree.ChildList content) {
        this.content = content;
    }

    public ZwyxTreeBean.ChildListBean getContent1() {
        return content1;
    }

    public void setContent1(ZwyxTreeBean.ChildListBean content1) {
        this.content1 = content1;
    }
}
