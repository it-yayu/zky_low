package com.sx.service.yayuService;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.sx.common.GenerateId;
import com.sx.conf.SessionConfig;
import com.sx.dao.yayuDao.HrTransferDao;
import com.sx.entity.yayuEntity.*;
import com.sx.exception.DbException;
import com.sx.helper.DateHelper;
import com.sx.util.ListPageUtil;
import com.sx.util.ResultBean;
import com.sx.util.ResultPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author yayu
 * @title: HrTransferController
 * @description: TODO
 * @date 2020/10/20 13:22
 */
@Service
public class HrTransferService {
    @Autowired
    private HrTransferDao hrTransferDao;

    public ResultBean writeDispatchLetter(KjdhjlbEntity kjdhjlbEntity) throws DbException {
        ResultBean resultBean = new ResultBean();
        if ("男".equals(kjdhjlbEntity.getSex())) {
            if (16 > kjdhjlbEntity.getAge() || 60 < kjdhjlbEntity.getAge()) {
                resultBean.setreturnMsg("开具调函人员的年龄范围应为：男16至60岁，女16-50岁！");
            }
        }
        if ("女".equals(kjdhjlbEntity.getSex())) {
            if (16 > kjdhjlbEntity.getAge() || 50 < kjdhjlbEntity.getAge()) {
                resultBean.setreturnMsg("开具调函人员的年龄范围应为：男16至60岁，女16-50岁！");
            }
        }
        kjdhjlbEntity.setDhid(GenerateId.getGenerateId());
        kjdhjlbEntity.setGdsj(DateHelper.fomat8Str(DateUtil.today())); //时间.
        Calendar cal = Calendar.getInstance();
        String year = String.valueOf(cal.get(Calendar.YEAR)).substring(0, 2);
        String s = RandomUtil.randomNumbers(6);
        kjdhjlbEntity.setDhbh("01" + year + s);
        Boolean aBoolean = hrTransferDao.writeDispatchLetter(kjdhjlbEntity);
        if (aBoolean) {
            KjdhjlbEntity entity = writeDispatchLetter_query(kjdhjlbEntity);
            resultBean.setObj(entity);
        }
        resultBean.setreturnCode("200");
        return resultBean;
    }

    public KjdhjlbEntity writeDispatchLetter_query(KjdhjlbEntity kjdhjlbEntity) throws DbException {
        KjdhjlbEntity entity = hrTransferDao.writeDispatchLetter_query(kjdhjlbEntity);
        entity.setGdsj(DateHelper.strToDateFormat(entity.getGdsj()));
        if (StrUtil.isNotEmpty(entity.getBlsx())) {
            String blsxpz = "";
            String[] split = entity.getBlsx().split(",");
            for (int i = 0; i < split.length; i++) {
                String pz = this.hrTransferDao.getCodeName(kjdhjlbEntity.getKjhlx(), split[i]);
//                if (pz.contains("其他:")) {
//                    if (StrUtil.isNotEmpty(entity.getBlsxpz())) {
//                        blsxpz = blsxpz + pz + entity.getBlsxpz() + "~";
//                        continue;
//                    }
//                    blsxpz = blsxpz + pz + "~";
//                }
                if (StrUtil.isNotEmpty(pz)) {
                    blsxpz = blsxpz + pz + "~";
                }
            }
            if (StrUtil.isNotEmpty(blsxpz) && blsxpz.endsWith("~")) {
                blsxpz = blsxpz.substring(0, blsxpz.length() - 1);
            }
            List<String> list = changeStrToList(blsxpz);
            entity.setBlsxList(list);
//            entity.setBlsxpz(blsxpz);
        }
        return entity;
    }

    public ResultPageBean queryDispatchLetter(HttpServletRequest request, KjdhjlbEntity kjdhjlbEntity) throws DbException {
        ResultPageBean resultPageBean = new ResultPageBean();
        SessionConfig sessionConfig = (SessionConfig) request.getSession().getAttribute("sessionConfig");
        List<KjdhjlbEntity> entityList = hrTransferDao.queryDispatchLetter(kjdhjlbEntity);
        int count = null != entityList ? entityList.size() : 0;
        List<KjdhjlbEntity> list = ListPageUtil.startPage(entityList, kjdhjlbEntity.getPageNum(), kjdhjlbEntity.getPageSize());
        if (null != sessionConfig) {
            list.forEach(li -> li.setCzyid(sessionConfig.getCzyxm()));
        }
        List<CodeEntity> typeList = hrTransferDao.queryType();
        list.forEach(li -> li.setGdsj(DateHelper.strToDateFormat(li.getGdsj())));
        resultPageBean.setRowsCount(count);
        resultPageBean.setBoj(new HashMap<Object, List>() {{
            put("typeList", typeList);
            put("AllList", list);
        }});
        resultPageBean.setReturnCode("200");
        return resultPageBean;
    }

    public ResultBean fileReceiveQueryByIdCard(KjdhjlbEntity kjdhjlbEntity) throws DbException {
        if (StrUtil.isNotEmpty(kjdhjlbEntity.getSfzhm())) {
            //根据身份证号码去存档人员中查询看是否存在
            CdrybEntity cdrybEntity = hrTransferDao.fileReceiveQueryByIdCard(kjdhjlbEntity.getSfzhm());
            if (null != cdrybEntity) {
                return ResultBean.ok("该人员已经存档!");
            } else {
                //去暂存人员表中查询
                ZcdabEntity zcdabEntity = hrTransferDao.fileReceiveQueryByIdCard_temp(kjdhjlbEntity.getSfzhm());
                if (null != zcdabEntity) {
                    return ResultBean.ok("暂存人员中数据", zcdabEntity);
                }
            }
        }
        return ResultBean.ok("该人员表中没有记录,请继续填写");
    }

    public ResultBean fileReceiving(CdrybEntity cdrybEntity) throws DbException {
        //人员信息表插入
        if (StrUtil.isNotEmpty(cdrybEntity.getCdrq())) {
            cdrybEntity.setCdrq(DateHelper.fomat8Str(cdrybEntity.getCdrq()));
        }
        boolean b = hrTransferDao.fileReceiving_Ryxxb(cdrybEntity);
        if (b) {
            //根据身份证号码查询档案信息
            CdrybEntity entity = hrTransferDao.queryRsdaglxx(cdrybEntity);
            cdrybEntity.setCdid(entity.getCdid());
            cdrybEntity.setGrbh(entity.getGrbh());
            //档案信息插入
            hrTransferDao.fileReceiving_daxxb(cdrybEntity);
            //判断缺失材料,插入缺失材料
            if (StrUtil.isNotEmpty(cdrybEntity.getShjg())) {
                if ("02".equals(cdrybEntity.getShjg())) {
                    cdrybEntity.setClid(GenerateId.getGenerateId());
                    hrTransferDao.insert_missing_material(cdrybEntity);
                }
            }
        }
        return ResultBean.ok("插入成功");
    }

    public ResultPageBean receiveQuery(CdrybEntity cdrybEntity) throws DbException {
        List<ReceiveEntity> entities = hrTransferDao.receiveQuery(cdrybEntity);
        int count = null != entities ? entities.size() : 0;
        List<ReceiveEntity> list = ListPageUtil.startPage(entities, cdrybEntity.getPageIndex(), cdrybEntity.getPageSize());
        return ResultPageBean.ok(count, list);
    }

    public ResultBean repealApplication(CdrybEntity cdrybEntity) throws DbException {
        boolean b = hrTransferDao.repealApplication(cdrybEntity);
        if (b) {
            return ResultBean.ok("撤销成功!");
        }
        return ResultBean.error("撤销失败!");
    }

    public ResultBean writeDispatchLetterUpdate(KjdhjlbEntity kjdhjlbEntity) throws DbException {
        boolean b = hrTransferDao.writeDispatchLetterUpdate(kjdhjlbEntity);
        if (b) {
            return ResultBean.ok("修改成功");
        }
        return ResultBean.error("修改失败");
    }

    public Map<Object, List> queryTypeUnit() throws DbException {
        //查询调函类型
        List<CodeEntity> typeList = hrTransferDao.queryType();
        //存档单位
        List<String> dwList = hrTransferDao.queryDwList();
        return new HashMap<Object, List>() {{
            put("typeList", typeList);
            put("dwList", dwList);
        }};
    }

    public List<CodeEntity> queryHandlingMatters(String dhid) throws DbException {
        return hrTransferDao.queryHandlingMatters(dhid);
    }


    private List<String> changeStrToList(String str) {
        if (StrUtil.isNotEmpty(str)) {
            String[] split = str.split("~");
            return new ArrayList<>(Arrays.asList(split));
        }
        return new ArrayList<>();
    }

    public ResultBean fileReceiveAllDown() throws DbException {
        ResultBean resultBean = new ResultBean();
        //民族
        List<CodeEntity> mzList = hrTransferDao.queryByMz();
        //政治面貌
        List<CodeEntity> zzmmList = hrTransferDao.queryByZzmm();
        //人员身份

        //存档类别
        List<CodeEntity> cdlbList = hrTransferDao.queryByCdlb();
        resultBean.setreturnCode("200");
        resultBean.setObj(new HashMap<String, Object>() {{
            put("mzList", mzList);
            put("zzmmList", zzmmList);
            put("cdlbList", cdlbList);
            put("rysfList", null);
        }});


        return resultBean;
    }
}
