<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="myup" scope="page" class="com.sx.utility.FileUpload" />
<jsp:useBean id="tempaffix" scope="page" class="com.sx.components.TempAffix" />
<%@ page import="com.sx.utility.StringTools"%>
<%
String id="";
String datetime="";
String path="";
String flag=null;
if (request.getContentType()!=null) {
	myup.setRequest(request);
	flag=myup.getParameter("flag");//判断是否为自提交。
	id=myup.getParameter("id");//16位人员标识
	datetime=myup.getParameter("datetime");//14位时间标识
	path=myup.getParameter("path");//上传附件路径
}else{
	id=request.getParameter("id");//16位人员标识
	datetime=request.getParameter("datetime");//14位时间标识
	path=request.getParameter("path");//上传附件路径
}
String operate_flag="";
operate_flag=myup.getParameter("operate_flag").trim();
if (operate_flag==null)
	operate_flag="";
if (flag!=null){   //-----------------------//插入附件开始
	if (operate_flag.equals("AddFile")) {
		String maxnum=tempaffix.getMaxNum(id,datetime);
		if(maxnum==null){
			System.out.println("读取最大号失败！");
		}
		String fsysname=id+datetime+maxnum;
		String fsysname1=path+"/"+id+datetime+maxnum;
		String fjcliname=myup.upload(fsysname1,"uploadFileName");
		String temp[]=new String[7];
		temp[0]=id;
		temp[1]=datetime;
		temp[2]=maxnum;
		temp[3]=fjcliname;
		temp[4]=fsysname;
		temp[5]="1";
		temp[6]="";
		if (!StringTools.toTrim(fjcliname).equals("")){
			if(!tempaffix.insert(temp)){
				System.out.println("插附件失败！");
			}
		}
	}//---------------------------------------插入附件结束

	if (operate_flag.equals("RemoveFile")){  //--删除一个附件开始
		String sc_maxxh=myup.getParameter("selectedID");
		String UpdTemp[]=new String[4];
		UpdTemp[0]=id;
		UpdTemp[1]=datetime;
		UpdTemp[2]=sc_maxxh.substring(30,34);
		UpdTemp[3]="2";
		if(!tempaffix.update(UpdTemp)){
			System.out.println("删除附件失败！");
		}
	}//end if flag 
  
}

	//---------------------------------------读取附件开始
	String SelTemp[]=new String[3];
	SelTemp[0]=id;
	SelTemp[1]=datetime;
	SelTemp[2]="1";
	int rows=0;
	String ResTemp[][]=tempaffix.select(SelTemp);
	if(ResTemp==null){
	//	System.out.println("读取附件失败！");
	}else{
		rows=ResTemp.length;
	}
	///////////
	String width_iframe= request.getParameter("width_iframe");//iframe框的宽度
	if (width_iframe==null)
	{
		width_iframe="";
	}
	width_iframe=width_iframe.trim();
	if (width_iframe.equals(""))
	{
		width_iframe="400";
	}
	//////////
%>
<html>
<head>
<title>上传附件</title>
</head>
<link href="css/common.css" rel="stylesheet" type="text/css">
<body  bgcolor="f6f6f6"  text="#000000" topmargin="0"  leftmargin=0>
<form name="tj1" action="" method="post" enctype="multipart/form-data">
      
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
          
      <td colspan="2" width="100%" > 
        <select name="selectedID" size="3" style="width:<%=width_iframe%>px" multiple  ondblclick="chakan()">
		 <% for (int i=0;i<rows;i++){%>
			<option value="<%=ResTemp[i][4]%>"><%=ResTemp[i][3]%></option>
		<%}%>
            </select>
          </td>
        </tr>
        <tr> 
          
      <td colspan="2"  height=35> 
        <input type="file"  size=27 name="uploadFileName" onkeydown="fj(this)">
		<input type="button" name="23232" value="上传" class=button onclick="submitForm('AddFile'); return false;">
		<input type="button" name="23232" value="删除" class=button onclick="submitForm('RemoveFile'); return false;">
		<input type="button" name="23232" value="查看" class=button onclick="chakan()">
      </td>
        </tr>
	</table>
	<input type="hidden" name="operate_flag" value="Load">
	<input type="hidden" name="id" value="<%=id%>">
	<input type="hidden" name="datetime" value="<%=datetime%>">
	<input type="hidden" name="path" value="<%=path%>">
	<input type="hidden" name="flag" value="1">
</form>
</body>
<SCRIPT LANGUAGE="JavaScript">
<!--//-------------------------------------------上传附件开始
function fj(str)//校验附件框即uploadFileName域的内容不能自己随便输入
{
	
	if(window.event.keyCode==8||window.event.keyCode==46)
	{ 
	str.select();
	}
	else
	{
	str.blur();
	}
}

function submitForm(act)  
{
	var uploadFileNames=document.tj1.uploadFileName;
	var selectedIDss=document.tj1.selectedID;

	if (act == 'AddFile') 
	{

		if (uploadFileNames.value == '')
		{
			alert('请选择要上传的文件。');
			uploadFileNames.select();
			return false;
		}
		var filename = (uploadFileNames.value);

		//======================================
		if (filename!="") 
		{
			filename.toLowerCase();
			if (filename.indexOf('.')==-1)
			{
				alert("您选择的文件名没有扩展名！");
				uploadFileNames.focus();
				return false;
			 }
			  l = filename.length - 3 ;
			  tmp = filename.substr(l,3) ;
			  tmp = tmp.toLowerCase() ;
			  if(!(tmp=='doc'||tmp=='txt'||tmp=='xls'||tmp=='pdf'||tmp=='jpg'||tmp=='gif'||tmp=='zip'||tmp=='wdl'||tmp=='ppt'||tmp=='rar'))
			 {
					alert("你只能上传扩展名为doc、txt、xls、pdf、wdl、ppt、jpg、gif、zip、rar的文件！");
					uploadFileNames.focus();
					return false;
			 }			
		}
		//======================================

	} 
	else 
	{
		uploadFileNames.disabled = false;
	}

	if (act == 'RemoveFile') 
	{
		if (selectedIDss.value == '') 
		{
			alert('请选择要删除的附件。');
			return false;
		}
////////////////////////////////////////////////////////////////
	var j=0;
	var list_num1=selectedIDss.options.length;	   
	//list_value1=new Array(list_num1);
	//list_text1=new Array(list_num1);
	var selected_value1="";
	var selected_text1="";
   //把selectedID的当前内容放入数组
   /*for (var i=0; i < selectedIDss.options.length; ++i)
	{<a name=""></a>"></a>"></a>"></a>"></a>"></a><a <a <a <a <a <a name="name="name="name="name="name=""></a>
			list_text1[i]=selectedIDss.options[i].text;
			list_value1[i]=selectedIDss.options[i].value;
	}
   */
   //把selected的options存到数组
	if(selectedIDss.selectedIndex != -1)
	{
		for (var i=0; i < selectedIDss.options.length; ++i)
		{

		   if (selectedIDss.options[i].selected)
		   {
				j++;
				selected_value1=selectedIDss.options[i].value;
				selected_text1=selectedIDss.options[i].text;
			}
		}
	}	
	if (j!=1)
	{
		alert("您一次只能删除一个文件！");
		return false;
	}
	
	/*//
	for (i = 0; i < selectedIDss.options.length; i++)
	{
		selectedIDss.options[i].selected=true;
	}
	//*/
   uploadFileNames.disabled = false;
//////////////////////////////////////////////////////////
}
	document.tj1.operate_flag.value = act;
	document.tj1.action="tempaffix.jsp";
	document.tj1.submit();
}
//-->--------------------------------------------上传附件结束

</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
///////------------------------------------查看文件内容
function chakan()
{
	var selectedIDs=document.tj1.selectedID;

	if (selectedIDs.value == '') 
	{
		alert('请选择要查看的附件！');
		return;
	}
	var j=0;
	var list_num1=selectedIDs.options.length;	   
	var selected_value1="";
	var selected_text1="";
   //把selected的options存到数组
	if(selectedIDs.selectedIndex != -1)
	{
		for (var i=0; i < selectedIDs.options.length; ++i)
		{

		   if (selectedIDs.options[i].selected)
		   {
				j++;
				selected_text1=selectedIDs.options[i].text;
				selected_value1=selectedIDs.options[i].value;
				//selectedIDs.options[i].selected=false;
			}
		}
	}
	if (j!=1)
	{
		alert("您一次只能查看一个文件！");
		return ;
	}	
	window.open("./openfile.jsp?fsername=<%=path%>/"+selected_value1+"&fcliname="+selected_text1,"","");
}
//-->
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
  function tui()
  {    
	 self.close();
  }
//-->
</SCRIPT>
</html>