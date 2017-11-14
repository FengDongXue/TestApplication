package com.lanwei.governmentstar.bean;

import java.io.Serializable;

/**
 * Created by 蓝威科技—技术部2 on 2017/4/10.
 */

public class Intendnb implements Serializable {

    /**
     * message : opId，userId，content 不能为空
     * data : hardwork
     */

    private String message;
    private Object data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
