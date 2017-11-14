package com.lanwei.governmentstar.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/6/3.
 */

public class Condition_Shift implements Serializable{

    private ArrayList<Data> data;

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    //    "opId":"933527f3-b1ef-48df-90b1-a9ef05de1342","opParent":"dcfe4325-eaca-431a-921a-36521f9b1053","opName":"王维兴","opDate":"","opDept":"蓟州区发展和改革委员会 - 党政办公室","zf":false,"ck":false,"sc":false


    public class Data{

        private String opId;
        private String opParent;
        private String opName;
        private String opDate;
        private String opDept;
        private Boolean zf;
        private Boolean ck;
        private Boolean sc;


        public String getOpId() {
            return opId;
        }

        public void setOpId(String opId) {
            this.opId = opId;
        }

        public String getOpParent() {
            return opParent;
        }

        public void setOpParent(String opParent) {
            this.opParent = opParent;
        }

        public String getOpName() {
            return opName;
        }

        public void setOpName(String opName) {
            this.opName = opName;
        }

        public String getOpDate() {
            return opDate;
        }

        public void setOpDate(String opDate) {
            this.opDate = opDate;
        }

        public String getOpDept() {
            return opDept;
        }

        public void setOpDept(String opDept) {
            this.opDept = opDept;
        }

        public Boolean getZf() {
            return zf;
        }

        public void setZf(Boolean zf) {
            this.zf = zf;
        }

        public Boolean getCk() {
            return ck;
        }

        public void setCk(Boolean ck) {
            this.ck = ck;
        }

        public Boolean getSc() {
            return sc;
        }

        public void setSc(Boolean sc) {
            this.sc = sc;
        }
    }



}
