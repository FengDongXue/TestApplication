package com.lanwei.governmentstar.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.lanwei.governmentstar.bean.Document;
import com.lanwei.governmentstar.bean.Logging_Success;

/**
 * Created by Administrator on 2017/4/11.
 */

public class GetAccount {
    private Logging_Success bean;
    private Document bean1;
    public GetAccount(Context context) {
        String defString = PreferencesManager.getInstance(context, "accountBean").get("jsonStr");
        Gson gson = new Gson();
        bean = gson.fromJson(defString, Logging_Success.class);
        bean1 = gson.fromJson(defString, Document.class);

    }

    /***
     * 获取当前登录Id
     * @return
     */
    public String opId () {
        if (bean != null) {
            return bean.getData().getOpId();
        }
        return "";
    }

    /***
     * 获取opName(当前登陆用户)
     * @return
     */
    public String opName () {
        if (bean != null) {
            return bean.getData().getOpName();
        }

        return "";
    }

    /***
     * 获取accountMobile(当前登陆用户的手机号)
     * @return
     */
    public String accountMobile () {
        if (bean != null) {
            return bean.getData().getAccountMobile();
        }

        return "";
    }


    /***
     * 获取login(用户)
     * @return
     */
    public String login () {
        if (bean != null) {
            return bean.getData().getAccountLogin();
        }

        return "";
    }

    /***
     * 获取accountlink(用户头像)
     * @return
     */
    public String accountlink () {
        if (bean != null) {
            return bean.getData().getAccountlink();
        }

        return "";
    }


    /***
     * 获取dptName(机关Name)
     * @return
     */
    public String dptName () {
        if (bean != null) {
            return bean.getData().getAccountDeptName();
        }

        return "";
    }

    /***
     * 获取dptId(机关Id)
     * @return
     */
    public String dptId() {
        if (bean != null) {
            return bean.getData().getAccountDeptId();
        }

        return "";
    }

    /***
     * 获取部门Id
     * @return
     */
    public String sectorId() {
        if (bean != null) {
            return bean.getData().getAccountSectorId();
        }
        return "";
    }

    /***
     * 获取部门Name
     * @return
     */
    public String sectorName() {
        if (bean != null) {
            return bean.getData().getAccountSectorName();
        }
        return "";
    }

    /***
     * 获取accountBean
     * @return
     */
    public Logging_Success accountBean() {
        if (bean != null) {
            return bean;
        }
        return null;
    }

    /***
     * 获取公文拟制opState
     * @return
     */
    public String opState() {
        if (bean1 != null) {
            return bean1.getOpState();
        }
        return null;
    }

    /***
     * 获取公文拟制docStatus
     * @return
     */
    public String docStatus() {
        if (bean1 != null) {
            return bean1.getDocStatus();
        }
        return null;
    }
}
