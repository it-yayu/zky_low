package com.sx.tableBeen;
//===============================================================================================================================================================================================================
//顺序      列名        中文名          数据类型      宽度        主键        非空        外键        外键表        外键列        索引        代码表        备注
//===============================================================================================================================================================================================================
//1         gnid        功能id          varchar       14          是          是

//2         gnmc        功能名称        varchar       32
//3         gnlj        功能路径        varchar       128
//4         gnlx        功能类型        varchar       1                                                                                                     0：模块 1:菜单2：功能

//5         tblj        功能类型        varchar       128
//6         fjgnid      父级功能id      varchar       14
//7         gncjrq      功能创建日期    varchar       14
//8         xh          序号            int           2
//===============================================================================================================================================================================================================
import com.sx.bean.AbsTableBean;
import com.sx.bean.KeyBean;

/**
 * <p>Title: 数字档案应用_权限_功能表 </p>
 * <p>Description: szdayy_qx_gnb </p>
 * <p>Copyright: Copyright (c) 2019 </p>
 * <p>Company: bksx</p>
 */
public class Szdayy_qx_gnbTB extends AbsTableBean{
    public Szdayy_qx_gnbTB(){
        super("szdayy_qx_gnb");
    }
    /**
     *功能id
     */
    private KeyBean key_gnid = new KeyBean("gnid");
    /**
     *功能名称
     */
    private String gnmc;
    /**
     *功能路径
     */
    private String gnlj;
    /**
     *功能类型
     */
    private String gnlx;
    /**
     *功能类型
     */
    private String tblj;
    /**
     *父级功能id
     */
    private String fjgnid;
    /**
     *功能创建日期
     */
    private String gncjrq;
    /**
     *序号
     */
    private String xh;
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
    /**
     * 得到功能名称
     * @param : 无

     * @return:gnmc,功能名称
     */
    public String getGnmc() {
        return gnmc;
    }
    /**
     * 设置功能名称
     * @param gnmc:功能名称
     * @return:无

     */
    public void setGnmc(String gnmc) {
        this.gnmc=gnmc;
    }
    /**
     * 得到功能路径
     * @param : 无

     * @return:gnlj,功能路径
     */
    public String getGnlj() {
        return gnlj;
    }
    /**
     * 设置功能路径
     * @param gnlj:功能路径
     * @return:无

     */
    public void setGnlj(String gnlj) {
        this.gnlj=gnlj;
    }
    /**
     * 得到功能类型
     * @param : 无

     * @return:gnlx,功能类型
     */
    public String getGnlx() {
        return gnlx;
    }
    /**
     * 设置功能类型
     * @param gnlx:功能类型
     * @return:无

     */
    public void setGnlx(String gnlx) {
        this.gnlx=gnlx;
    }
    /**
     * 得到功能类型
     * @param : 无

     * @return:tblj,功能类型
     */
    public String getTblj() {
        return tblj;
    }
    /**
     * 设置功能类型
     * @param tblj:功能类型
     * @return:无

     */
    public void setTblj(String tblj) {
        this.tblj=tblj;
    }
    /**
     * 得到父级功能id
     * @param : 无

     * @return:fjgnid,父级功能id
     */
    public String getFjgnid() {
        return fjgnid;
    }
    /**
     * 设置父级功能id
     * @param fjgnid:父级功能id
     * @return:无

     */
    public void setFjgnid(String fjgnid) {
        this.fjgnid=fjgnid;
    }
    /**
     * 得到功能创建日期
     * @param : 无

     * @return:gncjrq,功能创建日期
     */
    public String getGncjrq() {
        return gncjrq;
    }
    /**
     * 设置功能创建日期
     * @param gncjrq:功能创建日期
     * @return:无

     */
    public void setGncjrq(String gncjrq) {
        this.gncjrq=gncjrq;
    }
    /**
     * 得到序号
     * @param : 无

     * @return:xh,序号
     */
    public String getXh() {
        return xh;
    }
    /**
     * 设置序号
     * @param xh:序号
     * @return:无

     */
    public void setXh(String xh) {
        this.xh=xh;
    }
}