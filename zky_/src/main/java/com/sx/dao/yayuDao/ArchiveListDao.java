package com.sx.dao.yayuDao;

import cn.hutool.core.util.StrUtil;
import com.sx.db.AbsDaoSupport;
import com.sx.entity.yayuEntity.ArchiveListEntity;
import com.sx.exception.DbException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yayu
 * @title: ArchiveListDao
 * @description: TODO
 * @date 2020/9/2313:18
 */
@Repository
public class ArchiveListDao extends AbsDaoSupport {


    public List<ArchiveListEntity> getList_all(ArchiveListEntity entity) throws DbException {
        StringBuilder sb = new StringBuilder("SELECT" +
                "  a.yjqybh," +
                "  b.dmmc AS yjqyjc," +
                "  IFNULL(SUM(a.sjsd), 0) AS sjsd," +
                "  IFNULL(SUM(a.sjwc), 0) AS sjwc" +
                "  FROM " +
                "  dahtsys_gqtx a" +
                "  LEFT JOIN dahtsys_d_qydm b ON a.yjqybh = b.dmid where 1=1 ");
        ArrayList<String> strings = new ArrayList<>();
        if (StrUtil.isNotEmpty(entity.getFlag())) {
            if ("1".equals(entity.getFlag())) {
                sb.append(" and yjqybh LIKE ?");
                strings.add("TXXX%");
            }
            if ("2".equals(entity.getFlag())) {
                sb.append(" and yjqybh LIKE ?");
                strings.add("TX99%");
            }
            if ("3".equals(entity.getFlag())) {
                sb.append(" and yjqybh LIKE ?");
                strings.add("TX01%");
            }
        }
        if (StrUtil.isNotEmpty(entity.getNd())) {
            sb.append(" and nd=?");
            strings.add(entity.getNd());
        }

        sb.append(" group by a.yjqybh ");
        String[] value = new String[strings.size()];
        strings.toArray(value);
        List<ArchiveListEntity> list = this.querySqlForList(sb.toString(), value
                , ArchiveListEntity.class);
        return list;
    }


}
