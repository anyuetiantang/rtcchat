package com.rtcchat.service;

import java.util.List;
import java.util.Set;

import com.rtcchat.baseService.BaseService;
import com.rtcchat.entity.Group;
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
	
	//更新数据库用户头像
	public ErrorType updateHeadImg(int userid,String headImg);
	
	//更新用户信息
	public ErrorType updateUserInfo(int userid,User userInfo);
	
	//更新用户密码
	public ErrorType updatePassword(int userid,String passwordOld,String passwordNew);
	
	//获取所有指定用户创建的群组
	public Set<Group> findGroupCreated(int userid);
	
	//获取所有指定用户加入的群组
	public Set<Group> findGroupJoined(int userid);
	
	//获取某用户的好友
	public Set<User> findFriendById(int userId);
	
	//根据传入的字符串进行模糊查询用户
	public List<User> fuzzySearch(String str);
	
	//添加好友
	public ErrorType friendAdd(int userId,int targetId);
	
	//删除好友
	public ErrorType friendDelete(int userId,int targetId);
	
}
