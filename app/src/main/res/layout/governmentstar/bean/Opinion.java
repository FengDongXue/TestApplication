package com.lanwei.governmentstar.bean;

import java.io.Serializable;

/**
 * Created by xong2 on 2017/4/3.
 */

public class Opinion implements Serializable{
    /**
     * opOpinion : totti
     * opCreateName : 张浩宁
     * opCreateTime : 2017-03-27 20:57
     */

    private String opOpinion;
    private String opCreateName;
    private String opCreateTime;

    public String getOpOpinion() {
        return opOpinion;
    }

    public void setOpOpinion(String opOpinion) {
        this.opOpinion = opOpinion;
    }

    public String getOpCreateName() {
        return opCreateName;
    }

    public void setOpCreateName(String opCreateName) {
        this.opCreateName = opCreateName;
    }

    public String getOpCreateTime() {
        return opCreateTime;
    }

    public void setOpCreateTime(String opCreateTime) {
        this.opCreateTime = opCreateTime;
    }
}
