package com.sx.filter;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Session过滤器
 *
 */
public class SessionFilter implements Filter {

	/** 要检查的 session 的名称 */
	private String sessionKey;

	/** 需要排除（不拦截）的URL的正则表达式 */
	private Pattern excepUrlPattern;

	/** 检查不通过时，转发的URL */
	private String forwardUrl;

	/**
	 *
	 */
	public SessionFilter() {
		super();
	}

	public void init(FilterConfig cfg) throws ServletException {
		sessionKey = cfg.getInitParameter("sessionKey");
		String excepUrlRegex = cfg.getInitParameter("excepUrlRegex");
		if (!StringUtils.isBlank(excepUrlRegex)) {
			excepUrlPattern = Pattern.compile(excepUrlRegex);
		}
		forwardUrl = cfg.getInitParameter("forwardUrl");
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		// 如果 sessionKey 为空，则直接放行
		if (StringUtils.isBlank(sessionKey)) {
			chain.doFilter(req, res);
			return;
		}

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String servletPath = request.getServletPath();

		if (request.getServletPath() == null
				|| request.getServletPath().equals("/")
				|| request.getServletPath().equals("")) {
			chain.doFilter(req, res);
			return;
		}

		// 如果请求的路径与forwardUrl相同，或请求的路径是排除的URL时，则直接放行
		if (servletPath.equals(forwardUrl)
				|| excepUrlPattern.matcher(servletPath).matches()) {
			chain.doFilter(req, res);
			return;
		}

		Object sessionObj = request.getSession().getAttribute(sessionKey);
		// 如果Session为空，则跳转到指定页面
		if (sessionObj == null) {
			String contextPath = request.getContextPath();
			String redirect = servletPath + "?"
					+ StringUtils.defaultString(request.getQueryString());
			/*
			 * login.jsp 的 <form> 表单中新增一个隐藏表单域： <input type="hidden"
			 * name="redirect" value="${param.redirect }">
			 *
			 * LoginServlet.java 的 service 的方法中新增如下代码： String redirect =
			 * request.getParamter("redirect"); if(loginSuccess){ if(redirect ==
			 * null || redirect.length() == 0){ // 跳转到项目主页（home.jsp） }else{ //
			 * 跳转到登录前访问的页面（java.net.URLDecoder.decode(s, "UTF-8")） } }
			 */
			response.sendRedirect(contextPath
					+ StringUtils.defaultIfEmpty(forwardUrl, "/")
			);
			chain.doFilter(req, res);
		} else {
			chain.doFilter(req, res);
		}

	}

	public void destroy() {

	}

}
