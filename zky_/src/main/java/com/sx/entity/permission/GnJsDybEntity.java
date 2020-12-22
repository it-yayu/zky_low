package com.sx.entity.permission;

/**
 * @Author: zyt
 * @Date: 2019/5/28
 * @Description: 功能角色对应表

 */
public class GnJsDybEntity {
    /**
     * 功能id
     */
    private String gnid;
    /**
     * 角色id
     */
    private String jsid;
    /**
     * 功能数组
     */
    private GnbEntity[] gns;

    public String getGnid() {
        return gnid;
    }

    public void setGnid(String gnid) {
        this.gnid = gnid;
    }

    public String getJsid() {
        return jsid;
    }

    public void setJsid(String jsid) {
        this.jsid = jsid;
    }

    public GnbEntity[] getGns() {
        return gns;
    }

    public void setGns(GnbEntity[] gns) {
        this.gns = gns;
    }
}
