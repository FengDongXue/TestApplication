package com.lanwei.governmentstar.utils;

/**
 * ${END}
 * <p>
 * created by song on 2017/3/23.18:02
 */

public interface Constant {
    //    总的URL
//    String BASEURL = "http://www.govstar.gov.cn/json/";
//    String BASEURL = "http://47.92.80.23/json/";
//    String BASEURL = "http://192.168.1.120:188/json/";
    String BASEURL = "http://121.42.29.226/json/";

    //全部机关
    String GETALLGOV = "mobile?action=getDept&type=json";

    String GETALL = "mobile";

    //部门
    String DPTACTION = "getSector";
    //用户列表
    String CTSACTION = "getUser";
    //用户详情
    String CTSDTSACTION = "getAccount";

    //搜索用户
    String SEARCHACTION = "searchUser";

    //修改密码
    String PWDACTION = "updatePwd";

    //收文列表
    String MYHAND = "swcyList";

    //公文拟制列表
    String DOCUMENT = "gwnzList";

    //收文传阅
    String SWCYOPINION = "swcyOpinion";


    //公文拟制意见
    String OPIACTION = "gwnzOpinion";

    //拟办提交
    String NBEDITACTIO = "swcyNbEdit";

    //公文拟制处理结果
    String RESULTINFO = "resultInfo";

    //驳回提交
    String GWNZBH = "gwnzBh";

    String GWNZDETAIL = "gwnzDetail";

    //终止拟制
    String GWNZTERMINATION = "gwnzTermination";

    //版本号
    String VERSION = "version";

    //邮箱列表
    String ZWYXLIST = "zwyxList";

    //邮箱已发送列表
    String ZWYXSENDLIST = "zwyxSendList";

    //邮箱已临时列表
    String ZWYXTEMPSAVELIST = "zwyxTempSaveList";

    //邮箱已删除列表
    String ZWYXDELLIST = "zwyxDelList";

    //邮箱已删除列表
    String ZWYXCOLLECTIONLIST = "zwyxCollectionList";

    //邮箱未读个数
    String ZWYXNOREAD = "zwyxCount";

    //选择器结果
    public final  static  int   WHEEL_DIALOG_RESULT = 10003;

    //双列选择器结果
    public final  static  int   WHEEL_DIALOGS_RESULT = 10002;

    //保存密码
    public final  static  String   PASSWORD = "password";

    //审批申请列表
    String SPSQLIST = "spsqList";

    //审批申请详情
    String SPSQSHXX = "spsqShxx";
    //审批申请详情
    String SPSQCKxx = "spsqCKxx";
    //审批申请详情
    String spsqCreate = "spsqCreate";
}
