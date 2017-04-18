package com.rtcchat.service;

import java.util.List;

import com.rtcchat.baseService.BaseService;
import com.rtcchat.entity.GroupMessage;
import com.rtcchat.entity.UserMessage;

public interface MessageService extends BaseService{
	//获取私聊信息(ifNotRead指是否需要指定未读取)
	public List<UserMessage> getUserMessage(int fromUserId,int toUserId,int number,boolean ifNotRead);

	//获取群组信息
	public List<GroupMessage> getGroupMessage(int fromUserId,int groupId,int number);
	
	//将私聊信息的未读属性改为已读属性
	public void setIfRead(int fromUserId,int toUserId);
	
}
