package com.rtcchat.serviceImpl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.rtcchat.dao.GroupDao;
import com.rtcchat.dao.UserDao;
import com.rtcchat.entity.Group;
import com.rtcchat.entity.User;
import com.rtcchat.service.GroupService;
import com.rtcchat.service.UserService;
import com.rtcchat.tools.ErrorType;

@Service("groupService")
public class GroupServiceImpl  extends BaseServiceImpl implements GroupService{
	private GroupDao groupDao = null;
	private UserDao userDao = null;
	
	@Override
	public ErrorType groupCreated(int userid, String groupName) {
		try {
			User user = groupDao.findById(User.class, userid);
			Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(Group.class);
			criteria.add(Restrictions.eq("creator", user));
			criteria.add(Restrictions.eq("groupname", groupName));
			List<Group> tempList = groupDao.findByCriteria(criteria);
			
			if(tempList != null && tempList.size()!=0){
				return ErrorType.ERROR_GROUPEXIST;
			}
			Group group = new Group();
			group.setCreator(user);
			group.setGroupname(groupName);
			groupDao.save(group);
		} catch (Exception e) {
			return ErrorType.ERROR_UNKNOWN;
		}
		
		return ErrorType.ERROR_SUCCESS;
	}
	
	@Override
	public Set<User> findMembersById(int groupId) {
		Group group = groupDao.findById(Group.class, groupId);
		Set<User> members = group.getMembers();
		System.out.println(members.size());
		userDao.clear();
		for(User u : members){
			u.setPassword("");
			u.setFriends(null);
			u.setGroupsCreated(null);
			u.setGroupsJoined(null);
		}
		
		return members;
	}
	
	@Override
	public ErrorType groupUserAdd(int groupId, int targetId) {
		try {
			Group group = groupDao.findById(Group.class, groupId);
			User user = userDao.findById(User.class, targetId);
			group.getMembers().add(user);
			
			return ErrorType.ERROR_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorType.ERROR_UNKNOWN;
		}
	}

	@Override
	public ErrorType groupUserDelete(int groupId, int targetId) {
		try {
			Group group = groupDao.findById(Group.class, groupId);
			Set<User> members = group.getMembers();
			Set<User> temp = new HashSet<User>();
			
			Iterator<User> iterator = members.iterator();
			while(iterator.hasNext()){
				User member = iterator.next();
				if(targetId == member.getId()){
					temp.add(member);
				}
			}
			members.removeAll(temp);
			return ErrorType.ERROR_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorType.ERROR_UNKNOWN;
		}
		
	}
	
	@Override
	public List<Group> fuzzySearch(String str) {
		List<Group> groupList = null;
		if(str!=null && !"".equals(str)){
			StringBuffer hql = new StringBuffer();
			hql.append("from ").append(Group.FIELD_OBJECTNAME)
			   .append(" where ").append(Group.FIELD_GROUPNAME)
			   .append(" like '%").append(str).append("%'");
			groupList = groupDao.findByHql(Group.class, hql.toString());
			groupDao.clear();
			for(Group group : groupList){
				group.setMembers(null);
				group.getCreator().setPassword("");
				group.getCreator().setFriends(null);
				group.getCreator().setGroupsCreated(null);
				group.getCreator().setGroupsJoined(null);
			}
		}
		
		return groupList;
	}
	
	@Override
	public User findCreator(int groupId) {
		Group group = groupDao.findById(Group.class, groupId);
		User user = group.getCreator();
		groupDao.clear();
		group.setMembers(null);
		user.setPassword("");
		user.setFriends(null);
		user.setGroupsCreated(null);
		user.setGroupsJoined(null);
		
		return user;
	}
	
	@Resource(name="groupDao")
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


}
