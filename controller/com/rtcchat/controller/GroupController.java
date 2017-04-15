package com.rtcchat.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.rtcchat.entity.Group;
import com.rtcchat.entity.User;
import com.rtcchat.service.GroupService;
import com.rtcchat.tools.ErrorType;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/group")
public class GroupController extends BaseController{
	private GroupService groupService = null;

	@ResponseBody
	@RequestMapping(value="/groupCreated",method=RequestMethod.POST)
	public String groupCreated(
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
	
	@ResponseBody
	@RequestMapping(value="/findGroupMembers",method=RequestMethod.POST)
	public String findGroupMembers(
			@RequestParam(value="groupId",required=true) int groupId,
			HttpServletRequest request, 
			HttpServletResponse response
			) throws IllegalStateException, IOException{
		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			Set<User> members = groupService.findMembersById(groupId);
			map.put("members", members);
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
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String delete(
			@RequestParam(value="groupId",required=true) int groupId,
			HttpServletRequest request, 
			HttpServletResponse response
			) throws IllegalStateException, IOException{
		
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			groupService.deleteById(Group.class, groupId);
			
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
	@RequestMapping(value="/fuzzySearch",method=RequestMethod.POST)
	public String fuzzySearch(
			@RequestParam(value="targetStr",required=true) String targetStr,
			HttpServletRequest request, 
			HttpServletResponse response
			) throws IllegalStateException, IOException{
		
		Map<String,Object> map = new HashMap<String,Object>();

		try {
			List<Group> groupList = groupService.fuzzySearch(targetStr);
			if(groupList == null || groupList.size()==0){
				map.put("code", ErrorType.ERROR_NONE.getCode());
				map.put("msg", ErrorType.ERROR_NONE.getMsg());
			}else{
				map.put("code", ErrorType.ERROR_SUCCESS.getCode());
				map.put("msg", ErrorType.ERROR_SUCCESS.getMsg());
			}
			map.put("groupList", groupList);
		} catch (Exception e) {
			map.put("code", ErrorType.ERROR_UNKNOWN.getCode());
			map.put("msg", ErrorType.ERROR_UNKNOWN.getMsg());
			e.printStackTrace();
		}
		
		JSONObject jsonObj = JSONObject.fromObject(map);
		return jsonObj.toString();
	}
	
	@Resource(name="groupService")
	public void setGroupService(GroupService groupService) {
		this.groupService = groupService;
	}
}
