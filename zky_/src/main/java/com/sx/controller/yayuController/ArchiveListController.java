package com.sx.controller.yayuController;

import com.sx.entity.yayuEntity.ArchiveListEntity;
import com.sx.service.yayuService.ArchiveListService;
import com.sx.util.ResultPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yayu
 * @title: ArchiveListController
 * @description: TODO 归档清单
 * @date 2020/9/23 13:14
 */
@RestController
@RequestMapping("/archive")
@CrossOrigin
public class ArchiveListController {
    @Autowired
    private ArchiveListService archiveListService;

    @PostMapping("/list_all")
    public ResultPageBean getList_all(@RequestBody ArchiveListEntity entity) throws Exception {
        return archiveListService.getList_all(entity);
    }
}
