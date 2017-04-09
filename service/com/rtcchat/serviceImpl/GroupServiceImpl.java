package com.rtcchat.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rtcchat.dao.GroupDao;
import com.rtcchat.entity.Group;
import com.rtcchat.entity.User;
import com.rtcchat.service.GroupService;
import com.rtcchat.tools.ErrorType;

@Service("groupService")
public class GroupServiceImpl  extends BaseServiceImpl implements GroupService{
	private GroupDao groupDao = null;
	
	@Override
	public ErrorType groupCreated(int userid, String groupName) {
		try {
			User user = groupDao.findById(User.class, userid);
			Group group = new Group();
			group.setCreator(user);
			group.setGroupname(groupName);
			List<Group> tempList = groupDao.findByExample(group);
			if(tempList != null && tempList.size()!=0){
				return ErrorType.ERROR_GROUPEXIST;
			}
			groupDao.save(group);
		} catch (Exception e) {
			return ErrorType.ERROR_UNKNOWN;
		}
		
		return ErrorType.ERROR_SUCCESS;
	}
	
	@Resource(name="groupDao")
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
}
