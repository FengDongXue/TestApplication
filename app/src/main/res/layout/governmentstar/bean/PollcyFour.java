package com.lanwei.governmentstar.bean;

/**
 * Created by 蓝威科技-技术开发1 on 2017/7/27.
 */

public class PollcyFour {

    private String content;
    private String opId;
    public Boolean isChoosed = false;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public Boolean getChoosed() {
        return isChoosed;
    }

    public void setChoosed(Boolean choosed) {
        isChoosed = choosed;
    }

    @Override
    public String toString() {
        return "PollcyFour{" +
                "content='" + content + '\'' +
                '}';
    }

}
