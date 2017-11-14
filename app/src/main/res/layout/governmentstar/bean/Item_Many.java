package com.lanwei.governmentstar.bean;

/**
 * Created by 蓝威科技-技术开发1 on 2017/4/4.
 */

public class Item_Many {

    private String url;
    private String content;
    private String time_finish;
    private String time_during;

    public Item_Many(String url, String content, String time_during,String time_finish) {
        this.url = url;
        this.content = content;
        this.time_during = time_during;
        this.time_finish=time_finish;
    }

    public String getTime_finish() {
        return time_finish;
    }

    public void setTime_finish(String time_finish) {
        this.time_finish = time_finish;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime_during() {
        return time_during;
    }

    public void setTime_during(String time_during) {
        this.time_during = time_during;
    }
}
