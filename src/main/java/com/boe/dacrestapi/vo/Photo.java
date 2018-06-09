package com.boe.dacrestapi.vo;

import lombok.Data;

@Data
public class Photo {
	private String hash;
	private String name;
	private String author;
	private String reg_Time;
	private String ca_sn;
	private double price;
	private String referred;
}
