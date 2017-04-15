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
 *	group��service�㣬��Ҫ�������й���Ⱥ���ҵ���߼�
 */

public interface GroupService extends BaseService {
	//����Ⱥ��
	public ErrorType groupCreated(int userid,String groupName);
	
	//����Ⱥ��id��ȡ���г�Ա
	public Set<User> findMembersById(int groupId);
	
	//Ⱥ����ӳ�Ա
	public ErrorType groupUserAdd(int groupId,int targetId);
	
	//ɾ��Ⱥ���Ա
	public ErrorType groupUserDelete(int groupId,int targetId);
	
	//ģ����ѯ
	public List<Group> fuzzySearch(String targetStr);
	
	//��ȡȺ�鴴����
	public User findCreator(int groupId);
}
