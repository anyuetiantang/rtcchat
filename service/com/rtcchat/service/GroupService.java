package com.rtcchat.service;

import com.rtcchat.tools.ErrorType;

/**
 * 
 * @author anyuetiantang
 *
 *	group��service�㣬��Ҫ�������й���Ⱥ���ҵ���߼�
 */

public interface GroupService {
	//����Ⱥ��
	public ErrorType groupCreated(int userid,String groupName);
}
