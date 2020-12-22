package com.sx.controller.systemController;

import com.sx.common.message.Message;
import com.sx.common.message.ReturnInfo;
import com.sx.common.validater.EntityValidater;
import com.sx.conf.SessionConfig;
import com.sx.entity.permission.GnbEntity;
import com.sx.entity.permission.JsbEntity;
import com.sx.exception.DbException;
import com.sx.service.systemService.GnbService;
import com.sx.service.systemService.JsbService;
import com.sx.util.AjaxJsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: zyt
 * @Date: 2019/5/28
 * @Description: 角色Controller
 */
@Controller
@RequestMapping("/xtwh/js")
public class JsbController extends EntityValidater {

    @Autowired
    private JsbService jsbService;
    @Autowired
    private GnbService gnbService;

    /**
     * 获得下拉的角色列表
     *
     * @return
     */
    @GetMapping("/listAllJs")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult listAllJs() throws DbException {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        JsbEntity[] vbs = jsbService.getAllJs();
        ajaxJsonResult.setReturnData(new HashMap<String, Object>() {{
            put("jslist", vbs);
            put(ReturnInfo.message, Message.NO_MESSAGE);
            put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
        }});
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }


    /**
     * 动态查询角色
     *
     * @return
     * @throws DbException
     */
    @GetMapping("/list")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult listJs(HttpServletRequest request) throws DbException {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        SessionConfig sessionConfig = (SessionConfig) request.getSession().getAttribute("sessionConfig");
        if (null == sessionConfig) {
            if (sessionConfig == null) {
                ajaxJsonResult = new AjaxJsonResult(AjaxJsonResult.CODE_TIMEOUT);
                return AjaxJsonResult.formatNull(ajaxJsonResult);
            }
        }
        JsbEntity[] vbs = jsbService.getJs(sessionConfig.getDlyhm());
        ajaxJsonResult.setReturnData(new HashMap<String, Object>() {{
            put("jslist", vbs);
            put(ReturnInfo.message, Message.NO_MESSAGE);
            put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
        }});
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }

    /**
     * 新增角色
     *
     * @param entity
     * @return
     */
    @PostMapping("/add")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult addJs(@RequestBody JsbEntity entity, Errors errors) throws Exception {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        //校验输入项

        if (!checkError(errors, ajaxJsonResult)) {
            return AjaxJsonResult.formatNull(ajaxJsonResult);
        }
        boolean flag = jsbService.insertJs(entity);
        ajaxJsonResult.setReturnData(new HashMap<String, Object>() {{
            if (flag) {
                put(ReturnInfo.message, Message.SUCCESS_MESSAGE);
                put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
            } else {
                put(ReturnInfo.message, Message.FIELD_INSERT_ROLE);
                put(ReturnInfo.executeResult, ReturnInfo.ERROR);
            }

        }});
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }

    /**
     * 更新角色
     *
     * @param entity
     * @return
     */
    @PostMapping("/update")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult updateJs(@RequestBody JsbEntity entity, Errors errors) throws Exception {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        //校验输入项

        if (!checkError(errors, ajaxJsonResult)) {
            return AjaxJsonResult.formatNull(ajaxJsonResult);
        }
        boolean flag = jsbService.updateJs(entity);
        ajaxJsonResult.setReturnData(new HashMap<String, Object>() {{
            if (flag) {
                put(ReturnInfo.message, Message.SUCCESS_MESSAGE);
                put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
            } else {
                put(ReturnInfo.message, Message.FIELD_INSERT_ROLE);
                put(ReturnInfo.executeResult, ReturnInfo.ERROR);
            }
        }});
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }

    /**
     * 删除角色
     *
     * @param entity
     * @return
     * @throws DbException
     */
    @PostMapping("/delete")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult deleteJs(@RequestBody JsbEntity entity) throws DbException {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        boolean flag = jsbService.deleteJs(entity.getJsid());
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
     * 为角色添加功能
     *
     * @param entity
     * @return
     */

    @PostMapping("/addGnForJs")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult addGnForJs(HttpServletRequest request, @RequestBody JsbEntity entity) throws Exception {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        SessionConfig sessionConfig = (SessionConfig) request.getSession().getAttribute("sessionConfig");
        if (null == sessionConfig) {
            if (sessionConfig == null) {
                ajaxJsonResult = new AjaxJsonResult(AjaxJsonResult.CODE_TIMEOUT);
                return AjaxJsonResult.formatNull(ajaxJsonResult);
            }
        }
        //为角色新增功能
//       for (int i=0;i<entity.getGnids().length;i++){
//            if ("fb371448bcd501".equals(entity.getGnids()[i])){
//                boolean flag=true;
//                for (int j=0;j<entity.getGnids().length;j++){
//                    if ("fb371415b54700".equals(entity.getGnids()[j])){
//                        flag=false;
//                    }
//                }
//                if (flag){
//                    ajaxJsonResult.setReturnData(new HashMap<String, Object>() {{
//                        put(ReturnInfo.message, Message.FIELD_MESSAGE);
//                        put(ReturnInfo.executeResult, "请选择影像信息功能");
//                    }});
//                    return AjaxJsonResult.formatNull(ajaxJsonResult);
//                }
//            }
//           if ("fb371415b54700".equals(entity.getGnids()[i])){
//               boolean flag=true;
//               for (int j=0;j<entity.getGnids().length;j++){
//                   if ("fb3724159d7b00".equals(entity.getGnids()[j])){
//                       flag=false;
//                   }
//               }
//               if (flag){
//                   ajaxJsonResult.setReturnData(new HashMap<String, Object>() {{
//                       put(ReturnInfo.message, Message.FIELD_MESSAGE);
//                       put(ReturnInfo.executeResult, "请选择档案查询功能");
//                   }});
//                   return AjaxJsonResult.formatNull(ajaxJsonResult);
//               }
//           }
//       }

        jsbService.addJsForGn(entity);
        //查询当前用户的角色列表

        String[] jsids = jsbService.getCzyJs(sessionConfig.getCzyid());
        ajaxJsonResult.setReturnData(new HashMap<String, Object>() {{
            put(ReturnInfo.message, Message.SUCCESS_MESSAGE);
            put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
            put("jsids", jsids);
        }});
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }

    /**
     * 删除角色功能
     *
     * @param entity
     * @return
     * @throws DbException
     */
    /*@PostMapping("/deleteGnFromJs")
    @ResponseBody
    public AjaxJsonResult deleteGnFromJs(@RequestBody GnJsDybEntity entity) throws DbException {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        boolean flag = jsbService.deleteGnFromJs(entity);
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
    }*/

    /**
     * 获取角色的功能权限
     *
     * @param entity
     * @return
     * @throws DbException
     */
    @GetMapping("/getJsGn")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult getJsGn(JsbEntity entity) throws DbException {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        //获取功能树
        GnbEntity[] gnTree = gnbService.get2JGnTree(null);
        //更新 该操作员的模块功能及子功能
        //todo 修改,调换了接口,符合项目要求
        List<String> gnids = jsbService.getAllGnId(entity.getJsid());
        ajaxJsonResult.setReturnData(new HashMap<String, Object>() {
            {
                put("gnTree", gnTree);
                put("gnids", gnids);
                put(ReturnInfo.message, Message.NO_MESSAGE);
                put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
            }
        });
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }
}
