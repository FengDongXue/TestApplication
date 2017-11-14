package com.lanwei.governmentstar.http;

import com.google.gson.JsonObject;
import com.lanwei.governmentstar.bean.Result_Messsge;
import com.lanwei.governmentstar.utils.Constant;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * ${END}
 * <p>
 * created by song on 2017/3/23.18:08
 */

public class RetrofitHelper {
    private static RetrofitHelper mInstance = new RetrofitHelper();
    private final Retrofit retrofit;
    public RetrofitApi retrofitApi;
    private OkHttpClient okHttpClient;

    public RetrofitHelper() {


        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //构建OkHttp
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)//日志拦截器
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        retrofitApi = retrofit.create(RetrofitApi.class);
    }

    public static RetrofitHelper getInstance() {
        return mInstance;
    }

    /**
     * 获取全部机关的数据
     *
     * @param adapter
     */
    public void getAllGovInfo(CallBackAdapter adapter) {
        Call<JsonObject> govInfoCall = retrofitApi.getAllGovInfo();
        govInfoCall.enqueue(adapter);
    }

    /**
     * 获取全部部门的数据
     *
     * @param opid
     * @param adapter
     */
    public void getAllDptInfo(String opid, CallBackAdapter adapter) {
        Call<JsonObject> dptInfoCall = retrofitApi.getAllDptInfo(Constant.DPTACTION, opid);
        dptInfoCall.enqueue(adapter);
    }

    /**
     * 获取全部联系人的数据
     *
     * @param opid
     * @param adapter
     */
    public void getAllCtsInfo(String opid, CallBackAdapter adapter) {
        Call<JsonObject> ctsInfoCall = retrofitApi.getAllDptInfo(Constant.CTSACTION, opid);
        ctsInfoCall.enqueue(adapter);
    }

    /**
     * 获取某个联系人的详细信息
     *
     * @param opid
     * @param adapter
     */
    public void getAccountDetailInfo(String opid, CallBackAdapter adapter) {
        Call<JsonObject> ctsInfoCall = retrofitApi.getAccountDetailInfo(Constant.CTSDTSACTION, opid);
        ctsInfoCall.enqueue(adapter);
    }

    /**
     * 搜索某个联系人的详细信息
     *
     * @param opid
     * @param adapter
     */
    public void getSearchUserInfo(String opid, CallBackAdapter adapter) {
        Call<JsonObject> ctsInfoCall = retrofitApi.getSearchUserInfo(Constant.SEARCHACTION, opid);
        ctsInfoCall.enqueue(adapter);
    }

    /***
     * 个人设置
     * @param cardId
     * @param opId 当前修改的opId
     * @param office 机关
     * @param officePhone 机关电话
     * @param userMobile 个人手机号
     * @param part 头像
     * @param adapter
     */
    public void setModifyData(RequestBody opId, RequestBody office, RequestBody officePhone, RequestBody userMobile, RequestBody cardId, MultipartBody.Part part, CallBackAdapter adapter) {
        Call<JsonObject> ctsInfoCall = retrofitApi.setModifyData("modifyInformation", opId, office, officePhone, userMobile, cardId, part);
        ctsInfoCall.enqueue(adapter);
    }

    /**
     * 获取全部部门的数据
     *
     * @param opid
     * @param adapter
     */
    public void getThisGovInfo(String opid, CallBackAdapter adapter) {
        Call<JsonObject> dptInfoCall = retrofitApi.getAllDptInfo(Constant.DPTACTION, opid);
        dptInfoCall.enqueue(adapter);
    }

    /**
     * 获取修改密码的数据
     *
     * @param opid
     * @param adapter
     */
    public void getPwdInfo(String opid, String oldPwd, String newPwd, CallBackYSAdapter adapter) {
        Call<JsonObject> pwdInfoCall = retrofitApi.getPwdInfo(Constant.PWDACTION, opid, oldPwd, newPwd);
        pwdInfoCall.enqueue(adapter);
    }

    /**
     * 获取收文传阅列表的数据
     *
     * @param opid
     * @param adapter
     */
    public void getMyhandInfo(String pageNo, String opid, String search, String docType, String opState, String orderBy, CallBackYSAdapter adapter) {
        Call<JsonObject> handInfoCall = retrofitApi.getHandInfo(Constant.MYHAND, pageNo, opid, search, docType, opState, orderBy);
        handInfoCall.enqueue(adapter);
    }


    /**
     * 获取公文拟制的数据
     *
     * @param opid
     * @param adapter
     */
    public void getDocumentInfo(String pageNo, String opid, String search, String opState, String orderBy, CallBackYSAdapter adapter) {
        Call<JsonObject> docInfoCall = retrofitApi.getDocInfo(Constant.DOCUMENT, pageNo, opid, search, opState, orderBy);
        docInfoCall.enqueue(adapter);
    }


    /**
     * 获取收文传阅的数据
     *
     * @param opid
     * @param adapter
     */
    public void getOpinionInfo(String opid, String flowStatus, CallBackYSAdapter adapter) {
        Call<JsonObject> opiInfoCall = retrofitApi.getOpiInfo(Constant.SWCYOPINION, opid, flowStatus);
        opiInfoCall.enqueue(adapter);
    }


    /**
     * 获取公文拟制意见的数据
     *
     * @param opid
     * @param adapter
     */
    public void getNZOpinionInfo(String opid, String flowStatus, CallBackYSAdapter adapter) {
        Call<JsonObject> opiInfoCall = retrofitApi.getNZOpiInfo(Constant.OPIACTION, opid, flowStatus);
        opiInfoCall.enqueue(adapter);
    }

    /**
     * 获取拟办提交的数据bb
     * swcyNbEdit
     *
     * @param opid
     * @param adapter
     */
    public void getNbEditInfo(String opid, String userId, String content, String opIds, CallBackAdapter adapter) {
        Call<JsonObject> nbeditInfoCall = retrofitApi.getNbEditInfo(Constant.NBEDITACTIO, opid, userId, content, opIds);
        nbeditInfoCall.enqueue(adapter);
    }

    /**
     * 获取公文拟制处理条目的数据
     *
     * @param result
     * @param opState
     * @param docStatus
     * @param adapter
     */
    public void getResultInfo(boolean result, String opState, String docStatus, CallBackAdapter adapter) {
        Call<JsonObject> resultInfoCall = retrofitApi.getResultInfo(Constant.RESULTINFO, result, opState, docStatus);
        resultInfoCall.enqueue(adapter);
    }

    /**
     * 获取驳回的数据
     * swcyNbEdit
     *
     * @param opid
     * @param adapter
     */
    public void getRejectInfo(String opid, String userId, String content, CallBackYSAdapter adapter) {
        Call<JsonObject> rejectInfoCall = retrofitApi.getRejectInfo(Constant.GWNZBH, opid, userId, content);
        rejectInfoCall.enqueue(adapter);
    }

    /**
     * 获取文件详情的信息
     * swcyNbEdit
     *
     * @param opid
     * @param adapter
     */
    public void getDocumentDetaidsInfo(String opid, CallBackAdapter adapter) {
        Call<JsonObject> detailInfoCall = retrofitApi.getDocumentDetaidsInfo(Constant.GWNZDETAIL, opid);
        detailInfoCall.enqueue(adapter);
    }

    /**
     * 获取终止数据
     *
     * @param opid
     * @param adapter
     */
    public void getNbStopInfo(String opid, String userId, CallBackYSAdapter adapter) {
        Call<JsonObject> nbStopInfoCall = retrofitApi.getStopInfo(Constant.GWNZTERMINATION, opid, userId);
        nbStopInfoCall.enqueue(adapter);
    }

    /**
     * 政务邮箱列表
     *
     * @param adapter
     */
    public void getInboxInfo(String userId, String pageNo, String search, CallBackYSAdapter adapter) {
        Call<JsonObject> inboxInfoCall = retrofitApi.getInboxInfo(Constant.ZWYXLIST, userId, pageNo, search);
        inboxInfoCall.enqueue(adapter);
    }

    /**
     * 政务邮箱已发送
     *
     * @param adapter
     */
    public void getSentInfo(String userId, String pageNo, String search, CallBackYSAdapter adapter) {
        Call<JsonObject> sentInfoCall = retrofitApi.getSentInfo(Constant.ZWYXSENDLIST, userId, pageNo, search);
        sentInfoCall.enqueue(adapter);
    }

    /**
     * 政务邮箱临时邮件
     *
     * @param adapter
     */
    public void getTemporaryInfo(String userId, String pageNo, String search, CallBackYSAdapter adapter) {
        Call<JsonObject> temporaryInfoCall = retrofitApi.getTemporaryInfo(Constant.ZWYXTEMPSAVELIST, userId, pageNo, search);
        temporaryInfoCall.enqueue(adapter);
    }

    /**
     * 政务邮箱已删除
     *
     * @param adapter
     */
    public void getDeleteInfo(String userId, String pageNo, String search, CallBackYSAdapter adapter) {
        Call<JsonObject> deleteInfoCall = retrofitApi.getDeleteInfo(Constant.ZWYXDELLIST, userId, pageNo, search);
        deleteInfoCall.enqueue(adapter);
    }

    /**
     * 政务邮箱收藏的邮件
     *
     * @param adapter
     */
    public void getCollectInfo(String userId, String pageNo, String search, CallBackYSAdapter adapter) {
        Call<JsonObject> collectInfoCall = retrofitApi.getCollectInfo(Constant.ZWYXCOLLECTIONLIST, userId, pageNo, search);
        collectInfoCall.enqueue(adapter);
    }

    /**
     * 政务邮箱未读
     *
     * @param adapter
     */
    public void getNoReadInfo(String userId, CallBackYSAdapter adapter) {
        Call<JsonObject> noReadInfoCall = retrofitApi.getNoRead(Constant.ZWYXNOREAD, userId);
        noReadInfoCall.enqueue(adapter);
    }

//    /***
//     * 政务邮箱新建
//     * @param params 附件的集合
//     * @param userId  当前用户的userId
//     * @param mailTitle  邮件标题
//     * @param mailContent  邮件内容
//     * @param opIds 所有的收件人
//     * @param adapter
//     */
//    public void zwyxEdit(RequestBody userId, RequestBody mailTitle, RequestBody mailContent, RequestBody opIds, List<MultipartBody.Part> imageBodyPart, CallBackYSAdapter adapter) {
//        Call<JsonObject> zwyxEditCall = retrofitApi.getEdit("zwyxEdit", userId, mailTitle, mailContent, opIds, imageBodyPart);
//        zwyxEditCall.enqueue(adapter);
//    }

    /***
     * 政务邮箱新建
     * @param params 附件的集合
     * @param userId  当前用户的userId
     * @param mailTitle  邮件标题
     * @param mailContent  邮件内容
     * @param opIds 所有的收件人
     * @param adapter
     */
    public void zwyxEdit(RequestBody userId, RequestBody mailTitle, RequestBody mailContent, RequestBody opIds, Map<String, RequestBody> params, CallBackYSAdapter adapter) {
        Call<JsonObject> zwyxEditCall = retrofitApi.getEdit("zwyxEdit", userId, mailTitle, mailContent, opIds, params);
        zwyxEditCall.enqueue(adapter);
    }

    /***
     * 政务邮箱新建
     * @param params 附件的集合
     * @param userId  当前用户的userId
     * @param noticeTitle  邮件标题
     * @param noticeContent  邮件内容
     * @param opIds 所有的收件人
     * @param adapter
     */
//    String userId,String noticeTemplateId,String noticeTemplateId,String noticeContent,String opIds,List<File> files
    public void noticeEdit(RequestBody userId, RequestBody noticeTemplateId, RequestBody noticeTitle, RequestBody noticeContent, RequestBody opIds, Map<String, RequestBody> params, CallBackYSAdapter adapter) {
        Call<JsonObject> zwyxEditCall = retrofitApi.getNotice("ggtzEdit", userId, noticeTemplateId, noticeTitle, noticeContent, opIds, params);
        zwyxEditCall.enqueue(adapter);
    }


    /***
     * 资质印章提交申请
     * @param params 附件的集合
     * @param userId  当前用户的userId
     * @param adapter
     */
//    @Query("userId") String userId, @Query("Type") String Type, @Query("childtype") String childtype, @Query("SHRIds") String SHRIds, @Query("CSRlds") String CSRlds, @Query("stampfile") String stampfile, @Query("fileNum") String fileNum, @Query("fileType") String fileType, @Query("tampType") String tampType, @Query("reason") String reason
    public void doInsert_zzzz(RequestBody userId,RequestBody BT, RequestBody type, RequestBody childtype, RequestBody SHRIds, RequestBody CSRlds, RequestBody stampfile, RequestBody fileNum, RequestBody fileType, RequestBody tampType, RequestBody reason, Map<String, RequestBody> params, CallBackYSAdapter adapter) {
        Call<JsonObject> zwyxEditCall = retrofitApi.getZzzz("spsqInsert", userId,BT, type, childtype, SHRIds, CSRlds,stampfile,fileNum,fileType,tampType,reason, params);
        zwyxEditCall.enqueue(adapter);
    }

    /***
     * 外出申请公务出行提交申请
     * @param params 附件的集合
     * @param userId  当前用户的userId
     * @param adapter
     */
//    @Query("userId") String userId, @Query("Type") String Type, @Query("childtype") String childtype, @Query("SHRIds") String SHRIds, @Query("CSRlds") String CSRlds, @Query("stampfile") String stampfile, @Query("fileNum") String fileNum, @Query("fileType") String fileType, @Query("tampType") String tampType, @Query("reason") String reason
    public void doInsert_gwcx(RequestBody userId,RequestBody BT, RequestBody type, RequestBody childtype, RequestBody SHRIds, RequestBody CSRlds,RequestBody txrids, RequestBody startTime, RequestBody endTime, RequestBody vehicleType, RequestBody place,  RequestBody vehicleCharge, RequestBody foodCharge, RequestBody hotalCharge, RequestBody roadCharge, RequestBody otherCharge, RequestBody allCharge, RequestBody reason, RequestBody txrNum, Map<String, RequestBody> params,CallBackYSAdapter adapter) {
        Call<JsonObject> zwyxEditCall = retrofitApi.getGwcx("spsqInsert", userId,BT, type, childtype, SHRIds, CSRlds,txrids,startTime,endTime,vehicleType,place,vehicleCharge,foodCharge,hotalCharge,roadCharge,otherCharge,allCharge,reason,txrNum, params);
        zwyxEditCall.enqueue(adapter);
    }

  /***
     * 申请采购_物品申请
     * @param params 附件的集合
     * @param userId  当前用户的userId
     * @param adapter
     */
    public void doInsert_Sqcg(RequestBody userId,RequestBody BT, RequestBody type, RequestBody childtype, RequestBody SHRIds, RequestBody CSRlds,RequestBody Buytype, RequestBody Reason, RequestBody Deliverydatetime, RequestBody Name, RequestBody standard,  RequestBody num, RequestBody price, RequestBody unit , Map<String, RequestBody> params,CallBackYSAdapter adapter) {
        Call<JsonObject> zwyxEditCall = retrofitApi.getSqcg("spsqInsert", userId, BT,type, childtype, SHRIds, CSRlds,Buytype,Reason,Deliverydatetime,Name,standard,num,price,unit ,params);
        zwyxEditCall.enqueue(adapter);
    }

    /***
     * 请假申请
     * @param params 附件的集合
     * @param userId  当前用户的userId
     * @param adapter
     */
    public void doInsert_Qjsq(String opId,RequestBody userId,RequestBody BT, RequestBody type, RequestBody childtype, RequestBody SHRIds, RequestBody CSRlds,RequestBody startTime , RequestBody endTime , RequestBody reason , Map<String, RequestBody> params,CallBackYSAdapter adapter) {
        Call<JsonObject> zwyxEditCall = retrofitApi.getQjsq("spsqInsert",opId, userId, BT,type, childtype, SHRIds, CSRlds,startTime,endTime,reason,params);
        zwyxEditCall.enqueue(adapter);
    }

    /***
     * 请假申请
     * @param params 附件的集合
     * @param adapter
     */
    public void dospsqImagLoad(String opId,Map<String, RequestBody> params,CallBackYSAdapter adapter) {
        Call<JsonObject> zwyxEditCall = retrofitApi.spsqImagLoad("spsqImagLoad",opId,params);
        zwyxEditCall.enqueue(adapter);
    }

    /***
     * 请假申请
     * @param params 附件的集合
     * @param userId  当前用户的userId
     * @param adapter
     */
    public void doInsert_Qjsq2(RequestBody userId,RequestBody BT, RequestBody type, RequestBody childtype, RequestBody SHRIds, RequestBody CSRlds,RequestBody docContent, Map<String, RequestBody> params,CallBackYSAdapter adapter) {
        Call<JsonObject> zwyxEditCall = retrofitApi.getQjsq2("spsqInsert", userId, BT,type, childtype, SHRIds, CSRlds,docContent,params);
        zwyxEditCall.enqueue(adapter);
    }

    /**
     * 获取审批申请列表的数据
     *
     * @param userId
     * @param adapter
     */
    public void spsqList(String pageNo, String userId, String search, String state, String orderby, CallBackYSAdapter adapter) {
//    public void spsqList(String pageNo, String userId, String state, CallBackYSAdapter adapter) {

        Call<JsonObject> spsqListCall = retrofitApi.getSpsqList(Constant.SPSQLIST, pageNo, userId, search, state, orderby);
//        Call<JsonObject> spsqListCall = retrofitApi.getSpsqList(Constant.SPSQLIST, pageNo, userId, state);

        spsqListCall.enqueue(adapter);
    }


    /**
     * 审批申请详情页
     *
     * @param userId
     * @param adapter
     */
    public void spsqShxx(String opid, String userId, String message, CallBackYSAdapter adapter) {
        Call<JsonObject> spsqShxxCall = retrofitApi.getSpsqShxx(Constant.SPSQSHXX, opid, userId, message);

        spsqShxxCall.enqueue(adapter);
    }


    /**
     * 审批申请详情页
     *
     * @param userId
     * @param adapter
     */
    public void spsqCKxx(String opid, String userId, String message, CallBackYSAdapter adapter) {
        Call<JsonObject> spsqShxxCall = retrofitApi.getSpsqShxx(Constant.SPSQCKxx, opid, userId, message);
        spsqShxxCall.enqueue(adapter);
    }

    /**
     * 获取opId
     *
     * @param adapter
     */
    public void spsqCreate(CallBackYSAdapter adapter) {
        Call<JsonObject> spsqShxxCall = retrofitApi.getspsqCreate(Constant.spsqCreate);
        spsqShxxCall.enqueue(adapter);
    }


}

