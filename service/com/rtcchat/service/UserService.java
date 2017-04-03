package com.rtcchat.service;

import com.rtcchat.baseService.BaseService;
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
}
