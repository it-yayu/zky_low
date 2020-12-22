package com.sx.controller.systemController;

import com.sx.common.message.Message;
import com.sx.common.message.ReturnInfo;
import com.sx.common.validater.EntityValidater;
import com.sx.conf.SessionConfig;
import com.sx.entity.permission.CzyEntity;
import com.sx.entity.permission.CzypwdEntity;
import com.sx.exception.DbException;
import com.sx.service.systemService.CzyService;
import com.sx.util.AjaxJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zyt
 * @Date: 2019/5/28
 * @Description: 操作员Controller
 */
@Controller
@RequestMapping("/xtwh/czy")
public class CzyController extends EntityValidater {

    /**
     * 操作员Service
     */
    @Autowired
    private CzyService czyService;

    /**
     * 查询单位下的所有操作员
     *
     * @param entity
     * @return
     * @throws DbException
     */
    @GetMapping("/list")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult getCzyByDwid(HttpServletRequest request, CzyEntity entity) throws DbException {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        String id = request.getSession().getId();
        System.out.println(id);
        SessionConfig sessionConfig = (SessionConfig) request.getSession().getAttribute("sessionConfig");
        if (null == sessionConfig) {
            if (sessionConfig == null) {
                ajaxJsonResult = new AjaxJsonResult(AjaxJsonResult.CODE_TIMEOUT);
                return AjaxJsonResult.formatNull(ajaxJsonResult);
            }
        }
        CzyEntity[] czys = czyService.getCzyByDwid(entity, sessionConfig.getDlyhm());
        ajaxJsonResult.setReturnData(new HashMap<String, Object>(3) {{
            put("czylist", czys);
            put(ReturnInfo.message, Message.NO_MESSAGE);
            put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
        }});
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }

    /**
     * 查询某一操作员
     *
     * @param entity
     * @return
     * @throws DbException
     */
    @GetMapping("/getCzyById")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult getCzyById(CzyEntity entity) throws DbException {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        CzyEntity czy = czyService.getCzyById(entity.getCzyid());
        ajaxJsonResult.setReturnData(new HashMap<String, Object>(3) {{
            put("czy", czy);
            put(ReturnInfo.message, Message.NO_MESSAGE);
            put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
        }});
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }

    /**
     * 根据操作员姓名，登陆员号码，单位名称查询对应的操作员
     * @param entity
     * @return
     * @throws DbException
     */
    @GetMapping("/selczy")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult selczy(CzypwdEntity entity, HttpServletRequest request, Errors errors) throws Exception {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        //校验输入项
        if (!checkError(errors, ajaxJsonResult)) {
            return AjaxJsonResult.formatNull(ajaxJsonResult);
        }
        SessionConfig session = (SessionConfig) request.getSession().getAttribute("sessionConfig");
        if (null == session) {
            ajaxJsonResult.setLoginTimeOut();
            return AjaxJsonResult.formatNull(ajaxJsonResult);
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (null==entity){
            map.put(ReturnInfo.executeResult, ReturnInfo.ERROR);
            map.put(ReturnInfo.message, "请输入查询条件");
            return AjaxJsonResult.formatNull(ajaxJsonResult);
        }
        if (!"管理员".equals(session.getCzyxm())||!"fb48228abcc501".equals(session.getCzyid())){
            map.put(ReturnInfo.executeResult, ReturnInfo.ERROR);
            map.put(ReturnInfo.message, "当前登陆人员无权限进行此操作，请联系管理员");
            ajaxJsonResult.setReturnData(map);
            return AjaxJsonResult.formatNull(ajaxJsonResult);
        }
        CzyEntity[] czys=czyService.getczyxx(entity);
        if (czys==null){
            czys=new CzyEntity[0];
        }
        ajaxJsonResult.setPageInfo(this.czyService.getStartNum(), this.czyService.getPageCount(), this.czyService.getRowsNum());
        map.put("czys",czys);
        map.put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
        map.put(ReturnInfo.message, "查询成功");
        ajaxJsonResult.setReturnData(map);
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }




    /**
     * 根据id删除操作员
     *
     * @param entity
     * @return
     * @throws DbException
     */
    @PostMapping("/delete")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult deleteCzyById(@RequestBody CzyEntity entity) throws DbException {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
//        UserPersission check = new UserPersission();
//        boolean check1 = check.check("/xtwh/czy/delete", entity.getCzyid());
//        if(!check1){
//            ajaxJsonResult.setCodeAndMsg(AjaxJsonResult.CODE_ERROR,"该用户没有此功能权限!");
//        }
        boolean flag = czyService.deleteCzyById(entity.getCzyid());
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

    /**
     * 新增操作员
     *
     * @param entity
     * @param errors
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult addCzy(@RequestBody CzyEntity entity, Errors errors) throws Exception {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        //校验输入项
        if (!checkError(errors, ajaxJsonResult)) {
            return AjaxJsonResult.formatNull(ajaxJsonResult);
        }
        boolean flag = czyService.insertCzy(entity);
        ajaxJsonResult.setReturnData(new HashMap<String, Object>() {{
            if (true == flag) {
                put(ReturnInfo.message, Message.SUCCESS_MESSAGE);
                put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
            } else {
                put(ReturnInfo.message, Message.FIELD_INSERT);
                put(ReturnInfo.executeResult, ReturnInfo.ERROR);
            }
        }});
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }

    /**
     * 更新操作员信息
     *
     * @param entity
     * @param errors
     * @return
     * @throws IllegalAccessException
     * @throws DbException
     * @throws InvocationTargetException
     */
    @PostMapping("/update")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult updateCzy(@RequestBody CzyEntity entity, Errors errors) throws IllegalAccessException, DbException, InvocationTargetException {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        //校验数据
        if (!checkError(errors, ajaxJsonResult)) {
            return AjaxJsonResult.formatNull(ajaxJsonResult);
        }
        czyService.updateCzy(entity);
        ajaxJsonResult.setReturnData(new HashMap<String, Object>(2) {{
            put(ReturnInfo.message, Message.SUCCESS_MESSAGE);
            put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
        }});
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }

    /**
     * 修改密码
     *
     * @param request
     * @param entity
     * @return
     */
    @PostMapping("/changePwd")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult changePwd(HttpServletRequest request, @RequestBody CzyEntity entity, Errors errors) throws DbException {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        SessionConfig sessionConfig = (SessionConfig) request.getSession().getAttribute("sessionConfig");
        if (null == sessionConfig) {
            if (sessionConfig == null) {
                ajaxJsonResult = new AjaxJsonResult(AjaxJsonResult.CODE_TIMEOUT);
                return AjaxJsonResult.formatNull(ajaxJsonResult);
            }
        }
        //校验数据
        if (!checkError(errors, ajaxJsonResult)) {
            return AjaxJsonResult.formatNull(ajaxJsonResult);
        }
        boolean flag = czyService.changePwd(entity, sessionConfig.getCzyid());
        ajaxJsonResult.setReturnData(new HashMap<String, Object>() {
            {
                if (true == flag) {
                    if (entity.getDlmm().equals(entity.getXdlmm())) {
                        put(ReturnInfo.message, Message.FIELD_UPDATE_PWD);
                        put(ReturnInfo.executeResult, ReturnInfo.ERROR);
                    } else if (!entity.getXdlmm().equals(entity.getXdlmms())) {
                        put(ReturnInfo.message, Message.ERROR_XPWD);
                        put(ReturnInfo.executeResult, ReturnInfo.ERROR);
                    } else {
                        put(ReturnInfo.message, Message.SUCCESS_UPDATE);
                        put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
                    }
                } else {
                    put(ReturnInfo.message, Message.FIELD_UPPWD);
                    put(ReturnInfo.executeResult, ReturnInfo.ERROR);
                }
            }
        });
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }

    /**
     * 为操作员赋角色
     *
     * @param entity
     * @return
     * @throws Exception
     */
    @PostMapping("/addJsForCzy")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult addJsForCzy(@RequestBody CzyEntity entity) throws Exception {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        //为操作员赋角色
        czyService.insertJsForCzy(entity);
        ajaxJsonResult.setReturnData(new HashMap() {{
            put(ReturnInfo.message, Message.SUCCESS_MESSAGE);
            put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
        }});
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }

    /**
     * 重置密码
     *
     * @param entity
     * @return
     * @throws DbException
     */
    @PostMapping("/czmm")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult czmm(@RequestBody CzypwdEntity entity, HttpServletRequest request, Errors errors) throws Exception {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        //校验输入项
        if (!checkError(errors, ajaxJsonResult)) {
            return AjaxJsonResult.formatNull(ajaxJsonResult);
        }
        SessionConfig session = (SessionConfig) request.getSession().getAttribute("sessionConfig");
        if (null == session) {
            ajaxJsonResult.setLoginTimeOut();
            return AjaxJsonResult.formatNull(ajaxJsonResult);
        }
        Map<String,  Object> map = new HashMap<String, Object>();
        if (null == entity) {
            map.put(ReturnInfo.executeResult, ReturnInfo.ERROR);
            map.put(ReturnInfo.message, "请选择重置对象");
            ajaxJsonResult.setReturnData(map);
            return AjaxJsonResult.formatNull(ajaxJsonResult);
        }
        if (!"管理员".equals(session.getCzyxm()) || !"fb48228abcc501".equals(session.getCzyid())) {
            map.put(ReturnInfo.executeResult, ReturnInfo.ERROR);
            map.put(ReturnInfo.message, "当前登陆人员无权限进行此操作，请联系管理员");
            ajaxJsonResult.setReturnData(map);
            return AjaxJsonResult.formatNull(ajaxJsonResult);
        }
        boolean flag = czyService.czmm(entity);
        if (flag) {
            map.put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
            map.put(ReturnInfo.message, "重置成功");
        } else {
            map.put(ReturnInfo.executeResult, ReturnInfo.ERROR);
            map.put(ReturnInfo.message, "重置失败");
        }
        ajaxJsonResult.setReturnData(map);
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }


}
