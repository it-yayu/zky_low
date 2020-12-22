package com.sx.entity.permission;

/**
 * @Author: zyt
 * @Date: 2019/5/28
 * @Description: 人员角色对应表

 */
public class RyJsDybEntity {
    /**
     * 角色id
     */
    private String jsid;
    /**
     * 人员id
     */
    private String czyid;

    public String getJsid() {
        return jsid;
    }

    public void setJsid(String jsid) {
        this.jsid = jsid;
    }

    public String getCzyid() {
        return czyid;
    }

    public void setCzyid(String czyid) {
        this.czyid = czyid;
    }
}
