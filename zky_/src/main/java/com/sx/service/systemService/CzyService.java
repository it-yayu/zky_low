package com.sx.service.systemService;


import com.sx.bean.AppBeanUtils;
import com.sx.common.GenerateId;
import com.sx.conf.SxAppConfig;
import com.sx.dao.systemDao.CzyDao;
import com.sx.dao.systemDao.GnbDao;
import com.sx.dao.systemDao.JsbDao;
import com.sx.entity.permission.*;
import com.sx.exception.DbException;
import com.sx.helper.DateHelper;
import com.sx.tableBeen.Szdayy_qx_czybTB;
import com.sx.tableBeen.Szdayy_qx_mmxgjlbTB;
import com.sx.tableBeen.Szdayy_qx_ryjsdybTB;
import com.sx.utility.MD5Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author: zyt
 * @Date: 2019/5/28
 * @Description: 操作员Service
 */
@Service
public class CzyService {

    /**
     * 操作员Dao
     */
    @Autowired
    private CzyDao czyDao;
    /**
     * 角色Dao
     */
    @Autowired
    private JsbDao jsbDao;

    /**
     * 功能表Dao
     */
    @Autowired
    private GnbDao gnbDao;

    private int pageCount;
    private int startNum;
    private int rowsNum;

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getRowsNum() {
        return rowsNum;
    }

    public void setRowsNum(int rowsNum) {
        this.rowsNum = rowsNum;
    }

    /**
     * 登录
     *
     * @param dlyhm 登录用户名
     * @param dlmm  登录密码
     * @return
     * @throws DbException
     */
    public CzyEntity login(String dlyhm, String dlmm) throws DbException {
        return czyDao.login(dlyhm, dlmm);
    }

    /**
     * 根据id获取操作员
     *
     * @param czyid
     * @return
     */
    public CzyEntity getCzyById(String czyid) throws DbException {
        return czyDao.getCzy(czyid, null);
    }

    /**
     * 获取操作员功能
     *
     * @param czyid
     * @return
     * @throws DbException
     */
    public GnbEntity[] getCzy2JGn(String czyid) throws DbException {
        return gnbDao.getUpGn();
    }

    /**
     * 获得操作员功能
     *
     * @param czyid
     * @return
     */
    public GnbEntity[] getCzyGn(String czyid, String gnlx) throws DbException {
        return gnbDao.getCzyGn(czyid, gnlx);
    }

    /**
     * 查询操作员功能下的子功能
     *
     * @param czyid
     * @param fjgnid
     * @return
     * @throws DbException
     */
    public GnbEntity[] getCzyZiGn(String czyid, String fjgnid) throws DbException {
        return gnbDao.getCzyZiGn(czyid, fjgnid);
    }

    /**
     * 获取某单位下所有的操作员
     *
     * @param entity
     * @return
     * @throws DbException
     */
    public CzyEntity[] getCzyByDwid(CzyEntity entity, String czyid) throws DbException {
        //获取单位下的所有操作员
        CzyEntity[] czys = czyDao.getCzyByDwid(entity, czyid);
        if (null != czys && czys.length > 0) {
            for (int i = 0; i < czys.length; i++) {
                //获取操作员的角色
                JsbEntity[] roles = jsbDao.getCzyJs(czys[i].getCzyid());
                czys[i].setRoles(roles);
            }
        }
        return czys;
    }

    /**
     * 删除操作员
     *
     * @param czyid 删除操作员
     * @return
     * @throws DbException
     */
    public boolean deleteCzyById(String czyid) throws DbException {
        //判断是否是操作员如果是操作员则不可删除

        CzyEntity czy = czyDao.getCzy(czyid, "admin");
        if (czy != null) {
            return false;
        } else {
            //删除人员角色对应表数据

            jsbDao.deleteJsFromCzy(czyid, null);
            //删除操作员表数据
            return czyDao.deleteCzyById(czyid);
        }
    }

    /**
     * 新增操作员
     *
     * @param entity
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws DbException
     */
    public boolean insertCzy(CzyEntity entity) throws InvocationTargetException, IllegalAccessException, DbException {
        CzyEntity czy = czyDao.getCzy(null, entity.getDlyhm());
        if (null != czy) {
            return false;
        } else {
            Szdayy_qx_czybTB tb = new Szdayy_qx_czybTB();
            AppBeanUtils.copyProperties(tb, entity);
            tb.setDlmm(MD5Tools.getStringMD5(MD5Tools.getStringMD5(entity.getDlmm()) + SxAppConfig.getSALT()));
            tb.setCzyid(GenerateId.getGenerateId());
            czyDao.insert(tb);
            return true;
        }
    }

    /**
     * 更新操作员信息
     *
     * @param entity
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws DbException
     */
    public void updateCzy(CzyEntity entity) throws InvocationTargetException, IllegalAccessException, DbException {
        Szdayy_qx_czybTB tb = new Szdayy_qx_czybTB();
        AppBeanUtils.copyProperties(tb, entity);
        czyDao.update(tb);
    }


    /**
     * 更新操作员密码
     *
     * @param entity
     * @param czyid
     * @return
     * @throws DbException
     */
    public boolean changePwd(CzyEntity entity, String czyid) throws DbException {
        //判断使用原来的登录名和密码登录是否成功

        CzyEntity czy = czyDao.getAllInfo(entity.getDlyhm(), MD5Tools.getStringMD5(MD5Tools.getStringMD5(entity.getDlmm()) + SxAppConfig.getSALT()));
        if (null != czy) {
            //判断原密码和新密码是否相同
            if (entity.getDlmm().equals(entity.getXdlmm())) {
                return true;
            }
            if (!entity.getXdlmm().equals(entity.getXdlmms())) {
                return true;
            }
            entity.setCzyid(czy.getCzyid());
            //更新操作员表信息
            boolean flag = czyDao.changePwd(entity);
            if (flag) {
                //插入密码变更记录表数据

                Szdayy_qx_mmxgjlbTB tb = new Szdayy_qx_mmxgjlbTB();
                tb.setXgid(GenerateId.getGenerateId());
                tb.setCzyid(czy.getCzyid());
                tb.setCzyxm(czy.getCzyxm());
                tb.setDlyhm(czy.getDlyhm());
                tb.setXgqmm(czy.getDlmm());
                tb.setXghmm(MD5Tools.getStringMD5(MD5Tools.getStringMD5(entity.getXdlmm()) + SxAppConfig.getSALT()));
                tb.setXgsj(DateHelper.getNow());
                tb.setXgczyid(czyid);
                czyDao.insert(tb);
                return true;
            }
        }
        return false;
    }

    /**
     * 为操作员新增角色
     *
     * @param entity
     * @throws Exception
     */
    public void insertJsForCzy(CzyEntity entity) throws Exception {
        //先删除操作员之前的赋的角色

        jsbDao.deleteJsFromCzy(entity.getCzyid(), null);
        //为操作员赋新的角色

        String[] jsids = entity.getJsids();
        if (null != jsids && jsids.length > 0) {
            for (int i = 0; i < jsids.length; i++) {
                Szdayy_qx_ryjsdybTB tb = new Szdayy_qx_ryjsdybTB();
                tb.setCzyid(entity.getCzyid()); //操作员id
                tb.setJsid(jsids[i]); //角色id
                this.czyDao.insert(tb);
            }
        }
    }

    /**
     * 删除操作员角色
     *
     * @param entity
     * @return
     */
    public boolean deleteJsFromCzy(RyJsDybEntity entity) throws DbException {
        return jsbDao.deleteJsFromCzy(entity.getCzyid(), entity.getJsid());
    }

    /**
     * 批量操作
     *
     * @param czyids
     * @param jsids
     * @return
     * @throws DbException
     */
    public boolean plcz(String[] czyids, String[] jsids) throws DbException {
        if (null != czyids && czyids.length > 0 && null != jsids && jsids.length > 0) {
            for (int i = 0; i < czyids.length; i++) {
                //删除该操作员的角色

                jsbDao.deleteJsFromCzy(czyids[i], null);
                //为该操作员重新赋角色
                for (int j = 0; j < jsids.length; j++) {
                    Szdayy_qx_ryjsdybTB tb = new Szdayy_qx_ryjsdybTB();
                    tb.setCzyid(czyids[i]); //操作员id
                    tb.setJsid(jsids[j]); //角色id
                    this.czyDao.insert(tb);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 根据操作员姓名，登陆员号码，单位名称查询对应的操作员
     *
     * @param entity
     * @return
     * @throws DbException
     */
    public CzyEntity[] getczyxx(CzypwdEntity entity) throws DbException {
        CzyEntity[] czys = czyDao.getczyxx(entity);
        this.pageCount = this.czyDao.getIntPageCount();
        this.rowsNum = this.czyDao.getIntRowsCount();
        this.startNum = this.czyDao.getIntStartNum();
        return czys;
    }

    /**
     * 重置密码
     *
     * @param entity
     * @return
     * @throws DbException
     */
    public boolean czmm(CzypwdEntity entity) throws DbException {
        return czyDao.czmm(entity);
    }
}
