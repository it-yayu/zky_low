package com.sx.interceptor;

import org.springframework.stereotype.Service;

import com.sx.conf.SessionConfig;
import com.sx.spring.exetend.PropertyConfig;
import com.sx.rbac.RbacOperation;
import com.sx.support.rbac.po.UserDetailPO;

/**
 * 
 * 
 * <p>
 * Title: UserService
 * </p>
 * <p>
 * Description:用户服务实现类
 * </p>
 * <p>
 * Company: bksx
 * </p>
 * 
 * @author 殷龙飞
 * @version 1.0
 */
@Service
public class UserService implements IUserService {

    @PropertyConfig
    private String RESTYPE = "1";

    /**
     * 根据用户名密码查询用户信息
     * @param username 用户名
     * @param password 密码(加密后MD5)
     * @return sessionConfig 信息
     * @throws Exception 可能出现的异常
     */
    public SessionConfig getUserByUserNamePassword(String username, String password)
            throws Exception {
        UserDetailPO udp = RbacOperation.login(username, password);
        SessionConfig sessionConf = new SessionConfig();
        sessionConf.setUsername(username);
        sessionConf.setPassword(password);
        sessionConf.setCzyid(udp.getUserId());
        sessionConf.setCzyxm(udp.getUserName());
        sessionConf.setCzylxdh(udp.getUserDPhone());
        sessionConf.setDwid(udp.getUserDId());
        sessionConf.setDwmc(udp.getUserDName());
        sessionConf.setSessionid(udp.getSessionId());

        String userid = sessionConf.getCzyid();
        return sessionConf;
    }

    /**
     * 检查用户的资源权限
     * @param sessionId 会话ID
     * @param uId 操作员ID
     * @param opId 默认值为0000000001
     * @param obId 资源id
     * @return 是否有权限
     * @throws Exception 可能出现的异常
     */
    public boolean checkUser(String sessionId, String uId, String opId, String obId)
            throws Exception {
        return RbacOperation.checkUser(sessionId, uId, "0000000001", obId);
    }

}
