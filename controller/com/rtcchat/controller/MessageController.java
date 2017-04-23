package com.rtcchat.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rtcchat.baseController.BaseController;
import com.rtcchat.entity.GroupMessage;
import com.rtcchat.entity.UserMessage;
import com.rtcchat.service.MessageService;
import com.rtcchat.tools.ErrorType;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/message")
public class MessageController extends BaseController{
	private MessageService messageService = null;

	@ResponseBody
	@RequestMapping(value="/getMessage",method=RequestMethod.POST)
	public String getMessage(
			@RequestParam(value="type",required=true) String type,
			@RequestParam(value="sourceId",required=true) int sourceId,
			@RequestParam(value="targetId",required=true) int targetId,
			HttpServletRequest request, 
			HttpServletResponse response
			) throws IllegalStateException, IOException{
		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			if("user".equals(type)){
				List<UserMessage> messageList = messageService.getUserMessage(sourceId,targetId,10,true);
				messageService.setIfRead(sourceId, targetId);//将未读取的消息设为已读取
				map.put("messageList", messageList);
				map.put("sourceId",sourceId);
			}else if("group".equals(type)){
				List<GroupMessage> messageList = messageService.getGroupMessage(sourceId, targetId, 10);
				map.put("messageList", messageList);
				map.put("groupId",targetId);
			}
			map.put("code", ErrorType.ERROR_SUCCESS.getCode());
			map.put("msg", ErrorType.ERROR_SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", ErrorType.ERROR_UNKNOWN.getCode());
			map.put("msg", ErrorType.ERROR_UNKNOWN.getMsg());
		}
		
		JSONObject jsonObj = JSONObject.fromObject(map);
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/getHistoryMessage",method=RequestMethod.POST)
	public String getHistoryMessage(
			@RequestParam(value="type",required=true) String type,
			@RequestParam(value="sourceId",required=true) int sourceId,
			@RequestParam(value="targetId",required=true) int targetId,
			@RequestParam(value="number",required=true) int number,
			HttpServletRequest request, 
			HttpServletResponse response
			) throws IllegalStateException, IOException{
		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			if("user".equals(type)){
				List<UserMessage> messageList = messageService.getUserMessage(sourceId,targetId,number,false);
				messageService.setIfRead(sourceId, targetId);
				map.put("messageList", messageList);
				map.put("sourceId",sourceId);
			}else if("group".equals(type)){
				List<GroupMessage> messageList = messageService.getGroupMessage(sourceId, targetId, number);
				map.put("messageList", messageList);
				map.put("groupId",targetId);
			}
			map.put("code", ErrorType.ERROR_SUCCESS.getCode());
			map.put("msg", ErrorType.ERROR_SUCCESS.getMsg());
		} catch (Exception e) {
			e.printStackTrace();
			map.put("code", ErrorType.ERROR_UNKNOWN.getCode());
			map.put("msg", ErrorType.ERROR_UNKNOWN.getMsg());
		}
		
		JSONObject jsonObj = JSONObject.fromObject(map);
		return jsonObj.toString();
	}
	
	@Resource(name="messageService")
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
}
