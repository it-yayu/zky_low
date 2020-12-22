package com.sx.interceptor;

import org.springframework.stereotype.Repository;
import com.sx.db.AbsDaoSupport;

/**
 * @author windy
 *
 */
@Repository
public class AppLogDAO extends AbsDaoSupport {
    /**
     * 写操作日志
     * 
     * @param czlx
     * @param czff
     * @param czgjzmc
     * @param czgjz
     * @param cznr
     * @param czybmid
     * @param czyid
     * @param czsj
     * @param czjsjip
     * @param hhxh
     * @param appsid
     * @param dlpj
     * @param czyxm
     * @param xzqhdm
     * @param ywxh
     * @param zyid
     * @param yxsj
     * @param czjssj
     */

    public void write(String czlx, String czff, String czgjzmc, String czgjz, String cznr,
            String czybmid, String czyid, String czsj, String czjsjip, String hhxh, String appsid,
            String dlpj, String czyxm, String xzqhdm, String ywxh, String zyid, String czjssj,
            long yxsj) throws Exception {
        // 避免因为内容过长，日志报错的问题
        if (cznr.length() > 4000) {
            cznr = cznr.substring(0, 4000);
        }
        String sql = " insert into kg_czrz (xh,czlx,czff,czgjzmc,czgjz,cznr,czybmid,czyid,czsj,czjsjip,hhxh,appsid,dlpj,czyxm,xzqhdm,ywxh,zyid,czjssj,yxsj) values ";
        sql += " (kg_czrz_xh_seq.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String[] values = new String[18];
        values[0] = czlx;
        values[1] = czff;
        values[2] = czgjzmc;
        values[3] = czgjz;
        values[4] = cznr;
        values[5] = czybmid;
        values[6] = czyid;
        values[7] = czsj;
        values[8] = czjsjip;
        values[9] = hhxh;
        values[10] = appsid;
        values[11] = dlpj;
        values[12] = czyxm;
        values[13] = xzqhdm;
        values[14] = ywxh;
        values[15] = zyid;
        values[16] = czjssj;
        values[17] = String.valueOf(yxsj);
//        this.execSql(sql, values);
    }
}
