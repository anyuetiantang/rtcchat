package com.rtcchat.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rtcchat.baseController.BaseController;
import com.rtcchat.entity.User;
import com.rtcchat.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	private UserService userService;

	@ResponseBody
	@RequestMapping(value="/headModify",method=RequestMethod.POST)
	public String test(
			HttpServletRequest request, 
			HttpServletResponse response
			){
		
		HttpSession session = request.getSession();
		System.out.println(session.getAttribute("headImg"));
		session.setAttribute("headImg","/headImg/bg1");
		System.out.println(session.getAttribute("headImg"));
		
		return "ok";
	}
	
	@Resource(name="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
