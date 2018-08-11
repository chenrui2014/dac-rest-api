package com.boe.dacrestapi.model;

import java.io.Serializable;
import java.security.KeyStore.PrivateKeyEntry;
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
	@Column(nullable=false,unique=false)
	private String tranHash;//交易哈希
	@Column(nullable=true,unique=false)
	private String tranId;//交易号
	@ManyToOne
	@JoinColumn(name="initiator")
	private User initiatorUser;//交易发起方
	@ManyToOne
	@JoinColumn(name="receiver")
	private User receiverUser;//交易接收方
	@Column(nullable=false,unique=false)
	private String regBlock;//所属区块
	@ManyToOne
	@JoinColumn(name="paintingId")
	private Painting painting;//交易标的
	@Column(nullable=true,unique=false)
	private double tranAmount;//交易金额
	@JsonSerialize(using = CustomDateSerializer.class)
	@Column(nullable=false,unique=false)
	private Timestamp genTime;//生成时间
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="transactions")
	private List<Income> incomes;
}
