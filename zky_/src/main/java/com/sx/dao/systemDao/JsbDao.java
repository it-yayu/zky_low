package com.sx.dao.systemDao;

import com.sx.db.AbsDaoSupport;
import com.sx.entity.permission.JsbEntity;
import com.sx.exception.DbException;
import com.sx.helper.StringHelper;
import com.sx.support.dba.dbSQLResult;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zyt
 * @Date: 2019/5/28
 * @Description: 角色表Dao
 */
@Repository
public class JsbDao extends AbsDaoSupport {

    /**
     * 获取全部角色
     *
     * @return
     * @throws DbException
     */
    public JsbEntity[] getAllJs() throws DbException {
        String sql = " SELECT jsb.jsid,jsb.jsmc FROM szdayy_qx_jsb jsb ";
        return (JsbEntity[]) this.querySqlForArray(sql, new String[]{}, JsbEntity.class);
    }

    /**
     * 动态查询角色

     *
     * @return
     * @throws DbException
     */
    public JsbEntity[] getJs(String dlyhm) throws DbException {
        List list = new ArrayList();
        String sql = " SELECT jsb.jsid,jsb.jsmc FROM szdayy_qx_jsb jsb WHERE 1=1 ";
        if (!"admin".equals(dlyhm)) {
            sql += " AND jsmc not like ?";
            list.add("管理员");
        }
        String[] params = (String[]) list.toArray(new String[list.size()]);
        list.clear();
        return (JsbEntity[]) this.querySqlForArray(sql, params, JsbEntity.class);
    }

    /**
     * 判断角色名称是否重复
     *
     * @param jsmc
     * @return true：不存在 false：存在

     * @throws DbException
     */
    public boolean getJsByJsmc(String jsmc) throws DbException {
        String sql = "SELECT jsid,jsmc FROM szdayy_qx_jsb WHERE jsmc = ? ";
        JsbEntity[] roles = (JsbEntity[]) this.querySqlForArray(sql, new String[]{jsmc}, JsbEntity.class);
        if (null != roles) {
            return false;
        }
        return true;
    }

    /**
     * 删除角色
     *
     * @param jsid
     * @return
     * @throws DbException
     */
    public boolean deleteJs(String jsid) throws DbException {
        String sql = " DELETE FROM szdayy_qx_jsb WHERE jsid = ? ";
        return this.execSql(sql, new String[]{jsid});
    }

    /**
     * 删除操作员的角色
     *
     * @param czyid
     * @param jsid
     * @return
     * @throws DbException
     */
    public boolean deleteJsFromCzy(String czyid, String jsid) throws DbException {
        //如果全为空，不执行

        if (StringHelper.isNotEmpty(czyid) || StringHelper.isNotEmpty(jsid)) {
            String sql = " DELETE FROM szdayy_qx_ryjsdyb WHERE 1=1 ";
            List list = new ArrayList();
            if (StringHelper.isNotEmpty(czyid)) {
                sql += " AND czyid= ? ";
                list.add(czyid);
            }
            if (StringHelper.isNotEmpty(jsid)) {
                sql += "  AND jsid=? ";
                list.add(jsid);
            }
            String[] params = new String[list.size()];
            list.toArray(params);
            list.clear();
            return this.execSql(sql, params);
        }
        return false;
    }

    /**
     * 查询用户的所有角色

     *
     * @param czyid
     * @return
     * @throws DbException
     */
    public JsbEntity[] getCzyJs(String czyid) throws DbException {
        String sql = " SELECT jsb.jsid,jsb.jsmc FROM szdayy_qx_jsb jsb,szdayy_qx_ryjsdyb ryjs WHERE jsb.jsid = ryjs.jsid AND ryjs.czyid = ? ";
        return (JsbEntity[]) this.querySqlForArray(sql, new String[]{czyid}, JsbEntity.class);
    }

    /**
     * 查询用户角色id集合
     *
     * @param czyid
     * @return
     * @throws DbException
     */
    public String[] getCzyJsid(String czyid) throws DbException {
        String sql = " SELECT jsb.jsid FROM szdayy_qx_jsb jsb,szdayy_qx_ryjsdyb ryjs WHERE jsb.jsid = ryjs.jsid AND ryjs.czyid = ? ";
        dbSQLResult result = this.query(sql, new String[]{czyid});
        if (null != result && result.getRows() > 0) {
            String[] jsids = new String[result.getRows()];
            for (int i = 0; i < result.getRows(); i++) {
                jsids[i] = result.getData()[i][0];
            }
            return jsids;
        }
        return null;
    }
}
