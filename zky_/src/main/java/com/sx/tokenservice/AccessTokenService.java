package com.sx.tokenservice;

import com.sx.rbac.RbacOperation;
import com.sx.rbac.util.RbacStatics;
import com.sx.support.rbac.conf.OAuthClientConfig;
import net.sf.json.JSONObject;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.impl.JobExecutionContextImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by Administrator on 2017/1/12.
 */
public class AccessTokenService extends QuartzJobBean {
    private static final Logger log;
    private static String ACCESS_TOKEN;
    private static String ACCESS_TOKEN_FULLINFO;

    public AccessTokenService() {
    }

    protected void executeInternal(JobExecutionContext var1) throws JobExecutionException {
        JobDataMap var2 = ((JobExecutionContextImpl)var1).getJobDetail().getJobDataMap();
        String var3 = var2.getString("scope");
//        String var4 = init(var3);
//        dealToken(var4);
    }

    private static void dealToken(String var0) {
        ACCESS_TOKEN_FULLINFO = var0;
        JSONObject var1 = JSONObject.fromObject(var0);
        ACCESS_TOKEN = var1.getString("access_token");
        RbacStatics.ACCESS_TOKEN = ACCESS_TOKEN;
        log.info("context access_token init success, token is {}", ACCESS_TOKEN);
    }

    private static String init(String var0) {
        String var1 = RbacOperation.getCode(OAuthClientConfig.getClientId(), var0);
        log.info("code is {}", var1);
        String var2 = RbacOperation.getAccessTokenByPlat(var1, OAuthClientConfig.getClientId(), OAuthClientConfig.getClientSecret());
        log.info("token is {}", var2);
        return var2;
    }

    public static String getACCESS_TOKEN() {
        return ACCESS_TOKEN;
    }

    public static String getACCESS_TOKEN_FULLINFO() {
        return ACCESS_TOKEN_FULLINFO;
    }

    public static String refreshToken(String var0) {
        String var1 = init(var0);
        dealToken(var1);
        RbacStatics.ACCESS_TOKEN = ACCESS_TOKEN;
        return ACCESS_TOKEN;
    }

    static {
        log = LoggerFactory.getLogger(AccessTokenService.class);
        ACCESS_TOKEN = "";
        ACCESS_TOKEN_FULLINFO = "";
    }
}
