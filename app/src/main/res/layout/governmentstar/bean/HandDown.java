package com.lanwei.governmentstar.bean;

import java.util.ArrayList;

/**
 * Created by 蓝威科技-技术开发1 on 2017/8/4.
 */

public class HandDown {


//    {"pageCount":1,
//      "pageNo":1,
//       "data":[
//
//         {"opId":"681be9ab-6fe4-44d3-a80a-8f8e2791ff32",
//           "issuedTitle":"关于转发选择承办人001",
//            "issuedDeptName":"蓟州区工业和信息化委员会",
//             "opState":"0",
//               "opCreateName":"2017/07/27 14:32"}
//
//              ]
//        }

    private int  pageCount;
    private int  pageNo;
    private ArrayList<Data_Handdown> data;


    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public ArrayList<Data_Handdown> getData() {
        return data;
    }

    public void setData(ArrayList<Data_Handdown> data) {
        this.data = data;
    }
}
