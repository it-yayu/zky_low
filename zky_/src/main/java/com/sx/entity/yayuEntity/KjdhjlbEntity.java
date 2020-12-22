package com.sx.entity.yayuEntity;

import lombok.Data;

import java.util.List;

/**
 * @author yayu
 * @title: KjdhjlbEntity
 * @description: TODO
 * @date 2020/11/10 11:45
 */
@Data
public class KjdhjlbEntity {
    private String dhid;
    private String dhbh;
    //身份证号码
    private String sfzhm;
    //姓名
    private String xm;
    //联系方式
    private String lxdh;
    //调函类型
    private String kjhlx;
    //调出单位
    private String ycddwmc;
    private String wtcddwbh;
    //调入单位
    private String cddwmc;
    //有效期
    private String yxq;
    private String czyid;
    private String czybmid;
    private String czsj;
    private String sfgd;
    private String gdsj;
    private String sfyx;
    //办理事项
    private String blsx;
    //办理事项批注
    private String blsxpz;

    private List blsxList;

    private String sex;
    private int age;

    private String startTime;

    private String endTime;
    private String flag;

    private int pageNum;
    private int pageSize;
}
