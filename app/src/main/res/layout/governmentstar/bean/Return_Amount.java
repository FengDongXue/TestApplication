package com.lanwei.governmentstar.bean;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/19.
 */

public class Return_Amount {


//       "data": {
//                "gwnz_num": 1,
//                "manage_num": 6,
//                "ggtz_num": 0,
//                "dbsx_num": 3,
//                "swcy_num": 2
//                "zwyx_num":0
//    }

    private Data data;
    private String message;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}
