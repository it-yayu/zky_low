package com.sx.dao.yayuDao;

import cn.hutool.core.util.StrUtil;
import com.sx.db.AbsDaoSupport;
import com.sx.entity.yayuEntity.*;
import com.sx.exception.DbException;
import com.sx.helper.DateHelper;
import com.sx.helper.StringHelper;
import com.sx.support.dba.dbSQLResult;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yayu
 * @title: HrTransferDao
 * @description: TODO
 * @date 2020/10/20 13:23
 */
@Repository
public class HrTransferDao extends AbsDaoSupport {
    private List<String> list = new ArrayList<>();

    public Boolean writeDispatchLetter(KjdhjlbEntity kjdhjlbEntity) throws DbException {
        String sql = "insert INTO  rsdagl_zky_kjdhjlb (dhid,dhbh,sfzhm,xm,yxq,kjhlx,lxdh,cddwmc,ycddwmc,blsx,blsxpz,sfgd,gdsj) VALUE(?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String[] param = new String[]{kjdhjlbEntity.getDhid(), kjdhjlbEntity.getDhbh(), kjdhjlbEntity.getSfzhm(), kjdhjlbEntity.getXm(), kjdhjlbEntity.getYxq(), kjdhjlbEntity.getKjhlx(), kjdhjlbEntity.getLxdh(),
                kjdhjlbEntity.getCddwmc(), kjdhjlbEntity.getYcddwmc(), kjdhjlbEntity.getBlsx(), kjdhjlbEntity.getBlsxpz(), "0", kjdhjlbEntity.getGdsj()
        };
        return this.execSql(sql, param);
    }

    public KjdhjlbEntity writeDispatchLetter_query(KjdhjlbEntity kjdhjlbEntity) throws DbException {
        String sql = "select dhid,dhbh,cddwmc,xm,sfzhm,blsx,blsxpz,yxq,gdsj from rsdagl_zky_kjdhjlb where sfzhm=?";
        list.add(kjdhjlbEntity.getSfzhm());
        String[] param = (String[]) Array.newInstance(String.class, list.size());
        list.toArray(param);
        list.clear();
        KjdhjlbEntity[] vbs = (KjdhjlbEntity[]) querySqlForArray(sql, param, KjdhjlbEntity.class);
        if (null != vbs && vbs.length > 0) {
            return vbs[0];
        }
        return null;
    }

    public String getCodeName(String kjhlx, String xh) throws DbException {
        String sql = "select dmmc from rsdagl_zky_d_blsx where dhlxid=? and dmid=?";
        dbSQLResult query = this.query(sql, new String[]{kjhlx, xh});
        if (query != null && query.getRows() != 0) {
            String[][] data = query.getData();
            return data[0][0];
        }
        return null;
    }

    public List<KjdhjlbEntity> queryDispatchLetter(KjdhjlbEntity kjdhjlbEntity) throws DbException {
        StringBuilder sb = new StringBuilder("select dhid,xm,sfzhm,cddwmc,kjhlx,gdsj,lxdh from rsdagl_zky_kjdhjlb where 1=1");
        ArrayList<String> strings = new ArrayList<>();
        if (!StringHelper.isEmpty(kjdhjlbEntity.getXm())) {
            sb.append(" and xm like  ?");
            strings.add("%" + kjdhjlbEntity.getXm() + "%");
        }
        if (!StringHelper.isEmpty(kjdhjlbEntity.getSfzhm())) {
            sb.append(" and sfzhm like ?");
            strings.add("%" + kjdhjlbEntity.getSfzhm() + "%");
        }
        if (!StringHelper.isEmpty(kjdhjlbEntity.getKjhlx())) {
            sb.append(" and kjhlx=?");
            strings.add(kjdhjlbEntity.getKjhlx());
        }
        if (!StringHelper.isEmpty(kjdhjlbEntity.getYcddwmc())) {
            sb.append(" and ycddwmc=?");
            strings.add(kjdhjlbEntity.getYcddwmc());
        }
        if (!StringHelper.isEmpty(kjdhjlbEntity.getStartTime()) && StrUtil.isNotEmpty(kjdhjlbEntity.getEndTime())) {
            kjdhjlbEntity.setStartTime(DateHelper.fomat8Str(kjdhjlbEntity.getStartTime()));
            kjdhjlbEntity.setEndTime(DateHelper.fomat8Str(kjdhjlbEntity.getEndTime()));
            sb.append(" and gdsj between ? and ? ");
            strings.add(kjdhjlbEntity.getStartTime());
            strings.add(kjdhjlbEntity.getEndTime());
        }
        String[] value = new String[strings.size()];
        strings.toArray(value);
        List<KjdhjlbEntity> list = this.querySqlForList(sb.toString(), value
                , KjdhjlbEntity.class);
        return list;
    }

    public List<KjdhjlbEntity> queryDispatchLetterByxh(KjdhjlbEntity entity) throws DbException {
        StringBuilder sql1 = new StringBuilder("select xm,sfzhm,kjhlx,gdsj,lxdh from rsdagl_zky_kjdhjlb where 1=1  ");
        ArrayList<String> strings = new ArrayList<>();
        if (!StringHelper.isEmpty(entity.getFlag())) {
            String[] split = entity.getFlag().split(",");
            if (null != split && split.length > 0) {
                sql1.append(" and dhid in (");
                for (int i = 0; i < split.length; i++) {
                    if (i == (split.length - 1)) {
                        sql1.append("?");
                    } else {
                        sql1.append("?,");
                    }
                    strings.add(split[i]);
                }
                sql1.append(")");
            }

//            sql1.append("and xh in (" + entity.getFlag() + ")");
        }
        String[] value = new String[strings.size()];
        strings.toArray(value);
        List<KjdhjlbEntity> list = this.querySqlForList(sql1.toString(), value
                , KjdhjlbEntity.class);
        return list;
    }

    public CdrybEntity fileReceiveQueryByIdCard(String sfzhm) throws DbException {
        String sql = "select * from rsdagl_zky_cdryb where sfzhm=?";
        list.add(sfzhm);
        String[] param = (String[]) Array.newInstance(String.class, list.size());
        list.toArray(param);
        list.clear();
        CdrybEntity[] vbs = (CdrybEntity[]) querySqlForArray(sql, param, CdrybEntity.class);
        if (null != vbs && vbs.length > 0) {
            return vbs[0];
        }
        return null;
    }

    public ZcdabEntity fileReceiveQueryByIdCard_temp(String sfzhm) throws DbException {
        String sql = "select lxdh,mz,zzmm,rysf,cjgzsj from rsdagl_zky_zcdab where sfzhm=?";
        list.add(sfzhm);
        String[] param = (String[]) Array.newInstance(String.class, list.size());
        list.toArray(param);
        list.clear();
        ZcdabEntity[] vbs = (ZcdabEntity[]) querySqlForArray(sql, param, ZcdabEntity.class);
        if (null != vbs && vbs.length > 0) {
            return vbs[0];
        }
        return null;
    }

    public CdrybEntity queryRsdaglxx(CdrybEntity cdrybEntity) throws DbException {
        String sql = " select grbh,cdid from rsdagl_ryxxglb where sfzhm=?";
        list.add(cdrybEntity.getSfzhm());
        String[] param = (String[]) Array.newInstance(String.class, list.size());
        list.toArray(param);
        list.clear();
        CdrybEntity[] vbs = (CdrybEntity[]) querySqlForArray(sql, param, CdrybEntity.class);
        if (null != vbs && vbs.length > 0) {
            return vbs[0];
        }
        return null;
    }


    public boolean fileReceiving_Ryxxb(CdrybEntity cdrybEntity) throws DbException {
        String sql = "insert INTO  rsdagl_rydaxxb (xm,sfzhm,xb,lxdh,mz,zzmm,rysf,cjgzsj,bz) VALUE(?,?,?,?,?,?,?,?,?)";
        String[] param = new String[]{cdrybEntity.getXm(), cdrybEntity.getSfzhm(), cdrybEntity.getXb(), cdrybEntity.getLxdh(), cdrybEntity.getMz(), cdrybEntity.getZzmm(), cdrybEntity.getRysf(), cdrybEntity.getCjgzsj(), cdrybEntity.getBz()};
        return this.execSql(sql, param);
    }


    public boolean fileReceiving_daxxb(CdrybEntity cdrybEntity) throws DbException {
        String sql = "insert INTO  rsdagl_zky_cdryb (cdid,grbh,drsj,jfqsrq,dryy,shjg,drdw,dwbh,cdlx,ycddwmc,ycddwxzqh,jsdw) VALUE(?,?,?,?,?,?,?,?,?,?,?,?)";
        String[] param = new String[]{cdrybEntity.getCdid(), cdrybEntity.getGrbh(), cdrybEntity.getDrsj(), cdrybEntity.getJfjzrq(), cdrybEntity.getDryy(), cdrybEntity.getShjg(), cdrybEntity.getDrdw(), cdrybEntity.getDwbh(),
                cdrybEntity.getCdrylb(), cdrybEntity.getYcddwmc(), cdrybEntity.getYcddwxzqh(), cdrybEntity.getJsdw()
        };
        return this.execSql(sql, param);
    }

    public boolean insert_missing_material(CdrybEntity cdrybEntity) throws DbException {
        String sql = "insert INTO  rsdagl_zky_dashqsclgzsb (clid,grbh,sfzhm,qsclx,qtsm,bz,czyid,czybmid,czsj) VALUE(?,?,?,?,?,?,?,?,?)";
        String[] param = new String[]{cdrybEntity.getClid(), cdrybEntity.getGrbh(), cdrybEntity.getSfzhm(), cdrybEntity.getQsclx(), cdrybEntity.getQtsm(), cdrybEntity.getClbz(), cdrybEntity.getCzyid(), cdrybEntity.getCzybmid(), cdrybEntity.getCzsj()};
        return this.execSql(sql, param);
    }

    public List<ReceiveEntity> receiveQuery(CdrybEntity cdrybEntity) throws DbException {

        StringBuilder sql1 = new StringBuilder("SELECT " +
                " a.grbh," +
                " a.xm," +
                " a.sfzhm," +
                " a.ycddwmc as wtcddwmc," +
                " b.cdrq as drrq," +
                " (CASE" +
                " WHEN b.shjg = 01 THEN" +
                " '入库申请' " +
                " WHEN b.shjg = 02 THEN" +
                " '入库申请'" +
                " WHEN b.shjg = 03 THEN" +
                " '退档'" +
                " WHEN b.shjg = 04 THEN" +
                " '撤销申请'" +
                " END) as drzt," +
                " (CASE" +
                " WHEN b.shjg = 02 THEN" +
                " '是'" +
                " ELSE" +
                " '否'" +
                " END) as clqs" +
                " FROM" +
                " rsdagl_rydaxxb a" +
                " LEFT JOIN rsdagl_zky_cdryb b ON a.grbh = b.grbh ");
        ArrayList<String> strings = new ArrayList<>();
        String[] value = new String[strings.size()];
        strings.toArray(value);
        List<ReceiveEntity> list = this.querySqlForList(sql1.toString(), value
                , ReceiveEntity.class);
        return list;

    }

    public boolean repealApplication(CdrybEntity cdrybEntity) throws DbException {
        String sql = "update rsdagl_zky_cdryb set shjg=? where grbh=?";
        String[] param = new String[]{"04", cdrybEntity.getGrbh()};
        return this.execSql(sql, param);
    }

    public boolean writeDispatchLetterUpdate(KjdhjlbEntity kjdhjlbEntity) throws DbException {
        String sql = "update rsdagl_zky_kjdhjlb set sfzhm=?,xm=?,yxq=?,kjhlx=?,lxdh=?,cddwmc=?,ycddwmc=?,blsx=?,blsxpz=? where dhid=?";
        String[] param = new String[]{kjdhjlbEntity.getSfzhm(), kjdhjlbEntity.getXm(), kjdhjlbEntity.getYxq(), kjdhjlbEntity.getKjhlx(), kjdhjlbEntity.getLxdh(), kjdhjlbEntity.getCddwmc(), kjdhjlbEntity.getYcddwmc(),
                kjdhjlbEntity.getBlsx(), kjdhjlbEntity.getBlsxpz(), kjdhjlbEntity.getDhid()};
        return this.execSql(sql, param);
    }

    public List<CodeEntity> queryType() throws DbException {
        StringBuilder sb = new StringBuilder("select * from rsdagl_zky_d_dasdhlx where 1=?");
        ArrayList<String> strings = new ArrayList<>();
        list.add("1");
        strings.addAll(list);
        list.clear();
        String[] value = new String[strings.size()];
        strings.toArray(value);
        List<CodeEntity> list = this.querySqlForList(sb.toString(), value
                , CodeEntity.class);
        return list;
    }

    public List<String> queryDwList() throws DbException {
        StringBuilder sb = new StringBuilder("select dwmc from rsdagl_zky_cddwb where 1=?");
        ArrayList<String> strings = new ArrayList<>();
        list.add("1");
        strings.addAll(list);
        list.clear();
        String[] value = new String[strings.size()];
        strings.toArray(value);
        List<String> list = this.querySqlForList(sb.toString(), value
                , String.class);
        return list;
    }

    public List<CodeEntity> queryHandlingMatters(String dhid) throws DbException {
        StringBuilder sb = new StringBuilder("select * from rsdagl_zky_d_blsx where dhlxid=?");
        list.add(dhid);
        String[] value = new String[list.size()];
        list.toArray(value);
        list.clear();
        List<CodeEntity> list = this.querySqlForList(sb.toString(), value
                , CodeEntity.class);
        return list;
    }

    public List<CodeEntity> queryByMz() throws DbException {
        StringBuilder sb = new StringBuilder("select * from rsdagl_d_mz where 1=?");
        list.add("1");
        String[] value = new String[list.size()];
        list.toArray(value);
        list.clear();
        List<CodeEntity> list = this.querySqlForList(sb.toString(), value
                , CodeEntity.class);
        return list;
    }

    public List<CodeEntity> queryByZzmm() throws DbException {
        StringBuilder sb = new StringBuilder("select * from rsdagl_d_zzmm where 1=?");
        list.add("1");
        String[] value = new String[list.size()];
        list.toArray(value);
        list.clear();
        List<CodeEntity> list = this.querySqlForList(sb.toString(), value
                , CodeEntity.class);
        return list;
    }

    public List<CodeEntity> queryByCdlb() throws DbException {
        StringBuilder sb = new StringBuilder("select * from rsdagl_zky_d_cdlb where 1=?");
        list.add("1");
        String[] value = new String[list.size()];
        list.toArray(value);
        list.clear();
        List<CodeEntity> list = this.querySqlForList(sb.toString(), value
                , CodeEntity.class);
        return list;

    }
}
