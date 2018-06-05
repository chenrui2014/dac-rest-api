package com.boe.dacrestapi.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表
 * @author CAD40
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
	
	@OneToMany(fetch=FetchType.LAZY,mappedBy="user")
	private List<Painting> paintings;
	@OneToMany(fetch=FetchType.LAZY,mappedBy="initiatorUser")
	private List<Transactions> initiatingTran;
	@OneToMany(fetch=FetchType.LAZY,mappedBy="receiverUser")
	private List<Transactions> receiveTran;
	
}
