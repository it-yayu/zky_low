package com.sx.controller.systemController;

import com.sx.common.LoginCzyMap;
import com.sx.common.message.Message;
import com.sx.common.message.ReturnInfo;
import com.sx.util.AjaxJsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Author: zyt
 * @Date: 2019/5/28
 * @Description: 登出Controller
 */
@Controller
public class LogoutController {
    /**
     * 登出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    @ResponseBody
    @CrossOrigin
    public AjaxJsonResult logout(HttpServletRequest request) {
        AjaxJsonResult ajaxJsonResult = new AjaxJsonResult();
        //清除Session中的用户信息
        request.getSession().removeAttribute("sessionConf");
        //清除保存的用户登录信息

        LoginCzyMap.removeCzy(request.getSession().getId());
        ajaxJsonResult.setReturnData(new HashMap<String, Object>(2) {{
            put(ReturnInfo.message, Message.NO_MESSAGE);
            put(ReturnInfo.executeResult, ReturnInfo.SUCCESS);
        }});
        return AjaxJsonResult.formatNull(ajaxJsonResult);
    }
}
