package com.sx.controller.systemController;

import com.sx.common.message.Message;
import com.sx.common.message.ReturnInfo;
import com.sx.common.validater.EntityValidater;
import com.sx.entity.permission.GnbEntity;
import com.sx.exception.DbException;
import com.sx.service.systemService.GnbService;
import com.sx.util.AjaxJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @Author: zyt
 * @Date: 2019/5/28
 * @Description: 功能Controller
 */

@Controller
@RequestMapping("/xtwh/gn")
public class GnbController extends EntityValidater {
    /**
     * 功能Service
     */
    @Autowired
    private GnbService gnbService;

    /**
     * 查询功能树

     *
     * @param entity
     * @return
     * @throws DbException
     */
    @GetMapping("/listGnTree")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult listGn(GnbEntity entity) throws DbException {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        GnbEntity[] gns = gnbService.get3JGnTree(entity.getGnid());
//        if (null != gns && gns.length > 0) {
//            for (int i = 0; i < gns.length; i++) {
//                String one = Integer.valueOf(gns[i].getGnlx()) + 1 + "-" + gns[i].getXh();
//                gns[i].setXh2(one);
//                GnbEntity[] childrens = gns[i].getChildrens();
//                if(null != childrens && childrens.length > 0) {
//                    for (int j = 0; j < childrens.length; j++) {
//                        childrens[j].setXh2(one + "-" + childrens[j].getXh());
//                    }
//                }
//
//            }
//        }
        ajaxJsonResult.setReturnData(new HashMap<String, Object>(3) {{
            put("gnlist", gns);
            put(ReturnInfo.message, Message.NO_MESSAGE);
            put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
        }});
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }

    /**
     * 查询功能
     *
     * @return
     * @throws DbException
     */
    @GetMapping("/listUpGn")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult listGn() throws DbException {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        GnbEntity[] gns = gnbService.getUpGn();
        ajaxJsonResult.setPageCount(String.valueOf(gns.length));
        ajaxJsonResult.setReturnData(new HashMap<String, Object>(3) {{
            put("gnlist", gns);
            put(ReturnInfo.message, Message.NO_MESSAGE);
            put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
        }});
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }

    /**
     * 新增功能
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult addGn(@RequestBody  GnbEntity entity, Errors errors) throws Exception {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        //校验输入项

        if (!checkError(errors, ajaxJsonResult)) {
            return AjaxJsonResult.formatNull(ajaxJsonResult);
        }
        gnbService.insertGn(entity);
        ajaxJsonResult.setReturnData(new HashMap<String, Object>() {{
            put(ReturnInfo.message, Message.SUCCESS_MESSAGE);
            put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
        }});
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }

    /**
     * 更新功能
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @PostMapping("/update")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult updateGn(@RequestBody GnbEntity entity, Errors errors) throws Exception {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        //校验输入项

        if (!checkError(errors, ajaxJsonResult)) {
            return AjaxJsonResult.formatNull(ajaxJsonResult);
        }
        gnbService.updateGn(entity);
        ajaxJsonResult.setReturnData(new HashMap<String, Object>() {{
            put(ReturnInfo.message, Message.SUCCESS_MESSAGE);
            put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
        }});
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }

    /**
     * 删除功能
     *
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult deleteGn(@RequestBody GnbEntity entity) throws DbException {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        boolean flag = gnbService.deleteGn(entity.getGnid());
        ajaxJsonResult.setReturnData(new HashMap<String, Object>() {{
            if (true == flag) {
                put(ReturnInfo.message, Message.SUCCESS_MESSAGE);
                put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
            } else {
                put(ReturnInfo.message, Message.FIELD_MESSAGE);
                put(ReturnInfo.executeResult, ReturnInfo.ERROR);
            }
        }});
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }
}
