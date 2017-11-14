package com.lanwei.governmentstar.http;

import com.google.gson.JsonObject;
import com.lanwei.governmentstar.bean.BN_TDept;
import com.lanwei.governmentstar.bean.Bean_QuickReply;
import com.lanwei.governmentstar.bean.Bean_Reply_Add;
import com.lanwei.governmentstar.bean.Bean_Return_Flow;
import com.lanwei.governmentstar.bean.Bean_Spsq;
import com.lanwei.governmentstar.bean.Collection_Return;
import com.lanwei.governmentstar.bean.Condition_Shift;
import com.lanwei.governmentstar.bean.Data_HanddownTree;
import com.lanwei.governmentstar.bean.DocumentDetails;
import com.lanwei.governmentstar.bean.HandDown;
import com.lanwei.governmentstar.bean.InCirculationTree;
import com.lanwei.governmentstar.bean.InCirculationTree2;
import com.lanwei.governmentstar.bean.Logging_Success;
import com.lanwei.governmentstar.bean.Module;
import com.lanwei.governmentstar.bean.Notification_Item;
import com.lanwei.governmentstar.bean.Notification_List;
import com.lanwei.governmentstar.bean.Result_Message;
import com.lanwei.governmentstar.bean.Result_Messsge;
import com.lanwei.governmentstar.bean.Result_Push;
import com.lanwei.governmentstar.bean.Retrun_Classify;
import com.lanwei.governmentstar.bean.Retrun_Down;
import com.lanwei.governmentstar.bean.Return_Amount;
import com.lanwei.governmentstar.bean.Return_Down2;
import com.lanwei.governmentstar.bean.Return_Finish;
import com.lanwei.governmentstar.bean.Return_Guidang;
import com.lanwei.governmentstar.bean.Return_Handdown_Comin;
import com.lanwei.governmentstar.bean.Return_Many;
import com.lanwei.governmentstar.bean.Return_Nizhi;
import com.lanwei.governmentstar.bean.Return_Nizhidan;
import com.lanwei.governmentstar.bean.Return_Notification;
import com.lanwei.governmentstar.bean.Return_Private;
import com.lanwei.governmentstar.bean.Return_Proceed;
import com.lanwei.governmentstar.bean.Return_Shouwen;
import com.lanwei.governmentstar.bean.Return_Wait;
import com.lanwei.governmentstar.bean.Return_Weijiwei;
import com.lanwei.governmentstar.bean.Return_Weijiweif;
import com.lanwei.governmentstar.bean.Return_Work;
import com.lanwei.governmentstar.bean.Root;
import com.lanwei.governmentstar.bean.SendMSM;
import com.lanwei.governmentstar.bean.Service_Content;
import com.lanwei.governmentstar.bean.Signup;
import com.lanwei.governmentstar.bean.SpsqFlow;
import com.lanwei.governmentstar.bean.ZwyxTreeBean;
import com.lanwei.governmentstar.utils.Constant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/3/21.
 */

public interface GovernmentApi {

    /**
     * 获取全部机关数据
     */
    @GET("mobile?action=getDept")
    Call<ArrayList<BN_TDept>> getVideoNewsList();

    /**
     * 登录
     */
    @GET("mobile?action=login")
    Call<Logging_Success> logging(@Query("userName") String userName, @Query("userPwd") String userPwd);

    /**
     * 切换用户
     */
    @GET("mobile?action=swichLogin")
    Call<Logging_Success> swichLogin(@Query("userId") String userName, @Query("LoginName") String opId);

    /**
     * 发送验证码
     */
    @GET("mobile?action=sendMSM")
    Call<SendMSM> sendMSM(@Query("mobile") String mobile);

    /**
     * 获取服务协议内容
     */
    @GET("mobile?action=protocol")
    Call<String> getProtocol();

    /**
     * 验证手机验证码
     */
    @GET("mobile?action=validateSMS")
    Call<SendMSM> getCode(@Query("mobile") String mobile, @Query("code") String code);

    /**
     * 修改密码
     */
    @GET("mobile?action=updatePwd")
    Call<Service_Content> resetPassword(@Query("userName") String userName, @Query("newPwd") String newPwd);

    /**
     * 收文催办
     */
    @GET("mobile?action=swcyHasten")
    Call<Result_Push> pushReceive(@Query("userId") String userId, @Query("opId") String opId);

    /**
     * 获取隐私
     */
    @GET("mobile?action=getUserPrivacy")
    Call<Return_Private> getPrivacy(@Query("userId") String userId);


    /**
     * 更改隐私
     */
    @GET("mobile?action=updateUserPrivacy")
    Call<SendMSM> updatePrivacy(@Query("userId") String userId, @Query("notOfficeSendEmail") String notOfficeSendEmail, @Query("officClerkSendEmail") String officClerkSendEmail, @Query("displayPhone") String displayPhone);


    /**
     * 拟制催办
     */
    @GET("mobile?action=gwnzHasten")
    Call<Result_Push> pushDrawer(@Query("userId") String userId, @Query("opId") String opId);

    /**
     * 查看起草流程
     */
    @GET("mobile?action=gwnzFlow")
    Call<Return_Proceed> watch_proceed(@Query("opId") String opId, @Query("userId") String userId);

    /**
     * 查看拟办流程
     */
    @GET("mobile?action=swcyFlow")
    Call<Return_Proceed> watch_proceed_receiver(@Query("opId") String opId, @Query("userId") String userId);

    /**
     * 查看工作主页
     */
    @GET("mobile?action=workHome")
    Call<ArrayList<Return_Work>> main_work(@Query("userId") String userId);

    /**
     * 查看多人意见
     */
    @GET("mobile?action=swcyOpinion")
    Call<Return_Many> watch_advices(@Query("opId") String opId, @Query("flowStatus") String flowStatus);


    /**
     * 查看待办
     */
    @GET("mobile?action=matterList")
    Call<Return_Wait> Wait(@Query("userId") String userId, @Query("pageNo") String pageNo, @Query("order") String order);

    /**
     * 查看待办
     */
    @GET("mobile?action=noticeList")
    Call<Return_Notification> Notification(@Query("userId") String userId, @Query("pageNo") String pageNo, @Query("order") String order);


    /**
     * 获取未处理消息个数
     */
    @GET("mobile?action=getTotalNum")
    Call<Return_Amount> return_amount_daiban(@Query("userId") String userId);

    /**
     * 查看留言
     */
    @GET("mobile?action=swcyMessage")
    Call<Return_Down2> watch_liuyan(@Query("opId") String opId, @Query("userId") String userId);


    /**
     * 科员回复
     */
    @GET("mobile?action=swcyReply")
    Call<Retrun_Down> watch_replys(@Query("opId") String opId);

    /**
     * 快捷回复内容的获取
     */
    @GET("mobile?action=getQuickReply")
    Call<Bean_QuickReply> getQuickReply(@Query("userId") String userId, @Query("state") String state );


    /**
     * 增加快捷回复的条数
     */
    @GET("mobile?action=setQuickReply")
    Call<Bean_Reply_Add> setQuickReply(@Query("userId") String userId, @Query("state") String state, @Query("content") String content  );


    /**
     * 删除快捷回复
     */
    @GET("mobile?action=delQuickReply")
    Call<Result_Message> delQuickReply(@Query("opId") String opId );


    /*更新版本*/
    @POST("mobile?action=verifiedVersion")
    Call<JsonObject> verifiedVersion(@Query("version") String version, @Query("code") String code);

    /*未读消息总个数*/
    @POST("mobile?action=getTotalNum")
    Call<JsonObject> getTotalNum(@Query("userId") String userId);

    /**
     * 公文拟制
     */
    @POST("mobile?")
    Call<JsonObject> documentFiction(@Query("action") String action, @Query("opId") String opId);

    /*提交审核*/
    @POST("mobile?action=gwnzShEdit")
    Call<JsonObject> gwnzShEdit(@Query("opId") String opid, @Query("userId") String userId, @Query("content") String content);

    /*提交审阅*/
    @POST("mobile?action=gwnzSyEdit")
    Call<JsonObject> gwnzSyEdit(@Query("opId") String opid, @Query("userId") String userId, @Query("content") String content);

    /*提交校对*/
    @POST("mobile?action=gwnzJdEdit")
    Call<JsonObject> gwnzJdEdit(@Query("opId") String opid, @Query("userId") String userId, @Query("content") String content, @Query("status") String status);

    /*提交签发*/
    @POST("mobile?action=gwnzQfEdit")
    Call<JsonObject> gwnzQfEdit(@Query("opId") String opid, @Query("userId") String userId, @Query("content") String content);

    /*提交会签*/
    @POST("mobile?action=gwnzHqEdit")
    Call<JsonObject> gwnzHqEdit(@Query("opId") String opid, @Query("userId") String userId, @Query("content") String content);

    //    /*提交核发*/
//    @POST("mobile?action=gwnzHfEdit")
//    Call<Logging_Success> gwnzHfEdit(@Query("opId") String opid ,@Query("userId") String userId,@Query("content") String content,
//    @Query("docStatus") String docStatus,@Query("docZh1") String docZh1 ,@Query("docZh2") String docZh2 ,
//                                     @Query("docZh3") String docZh3 ,@Query("docZh4") String docZh4 ,@Query("docZh5") String docZh5);
    /*提交核发*/
    @GET("mobile?action=gwnzHfEdit")
    Call<JsonObject> gwnzHfEdit(@Query("opId") String opid, @Query("userId") String userId, @Query("content") String content,
                                @Query("docStatus") String docStatus, @Query("docZh1") String docZh1, @Query("docZh2") String docZh2,
                                @Query("docZh3") String docZh3, @Query("docZh4") String docZh4, @Query("docZh5") String docZh5, @Query("deptId") String deptId);

    /*提交驳回*/
    @POST("mobile?action=gwnzBh")
    Call<JsonObject> gwnzBh(@Query("opId") String opid, @Query("userId") String userId, @Query("content") String content);

    /*拟制终止*/
    @POST("mobile?action=gwnzTermination")
    Call<JsonObject> gwnzTermination(@Query("opId") String opid, @Query("userId") String userId);


    /**
     * 收文传阅
     */
    @GET("mobile?")
    Call<JsonObject> inCirculation(@Query("action") String action, @Query("opId") String opId, @Query("userId") String userId);

    /**
     * 处理完成
     */
    @GET("mobile?action=swcyDetail")
    //Call<JsonObject> getInformation( @Query("opId") String opId);
    Call<Return_Finish> getInformation(@Query("opId") String opId);

    /**
     * 文件详情
     */
    @GET("mobile?action=swcyDetail")
    //Call<JsonObject> get_Information( @Query("opId") String opId);
    Call<DocumentDetails> getDocumentDetailsInfo(@Query("opId") String opId);

    /**
     * 收文树
     */

    @POST("mobile?")
    Call<InCirculationTree> inCirculationTree(@Query("action") String action, @Query("userId") String userId, @Query("deptId") String deptId);

    /**
     * 转发树
     */

    @POST("mobile?")
    Call<InCirculationTree> inCirculationTree2(@Query("opId") String opId, @Query("action") String action, @Query("userId") String userId, @Query("deptId") String deptId);

    /**
     * 转发树
     */

    @POST("mobile?")
    Call<InCirculationTree> inCirculationTreeZ(@Query("action") String action, @Query("userId") String userId, @Query("deptId") String deptId, @Query("opId") String opId);


    /**
     * 协办树
     */

    @POST("mobile?action=swcyXbTree")
    Call<InCirculationTree> inCirculationTreeX(@Query("userId") String userId, @Query("deptId") String deptId, @Query("opId") String opId);


    /**
     * 办理和转发 协办树
     */
    @POST("mobile?action=swcyXbrTree")
    Call<Root> swcyXbrTree(@Query("userId") String userId, @Query("deptId") String deptId, @Query("opId") String opId);

    /**
     * 办理和转发 协办树
     */
    @POST("mobile?action=swcyXbrTree")
    Call<Root> swcyXbrTree_weijiwei(@Query("userId") String userId, @Query("deptId") String deptId, @Query("opId") String opId);



    /**
     * 拟办提交
     */
    @POST("mobile?")
    Call<JsonObject> swcyNbEdit(@Query("action") String action, @Query("userId") String userId, @Query("opId") String opId, @Query("opIds") String opIds, @Query("content") String content, @Query("opBsrIds") String opBsrIds);


    //  拟办提交
    @FormUrlEncoded
    @POST("mobile?action=swcyZfUser")
    Call<JsonObject> swcyblEdit(@Field("userId") String userId, @Field("opId") String opId, @Field("opIds") String opIds, @Field("content") String content, @Field("opBsrIds") String opBsrIds);

    // 办事提交
    @FormUrlEncoded
    @POST("mobile?action=swcyBsEdit")
    Call<JsonObject> swcybsEdit(@Field("userId") String userId, @Field("opId") String opId, @Field("opIds") String opIds, @Field("content") String content, @Field("opBsrIds") String opBsrIds, @Field("opXbrId") String opXbrId);

    // 批示提交

    @POST("mobile?action=swcyPsEdit")
    Call<JsonObject> swcypsEdit(@Query("userId") String userId, @Query("opId") String opId, @Query("opIds") String opIds, @Query("content") String content, @Query("opBsrIds") String opBsrIds, @Query("opNbrId") String opNbrId ,@Query("tjms") String tjms);// 批示提交

    // 转发提交
    @FormUrlEncoded
    @POST("mobile?action=swcyZfEdit")
    Call<JsonObject> swcyzfEdit(@Field("userId") String userId, @Field("opId") String opId, @Field("opIds") String opIds, @Field("content") String content, @Field("opBsrIds") String opBsrIds, @Field("opXbrId") String opXbrId);

    // 协办提交

    @POST("mobile?action=swcyXbEdit")
    Call<JsonObject> swcyXbEdit(@Query("userId") String userId, @Query("opId") String opId, @Query("content") String content);


    // 拟办提交

    @POST("mobile?action=swcyNbEdit")
    Call<JsonObject> swcynbEdit(@Query("userId") String userId, @Query("opId") String opId, @Query("state") String state, @Query("opIds") String opIds, @Query("content") String content, @Query("opBsrIds") String opBsrIds);


    // 进入协办

    @GET("mobile?action=swcyXb")
    Call<Return_Finish> swcyXb(@Query("opId") String opId);


    // 承办提交
    @FormUrlEncoded
    @POST("mobile?action=swcyCbEdit")
    Call<JsonObject> swcycbEdit(@Field("userId") String userId, @Field("opId") String opId, @Field("opIds") String opIds, @Field("content") String content, @Field("opXbrId") String opBsrIds);


    // 阅办提交
    @FormUrlEncoded
    @POST("mobile?action=swcyYbEdit")
    Call<JsonObject> swcyybEdit(@Field("userId") String userId, @Field("opId") String opId, @Field("opIds") String opIds, @Field("content") String content, @Field("opBsrIds") String opBsrIds);


    /**
     * 办理提交
     */
    @FormUrlEncoded
    @POST("mobile?")
    Call<Logging_Success> swcyBsEdit(@Field("action") String action, @Field("userId") String userId,
                                     @Field("opId") String opId, @Field("content") String content);


    /**
     * 公告通知列表
     */
    @GET("mobile?action=ggtzList")
    Call<Notification_List> list_notification(@Query("userId") String userId,
                                              @Query("pageNo") String pageNo, @Query("search") String search, @Query("orderBy") String orderBy, @Query("readState") String readState);

    /**
     * 查看通知
     */
    @GET("mobile?action=ggtzDetail")
    Call<Notification_Item> watch_notification(@Query("opId") String opId, @Query("userId") String userId);


    /**
     * 转发树
     */
    @GET("mobile?action=ggtzZfTree")
    Call<InCirculationTree> tree_zhaunfa(@Query("opId") String opId, @Query("userId") String userId, @Query("deptId") String deptId);


    /**
     * 进入转发通知
     */
    @GET("mobile?action=ggtzZf")
    Call<Notification_Item> join_zhuanfa(@Query("opId") String opId, @Query("userId") String userId);

    /**
     * 转发通知
     */
    @POST("mobile?action=ggtzZfEdit")
    Call<Collection_Return> shift_commit(@Query("opId") String opId, @Query("userId") String userId, @Query("message") String message, @Query("opIds") String opIds);


    /**
     * 收藏通知
     */
    @GET("mobile?action=ggtzCollection")
    Call<Collection_Return> collect_notification(@Query("opId") String opId, @Query("userId") String userId);

    /**
     * 取消收藏通知
     */
    @GET("mobile?action=ggtzCollectionFail")
    Call<Collection_Return> collect_cancel(@Query("opId") String opId, @Query("userId") String userId);

    /**
     * 作废
     */
    @GET("mobile?action=ggtzInvalid")
    Call<Collection_Return> delete(@Query("opId") String opId, @Query("userId") String userId, @Query("message") String message);

    /**
     * 接收状态
     */
    @GET("mobile?action=ggtzState")
    Call<Condition_Shift> watch_state(@Query("opId") String opId, @Query("userId") String userId);


    /**
     * 模板类型
     */
    @GET("mobile?action=getGgtzMould")
    Call<JsonObject> modules_choose();

    /**
     * 发布公告通知接收者
     */
    @GET("mobile?action=getReceiveAcc")
    Call<Return_Weijiweif> receivers_notification(@Query("deptId") String deptId);

    /**
     * 发布公告通知接收者
     */
    @GET("mobile?action=getReceiveAcc")
    Call<InCirculationTree2> receivers_notification2(@Query("deptId") String deptId);

    /**
     * 获取公告通知的模板
     */
    @GET("mobile?action=getChoiceMould")
    Call<Module> module_choose(@Query("opId") String opId);

    /**
     * 发布公告通知
     */

    //公告通知新建
    @POST("mobile?action=ggtzEdit")
    Call<JsonObject> noticeEdit(@Body MultipartBody multipartBody);

    //    Call<JsonObject> getEdit(@Query("action") String action, @Part("userId") RequestBody userId, @Part("mailTitle") RequestBody mailTitle, @Part("mailContent") RequestBody mailContent, @Part("opIds") RequestBody opIds, @Part() List<MultipartBody.Part> imageBodyPart);


    /**
     * 下发列表
     */

    //下发列表
    @GET("mobile?action=getGwxfList")
    Call<HandDown> list_handdown(@Query("userId") String userId, @Query("state") String state, @Query("search") String search, @Query("pageNo") String pageNo);


    /**
     * 进入下发
     */
    //进入下发
    @GET("mobile?action=getGwxfXf")
    Call<Return_Handdown_Comin> handdown_comein(@Query("opId") String opId, @Query("userId") String userId);

    /**
     * 下发树
     */
    @GET("mobile?action=getGwxfTree")
    Call<Data_HanddownTree> handdown_tree(@Query("deptId") String deptId);


    /**
     * 来文单位
     */
    @GET("mobile?action=getGwxfLwdw")
    Call<JsonObject> department_from(@Query("userId") String userId);

    /**
     * 查看下发事业单位列表
     */
    @GET("mobile?action=gwxfStatusList")
    Call<HandDown> departments_list(@Query("opId") String opId, @Query("pageNo") String pageNo);


    /**
     * 下发撤回
     */
    @POST("mobile?action=gwxfCh")
    Call<JsonObject> handdown_withdrawal(@Query("opId") String opId ,@Query("userId") String userId ,@Query("message") String message ,@Query("state") String state);


    /**
     * 下发失效
     */
    @GET("mobile?action=gwxfSx")
    Call<JsonObject> handdown_cancel(@Query("opId") String opId, @Query("userId") String userId);


    /**
     * 下发提交
     */
    @POST("mobile?action=gwxfEdit")
    Call<JsonObject> summit_handdown(@Query("opId") String opId, @Query("userId") String userId, @Query("gwxfTitle") String gwxfTitle,
                                     @Query("gwxfData") String gwxfData, @Query("gwxfLwdwName") String gwxfLwdwName, @Query("gwxfLwdwId") String gwxfLwdwId,
                                     @Query("opIds") String opIds, @Query("content") String content, @Query("gwxfZh1") String gwxfZh1,
                                     @Query("gwxfZh2") String gwxfZh2, @Query("gwxfZh3") String gwxfZh3, @Query("gwxfZh4") String gwxfZh4, @Query("gwxfZh5") String gwxfZh5);


    /**
     *公文归档
     */
    @GET("mobile?action=wjgdList")
    Call<Retrun_Classify> list_classify(@Query("userId") String userId , @Query("pageNo") String pageNo , @Query("state") String state );


    /**
     *进入拟制归档界面
     */
    @GET("mobile?action=wjgdGwnz")
    Call<Return_Nizhi> nizhi_comin(@Query("userId") String userId , @Query("opId") String opId );

    /**
     *拟制归档的提交
     */
    @GET("mobile?action=wjgdGwnzEdit")
    Call<JsonObject> nizhi_commit( @Query("userId") String userId , @Query("opId") String opId ,@Query("message") String message, @Query("zsJg") String zsJg,@Query("csJg") String csJg,@Query("gxState") String gxState,@Query("bcnx") String bcnx);


    /**
     *拟制归档 – 公文处理单
     */
    @GET("mobile?action=wjgdGwnzCld")
    Call<Return_Nizhidan> nizhi_chulidan(@Query("userId") String userId , @Query("opId") String opId );


    /**
     *拟制归档 – 更改共享
     */
    @GET("mobile?action=wjgdGwnzGxEdit")
    Call<JsonObject> nizhi_share(@Query("opId") String opId ,@Query("userId") String userId ,@Query("gxState") String gxState );



    /**
     *拟制归档的提交
     */
    @GET("mobile?action=wjgdSwcyEdit")
    Call<JsonObject> showuen_commit( @Query("userId") String userId , @Query("opId") String opId ,@Query("message") String message,@Query("bcNx") String bcNx );



    /**
     *进入收文归档
     */
    @GET("mobile?action=wjgdSwcy")
    Call<Return_Shouwen> showuen_comin(@Query("userId") String userId , @Query("opId") String opId);


    /**
     *进入收文归档说明
     */
    @GET("mobile?action=wjgdSwcyCld")
    Call<JsonObject> showuen_instruction(@Query("opId") String opId);







    /**
     * 政务邮箱
     */

    /*政务邮箱删除*/
    @POST("mobile?action=zwyxListDel")
    Call<JsonObject> zwyxListDel(@Query("userId") String userId, @Query("opIds") String opIds);

    /*政务邮箱临时列表*/
    @POST("mobile?action=zwyxTempSaveList")
    Call<JsonObject> zwyxTempSaveList(@Query("userId") String userId, @Query("pageNo") String pageNo, @Query("search ") String search);

    /*政务邮箱临时删除*/
    @POST("mobile?action=zwyxTempSaveDel")
    Call<JsonObject> zwyxTempSaveDel(@Query("opIds") String opIds);

    /*政务邮箱收藏列表*/
    @POST("mobile?action=zwyxCollectionList")
    Call<JsonObject> zwyxCollectionList(@Query("userId") String userId, @Query("pageNo") String pageNo, @Query("search ") String search);

    /*政务邮箱已发送列表*/
    @POST("mobile?action=zwyxSendList")
    Call<JsonObject> zwyxSendList(@Query("userId") String userId, @Query("pageNo") String pageNo, @Query("search ") String search);

    /*政务邮箱已发送删除*/
    @POST("mobile?action=zwyxSendDel")
    Call<JsonObject> zwyxSendDel(@Query("opIds") String opIds);

    /*政务邮箱已删除列表*/
    @POST("mobile?action=zwyxDelList")
    Call<JsonObject> zwyxDelList(@Query("userId") String userId, @Query("pageNo") String pageNo, @Query("search ") String search);

    /*政务邮箱删除恢复*/
    @POST("mobile?action=zwyxDelRecovery")
    Call<JsonObject> zwyxDelRecovery(@Query("opIds") String opIds);

    /*政务邮箱删除 彻底删除*/
    @POST("mobile?action=zwyxDel")
    Call<JsonObject> zwyxDel(@Query("opIds") String opIds);

    /*政务邮箱收藏*/
    @POST("mobile?action=zwyxCollection")
    Call<JsonObject> zwyxCollection(@Query("userId") String userId, @Query("opIds") String opIds);

    /*政务邮箱取消收藏*/
    @POST("mobile?action=zwyxCancelCollection")
    Call<JsonObject> zwyxCancelCollection(@Query("opIds") String opIds);

    /*政务邮箱 查看邮件*/
    @POST("mobile?action=zwyxDetail")
    Call<JsonObject> zwyxDetail(@Query("userId") String userId, @Query("opId") String opId);

    /*政务邮箱 回复邮件*/
    @POST("mobile?action=zwyxReply")
    Call<JsonObject> zwyxReply(@Query("userId") String userId, @Query("opId") String opId, @Query("message") String message);

    /**
     * * 政务邮箱 收件人树
     */

    @POST("mobile?")
    Call<ZwyxTreeBean> zwyxTree(@Query("action") String action, @Query("deptId") String deptId, @Query("search") String search);

//    /**
//     * * 政务邮箱 收件人树
//     */
//
//    @POST("mobile?")
//    Call<JsonObject> zwyxTree1(@Query("action") String action, @Query("deptId") String deptId);

    /**
     * 查看审批申请流程
     */
    @GET("mobile?action=spsqFlow")
    Call<SpsqFlow> spsqFlow(@Query("opId") String opId, @Query("userId") String userId);

    /*同意申请*/
    @POST("mobile?action=spsqShtg")
    Call<JsonObject> spsqShtg(@Query("opId") String opid, @Query("userId") String userId, @Query("message") String message);

    /*拒绝申请*/
    @POST("mobile?action=spsqShjj")
    Call<JsonObject> spsqShjj(@Query("opId") String opid, @Query("userId") String userId, @Query("message") String message);


    /*资质印章提交申请*/
    @POST("mobile?action=doInsert")
    Call<JsonObject> doInsert_zzzz(@Query("userId") String userId, @Query("type") String Type, @Query("childtype") String childtype, @Query("SHRIds") String SHRIds, @Query("CSRlds") String CSRlds, @Query("stampfile") String stampfile, @Query("fileNum") String fileNum, @Query("fileType") String fileType, @Query("tampType") String tampType, @Query("reason") String reason);

    /*审核撤回*/
    @POST("mobile?action=spsqSHch")
    Call<JsonObject> spsqSHch(@Query("userId") String userId, @Query("opId") String opId, @Query("message") String message);


    /*审核查看流程*/
    @POST("mobile?action=spsqFlow")
    Call<Bean_Return_Flow> spsqFlow(@Query("userId") String userId, @Query("opId") String opId, @Query("message") String message);


    /*审核查看流程*/
    @POST("mobile?action=getspsqAcc")
    Call<Bean_Spsq> getspsqAcc(@Query("userId") String userId, @Query("deptId") String deptId);


    /*审核查看流程*/
    @GET("mobile?action=dzgdDealedMenu")
    Call<Return_Guidang> dzgdDealedMenu(@Query("userId") String userId, @Query("opId") String opId);

//    http://121.42.29.226/json/mobile?action=sydwQsxx&userId=1f062397-b8eb-40a3-974a-95ea03d11e5b&opId=1a8632ed-604b-4195-acab-777b0a641b34
    /*签收文件的接口*/
    @GET("mobile?action=sydwQsxx")
    Call<Signup> sydwQsxx(@Query("userId") String userId, @Query("opId") String opId);

//    http://121.42.29.226/json/mobile?action=gwcyDw&userId=1f062397-b8eb-40a3-974a-95ea03d11e5b
    /*签收文件的接口*/
    @GET("mobile?action=gwcyDw")
    Call<JsonObject> gwcyDw(@Query("userId") String userId);


//    http://121.42.29.226/json/mobile?action=sydwQs&opId=1a8632ed-604b-4195-acab-777b0a641b34&userId=1f062397-b8eb-40a3-974a-95ea03d11e5b
    @POST("mobile?action=sydwQs")
    Call<JsonObject> sydwQs(@Query("userId") String userId ,@Query("opId") String opId ,@Query("wjbz") String wjbz );


//    http://121.42.29.226/json/mobile?action=zfjgQs&opId=1a8632ed-604b-4195-acab-777b0a641b34&userId=1f062397-b8eb-40a3-974a-95ea03d11e5b&wjzy=天下&wjbz=环境&lwdw=天津市政府&jjcd=独山大道&wjfl=文件类别&clqx=处理期限&gwzh1=公文字号1&gwzh2=公文字号2&gwzh3=公文字号3&gwzh4=公文字号4&gwzh5=公文字号5    @POST("mobile?action=zfjgQs")
     @POST("mobile?action=zfjgQs")
     Call<JsonObject> zfjgQs(@Query("userId") String userId ,@Query("opId") String opId ,@Query("wjzy") String wjzy,@Query("wjbz") String wjbz,@Query("lwdw") String lwdw,@Query("jjcd") String jjcd,@Query("wjfl") String wjfl,@Query("clqx") String clqx,@Query("gwzh1") String gwzh1,@Query("gwzh2") String gwzh2,@Query("gwzh3") String gwzh3 ,@Query("gwzh4") String gwzh4 ,@Query("gwzh5") String gwzh5 );


}
