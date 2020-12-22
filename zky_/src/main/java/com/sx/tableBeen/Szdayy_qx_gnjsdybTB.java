package com.sx.tableBeen;
//===============================================================================================================================================================================================================
//顺序      列名        中文名        数据类型      宽度        主键        非空        外键        外键表        外键列        索引        代码表        备注
//===============================================================================================================================================================================================================
//1         jsid        角色id        varchar2      14          是          是

//2         gnid        功能id        varchar2      14          是          是

//===============================================================================================================================================================================================================
import com.sx.bean.AbsTableBean;
import com.sx.bean.KeyBean;

/**
 * <p>Title: 数字档案应用_权限_功能角色对应表 </p>
 * <p>Description: szdayy_qx_gnjsdyb </p>
 * <p>Copyright: Copyright (c) 2019 </p>
 * <p>Company: bksx</p>
 */
public class Szdayy_qx_gnjsdybTB extends AbsTableBean{
    public Szdayy_qx_gnjsdybTB(){
        super("szdayy_qx_gnjsdyb");
    }
    /**
     *角色id
     */
    private KeyBean key_jsid = new KeyBean("jsid");
    /**
     *功能id
     */
    private KeyBean key_gnid = new KeyBean("gnid");
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
     * 得到功能id
     * @param : 无

     * @return:gnid,功能id
     */
    public String getGnid() {
        if(this.key_gnid==null){
            return "";
        }
        return key_gnid.getValue();
    }
    /**
     * 设置功能id
     * @param gnid:功能id
     * @return:无

     */
    public void setGnid(String gnid) {
        if(this.key_gnid==null){
            this.key_gnid = new KeyBean("gnid");
        }
        this.key_gnid.setValue(gnid);
    }
    /**
     * 得到功能id
     * @param : 无

     * @return:gnid,功能id
     */
    public KeyBean getKey_gnid() {
        return key_gnid;
    }
    /**
     * 设置功能id
     * @param gnid:功能id
     * @return:无

     */
    public void setKey_gnid(KeyBean gnid) {
        this.key_gnid=gnid;
    }
}