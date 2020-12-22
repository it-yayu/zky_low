package com.sx.tableBeen;
//===============================================================================================================================================================================================================
//顺序      列名        中文名        数据类型      宽度        主键        非空        外键        外键表        外键列        索引        代码表        备注
//===============================================================================================================================================================================================================
//1         czyid       操作员id      varchar2      14          是          是

//2         czyxm       操作员姓名    varchar2      14
//3         dlyhm       登录用户名    varchar2      10
//4         dlmm        登录密码      varchar2      20
//5         ssdwid      所属单位id    varchar2      14
//===============================================================================================================================================================================================================
import com.sx.bean.AbsTableBean;
import com.sx.bean.KeyBean;

/**
 * <p>Title: 数字档案应用_权限_操作员表 </p>
 * <p>Description: szdayy_qx_czyb </p>
 * <p>Copyright: Copyright (c) 2019 </p>
 * <p>Company: bksx</p>
 */
public class Szdayy_qx_czybTB extends AbsTableBean{
    public Szdayy_qx_czybTB(){
        super("szdayy_qx_czyb");
    }
    /**
     *操作员id
     */
    private KeyBean key_czyid = new KeyBean("czyid");
    /**
     *操作员姓名

     */
    private String czyxm;
    /**
     *登录用户名

     */
    private String dlyhm;
    /**
     *登录密码
     */
    private String dlmm;
    /**
     *所属单位id
     */
    private String ssdwid;
    /**
     * 得到操作员id
     * @param : 无

     * @return:czyid,操作员id
     */
    public String getCzyid() {
        if(this.key_czyid==null){
            return "";
        }
        return key_czyid.getValue();
    }
    /**
     * 设置操作员id
     * @param czyid:操作员id
     * @return:无

     */
    public void setCzyid(String czyid) {
        if(this.key_czyid==null){
            this.key_czyid = new KeyBean("czyid");
        }
        this.key_czyid.setValue(czyid);
    }
    /**
     * 得到操作员id
     * @param : 无

     * @return:czyid,操作员id
     */
    public KeyBean getKey_czyid() {
        return key_czyid;
    }
    /**
     * 设置操作员id
     * @param czyid:操作员id
     * @return:无

     */
    public void setKey_czyid(KeyBean czyid) {
        this.key_czyid=czyid;
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
     * 得到登录密码
     * @param : 无

     * @return:dlmm,登录密码
     */
    public String getDlmm() {
        return dlmm;
    }
    /**
     * 设置登录密码
     * @param dlmm:登录密码
     * @return:无

     */
    public void setDlmm(String dlmm) {
        this.dlmm=dlmm;
    }
    /**
     * 得到所属单位id
     * @param : 无

     * @return:ssdwid,所属单位id
     */
    public String getSsdwid() {
        return ssdwid;
    }
    /**
     * 设置所属单位id
     * @param ssdwid:所属单位id
     * @return:无

     */
    public void setSsdwid(String ssdwid) {
        this.ssdwid=ssdwid;
    }
}