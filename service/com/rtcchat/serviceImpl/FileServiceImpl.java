package com.rtcchat.serviceImpl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.rtcchat.dao.FileDao;
import com.rtcchat.dao.GroupDao;
import com.rtcchat.dao.UserDao;
import com.rtcchat.entity.File;
import com.rtcchat.entity.Group;
import com.rtcchat.entity.User;
import com.rtcchat.service.FileService;
import com.rtcchat.tools.ErrorType;
import com.rtcchat.tools.FileType;

@Service("fileService")
public class FileServiceImpl extends BaseServiceImpl implements FileService{
	private UserDao userDao = null;
	private GroupDao groupDao = null;
	private FileDao fileDao = null;

	@Override
	public ErrorType fileUpload(int sourceId, int targetId, String type, String originName, String path) {
		try {
			File myFile = new File();
			User sourceUser = userDao.findById(User.class, sourceId);
			if(FileType.FILE_TYPE_USER.getType().equals(type)){
				User targetUser =  userDao.findById(User.class, targetId);
				myFile.setSendToUser(targetUser);
			}else if(FileType.FILE_TYPE_GROUP.getType().equals(type)){
				Group targetGroup = groupDao.findById(Group.class, targetId);
				myFile.setBelongToGroup(targetGroup);
			}
			myFile.setBelongToUser(sourceUser);
			myFile.setOriginName(originName);
			myFile.setFilepath(path);
			myFile.setUploadTime(new Date());
			myFile.setFiletype(type);
			fileDao.save(myFile);
			
			return ErrorType.ERROR_SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ErrorType.ERROR_UNKNOWN;
		}
		
	}

	@Override
	public List<File> getFiles(int sourceId, int targetId, String type) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(File.class);
		User sourceUser = userDao.findById(User.class, sourceId);
		if(FileType.FILE_TYPE_USER.getType().equals(type)){
			User targetUser =  userDao.findById(User.class, targetId);
			criteria.add(Restrictions.eq(File.FIELD_SENDTOUSER, targetUser));
		}else if(FileType.FILE_TYPE_GROUP.getType().equals(type)){
			Group targetGroup = groupDao.findById(Group.class, targetId);
			criteria.add(Restrictions.eq(File.FIELD_BELONGTOGROUP, targetGroup));
		}
		criteria.add(Restrictions.eq(File.FIELD_BELONGTOUSER, sourceUser));
		criteria.add(Restrictions.eq(File.FIELD_FILETYPE, type));
		criteria.addOrder(Order.asc(File.FIELD_UPLOADTIME));
		List<File> fileList = fileDao.findByCriteria(criteria);
		fileDao.clear();
		for(File file : fileList){
			if(file.getBelongToUser()!=null){
				file.getBelongToUser().setPassword("");
				file.getBelongToUser().setFriends(null);
				file.getBelongToUser().setGroupsCreated(null);
				file.getBelongToUser().setGroupsJoined(null);
			}
			
			if(file.getSendToUser()!=null){
				file.getSendToUser().setPassword("");
				file.getSendToUser().setFriends(null);
				file.getSendToUser().setGroupsCreated(null);
				file.getSendToUser().setGroupsJoined(null);
			}
			
			if(file.getBelongToGroup()!=null){
				file.getBelongToGroup().setMembers(null);
				file.getBelongToGroup().getCreator().setPassword("");
				file.getBelongToGroup().getCreator().setFriends(null);
				file.getBelongToGroup().getCreator().setGroupsCreated(null);
				file.getBelongToGroup().getCreator().setGroupsJoined(null);
			}
		}
		
		return fileList;
	}
	
	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Resource(name="groupDao")
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}
	
	@Resource(name="fileDao")
	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}

}
