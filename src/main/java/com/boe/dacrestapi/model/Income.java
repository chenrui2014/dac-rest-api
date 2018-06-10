package com.boe.dacrestapi.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.boe.dacrestapi.utils.CustomDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="tb_income")
public class Income implements Serializable {
	/**
	 * @description：字段功能描述
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	@ManyToOne(optional=true)
	@JoinColumn(name="userId")
	private User user;
	@ManyToOne
	@JoinColumn(name="paintingId")
	private Painting incomePainting;
	@ManyToOne
	@JoinColumn(name="transactionId")
	private Transactions transactions;
	@Column(nullable=true,unique=false)
	private double rate;
	@Column(nullable=true,unique=false)
	private double amount;
	@Column(nullable=true,unique=false)
	private double income;
	@JsonSerialize(using = CustomDateSerializer.class) 
	@Column(nullable=true,unique=false)
	private Timestamp tranTime;

}
