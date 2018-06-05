package com.boe.dacrestapi.vo;

import lombok.Data;

@Data
public class PaintingVO {
	
	private long id;
	private String depCerticateId;
	private String digFingerPrint;
	private String paintHash;
	private String paintName;
	private String author;//作者
	private String transactionId;//交易号
	private double paintingPrice;//作品价格
	private String denPaintHash;//依赖作品哈希
	private long denPaintId;//依赖作品ID
	private String denPaintName;//依赖作品名称
	private String type;//画作类型
	private String regTime;//登记时间
	private String status;
	private String genFlag;
	private long userId;
	private String userName;
}
