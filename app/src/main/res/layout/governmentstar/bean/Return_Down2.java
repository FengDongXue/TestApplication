package com.lanwei.governmentstar.bean;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/5/16.
 */

public class Return_Down2 {


    private Data data;


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{

        private String message;
        private String opCreateName;
        private String opCreateTime;


        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
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





}
