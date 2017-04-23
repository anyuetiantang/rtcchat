package com.rtcchat.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author anyuetiantang
 *	Ⱥ����Ϣ�б�,������Ϣ�塢�����ߡ������ߡ�����ʱ�䡢�Ƿ��Ķ�����Ϣ����Ϣ
 */

@Entity
@Table(name="user_message")
public class UserMessage {
	public static final String FIELD_OBJECTNAME = "UserMessage";
	public static final String FIELD_ID = "id";
	public static final String FIELD_TYPE = "type";
	public static final String FIELD_TEXT = "text";
	public static final String FIELD_FROMUSER = "fromUser";
	public static final String FIELD_TOUSER = "toUser";
	public static final String FIELD_RELATEDGROUP = "relatedGroup";
	public static final String FIELD_SENDTIME = "sendTime";
	public static final String FIELD_IFREAD = "ifread";
	
	private int id;
	private String type;//��Ϣ����
	private String text;//��Ϣ��
	private User fromUser;//������
	private User toUser;//������
	private Group relatedGroup;//������Ⱥ�飬�����ڼ���Ⱥ���ʱ���¼Ⱥ����Ϣ
	private Date sendTime;//���͵�ʱ��
	private boolean ifread;//�Ƿ񱻶�ȡ
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",nullable=false,unique=true)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(nullable=false)
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	@Column(nullable=true)
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@ManyToOne
	@JoinColumn(name="from_user_id")
	public User getFromUser() {
		return fromUser;
	}
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	
	@ManyToOne
	@JoinColumn(name="to_user_id")
	public User getToUser() {
		return toUser;
	}
	public void setToUser(User toUser) {
		this.toUser = toUser;
	}
	
	@ManyToOne
	@JoinColumn(name="relate_group_id")
	public Group getRelatedGroup() {
		return relatedGroup;
	}
	public void setRelatedGroup(Group relatedGroup) {
		this.relatedGroup = relatedGroup;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	@Column(nullable = false)
	public boolean getIfread() {
		return ifread;
	}
	public void setIfread(boolean ifread) {
		this.ifread = ifread;
	}
}
