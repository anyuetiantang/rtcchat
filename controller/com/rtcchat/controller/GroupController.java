package com.rtcchat.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rtcchat.baseController.BaseController;
import com.rtcchat.service.GroupService;
import com.rtcchat.tools.ErrorType;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/group")
public class GroupController extends BaseController{
	private GroupService groupService = null;

	@ResponseBody
	@RequestMapping(value="/groupCreated",method=RequestMethod.POST)
	public String userInfoChange(
			@RequestParam(value="groupName",required=true) String groupName,
			HttpServletRequest request, 
			HttpServletResponse response
			) throws IllegalStateException, IOException{
		
		Map<String,String> map = new HashMap<String,String>();
		HttpSession session = request.getSession();
		int userid = (int)session.getAttribute("userid");
		ErrorType errMsg = groupService.groupCreated(userid, groupName);
		
		map.put("code", errMsg.getCode());
		map.put("msg", errMsg.getMsg());
		JSONObject jsonObj = JSONObject.fromObject(map);
		return jsonObj.toString();
	}
	
	@Resource(name="groupService")
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
}
