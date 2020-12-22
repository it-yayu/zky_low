package com.sx.common;

/**
 * <pre>
 * Title: 横向菜单数据bean
 * Description: 横向菜单数据bean
 * Copyright: Copyright (c) 2006
 * Company: bksx
 * @author oyxz
 * @version 1.0
 * </pre>
 */
public class MenuBean {

	public MenuBean() {
	}

	private String[] menuname;//菜单名称
	
	private String[] resid;// 资源id

	private String[] href;//链接地址

	private boolean[] checked;//是否默认选中

	public boolean[] getChecked() {
		return checked;
	}

	public void setChecked(boolean[] checked) {
		this.checked = checked;
	}

	public String[] getHref() {
		return href;
	}

	public void setHref(String[] href) {
		this.href = href;
	}

	public String[] getResid() {
		return resid;
	}

	public void setResid(String[] resid) {
		this.resid = resid;
	}

	public String[] getMenuname() {
		return menuname;
	}

	public void setMenuname(String[] menuname) {
		this.menuname = menuname;
	}

}
