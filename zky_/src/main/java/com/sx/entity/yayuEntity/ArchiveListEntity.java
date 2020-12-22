package com.sx.entity.yayuEntity;

import lombok.Data;

/**
 * @author yayu
 * @title: ArchiveListEntity
 * @description: TODO 归档清单实体类
 * @date 2020/9/2313:21
 */
@Data
public class ArchiveListEntity {
    private String yjqybh;
    private String yjqyjc;
    private String sjsd;
    private String sjwc;
    //1市属企业  2央企退休 3区属国企
    private String flag;
    private String nd;
    private int pageIndex;
    private int pageSize;
}
