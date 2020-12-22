<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.sx.conf.SxAppConfig"%>
<%@ page import="com.sx.conf.SessionConfig" %>
<%@ page import="com.sx.conf.AppConfig" %>
<%@ taglib uri= '/WEB-INF/tld/menutag.tld' prefix='menutag'%>
<%
	//===================================================================
	//检查session中的值
	//===================================================================
	SessionConfig sessionConf = (SessionConfig) request.getSession().getAttribute(
			"sessionConf");
	if (sessionConf == null) {
		response.sendRedirect(AppConfig.getAPPCONTEXT() + "/frames/return.jsp");
		return;
	}
	String jmfg = sessionConf.getJmfg();
%>
<html>
<head>
    <title>ECharts</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="<%=jmfg%>/css/common.css">
    <link rel="stylesheet" href="<%=AppConfig.getAPPCONTEXT()%>/common/js/bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="<%=AppConfig.getAPPCONTEXT()%>/common/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="<%=AppConfig.getAPPCONTEXT()%>/common/js/bootstrap/js/bootstrap.min.js"></script>
    <script src="<%=AppConfig.getAPPCONTEXT()%>/common/js/jquery/echarts/www2/js/echarts-all.js"></script>
</head>
 
<body>
<form name="form1" method="post" msgtype="1" validate="submit">
<jsp:include page="/common/tools/tyym/czclym.jsp" flush="true" >
	<jsp:param name="resid" value="" /> 
</jsp:include>
<table width="98%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
      <div class="panel-default">
          <p class="text-primary" style="font-size: xx-large">当前位置：示例&nbsp;&gt;&nbsp;ECharts</p>
      </div>
  </tr>
  <tr>
  <td height="20" valign="bottom">
    <table height="18" border="0" cellpadding="0" cellspacing="0">
	  <tr>
        <td height="18"><table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td valign="middle" background="<%=jmfg%>/images/right/tagm_on_bg.gif" style="padding-left:10px;padding-top:2px" class="white">标准折线图</td>
            <td><img src="<%=jmfg%>/images/right/tagr_on_x.gif" width="23" height="18"></td>
          </tr>
        </table></td>
        <td height="18"><table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td background="<%=jmfg%>/images/right/tagm_off_bg.gif" style="padding-top:2px;"><a href="<%=SxAppConfig.getAPPCONTEXT()%>/echarts/bzzzt.jsp" class="link04">标准柱状图</a></td>
            <td width="23"><img src="<%=jmfg%>/images/right/tagr_off_all.gif" width="23" height="18"></td>
          </tr>
        </table></td>
		<td height="18"><table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td background="<%=jmfg%>/images/right/tagm_off_bg.gif" style="padding-top:2px;"><a href="<%=SxAppConfig.getAPPCONTEXT()%>/echarts/bzbt.jsp" class="link04">标准饼图</a></td>
            <td width="23"><img src="<%=jmfg%>/images/right/tagr_off_end.gif" width="23" height="18"></td>
          </tr>
        </table></td>
      </tr>
    </table>
    </td>
  </tr>
</table>
<table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="title">
	<tr>
		<td width="30" height="19"><img src="<%=jmfg%>/css/bb_d.gif" width="30" height="19"></td>
		<td style="padding-top:4px">ECharts</td>
	</tr>
</table>

<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main3" style="height:400px"></div>
</form>
</body>
<script type="text/javascript">
	var myChart3 = echarts.init(document.getElementById('main3')); 
	var option3 = {
	    title : {
	        text: '未来一周气温变化',
	        subtext: '纯属虚构'
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        data:['最高气温','最低气温']
	    },
	    toolbox: {
	        show : false,
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            magicType : {show: true, type: ['line', 'bar']},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            data : ['周一','周二','周??','周四','周五','周六','周??']
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            axisLabel : {
	                formatter: '{value} °C'
	            }
	        }
	    ],
	    series : [
	        {
	            name:'最高气温',
	            type:'line',
	            data:[11, 11, 15, 13, 12, 13, 10],
	            markPoint : {
	                data : [
	                    {type : 'max', name: '最大值'},
	                    {type : 'min', name: '最小值'}
	                ]
	            },
	            markLine : {
	                data : [
	                    {type : 'average', name: '平均值'}
	                ]
	            }
	        },
	        {
	            name:'最低气温',
	            type:'line',
	            data:[1, -2, 2, 5, 3, 2, 0],
	            markPoint : {
	                data : [
	                    {name : '周最低', value : -2, xAxis: 1, yAxis: -1.5}
	                ]
	            },
	            markLine : {
	                data : [
	                    {type : 'average', name : '平均值'}
	                ]
	            }
	        }
	    ]
	};
	myChart3.setOption(option3);
</script>
</html>
