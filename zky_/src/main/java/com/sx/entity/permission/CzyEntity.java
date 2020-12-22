package com.sx.entity.permission;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @Author: zyt
 * @Date: 2019/5/28
 * @Description: 操作员实体类
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CzyEntity {

    /**
     * 操作员id
     */
    private String czyid;
    /**
     * 操作员姓名

     */
//    @SxLength(min = 1, max = 14, message = "操作员姓名长度不能为0且不能大于14", groups = {BaseValiGroup.class})
//    @Pattern(regexp = PatternValidater.full, message = "操作员姓名包含非法字符", groups = {BaseValiGroup.class})
    private String czyxm;
    /**
     * 操作员登录名
     */
//    @SxLength(min = 1, max = 10, message = "操作员登录名长度不能为0且不能大于10", groups = {BaseValiGroup.class})
//    @Pattern(regexp = PatternValidater.yhm, message = "操作员登录名包含非法字符", groups = {BaseValiGroup.class})
    private String dlyhm;
    /**
     * 登录密码
     */
//    @SxLength(min = 1, max = 32, message = "登录密码长度不能为0且不能大于32", groups = {BaseValiGroup.class, BaseValiGroup1.class})
//    @Pattern(regexp = PatternValidater.less, message = "登录密码包含非法字符", groups = {BaseValiGroup.class, BaseValiGroup1.class})
    private String dlmm;
    /**
     * 新登录密码

     */
//    @SxLength(min = 1, max = 32, message = "新登录密码长度不能为0且不能大于32", groups = {BaseValiGroup1.class})
//    @Pattern(regexp = PatternValidater.less, message = "新登录密码包含非法字符", groups = {BaseValiGroup1.class})
    private String xdlmm;
    /**
     * 所属单位id
     */
    private String ssdwid;
    /**
     * 页数
     */
    private String pageNum;
    /**
     * 系统时间
     */
    private String xtsj;
    /**
     * 操作员id集合
     */
    private String[] czyids;
    /**
     * 角色id集合
     */
    private String[] jsids;
    /**
     * 角色集合
     */
    private JsbEntity[] roles;
    /**
     * 确认新密码
     */
    private String xdlmms;
    /**
     * 单位名称
     */
    private String dwmc;


}
