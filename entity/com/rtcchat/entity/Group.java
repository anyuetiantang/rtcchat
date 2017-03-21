package com.rtcchat.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author anyuetiantang
 *	群组列表，包含群组名称、创建者、成员
 */

@Entity
@Table(name="group")
public class Group implements Serializable{
	public static final String FIELD_ID = "id";
	public static final String FIELD_GROUPNAME = "groupname";
	public static final String FIELD_CREATOR = "creator";
	public static final String FIELD_MEMBERS = "members";
	
	private int id;
	private String groupname;//群组名称
	private User creator;//创建者
	private Set<User> members;//成员
	
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
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	
	@ManyToOne
	@JoinColumn(name="creator_id")
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	
	@ManyToMany
	@JoinTable(name="users_join_groups",
			joinColumns={@JoinColumn(name="group_id",referencedColumnName="id")},
			inverseJoinColumns={@JoinColumn(name="user_id",referencedColumnName="id")}
			)
	public Set<User> getMembers() {
		return members;
	}
	public void setMembers(Set<User> members) {
		this.members = members;
	}
}
