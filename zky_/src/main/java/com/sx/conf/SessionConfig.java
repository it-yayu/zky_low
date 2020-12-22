package com.sx.conf;

import com.sx.entity.permission.KsljVB;
import com.sx.utility.StringTools;

import java.util.ArrayList;

public class SessionConfig extends BaseSessionConfig {

    private static final long serialVersionUID = 1L;

    /**
     * 登陆单位id（RBAC）
     */
    private String dwid;

    /**
     * 单位编号（基础库）
     */
    private String dwbh;

    /**
     * 登陆单位名称
     */
    private String dwmc;

    /**
     * 登陆单位联系电话
     */
    private String dwlxdh;

    /**
     * 登陆单位联系人
     */
    private String dwlxr;

    /**
     * 登陆单位负责人
     */
    private String dwfzr;

    /**
     * 单位联系地址
     */
    private String dwlxdz;

    /**
     * 单位邮编
     */
    private String dwyb;

    /**
     * 单位电子邮箱
     */
    private String dwdzyj;

    /**
     * 单位法人码
     */
    private String dwfrm;

    /**
     * 登陆操作员id
     */
    private String czyid;

    /**
     * 登陆操作员姓名
     */
    private String czyxm;

    /**
     * 登陆操作员联系电话
     */
    private String czylxdh;

    /**
     * 登陆时间
     */
    private String dlsj;

    /**
     * 登陆ip
     */
    private String dlip;

    /**
     * 使用界面风格
     */
    private String jmfg;

    /**
     * 使用监管平台界面风格
     */
    private String jgpt_jmfg;

    /**
     * 使用业务监管平台界面风格--姚兴增加2012-12-11
     */
    private String ywjgpt_jmfg;

    /**
     * 单位所在的行政区划代码
     */
    private String xzqhdm;

    /**
     * 单位所在的行政区划名称
     */
    private String xzqhmc;

    /**
     * 登陆操作员所属级别（市、区、街、居）
     */
    private String ssjb;

    /**
     * 基础库注册cao对象
     */
    private Object cao;

    /**
     * 基础库注册pmo对象
     */
    private Object pmo;

    private String sessionid;

    // =============================================================
    // 人事代理参数
    // =============================================================

    /* 人事代理参数 */
    private String qsdwxh;// 区属单位序号

    private String jdsdwxh;// 街道属单位序号

    // private String cdbh_cddwid;//存档编号
    // =============================================================
    // =============================================================
    // 职介业务session
    // =============================================================
    /**
     * 登录职介的对单位服务的有效期（业务session）
     */
    private String dwyxq;

    /**
     * 登录职介的对单位某一工种服务的有效期（业务session）
     */
    private String dwgzyxq;

    /**
     * 登录职介的对个人服务的有效期（业务session）
     */
    private String gryxq;

    /**
     * 登录职介的推荐信的回执期限（业务session）
     */
    private String hzqx;

    /**
     * 登录职介的推荐信标题（业务session）
     */
    private String tjxbt;

    /**
     * 登录职介所设置的个人服务次数（业务session）
     */
    private String grfwcs;

    /**
     * 登录职介所设置的单位服务比例（业务session）
     */
    private String dwfwbl;

    /**
     * 登录职介是否为乡镇（业务session）
     */
    private String sfxz;

    /**
     * 单位有效期次数（业务session）
     */
    private String dwyxqcs;

    /**
     * 个人有效期次数（业务session）
     */
    private String gryxqcs;

    // ======================================================

    // ======================================================
    // 失业人员参数
    // ======================================================

    /**
     * 单位办公时间
     */
    private String dwbgsj;

    /**
     * 操作员办公时间
     */
    private String czybgsj;

    /**
     * 求职证序号（业务session）
     */
    private String qzzxh;

    /**
     * 指定医院（业务session）
     */
    private String zdyy;

    /**
     * 是否银行发放（业务session）
     */
    private String sfyhff;

    //====================chen===20070302add==========begin======================
    /**
     * 单位简称（业务session）
     */
    private String dwjc;
    /**
     * 单位性质（业务session）
     */
    private String dwxz;
    /**
     * 组织机构代码（业务session）
     */
    private String zzjgdm;
    /**
     * 社会保险登记证号（业务session）
     */
    private String shbxdjzh;
    /**
     * 电子档案IP（业务session）
     */
    private String dzdaip;
    /**
     * 电子档案是否有效（业务session）
     */
    private String dzdasfyx;
    /**
     * 条码是否有效（业务session）
     */
    private String tmsfyx;
    //====================chen===20070302add=========end=======================

    // ======================================================
    // 系统保留配置
    // ======================================================
    /**
     * 实现快速连接
     */
    @SuppressWarnings("rawtypes")
    private ArrayList ksljal = null;

    private KsljVB[] ksljvb = null;

    /**
     * lx  add  2007-6-29
     * LXNJYF	例行年检月份
     * NJZQ	年检周期
     */
    private String lxnjyf;
    private String njzq;
    private String sbdwdm;
    //监管平台添加--跳转单位ID
    private String tzdwid;
    /**
     * 实现查询列表返回
     */
    private Object object = null;

    //======================================================
    // ca保留认证
    // ======================================================
    private String serverip = null;
    private String clientid = null;
    private String vcode = null;
    private String cert = null;


    /**
     * 高校毕业生事项
     */
    private String gxbysJhbh;//高校毕业生计划编号
    private String gxbysDqsx;//高校毕业生当前事项
    private String gxbySxnf;//高校毕业生事项年份

    private String jgh;//机构号  add by HY for rsdagl at 2013-11-26

    //应用服务器sessionid，通过request.getSession().getId()
    private String appsessionid;


    //新身份认证，增加票据、票据签名、容器名称
    private String ticket;
    private String ticketSignedData;
    private String containerName;

    //劳服监管平台界面风格
    private String lfjgpt_jmfg;

    /**
     * cdhbm
     */
    private String cdhbm;

    // 数字档案管理专用字段
    /**
     * 登录用户名
     */
    private String dlyhm;
    /**
     * 功能路径
     */
    private String gnlj;

    public String getDlyhm() {
        return dlyhm;
    }

    public void setDlyhm(String dlyhm) {
        this.dlyhm = dlyhm;
    }

    public String getGnlj() {
        return gnlj;
    }

    public void setGnlj(String gnlj) {
        this.gnlj = gnlj;
    }


    /**
     * @return 返回 cdhbm。
     */
    public String getCdhbm() {
        return cdhbm;
    }

    /**
     * @param cdhbm 要设置的 cdhbm。
     */
    public void setCdhbm(String cdhbm) {
        this.cdhbm = cdhbm;
    }

    public String getLfjgpt_jmfg() {
        return lfjgpt_jmfg;
    }

    public void setLfjgpt_jmfg(String lfjgpt_jmfg) {
        this.lfjgpt_jmfg = lfjgpt_jmfg;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }


    public String getTicketSignedData() {
        return ticketSignedData;
    }

    public void setTicketSignedData(String ticketSignedData) {
        this.ticketSignedData = ticketSignedData;
    }

    /**
     * @return Returns the appsessionid.
     */
    public String getAppsessionid() {
        return appsessionid;
    }

    /**
     * @param appsessionid The appsessionid to set.
     */
    public void setAppsessionid(String appsessionid) {
        this.appsessionid = appsessionid;
    }

    public String getGxbysJhbh() {
        return gxbysJhbh;
    }

    public void setGxbysJhbh(String gxbysJhbh) {
        this.gxbysJhbh = gxbysJhbh;
    }

    public String getGxbysDqsx() {
        return gxbysDqsx;
    }

    public void setGxbysDqsx(String gxbysDqsx) {
        this.gxbysDqsx = gxbysDqsx;
    }

    public String getGxbySxnf() {
        return gxbySxnf;
    }

    public void setGxbySxnf(String gxbySxnf) {
        this.gxbySxnf = gxbySxnf;
    }

    /**
     * @return Returns the cert.
     */
    public String getCert() {
        return cert;
    }

    /**
     * @param cert The cert to set.
     */
    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getServerip() {
        return serverip;
    }

    public void setServerip(String serverip) {
        this.serverip = serverip;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getCzyid() {
        return czyid;
    }

    public String getCzylxdh() {
        return czylxdh;
    }

    public String getCzyxm() {
        return czyxm;
    }

    public String getDlip() {
        return dlip;
    }

    public String getDlsj() {
        return dlsj;
    }

    public String getDwdzyj() {
        return dwdzyj;
    }

    public String getDwfrm() {
        return dwfrm;
    }

    public String getDwfzr() {
        return dwfzr;
    }

    public String getDwid() {
        return dwid;
    }

    public String getDwlxdh() {
        return dwlxdh;
    }

    public String getDwlxdz() {
        return dwlxdz;
    }

    public String getDwlxr() {
        return dwlxr;
    }

    public String getDwmc() {
        return dwmc;
    }

    public String getDwyb() {
        return dwyb;
    }

    public String getJmfg() {
        return jmfg;
    }

    public String getSsjb() {
        return ssjb;
    }

    public String getXzqhdm() {
        return xzqhdm;
    }

    public String getXzqhmc() {
        return xzqhmc;
    }

    public void setXzqhmc(String xzqhmc) {
        this.xzqhmc = xzqhmc;
    }

    public void setXzqhdm(String xzqhdm) {
        this.xzqhdm = xzqhdm;
        this.ssjb = this.getSsjb(xzqhdm);
    }

    private String getSsjb(String xzqhdm) {
        String ssjb = null;
        if (xzqhdm.length() == 12) {
            if (xzqhdm.substring(2).equals("0000000000"))
                ssjb = "1"; // 市
            else if (xzqhdm.substring(6).equals("000000"))
                ssjb = "2"; // 区
            else if (xzqhdm.substring(9).equals("000"))
                ssjb = "3"; // 街
            else
                ssjb = "4"; // 居
        }
        return ssjb;
    }

    public void setSsjb(String ssjb) {
        this.ssjb = ssjb;
    }

    public void setJmfg(String jmfg) {
        this.jmfg = jmfg;
    }

    public void setDwyb(String dwyb) {
        this.dwyb = dwyb;
    }

    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }

    public void setDwlxr(String dwlxr) {
        this.dwlxr = dwlxr;
    }

    public void setDwlxdz(String dwlxdz) {
        this.dwlxdz = dwlxdz;
    }

    public void setDwlxdh(String dwlxdh) {
        this.dwlxdh = dwlxdh;
    }

    public void setDwid(String dwid) {
        this.dwid = dwid;
    }

    public void setDwfzr(String dwfzr) {
        this.dwfzr = dwfzr;
    }

    public void setDwfrm(String dwfrm) {
        this.dwfrm = dwfrm;
    }

    public void setDwdzyj(String dwdzyj) {
        this.dwdzyj = dwdzyj;
    }

    public void setDlsj(String dlsj) {
        this.dlsj = dlsj;
    }

    public void setDlip(String dlip) {
        this.dlip = dlip;
    }

    public void setCzyxm(String czyxm) {
        this.czyxm = czyxm;
    }

    public void setCzylxdh(String czylxdh) {
        this.czylxdh = czylxdh;
    }

    public void setCzyid(String czyid) {
        this.czyid = czyid;
    }

    /* =======职介业务=====begin======== */

    /**
     * @return 返回 dwfwbl。
     */
    public String getDwfwbl() {
        return dwfwbl;
    }

    /**
     * @param dwfwbl 要设置的 dwfwbl。
     */
    public void setDwfwbl(String dwfwbl) {
        this.dwfwbl = dwfwbl;
    }

    /**
     * @return 返回 dwyxq。
     */
    public String getDwyxq() {
        return dwyxq;
    }

    /**
     * @return 返回 dwgzyxq。
     */
    public String getDwgzyxq() {
        return dwgzyxq;
    }

    /**
     * @param dwgzyxq 要设置的 dwgzyxq。
     */
    public void setDwgzyxq(String dwgzyxq) {
        this.dwgzyxq = dwgzyxq;
    }

    /**
     * @param dwyxq 要设置的 dwyxq。
     */
    public void setDwyxq(String dwyxq) {
        this.dwyxq = dwyxq;
    }

    /**
     * @return 返回 grfwcs。
     */
    public String getGrfwcs() {
        return grfwcs;
    }

    /**
     * @param grfwcs 要设置的 grfwcs。
     */
    public void setGrfwcs(String grfwcs) {
        this.grfwcs = grfwcs;
    }

    /**
     * @return 返回 gryxq。
     */
    public String getGryxq() {
        return gryxq;
    }

    /**
     * @param gryxq 要设置的 gryxq。
     */
    public void setGryxq(String gryxq) {
        this.gryxq = gryxq;
    }

    /**
     * @return 返回 hzqx。
     */
    public String getHzqx() {
        return hzqx;
    }

    /**
     * @param hzqx 要设置的 hzqx。
     */
    public void setHzqx(String hzqx) {
        this.hzqx = hzqx;
    }

    /**
     * @return 返回 tjxbt。
     */
    public String getTjxbt() {
        return tjxbt;
    }

    /**
     * @param tjxbt 要设置的 tjxbt。
     */
    public void setTjxbt(String tjxbt) {
        this.tjxbt = tjxbt;
    }

    /**
     * @return 返回 sfxz。
     */
    public String getSfxz() {
        return sfxz;
    }

    /**
     * @param sfxz 要设置的 sfxz。
     */
    public void setSfxz(String sfxz) {
        this.sfxz = sfxz;
    }

    public String getDwyxqcs() {
        return dwyxqcs;
    }

    public void setDwyxqcs(String dwyxqcs) {
        this.dwyxqcs = dwyxqcs;
    }

    public String getGryxqcs() {
        return gryxqcs;
    }

    public void setGryxqcs(String gryxqcs) {
        this.gryxqcs = gryxqcs;
    }

    /* =======职介业务=======end====== */
    public Object getCao() {
        return cao;
    }

    public void setCao(Object cao) {
        this.cao = cao;
    }

    public Object getPmo() {
        return pmo;
    }

    public String getDwbgsj() {
        return dwbgsj;
    }

    public String getCzybgsj() {
        return czybgsj;
    }

    public void setPmo(Object pmo) {
        this.pmo = pmo;
    }

    public void setDwbgsj(String dwbgsj) {
        this.dwbgsj = dwbgsj;
    }

    public void setCzybgsj(String czybgsj) {
        this.czybgsj = czybgsj;
    }

    /*
     * public ArrayList getLjal() { return ljal; }
     *
     * public void setLjal(KsljVB ksljvb) { if (ljal == null) ljal = new
     * ArrayList(); if (!ljal.contains(ksljvb)) ljal.add(ksljvb); if
     * (ljal.size() >= 6) { ljal.remove(0); } }
     */
    public KsljVB[] getKsljvb() {
        return ksljvb;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void setKsljvb(KsljVB pksljvb) {
        KsljVB tempksljvb = null;
        int flag = 0;
        if (ksljal == null)
            ksljal = new ArrayList();

        // 判断是否存在现有列表中
        for (int i = 0; i < ksljal.size(); i++) {
            tempksljvb = (KsljVB) ksljal.get(i);
            // System.out.println(tempksljvb.getGrbh());
            // System.out.println(pksljvb.getGrbh());
            if (StringTools.toTrim(tempksljvb.getGrbh()).equals(
                    StringTools.toTrim(pksljvb.getGrbh()))) {
                flag = 1;
                break;
            }
        }
        // System.out.println(flag);
        // 如果不存在将加入列表
        if (flag == 0)
            ksljal.add(pksljvb);

        // 超过5个将移出最先进入的人员
        if (ksljal.size() >= 6) {
            ksljal.remove(0);
        }
        // 放入KsljVB数组中
        ksljvb = new KsljVB[ksljal.size()];
        ksljal.toArray(ksljvb);
        // ksljal.clear();

    }

    /**
     * @return 返回 dwbh。
     */
    public String getDwbh() {
        return dwbh;
    }

    /**
     * @param dwbh 要设置的 dwbh。
     */
    public void setDwbh(String dwbh) {
        this.dwbh = dwbh;
    }

    public String getJdsdwxh() {
        return jdsdwxh;
    }

    public void setJdsdwxh(String jdsdwxh) {
        this.jdsdwxh = jdsdwxh;
    }

    public String getQsdwxh() {
        return qsdwxh;
    }

    public void setQsdwxh(String qsdwxh) {
        this.qsdwxh = qsdwxh;
    }

    /**
     * @return 返回存档单位编号
     */
    public String getCdbh_cddwid() {
        if (this.xzqhdm == null) {
            return null;
        }
        /**
         * 如果区属单位序号不为空 返回行政区划的3-8位+区属单位序号 否则 返回行政区划的3-8位+街道属单位序号
         */
        if (this.qsdwxh != null) {
            return this.xzqhdm.substring(4, 9) + this.qsdwxh;
        }
        return this.xzqhdm.substring(4, 9) + this.jdsdwxh;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    /**
     * @return 返回 qzzxh。
     */
    public String getQzzxh() {
        return qzzxh;
    }

    /**
     * @param qzzxh 要设置的 qzzxh。
     */
    public void setQzzxh(String qzzxh) {
        this.qzzxh = qzzxh;
    }

    /**
     * @return 返回 zdyy。
     */
    public String getZdyy() {
        return zdyy;
    }

    /**
     * @param zdyy 要设置的 zdyy。
     */
    public void setZdyy(String zdyy) {
        this.zdyy = zdyy;
    }

    /**
     * @return 返回 sfyhff。
     */
    public String getSfyhff() {
        return sfyhff;
    }

    /**
     * @param sfyhff 要设置的 sfyhff。
     */
    public void setSfyhff(String sfyhff) {
        this.sfyhff = sfyhff;
    }

    public String getDwjc() {
        return dwjc;
    }

    public void setDwjc(String dwjc) {
        this.dwjc = dwjc;
    }

    public String getDwxz() {
        return dwxz;
    }

    public void setDwxz(String dwxz) {
        this.dwxz = dwxz;
    }

    public String getDzdaip() {
        return dzdaip;
    }

    public void setDzdaip(String dzdaip) {
        this.dzdaip = dzdaip;
    }

    public String getDzdasfyx() {
        return dzdasfyx;
    }

    public void setDzdasfyx(String dzdasfyx) {
        this.dzdasfyx = dzdasfyx;
    }

    public String getTmsfyx() {
        return tmsfyx;
    }

    public void setTmsfyx(String tmsfyx) {
        this.tmsfyx = tmsfyx;
    }

    @SuppressWarnings("rawtypes")
    public ArrayList getKsljal() {
        return ksljal;
    }

    @SuppressWarnings("rawtypes")
    public void setKsljal(ArrayList ksljal) {
        this.ksljal = ksljal;
    }

    public String getShbxdjzh() {
        return shbxdjzh;
    }

    public void setShbxdjzh(String shbxdjzh) {
        this.shbxdjzh = shbxdjzh;
    }

    public String getZzjgdm() {
        return zzjgdm;
    }

    public void setZzjgdm(String zzjgdm) {
        this.zzjgdm = zzjgdm;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }


    public void setKsljvb(KsljVB[] ksljvb) {
        this.ksljvb = ksljvb;
    }

    public String getLxnjyf() {
        return lxnjyf;
    }

    public void setLxnjyf(String lxnjyf) {
        this.lxnjyf = lxnjyf;
    }

    public String getNjzq() {
        return njzq;
    }

    public void setNjzq(String njzq) {
        this.njzq = njzq;
    }

    public String getJgpt_jmfg() {
        return jgpt_jmfg;
    }

    public void setJgpt_jmfg(String jgpt_jmfg) {
        this.jgpt_jmfg = jgpt_jmfg;
    }

    public String getYwjgpt_jmfg() {
        return ywjgpt_jmfg;
    }

    public void setYwjgpt_jmfg(String ywjgpt_jmfg) {
        this.ywjgpt_jmfg = ywjgpt_jmfg;
    }

    public String getTzdwid() {
        return tzdwid;
    }

    public void setTzdwid(String tzdwid) {
        this.tzdwid = tzdwid;
    }

    public String getSbdwdm() {
        return sbdwdm;
    }

    public void setSbdwdm(String sbdwdm) {
        this.sbdwdm = sbdwdm;
    }

    public String getJgh() {
        return jgh;
    }

    public void setJgh(String jgh) {
        this.jgh = jgh;
    }

}
