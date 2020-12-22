package com.sx.dao.systemDao;

import com.sx.conf.SxAppConfig;
import com.sx.db.AbsDaoSupport;
import com.sx.entity.permission.CzyEntity;
import com.sx.entity.permission.CzypwdEntity;
import com.sx.exception.DbException;
import com.sx.helper.StringHelper;
import com.sx.utility.MD5Tools;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zyt
 * @Date: 2019/5/28
 * @Description:
 */
@Repository
public class CzyDao extends AbsDaoSupport {

    /**
     * 操作员登录
     *
     * @param dlyhm 登录用户名
     * @param dlmm  登录密码
     * @return
     * @throws DbException
     */
    public CzyEntity login(String dlyhm, String dlmm) throws DbException {
        String sql = " SELECT czyid,czyxm,dlyhm,ssdwid FROM szdayy_qx_czyb WHERE dlyhm = ? AND dlmm = ? ";
        CzyEntity[] czy = (CzyEntity[]) this.querySqlForArray(sql, new String[]{dlyhm, dlmm}, CzyEntity.class);
        if (null != czy && czy.length > 0) {
            return czy[0];
        }
        return null;
    }

    /**
     * 获取操作员全部信息
     *
     * @param dlyhm
     * @param dlmm
     * @return
     * @throws DbException
     */
    public CzyEntity getAllInfo(String dlyhm, String dlmm) throws DbException {
        String sql = " SELECT czyid,czyxm,dlyhm,dlmm,ssdwid FROM szdayy_qx_czyb WHERE dlyhm = ? AND dlmm = ? ";
        CzyEntity[] czy = (CzyEntity[]) this.querySqlForArray(sql, new String[]{dlyhm, dlmm}, CzyEntity.class);
        if (null != czy && czy.length > 0) {
            return czy[0];
        }
        return null;
    }


    /**
     * 获得操作员
     *
     * @param czyid
     * @param dlyhm
     * @return
     * @throws DbException
     */
    public CzyEntity getCzy(String czyid, String dlyhm) throws DbException {
        String sql = " SELECT czyid,czyxm,dlyhm,ssdwid FROM szdayy_qx_czyb WHERE 1=1 ";
        List list = new ArrayList<>();
        if (StringHelper.isNotEmpty(czyid)) {
            sql += " AND czyid = ? ";
            list.add(czyid);
        }
        if (StringHelper.isNotEmpty(dlyhm)) {
            sql += " AND dlyhm = ? ";
            list.add(dlyhm);
        }
        String[] params = new String[list.size()];
        list.toArray(params);
        list.clear();
        CzyEntity[] vbs = (CzyEntity[]) this.querySqlForArray(sql, params, CzyEntity.class);
        if (null != vbs && vbs.length > 0) {
            return vbs[0];
        }
        return null;
    }

    /**
     * 查询单位下的所有操作员
     *
     * @param entity
     * @return
     * @throws DbException
     */
    public CzyEntity[] getCzyByDwid(CzyEntity entity, String dlyhm) throws DbException {
        String sql = " SELECT czy.czyid,czyxm,czy.dlyhm,czy.ssdwid FROM szdayy_qx_czyb czy WHERE czy.ssdwid = ? ";
        List list = new ArrayList();
        list.add(entity.getSsdwid());
        if (!"admin".equals(dlyhm)) {
            sql += " AND czy.dlyhm !=? ";
            list.add("admin");
        }
        String[] params = (String[]) list.toArray(new String[list.size()]);
        list.clear();
        if (StringHelper.isNotEmpty(entity.getPageNum())) {
            return (CzyEntity[]) this.querySqlPageForArray(sql, params, SxAppConfig.getPAGECOUNTBYH(), Integer.valueOf(entity.getPageNum()), CzyEntity.class);
        } else {
            return (CzyEntity[]) this.querySqlForArray(sql, params, CzyEntity.class);
        }
    }

    /**
     * 删除操作员
     *
     * @param czyid
     * @return
     * @throws DbException
     */
    public boolean deleteCzyById(String czyid) throws DbException {
        String sql = " DELETE FROM szdayy_qx_czyb WHERE czyid=?";
        return this.execSql(sql, new String[]{czyid});
    }

    /**
     * 修改密码
     *
     * @param entity
     * @return
     * @throws DbException
     */
    public boolean changePwd(CzyEntity entity) throws DbException {
        String sql = " UPDATE szdayy_qx_czyb SET dlmm = ? WHERE czyid = ? ";

        return this.execSql(sql, new String[]{MD5Tools.getStringMD5(MD5Tools.getStringMD5(entity.getXdlmm()) + SxAppConfig.getSALT()), entity.getCzyid()});
    }

    /**
     * 根据操作员姓名，登陆员号码，单位名称查询对应的操作员
     *
     * @param entity
     * @return
     * @throws DbException
     */
    public CzyEntity[] getczyxx(CzypwdEntity entity) throws DbException {
        StringBuffer sql = new StringBuffer();
        ArrayList<String> list = new ArrayList<>();
        sql.append("select czy.czyid,czy.czyxm,czy.dlyhm,dw.dwmc from szdayy_qx_czyb czy, dahtsys_dwjbxxb dw where dw.dwid=czy.ssdwid");
        if (StringHelper.isNotEmpty(entity.getDlyhm())) {
            sql.append(" and czy.dlyhm=? ");
            list.add(entity.getDlyhm());
        }
        if (StringHelper.isNotEmpty(entity.getCzyxm())) {
            sql.append(" and czy.czyxm=? ");
            list.add(entity.getCzyxm());
        }
        if (StringHelper.isNotEmpty(entity.getDwmc())) {
            sql.append(" and dw.dwmc=? ");
            list.add(entity.getDwmc());
        }
        sql.append(" order by czy.czyid");
        String[] tj = list.toArray(new String[list.size()]);
        CzyEntity[] czys = (CzyEntity[]) this.querySqlPageForArray(sql.toString(), tj, SxAppConfig.getPAGECOUNTBYH(), Integer.valueOf(entity.getPageNum()), CzyEntity.class);
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
        String sql = "update szdayy_qx_czyb set dlmm=? where czyid=?";
        return this.execSql(sql, new String[]{MD5Tools.getStringMD5(MD5Tools.getStringMD5("0") + SxAppConfig.getSALT()), entity.getCzyid()});

    }
}
