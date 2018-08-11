package com.boe.dacrestapi.vo;

import lombok.Data;

@Data
public class UserVO {
	private long id;
	private String userName;
	private String email;
	private String password;
	private String nickName;
	private String regTime;
	private String hashString;

}
