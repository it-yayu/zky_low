package com.sx.service.yayuService;

import com.sx.dao.yayuDao.ArchiveListDao;
import com.sx.entity.yayuEntity.ArchiveListEntity;
import com.sx.exception.DbException;
import com.sx.util.ListPageUtil;
import com.sx.util.ResultPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yayu
 * @title: ArchiveListService
 * @description: TODO
 * @date 2020/9/2313:17
 */
@Service
public class ArchiveListService {
    @Autowired
    private ArchiveListDao archiveListDao;

    public ResultPageBean getList_all(ArchiveListEntity entity) throws DbException {
        List<ArchiveListEntity> sgqyList = archiveListDao.getList_all(entity);
        int count = null != sgqyList ? sgqyList.size() : 0;
        List list = ListPageUtil.startPage(sgqyList, entity.getPageIndex(), entity.getPageSize());
        return ResultPageBean.ok(count, list);
    }
}
