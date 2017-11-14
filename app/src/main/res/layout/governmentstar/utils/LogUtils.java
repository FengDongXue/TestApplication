package com.lanwei.governmentstar.utils;

import android.util.Log;

public class LogUtils {
	public final static boolean DEBUG = true;
	public static void sys(String s){
		System.out.println(s);
	}
	public static void i(String tag, String msg) {
		if(DEBUG) {
			Log.i(tag, msg);
		}
	}
	public static void i(String tag, Object msg) {
		if(DEBUG) {
			Log.i(tag, msg.toString());
		}
	}
	
	public static void w(String tag, String msg) {
		if(DEBUG) {
			Log.w(tag, msg);
		}
	}

	public static void w(String tag, Object msg) {
		if(DEBUG) {
			Log.w(tag, msg.toString());
		}
	}
	
	public static void e(String tag, String msg) {
		if(DEBUG) {
			Log.e(tag, msg);
		}
	}

	public static void e(String tag, Object msg) {
		if(DEBUG) {
			Log.e(tag, msg.toString());
		}
	}
	
	public static void d(String tag, String msg) {
		if(DEBUG) {
			Log.d(tag, msg);
		}
	}
	
	public static void d(String tag, Object msg) {
		if(DEBUG) {
			Log.d(tag, msg.toString());
		}
	}
	
	public static void v(String tag, String msg) {
		if(DEBUG) {
			Log.v(tag, msg);
		}
	}
	
	public static void v(String tag, Object msg) {
		if(DEBUG) {
			Log.v(tag, msg.toString());
		}
	}
	public static void Systemm(String str){
		if(DEBUG){
			System.out.println(str);
		}
	}
}
