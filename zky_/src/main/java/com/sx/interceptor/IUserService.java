package com.sx.interceptor;

import com.sx.conf.SessionConfig;
/**
 * 
 * 
 * <p>
 * Title: IUserService
 * </p>
 * <p>
 * Description: 用户接口
 * </p>
 * <p>
 * Company: bksx
 * </p>
 * 
 * @author 殷龙飞
 * @version 1.0
 */
public interface IUserService{
	/**
	 * 根据用户名密码查询用户信息
	 * @param username 用户名
	 * @param password 密码(加密后MD5)
	 * @return sessionConfig 信息
	 * @throws Exception 可能出现的异常
	 */
	public SessionConfig getUserByUserNamePassword(String username,String password)
			throws Exception;
	
	/**
	 * 检查用户的资源权限
	 * @param sessionId 会话ID
	 * @param uId 操作员ID
	 * @param opId 默认值为0000000001
	 * @param obId 资源id
	 * @return 是否有权限
	 * @throws Exception 可能出现的异常
	 */
	public boolean checkUser(String sessionId,String uId,String opId,String obId) throws Exception;
	
	

}
