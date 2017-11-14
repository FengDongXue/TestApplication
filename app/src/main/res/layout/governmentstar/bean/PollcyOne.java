package com.lanwei.governmentstar.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蓝威科技-技术开发1 on 2017/7/27.
 */

public class PollcyOne {

    private String content;
    private String opId;
    public List<PollcyTwo> pollcyTwos = new ArrayList<>();

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

    public List<PollcyTwo> getPollcyTwos() {
        return pollcyTwos;
    }

    public void setPollcyTwos(List<PollcyTwo> pollcyTwos) {
        this.pollcyTwos = pollcyTwos;
    }

    @Override
    public String toString() {
        return "PollcyOne{" +
                "content='" + content + '\'' +
                ", pollcyTwos=" + pollcyTwos +
                '}';
    }

}
