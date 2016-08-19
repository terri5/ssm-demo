package com.terri.sys.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import net.sf.json.JSONObject;


public class SecurityInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String param=request.getParameter("param");
		String result = null;
		if(StringUtils.isEmpty(param)){
			result = OutputUtils.handleFail(OutputUtils.CODE_PARAM_NULL, "参数为空");
			RequestProcessor.outJson(response, result);
			return false;
		}else{			
			
			JSONObject json = JSONObject.fromObject(param);
			Map<String, Object> map;
			try {
				map = AppUtils.ValidUser(json);
				if(map.get(AppUtils.MSG) != null){		//如果有错误信息，说明登录失败
					result = (String)map.get(AppUtils.MSG);
					RequestProcessor.outJson(response, result);
					return false;
				}	
			}catch (ApplicationException e) {
					e.printStackTrace();
					result = OutputUtils.handleFail(OutputUtils.CODE_QUERY_EXCEPTION,e.getMessage());
					RequestProcessor.outJson(response, result);
					return false;
				}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
