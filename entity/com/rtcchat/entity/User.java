package com.rtcchat.entity;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author anyuetiantang
 *	用户列表，包含了用户的账号、密码、联系方式、头像、创建的群组、加入的群组、好友
 */

@Entity
@Table(name="user")
public class User implements Serializable {
	public static final String FIELD_OBJECTNAME = "User";
	public static final String FIELD_ID = "id";
	public static final String FIELD_USERNAME = "username";
	public static final String FIELD_PASSWORD = "password";
	public static final String FIELD_CONTACT = "contact";
	public static final String FIELD_HEADIMG = "headImg";
	public static final String FIELD_GROUPSCREATED = "goupsCreated";
	public static final String FIELD_GROUPSJOINED = "groupsJoined";
	
	
	private int id;
	private String username;//用户名
	private String password;//密码
	private String contact;//联系方式
	private String headImg;//头像的路径
	private Set<Group> groupsCreated;//每个用户创建的群组
	private Set<Group> groupsJoined;//每个用户加入的群组
	private Set<User> friends;//每个用户的好友
	
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@Column(nullable=false,length=100)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(nullable=false)
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(nullable=true)
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	
	@OneToMany(mappedBy="creator")
	public Set<Group> getGroupsCreated() {
		return groupsCreated;
	}
	public void setGroupsCreated(Set<Group> groupsCreated) {
		this.groupsCreated = groupsCreated;
	}
	
	@ManyToMany(mappedBy="members")
	public Set<Group> getGroupsJoined() {
		return groupsJoined;
	}
	public void setGroupsJoined(Set<Group> groupsJoined) {
		this.groupsJoined = groupsJoined;
	}
	
	@ManyToMany
	@JoinTable(name="friends",
			joinColumns={@JoinColumn(name="user_id",referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="friend_id",referencedColumnName="id")}
			)
	public Set<User> getFriends() {
		return friends;
	}
	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}
	
	public String toString(){
		return this.id+this.username;
	}
}
