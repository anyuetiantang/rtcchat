package com.rtcchat.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rtcchat.baseService.BaseServiceImpl;
import com.rtcchat.dao.UserDao;
import com.rtcchat.entity.User;
import com.rtcchat.service.UserService;
import com.rtcchat.tools.ErrorType;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {
	private UserDao userDao = null;

	@Override
	public ErrorType userAdd(User user) {
		List<User> userList = userDao.findByUsernameOrPassword(user.getUsername(),null);
		if(userList!=null && userList.size()!=0){
			return ErrorType.ERROR_EXIST;
		}else{
			this.save(user);
			return ErrorType.ERROR_SUCCESS;
		}
	}
	
	@Override
	public ErrorType login(String username, String password) {
		List<User> userList = userDao.findByUsernameOrPassword(username,password);
		if(userList!=null && userList.size()!=0){
			return ErrorType.ERROR_SUCCESS;
		}else{
			return ErrorType.ERROR_LOGIN;
		}
	}
	
	@Override
	public User findByUsername(String username) {
		List<User> userList = userDao.findByUsernameOrPassword(username,null);
		if(userList!=null && userList.size()!=0){
			return userList.get(0);
		}else{
			return null;
		}
	}

	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
}
