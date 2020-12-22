package com.sx.entity.permission;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sx.common.annotation.SxLength;
import com.sx.common.validater.PatternValidater;


import javax.validation.constraints.Pattern;

/**
 * @Author: zyt
 * @Date: 2019/5/28
 * @Description: 角色Entity
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsbEntity {
    /**
     * 角色id
     */
    private String jsid;
    /**
     * 角色名称
     */
    @SxLength(min = 1, max = 32, message = "角色名称长度不能为0且不能大于32")
    @Pattern(regexp = PatternValidater.full, message = "角色名称包含非法字符")
    private String jsmc;
    /**
     * 角色创建日期
     */
    private String jscjrq;
    /**
     * 功能id集合
     */
    private String[] gnids;

    public String getJsid() {
        return jsid;
    }

    public void setJsid(String jsid) {
        this.jsid = jsid;
    }

    public String getJsmc() {
        return jsmc;
    }

    public void setJsmc(String jsmc) {
        this.jsmc = jsmc;
    }

    public String getJscjrq() {
        return jscjrq;
    }

    public void setJscjrq(String jscjrq) {
        this.jscjrq = jscjrq;
    }

    public String[] getGnids() {
        return gnids;
    }

    public void setGnids(String[] gnids) {
        this.gnids = gnids;
    }
}
