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
 *	Ⱥ����Ϣ�б�,������Ϣ�塢����Ⱥ�顢�����ߡ�����ʱ�����Ϣ
 */

@Entity
@Table(name="group_message")
public class GroupMessage {
	public static final String FIELD_ID = "id";
	public static final String FIELD_TEXT = "text";
	public static final String FIELD_BELONGTOGROUP = "belongToGroup";
	public static final String FIELD_FROMUSER = "fromUser";
	public static final String FIELD_SENDTIME = "sendTime";
	
	private int id;
	private String text;//��Ϣ��
	private Group belongToGroup;//���ڵ�Ⱥ��
	private User fromUser;//������
	private Date sendTime;//���͵�ʱ��
	
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
	@JoinColumn(name="group_id")
	public Group getBelongToGroup() {
		return belongToGroup;
	}
	public void setBelongToGroup(Group belongToGroup) {
		this.belongToGroup = belongToGroup;
	}
	
	@ManyToOne
	@JoinColumn(name="user_id")
	public User getFromUser() {
		return fromUser;
	}
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}
}
