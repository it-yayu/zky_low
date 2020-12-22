package com.sx.util;

import lombok.Data;

/**
 * 返回数据封装.
 */
@Data
public class ResultBean {
    private String returnCode;
    private String returnMsg;
    private Object obj;


    public static ResultBean ok(String returnMsg) {
        return new ResultBean("200", returnMsg, null);
    }

    public static ResultBean ok(String returnMsg, Object obj) {
        return new ResultBean("200", returnMsg, obj);
    }

    public static ResultBean error(String returnMsg) {
        return new ResultBean("500", returnMsg, null);
    }

    public static ResultBean error(String returnMsg, Object obj) {
        return new ResultBean("500", returnMsg, obj);
    }

    public ResultBean() {
    }

    private ResultBean(String returnCode, String returnMsg, Object obj) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        this.obj = obj;
    }

    public String getreturnCode() {
        return returnCode;
    }

    public ResultBean setreturnCode(String returnCode) {
        this.returnCode = returnCode;
        return this;
    }

    public String getreturnMsg() {
        return returnMsg;
    }

    public ResultBean setreturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
        return this;
    }

    public Object getObj() {
        return obj;
    }

    public ResultBean setObj(Object obj) {
        this.obj = obj;
        return this;
    }
}
