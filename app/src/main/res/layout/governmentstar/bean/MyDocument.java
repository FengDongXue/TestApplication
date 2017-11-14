package com.lanwei.governmentstar.bean;

/**
 * Created by 蓝威科技-技术开发1 on 2017/3/20.
 */

public class MyDocument {


    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    private String isSelected ="false";
    private String theme;
    private String time;
    private String status;

    public MyDocument(String theme, String time, String status) {
        this.theme = theme;
        this.time = time;
        this.status = status;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
