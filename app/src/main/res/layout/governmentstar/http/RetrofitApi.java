package com.lanwei.governmentstar.http;

import com.google.gson.JsonObject;
import com.lanwei.governmentstar.utils.Constant;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * ${END}
 * <p>
 * created by song on 2017/3/23.18:03
 */

public interface RetrofitApi {
    //获取全部的机关数据
    @GET(Constant.GETALLGOV)
    Call<JsonObject> getAllGovInfo();

    //获取全部部门的数据
    @GET(Constant.GETALL)
    Call<JsonObject> getAllDptInfo(@Query("action") String action, @Query("deptId") String opid);

    //获取全部联系人的数据
    @GET(Constant.GETALL)
    Call<JsonObject> getAllCtsInfo(@Query("action") String action, @Query("deptId") String opid);

    //获取某个联系人的详细信息
    @GET(Constant.GETALL)
    Call<JsonObject> getAccountDetailInfo(@Query("action") String action, @Query("opId") String opid);

    //个人信息设置
    @Multipart
    @POST(Constant.GETALL)
    Call<JsonObject> setModifyData(@Query("action") String action, @Part("op_id") RequestBody opId, @Part("account_office_name") RequestBody office, @Part("account_office_phone") RequestBody officePhone, @Part("account_mobile") RequestBody userMobile, @Part("account_card") RequestBody cardId, @Part MultipartBody.Part part);

    //获取某个联系人的详细信息
    @GET(Constant.GETALL)
    Call<JsonObject> getSearchUserInfo(@Query("action") String action, @Query("name") String opid);

    //修改密码
    @GET(Constant.GETALL)
    Call<JsonObject> getPwdInfo(@Query("action") String action, @Query("opId") String opid, @Query("oldPwd") String oldPwd, @Query("newPwd") String newPwd);

    //获取收文传阅
    @GET(Constant.GETALL)
    Call<JsonObject> getHandInfo(@Query("action") String action, @Query("pageNo") String dptaction, @Query("userId") String opid, @Query("search") String search, @Query("docType") String docType, @Query("opState") String opState, @Query("orderBy") String orderBy);

    //获取公文拟制
    @GET(Constant.GETALL)
    Call<JsonObject> getDocInfo(@Query("action") String action, @Query("pageNo") String dptaction, @Query("opId") String opid, @Query("search") String search, @Query("opState") String opState, @Query("orderBy") String orderBy);

    //获取公文拟制处理结果
    @GET(Constant.GETALL)
    Call<JsonObject> getResultInfo(@Query("action") String action, @Query("result") Boolean result, @Query("opState") String opstates, @Query("docStatus") String docStatus);


    //获取意见
    @GET(Constant.GETALL)
    Call<JsonObject> getOpiInfo(@Query("action") String action, @Query("opId") String dptaction, @Query("flowStatus") String opid);

    //获取意见
    @GET(Constant.GETALL)
    Call<JsonObject> getNZOpiInfo(@Query("action") String action, @Query("opId") String dptaction, @Query("flowStatus") String opid);

    //获取拟办提交
    @GET(Constant.GETALL)
    Call<JsonObject> getNbEditInfo(@Query("action") String action, @Query("opId") String opId, @Query("userId") String userId, @Query("content") String content, @Query("opIds") String opids);//获取拟办提交

    //提交驳回
    @GET(Constant.GETALL)
    Call<JsonObject> getRejectInfo(@Query("action") String action, @Query("opId") String opId, @Query("userId") String userId, @Query("content") String content);

    //提交驳回
    @GET(Constant.GETALL)
    Call<JsonObject> getDocumentDetaidsInfo(@Query("action") String action, @Query("opId") String opId);

    //提交驳回
    @GET(Constant.GETALL)
    Call<JsonObject> getStopInfo(@Query("action") String action, @Query("opId") String opId, @Query("userId") String userId);

    /*政务邮箱列表*/
    @GET(Constant.GETALL)
    Call<JsonObject> getInboxInfo(@Query("action") String action, @Query("userId") String userId, @Query("pageNo") String pageNo, @Query("search") String search);

    /*政务邮箱已发送列表*/
    @GET(Constant.GETALL)
    Call<JsonObject> getSentInfo(@Query("action") String action, @Query("userId") String userId, @Query("pageNo") String pageNo, @Query("search") String search);

    /*政务邮箱临时邮件列表*/
    @GET(Constant.GETALL)
    Call<JsonObject> getTemporaryInfo(@Query("action") String action, @Query("userId") String userId, @Query("pageNo") String pageNo, @Query("search") String search);

    /*政务邮箱已删除列表*/
    @GET(Constant.GETALL)
    Call<JsonObject> getDeleteInfo(@Query("action") String action, @Query("userId") String userId, @Query("pageNo") String pageNo, @Query("search") String search);

    /*政务邮箱收藏的邮件列表*/
    @GET(Constant.GETALL)
    Call<JsonObject> getCollectInfo(@Query("action") String action, @Query("userId") String userId, @Query("pageNo") String pageNo, @Query("search") String search);

    /*政务邮箱未读*/
    @GET(Constant.GETALL)
    Call<JsonObject> getNoRead(@Query("action") String action, @Query("userId") String userId);

    //政务邮箱新建
    @Multipart
    @POST(Constant.GETALL)
//    Call<JsonObject> getEdit(@Query("action") String action, @Part("userId") RequestBody userId, @Part("mailTitle") RequestBody mailTitle, @Part("mailContent") RequestBody mailContent, @Part("opIds") RequestBody opIds, @Part() List<MultipartBody.Part> imageBodyPart);
    Call<JsonObject> getEdit(@Query("action") String action, @Part("userId") RequestBody userId, @Part("mailTitle") RequestBody mailTitle, @Part("mailContent") RequestBody mailContent, @Part("opIds") RequestBody opIds, @PartMap Map<String, RequestBody> params);

    //公告通知新建
    @Multipart
    @POST(Constant.GETALL)
    Call<JsonObject> getNotice(@Query("action") String action, @Part("userId") RequestBody userId, @Part("noticeTemplateId") RequestBody noticeTemplateId, @Part("noticeTitle") RequestBody noticeTitle, @Part("noticeContent") RequestBody noticeContent,  @Part("opIds") RequestBody opIds, @PartMap Map<String, RequestBody> params);

    //资质印章提交申请
    @Multipart
    @POST(Constant.GETALL)
    Call<JsonObject> getZzzz(@Query("action") String action, @Part("userId") RequestBody userId,@Part("BT") RequestBody BT, @Part("type") RequestBody type, @Part("childtype") RequestBody childtype, @Part("SHRIds") RequestBody SHRIds,  @Part("CSRlds") RequestBody CSRlds,  @Part("stampfile") RequestBody stampfile,  @Part("fileNum") RequestBody fileNum,  @Part("fileType") RequestBody fileType,  @Part("tampType") RequestBody tampType,  @Part("reason") RequestBody reason, @PartMap Map<String, RequestBody> params);
//    @Query("userId") String userId, @Query("Type") String Type, @Query("childtype") String childtype, @Query("SHRIds") String SHRIds, @Query("CSRlds") String CSRlds, @Query("stampfile") String stampfile, @Query("fileNum") String fileNum, @Query("fileType") String fileType, @Query("tampType") String tampType, @Query("reason") String reason

    //外出申请公务出行提交申请
    @Multipart
    @POST(Constant.GETALL)
    Call<JsonObject> getGwcx(@Query("action") String action, @Part("userId") RequestBody userId,@Part("BT") RequestBody BT, @Part("type") RequestBody type, @Part("childtype") RequestBody childtype, @Part("SHRIds") RequestBody SHRIds,  @Part("CSRlds") RequestBody CSRlds,  @Part("txrids") RequestBody txrids,  @Part("startTime") RequestBody starttime,  @Part("endTime") RequestBody endtime,  @Part("vehicleType") RequestBody vehicleType,  @Part("place") RequestBody place,  @Part("vehicleCharge") RequestBody vehicleCharge,  @Part("foodCharge") RequestBody foodCharge,  @Part("hotalCharge") RequestBody hotalCharge ,  @Part("roadCharge") RequestBody roadCharge ,  @Part("otherCharge") RequestBody otherCharge,  @Part("allCharge") RequestBody allCharge ,  @Part("reason") RequestBody reason,@Part("txrNum") RequestBody txrNum,@PartMap Map<String, RequestBody> params);


    //申请采购_物品申请
    @Multipart
    @POST(Constant.GETALL)
    Call<JsonObject> getSqcg(@Query("action") String action, @Part("userId") RequestBody userId,@Part("BT") RequestBody BT, @Part("type") RequestBody type, @Part("childtype") RequestBody childtype, @Part("SHRIds") RequestBody SHRIds,  @Part("CSRlds") RequestBody CSRlds,  @Part("buytype") RequestBody buyType,  @Part("reason") RequestBody reason ,  @Part("deliverydatetime") RequestBody deliverydatetime,  @Part("name") RequestBody name,  @Part("standard") RequestBody standard,  @Part("num") RequestBody num,  @Part("price") RequestBody price,  @Part("unit  ") RequestBody unit ,@PartMap Map<String, RequestBody> params);


    //请假申请
    @Multipart
    @POST(Constant.GETALL)
    Call<JsonObject> getQjsq(@Query("action") String action , @Query("opId") String opId , @Part("userId") RequestBody userId, @Part("BT") RequestBody BT, @Part("type") RequestBody type, @Part("childtype") RequestBody childtype, @Part("SHRIds") RequestBody SHRIds,  @Part("CSRlds") RequestBody CSRlds,  @Part("startTime") RequestBody startTime ,  @Part("endTime") RequestBody endTime  ,  @Part("reason") RequestBody reason,@PartMap Map<String, RequestBody> params);


    //图片的提交
    @Multipart
    @POST(Constant.GETALL)
    Call<JsonObject> spsqImagLoad(@Query("action") String action ,@Query("opId") String opId ,@PartMap Map<String, RequestBody> params);


    //请假申请
    @Multipart
    @POST(Constant.GETALL)
    Call<JsonObject> getQjsq2(@Query("action") String action, @Part("userId") RequestBody userId,@Part("BT") RequestBody BT, @Part("type") RequestBody type, @Part("childtype") RequestBody childtype, @Part("SHRIds") RequestBody SHRIds,  @Part("CSRlds") RequestBody CSRlds,  @Part("docContent") RequestBody docContent,@PartMap Map<String, RequestBody> params);


    //获取审批申请的列表
    @GET(Constant.GETALL)
    Call<JsonObject> getSpsqList(@Query("action") String action, @Query("pageNo") String pageNo, @Query("userId") String userId , @Query("search") String search, @Query("state") String state, @Query("oderby") String oderby);

    //获取审批申请的详情页
    @GET(Constant.GETALL)
    Call<JsonObject> getSpsqShxx(@Query("action") String action, @Query("opId") String opid, @Query("userId") String userId ,@Query("message") String message);//获取审批申请的详情页

    @GET(Constant.GETALL)
    Call<JsonObject> getspsqCreate(@Query("action") String action);
}
