package com.lanwei.governmentstar.bean;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/24.
 */

public class Retrun_Down {


 //   {"data":
    //     [{"opOpinion":"?ˉ??ˉ?????1′??-?¤?é?3?1??-|é?￠???",
    //       "opCreateName":"史振环",
    //        "opCreateTime":"2017-04-24 15:29"},
    //      {"opOpinion":"?????§è?¨?????????",
    //       "opCreateName":"卢红梅",
    //        "opCreateTime":"2017-04-24 15:29"}]
    // }

    private ArrayList<Data> data;
    private String message;

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Data{

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



}
