package com.rtcchat.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rtcchat.baseController.BaseController;
import com.rtcchat.service.MessageService;

@Controller
@RequestMapping("/message")
public class MessageController extends BaseController{
	private MessageService messageService = null;

	@Resource(name="messageService")
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
}
