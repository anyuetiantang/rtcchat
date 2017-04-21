package com.rtcchat.entity;

import java.io.Serializable;
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
 *	文件列表，包含文件的名称、路径、上传时间、文件类型（群组文件还是用户私传的文件）、属于的用户/群组
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
	private String originName;//文件原名
	private String filepath;//文件存储相对路径
	private Date uploadTime;//文件上传时间
	private String filetype;//文件类型，是群组的文件还是用户的文件
	private User belongToUser;//表示属于哪个用户
	private User sendToUser;//当文件名为user的时候此属性不为空，表示发送给哪个用户
	private Group belongToGroup;//当文件名为group的时候此属性不为空，表示属于哪个群组
	
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
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	
	@Column(nullable=false,length=100)
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
