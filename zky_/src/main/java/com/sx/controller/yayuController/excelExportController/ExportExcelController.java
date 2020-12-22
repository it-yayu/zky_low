package com.sx.controller.yayuController.excelExportController;

import com.sx.entity.yayuEntity.KjdhjlbEntity;
import com.sx.entity.yayuEntity.ReceiveEntity;
import com.sx.exception.DbException;
import com.sx.service.excelExportService.ExportExcelService;
import com.sx.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author yayu
 * @title: ExportExcelController
 * @description: TODO
 * @date 2020/11/11 10:37
 */
@RestController
@RequestMapping("/excelExport")
public class ExportExcelController {
    @Autowired
    private ExportExcelService excelService;

    /**
     * 调函查询导出
     *
     * @param response
     * @param entity
     * @return
     * @throws DbException
     */
    @RequestMapping("/queryDispatchLetter_export")
    public ResultBean exportExcel(HttpServletRequest request, HttpServletResponse response, @RequestBody KjdhjlbEntity entity) throws DbException {
        return excelService.queryDispatchLetter_export(request, response, entity);

    }

    /**
     * 档案接收查询导出
     */
    @RequestMapping("/repealApplication_export")
    public ResultBean repealApplication_export(HttpServletResponse response, @RequestBody ReceiveEntity entity) throws DbException {
        return excelService.repealApplication_export(response, entity);

    }
}
