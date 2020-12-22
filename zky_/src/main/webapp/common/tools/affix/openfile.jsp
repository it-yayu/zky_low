
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.sx.conf.SysConfig" %>
<%@ page import="com.sx.conf.UploadConfig" %>
<%@ page import="java.io.*" %>

<%
String Dfilepath = SysConfig.getROOT_REAL_PATH()+UploadConfig.getFILE_PATH()+"/";
//System.out.println(Dfilepath);
//request.setCharacterEncoding("GBK");
String fsername=request.getParameter("fsername").trim();
String fcliname=request.getParameter("fcliname").trim();

if(fsername.indexOf("../")!=-1||fsername.indexOf("..")!=-1||fsername.toLowerCase().indexOf(".jsp")!=-1||fsername.toLowerCase().indexOf(".xml")!=-1||fsername.toLowerCase().indexOf(".htm")!=-1||fsername.toLowerCase().indexOf(".html")!=-1||fsername.toLowerCase().indexOf(".css")!=-1||fsername.toLowerCase().indexOf(".js")!=-1){
    out.println("您访问的文件出现错误，请与系统管理员联系！");
    return;
 }

//取文件的扩展名                                
int len=fcliname.length();
int pos=fcliname.lastIndexOf(".");
if (pos==-1)
{
	out.println("没有正文！");
	return;
}
String ext=fcliname.substring(pos,len);
fsername=Dfilepath+fsername+ext;
//out.println(fsername);
//out.println(fcliname);

File f = new File(fsername);
FileInputStream in = new FileInputStream(f);

response.setHeader("Content-Disposition", "attachment;filename="+fcliname+"");

response.setContentLength((int)f.length());
//fetch the file
int filelen = (int)f.length();
if(filelen != 0)  {
  byte[] buf = new byte[4096];
  ServletOutputStream op = response.getOutputStream();
  while ((in != null) && ((filelen = in.read(buf)) != -1))  {
  op.write(buf,0, filelen);			}
  in.close();
  op.flush();
  op.close();
}
%>

