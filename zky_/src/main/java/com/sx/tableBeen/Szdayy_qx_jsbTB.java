package com.sx.tableBeen;
//===============================================================================================================================================================================================================
//顺序      列名        中文名          数据类型      宽度        主键        非空        外键        外键表        外键列        索引        代码表        备注
//===============================================================================================================================================================================================================
//1         jsid        角色id          varchar2      14          是          是

//2         jsmc        角色名称        varchar2      14
//3         jscjrq      角色创建日期    varchar2      14
//===============================================================================================================================================================================================================
import com.sx.bean.AbsTableBean;
import com.sx.bean.KeyBean;

/**
 * <p>Title: 数字档案应用_权限_角色表 </p>
 * <p>Description: szdayy_qx_jsb </p>
 * <p>Copyright: Copyright (c) 2019 </p>
 * <p>Company: bksx</p>
 */
public class Szdayy_qx_jsbTB extends AbsTableBean{
    public Szdayy_qx_jsbTB(){
        super("szdayy_qx_jsb");
    }
    /**
     *角色id
     */
    private KeyBean key_jsid = new KeyBean("jsid");
    /**
     *角色名称
     */
    private String jsmc;
    /**
     *角色创建日期
     */
    private String jscjrq;
    /**
     * 得到角色id
     * @param : 无

     * @return:jsid,角色id
     */
    public String getJsid() {
        if(this.key_jsid==null){
            return "";
        }
        return key_jsid.getValue();
    }
    /**
     * 设置角色id
     * @param jsid:角色id
     * @return:无

     */
    public void setJsid(String jsid) {
        if(this.key_jsid==null){
            this.key_jsid = new KeyBean("jsid");
        }
        this.key_jsid.setValue(jsid);
    }
    /**
     * 得到角色id
     * @param : 无

     * @return:jsid,角色id
     */
    public KeyBean getKey_jsid() {
        return key_jsid;
    }
    /**
     * 设置角色id
     * @param jsid:角色id
     * @return:无

     */
    public void setKey_jsid(KeyBean jsid) {
        this.key_jsid=jsid;
    }
    /**
     * 得到角色名称
     * @param : 无

     * @return:jsmc,角色名称
     */
    public String getJsmc() {
        return jsmc;
    }
    /**
     * 设置角色名称
     * @param jsmc:角色名称
     * @return:无

     */
    public void setJsmc(String jsmc) {
        this.jsmc=jsmc;
    }
    /**
     * 得到角色创建日期
     * @param : 无

     * @return:jscjrq,角色创建日期
     */
    public String getJscjrq() {
        return jscjrq;
    }
    /**
     * 设置角色创建日期
     * @param jscjrq:角色创建日期
     * @return:无

     */
    public void setJscjrq(String jscjrq) {
        this.jscjrq=jscjrq;
    }
}