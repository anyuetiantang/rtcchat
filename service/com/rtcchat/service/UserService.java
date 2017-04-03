package com.rtcchat.service;

import com.rtcchat.baseService.BaseService;
import com.rtcchat.entity.User;
import com.rtcchat.tools.ErrorType;

/**
 * 
 * @author anyuetiantang
 *
 *	user的service层，主要包括了有关于用户的业务逻辑
 */

public interface UserService extends BaseService {
	//用户添加，先查询数据库中是否有同名的存在，无则存储，有则返回已存在的信息
	public ErrorType userAdd(User user);
	
	//用户登录，查询数据库中是否存在用户，存在则登录，不存在则返回不存在的信息
	public ErrorType login(String username,String password);
	
	//用户查询，根据用户的用户名查询用户
	public User findByUsername(String username);
}
