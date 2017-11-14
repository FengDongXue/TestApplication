package com.lanwei.governmentstar.bean;

import java.util.List;

/**
 * Created by 蓝威科技-技术部3 on 2017/4/3.
 */

public class DocumentBaseC {
    private Data data;

    public DocumentBaseC() {
    }

    public DocumentBaseC(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DocumentBaseC{" +
                "data=" + data +
                '}';
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{
        @Override
        public String toString() {
            return "Data{" +
                    "opId='" + opId + '\'' +
                    ", docUnit='" + docUnit + '\'' +
                    ", opCreateTime='" + opCreateTime + '\'' +
                    ", docTheme='" + docTheme + '\'' +
                    ", docUrgent='" + docUrgent + '\'' +
                    ", docUrl='" + docUrl + '\'' +
                    ", docStatus='" + docStatus + '\'' +
                    ", docType='" + docType + '\'' +
                    ", flowList=" + flowList +
                    ", fileList=" + fileList +
                    ", imageList=" + imageList +
                    '}';
        }

        String opId;
        String docUnit;
        String opCreateTime;
        String docTheme;
        String docUrgent;
        String docUrl;
        String docStatus;
        String docType;
        String docTitle;

        private List<FlowList > flowList;
        private List<FileList > fileList;
        private List<FileList > imageList;

        public String getDocTitle() {
            return docTitle;
        }

        public void setDocTitle(String docTitle) {
            this.docTitle = docTitle;
        }

        public String getOpId() {
            return opId;
        }

        public void setOpId(String opId) {
            this.opId = opId;
        }

        public String getDocUnit() {
            return docUnit;
        }

        public void setDocUnit(String docUnit) {
            this.docUnit = docUnit;
        }

        public String getOpCreateTime() {
            return opCreateTime;
        }

        public void setOpCreateTime(String opCreateTime) {
            this.opCreateTime = opCreateTime;
        }

        public String getDocTheme() {
            return docTheme;
        }

        public void setDocTheme(String docTheme) {
            this.docTheme = docTheme;
        }

        public String getDocUrgent() {
            return docUrgent;
        }

        public void setDocUrgent(String docUrgent) {
            this.docUrgent = docUrgent;
        }

        public String getDocUrl() {
            return docUrl;
        }

        public void setDocUrl(String docUrl) {
            this.docUrl = docUrl;
        }

        public String getDocStatus() {
            return docStatus;
        }

        public void setDocStatus(String docStatus) {
            this.docStatus = docStatus;
        }

        public String getDocType() {
            return docType;
        }

        public void setDocType(String docType) {
            this.docType = docType;
        }

        public List<FlowList> getFlowList() {
            return flowList;
        }

        public void setFlowList(List<FlowList> flowList) {
            this.flowList = flowList;
        }

        public List<FileList> getFileList() {
            return fileList;
        }

        public void setFileList(List<FileList> fileList) {
            this.fileList = fileList;
        }

        public List<FileList> getImageList() {
            return imageList;
        }

        public void setImageList(List<FileList> imageList) {
            this.imageList = imageList;
        }

        public Data() {
        }

        public Data(String opId, String docUnit, String opCreateTime, String docTheme, String docUrgent, String docUrl, String docStatus, String docType, List<FlowList> flowList, List<FileList> fileList, List<FileList> imageList) {
            this.opId = opId;
            this.docUnit = docUnit;
            this.opCreateTime = opCreateTime;
            this.docTheme = docTheme;
            this.docUrgent = docUrgent;
            this.docUrl = docUrl;
            this.docStatus = docStatus;
            this.docType = docType;
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
    public static class FileList{
        @Override
        public String toString() {
            return "FileList{" +
                    "path='" + path + '\'' +
                    ", opName='" + opName + '\'' +
                    '}';
        }

        String path;
        String opName;
        String filePreview;

        public String getFilePreview() {
            return filePreview;
        }

        public void setFilePreview(String filePreview) {
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

        public FileList(String path, String opName) {
            this.path = path;
            this.opName = opName;
        }
    }
}
