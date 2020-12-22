package com.sx.tableBeen;
//===============================================================================================================================================================================================================
//顺序      列名        中文名        数据类型      宽度        主键        非空        外键        外键表        外键列        索引        代码表        备注
//===============================================================================================================================================================================================================
//1         jsid        角色id        varchar2      14          是          是

//2         czyid       操作员id      varchar2      14          是          是

//===============================================================================================================================================================================================================
import com.sx.bean.AbsTableBean;
import com.sx.bean.KeyBean;

/**
 * <p>Title: 数字档案应用_权限_人员角色对应表 </p>
 * <p>Description: szdayy_qx_ryjsdyb </p>
 * <p>Copyright: Copyright (c) 2019 </p>
 * <p>Company: bksx</p>
 */
public class Szdayy_qx_ryjsdybTB extends AbsTableBean{
    public Szdayy_qx_ryjsdybTB(){
        super("szdayy_qx_ryjsdyb");
    }
    /**
     *角色id
     */
    private KeyBean key_jsid = new KeyBean("jsid");
    /**
     *操作员id
     */
    private KeyBean key_czyid = new KeyBean("czyid");
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
}