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
 *	user��service�㣬��Ҫ�������й����û���ҵ���߼�
 */

public interface UserService extends BaseService {
	//�û���ӣ��Ȳ�ѯ���ݿ����Ƿ���ͬ���Ĵ��ڣ�����洢�����򷵻��Ѵ��ڵ���Ϣ
	public ErrorType userAdd(User user);
	
	//�û���¼����ѯ���ݿ����Ƿ�����û����������¼���������򷵻ز����ڵ���Ϣ
	public ErrorType login(String username,String password);
	
	//�û���ѯ�������û����û�����ѯ�û�
	public User findByUsername(String username);
	
	//�������ݿ��û�ͷ��
	public ErrorType updateHeadImg(int userid,String headImg);
	
	//�����û���Ϣ
	public ErrorType updateUserInfo(int userid,User userInfo);
	
	//�����û�����
	public ErrorType updatePassword(int userid,String passwordOld,String passwordNew);
	
	//��ȡ����ָ���û�������Ⱥ��
	public Set<Group> findGroupCreated(int userid);
	
	//��ȡ����ָ���û������Ⱥ��
	public Set<Group> findGroupJoined(int userid);
	
	//��ȡĳ�û��ĺ���
	public Set<User> findFriendById(int userId);
	
	//���ݴ�����ַ�������ģ����ѯ�û�
	public List<User> fuzzySearch(String str);
	
	//��Ӻ���
	public ErrorType friendAdd(int userId,int targetId);
	
	//ɾ������
	public ErrorType friendDelete(int userId,int targetId);
	
}
