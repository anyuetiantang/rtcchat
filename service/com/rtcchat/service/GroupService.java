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
 *	group的service层，主要包括了有关于群组的业务逻辑
 */

public interface GroupService extends BaseService {
	//创建群组
	public ErrorType groupCreated(int userid,String groupName);
	
	//根据群组id获取所有成员
	public Set<User> findMembersById(int groupId);
	
	//群组添加成员
	public ErrorType groupUserAdd(int groupId,int targetId);
	
	//删除群组成员
	public ErrorType groupUserDelete(int groupId,int targetId);
	
	//模糊查询
	public List<Group> fuzzySearch(String targetStr);
	
	//获取群组创建者
	public User findCreator(int groupId);
}
