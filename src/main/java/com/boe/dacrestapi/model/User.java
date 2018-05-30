package com.boe.dacrestapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 用户表
 * @author CAD40
 *
 */
@Entity(name="tb_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(nullable=false,unique=true)
	private String userName;
	@Column(nullable=false)
	private String password;
	@Column(nullable=false, unique=true)
	private String email;
	@Column(nullable=true,unique=true)
	private String nickName;
	@Column(nullable=false)
	private String regTime;
	@Column(nullable=true)
	private String hashString;
	
	public User() {}
	public User(String userName,String password,String email,String nickName,String regTime,String hashString) {
		this.userName = userName;
		this.password=password;
		this.email = email;
		this.nickName = nickName;
		this.regTime = regTime;
		this.hashString = hashString;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public String getHashString() {
		return hashString;
	}
	public void setHashString(String hashString) {
		this.hashString = hashString;
	}
	
}
