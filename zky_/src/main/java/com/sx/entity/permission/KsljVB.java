package com.sx.entity.permission;

import java.io.Serializable;

/**
 * <p>
 * Title: 北京市劳动力市场信息系统，实现滑动连接的数据对象
 * </p>
 * <p>
 * Description: 北京市劳动力市场信息系统，实现滑动连接的数据对象
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: bksx
 * </p>
 *
 * @author oyxz
 * @version 1.0
 */

public class KsljVB implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String sfzhm = null; // 身份证号码

    private String xm = null; // 姓名

    private String grbh = null; // 个人编号

    // private String ljdz = null; //连接地址

    public KsljVB() {
    }

    public String getGrbh() {
        return grbh;
    }

    public String getSfzhm() {
        return sfzhm;
    }

    public String getXm() {
        return xm;
    }

    public void setGrbh(String grbh) {
        this.grbh = grbh;
    }

    public void setSfzhm(String sfzhm) {
        this.sfzhm = sfzhm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

}