package com.sx.entity.permission;

import com.sx.common.annotation.SxLength;
import com.sx.common.validater.PatternValidater;


import javax.validation.constraints.Pattern;

public class CzypwdEntity {
    /**
     * 操作员id
     */
    private String czyid;
    /**
     * 操作员姓名

     */
    @SxLength(min = 0, max = 14, message = "操作员姓名长度不能为0且不能大于14")
    @Pattern(regexp = PatternValidater.full, message = "操作员姓名包含非法字符")
    private String czyxm;
    /**
     * 操作员登录名
     */
    @SxLength(min = 0, max = 10, message = "操作员登录名长度不能为0且不能大于10")
    @Pattern(regexp = PatternValidater.yhm, message = "操作员登录名包含非法字符")
    private String dlyhm;
    /**
     * 登录密码
     */
    @SxLength(min = 0, max = 32, message = "登录密码长度不能为0且不能大于32")
    @Pattern(regexp = PatternValidater.less, message = "登录密码包含非法字符")
    private String dlmm;
    /**
     * 所属单位id
     */
    private String ssdwid;
    /**
     * 页数
     */
    private String pageNum;
    /**
     * 单位名称
     */
    @SxLength(min = 0, max = 128, message = "单位名称长度不能大于128")
    @Pattern(regexp = PatternValidater.full, message = "单位名称包含非法字符")
    private String dwmc;


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

    public String getDlyhm() {
        return dlyhm;
    }

    public void setDlyhm(String dlyhm) {
        this.dlyhm = dlyhm;
    }

    public String getDlmm() {
        return dlmm;
    }

    public void setDlmm(String dlmm) {
        this.dlmm = dlmm;
    }

    public String getSsdwid() {
        return ssdwid;
    }

    public void setSsdwid(String ssdwid) {
        this.ssdwid = ssdwid;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public String getDwmc() {
        return dwmc;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }
}
