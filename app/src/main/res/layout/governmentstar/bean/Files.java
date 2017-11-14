package com.lanwei.governmentstar.bean;

/**
 * Created by 蓝威科技-技术开发1 on 2017/8/8.
 */

public class Files {


    private String path;
    private String opName;
    private String filePreview;

    public Files(String path, String opName, String filePreview) {

        this.path = path;
        this.opName = opName;
        this.filePreview = filePreview;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getFilePreview() {
        return filePreview;
    }

    public void setFilePreview(String filePreview) {
        this.filePreview = filePreview;
    }
}
