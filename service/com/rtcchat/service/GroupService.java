package com.rtcchat.service;

import com.rtcchat.tools.ErrorType;

/**
 * 
 * @author anyuetiantang
 *
 *	group的service层，主要包括了有关于群组的业务逻辑
 */

public interface GroupService {
	//创建群组
	public ErrorType groupCreated(int userid,String groupName);
}
