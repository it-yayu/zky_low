package com.sx.tableBeen;
//===============================================================================================================================================================================================================
//顺序      列名        中文名          数据类型      宽度        主键        非空        外键        外键表        外键列        索引        代码表        备注
//===============================================================================================================================================================================================================
//1         xgid        修改id          varchar2      14          是          是

//2         czyid       操作员id        varchar2      14
//3         czyxm       操作员姓名      varchar2      14
//4         dlyhm       登录用户名      varchar2      10
//5         xgqmm       修改前密码      varchar2      20
//6         xghmm       修改后密码      varchar2      20
//7         xgsj        修改时间        varchar2      14
//8         xgczyid     修改操作员id    varchar2      14
//===============================================================================================================================================================================================================
import com.sx.bean.AbsTableBean;
import com.sx.bean.KeyBean;

/**
 * <p>Title: 数字档案应用_权限_密码修改记录表 </p>
 * <p>Description: szdayy_qx_mmxgjlb </p>
 * <p>Copyright: Copyright (c) 2019 </p>
 * <p>Company: bksx</p>
 */
public class Szdayy_qx_mmxgjlbTB extends AbsTableBean{
    public Szdayy_qx_mmxgjlbTB(){
        super("szdayy_qx_mmxgjlb");
    }
    /**
     *修改id
     */
    private KeyBean key_xgid = new KeyBean("xgid");
    /**
     *操作员id
     */
    private String czyid;
    /**
     *操作员姓名

     */
    private String czyxm;
    /**
     *登录用户名

     */
    private String dlyhm;
    /**
     *修改前密码

     */
    private String xgqmm;
    /**
     *修改后密码

     */
    private String xghmm;
    /**
     *修改时间
     */
    private String xgsj;
    /**
     *修改操作员id
     */
    private String xgczyid;
    /**
     * 得到修改id
     * @param : 无

     * @return:xgid,修改id
     */
    public String getXgid() {
        if(this.key_xgid==null){
            return "";
        }
        return key_xgid.getValue();
    }
    /**
     * 设置修改id
     * @param xgid:修改id
     * @return:无

     */
    public void setXgid(String xgid) {
        if(this.key_xgid==null){
            this.key_xgid = new KeyBean("xgid");
        }
        this.key_xgid.setValue(xgid);
    }
    /**
     * 得到修改id
     * @param : 无

     * @return:xgid,修改id
     */
    public KeyBean getKey_xgid() {
        return key_xgid;
    }
    /**
     * 设置修改id
     * @param xgid:修改id
     * @return:无

     */
    public void setKey_xgid(KeyBean xgid) {
        this.key_xgid=xgid;
    }
    /**
     * 得到操作员id
     * @param : 无

     * @return:czyid,操作员id
     */
    public String getCzyid() {
        return czyid;
    }
    /**
     * 设置操作员id
     * @param czyid:操作员id
     * @return:无

     */
    public void setCzyid(String czyid) {
        this.czyid=czyid;
    }
    /**
     * 得到操作员姓名

     * @param : 无

     * @return:czyxm,操作员姓名

     */
    public String getCzyxm() {
        return czyxm;
    }
    /**
     * 设置操作员姓名

     * @param czyxm:操作员姓名

     * @return:无

     */
    public void setCzyxm(String czyxm) {
        this.czyxm=czyxm;
    }
    /**
     * 得到登录用户名

     * @param : 无

     * @return:dlyhm,登录用户名

     */
    public String getDlyhm() {
        return dlyhm;
    }
    /**
     * 设置登录用户名

     * @param dlyhm:登录用户名

     * @return:无

     */
    public void setDlyhm(String dlyhm) {
        this.dlyhm=dlyhm;
    }
    /**
     * 得到修改前密码

     * @param : 无

     * @return:xgqmm,修改前密码

     */
    public String getXgqmm() {
        return xgqmm;
    }
    /**
     * 设置修改前密码

     * @param xgqmm:修改前密码

     * @return:无

     */
    public void setXgqmm(String xgqmm) {
        this.xgqmm=xgqmm;
    }
    /**
     * 得到修改后密码

     * @param : 无

     * @return:xghmm,修改后密码

     */
    public String getXghmm() {
        return xghmm;
    }
    /**
     * 设置修改后密码

     * @param xghmm:修改后密码

     * @return:无

     */
    public void setXghmm(String xghmm) {
        this.xghmm=xghmm;
    }
    /**
     * 得到修改时间
     * @param : 无

     * @return:xgsj,修改时间
     */
    public String getXgsj() {
        return xgsj;
    }
    /**
     * 设置修改时间
     * @param xgsj:修改时间
     * @return:无

     */
    public void setXgsj(String xgsj) {
        this.xgsj=xgsj;
    }
    /**
     * 得到修改操作员id
     * @param : 无

     * @return:xgczyid,修改操作员id
     */
    public String getXgczyid() {
        return xgczyid;
    }
    /**
     * 设置修改操作员id
     * @param xgczyid:修改操作员id
     * @return:无

     */
    public void setXgczyid(String xgczyid) {
        this.xgczyid=xgczyid;
    }
}