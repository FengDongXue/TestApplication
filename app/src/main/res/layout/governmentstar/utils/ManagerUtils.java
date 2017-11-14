package com.lanwei.governmentstar.utils;

import android.content.Context;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2016/12/27 0027.
 */
public class ManagerUtils {
    public static void showToast(Context context, String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
    public static boolean isNull(String str){//不为空返回true
        if(str==null||str.equals("")){
            return false;
        }else{
            return true;
        }
    }
    /**
     * 判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumber(String mobiles) {   //是否是全数字
        Pattern p = Pattern
                .compile("^\\d+$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    /**
     * 描述：是否只是字母和数字和下划线.
     *
     * @param str 指定的字符串
     * @return 是否只是字母和数字:是为true，否则false
     */
    public static Boolean isNumberLetter(String str) {
        Boolean isNoLetter = false;
        String expr = "^[A-Za-z0-9_]+$";
        if (str.matches(expr)) {
            isNoLetter = true;
        }
        return isNoLetter;
    }
    /**
     * 描述：是否汉子，字母和数字和下划线.
     *
     * @param str 指定的字符串
     * @return 是否只是字母和数字:是为true，否则false
     */
    public static Boolean isHanZi(String str) {
        Boolean isNoLetter = false;
        String expr = "^[A-Za-z0-9_\u4E00-\u9FA5]+$";
        if (str.matches(expr)) {
            isNoLetter = true;
        }
        return isNoLetter;
    }
    public static boolean isPassWord(String mobiles) {   //密码需要是数字和字母组合
        Pattern p = Pattern
                .compile("^(?!\\D+$)(?![^a-z]+$)[a-zA-Z\\d]{8,16}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    public static boolean isPassWord2(String mobiles) {   //不包含其他字符
        Pattern p = Pattern
                .compile("^([\u4E00-\u9FA5]|\\w|\\s|[！~@#￥%……&*（）？+——~!@#$%^&*()_+?<>,.，。;；''\"\"“”])*$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
    /*
   * 手机正则
   * */
    public static final boolean isMobileNO (String data) {
        Pattern pattern = Pattern.compile("^(1[3578][0-9])\\d{8}$");
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }
    /*
   * 邮箱正则
   * */
    public static final boolean isMailNO(String data) {
        String regEx="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(data);
        return m.matches();
    }

    public static long StringToData(String pattern,String str){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        long millionSeconds;
        try {
            millionSeconds = sdf.parse(str).getTime();
            return  millionSeconds/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  -1;
    }
}
