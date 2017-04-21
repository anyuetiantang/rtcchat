package com.rtcchat.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.rtcchat.dao.GroupDao;
import com.rtcchat.dao.MessageDao;
import com.rtcchat.dao.UserDao;
import com.rtcchat.entity.Group;
import com.rtcchat.entity.GroupMessage;
import com.rtcchat.entity.User;
import com.rtcchat.entity.UserMessage;
import com.rtcchat.service.MessageService;
import com.rtcchat.tools.WebSocketMsgType;

@Service("messageService")
public class MessageServiceImpl extends BaseServiceImpl implements MessageService{
	private MessageDao messageDao = null;
	private UserDao userDao = null;
	private GroupDao groupDao = null;

	@Override
	public List<UserMessage> getUserMessage(int fromUserId,int toUserId, int number, boolean ifNotRead) {
		User fromUser = userDao.findById(User.class, fromUserId);
		User toUser = userDao.findById(User.class, toUserId);
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserMessage.class);
		criteria.add(Restrictions.or(Restrictions.eq(UserMessage.FIELD_FROMUSER,toUser),Restrictions.eq(UserMessage.FIELD_FROMUSER, fromUser)));
		criteria.add(Restrictions.or(Restrictions.eq(UserMessage.FIELD_TOUSER,toUser),Restrictions.eq(UserMessage.FIELD_TOUSER, fromUser)));
		criteria.add(Restrictions.eq(UserMessage.FIELD_TYPE, WebSocketMsgType.MESSAGE_USER.getSocketType()));
		criteria.addOrder(Order.asc(UserMessage.FIELD_SENDTIME));
		criteria.setMaxResults(number);
		if(ifNotRead){
			criteria.add(Restrictions.eq(UserMessage.FIELD_IFREAD, false));
		}
		List<UserMessage> messageList = messageDao.findByCriteria(criteria);
		messageDao.clear();
		for(UserMessage message : messageList){
			message.getFromUser().setPassword("");
			message.getFromUser().setFriends(null);
			message.getFromUser().setGroupsCreated(null);
			message.getFromUser().setGroupsJoined(null);
			
			message.getToUser().setPassword("");
			message.getToUser().setFriends(null);
			message.getToUser().setGroupsCreated(null);
			message.getToUser().setGroupsJoined(null);
		}
		return messageList;
	}
	
	@Override
	public List<GroupMessage> getGroupMessage(int fromUserId, int groupId, int number) {
		User fromUser = userDao.findById(User.class, fromUserId);
		Group belongToGroup = groupDao.findById(Group.class, groupId);
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(GroupMessage.class);
		criteria.add(Restrictions.eq(GroupMessage.FIELD_BELONGTOGROUP, belongToGroup));
		criteria.add(Restrictions.eq(GroupMessage.FIELD_TYPE, WebSocketMsgType.MESSAGE_GROUP.getSocketType()));
		criteria.addOrder(Order.asc(GroupMessage.FIELD_SENDTIME));
		criteria.setMaxResults(number);
		List<GroupMessage> messageList = messageDao.findByCriteria(criteria);
		messageDao.clear();
		
		for(GroupMessage message : messageList){
			message.getFromUser().setPassword("");
			message.getFromUser().setFriends(null);
			message.getFromUser().setGroupsCreated(null);
			message.getFromUser().setGroupsJoined(null);
			
			message.getBelongToGroup().setMembers(null);
			message.getBelongToGroup().getCreator().setPassword("");
			message.getBelongToGroup().getCreator().setFriends(null);
			message.getBelongToGroup().getCreator().setGroupsCreated(null);
			message.getBelongToGroup().getCreator().setGroupsJoined(null);
		}
		return messageList;
	}
	
	@Override
	public void setIfRead(int fromUserId, int toUserId) {
		User fromUser = userDao.findById(User.class, fromUserId);
		User toUser = userDao.findById(User.class, toUserId);
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(UserMessage.class);
		criteria.add(Restrictions.eq(UserMessage.FIELD_FROMUSER, fromUser));
		criteria.add(Restrictions.eq(UserMessage.FIELD_TOUSER, toUser));
		criteria.add(Restrictions.eq(UserMessage.FIELD_TYPE, WebSocketMsgType.MESSAGE_USER.getSocketType()));
		criteria.add(Restrictions.eq(UserMessage.FIELD_IFREAD, false));
		List<UserMessage> messageList = messageDao.findByCriteria(criteria);
		for(UserMessage message : messageList){
			message.setIfread(true);
			messageDao.update(message);
		}
	}
	
	@Resource(name="messageDao")
	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Resource(name="groupDao")
	public void setGroupDao(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

}
