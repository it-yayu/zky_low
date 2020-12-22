package com.sx.util;

import lombok.Data;

import java.util.List;

/**
 * 分页返回数据.
 */
@Data
public class ResultPageBean {
    private String returnCode;
    private Integer rowsCount;
    private List<?> data;
    private Object boj;

    public ResultPageBean() {
    }

    public static ResultPageBean ok(Integer rowsCount, List<?> data) {
        return new ResultPageBean(Constants.SUCCESS, rowsCount, data);
    }

    public ResultPageBean(String returnCode, Integer rowsCount, List<?> data) {
        this.returnCode = returnCode;
        this.rowsCount = rowsCount;
        this.data = data;
    }

}
