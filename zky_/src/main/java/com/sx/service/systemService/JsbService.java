package com.sx.service.systemService;

import com.sx.bean.AppBeanUtils;
import com.sx.common.GenerateId;
import com.sx.dao.systemDao.GnbDao;
import com.sx.dao.systemDao.JsbDao;
import com.sx.entity.permission.GnJsDybEntity;
import com.sx.entity.permission.JsbEntity;
import com.sx.exception.DbException;
import com.sx.helper.DateHelper;
import com.sx.tableBeen.Szdayy_qx_gnjsdybTB;
import com.sx.tableBeen.Szdayy_qx_jsbTB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @Author: zyt
 * @Date: 2019/5/28
 * @Description: 角色Service
 */
@Service
public class JsbService {

    @Autowired
    private JsbDao jsbDao;
    @Autowired
    private GnbDao gnbDao;

    /**
     * 获取的全部角色
     *
     * @return
     * @throws DbException
     */
    public JsbEntity[] getAllJs() throws DbException {
        return jsbDao.getAllJs();
    }

    /**
     * 动态查询角色
     *
     * @return
     * @throws DbException
     */
    public JsbEntity[] getJs(String dlyhm) throws DbException {
        return jsbDao.getJs(dlyhm);
    }

    /**
     * 获得角色功能树
     *
     * @param jsid
     * @return
     * @throws DbException
     */
    public List<String> getJsGn(String jsid) throws DbException {
        //查询模块功能
        String[] gnids = gnbDao.getJsGn(jsid, null, "1");
        List<String> gnidList = new ArrayList<>();
        if (null != gnids && gnids.length > 0) {
            //获取最下层功能id集合
            gnidList = getGnId(gnidList, gnids, jsid);
        }
        return gnidList;
    }

    public List<String> getAllGnId(String jsid) throws DbException {
        String[] gnids = gnbDao.getJsGn(jsid, null, "1");
        if (gnids == null || gnids.length == 0) {
            List<String> jsGn = getJsGn(jsid);
            return jsGn;
        }
        List<String> list = Arrays.asList(gnids);
        return list;
    }


    /**
     * 新增角色
     *
     * @param entity
     * @throws Exception
     */
    public boolean insertJs(JsbEntity entity) throws Exception {
        //判断角色名称是否存在，如果存在，不让保存
        boolean flag = jsbDao.getJsByJsmc(entity.getJsmc());
        if (flag) {
            Szdayy_qx_jsbTB tb = new Szdayy_qx_jsbTB();
            AppBeanUtils.copyProperties(tb, entity);
            tb.setJsid(GenerateId.getGenerateId());
            tb.setJscjrq(DateHelper.getNow());
            this.jsbDao.insert(tb);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 更新角色信息
     *
     * @param entity
     * @throws DbException
     */
    public boolean updateJs(JsbEntity entity) throws DbException, InvocationTargetException, IllegalAccessException {
        //判断角色名称是否存在，如果存在，不让保存
        boolean flag = jsbDao.getJsByJsmc(entity.getJsmc());
        if (flag) {
            Szdayy_qx_jsbTB tb = new Szdayy_qx_jsbTB();
            AppBeanUtils.copyProperties(tb, entity);
            jsbDao.update(tb);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除角色
     *
     * @param jsid
     * @return
     * @throws DbException
     */
    public boolean deleteJs(String jsid) throws DbException {
        //删除操作员角色对应表数据
        jsbDao.deleteJsFromCzy(null, jsid);
        //删除角色功能表数据

        gnbDao.deleteGnFromJs(jsid, null);
        //删除角色表数据

        return jsbDao.deleteJs(jsid);
    }

    /**
     * 为角色添加功能
     *
     * @param entity
     * @throws Exception
     */
    public void addJsForGn(JsbEntity entity) throws Exception {
        //删除角色功能对应表中的数据

        gnbDao.deleteGnFromJs(entity.getJsid(), null);
        //再将新的关系新增到角色功能对应表中

        if (null != entity.getGnids() && entity.getGnids().length > 0) {
            for (int i = 0; i < entity.getGnids().length; i++) {
                Szdayy_qx_gnjsdybTB tb = new Szdayy_qx_gnjsdybTB();
                tb.setJsid(entity.getJsid());
                tb.setGnid(entity.getGnids()[i]);
                this.jsbDao.insert(tb);
            }
        }
    }

    /**
     * 删除角色功能
     *
     * @param entity
     * @return
     */
    public boolean deleteGnFromJs(GnJsDybEntity entity) throws DbException {
        return gnbDao.deleteGnFromJs(entity.getJsid(), entity.getGnid());
    }


    /**
     * 递归获取角色已经赋的功能，因为前端VUE框架的树插件如果子节点选了，就不需要在返回其父级节点，所以单写的此方法
     *
     * @param gnidList
     * @param gnids
     * @param jsid
     * @return
     * @throws DbException
     */
    public List<String> getGnId(List<String> gnidList, String[] gnids, String jsid) throws DbException {
        for (int i = 0; i < gnids.length; i++) {
            //获取子节点集合

            String[] childrenGnids = gnbDao.getJsGn(jsid, gnids[i], null);
            //如果子节点不为空，继续递归
            if (null != childrenGnids && childrenGnids.length > 0) {
                //将递归后的集合合并到已有的集合中

                getGnId(gnidList, childrenGnids, jsid);
            } else {
                //如果子节点为空，将其父节点添加到集合中

                gnidList.add(gnids[i]);
            }
        }
        return gnidList;
    }

    /**
     * 获得操作员角色id集合
     *
     * @param czyid
     * @return
     * @throws DbException
     */
    public String[] getCzyJs(String czyid) throws DbException {
        return jsbDao.getCzyJsid(czyid);
    }
}
