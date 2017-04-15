package com.rtcchat.controller;

import java.io.File;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.rtcchat.baseController.BaseController;
import com.rtcchat.entity.Group;
import com.rtcchat.entity.User;
import com.rtcchat.service.UserService;
import com.rtcchat.tools.ErrorType;
import com.rtcchat.tools.Md5Tool;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	private UserService userService = null;

	@ResponseBody
	@RequestMapping(value="/headImgChange",method=RequestMethod.POST)
	public String headImgChange(
			@RequestParam(value="headImg",required=true) CommonsMultipartFile headImg,
			HttpServletRequest request, 
			HttpServletResponse response
			) throws IllegalStateException, IOException{
		
		Map<String,String> map = new HashMap<String,String>();
		if(!headImg.isEmpty()){
			HttpSession session = request.getSession();
			String originName = headImg.getOriginalFilename();
			String headImgPath = "/headImg/"+originName;
//            String path = request.getServletContext().getRealPath("/")+headImg.getOriginalFilename();
            String path = request.getServletContext().getRealPath("/")+"headImg\\"+originName;
            System.out.println(path);
            File newFile = new File(path);
            //通过CommonsMultipartFile本身的transferTo方法来实现
            headImg.transferTo(newFile);

            //更新数据库头像
            int userid = (int)session.getAttribute("userid");
            ErrorType errMsg = userService.updateHeadImg(userid, headImgPath);
            
            //更新session数据
            session.setAttribute("headImg", headImgPath);
            
            //返回前台headImg用于实时刷新界面上的头像
    		map.put("code", errMsg.getCode());
    		map.put("msg", errMsg.getMsg());
    		map.put("headImg", headImgPath);
		}else{
    		map.put("code", ErrorType.ERROR_PARAM.getCode());
    		map.put("msg", ErrorType.ERROR_PARAM.getMsg());
		}
		
		JSONObject jsonObj = JSONObject.fromObject(map);
		
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/userInfoChange",method=RequestMethod.POST)
	public String userInfoChange(
			@RequestParam(value="username",required=true) String username,
			@RequestParam(value="contact",required=true) String contact,
			HttpServletRequest request, 
			HttpServletResponse response
			) throws IllegalStateException, IOException{
		
		Map<String,String> map = new HashMap<String,String>();
		HttpSession session = request.getSession();
		int userid = (int)session.getAttribute("userid");
		User userInfo = new User();
		userInfo.setUsername(username);
		userInfo.setContact(contact);
		ErrorType errMsg = userService.updateUserInfo(userid, userInfo);
		
		if(errMsg.getCode().equals("200")){
			session.setAttribute("username", username);
			session.setAttribute("contact", contact);
		}
		
		map.put("code", errMsg.getCode());
		map.put("msg", errMsg.getMsg());
		JSONObject jsonObj = JSONObject.fromObject(map);
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/passwordChange",method=RequestMethod.POST)
	public String passwordChange(
			@RequestParam(value="passwordOld",required=true) String passwordOld,
			@RequestParam(value="passwordNew",required=true) String passwordNew,
			HttpServletRequest request, 
			HttpServletResponse response
			) throws IllegalStateException, IOException{
		
		Map<String,String> map = new HashMap<String,String>();
		HttpSession session = request.getSession();
		int userid = (int)session.getAttribute("userid");
		String passwordOldMd5 = Md5Tool.encryption(passwordOld);
		String passwordNewMd5 = Md5Tool.encryption(passwordNew);
		ErrorType errMsg = userService.updatePassword(userid, passwordOldMd5, passwordNewMd5);
		
		map.put("code", errMsg.getCode());
		map.put("msg", errMsg.getMsg());
		JSONObject jsonObj = JSONObject.fromObject(map);
		return jsonObj.toString();
	}
	
	@ResponseBody
	@RequestMapping(value="/findFriendsAndGroups",method=RequestMethod.POST)
	public String findGroupsCreated(
			@RequestParam(value="ifGetMyGroups",required=false,defaultValue="false") boolean ifGetMyGroups,
			@RequestParam(value="ifGetMyFriends",required=false,defaultValue="false") boolean ifGetMyFriends,
			@RequestParam(value="ifGetJoinedGroups",required=false,defaultValue="false") boolean ifGetJoinedGroups,
			HttpServletRequest request, 
			HttpServletResponse response
			) throws IllegalStateException, IOException{
		
		Map<String,Object> map = new HashMap<String,Object>();
		HttpSession session = request.getSession();
		int userid = (int)session.getAttribute("userid");
		
		try {
			if(ifGetMyGroups){
				Set<Group> groupSet = userService.findGroupCreated(userid);
				map.put("groupsCreated", groupSet);
			}
			
			if(ifGetMyFriends){
				Set<User> friends = userService.findFriendById(userid);
				map.put("myFriends", friends);
			}
			
			if(ifGetJoinedGroups){
				Set<Group> groupSet = userService.findGroupJoined(userid);
				map.put("groupsJoined", groupSet);
			}
			
			map.put("code", ErrorType.ERROR_SUCCESS.getCode());
			map.put("msg", ErrorType.ERROR_SUCCESS.getMsg());
		} catch (Exception e) {
			map.put("code", ErrorType.ERROR_UNKNOWN.getCode());
			map.put("msg", ErrorType.ERROR_UNKNOWN.getMsg());
			e.printStackTrace();
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
			List<User> userList = userService.fuzzySearch(targetStr);
			if(userList == null || userList.size()==0){
				map.put("code", ErrorType.ERROR_NONE.getCode());
				map.put("msg", ErrorType.ERROR_NONE.getMsg());
			}else{
				map.put("code", ErrorType.ERROR_SUCCESS.getCode());
				map.put("msg", ErrorType.ERROR_SUCCESS.getMsg());
			}
			map.put("userList", userList);
		} catch (Exception e) {
			map.put("code", ErrorType.ERROR_UNKNOWN.getCode());
			map.put("msg", ErrorType.ERROR_UNKNOWN.getMsg());
			e.printStackTrace();
		}
		
		JSONObject jsonObj = JSONObject.fromObject(map);
		return jsonObj.toString();
	}

	@Resource(name="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
