package lanwei.com.gesture_application.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences的工具类
 * 黑马程序员98期
 * 2016年12月23日 上午11:18:38
 */
public class SharedPreferencesUtil {

	private static SharedPreferences sp;

	/**
	 * 保存boolean值的操作
	 * 
	 * 2016年12月23日 上午11:19:06
	 */
	public static void saveBoolean(Context context, String key, boolean value){
		//name : 保存信息的xml文件的名称
		//mode : 读取模式（权限）
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().putBoolean(key, value).commit();
	}
	
	/**
	 * 获取boolean值的操作
	 * 
	 * 2016年12月23日 上午11:22:29
	 */
	public static boolean getBoolean(Context context, String key, boolean defValue){
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getBoolean(key, defValue);
	}
	
	/**
	 * 保存String值的操作
	 * 
	 * 2016年12月23日 上午11:19:06
	 */
	public static void saveString(Context context, String key, String value){
		//name : 保存信息的xml文件的名称
		//mode : 读取模式（权限）
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().putString(key, value).commit();
	}
	
	/**
	 * 获取String值的操作
	 * 
	 * 2016年12月23日 上午11:22:29
	 */
	public static String getString(Context context, String key, String defValue){
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getString(key, defValue);
	}





	/**
	 * 保存int值的操作
	 *
	 * 2016年12月23日 上午11:19:06
	 */
	public static void savaInt(Context context, String key, int value){
		//name : 保存信息的xml文件的名称
		//mode : 读取模式（权限）
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().putInt(key, value).commit();
	}

	/**
	 * 获取int值的操作
	 *
	 * 2016年12月23日 上午11:22:29
	 */
	public static int getInt(Context context, String key, int defValue){
		if (sp == null) {
			sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getInt(key, defValue);
	}










}
