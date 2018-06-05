package com.boe.dacrestapi.vo;

import lombok.Data;

@Data
public class TransactionsVO {
	private long id;
	private String tranHash;
	private String tranId;
	private long initiatorUserId;
	private String initiatorUserName;
	private long receiverUserId;
	private String receiverUserName;
	private String regBlock;
	private long paintingId;
	private String paintingName;
	private String paintingHash;
	private double tranAmount;
	private String genTime;
}
