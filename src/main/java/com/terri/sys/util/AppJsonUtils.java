package com.terri.sys.util;

import java.io.InputStream;

import net.sf.json.JSONObject;



public class AppJsonUtils {
	public static final String var = "app.json";
	private static String sets;
	/**
	 * 初始化json文件
	 */
	static{
		sets = ReadFile(var);
	}
	
	/**
	 * 根据key获取JSONObject
	 * @param key
	 * @return
	 */
	public static JSONObject getJSONObject(String key){
		return JSONObject.fromObject(sets).getJSONObject(key);
	}
	
	/**
	 * 读取路径下的文件
	 * @param path
	 * @return
	 */
	private static  String ReadFile(String path) {
		InputStream in = null;  
		in = AppJsonUtils.class.getClassLoader().getResourceAsStream(path);  
		return FileHelper.readFile(in, "utf-8");
	}
}
