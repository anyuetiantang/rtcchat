package com.rtcchat.serviceImpl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rtcchat.dao.UserDao;
import com.rtcchat.entity.Group;
import com.rtcchat.entity.User;
import com.rtcchat.service.UserService;
import com.rtcchat.tools.ErrorType;
import com.rtcchat.tools.Storage;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {
	private UserDao userDao = null;

	@Override
	public ErrorType userAdd(User user) {
		List<User> userList = userDao.findByUsernameOrPassword(user.getUsername(),null);
		if(userList!=null && userList.size()!=0){
			return ErrorType.ERROR_USEREXIST;
		}else{
			this.save(user);
			return ErrorType.ERROR_SUCCESS;
		}
	}
	
	@Override
	public ErrorType login(String username, String password) {
		List<User> userList = userDao.findByUsernameOrPassword(username,password);
		if(userList!=null && userList.size()==1){
			User user = userList.get(0);
			if(Storage.socketMap.get(user.getId()) == null){
				return ErrorType.ERROR_SUCCESS;
			}else{
				return ErrorType.ERROR_ONLINE;
			}
		}else if(userList.size() > 1){
			return ErrorType.ERROR_UNKNOWN;
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
	
	@Override
	public ErrorType updateHeadImg(int userid, String headImg) {
		try {
			User user = this.findById(User.class,userid);
			user.setHeadImg(headImg);
			this.update(user);
		} catch (Exception e) {
			return ErrorType.ERROR_HEADIMG;
		}
		
		return ErrorType.ERROR_SUCCESS;
	}
	
	@Override
	public ErrorType updateUserInfo(int userid, User userInfo)  {
		try {
			//password和headImg两项是不进行更新的，需要先将它们保存下来
			User user = userDao.findById(User.class, userid);
			userInfo.setId(userid);
			userInfo.setPassword(user.getPassword());
			userInfo.setHeadImg(user.getHeadImg());
//			user = DataHelper.copyByTool(User.class, userInfo);//将userInfo的属性复制到user上
			userDao.merge(userInfo);
			return ErrorType.ERROR_SUCCESS;
		} catch (Exception e) {
			return ErrorType.ERROR_UNKNOWN;
		}
	}
	
	@Override
	public ErrorType updatePassword(int userid, String passwordOld, String passwordNew) {
		User user = userDao.findById(User.class, userid);
		if(passwordOld.equals(user.getPassword())){
			user.setPassword(passwordNew);
			userDao.update(user);
			return ErrorType.ERROR_SUCCESS;
		}else{
			return ErrorType.ERROR_PASSWORDERROR;
		}
	}
	
	@Override
	public Set<Group> findGroupCreated(int userid) {
		User user = userDao.findById(User.class, userid);
		Set<Group> groupSet = user.getGroupsCreated();
		//使用代理对象，获取真实对象
		for(Group group : groupSet){
			System.out.println(group.getMembers().size());
		}
		userDao.clear();
		for(Group group : groupSet){
			group.getCreator().setPassword("");
			group.getCreator().setFriends(null);
			group.getCreator().setGroupsCreated(null);
			group.getCreator().setGroupsJoined(null);
			Set<User> members = group.getMembers();
			for(User member : members){
				member.setPassword("");
				member.setFriends(null);
				member.setGroupsCreated(null);
				member.setGroupsJoined(null);
			}
		}
		
		return groupSet;
	}
	
	@Override
	public Set<Group> findGroupJoined(int userid) {
		User user = userDao.findById(User.class, userid);
		Set<Group> groupSet = user.getGroupsJoined();
		//使用代理对象，获取真实对象
		for(Group group : groupSet){
			System.out.println(group.getMembers().size());
		}
		userDao.clear();
		for(Group group : groupSet){
			group.getCreator().setPassword("");
			group.getCreator().setFriends(null);
			group.getCreator().setGroupsCreated(null);
			group.getCreator().setGroupsJoined(null);
			Set<User> members = group.getMembers();
			for(User member : members){
				member.setPassword("");
				member.setFriends(null);
				member.setGroupsCreated(null);
				member.setGroupsJoined(null);
			}
		}
		return groupSet;
	}
	
	@Override
	public List<User> fuzzySearch(String str) {
		List<User> userList = null;
		if(str != null && !"".equals(str)){
			StringBuffer hql =  new StringBuffer();
			hql.append("from ").append(User.FIELD_OBJECTNAME)
			   .append(" where ").append(User.FIELD_USERNAME)
			   .append(" like '%").append(str).append("%'");
			userList = userDao.findByHql(User.class, hql.toString());
			userDao.clear();
			for(User user : userList){
				user.setPassword("");
				user.setFriends(null);
				user.setGroupsCreated(null);
				user.setGroupsJoined(null);
			}
		}
		return userList;
	}
	
	
	@Override
	public ErrorType friendAdd(int userId, int targetId) {
		try {
			User user = userDao.findById(User.class, userId);
			User friend = userDao.findById(User.class, targetId);
			user.getFriends().add(friend);
			friend.getFriends().add(user);
			
			return ErrorType.ERROR_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorType.ERROR_UNKNOWN;
		}
	}
	
	@Override
	public ErrorType friendDelete(int userId, int targetId) {
		try {
			User user = userDao.findById(User.class, userId);
			Set<User> sourceFriends = user.getFriends();
			
			User friend = userDao.findById(User.class, targetId);
			Set<User> targetFriends = friend.getFriends();
			
			Iterator<User> sourceIterator = sourceFriends.iterator();
			while(sourceIterator.hasNext()){
				User u = sourceIterator.next();
				if(u.getId() == targetId){
					sourceFriends.remove(u);
				}
			}
			
			Iterator<User> targetIterator = targetFriends.iterator();
			while(targetIterator.hasNext()){
				User u = targetIterator.next();
				if(u.getId() == userId){
					targetFriends.remove(u);
				}
			}
			
			return ErrorType.ERROR_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorType.ERROR_UNKNOWN;
		}
	}

	@Override
	public Set<User> findFriendById(int userId) {
		User user = userDao.findById(User.class, userId);
		Set<User> friends = user.getFriends();
		System.out.println(friends.size());
		userDao.clear();
		for(User friend : friends){
			friend.setPassword("");
			friend.setFriends(null);
			friend.setGroupsCreated(null);
			friend.setGroupsJoined(null);
		}
		
		return friends;
	}
	
	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
}
