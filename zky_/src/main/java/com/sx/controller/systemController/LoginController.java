package com.sx.controller.systemController;

import com.sx.common.LoginCzyMap;
import com.sx.common.message.Message;
import com.sx.common.message.ReturnInfo;
import com.sx.conf.SessionConfig;
import com.sx.conf.SxAppConfig;
import com.sx.entity.permission.CzyEntity;
import com.sx.entity.permission.GnbEntity;
import com.sx.exception.DbException;
import com.sx.helper.DateHelper;
import com.sx.helper.StringHelper;
import com.sx.service.systemService.CzyService;
import com.sx.util.AjaxJsonResult;
import com.sx.utility.MD5Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/4/25.
 */
@Controller
public class LoginController {

    @Autowired
    private CzyService czyService;

    @PostMapping("/logining")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult login(HttpServletRequest request, @RequestBody CzyEntity entity) throws DbException {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        String dlyhm = StringHelper.toTrim(entity.getDlyhm());
        String dlmm = StringHelper.toTrim(entity.getDlmm());
        //登录用户名和登录密码不能为空
        if (StringHelper.isEmpty(dlyhm) || StringHelper.isEmpty(dlmm)) {
            ajaxJsonResult.setReturnData(new HashMap<String, Object>(2) {{
                put(ReturnInfo.message, Message.LOGIN_EMPTY);
                put(ReturnInfo.executeResult, ReturnInfo.ERROR);
            }});
            return AjaxJsonResult.formatNull(ajaxJsonResult);
        }
        CzyEntity czy = czyService.login(dlyhm, MD5Tools.getStringMD5(MD5Tools.getStringMD5(dlmm) + SxAppConfig.getSALT()));
        //用户名或密码错误
        if (null == czy) {
            ajaxJsonResult.setReturnData(new HashMap<String, Object>(2) {{
                put(ReturnInfo.message, Message.LOGIN_FIELD);
                put(ReturnInfo.executeResult, ReturnInfo.ERROR);
            }});
            return AjaxJsonResult.formatNull(ajaxJsonResult);
        }
        //存储用户信息
        HttpSession session = request.getSession();
        LoginCzyMap.setLoginCzys(czy.getDlyhm(), session.getId());
        //登录成功
        SessionConfig sessionConfig = new SessionConfig();
        sessionConfig.setCzyid(czy.getCzyid());
        sessionConfig.setDwid(czy.getSsdwid());
        sessionConfig.setCzyxm(czy.getCzyxm());
        sessionConfig.setDlyhm(czy.getDlyhm());
        //获取该操作员的模块及链接功能
        GnbEntity[] gns = czyService.getCzyGn(czy.getCzyid(), "0");
        if (null != gns && gns.length > 0) {
            gns = getChildrens(gns, czy.getCzyid());
        }
        //查询全部功能路径
        StringBuffer sb = new StringBuffer();
        GnbEntity[] gnlj = czyService.getCzy2JGn(czy.getCzyid());
        if (null != gnlj && gnlj.length > 0) {
            for (int i = 0; i < gnlj.length; i++) {
                sb.append(gnlj[i].getGnlj() + "|");
            }
        }
        sessionConfig.setGnlj(sb.toString());
        //放入session中
        request.getSession().setAttribute("sessionConfig", sessionConfig);
        GnbEntity[] finalGns = gns;
        ajaxJsonResult.setReturnData(new HashMap<String, Object>(4) {
            {
                //系统时间
                czy.setXtsj(DateHelper.getNow("yyyy-MM-dd"));
                //操作员信息

                put("czyInfo", czy);
                //操作员可用功能

                put("gns", finalGns);
                put(ReturnInfo.message, Message.LOGIN_SUCCESS);
                put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
            }
        });
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }

    /**
     * 递归查询该操作员的功能下的子功能
     *
     * @param gns
     * @param czyid
     * @return
     * @throws DbException
     */
    public GnbEntity[] getChildrens(GnbEntity[] gns, String czyid) throws DbException {
        for (int i = 0; i < gns.length; i++) {
            GnbEntity[] childrens = czyService.getCzyZiGn(czyid, gns[i].getGnid());
            if (childrens != null && childrens.length > 0) {
                gns[i].setChildrens(getChildrens(childrens, czyid));
            }
        }
        return gns;
    }


}
