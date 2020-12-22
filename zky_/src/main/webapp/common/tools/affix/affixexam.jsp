<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
	String rbs = "0000000000000000";//登录人的16位ID
	String datetime = "20060615010101";//当前时间
	String path = "path";//要上传文件保存的路径


%>

<iframe width='400' id="affixiframe1" src="tempaffix.jsp?id=<%=rbs%>&datetime=<%=datetime%>&path=<%=path%>" frameborder=0  noresize marginheight=0 marginwidth=0 scrolling=no height="85"></iframe>