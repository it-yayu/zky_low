package com.sx.common;

import com.sx.conf.SxAppConfig;
import com.sx.conf.SessionConfig;
import com.sx.rbac.RbacOperation;
import com.sx.tag.SxTagEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * <pre>
 * Title: 生成横向菜单，并且进行权限校验
 * Description: 生成横向菜单，并且进行权限校验
 * Copyright: Copyright (c) 2006
 * Company: bksx
 * @author oyxz
 * @version 1.0
 * </pre>
 */
@SuppressWarnings("serial")
public class MenuTag extends SxTagEx {

	private Logger logger = LoggerFactory.getLogger(MenuTag.class);

	private String menubeandata;//菜单数据bean
	private boolean checkuser=false;//是否执行checkuser
	private String name;//名称

	public MenuTag() {
		
	}
	/**
	 * DoStartTag方法
	 */
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public int sxDoStartTag(){
		JspWriter out = pageContext.getOut();
		
		MenuBean mbd = (MenuBean) pageContext.getAttribute(this.menubeandata);
		
		SessionConfig sessionConf = (SessionConfig) pageContext.getSession().getAttribute("sessionConf");
		if (sessionConf == null) {
			logger.debug("MenuTag,sxDoStartTag,sessionConf is null!");
			return SKIP_BODY; 
		}
		String czyid = sessionConf.getCzyid();
		String sessionid = sessionConf.getSessionid();
		String jmfg = sessionConf.getJmfg();
		
		String[] menuname = mbd.getMenuname();
		String[] href = mbd.getHref();
		String[] resid = mbd.getResid();
		boolean[] checked = mbd.getChecked();

		
		if (menuname==null||href==null||resid==null||checked==null){
			logger.debug("MenuTag,sxDoStartTag:{} is null!",this.menubeandata);
			return SKIP_BODY; 
		}
		
		//==============================================================
		//校验权限开始
		//==============================================================
		//操作id
		if (checkuser) {
			String opid[] = new String[resid.length];
			for (int i = 0; i < opid.length; i++) {
				opid[i] = "0000000001";
			}
			
			boolean checkb[] = RbacOperation.checkUser(sessionid, czyid, opid,resid);
			if (checkb==null){
				logger.error("MenuTa,sxDoStartTag,checkUser return is null!");
				return SKIP_BODY; 
			}
			LinkedList llmenuname = new LinkedList();
			LinkedList llhref = new LinkedList();
			LinkedList llchecked = new LinkedList();
			for (int i=0; i<checkb.length;i++){
				logger.debug("MenuTag,sxDoStartTag:{} , checkUser return is: {}",resid[i],checkb[i]);
				if(checkb[i]){//有权限
					llmenuname.add(menuname[i]);  
					llhref.add(href[i]);  
					if(checked[i]){
						llchecked.add("1");  
					}else{
						llchecked.add("0");
					}
				}
			}
			menuname = new String[llmenuname.size()];
			href = new String[llhref.size()];
			llmenuname.toArray(menuname);
			llhref.toArray(href);
			checked = new boolean[llchecked.size()];
			for (int i=0;i<llchecked.size(); i++){
				checked[i]=false;
				if (llchecked.get(i).equals("1")){
					checked[i]=true;
				}
			}
			llmenuname.clear();
			llhref.clear();
			llchecked.clear();
			
		}
		//==============================================================
		//校验权限结束
		//==============================================================
		
				
		try {			
			for(int i=0;i<menuname.length;i++){
//				out.println("<td><table height='33' border='0' cellspacing='0' cellpadding='0' class='tag_a'><tr>");
//				out.println("<td height='18' valign='middle' background='"+jmfg+"");
//				if(checked[i]){
//					if(i==0){
//						out.println("/images/right/tagm_on_bg.gif' style='padding-left:10px;padding-top:2px' class='white'>"+menuname[i]+"");
//					}else{
//						out.println("/images/right/tagm_on_bg.gif' style='padding-top:2px' class='white'>"+menuname[i]+"");
//					}
//					if(i==menuname.length-1){
//						out.println("</td><td width='23'><img src='"+jmfg+"/images/right/tagr_on_end.gif' width='23' height='18'></td></tr></table></td>");
//					}else{
//						out.println("</td><td width='23'><img src='"+jmfg+"/images/right/tagr_on_x.gif' width='23' height='18'></td> </tr></table></td>");
//					}
//
//				}else{
//					if(i==0){
//						out.println("/images/right/tagm_off_bg.gif' style='padding-left:10px;padding-top:2px'><a href='"+SxAppConfig.getAPPCONTEXT()+href[i]+"' class='link04'>"+menuname[i]+"</a>");
//					}else{
//						out.println("/images/right/tagm_off_bg.gif' style='padding-top:2px'><a href='"+SxAppConfig.getAPPCONTEXT()+href[i]+"' class='link04'>"+menuname[i]+"</a>");
//					}
//
//					if(i<menuname.length-1){
//						if(checked[i+1]){
//							out.println("</td><td width='23' ><img src='"+jmfg+"/images/right/tagr_off_x.gif'width='23' height='18'></td></tr></table></td>");
//						}else{
//							out.println("</td><td width='23'><img src='"+jmfg+"/images/right/tagr_off_all.gif' width='23' height='18'></td></tr></table></td>");
//						}
//					}
//					if(i==menuname.length-1){
//						out.println("</td><td width='23'><img src='"+jmfg+"/images/right/tagr_off_end.gif'width='23' height='18'></td></tr></table></td>");
//					}
//				}
				
				if(checked[i]){
					out.println("<td><table height='33' border='0' cellspacing='0' cellpadding='0' class='tag_a'><tr><td class='n1'></td><td class='n2'>"+menuname[i]+"</td><td class='n3'></td></tr></table></td>");

				}else{
					out.println("<td><table height='33' border='0' cellspacing='0' cellpadding='0' class='tag_n'><tr><td class='n1'></td><td class='n2'><a href='"+ SxAppConfig.getAPPCONTEXT()+href[i]+"' class='link04'>"+menuname[i]+"</a></td><td class='n3'></td></tr></table></td>");
				}
			}
			
			return SKIP_BODY;
			
		} catch (IOException e) {//第三方代码扫描修改，异常由统一的工具类控制输出--zgj
			e.printStackTrace();
			logger.debug("MenuTag,sxDoStartTag:{}","MenuTag Exception is "+e.getMessage());
			return SKIP_BODY;
		}
	}
	
	public boolean isCheckuser() {
		return checkuser;
	}
	public void setCheckuser(boolean checkuser) {
		this.checkuser = checkuser;
	}
	public String getMenubeandata() {
		return menubeandata;
	}
	public void setMenubeandata(String menubeandata) {
		this.menubeandata = menubeandata;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	


}
