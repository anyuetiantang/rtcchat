package com.rtcchat.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.rtcchat.tools.MessageType;

/**
 * 
 * @author anyuetiantang
 *	Ⱥ����Ϣ�б�,������Ϣ�塢�����ߡ������ߡ�����ʱ�䡢�Ƿ��Ķ�����Ϣ����Ϣ
 */

@Entity
@Table(name="user_message")
public class UserMessage {
	public static final String FIELD_ID = "id";
	public static final String FIELD_TEXT = "text";
	public static final String FIELD_FROMUSER = "fromUser";
	public static final String FIELD_TOUSER = "toUser";
	public static final String FIELD_SENDTIME = "sendTime";
	public static final String FIELD_IFREAD = "ifread";
	
	private int id;
	private String text;//��Ϣ��
	private User fromUser;//������
	private User toUser;//������
	private Date sendTime;//���͵�ʱ��
	private Enum<MessageType> ifread;//�Ƿ񱻶�ȡ
	
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
	
	@Enumerated
	@Column(nullable = false)
	public Enum<MessageType> getIfread() {
		return ifread;
	}
	public void setIfread(Enum<MessageType> ifread) {
		this.ifread = ifread;
	}
}