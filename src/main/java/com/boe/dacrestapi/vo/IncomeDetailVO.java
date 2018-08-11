package com.boe.dacrestapi.vo;

import lombok.Data;

@Data
public class IncomeDetailVO {

	private long id;
	private long userId;
	private String userHash;
	private String userName;
	private long transactionId;
	private String transactionHash;
	private long paintingId;
	private String paintingGenFlag;
	private String paintingName;
	private String paintingHash;
	private double tranAmount;
	private double income;
	private double rate;
	private String tranTime;
}
