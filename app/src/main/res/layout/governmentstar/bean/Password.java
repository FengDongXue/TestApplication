package com.lanwei.governmentstar.bean;

import java.io.Serializable;

/**
 * Created by 蓝威科技—技术部2 on 2017/3/31.
 */

public class Password implements Serializable {

    /**
     * message : 密码修改成功！
     * data : true
     */

    private String message;
    private boolean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
