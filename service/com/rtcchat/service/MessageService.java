package com.rtcchat.service;

import java.util.List;

import com.rtcchat.baseService.BaseService;
import com.rtcchat.entity.GroupMessage;
import com.rtcchat.entity.UserMessage;

public interface MessageService extends BaseService{
	//��ȡ˽����Ϣ(ifNotReadָ�Ƿ���Ҫָ��δ��ȡ)
	public List<UserMessage> getUserMessage(int fromUserId,int toUserId,int number,boolean ifNotRead);

	//��ȡȺ����Ϣ
	public List<GroupMessage> getGroupMessage(int fromUserId,int groupId,int number);
	
	//��˽����Ϣ��δ�����Ը�Ϊ�Ѷ�����
	public void setIfRead(int fromUserId,int toUserId);
	
}
