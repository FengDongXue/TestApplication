package com.lanwei.governmentstar.bean;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/8/5.
 */

public class Data_HanddownTree {

    private ArrayList<ChildList> data;

    public ArrayList<ChildList> getData() {
        return data;
    }

    public void setData(ArrayList<ChildList> data) {
        this.data = data;
    }

    public class ChildList{

        ArrayList<childList_bean> childList;
        private String opId;
        private String opName;
        private int isChoosed;
        private int isTurn;

        public int getIsTurn() {
            return isTurn;
        }

        public void setIsTurn(int isTurn) {
            this.isTurn = isTurn;
        }

        public int getIsChoosed() {
            return isChoosed;
        }

        public void setIsChoosed(int isChoosed) {
            this.isChoosed = isChoosed;
        }

        public String getOpId() {
            return opId;
        }

        public void setOpId(String opId) {
            this.opId = opId;
        }

        public String getOpName() {
            return opName;
        }

        public void setOpName(String opName) {
            this.opName = opName;
        }

        public ArrayList<childList_bean> getChildList() {
            return childList;
        }

        public void setChildList(ArrayList<childList_bean> childList) {
            this.childList = childList;
        }
    }


    //    "childList":[],"opId":"0154","opName":"蓟州区下窝头镇人民政府"

    public class childList_bean{

        private String opId;
        private String opName;
        private ArrayList childList;
        private int isChoosed;

        public int getIsChoosed() {
            return isChoosed;
        }

        public void setIsChoosed(int isChoosed) {
            this.isChoosed = isChoosed;
        }

        public String getOpId() {
            return opId;
        }

        public void setOpId(String opId) {
            this.opId = opId;
        }

        public String getOpName() {
            return opName;
        }

        public void setOpName(String opName) {
            this.opName = opName;
        }

        public ArrayList getChildList() {
            return childList;
        }

        public void setChildList(ArrayList childList) {
            this.childList = childList;
        }
    }


}
