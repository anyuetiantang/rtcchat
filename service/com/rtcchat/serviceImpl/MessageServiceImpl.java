package com.rtcchat.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rtcchat.dao.MessageDao;
import com.rtcchat.service.MessageService;

@Service("messageService")
public class MessageServiceImpl extends BaseServiceImpl implements MessageService{
	private MessageDao messageDao = null;

	@Resource(name="messageDao")
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}
}
