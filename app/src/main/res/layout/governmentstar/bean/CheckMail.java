package com.lanwei.governmentstar.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Administrator on 2017/5/31/031.
 */

public class CheckMail implements Serializable {
    private String opId;
    private String mailTitle;
    private String mailUrl;
    private String mailDate;
    private String mailAttn;
    private String mailSend;
    private ArrayList<FileData> fileList;

    public String getOpId() {
        return opId;
    }

    public void setOpId(String opId) {
        this.opId = opId;
    }

    public String getMailTitle() {
        return mailTitle;
    }

    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }

    public String getMailUrl() {
        return mailUrl;
    }

    public void setMailUrl(String mailUrl) {
        this.mailUrl = mailUrl;
    }

    public String getMailDate() {
        return mailDate;
    }

    public void setMailDate(String mailDate) {
        this.mailDate = mailDate;
    }

    public String getMailAttn() {
        return mailAttn;
    }

    public void setMailAttn(String mailAttn) {
        this.mailAttn = mailAttn;
    }

    public String getMailSend() {
        return mailSend;
    }

    public void setMailSend(String mailSend) {
        this.mailSend = mailSend;
    }


    public void setFileList(ArrayList<FileData> fileList) {
        this.fileList = fileList;
    }

    public ArrayList<FileData> getFileList() {
        return fileList;
    }


    public class FileData {

        /**
         * path : http://121.42.29.226:80/uploadFile/mk-xtgl-zwyx/18537d2f-5466-4279-b4be-ab66e2c96d7b/file/20170531/69b51bc2-aee4-4306-9b4f-9251094ae483/2.jpg
         * opName : 2.jpg
         * filePreview : http://121.42.29.226:80/docHtml/fileHtml.jsp?opId=6a87e6c4-9add-4dd2-9905-7f1289867a62
         */

        @Override
        public String toString() {
            return "FileList{" +
                    "path='" + path + '\'' +
                    ", opName='" + opName + '\'' +
                    ", filePreview='" + filePreview + '\'' +
                    '}';
        }

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
