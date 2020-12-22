package com.sx.common.message;

/**
 * Created by lrz on 2017/12/7.
 */
public class ReturnInfo {
    public  static final String executeResult = "executeResult";
    public  static final String message = "message";

    public static final String SUCCESS = "1";//操作成功:保存成功，修改成功，删除成功
    public static final String ERROR = "0";//操作失败：保存失败，修改失败，删除失败
    public static final String HAS_RESULT = "1";//操作成功：查询有结果
    public static final String NO_RESULT = "0";//操作失败：查询无结果
}
