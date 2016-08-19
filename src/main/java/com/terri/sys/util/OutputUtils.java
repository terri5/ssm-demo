package com.terri.sys.util;


import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;



public class OutputUtils {
	public static final Integer CODE_PARAM_NULL = -1;//参数为空
	public static final Integer CODE_QUERY_NULL = -2;//查找对象为空
	public static final Integer CODE_QUERY_EXCEPTION = -3;//查找发生异常
	public static final Integer CODE_NOT_MATCh = -4;//比较信息不匹配
	public static final Integer CODE_DB_REPEART = -5;//数据库中已重复
	/**
	 * 分页返回格式
	 * @param page
	 * @param msg
	 * @param data
	 * @return
	 */
	public final static String handlePage(PagedDataVO page, String msg, String data) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"code\":\"0\",");
		sb.append("\"msg\":\""+msg+"\",");
		sb.append("\"time\":\""+System.currentTimeMillis()+"\",");
		sb.append("\"page\":{");
		sb.append("\"curr_page\":\""+page.getPageNo()+"\",");
		sb.append("\"page_size\":\""+page.getRowSize()+"\",");
		sb.append("\"total\":\""+page.getTotalRows()+"\",");
		sb.append("\"data\":"+data);
		sb.append("}");
		sb.append("}");
		return sb.toString();
		
	}
	
	
	
	/**
	 * 普通返回格式
	 * @param msg
	 * @param data
	 * @return
	 */

	private static String handleSuccess(String msg, String data) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"code\":\"0\",");
		sb.append("\"msg\":\""+msg+"\",");
		sb.append("\"time\":\""+System.currentTimeMillis()+"\",");
		if(StringUtils.isEmpty(data)){
			sb.append("\"data\":\"\"");
		}else{
			sb.append("\"data\":"+data);
		}
		sb.append("}");
		return sb.toString();
		
	}
	/**
	 * 获取数据失败
	 * @param code
	 * @param msg
	 * @return
	 */
	public static String handleFail(Integer code, String msg) {
		JSONObject obj = AppJsonUtils.getJSONObject("fail");
		obj.element(AppUtils.CODE, "\""+code+"\"");
		obj.element(AppUtils.MSG, msg);
		obj.element(AppUtils.TIME, "\""+System.currentTimeMillis()+"\"");
		return obj.toString();
	}

}
