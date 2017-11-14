package com.lanwei.governmentstar.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蓝威科技-技术开发1 on 2017/7/27.
 */

public class PollcyThree {

    private String content;
    private String opId;
    private boolean not_expand = false;
    public List<PollcyFour> pollcyFours = new ArrayList<>();


    public boolean isNot_expand() {
        return not_expand;
    }

    public void setNot_expand(boolean not_expand) {
        this.not_expand = not_expand;
    }

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

    public List<PollcyFour> getPollcyFours() {
        return pollcyFours;
    }

    public void setPollcyFours(List<PollcyFour> pollcyFours) {
        this.pollcyFours = pollcyFours;
    }

    @Override
    public String toString() {
        return "PollcyThree{" +
                "content='" + content + '\'' +
                ", pollcyFours=" + pollcyFours +
                '}';
    }

}
