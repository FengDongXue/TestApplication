package com.lanwei.governmentstar.bean;

import retrofit2.http.Field;

/**
 * Created by 蓝威科技-技术开发1 on 2017/5/6.
 */

public class RequestBean {

//    @Field("userId") String userId, @Field("opId") String opId, @Field("opIds") String opIds, @Field("content") String content, @Field("opBsrIds") String opBsrIds

    private String userId;
    private String opId;
    private String opIds;
    private String content;
    private String opBsrIds;


    public RequestBean(String userId, String opId, String opIds, String content, String opBsrIds) {
        this.userId = userId;
        this.opId = opId;
        this.opIds = opIds;
        this.content = content;
        this.opBsrIds = opBsrIds;
    }
}
