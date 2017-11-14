package com.lanwei.governmentstar.bean;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by 蓝威科技-技术开发1 on 2017/9/23.
 */

public class Bean_QuickReply {

    private JsonObject data;
    private String message;

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
