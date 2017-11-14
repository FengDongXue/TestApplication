package com.lanwei.governmentstar.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蓝威科技-技术开发1 on 2017/7/27.
 */

public class PollcyTwo {

    private String content;
    private String opId;
    public List<PollcyThree> pollcyThrees = new ArrayList<>();

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

    public List<PollcyThree> getPollcyThrees() {
        return pollcyThrees;
    }

    public void setPollcyThrees(List<PollcyThree> pollcyThrees) {
        this.pollcyThrees = pollcyThrees;
    }

    @Override
    public String toString() {
        return "PollcyTwo{" +
                "content='" + content + '\'' +
                ", pollcyThrees=" + pollcyThrees +
                '}';
    }
}
