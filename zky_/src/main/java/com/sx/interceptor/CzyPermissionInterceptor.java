package com.sx.interceptor;

import com.sx.conf.SessionConfig;
import com.sx.helper.JsonHelper;
import com.sx.helper.StringHelper;
import com.sx.util.AjaxJsonResult;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Author: zyt
 * @Date: 2019/5/29
 * @Description: 操作员权限拦截器
 */
    public class CzyPermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        //获取session存储的操作员信息。

        SessionConfig sessionConfig = (SessionConfig) request.getSession().getAttribute("sessionConfig");
        if (sessionConfig != null) {
            //获取操作员的功能集合路径
            String gnlj = sessionConfig.getGnlj();
            if (StringHelper.isNotEmpty(gnlj)) {
                //获取当前请求路径
                String requestPath = request.getRequestURI().substring(1);
                requestPath = requestPath.substring(requestPath.indexOf("/"),requestPath.lastIndexOf("/"));
                //如果请求路径不在功能集合路径中

                if (gnlj.indexOf(requestPath) != -1) {
                    flag = true;
                }
            }
        }
        //如果被拦截

        if (!flag) {
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter pw = response.getWriter();
            AjaxJsonResult jsonResult = new AjaxJsonResult();
            //设置无此功能权限的返回值和信息
            jsonResult.setCodeAndMsg(-4, "该用户无此功能权限");
            pw.write(JsonHelper.object2str(jsonResult));
            pw.flush();
            pw.close();
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
