package com.sx.exception;

import com.sx.helper.JsonHelper;
import com.sx.util.AjaxJsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


@Component
public class ExceptionHandler implements HandlerExceptionResolver {
	private static final Logger logger = LoggerFactory
			.getLogger(ExceptionHandler.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.debug("系统出现异常，资源:{},异常：{}",request.getRequestURI(),ex.toString());
        String s = request.getHeader("x-requested-with"); //不为空ajax请求
		Map<String, Object> model = new HashMap<String, Object>(); 
        if(null!=s){//处理ajax请求
        	AjaxJsonResult ajaxJsonResult = new AjaxJsonResult(AjaxJsonResult.CODE_SYSEXCEP);
        	response.setContentType("text/html;charset=utf-8");
			String res = JsonHelper.object2str(ajaxJsonResult);
			PrintWriter pw;
			try {
				pw = response.getWriter();
				pw.write(res);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
        }else{//处理普通请求
            return new ModelAndView("common/exceptionpage", model);  
        }
       
	}

}
