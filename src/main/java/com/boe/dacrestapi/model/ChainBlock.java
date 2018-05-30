package com.boe.dacrestapi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 区块信息表
 * @author CAD40
 *
 */
@Entity(name="tb_chainblock")
public class ChainBlock implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(nullable=false,unique=true)
	private long blockNum;//区块号
	@Column(nullable=false,unique=true)
	private String blockHash;//区块哈希
	@Column(nullable=true,unique=false)
	private int tranNum;//交易数量
	@Column(nullable=true,unique=false)
	private long pacTime;//生成区块耗时
	@Column(nullable=true,unique=false)
	private String genTime;//生成时间
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getBlockNum() {
		return blockNum;
	}
	public void setBlockNum(long blockNum) {
		this.blockNum = blockNum;
	}
	public String getBlockHash() {
		return blockHash;
	}
	public void setBlockHash(String blockHash) {
		this.blockHash = blockHash;
	}
	public int getTranNum() {
		return tranNum;
	}
	public void setTranNum(int tranNum) {
		this.tranNum = tranNum;
	}
	public long getPacTime() {
		return pacTime;
	}
	public void setPacTime(long pacTime) {
		this.pacTime = pacTime;
	}
	public String getGenTime() {
		return genTime;
	}
	public void setGenTime(String genTime) {
		this.genTime = genTime;
	}
}
