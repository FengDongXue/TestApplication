package lanwei.com.gesture_application.http;

import com.google.gson.JsonObject;

import lanwei.com.gesture_application.bean.Beans_Return;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 庄志潮 on 2017/6/28.
 */

public interface SafeTypoApi {
    //验证登陆
    @POST("security?action=validateAcc")
    Call<JsonObject> login(@Query("mobile") String mobile, @Query("password") String password);

    //发送验证码
    @POST("security?action=sendMSM")
    Call<JsonObject> sendSMS(@Query("mobile") String mobile);

    //验证短信
    @POST("security?action=validateMSM")
    Call<JsonObject> validateMSM(@Query("mobile") String mobile, @Query("code") String code, @Query("imei") String imei, @Query("model") String model);

    //扫描二维码
    @POST("qrcode?action=qrCodeValidate")
    Call<JsonObject> qrCodeValidate(@Query("mobile") String mobile, @Query("uuid") String uuid, @Query("imei") String imei);

    //确定登陆网页端
    @POST("qrcode?action=qrCodeLogin")
    Call<JsonObject> qrCodeLogin(@Query("mobile") String mobile, @Query("uuid") String uuid, @Query("imei") String imei);

    //退出登陆网页端
    @POST("qrcode?action=qrcodeLogout")
    Call<JsonObject> qrcodeLogout(@Query("mobile") String mobile, @Query("imei") String imei);

    //验证网页端是否在线
    @POST("security?action=onLine")
    Call<JsonObject> onLine(@Query("mobile") String mobile, @Query("imei") String imei);

    //验证网页端是否解绑
    @POST("security?action=unbundling")
    Call<JsonObject> unbundling(@Query("mobile") String mobile, @Query("imei") String imei);

    //验证是否更新
    @POST("security?action=verifiedVersion")
    Call<JsonObject> verifiedVersion(@Query("mobile") String mobile, @Query("imei") String imei, @Query("version") String version);

    //验证手机是否被锁定
    @POST("security?action=locking")
    Call<JsonObject> locking(@Query("mobile") String mobile, @Query("imei") String imei);

    /**
     * 发布公告通知接收者
     */
    @GET("mobile?action=getReceiveAcc")
    Call<Beans_Return> receivers_notification(@Query("deptId") String deptId);


}
