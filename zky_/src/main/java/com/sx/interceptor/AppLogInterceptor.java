package com.sx.interceptor;

import com.sx.conf.SessionConfig;
import com.sx.helper.DateHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Enumeration;

/**
 * @author windy
 *
 */
@SuppressWarnings("all")
@Component
public class AppLogInterceptor implements HandlerInterceptor {
	private static final Logger logger = LoggerFactory.getLogger(AppLogInterceptor.class);
	private String czsj = ""; // 操作时间
	private long startTime;// 开始时间
	@Autowired
	private AppLogDAO dao = null;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		startTime = System.currentTimeMillis();
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception e)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			String czlx = handler.toString(); // 操作类型
			String czff = method.getName(); // 操作方法
			String czgjzmc = ""; // 操作关键字名称
			StringBuffer czgjz = new StringBuffer(""); // 操作关键字
			String cznr = ""; // 操作内容
			String czybmid = ""; // 操作员部门id
			String czyid = ""; // 操作员id
			String czjsjip = this.getRemoteHost(req); // 操作计算机ip
			String hhxh = ""; // 会话序号
			String appsid = ""; // 应用sessionid
			String dlpj = ""; // 登录票据

			String czyxm = ""; // 用户名

			String xzqhdm = ""; // 用户所属区县
			String ywxh = req.getRequestURI(); // 业务序号
			String zyid = ""; // 操作模块（资源id）

			String czjssj = "";

			long yxsj = 0;
			if (startTime != 0) {
				yxsj = System.currentTimeMillis() - startTime;
			}
			czjssj = DateHelper.long2str17(System.currentTimeMillis());
			if ("".equals(czsj)) {
				czsj = czjssj;
			}
			// 处理参数
			StringBuffer paramStr = new StringBuffer("{");
			Enumeration params = req.getParameterNames();
			int n = 0;
			while (params.hasMoreElements()) {
				// 得到参数名
				if (n != 0) {
					paramStr.append(",");
				}
				n++;

				String name = params.nextElement().toString();
				paramStr.append("\"" + name + "\"");
				paramStr.append(":");
				// 得到参数对应值
				String[] value = req.getParameterValues(name);

				if (value == null) {
					paramStr.append("\"\"");
					continue;
				}

				if (value.length > 1) {
					paramStr.append("[");
				}
				for (int i = 0; i < value.length; i++) {
					if (i != 0) {
						paramStr.append(",");
					}
					if (value[i] != null) {
						if (value[i].indexOf("%") != -1) {
							paramStr.append(URLDecoder.decode("\"" + value[i], "utf-8") + "\"");
							continue;
						}
					}
					paramStr.append("\"" + value[i] + "\"");

				}
				if (value.length > 1) {
					paramStr.append("]");
				}

			}

			paramStr.append("}");

			cznr = paramStr.toString();
			logger.debug("request Parameters:{}",cznr);

			// 处理操作员信息
			SessionConfig sessionConf = (SessionConfig) req.getSession().getAttribute("sessionConf");
			if (sessionConf != null) {
				czybmid = sessionConf.getDwid();
				czyid = sessionConf.getCzyid();
				czjsjip = req.getLocalAddr();
				hhxh = sessionConf.getSessionid();
				appsid = req.getRequestedSessionId();
				dlpj = sessionConf.getTicket();
				czyxm = sessionConf.getCzyxm();
				xzqhdm = sessionConf.getXzqhdm();
			}
			zyid = req.getParameter("_resid");

			dao.write(czlx, czff, czgjzmc, czgjz.toString(), cznr, czybmid, czyid, czsj, czjsjip, hhxh, appsid, dlpj,
					czyxm, xzqhdm, ywxh, zyid, czjssj, yxsj);

		}
	}
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object handler, ModelAndView mv)
			throws Exception {
	}

	private String getRemoteHost(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
			ip = request.getRemoteAddr();
		}
		return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}


}
