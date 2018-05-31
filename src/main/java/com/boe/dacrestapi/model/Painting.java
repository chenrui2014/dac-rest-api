package com.boe.dacrestapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
	@Column(nullable=false,unique=false)
	private String depCerticateId;//存证ID
	@Column(nullable=true,unique=false)
	private String paintHash;//画作区块哈希
	@Column(nullable=false,unique=false)
	private String paintName;
	@Column(nullable=false,unique=false)
	private String author;//作者
	@Column(nullable=false,unique=false)
	private int userId;//上传用户ID
	@Column(nullable=true,unique=false)
	private String denPaintHash;//依赖版权ID,原创可为空
	@Column(nullable=false,unique=false)
	private String type;//画作类型：1:原创，2:二次创作，3:三次创作
	@Column(nullable=false,unique=false)
	private String regTime;//登记时间
	@Column(nullable=false,unique=false)
	private String status;//登记状态：审核中，已审核，已公正
}
