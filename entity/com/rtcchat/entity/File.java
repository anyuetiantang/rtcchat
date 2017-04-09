package com.rtcchat.entity;

import java.io.Serializable;
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

import com.rtcchat.tools.FileType;

/**
 * 
 * @author anyuetiantang
 *	�ļ��б������ļ������ơ�·�����ϴ�ʱ�䡢�ļ����ͣ�Ⱥ���ļ������û�˽�����ļ��������ڵ��û�/Ⱥ��
 */

@Entity
@Table(name="file")
public class File implements Serializable{
	public static final String FIELD_OBJECTNAME = "File";
	public static final String FIELD_ID = "id";
	public static final String FIELD_FILENAME = "filename";
	public static final String FIELD_FILEPATH = "filepath";
	public static final String FIELD_UPLOADTIME = "uploadTime";
	public static final String FIELD_FILETYPE= "filetype";
	public static final String FIELD_BELONGTOUSER = "belongToUser";
	public static final String FIELD_SENDTOUSER = "sendToUser";
	public static final String FIELD_BELONGTOGROUP = "belongToGroup";

	
	private int id;
	private String filename;//�ļ���
	private String filepath;//�ļ��洢���·��
	private Date uploadTime;//�ļ��ϴ�ʱ��
	private String filetype;//�ļ����ͣ���Ⱥ����ļ������û����ļ�
	private User belongToUser;//���ļ���Ϊuser��ʱ������Բ�Ϊ�գ���ʾ�����ĸ��û�
	private User sendToUser;//���ļ���Ϊuser��ʱ������Բ�Ϊ�գ���ʾ���͸��ĸ��û�
	private Group belongToGroup;//���ļ���Ϊgroup��ʱ������Բ�Ϊ�գ���ʾ�����ĸ�Ⱥ��
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id",nullable=false,unique=true)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(nullable=false,length=20)
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@Column(nullable=false,length=20)
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}
	
	@Column(nullable = false)
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	
	@ManyToOne
	@JoinColumn(name="belong_user_id")
	public User getBelongToUser() {
		return belongToUser;
	}
	public void setBelongToUser(User belongToUser) {
		this.belongToUser = belongToUser;
	}
	
	@ManyToOne
	@JoinColumn(name="send_user_id")
	public User getSendToUser() {
		return sendToUser;
	}
	public void setSendToUser(User sendToUser) {
		this.sendToUser = sendToUser;
	}
	
	@ManyToOne
	@JoinColumn(name="belong_group_id")
	public Group getBelongToGroup() {
		return belongToGroup;
	}
	public void setBelongToGroup(Group belongToGroup) {
		this.belongToGroup = belongToGroup;
	}
	
}
