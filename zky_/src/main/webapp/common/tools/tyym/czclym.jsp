<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.sx.exception.*"%>
<%@ page import="com.sx.rbac.RbacOperation"%>
<%@ page import="com.sx.conf.SessionConfig" %>
<%@ page import="com.sx.conf.AppConfig" %>
<%@ page import="com.sx.helper.StringHelper" %>
<%@ page import="com.sx.helper.DateHelper" %>
<%@ page import="com.sx.helper.ExceptionMessage" %>

<%
	//===================================================================
	//检查session中的值
	//===================================================================
	SessionConfig sessionConf = (SessionConfig) request.getSession().getAttribute("sessionConf");
	if (sessionConf==null){
	out.println("<script language='javascript'>");
	out.println("window.navigate('"+AppConfig.getAPPCONTEXT()+"/frames/return.jsp');");
	out.println("</script>");
	return ;
	}
	String cert = StringHelper.replace(sessionConf.getCert(),"\r\n","");/////????
	//================================================================
	//成功失败处理
	//================================================================
	Object temp=request.getSession().getAttribute("errorcode");
	if(temp!=null){
		AppErrorCode errorcode=(AppErrorCode)temp;
		if (errorcode!=null){
	if(4!=errorcode.getErrortype()){
		if(errorcode.equals(AppErrorCode.OPERSUC)){//操作成功
			out.println("<script language='javascript'>");
			//out.println("window.open('"+AppConfig.getAPPCONTEXT()+"/common/czts/czcg.html','操作成功','width=300 height=200 top=200 left=200 scrollbars=no')");
			out.println("alert('操作成功！')");
			out.println("</script>");
		}
		if(errorcode.equals(AppErrorCode.OPERERR)){//操作失败
			out.println("<script language='javascript'>");
			//out.println("window.open('"+AppConfig.getAPPCONTEXT()+"/common/czts/czsb.html','操作失败','width=300 height=200 top=200 left=200 scrollbars=no')");
			out.println("alert('操作失败！')");
			out.println("</script>");
		}
		if(!errorcode.equals(AppErrorCode.OPERERR) && !errorcode.equals(AppErrorCode.OPERSUC)){//以上情况都不是
			out.println("<script language=\"javascript\"> alert(\""+errorcode.getMessage()+"\") </script>");
		}
	}
	else{
		ExceptionMessage excepMsg = new ExceptionMessage();
		out.println("<script language='javascript'>");
		out.println("alert('操作失败"+excepMsg.getExceptionCode(errorcode.getMessage())+"')");
		out.println("</script>");
	}
	request.getSession().removeAttribute("errorcode");
		}
	}
	//================================================================
	//权限校验
	//================================================================

	String resid = request.getParameter("resid");
	if (!StringHelper.toTrim(resid).equals("")){
		String opid = "0000000001";
		String czyid = sessionConf.getCzyid();
		String sessionid = sessionConf.getSessionid();
		if(!RbacOperation.checkUser(sessionid, czyid, opid,resid)){
	out.println("<script language='javascript'>");
	out.println("alert('您没有访问此功能的权限！');");
	out.println("window.navigate('"+AppConfig.getAPPCONTEXT()+"/xtsyAction.do?method=ywsy');");
	out.println("</script>");
	return ;
		}
	}
%>
