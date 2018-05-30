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
 * 交易表
 * @author CAD40
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="tb_transactions")
public class Transactions implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@Column(nullable=false,unique=true)
	private String tranHash;//交易哈希
	@Column(nullable=false,unique=false)
	private String initiator;//交易发起方
	@Column(nullable=false,unique=false)
	private String receiver;//交易接收方
	@Column(nullable=false,unique=false)
	private String regBlock;//所属区块
	@Column(nullable=false,unique=false)
	private String genTime;//生成时间
}
