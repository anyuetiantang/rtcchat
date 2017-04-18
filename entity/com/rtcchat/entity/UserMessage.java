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
 *	群组消息列表,包含消息体、发送者、接收者、发送时间、是否被阅读等信息等信息
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
	public static final String FIELD_SENDTIME = "sendTime";
	public static final String FIELD_IFREAD = "ifread";
	
	private int id;
	private String type;//消息类型
	private String text;//消息体
	private User fromUser;//发送者
	private User toUser;//接收者
	private Date sendTime;//发送的时间
	private boolean ifread;//是否被读取
	
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
	
	@Column(nullable = false)
	public boolean getIfread() {
		return ifread;
	}
	public void setIfread(boolean ifread) {
		this.ifread = ifread;
	}
}
