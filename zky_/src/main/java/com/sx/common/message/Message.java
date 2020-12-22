package com.sx.common.message;

/**
 * @Author: zyt
 * @Date: 2019/5/28
 * @Description:
 */
public class Message {

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "登录成功";
    /**
     * 登录失败
     */
    public static final String LOGIN_FIELD = "登录失败，用户名或密码错误";
    /**
     * 登录用户名和登录密码不能为空
     */
    public static final String LOGIN_EMPTY = "登录用户名和登录密码不能为空";
    /**
     * 无返回值

     */
    public static final String NO_MESSAGE = "";
    /**
     * 操作成功
     */
    public static final String SUCCESS_MESSAGE = "操作成功";
    /**
     * 操作失败
     */
    public static final String FIELD_MESSAGE = "操作失败";
    /**
     * 登录用户名重复

     */
    public static final String FIELD_INSERT = "该登录用户名已存在";
    /**
     * 原用户名或密码不正确
     */
    public static final String FIELD_LOGIN = "原用户名或密码不正确";
    /**
     * 角色名称重复
     */
    public static final String FIELD_INSERT_ROLE = "该角色名称已存在";

    /**
     * 原密码与新密码相同
     */
    public static final String FIELD_UPDATE_PWD = "原密码与新密码不能相同";

    /**
     * 修改成功
     */
    public static final String SUCCESS_UPDATE = "修改成功";
    /**
     * 请重新确认新密码
     */
    public static final String ERROR_XPWD = "新密码与确认新密码不同";

    /**
     * 原用户名或密码不正确
     */
    public static final String FIELD_UPPWD = "原密码不正确";
}
