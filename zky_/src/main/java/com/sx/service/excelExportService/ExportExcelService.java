package com.sx.service.excelExportService;

import cn.hutool.core.util.StrUtil;
import com.sx.conf.SessionConfig;
import com.sx.dao.yayuDao.HrTransferDao;
import com.sx.entity.yayuEntity.KjdhjlbEntity;
import com.sx.entity.yayuEntity.ReceiveEntity;
import com.sx.exception.DbException;
import com.sx.helper.DateHelper;
import com.sx.util.ExcelFormatUtil;
import com.sx.util.FileUtil;
import com.sx.util.ResultBean;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;

/**
 * @author yayu
 * @title: ExportExcelService
 * @description: TODO
 * @date 2020/11/11 10:40
 */
@Service
public class ExportExcelService {
    @Autowired
    private HrTransferDao hrTransferDao;


    public ResultBean queryDispatchLetter_export(HttpServletRequest request, HttpServletResponse response, KjdhjlbEntity entity) throws DbException {
        try {
            SessionConfig sessionConfig = (SessionConfig) request.getSession().getAttribute("sessionConfig");
            List<KjdhjlbEntity> list = null;
            if (StrUtil.isNotEmpty(entity.getFlag())) {
                String flag = entity.getFlag();
                if (flag.endsWith(",")) {
                    entity.setFlag(flag.substring(0, flag.length() - 1));
                }
                list = hrTransferDao.queryDispatchLetterByxh(entity);
            } else {
                list = hrTransferDao.queryDispatchLetter(entity);
            }
            if (null != sessionConfig) {
                list.forEach(li -> li.setCzyid(sessionConfig.getCzyxm()));
            }
            list.forEach(li -> li.setGdsj(DateHelper.strToDateFormat(li.getGdsj())));
            queryDispatchLetter_export_excel(list, response);
            return ResultBean.ok("导出成功");
        } catch (DbException e) {
            e.printStackTrace();
        }
        return ResultBean.error("导出失败");
    }

    private InputStream queryDispatchLetter_export_excel(List<KjdhjlbEntity> list, HttpServletResponse response) {
        HSSFWorkbook wb = new HSSFWorkbook();// 保留1000条数据在内存中
        HSSFSheet sheet = wb.createSheet();
        // 设置报表头样式
//        CellStyle header = ExcelFormatUtil.headSytle(wb);// cell样式
//        CellStyle content = ExcelFormatUtil.contentStyle(wb);// 报表体样式

        // 每一列字段名
        String[] strs = new String[]{
                "姓名",
                "身份证号",
                "委托存档单位名称",
                "开具调函类型",
                "开具日期",
                "操作员",
                "联系方式"
        };

        // 字段名所在表格的宽度
        int[] ints = new int[]{5000, 5000, 5000, 5000, 5000, 5000, 5000};

        // 设置表头样式
//        ExcelFormatUtil.initTitleEX(sheet, header, strs, ints);
        ExcelFormatUtil.initTitleEX2(sheet, null, strs, ints);

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                KjdhjlbEntity entity = list.get(i);
                Row row = sheet.createRow(i + 1);
                int j = 0;
                Cell cell = row.createCell(j++);
                if (null != entity.getXm()) {
                    cell.setCellValue(entity.getXm());
                } else {
                    cell.setCellValue("");
                }

                cell = row.createCell(j++);
                if (null != entity.getSfzhm()) {
                    cell.setCellValue(entity.getSfzhm());
                } else {
                    cell.setCellValue("");
                }

                cell = row.createCell(j++);
                if (null != entity.getWtcddwbh()) {
                    cell.setCellValue(entity.getWtcddwbh());
                } else {
                    cell.setCellValue("");
                }

                cell = row.createCell(j++);
                if (null != entity.getKjhlx()) {
                    cell.setCellValue(entity.getKjhlx());
                } else {
                    cell.setCellValue("");
                }

                cell = row.createCell(j++);
                if (null != entity.getGdsj()) {
                    cell.setCellValue(entity.getGdsj());
                } else {
                    cell.setCellValue("");
                }

                cell = row.createCell(j++);
                if (null != entity.getCzyid()) {
                    cell.setCellValue(entity.getCzyid());
                } else {
                    cell.setCellValue("");
                }

                cell = row.createCell(j++);
                if (null != entity.getLxdh()) {
                    cell.setCellValue(entity.getLxdh());
                } else {
                    cell.setCellValue("");
                }

            }
            System.out.println("遍历完成");
        }
        FileUtil.createFile(response, wb);
        return null;
    }

    public ResultBean repealApplication_export(HttpServletResponse response, ReceiveEntity entity) {
        return null;
    }
}
