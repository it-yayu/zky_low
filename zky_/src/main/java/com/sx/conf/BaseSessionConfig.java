package com.sx.conf;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@SuppressWarnings("all")
@JsonInclude(Include.NON_EMPTY)
public class BaseSessionConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;

    /**
     * 服务器端IP
     */
    private String serverip;

    /**
     * 客户端IP
     */
    private String clientid;

    /**
     * 登陆单位id（RBAC）
     */
    private String dwid;

    /**
     * 单位编号（基础库）
     */
    private String dwbh;

    /**
     * 登陆单位名称
     */
    private String dwmc;

    /**
     * 登陆单位联系电话
     */
    private String dwlxdh;

    /**
     * 登陆单位联系人
     */
    private String dwlxr;

    /**
     * 登陆单位负责人
     */
    private String dwfzr;

    /**
     * 单位联系地址
     */
    private String dwlxdz;

    /**
     * 单位法人码
     */
    private String dwfrm;

    /**
     * 登陆操作员id
     */

    private String czyid;

    /**
     * 登陆操作员姓名
     */
    private String czyxm;

    /**
     * 登陆操作员联系电话
     */
    private String czylxdh;

    /**
     * 登陆时间
     */
    private String dlsj;

    /**
     * 登陆ip
     */
    private String dlip;

    /**
     * 单位所在的行政区划代码
     */
    private String xzqhdm;

    /**
     * 单位所在的行政区划名称
     */
    private String xzqhmc;

    /**
     * 登陆操作员所属级别
     */
    private String ssjb;

    /**
     * 登录票据
     */
    private String ticket;

    /**
     * 登陆SessionID
     */
    private String sessionid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServerip() {
        return serverip;
    }

    public void setServerip(String serverip) {
        this.serverip = serverip;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getDwid() {
        return dwid;
    }

    public void setDwid(String dwid) {
        this.dwid = dwid;
    }

    public String getDwbh() {
        return dwbh;
    }

    public void setDwbh(String dwbh) {
        this.dwbh = dwbh;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public String getDwlxdh() {
        return dwlxdh;
    }

    public void setDwlxdh(String dwlxdh) {
        this.dwlxdh = dwlxdh;
    }

    public String getDwlxr() {
        return dwlxr;
    }

    public void setDwlxr(String dwlxr) {
        this.dwlxr = dwlxr;
    }

    public String getDwfzr() {
        return dwfzr;
    }

    public void setDwfzr(String dwfzr) {
        this.dwfzr = dwfzr;
    }

    public String getDwlxdz() {
        return dwlxdz;
    }

    public void setDwlxdz(String dwlxdz) {
        this.dwlxdz = dwlxdz;
    }

    public String getDwfrm() {
        return dwfrm;
    }

    public void setDwfrm(String dwfrm) {
        this.dwfrm = dwfrm;
    }

    public String getCzyid() {
        return czyid;
    }

    public void setCzyid(String czyid) {
        this.czyid = czyid;
    }

    public String getCzyxm() {
        return czyxm;
    }

    public void setCzyxm(String czyxm) {
        this.czyxm = czyxm;
    }

    public String getCzylxdh() {
        return czylxdh;
    }

    public void setCzylxdh(String czylxdh) {
        this.czylxdh = czylxdh;
    }

    public String getDlsj() {
        return dlsj;
    }

    public void setDlsj(String dlsj) {
        this.dlsj = dlsj;
    }

    public String getDlip() {
        return dlip;
    }

    public void setDlip(String dlip) {
        this.dlip = dlip;
    }

    public String getXzqhdm() {
        return xzqhdm;
    }

    public void setXzqhdm(String xzqhdm) {
        this.xzqhdm = xzqhdm;
    }

    public String getXzqhmc() {
        return xzqhmc;
    }

    public void setXzqhmc(String xzqhmc) {
        this.xzqhmc = xzqhmc;
    }

    public String getSsjb() {
        return ssjb;
    }

    public void setSsjb(String ssjb) {
        this.ssjb = ssjb;
    }

    public final String getTicket() {
        return ticket;
    }

    public final void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

}
