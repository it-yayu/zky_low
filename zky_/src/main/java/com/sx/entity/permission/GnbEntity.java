package com.sx.entity.permission;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sx.common.annotation.SxLength;
import com.sx.common.validater.PatternValidater;


import javax.validation.constraints.Pattern;

/**
 * @Author: zyt
 * @Date: 2019/5/28
 * @Description: 功能实体类

 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GnbEntity {
    /**
     * 功能id
     */
    private String gnid;
    /**
     * 功能名称
     */
    @SxLength(min = 1, max = 32, message = "功能名称长度不能为0且不能大于32")
    @Pattern(regexp = PatternValidater.normal, message = "功能名称包含非法字符")
    private String gnmc;
    /**
     * 功能路径
     */
    @SxLength(min = 1, max = 128, message = "功能路径长度不能为0且不能大于128")
    @Pattern(regexp = PatternValidater.normal, message = "功能路径包含非法字符")
    private String gnlj;
    /**
     * 功能类型 0：模块 1：菜单 2：功能

     */
    private String gnlx;
    /**
     * 父级功能id
     */
    private String fjgnid;
    /**
     * 功能创建日期
     */
    private String gncjrq;
    /**
     * 序号
     */
  /*  @SxLength(min = 0, max = 2, message = "序号长度不能大于2", groups = {BaseValiGroup.class})
    @Pattern(regexp = PatternValidater.num, message = "序号应为正整数", groups = {BaseValiGroup.class})*/
    private Integer xh;


    private String xh2;

    /**
     * 子功能

     */
    private GnbEntity[] childrens;
    /**
     * 页码
     */
    private String pageNum;
    /**
     * 图标路径
     */
    @SxLength(min = 0, max = 128, message = "图标路径长度不能大于128")
    @Pattern(regexp = PatternValidater.normal, message = "图标路径包含非法字符")
    private String tblj;

    public String getGnid() {
        return gnid;
    }

    public void setGnid(String gnid) {
        this.gnid = gnid;
    }

    public String getGnmc() {
        return gnmc;
    }

    public void setGnmc(String gnmc) {
        this.gnmc = gnmc;
    }

    public String getGnlj() {
        return gnlj;
    }

    public void setGnlj(String gnlj) {
        this.gnlj = gnlj;
    }

    public String getGnlx() {
        return gnlx;
    }

    public void setGnlx(String gnlx) {
        this.gnlx = gnlx;
    }

    public String getFjgnid() {
        return fjgnid;
    }

    public void setFjgnid(String fjgnid) {
        this.fjgnid = fjgnid;
    }

    public String getGncjrq() {
        return gncjrq;
    }

    public void setGncjrq(String gncjrq) {
        this.gncjrq = gncjrq;
    }

    public String getPageNum() {
        return pageNum;
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public GnbEntity[] getChildrens() {
        return childrens;
    }

    public void setChildrens(GnbEntity[] childrens) {
        this.childrens = childrens;
    }

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
    }

    public String getTblj() {
        return tblj;
    }

    public void setTblj(String tblj) {
        this.tblj = tblj;
    }

    public String getXh2() {
        return xh2;
    }

    public void setXh2(String xh2) {
        this.xh2 = xh2;
    }
}
