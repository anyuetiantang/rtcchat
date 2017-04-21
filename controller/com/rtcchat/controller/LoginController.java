package com.rtcchat.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rtcchat.baseController.BaseController;
import com.rtcchat.entity.User;
import com.rtcchat.service.UserService;
import com.rtcchat.tools.ErrorType;
import com.rtcchat.tools.Md5Tool;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/start")
public class LoginController extends BaseController{
	private UserService userService = null;
	
	@RequestMapping(value="/toLogin",method=RequestMethod.GET)
	public String toLogin(
			HttpServletRequest request, 
			HttpServletResponse response
			){
		
		return "login";
		
	}
	
	@RequestMapping(value="/loginSuccess",method=RequestMethod.GET)
	public String loginSuccess(
			@RequestParam(value="username",required=true)String usernameConfirm,
			@RequestParam(value="random",required=true)String randomConfirm,
			@RequestParam(value="remember",required=false,defaultValue="false")boolean remember,
			HttpServletRequest request, 
			HttpServletResponse response
			){
		
		HttpSession session = request.getSession();
		String username = (String)session.getAttribute("username");
		String random = (String)session.getAttribute("random");
		if(username!=null && username.equals(usernameConfirm) && random!=null && random.equals(randomConfirm)){
			User user = userService.findByUsername(username);
			session.setAttribute("userid", user.getId());
			session.setAttribute("contact", user.getContact());
			session.setAttribute("headImg", user.getHeadImg());
			
			//进行对cookie的操作
			if(remember){
				Cookie cookie_username = new Cookie("username", user.getUsername());
				Cookie cookie_headImg = new Cookie("headImg", user.getHeadImg());
				Cookie cookie_contact = new Cookie("contact", user.getContact());
				cookie_username.setMaxAge(3600);
				cookie_headImg.setMaxAge(3600);
				cookie_contact.setMaxAge(3600);
				response.addCookie(cookie_username);
				response.addCookie(cookie_headImg);
				response.addCookie(cookie_contact);
			}else{
				Cookie[] cookies= request.getCookies();
				for(Cookie cookie : cookies){
					if(cookie.getName().equals("username") || cookie.getName().equals("headImg") || cookie.getName().equals("contact")){
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
				}
			}
			return "main";
		}else{
			return "login";
		}
	}
	
	@ResponseBody
	@RequestMapping(value="/loginRequest",method=RequestMethod.POST)
	public String login(
			@RequestParam(value="username",required=true)String username,
			@RequestParam(value="password",required=true)String password,
			HttpServletRequest request, 
			HttpServletResponse response
			) throws ServletException, IOException, NoSuchAlgorithmException{
		
		String random = null;//设置随机值防止直接进行无账号登录
		Map<String,String> map = new HashMap<String,String>();
		ErrorType errMsg = userService.login(username, Md5Tool.encryption(password));
		if(errMsg.getCode().equals("200")){
			
			HttpSession session = request.getSession();
			random = String.valueOf(Math.random());
			session.setAttribute("username", username);
			session.setAttribute("random", random);
		}
		map.put("code", errMsg.getCode());
		map.put("msg", errMsg.getMsg());
		map.put("random", random);
		JSONObject jsonObj = JSONObject.fromObject(map);
		
		return jsonObj.toString();
	}
	

	
	@ResponseBody
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String register(
			@RequestParam(value="username",required=true)String username,
			@RequestParam(value="password",required=true)String password,
			@RequestParam(value="contact",required=true)String contact,
			HttpServletRequest request, 
			HttpServletResponse response
			){
		
		Map<String,String> map = new HashMap<String,String>();
		User user = new User();
		user.setUsername(username);
		user.setPassword(Md5Tool.encryption(password));
		user.setHeadImg("/headImg/head.jpg");
		user.setContact(contact);
		ErrorType errMsg = userService.userAdd(user);
		
		map.put("code", errMsg.getCode());
		map.put("msg", errMsg.getMsg());
		JSONObject jsonObj = JSONObject.fromObject(map);
		
		return jsonObj.toString();
	}
	
	
	@Resource(name="userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
