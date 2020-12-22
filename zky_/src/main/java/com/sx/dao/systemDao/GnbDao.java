package com.sx.dao.systemDao;

import com.sx.db.AbsDaoSupport;
import com.sx.entity.permission.GnbEntity;
import com.sx.exception.DbException;
import com.sx.helper.StringHelper;
import com.sx.support.dba.dbSQLResult;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zyt
 * @Date: 2019/5/28
 * @Description: 功能表Dao
 */
@Repository
public class GnbDao extends AbsDaoSupport {

    /**
     * 查询功能
     *
     * @return
     * @throws DbException
     */
    public GnbEntity[] getGn(String gnid, String fjgnid, String gnlx) throws DbException {
        String sql = " SELECT gnb.gnid,gnb.gnmc,gnb.gnlj,gnb.gnlx,gnb.tblj,gnb.fjgnid,gnb.xh FROM szdayy_qx_gnb gnb  WHERE 1=1 ";
        List list = new ArrayList();
        if (StringHelper.isNotEmpty(gnid)) {
            sql += " AND gnb.gnid = ? ";
            list.add(gnid);
        }
        if (StringHelper.isNotEmpty(fjgnid)) {
            sql += " AND gnb.fjgnid =? ";
            list.add(fjgnid);
        }
        if (StringHelper.isNotEmpty(gnlx)) {
            sql += " AND gnb.gnlx = ? ";
            list.add(gnlx);
        }
        sql += " ORDER BY gnb.xh ASC ";
        String[] params = new String[list.size()];
        list.toArray(params);
        list.clear();
        return (GnbEntity[]) this.querySqlForArray(sql, params, GnbEntity.class);
    }

    /**
     * 查询全部父级功能
     *
     * @return
     * @throws DbException
     */
    public GnbEntity[] getUpGn() throws DbException {
        String sql = " SELECT gnid,gnmc,gnlj,fjgnid,gnlx FROM szdayy_qx_gnb WHERE gnlx != ? ORDER BY fjgnid,xh ";
        return (GnbEntity[]) this.querySqlForArray(sql, new String[]{"2"}, GnbEntity.class);
    }


    /**
     * 获取操作员模块功能
     *
     * @param czyid 操作员id
     * @return
     * @throws DbException
     */
    public GnbEntity[] getCzyGn(String czyid, String gnlx) throws DbException {
        String sql = " SELECT DISTINCT gnb.gnid,gnb.gnmc,gnb.gnlj,gnb.gnlx,gnb.fjgnid,gnb.tblj,gnb.xh "
                + " FROM szdayy_qx_czyb czy,szdayy_qx_jsb jsb,szdayy_qx_ryjsdyb ryjs,szdayy_qx_gnb gnb,szdayy_qx_gnjsdyb jsgn "
                + " WHERE czy.czyid = ryjs.czyid AND ryjs.jsid = jsb.jsid AND jsb.jsid = jsgn.jsid AND jsgn.gnid = gnb.gnid"
                + " AND czy.czyid = ? ";
        if (StringHelper.isNotEmpty(gnlx)) {
            sql += " AND gnb.gnlx=? ORDER BY gnb.xh ASC ";
            return (GnbEntity[]) this.querySqlForArray(sql, new String[]{czyid, gnlx}, GnbEntity.class);
        } else {
            sql += " ORDER BY gnb.xh ASC ";
            return (GnbEntity[]) this.querySqlForArray(sql, new String[]{czyid}, GnbEntity.class);
        }
    }

    /**
     * 查询子功能
     *
     * @param fjgnid 父级功能id
     * @return
     * @throws DbException
     */
    public GnbEntity[] getCzyZiGn(String czyid, String fjgnid) throws DbException {
        String sql = " SELECT DISTINCT gnb.gnid,gnb.gnmc,gnb.gnlj,gnb.gnlx,gnb.fjgnid,gnb.tblj,gnb.xh "
                + " FROM szdayy_qx_czyb czy,szdayy_qx_jsb jsb,szdayy_qx_ryjsdyb ryjs,szdayy_qx_gnb gnb,szdayy_qx_gnjsdyb jsgn "
                + " WHERE czy.czyid = ryjs.czyid AND ryjs.jsid = jsb.jsid AND jsb.jsid = jsgn.jsid AND jsgn.gnid = gnb.gnid"
                + " AND czy.czyid = ? AND gnb.fjgnid = ? AND gnb.gnlx!=? ORDER BY gnb.xh ASC ";
        return (GnbEntity[]) this.querySqlForArray(sql, new String[]{czyid, fjgnid, "2"}, GnbEntity.class);

    }

    /**
     * 获取角色的功能
     *
     * @param jsid
     * @return
     * @throws DbException
     */
  /*  public GnbEntity[] getJsGn(String jsid) throws DbException {
        String sql = " SELECT gnb.gnid,gnb.gnmc,gnb.gnlj "
                + " FROM szdayy_qx_jsb jsb,szdayy_qx_gnjsdyb jsgn,szdayy_qx_gnb gnb "
                + " WHERE jsb.jsid = jsgn.jsid AND jsgn.gnid = gnb.gnid AND jsb.jsid=? ";
        return (GnbEntity[]) this.querySqlForArray(sql, new String[]{jsid}, GnbEntity.class);
    }*/

    /**
     * 获取角色的功能
     *
     * @param jsid
     * @return
     * @throws DbException
     */
    public String[] getJsGn(String jsid, String fjgnid, String gnlx) throws DbException {
        List list = new ArrayList();
        String sql = " SELECT gnb.gnid"
                + " FROM szdayy_qx_jsb jsb,szdayy_qx_gnjsdyb jsgn,szdayy_qx_gnb gnb "
                + " WHERE jsb.jsid = jsgn.jsid AND jsgn.gnid = gnb.gnid AND jsb.jsid=?";
        list.add(jsid);
        if (StringHelper.isNotEmpty(fjgnid)) {
            sql += " AND gnb.fjgnid=? ";
            list.add(fjgnid);
        }
        if (StringHelper.isNotEmpty(gnlx)) {
            sql += " AND gnb.gnlx=?";
            list.add(gnlx);
        }
        String[] params = new String[list.size()];
        list.toArray(params);
        list.clear();
        dbSQLResult rs = this.query(sql, params);
        if (null != rs && rs.getRows() > 0) {
            String[] gnids = new String[rs.getRows()];
            for (int i = 0; i < rs.getRows(); i++) {
                gnids[i] = rs.getData()[i][0];
            }
            return gnids;
        }
        return null;
    }

    /**
     * 删除功能
     *
     * @param gnid
     * @return
     */
    public boolean deleteGn(String gnid) throws DbException {
        String sql = " DELETE FROM szdayy_qx_gnb WHERE gnid = ? ";
        return this.execSql(sql, new String[]{gnid});
    }

    /**
     * 删除角色对应表数据
     *
     * @param jsid
     * @param gnid
     * @return
     * @throws DbException
     */
    public boolean deleteGnFromJs(String jsid, String gnid) throws DbException {
        if (StringHelper.isNotEmpty(jsid) || StringHelper.isNotEmpty(gnid)) {
            String sql = " DELETE FROM szdayy_qx_gnjsdyb WHERE 1=1 ";
            List list = new ArrayList();
            if (StringHelper.isNotEmpty(jsid)) {
                sql += " AND jsid = ? ";
                list.add(jsid);
            }
            if (StringHelper.isNotEmpty(gnid)) {
                sql += " AND gnid = ? ";
                list.add(gnid);
            }
            String[] params = new String[list.size()];
            list.toArray(params);
            list.clear();
            return this.execSql(sql, params);
        }
        return false;
    }


    public int getMaxXh(GnbEntity entity) throws DbException {
        String sql = "SELECT IFNULL(MAX(xh),0) from szdayy_qx_gnb where 1=1 ";
        List list = new ArrayList();
        if (StringHelper.isNotEmpty(entity.getFjgnid())) {
            sql += " AND fjgnid = ? ";
            list.add(entity.getFjgnid());
        }
        if (StringHelper.isNotEmpty(entity.getGnlx())) {
            sql += " AND gnlx = ? ";
            list.add(entity.getGnlx());
        }
        String[] params = new String[list.size()];
        list.toArray(params);
        list.clear();
        dbSQLResult query = this.query(sql, params);
        if (query != null && query.getRows() != 0) {
            String[][] data = query.getData();
            return Integer.valueOf(data[0][0]);
        }
        return 0;
    }


}
