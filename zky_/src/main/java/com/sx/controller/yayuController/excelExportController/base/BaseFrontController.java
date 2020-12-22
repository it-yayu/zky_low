package com.sx.controller.yayuController.excelExportController.base;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yayu
 * @title: BaseFrontController
 * @description: TODO 一个通用的文件下载controller
 * @date 2020/7/2716:25
 */
@Validated
public class BaseFrontController {

    /**
     * 下载文件,纯SpringMVC的API来完成
     *
     * @param is   文件输入流
     * @param name 文件名称,带后缀名
     * @throws Exception
     */
    public ResponseEntity<byte[]> buildResponseEntity(InputStream is, String name) throws Exception {
        HttpHeaders header = new HttpHeaders();
        String fileSuffix = name.substring(name.lastIndexOf('.') + 1);
        fileSuffix = fileSuffix.toLowerCase();

        Map<String, String> arguments = new HashMap<String, String>();
//        arguments.put("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

//        arguments.put("xls", "application/vnd.ms-excel");
        arguments.put("xls", "application/vnd.ms-excel;charset=gb2312");

        String contentType = arguments.get(fileSuffix);
        header.add("Content-Type", (StringUtils.hasText(contentType) ? contentType : "application/x-download"));
        if (is != null && is.available() != 0) {
            header.add("Content-Length", String.valueOf(is.available()));
            header.add("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(name, "UTF-8"));
            byte[] bs = IOUtils.toByteArray(is);
            return new ResponseEntity<>(bs, header, HttpStatus.OK);
        } else {
            String string = "数据为空";
            header.add("Content-Length", "0");
            header.add("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(name, "UTF-8"));
            return new ResponseEntity<>(string.getBytes(), header, HttpStatus.OK);
        }
    }

}
