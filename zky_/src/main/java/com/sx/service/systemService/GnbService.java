package com.sx.service.systemService;

import com.sx.bean.AppBeanUtils;
import com.sx.common.GenerateId;
import com.sx.dao.systemDao.GnbDao;
import com.sx.entity.permission.GnbEntity;
import com.sx.exception.DbException;
import com.sx.helper.DateHelper;

import com.sx.tableBeen.Szdayy_qx_gnbTB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * @Author: zyt
 * @Date: 2019/5/28
 * @Description: 功能service
 */
@Service
public class GnbService {

    @Autowired
    private GnbDao gnbDao;

    /**
     * 获得全部功能树,到3级

     *
     * @param gnid
     * @return
     * @throws DbException
     */
    public GnbEntity[] get3JGnTree(String gnid) throws DbException {
        //查询功能模块
        GnbEntity[] mkgns = gnbDao.getGn(gnid, null, "0");
        if (null != mkgns && mkgns.length > 0) {
            mkgns = get3JChildrens(mkgns);
        }
        return mkgns;
    }

    /**
     * 获得全部功能树,到2级

     *
     * @param gnid
     * @return
     * @throws DbException
     */
    public GnbEntity[] get2JGnTree(String gnid) throws DbException {
        //查询功能模块
        GnbEntity[] mkgns = gnbDao.getGn(gnid, null, "0");
        if (null != mkgns && mkgns.length > 0) {
            mkgns = get2JChildrens(mkgns);
        }
        return mkgns;
    }

    /**
     * 查询父级功能
     *
     * @return
     * @throws DbException
     */
    public GnbEntity[] getUpGn() throws DbException {
        return gnbDao.getUpGn();
    }

    /**
     * 新增功能
     *
     * @param entity
     */
    public void insertGn(GnbEntity entity) throws Exception {
        int maxXh = gnbDao.getMaxXh(entity);
        entity.setXh(maxXh+1);
        Szdayy_qx_gnbTB tb = new Szdayy_qx_gnbTB();
        AppBeanUtils.copyProperties(tb, entity);
        tb.setGnid(GenerateId.getGenerateId());
        tb.setGncjrq(DateHelper.getNow());
        gnbDao.insert(tb);
    }

    /**
     * 删除功能
     *
     * @param gnid
     * @return
     * @throws DbException
     */
    public boolean deleteGn(String gnid) throws DbException {
        // 删除角色功能对应表


        gnbDao.deleteGnFromJs(null, gnid);
        //删除功能表


        return gnbDao.deleteGn(gnid);
    }

    /**
     * 查询功能下的子功能,到3级

     *
     * @param gns
     * @return
     * @throws DbException
     */
    public GnbEntity[] get3JChildrens(GnbEntity[] gns) throws DbException {
        for (int i = 0; i < gns.length; i++) {
            GnbEntity[] childrens = gnbDao.getGn(null, gns[i].getGnid(), null);
            if (null != childrens && childrens.length > 0) {
                gns[i].setChildrens(get3JChildrens(childrens));
            }
        }
        return gns;
    }

    /**
     * 查询功能下的子功能,到2J级

     * @param gns
     * @return
     * @throws DbException
     */
    public GnbEntity[] get2JChildrens(GnbEntity[] gns) throws DbException {
        for (int i = 0; i < gns.length; i++) {
            GnbEntity[] childrens = gnbDao.getGn(null, gns[i].getGnid(), "1");
            if (null != childrens && childrens.length > 0) {
                gns[i].setChildrens(get2JChildrens(childrens));
            }
        }
        return gns;
    }

    /**
     * 更新功能
     *
     * @param entity
     * @throws DbException
     */
    public void updateGn(GnbEntity entity) throws DbException, InvocationTargetException, IllegalAccessException {
        Szdayy_qx_gnbTB tb = new Szdayy_qx_gnbTB();
        AppBeanUtils.copyProperties(tb, entity);
        gnbDao.update(tb);
    }
}
