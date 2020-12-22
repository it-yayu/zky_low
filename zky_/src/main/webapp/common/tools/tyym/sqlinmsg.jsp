
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.sx.conf.AppConfig" %>

<%
	Object sqlinmsg=request.getSession().getAttribute("sqlinmsg");
	request.getSession().removeAttribute("sqlinmsg");
%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>系统页面</title>
<style type="text/css">
/*全局页面样式*/
body {
    margin:0 0 0 0;
	font-size:9pt;
	font-family:"宋体",arial; 
	/*下面七行是滚动条的样式，IE5无效*/
	scrollbar-arrow-color:#000000;
	scrollbar-track-color:#EEEEEE;
	scrollbar-highlight-color:#D4D0C8;
	scrollbar-face-color:#D4D0C8;
	scrollbar-shadow-color:#D4D0C8;
	scrollbar-3dlight-color:#FFFFFF;
	scrollbar-darkshadow-color:#808080;
	overflow:auto;
	background-color:#FFFFFF;
	/*background-image:url(bg.gif);*/
}
.mybg {
	width:290px;
	height:438px;
	background-color:#FFFFFF;
	background-image:url(images/exception.jpg);
	background-repeat:no-repeat;
	background-position:center;
}
.mybg2 {
	width:360px;
	height:438px;
	background-color:#FFFFFF;
	background-image:url(images/exception1.jpg);
	background-repeat:no-repeat;
	background-position:center;
}
.zt1{
	font:bold 18pt "微软雅黑","宋体",arial;
	color:#3d9be3;
	padding:3px 0px 0px 5px;
}
.zt2{
	font:bold 14pt "微软雅黑","宋体",arial;
	color:#a8a7a7;
	padding:3px 0px 0px 5px;
}

</style>

<body>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="margin:0 auto;">
  <tr>
    <td valign="bottom" class="mybg2" style="padding:0 0 100px 0;"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="zt2" align="center">您输入的内容包含【<%=sqlinmsg%>】特殊字符!!!</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </table></td>
 
  </tr>
</table>
</body>
</html>
