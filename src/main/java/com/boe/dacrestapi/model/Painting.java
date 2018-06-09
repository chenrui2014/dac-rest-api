package com.boe.dacrestapi.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.boe.dacrestapi.utils.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 画作表
 * @author CAD40
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="tb_painting")
public class Painting implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@Column(nullable=true,unique=false)
	private String depCerticateId;//版权证书
	@Column(nullable=true,unique=false)
	private String digFingerPrint;//数字指纹
	@Column(nullable=true,unique=false)
	private String paintHash;//画作区块哈希
	@Column(nullable=false,unique=false)
	private String paintName;
	@Column(nullable=false,unique=false)
	private String author;//作者
	//@Column(nullable=false,unique=false)
	//private long userId;//上传用户ID
	@Column(nullable=true,unique=false)
	private String transactionId;//交易号
	@Column(nullable=true,unique=false)
	private double paintingPrice;//作品价格
	@Column(nullable=true,unique=false)
	private String denPaintHash;//依赖版权ID,原创可为空
	@Column(nullable=false,unique=false)
	private String type;//画作类型
	@Column(nullable=false,unique=false)
	private String paintDes;//画作描述
	@Column(nullable=false,unique=false)
	private String paintUrl;//画作存储地址
	@Column(nullable=false,unique=false)
	private Timestamp regTime;//登记时间
	@Column(nullable=false,unique=false)
	private String status;//登记状态：审核中，已审核，已公正
	@JsonSerialize(using = CustomDateSerializer.class)  
	@Column(nullable=true,unique=false)
	private String genFlag;//标记：1:原创，2:二次创作，3:三次创作
	
	@ManyToOne(optional=true)
	@JoinColumn(name="denPaintId")
	private Painting denPainting; 
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="denPainting")
	private List<Painting> beDenPaintings;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	@OneToMany(fetch=FetchType.LAZY,mappedBy="painting")
	private List<Transactions> transactions;
}
