package com.sx.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zyt
 * @Date: 2019/6/12
 * @Description: 用来存储登录用户信息
 */
public class LoginCzyMap {

    /**
     * 用来存放登录用户信息
     */
    private static Map<String, String> loginCzys = new ConcurrentHashMap<>();

    /**
     * 存储用户
     *
     * @param dlyhm
     * @param sessionId
     */
    public static void setLoginCzys(String dlyhm, String sessionId) {
        loginCzys.put(dlyhm, sessionId);
    }

    public static Map<String, String> getLoginCzys() {
        return loginCzys;
    }

    /**
     * 移除用户
     *
     * @param sessionId
     */
    public static void removeCzy(String sessionId) {
        for (Map.Entry<String, String> entry : loginCzys.entrySet()) {
            if (sessionId.equals(entry.getValue())) {
                loginCzys.remove(entry.getKey());
                break;
            }
        }
    }

    /**
     * 判读操作员是否登录

     *
     * @param dlyhm     登录用户名

     * @param sessionId sessionId
     * @return
     */
    public static boolean isLogin(String dlyhm, String sessionId) {
        return (loginCzys.containsKey(dlyhm) && sessionId.equals(loginCzys.get(dlyhm)));
    }

}
