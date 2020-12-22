package com.sx.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sx.helper.JsonHelper;
import com.sx.util.AjaxJsonResult;

/**
 *
 * <p>
 * Title: commonFilter,检查特殊字符，设计字符集
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: bksx
 * </p>
 *
 * @author ztw
 * @version 1.0
 */
public class CommonFilter extends HttpServlet implements Filter {
	private final Logger logger = LoggerFactory.getLogger(CommonFilter.class);
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private FilterConfig filterConfig;

	// private static String[]
	// filterStr={"'",";","and ","or ","insert ","select ","delete ","drop ","update "," count","* ","master","truncate ","declare "};
	// '|;|and|(|)|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare
	// private static String[]
	// filterStr={"|","&",";","$","%","@","'","\'","\"","<",">","(",")","+","\r","\n",",","\\","--","/*","*/","||","../","and ","or ","insert ","select ","delete ","drop ","update "," count","*"," master","truncate ","declare "};
	// private static String[]
	// filterStr={"|",";","$","%","@","'","\'","\"","<",">","(",")","+","\r","\n",",","\\","--","/*","*/","||","../","and ","or ","insert ","select ","delete ","drop ","update "," count","*"," master","truncate ","declare "};
	// private static String[]
	// filterStr={"|",";","$","%","@","'","\'","\"","<",">","(",")","+","\r","\n","\\","--","/*","*/","||","../","and ","or ","insert ","select ","delete ","drop ","update "," count","*"," master","truncate ","declare "};
	private static String[] filterStr = { "$", "%", "@", "'", "\'", "\"", "<",
			">", "+", "\r", "\n", "\\", "--", "/*", "*/", "../", "and ", "or ",
			"insert ", "select ", "delete ", "drop ", "update ", " count", "*",
			" master", "truncate ", "declare " };

	// Handle the passed-in FilterConfig
	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}

	// Process the request/response pair
	@SuppressWarnings("rawtypes")
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		// 将ServletRequest对象转换HttpServletRequest
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		// 将ServletRequest对象转换HttpServletResponse
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		// 设置字符编码GBK
		String encoding = filterConfig.getInitParameter("encoding");
		httpRequest.setCharacterEncoding(encoding);
		// 设置不读取IE缓存
		httpResponse.setHeader("Pragma", "No-Cache");
		httpResponse.setHeader("Cache-Control", "No-Cache");
		httpResponse.setDateHeader("Expires", 0);

		/************************* 根据业务需求选择严格检查或是简单检查 ****************************************/
		//
		// ============================================================================
		// 获得所有请求参数名（严格检查）
		// ============================================================================
		Enumeration params = httpRequest.getParameterNames();
		String queryStr = "";
		while (params.hasMoreElements()) {
			// 得到参数名
			String name = params.nextElement().toString();
			if (logger.isDebugEnabled()) {
				logger.debug("params name is {}", name);
			}
			queryStr = queryStr + name;
			// 得到参数对应值
			String[] value = httpRequest.getParameterValues(name);
			for (int i = 0; i < value.length; i++) {
				if (logger.isDebugEnabled()) {
					logger.debug("params value[{}] is {}", i, name);
				}
				if (value[i] != null) {
					if (value[i].indexOf("%") != -1) {
						try{
							queryStr = queryStr + URLDecoder.decode(value[i], "utf-8");
						}catch (Exception e){}
						continue;
					}
				}
				queryStr = queryStr + value[i];
			}
		}

		// 处理特殊字符
		//String queryStr = StringTools.toTrim(httpRequest.getQueryString());

		if (!queryStr.equals("")) {
			if (logger.isDebugEnabled()) {
				logger.debug("getQueryString is {}", queryStr);
			}
			// ============================================================================
			// 只检查url的参数时执行（简单检查）
			// ============================================================================
			if (queryStr.indexOf("%") != -1) {
				queryStr = URLDecoder.decode(queryStr, "utf-8");
				if (logger.isDebugEnabled()) {
					logger.debug("URLDecoder.decode1 getQueryString is {}",
							queryStr);
				}
				if (queryStr.indexOf("%") != -1) {
					try{
						queryStr = URLDecoder.decode(queryStr, "utf-8");
					}catch (Exception e){}
					if (logger.isDebugEnabled()) {
						logger.debug("URLDecoder.decode2 getQueryString is {}",
								queryStr);
					}
				}
			}
			// ============================================================================

			queryStr = queryStr.toLowerCase();
			for (int i = 0; i < filterStr.length; i++) {
				if (queryStr.indexOf(filterStr[i]) != -1) {
					logger.warn("getQueryString is {}, filterStr is {}",
							queryStr, filterStr[i]);
					// 可根据业务需要记录错误信息到数据库表
					httpRequest.getSession().setAttribute("sqlinmsg",
							filterStr[i]);

					PrintWriter pw = response.getWriter();
					// 可根据业务需要相应不同内容到页面
					AjaxJsonResult jsonResult = new AjaxJsonResult();
					jsonResult.setCodeAndMsg(400, "containerFilterStr");
					jsonResult.setReturnData(filterStr[i]);
					pw.write(JsonHelper.object2str(jsonResult));
					pw.flush();
					pw.close();
					//httpResponse.sendRedirect(AppConfig.getAPPCONTEXT()
					//		+ "/to-login");
					return;
				}
			}
		}
		// 调用后续过滤器
		filterChain.doFilter(httpRequest, httpResponse);
	}

	// Clean up resources
	public void destroy() {
	}
}