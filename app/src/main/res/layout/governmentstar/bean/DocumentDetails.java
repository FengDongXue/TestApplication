package com.lanwei.governmentstar.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by 蓝威科技—技术部2 on 2017/4/19.
 */

public class DocumentDetails implements Serializable {
    /**
     * data : {"opId":"245f53f0-2d66-4b41-8e74-2ce8aa7b6449","opCreateName":"张浩宁","docType":"命令","docTheme":"\r\n应急管理","isHq":"会签文件","opShName":hardwork,"opCreateTime":"2017/04/17 22:56","docUrl":"http://121.42.29.226:80/uploadFile/mk-gwnz/18537d2f-5466-4279-b4be-ab66e2c96d7b/grxt-ordinary-3/grxt-ordinary-3_20170417225618974.doc","flowList":hardwork,"fileList":[],"docStatus":hardwork}
     */
    /**
     * opId : 245f53f0-2d66-4b41-8e74-2ce8aa7b6449
     * opCreateName : 张浩宁
     * docType : 命令
     * docTheme :
     * 应急管理
     * isHq : 会签文件
     * opShName : hardwork
     * opCreateTime : 2017/04/17 22:56
     * docUrl : http://121.42.29.226:80/uploadFile/mk-gwnz/18537d2f-5466-4279-b4be-ab66e2c96d7b/grxt-ordinary-3/grxt-ordinary-3_20170417225618974.doc
     * flowList : hardwork
     * fileList : []
     * docStatus : hardwork
     */

    private String opId;
    private String opCreateName;
    private String docType;
    private String docTheme;
    private String docTitle;
    private String isHq;
    private String opShName;
    private String opCreateTime;
    private String docUrl;
    private ArrayList<Objects> flowList;
    private String docStatus;
    private ArrayList<FileData> fileList;

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getOpCreateName() {
        return opCreateName;
    }

    public void setOpCreateName(String opCreateName) {
        this.opCreateName = opCreateName;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocTheme() {
        return docTheme;
    }

    public void setDocTheme(String docTheme) {
        this.docTheme = docTheme;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getIsHq() {
        return isHq;
    }

    public void setIsHq(String isHq) {
        this.isHq = isHq;
    }

    public Object getOpShName() {
        return opShName;
    }

    public void setOpShName(String opShName) {
        this.opShName = opShName;
    }

    public String getOpCreateTime() {
        return opCreateTime;
    }

    public void setOpCreateTime(String opCreateTime) {
        this.opCreateTime = opCreateTime;
    }

    public String getDocUrl() {
        return docUrl;
    }

    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }

//    public String getFilePreview() {
//        return filePreview;
//    }
//
//    public void setFilePreview(String filePreview) {
//        this.filePreview = filePreview;
//    }

    public ArrayList<Objects> getFlowList() {
        return flowList;
    }

    public void setFlowList(ArrayList<Objects> flowList) {
        this.flowList = flowList;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public void setFileList(ArrayList<FileData> fileList) {
        this.fileList = fileList;
    }

    public ArrayList<FileData> getFileList() {
        return fileList;
    }


    public class FileData{

        /**
         * path : http://121.42.29.226:80/uploadFile/mk-gwnz/18537d2f-5466-4279-b4be-ab66e2c96d7b/file/20170516/2bd4d192-b23f-424e-9e89-fa2f1fbaaef7/2003doc.doc
         * opName : 2003doc.doc
         * filePreview : http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=db7a6e16-d351-4bfb-af9f-e09f3c403714
         */

        private String path;
        private String opName;
        private String filePreview;

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

}
