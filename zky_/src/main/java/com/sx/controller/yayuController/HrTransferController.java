package com.sx.controller.yayuController;

import com.sx.entity.yayuEntity.CdrybEntity;
import com.sx.entity.yayuEntity.CodeEntity;
import com.sx.entity.yayuEntity.KjdhjlbEntity;
import com.sx.exception.DbException;
import com.sx.service.yayuService.HrTransferService;
import com.sx.util.CardUtil;
import com.sx.util.ResultBean;
import com.sx.util.ResultPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author yayu
 * @title: HrTransferController
 * @description: TODO 人事调转
 * @date 2020/10/20 11:28
 */
@RestController
@RequestMapping("/hr")
public class HrTransferController {
    @Autowired
    private HrTransferService hrTransferService;

    /**
     * 查询调函类型和调入单位
     *
     * @param
     * @return
     * @throws DbException
     */
    @GetMapping("/queryTypeUnit")
    public ResultBean queryTypeUnit() throws DbException {
        Map<Object, List> map = hrTransferService.queryTypeUnit();
        return ResultBean.ok("查询成功", map);
    }

    /**
     * 查询办理事项
     */

    @GetMapping("/queryHandlingMatters")
    public ResultBean queryHandlingMatters(String dhid) throws DbException {
        List<CodeEntity> codeEntities = hrTransferService.queryHandlingMatters(dhid);
        return ResultBean.ok("查询成功!", codeEntities);
    }


    /**
     * 开具调函.
     *
     * @return
     */
    @RequestMapping("/writeDispatchLetter")
    public ResultBean writeDispatchLetter(@RequestBody KjdhjlbEntity kjdhjlbEntity) throws DbException {
        return hrTransferService.writeDispatchLetter(kjdhjlbEntity);
    }

    /**
     * 开具调函打印页面查询
     *
     * @param kjdhjlbEntity
     * @return
     * @throws DbException
     */

    @RequestMapping("/writeDispatchLetterQuery")
    public KjdhjlbEntity writeDispatchLetter_query(@RequestBody KjdhjlbEntity kjdhjlbEntity) throws DbException {
        return hrTransferService.writeDispatchLetter_query(kjdhjlbEntity);
    }

    /**
     * 开具调函打印页面修改
     *
     * @param kjdhjlbEntity
     * @return
     * @throws DbException
     */

    @RequestMapping("/writeDispatchLetterUpdate")
    public ResultBean writeDispatchLetterUpdate(@RequestBody KjdhjlbEntity kjdhjlbEntity) throws DbException {
        return hrTransferService.writeDispatchLetterUpdate(kjdhjlbEntity);
    }

    /**
     * 根据身份证号码获取信息
     *
     * @param map
     * @return
     * @throws Exception
     */

    @RequestMapping("/queryNameSexByIdCard")
    public Map queryNameSexByIdCard(@RequestBody Map map) throws Exception {
        String idCard = (String) map.get("IdCard");
        return CardUtil.getCarInfo(idCard);
    }


    /**
     * 调函查询
     */
    @RequestMapping("/queryDispatchLetter")
    public ResultPageBean queryDispatchLetter(HttpServletRequest request, @RequestBody KjdhjlbEntity kjdhjlbEntity) throws DbException {
        return hrTransferService.queryDispatchLetter(request, kjdhjlbEntity);
    }

    /**
     * 档案接收根据身份证查询信息回显
     */
    @RequestMapping("/fileReceiveQueryByIdCard")
    public ResultBean fileReceiveQueryByIdCard(@RequestBody KjdhjlbEntity kjdhjlbEntity) throws DbException {
        return hrTransferService.fileReceiveQueryByIdCard(kjdhjlbEntity);
    }

    /**
     * 档案接收下拉框查询
     */
    @RequestMapping("fileReceiveAllDown")
    public ResultBean fileReceiveAllDown() throws DbException {
        return hrTransferService.fileReceiveAllDown();
    }

    /**
     * 档案接收
     */
    @RequestMapping("/fileReceive")
    public ResultBean fileReceiving(@RequestBody CdrybEntity cdrybEntity) throws DbException {
        return hrTransferService.fileReceiving(cdrybEntity);
    }

    /**
     * 接收查询
     */
    @RequestMapping("/receiveQuery")
    public ResultPageBean receiveQuery(@RequestBody CdrybEntity cdrybEntity) throws DbException {
        return hrTransferService.receiveQuery(cdrybEntity);
    }

    /**
     * 撤销申请
     */
    @RequestMapping("repealApplication")
    public ResultBean repealApplication(@RequestBody CdrybEntity cdrybEntity) throws DbException {
        return hrTransferService.repealApplication(cdrybEntity);
    }
}
