<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="com.sx.conf.SxAppConfig"%>
<%@ page import="com.sx.conf.SessionConfig" %>
<%@ page import="com.sx.conf.AppConfig" %>
<%@ taglib prefix="menutag" uri="/WEB-INF/tld/menutag.tld" %>
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
            <td valign="middle" background="<%=jmfg%>/images/right/tagm_off_bg.gif" style="padding-left:10px;padding-top:2px"><a href="<%=SxAppConfig.getAPPCONTEXT()%>/echarts/bzzxt.jsp" class="link04">标准折线图</a></td>
            <td><img src="<%=jmfg%>/images/right/tagr_off_all.gif" width="23" height="18"></td>
          </tr>
        </table></td>
        <td height="18"><table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td background="<%=jmfg%>/images/right/tagm_off_bg.gif" style="padding-top:2px;"><a href="<%=SxAppConfig.getAPPCONTEXT()%>/echarts/bzzzt.jsp" class="link04">标准柱状图</a></td>
            <td width="23"><img src="<%=jmfg%>/images/right/tagr_off_x.gif" width="23" height="18"></td>
          </tr>
        </table></td>
		<td height="18"><table border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td background="<%=jmfg%>/images/right/tagm_on_bg.gif" style="padding-top:2px;" class="white">标准饼图</td>
            <td width="23"><img src="<%=jmfg%>/images/right/tagr_on_end.gif" width="23" height="18"></td>
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
<div id="main2" style="height:400px"></div>
</form>
</body>
<script type="text/javascript">

    var myChart2 = echarts.init(document.getElementById('main2')); 
	var option2 = {
	    title : {
	        text: '某站点用户访问来源',
	        subtext: '纯属虚构',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient : 'vertical',
	        x : 'right',
	        data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
	    },
	    toolbox: {
	        show : false,
	        feature : {
	            mark : {show: true},
	            dataView : {show: true, readOnly: false},
	            magicType : {
	                show: true, 
	                type: ['pie', 'funnel'],
	                option: {
	                    funnel: {
	                        x: '25%',
	                        width: '50%',
	                        funnelAlign: 'left',
	                        max: 1548
	                    }
	                }
	            },
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    calculable : true,
	    series : [
	        {
	            name:'访问来源',
	            type:'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            data:[
	                {value:335, name:'直接访问'},
	                {value:310, name:'邮件营销'},
	                {value:234, name:'联盟广告'},
	                {value:135, name:'视频广告'},
	                {value:1548, name:'搜索引擎'}
	            ]
	        }
	    ]
	};
	myChart2.setOption(option2);
</script>
</html>
