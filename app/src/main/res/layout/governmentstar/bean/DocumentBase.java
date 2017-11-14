package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by 蓝威科技-技术部3 on 2017/4/3.
 */

public class DocumentBase {
    private Data data;

    @Override
    public String toString() {
        return "DocumentBase{" +
                "data=" + data +
                '}';
    }

    public DocumentBase() {
    }

    public DocumentBase(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        public Data() {
        }

        String opId;
        String opCreateName;
        String docType;
        String docTheme;
        String docTitle;
        String isHq;
        String opShName;
        String opCreateTime;
        String docUrl;
        String docStatus;
        private List<FlowList> flowList;
        private List<DocStatus > fileList;
        private List<DocStatus > imageList;

        public String getDocStatus() {
            return docStatus;
        }

        public void setDocStatus(String docStatus) {
            this.docStatus = docStatus;
        }

        public Data(String opId, String opCreateName, String docType, String docTheme, String docTitle, String isHq, String opShName, String opCreateTime, String docUrl, String docStatus, List<FlowList> flowList, List<DocStatus> fileList, List<DocStatus> imageList) {
            this.opId = opId;
            this.opCreateName = opCreateName;
            this.docType = docType;
            this.docTheme = docTheme;
            this.docTitle = docTitle;
            this.isHq = isHq;
            this.opShName = opShName;
            this.opCreateTime = opCreateTime;
            this.docUrl = docUrl;
            this.docStatus = docStatus;
            this.flowList = flowList;
            this.fileList = fileList;
            this.imageList = imageList;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "opId='" + opId + '\'' +
                    ", opCreateName='" + opCreateName + '\'' +
                    ", docType='" + docType + '\'' +
                    ", docTheme='" + docTheme + '\'' +
                    ", docTitle='" + docTitle + '\'' +
                    ", isHq='" + isHq + '\'' +
                    ", opShName='" + opShName + '\'' +
                    ", opCreateTime='" + opCreateTime + '\'' +
                    ", docUrl='" + docUrl + '\'' +
                    ", docStatus='" + docStatus + '\'' +
                    ", flowList=" + flowList +
                    ", fileList=" + fileList +
                    ", imageList=" + imageList +
                    '}';
        }

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

        public String getOpShName() {
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

        public List<FlowList> getFlowList() {
            return flowList;
        }

        public void setFlowList(List<FlowList> flowList) {
            this.flowList = flowList;
        }

        public List<DocStatus> getFileList() {
            return fileList;
        }

        public void setFileList(List<DocStatus> fileList) {
            this.fileList = fileList;
        }

        public List<DocStatus> getImageList() {
            return imageList;
        }

        public void setImageList(List<DocStatus> imageList) {
            this.imageList = imageList;
        }

        public Data(String opId, String opCreateName, String docType, String docTheme, String docTitle, String isHq, String opShName, String opCreateTime, String docUrl, List<FlowList> flowList, List<DocStatus> fileList, List<DocStatus> imageList) {
            this.opId = opId;
            this.opCreateName = opCreateName;
            this.docType = docType;
            this.docTheme = docTheme;
            this.docTitle = docTitle;
            this.isHq = isHq;
            this.opShName = opShName;
            this.opCreateTime = opCreateTime;
            this.docUrl = docUrl;
            this.flowList = flowList;
            this.fileList = fileList;
            this.imageList = imageList;
        }
    }
    public static class FlowList{

        @Override
        public String toString() {
            return "FlowList{" +
                    "flowStatus='" + flowStatus + '\'' +
                    ", flowName='" + flowName + '\'' +
                    ", flowImageUrl='" + flowImageUrl + '\'' +
                    ", flowContent='" + flowContent + '\'' +
                    ", flowTime='" + flowTime + '\'' +
                    '}';
        }

        String flowStatus;
        String flowName;
        String flowImageUrl;
        String flowContent;
        String flowTime;

        public String getFlowStatus() {
            return flowStatus;
        }

        public void setFlowStatus(String flowStatus) {
            this.flowStatus = flowStatus;
        }

        public String getFlowName() {
            return flowName;
        }

        public void setFlowName(String flowName) {
            this.flowName = flowName;
        }

        public String getFlowImageUrl() {
            return flowImageUrl;
        }

        public void setFlowImageUrl(String flowImageUrl) {
            this.flowImageUrl = flowImageUrl;
        }

        public String getFlowContent() {
            return flowContent;
        }

        public void setFlowContent(String flowContent) {
            this.flowContent = flowContent;
        }

        public String getFlowTime() {
            return flowTime;
        }

        public void setFlowTime(String flowTime) {
            this.flowTime = flowTime;
        }

        public FlowList(String flowStatus, String flowName, String flowImageUrl, String flowContent, String flowTime) {
            this.flowStatus = flowStatus;
            this.flowName = flowName;
            this.flowImageUrl = flowImageUrl;
            this.flowContent = flowContent;
            this.flowTime = flowTime;
        }
    }
    public static class DocStatus{
        @Override
        public String toString() {
            return "FileList{" +
                    "path='" + path + '\'' +
                    ", opName='" + opName + '\'' +
                    ", filePreview='" + filePreview + '\'' +
                    '}';
        }

        String path;
        String opName;
        String filePreview;

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

        public DocStatus(String path, String opName, String filePreview) {
            this.path = path;
            this.opName = opName;
            this.filePreview = filePreview;
        }
    }
}
