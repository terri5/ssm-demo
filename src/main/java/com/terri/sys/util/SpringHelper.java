package com.terri.sys.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SpringHelper
{

	private static ServletContext servletContext = null;

	/**
	 * 将<code>ServletContext</code>绑定在<code>SpringHelper</code>当中.
	 * 
	 * @param servletContext
	 */
	public static void bindSessionContext(ServletContext servletContext)
	{
		if (SpringHelper.servletContext == null)
		{
			SpringHelper.servletContext = servletContext;
		}
	}

	/**
	 * 得到applicationcontext.xml,applicationcontext-hibernate.xml当中定义的的bean实体.
	 * 
	 * @param beanName
	 * @return object
	 */
	public static Object getBean(String beanName)
	{
		WebApplicationContext context = null;
		if (servletContext != null)
			context = WebApplicationContextUtils
					.getRequiredWebApplicationContext(servletContext);
		if (context != null)
			return context.getBean(beanName);
		else
			return null;
	}

	/**
	 * 得到applicationcontext.xml,application-hibernate.xml当中定义的bean实体.
	 * 
	 * @param beanName
	 * @param request
	 * @return Object
	 * @deprecated
	 */
	public static Object getBean(String beanName, HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		ServletContext servletContext = session.getServletContext();
		WebApplicationContext context = (WebApplicationContext) servletContext
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		return context.getBean(beanName);
	}

	/**
	 * 得到applicationcontext.xml,application-hibernate.xml当中定义的bean实体.
	 * 
	 * @param beanName
	 * @param servletContext
	 * @return Object
	 * @deprecated
	 */
	public static Object getBean(String beanName, ServletContext servletContext)
	{
		WebApplicationContext context = (WebApplicationContext) servletContext
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		return context.getBean(beanName);
	}

	public static ServletContext getServletContext()
	{
		return servletContext;
	}

	public static void debugRequest(HttpServletRequest request,
			ServletContext servletContext)
	{
		System.out.println("Protocol: " + request.getProtocol() + "<br>");
		System.out.println("Scheme: " + request.getScheme() + "<br>");
		System.out.println("Server Name: " + request.getServerName() + "<br>");
		System.out.println("Server Port: " + request.getServerPort() + "<br>");
		System.out.println("Protocol: " + request.getProtocol() + "<br>");
		System.out.println("Server Info: " + servletContext.getServerInfo()
				+ "<br>");
		System.out.println("Remote Addr: " + request.getRemoteAddr() + "<br>");
		System.out.println("Remote Host: " + request.getRemoteHost() + "<br>");
		System.out.println("Character Encoding: "
				+ request.getCharacterEncoding() + "<br>");
		System.out.println("Content Length: " + request.getContentLength()
				+ "<br>");
		System.out
				.println("Content Type: " + request.getContentType() + "<br>");
		System.out.println("Auth Type: " + request.getAuthType() + "<br>");
		System.out.println("HTTP Method: " + request.getMethod() + "<br>");
		System.out.println("Path Info: " + request.getPathInfo() + "<br>");
		System.out.println("Path Trans: " + request.getPathTranslated()
				+ "<br>");
		System.out
				.println("Query String: " + request.getQueryString() + "<br>");
		System.out.println("Remote User: " + request.getRemoteUser() + "<br>");
		System.out.println("Session Id: " + request.getRequestedSessionId()
				+ "<br>");
		System.out.println("Request URI: " + request.getRequestURI() + "<br>");
		System.out
				.println("Servlet Path: " + request.getServletPath() + "<br>");
		System.out.println("Accept: " + request.getHeader("Accept") + "<br>");
		System.out.println("Host: " + request.getHeader("Host") + "<br>");
		System.out
				.println("Referer : " + request.getHeader("Referer") + "<br>");
		System.out.println("Accept-Language : "
				+ request.getHeader("Accept-Language") + "<br>");
		System.out.println("Accept-Encoding : "
				+ request.getHeader("Accept-Encoding") + "<br>");
		System.out.println("User-Agent : " + request.getHeader("User-Agent")
				+ "<br>");
		System.out.println("Connection : " + request.getHeader("Connection")
				+ "<br>");
		System.out.println("Cookie : " + request.getHeader("Cookie") + "<br>");

	}

}
